package pl.allegro.automate.adapter.system.cli;

import org.immutables.value.Value;
import org.immutables.value.Value.Default;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;

@Value.Immutable(singleton = true)
public interface Params {

    @Default
    default boolean displayHelp() {
        return false;
    }

    @Default
    default Path imagesPath() {
        return Paths.get("C:\\Users\\rafal.spryszynski\\Desktop\\automate");
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
