package pl.allegro.automate.gui;

public interface Image {

    int height();

    int width();

    String name();

    default int bottom() {
        return height() - 1;
    }

    default int right() {
        return width() - 1;
    }

    default int verticalCenter() {
        return height() / 2;
    }

    default int horizontalCenter() {
        return width() / 2;
    }

    int getPixel(int y, int x);
}
