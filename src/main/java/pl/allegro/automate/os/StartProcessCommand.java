package pl.allegro.automate.os;

import pl.allegro.automate.AutomationStep;

import java.nio.file.Path;

public interface StartProcessCommand extends AutomationStep {

    void startProcess(Path path);
}
