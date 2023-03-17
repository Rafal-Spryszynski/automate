package pl.allegro.automate.gui;

import org.immutables.value.Value;

@Value.Immutable
public interface ImageOnScreen {

    Image screenCapture();

    ScreenLocation screenLocation();

    Image image();
}
