package pl.allegro.automate.gui;

import pl.allegro.automate.AutomationStep;

public interface LoadImageCommand extends AutomationStep {

    Image loadImage(String imageFileName);
}
