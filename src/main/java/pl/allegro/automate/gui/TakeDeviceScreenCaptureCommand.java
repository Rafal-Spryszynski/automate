package pl.allegro.automate.gui;

import pl.allegro.automate.metrics.Metrics;

import javax.inject.Inject;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

class TakeDeviceScreenCaptureCommand implements TakeScreenCaptureCommand {

    private final Metrics metrics;
    private final ImageCreator imageCreator;

    @Inject
    TakeDeviceScreenCaptureCommand(Metrics metrics, ImageCreator imageCreator) {
        this.metrics = metrics;
        this.imageCreator = imageCreator;
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
//        ImageIO.write(screenCapture, "png", Paths.get("C:\\Users\\rafal.spryszynski\\Desktop\\automate\\screen capture.png").toFile());
        return imageCreator.createImage(screenCapture);
    }
}
