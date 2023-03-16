package pl.allegro.automate.gui;

import org.immutables.value.Value;
import org.immutables.value.Value.Parameter;

@Value.Style(overshadowImplementation = true)
@Value.Immutable
public interface ScreenLocation {

    @Parameter
    int y();

    @Parameter
    int x();

    default ScreenLocation withOffset(ScreenLocation offset) {
        return ImmutableScreenLocation.of(y() + offset.y(), x() + offset.x());
    }
}
