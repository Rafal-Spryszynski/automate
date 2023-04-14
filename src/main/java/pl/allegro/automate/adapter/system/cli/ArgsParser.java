package pl.allegro.automate.adapter.system.cli;

import io.vavr.control.Try;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

import javax.inject.Inject;
import java.nio.file.Paths;
import java.util.Optional;

public class ArgsParser {

    private final CommandLineParser parser;
    private final Options options;
    private final String[] args;

    @Inject
    ArgsParser(CommandLineParser parser, Options options, String[] args) {
        this.parser = parser;
        this.options = options;
        this.args = args;
    }

    public Params parseArgs() {
        CommandLine commandLine = Try.of(() -> parser.parse(options, args)).get();
        ImmutableParams.Builder paramsBuilder = ImmutableParams.builder();

        if (commandLine.hasOption("help")) {
            HelpFormatter helpFormatter = new HelpFormatter();
            helpFormatter.printHelp("java -jar target/automate-1.0-SNAPSHOT.jar", options);
        }
        Optional.ofNullable(commandLine.getOptionValue("imagesPath"))
            .map(Paths::get)
            .ifPresent(paramsBuilder::imagesPath);
        if (commandLine.hasOption("saveScreenCaptures")) {
            paramsBuilder.saveScreenCaptures(true);
        }
        return paramsBuilder.build();
    }
}
