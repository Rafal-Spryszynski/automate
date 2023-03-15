package pl.allegro.automate.gui;

public interface Image {

    int height();

    int width();

    default int bottom() {
        return height() - 1;
    }

    default int right() {
        return width() - 1;
    }

    int getPixel(int y, int x);
}
