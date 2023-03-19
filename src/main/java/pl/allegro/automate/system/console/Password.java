package pl.allegro.automate.system.console;

import org.immutables.value.Value;
import org.immutables.value.Value.Auxiliary;
import org.immutables.value.Value.Parameter;

@Value.Immutable
public interface Password {

    @Auxiliary
    @Parameter
    char[] chars();
}
