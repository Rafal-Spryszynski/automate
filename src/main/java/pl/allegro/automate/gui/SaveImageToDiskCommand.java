package pl.allegro.automate.gui;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;

class SaveImageToDiskCommand {

    @Inject
    SaveImageToDiskCommand() {}

    void saveImage(BufferedImage image, Path path) throws IOException {
        ImageIO.write(image, "png", path.toFile());
    }
}
