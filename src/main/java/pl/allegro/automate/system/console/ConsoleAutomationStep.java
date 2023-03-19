package pl.allegro.automate.system.console;

import pl.allegro.automate.AutomationStep;

public interface ConsoleAutomationStep extends AutomationStep {

    Password promptPassword(String prompt);
}
