package pl.allegro.automate.adapter.os;

import pl.allegro.automate.os.StartProcessCommand;

import javax.inject.Inject;
import java.io.IOException;
import java.nio.file.Path;

class StartOsProcessCommand implements StartProcessCommand {

    @Inject
    StartOsProcessCommand() {}

    @Override
    public void startProcess(Path path) throws IOException {
        new ProcessBuilder(path.toString())
            .start();
    }
}
