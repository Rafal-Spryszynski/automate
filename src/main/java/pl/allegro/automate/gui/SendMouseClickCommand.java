package pl.allegro.automate.gui;

import pl.allegro.automate.VoidAutomationStep;

public interface SendMouseClickCommand extends VoidAutomationStep {

    default void sendMouseClick(ScreenLocation screenLocation) {
        run(screenLocation);
    }
}
