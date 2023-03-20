package pl.allegro.automate.gui;

import pl.allegro.automate.AutomationStep;
import pl.allegro.automate.system.console.Password;

public interface TypeCharsAutomationStep extends AutomationStep {

    void typeChars(Password password);

    void typeChars(String text);
}
