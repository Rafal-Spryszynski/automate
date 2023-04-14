package pl.allegro.automate.adapter.system.cli;

import org.immutables.value.Value;
import org.immutables.value.Value.Default;

import java.nio.file.Path;
import java.nio.file.Paths;

@Value.Immutable
public interface Params {

    @Default
    default Path imagesPath() {
        return Paths.get("C:\\Users\\rafal.spryszynski\\Desktop\\automate");
    }

    @Default
    default boolean saveScreenCaptures() {
        return false;
    }
}
