package pl.allegro.automate.gui;

import java.nio.file.Path;

public interface LoadImageCommand {

    Image loadImage(Path path) throws Exception;
}
