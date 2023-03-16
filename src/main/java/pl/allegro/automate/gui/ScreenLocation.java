package pl.allegro.automate.gui;

import org.immutables.value.Value;
import org.immutables.value.Value.Parameter;

@Value.Immutable
public interface ScreenLocation {

    @Parameter
    int y();

    @Parameter
    int x();
}
