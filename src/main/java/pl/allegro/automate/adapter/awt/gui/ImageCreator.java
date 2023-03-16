package pl.allegro.automate.adapter.awt.gui;

import pl.allegro.automate.gui.Image;
import pl.allegro.automate.metrics.Metrics;

import javax.inject.Inject;
import java.awt.image.BufferedImage;
import java.util.stream.IntStream;

class ImageCreator {

    private final Metrics metrics;

    @Inject
    ImageCreator(Metrics metrics) {
        this.metrics = metrics;
    }

    Image createImage(BufferedImage image, String imageName) {
        int[][] imageCache = metrics.measure("cache \"{0}\" image", () -> cache(image), imageName);
        return ImmutableGuiImage.builder()
            .image(image)
            .imageCache(imageCache)
            .name(imageName)
            .build();
    }

    private int[][] cache(BufferedImage image) {
        int[][] imageCache = new int[image.getHeight()][];

        IntStream.range(0, imageCache.length)
            .forEach(y -> {
                imageCache[y] = new int[image.getWidth()];
                IntStream.range(0, imageCache[y].length)
                    .forEach(x -> imageCache[y][x] = image.getRGB(x, y));
            });
        return imageCache;
    }
}
