package pl.allegro.automate.adapter.awt.gui;

import org.immutables.value.Value;
import pl.allegro.automate.gui.Image;

import java.awt.image.BufferedImage;

@Value.Immutable
interface GuiImage extends Image {

    BufferedImage image();

    int[][] imageCache();

    @Override
    String name();

    @Override
    default int height() {
        return image().getHeight();
    }

    @Override
    default int width() {
        return image().getWidth();
    }

    @Override
    default int getPixel(int y, int x) {
        return imageCache()[y][x];
    }
}
