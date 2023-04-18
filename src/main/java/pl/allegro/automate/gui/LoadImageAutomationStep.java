package pl.allegro.automate.gui;

import pl.allegro.automate.AutomationStep;

public interface LoadImageAutomationStep extends AutomationStep {

    Image loadImage(String imageFileName);
}
