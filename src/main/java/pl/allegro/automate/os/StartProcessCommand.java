package pl.allegro.automate.os;

import java.io.IOException;
import java.nio.file.Path;

public interface StartProcessCommand {

    void startProcess(Path path) throws IOException;
}
