package pl.allegro.automate.adapter.awt.gui;

import pl.allegro.automate.gui.Image;

import java.awt.image.BufferedImage;

class GuiImage implements Image {

    private final BufferedImage image;
    private final int[][] imageCache;
    private final String fileName;

    GuiImage(BufferedImage image, int[][] imageCache, String fileName) {
        this.image = image;
        this.imageCache = imageCache;
        this.fileName = fileName;
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
    public String name() {
        return fileName;
    }

    @Override
    public int getPixel(int y, int x) {
        return imageCache[y][x];
    }
}
