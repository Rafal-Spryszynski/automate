package pl.allegro.automate.gui;

import pl.allegro.automate.AutomationStep;

public interface TakeScreenCaptureAutomationStep extends AutomationStep {

    Image takeScreenCapture();
}
