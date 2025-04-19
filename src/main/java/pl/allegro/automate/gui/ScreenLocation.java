package pl.allegro.automate.gui;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;
import org.immutables.value.Value.Parameter;

@JsonDeserialize(as = ImmutableScreenLocation.class)
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
