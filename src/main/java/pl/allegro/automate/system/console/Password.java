package pl.allegro.automate.system.console;

import org.immutables.value.Value;
import org.immutables.value.Value.Parameter;

@Value.Immutable
public interface Password {

    @Parameter
    char[] chars();
}
