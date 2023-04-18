package pl.allegro.automate.adapter.awt.gui;

import pl.allegro.automate.Exchange;
import pl.allegro.automate.gui.Image;
import pl.allegro.automate.gui.TakeScreenCaptureAutomationStep;
import pl.allegro.automate.metrics.Metrics;

import javax.inject.Inject;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class TakeDeviceScreenCaptureAutomationStep implements TakeScreenCaptureAutomationStep {

    private final Robot robot;
    private final Metrics metrics;
    private final ImageCreator imageCreator;
    private final boolean saveScreenCaptures;
    private final SaveImageToDiskCommand saveImageCommand;
    private final Clock clock;
    private final Path imagesPath;

    @Inject
    TakeDeviceScreenCaptureAutomationStep(
        Robot robot,
        Metrics metrics,
        ImageCreator imageCreator,
        boolean saveScreenCaptures,
        SaveImageToDiskCommand saveImageCommand,
        Clock clock,
        Path imagesPath
    ) {
        this.robot = robot;
        this.metrics = metrics;
        this.imageCreator = imageCreator;
        this.saveScreenCaptures = saveScreenCaptures;
        this.saveImageCommand = saveImageCommand;
        this.clock = clock;
        this.imagesPath = imagesPath;
    }

    @Override
    public void execute(Exchange exchange) {
        exchange.expectNoParams();
        takeScreenCapture();
    }

    @Override
    public Image takeScreenCapture() {
        BufferedImage screenCapture = metrics.measure("createScreenCapture", () -> {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            Rectangle rectangle = new Rectangle(new Point(), screenSize);
            return robot.createScreenCapture(rectangle);
        });
        Image screenCaptureImage = imageCreator.createImage(screenCapture, "screen capture");

        optionallySaveScreenCapture(screenCapture);
        return screenCaptureImage;
    }

    private void optionallySaveScreenCapture(BufferedImage screenCapture) {
        if (saveScreenCaptures) {
            LocalDateTime now = LocalDateTime.now(clock);
            String format = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH_mm_ss_SSS"));
            Path path = imagesPath.resolve(MessageFormat.format("screen captures\\{0}.png", format));
            saveImageCommand.saveImage(screenCapture, path);
        }
    }
}
