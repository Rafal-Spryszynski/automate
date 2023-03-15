package pl.allegro.automate.os;

import javax.inject.Inject;
import java.io.IOException;
import java.nio.file.Path;

class OsProcessCommand implements ProcessCommand {

    @Inject
    OsProcessCommand() {}

    @Override
    public void startProcess(Path path) throws IOException {
        new ProcessBuilder(path.toString())
            .start();
    }
}
