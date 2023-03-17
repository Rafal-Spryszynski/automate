package pl.allegro.automate.os;

import pl.allegro.automate.VoidAutomationStep;

import java.nio.file.Path;

public interface StartProcessCommand extends VoidAutomationStep {

    default void startProcess(Path path) {
        run(path);
    }
}
