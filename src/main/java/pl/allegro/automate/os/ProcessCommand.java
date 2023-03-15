package pl.allegro.automate.os;

import java.io.IOException;
import java.nio.file.Path;

public interface ProcessCommand {

    void startProcess(Path path) throws IOException;
}
