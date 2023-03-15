package pl.allegro.automate.gui;

import java.nio.file.Path;

public interface SaveImageCommand {

    void saveImage(Image image, Path path) throws Exception;
}
