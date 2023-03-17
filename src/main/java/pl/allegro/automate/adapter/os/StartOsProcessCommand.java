package pl.allegro.automate.adapter.os;

import io.vavr.control.Try;
import pl.allegro.automate.os.StartProcessCommand;

import javax.inject.Inject;
import java.nio.file.Path;

class StartOsProcessCommand implements StartProcessCommand {

    @Inject
    StartOsProcessCommand() {}

    @Override
    public void run(Object... arguments) {
        Path path = (Path) arguments[0];
        Try.run(() ->
            new ProcessBuilder(path.toString())
                .start()
        ).get();
    }
}
