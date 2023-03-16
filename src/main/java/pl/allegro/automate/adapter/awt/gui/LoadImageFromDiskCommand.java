package pl.allegro.automate.adapter.awt.gui;

import pl.allegro.automate.gui.Image;
import pl.allegro.automate.gui.LoadImageCommand;
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
    public Image loadImage(Path path) {
        String fileName = path.getFileName().toString();
        BufferedImage image = metrics.measure("load \"{0}\" image from disk", () -> ImageIO.read(path.toFile()), fileName);
        return imageCreator.createImage(image, fileName);
    }
}
