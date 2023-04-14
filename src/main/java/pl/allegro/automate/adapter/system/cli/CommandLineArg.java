package pl.allegro.automate.adapter.system.cli;

import org.apache.commons.cli.Option;

import java.util.function.Consumer;
import java.util.function.Function;

interface CommandLineArg<T> {

    default String longName() {
        return option().getLongOpt();
    }

    default boolean hasArg() {
        return option().hasArg();
    }

    Option option();

    Function<ImmutableParams.Builder, Consumer<T>> setter();
}
