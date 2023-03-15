package pl.allegro.automate.gui;

import pl.allegro.automate.metrics.Metrics;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.util.function.IntBinaryOperator;
import java.util.stream.IntStream;

class LoadImageFromDiskCommand implements LoadImageCommand {

    private final Metrics metrics;

    @Inject
    LoadImageFromDiskCommand(Metrics metrics) {
        this.metrics = metrics;
    }

    @Override
    public Image loadImage(Path path) throws Exception {
        BufferedImage image = metrics.measure("load image from disk", () -> ImageIO.read(path.toFile()));
        int yStart = image.getHeight() - 1;
        int xStart = image.getWidth() - 1;
        int[][] imageCache = metrics.measure("cache image", () -> cache(image));
        IntBinaryOperator imageFunction = (y, x) -> imageCache[y][x];
        return null;
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
