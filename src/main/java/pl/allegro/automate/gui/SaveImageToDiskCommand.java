package pl.allegro.automate.gui;

import io.vavr.control.Try;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import java.awt.image.BufferedImage;
import java.nio.file.Path;

class SaveImageToDiskCommand {

    @Inject
    SaveImageToDiskCommand() {}

    void saveImage(BufferedImage image, Path path) {
        Try.run(() -> ImageIO.write(image, "png", path.toFile()))
            .get();
    }
}
