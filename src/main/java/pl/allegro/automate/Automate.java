package pl.allegro.automate;

import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.allegro.automate.gui.Image;
import pl.allegro.automate.gui.LoadImageCommand;
import pl.allegro.automate.os.ProcessCommand;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.lang.invoke.MethodHandles;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.IntBinaryOperator;

class Automate {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final ProcessCommand processCommand;
    private final LoadImageCommand loadImageCommand;

    @Inject
    Automate(ProcessCommand processCommand, LoadImageCommand loadImageCommand) {
        this.processCommand = processCommand;
        this.loadImageCommand = loadImageCommand;
    }

    void runAutomation() throws Exception {
        processCommand.startProcess(Paths.get("C:\\Program Files (x86)\\Cisco\\Cisco Secure Client\\UI\\csc_ui.exe"));
        Image vpnWindowImage = loadImageCommand.loadImage(Paths.get("C:\\Users\\rafal.spryszynski\\Desktop\\automate\\cisco client.png"));
        BufferedImage image = measure("load image from disk", () -> {
            Path path = Paths.get("C:\\Users\\rafal.spryszynski\\Desktop\\automate\\cisco client.png");
            return ImageIO.read(path.toFile());
        });
        int yStart = image.getHeight() - 1;
        int xStart = image.getWidth() - 1;
        int[][] imageCache = measure("cache image", () -> cache(image));
        IntBinaryOperator imageFunction = (y, x) -> imageCache[y][x];

        Robot robot = new Robot();

        robot.delay(200);

        BufferedImage screenCapture = measure("createScreenCapture", () -> {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            Rectangle rectangle = new Rectangle(new Point(), screenSize);
            return robot.createScreenCapture(rectangle);
        });
        ImageIO.write(screenCapture, "png", Paths.get("C:\\Users\\rafal.spryszynski\\Desktop\\automate\\screen capture.png").toFile());
        int[][] screenCaptureCache = measure("cache screen capture", () -> cache(screenCapture));
        findOccurrences("find occurrences", screenCapture, yStart, xStart, (y, x) -> screenCaptureCache[y][x], imageFunction);
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
