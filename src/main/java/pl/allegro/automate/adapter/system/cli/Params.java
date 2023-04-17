package pl.allegro.automate.adapter.system.cli;

import org.apache.commons.lang3.SystemUtils;
import org.immutables.value.Value;
import org.immutables.value.Value.Default;

import java.nio.file.Path;
import java.time.Duration;

@Value.Immutable(singleton = true)
public interface Params {

    @Default
    default boolean displayHelp() {
        return false;
    }

    @Default
    default Path imagesPath() {
        return SystemUtils.getUserDir().toPath();
    }

    @Default
    default boolean saveScreenCaptures() {
        return false;
    }

    @Default
    default Duration autoDelay() {
        return Duration.ofMillis(100);
    }

    @Default
    default Duration defaultSleepDuration() {
        return Duration.ofMillis(200);
    }
}
