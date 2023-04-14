package pl.allegro.automate.adapter.system.cli;

import org.immutables.value.Value;

import java.util.function.Function;

@Value.Immutable
interface CommandLineArgWithValue<T> extends CommandLineArg<T> {

    Function<String, T> mapper();
}
