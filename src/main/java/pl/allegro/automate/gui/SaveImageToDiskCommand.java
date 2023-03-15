package pl.allegro.automate.gui;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import java.awt.image.BufferedImage;
import java.nio.file.Path;

class SaveImageToDiskCommand implements SaveImageCommand {

    @Inject
    SaveImageToDiskCommand() {}

    @Override
    public void saveImage(Image image, Path path) throws Exception {
        BufferedImage bufferedImage = ((GuiImage) image).getBufferedImage();
        ImageIO.write(bufferedImage, "png", path.toFile());
    }
}
