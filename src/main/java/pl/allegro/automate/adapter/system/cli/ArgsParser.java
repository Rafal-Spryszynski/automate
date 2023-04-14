package pl.allegro.automate.adapter.system.cli;

import io.vavr.control.Try;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

import javax.inject.Inject;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

import static java.util.function.Predicate.not;

public class ArgsParser {

    private final CommandLineParser parser;
    private final Options options;
    private final String[] args;
    private final Set<CommandLineArg<?>> commandLineArgs;

    @Inject
    ArgsParser(
        CommandLineParser parser,
        Options options,
        String[] args,
        Set<CommandLineArg<?>> commandLineArgs
    ) {
        this.parser = parser;
        this.options = options;
        this.args = args;
        this.commandLineArgs = commandLineArgs;
    }

    public Params parseArgs() {
        CommandLine commandLine = Try.of(() -> parser.parse(options, args)).get();
        return buildParams(commandLine);
    }

    private Params buildParams(CommandLine commandLine) {
        ImmutableParams.Builder paramsBuilder = ImmutableParams.builder();
        buildNoArgsParams(commandLine, paramsBuilder);
        buildParamsWithArgs(commandLine, paramsBuilder);
        return paramsBuilder.build();
    }

    private void buildNoArgsParams(CommandLine commandLine, ImmutableParams.Builder paramsBuilder) {
        commandLineArgs.stream()
            .filter(not(CommandLineArg::hasArg))
            .filter(commandLineArg -> commandLine.hasOption(commandLineArg.longName()))
            .map(CommandLineArg::setter)
            .map(setter -> setter.apply(paramsBuilder))
            .map(setter -> (Consumer<Boolean>) setter)
            .forEach(setter -> setter.accept(true));
    }

    private void buildParamsWithArgs(CommandLine commandLine, ImmutableParams.Builder paramsBuilder) {
        commandLineArgs.stream()
            .filter(CommandLineArgWithValue.class::isInstance)
            .map(CommandLineArgWithValue.class::cast)
            .forEach(commandLineArg ->
                Optional.ofNullable(commandLine.getOptionValue(commandLineArg.longName()))
                    .map(commandLineArg.mapper())
                    .ifPresent((Consumer<Object>) commandLineArg.setter().apply(paramsBuilder))
            );
    }

    public void displayHelp() {
        HelpFormatter helpFormatter = new HelpFormatter();
        helpFormatter.printHelp("java -jar target/automate-1.0-SNAPSHOT.jar", options);
    }
}
