package pl.allegro.automate.gui;

import pl.allegro.automate.AutomationStep;

public interface SendMouseClickAutomationStep extends AutomationStep {

    void sendMouseClick(ScreenLocation screenLocation);
}
