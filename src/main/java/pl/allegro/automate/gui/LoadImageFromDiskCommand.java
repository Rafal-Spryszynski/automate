package pl.allegro.automate.gui;

import pl.allegro.automate.metrics.Metrics;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import java.awt.image.BufferedImage;
import java.nio.file.Path;

class LoadImageFromDiskCommand implements LoadImageCommand {

    private final Metrics metrics;
    private final ImageCreator imageCreator;

    @Inject
    LoadImageFromDiskCommand(Metrics metrics, ImageCreator imageCreator) {
        this.metrics = metrics;
        this.imageCreator = imageCreator;
    }

    @Override
    public Image loadImage(Path path) throws Exception {
        BufferedImage image = metrics.measure("load image from disk", () -> ImageIO.read(path.toFile()));
        return imageCreator.createImage(image);
    }
}
