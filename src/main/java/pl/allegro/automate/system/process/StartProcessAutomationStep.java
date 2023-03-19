package pl.allegro.automate.system.process;

import pl.allegro.automate.AutomationStep;

import java.nio.file.Path;

public interface StartProcessAutomationStep extends AutomationStep {

    void startProcess(Path path);
}
