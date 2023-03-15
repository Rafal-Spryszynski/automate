package pl.allegro.automate.gui;

import java.awt.image.BufferedImage;

class GuiImage implements Image {

    private final BufferedImage image;
    private final int[][] imageCache;

    GuiImage(BufferedImage image, int[][] imageCache) {
        this.image = image;
        this.imageCache = imageCache;
    }

    @Override
    public int height() {
        return image.getHeight();
    }

    @Override
    public int width() {
        return image.getWidth();
    }

    @Override
    public int getPixel(int y, int x) {
        return imageCache[y][x];
    }

    BufferedImage getBufferedImage() {
        return image;
    }
}
