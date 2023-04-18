package pl.allegro.automate.adapter.awt.gui;

import pl.allegro.automate.Exchange;
import pl.allegro.automate.gui.Image;
import pl.allegro.automate.gui.LoadImageAutomationStep;
import pl.allegro.automate.metrics.Metrics;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import java.awt.image.BufferedImage;
import java.nio.file.Path;

class LoadImageFromDiskAutomationStep implements LoadImageAutomationStep {

    private final Path imagesPath;
    private final Metrics metrics;
    private final ImageCreator imageCreator;

    @Inject
    LoadImageFromDiskAutomationStep(Path imagesPath, Metrics metrics, ImageCreator imageCreator) {
        this.imagesPath = imagesPath;
        this.metrics = metrics;
        this.imageCreator = imageCreator;
    }

    @Override
    public void execute(Exchange exchange) {
        String imageFileName = exchange.getSingleParam();
        loadImage(imageFileName);
    }

    @Override
    public Image loadImage(String imageFileName) {
        Path imageFilePath = imagesPath.resolve(imageFileName);
        BufferedImage image = metrics.measure("load \"{0}\" image from disk", () -> ImageIO.read(imageFilePath.toFile()), imageFileName);
        return imageCreator.createImage(image, imageFileName);
    }
}
