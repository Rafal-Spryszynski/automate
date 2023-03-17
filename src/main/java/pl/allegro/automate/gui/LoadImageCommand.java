package pl.allegro.automate.gui;

import pl.allegro.automate.AutomationStep;

public interface LoadImageCommand extends AutomationStep {

    default Image loadImage(String imageFileName) {
        return (Image) execute(imageFileName);
    }
}
