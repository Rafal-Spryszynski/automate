package pl.allegro.automate.gui;

import pl.allegro.automate.AutomationStep;

public interface TakeScreenCaptureCommand extends AutomationStep {

    default Image takeScreenCapture() {
        return (Image) execute();
    }
}
