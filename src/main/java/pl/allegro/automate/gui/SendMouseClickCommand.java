package pl.allegro.automate.gui;

import pl.allegro.automate.AutomationStep;

public interface SendMouseClickCommand extends AutomationStep {

    void sendMouseClick(ScreenLocation screenLocation);
}
