package pl.allegro.automate;

import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Callable;
import java.util.function.IntBinaryOperator;
import java.util.stream.IntStream;

class AutomateMain {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public static void main(String[] args) throws Exception {
        new ProcessBuilder("C:\\Program Files (x86)\\Cisco\\Cisco Secure Client\\UI\\csc_ui.exe")
            .start();
        BufferedImage image = measure("load image from disk", () -> {
            Path path = Paths.get("C:\\Users\\rafal.spryszynski\\Desktop\\automate\\cisco client.png");
            return ImageIO.read(path.toFile());
        });
        int yStart = image.getHeight() - 1;
        int xStart = image.getWidth() - 1;
        int[][] imageCache = measure("cache image", () -> cache(image));
        IntBinaryOperator imageFunction = (y, x) -> imageCache[y][x];

        Robot robot = new Robot();

        robot.delay(2000);

        BufferedImage screenCapture = measure("createScreenCapture", () -> {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            Rectangle rectangle = new Rectangle(new Point(), screenSize);
            return robot.createScreenCapture(rectangle);
        });
        ImageIO.write(screenCapture, "png", Paths.get("C:\\Users\\rafal.spryszynski\\Desktop\\automate\\screen capture.png").toFile());
        int[][] screenCaptureCache = measure("cache screen capture", () -> cache(screenCapture));
        findOccurrences("find occurrences", screenCapture, yStart, xStart, (y, x) -> screenCaptureCache[y][x], imageFunction);
    }

    private static <T> T measure(String label, Callable<T> action) throws Exception {
        StopWatch stopWatch = new StopWatch(label);
        stopWatch.start();
        T result = action.call();
        stopWatch.stop();
        LOG.info("{}", stopWatch);
        return result;
    }

    private static int[][] cache(BufferedImage image) {
        int[][] imageCache = new int[image.getHeight()][];

        IntStream.range(0, imageCache.length)
            .forEach(y -> {
                imageCache[y] = new int[image.getWidth()];
                IntStream.range(0, imageCache[y].length)
                    .forEach(x -> imageCache[y][x] = image.getRGB(x, y));
            });
        return imageCache;
    }

    private static void findOccurrences(
        String label,
        BufferedImage screenCapture,
        int yStart,
        int xStart,
        IntBinaryOperator screenCaptureFunction,
        IntBinaryOperator imageFunction
    ) {
        StopWatch stopWatch = new StopWatch(label);
        stopWatch.start();
        for (int y = yStart; y < screenCapture.getHeight(); y++) {
            for (int x = xStart; x < screenCapture.getWidth(); x++) {
                int yCheck = y;
                imageLoop:
                for (int imageY = yStart; 0 <= imageY; --imageY, --yCheck) {
                    int xCheck = x;
                    for (int imageX = xStart; 0 <= imageX; --imageX, --xCheck) {
                        if (screenCaptureFunction.applyAsInt(yCheck, xCheck) != imageFunction.applyAsInt(imageY, imageX)) {
                            break imageLoop;
                        }
                        if (imageY == 0 && imageX == 0) {
                            stopWatch.stop();
                            LOG.info("hit {},{} {}", yCheck, xCheck, stopWatch);
                            return;
                        }
                    }
                }
            }
        }
        stopWatch.stop();
        LOG.info("finish {}", stopWatch);
    }
}
