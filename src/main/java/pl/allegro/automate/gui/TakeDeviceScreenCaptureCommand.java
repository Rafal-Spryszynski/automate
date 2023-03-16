package pl.allegro.automate.gui;

import pl.allegro.automate.metrics.Metrics;

import javax.inject.Inject;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class TakeDeviceScreenCaptureCommand implements TakeScreenCaptureCommand {

    private final Metrics metrics;
    private final ImageCreator imageCreator;
    private final boolean saveScreenCaptures;
    private final SaveImageToDiskCommand saveImageCommand;
    private final Clock clock;

    @Inject
    TakeDeviceScreenCaptureCommand(
        Metrics metrics,
        ImageCreator imageCreator,
        boolean saveScreenCaptures,
        SaveImageToDiskCommand saveImageCommand,
        Clock clock
    ) {
        this.metrics = metrics;
        this.imageCreator = imageCreator;
        this.saveScreenCaptures = saveScreenCaptures;
        this.saveImageCommand = saveImageCommand;
        this.clock = clock;
    }

    @Override
    public Image takeScreenCapture() throws Exception {
        Robot robot = new Robot();

        robot.delay(200);

        BufferedImage screenCapture = metrics.measure("createScreenCapture", () -> {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            Rectangle rectangle = new Rectangle(new Point(), screenSize);
            return robot.createScreenCapture(rectangle);
        });
        Image screenCaptureImage = imageCreator.createImage(screenCapture);

        if (saveScreenCaptures) {
            LocalDateTime now = LocalDateTime.now(clock);
            String format = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH_mm_ss_SSS"));
            Path path = Paths.get(MessageFormat.format("C:\\Users\\rafal.spryszynski\\Desktop\\automate\\screen captures\\{0}.png", format));
            saveImageCommand.saveImage(screenCapture, path);
        }
        return screenCaptureImage;
    }
}
