package pl.allegro.automate.adapter.awt.gui;

import pl.allegro.automate.gui.Image;
import pl.allegro.automate.gui.LoadImageCommand;
import pl.allegro.automate.metrics.Metrics;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import java.awt.image.BufferedImage;
import java.nio.file.Path;

class LoadImageFromDiskCommand implements LoadImageCommand {

    private final Path imagesPath;
    private final Metrics metrics;
    private final ImageCreator imageCreator;

    @Inject
    LoadImageFromDiskCommand(Path imagesPath, Metrics metrics, ImageCreator imageCreator) {
        this.imagesPath = imagesPath;
        this.metrics = metrics;
        this.imageCreator = imageCreator;
    }

    @Override
    public Image execute(Object... arguments) {
        String imageFileName = (String) arguments[0];
        Path imageFilePath = imagesPath.resolve(imageFileName);
        BufferedImage image = metrics.measure("load \"{0}\" image from disk", () -> ImageIO.read(imageFilePath.toFile()), imageFileName);
        return imageCreator.createImage(image, imageFileName);
    }
}
