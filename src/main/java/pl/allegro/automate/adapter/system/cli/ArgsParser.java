package pl.allegro.automate.adapter.system.cli;

import io.vavr.control.Try;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import java.util.Optional;

public class ArgsParser {

    private CommandLine commandLine;

    public void parse(String[] args) {
        Options options = new Options();
        Option option = Option.builder()
            .longOpt("imagesPath")
            .hasArg()
            .desc("Images path location. Default: C:\\Users\\rafal.spryszynski\\Desktop\\automate")
            .build();
        options.addOption(option);
        options.addOption(null, "help", false, "Prints all available options");

        CommandLineParser parser = new DefaultParser();
        commandLine = Try.of(() -> parser.parse(options, args)).get();

        if (commandLine.hasOption("help")) {
            HelpFormatter helpFormatter = new HelpFormatter();
            helpFormatter.printHelp("java -jar target/automate-1.0-SNAPSHOT.jar", options);
        }
    }

    public String imagesPath() {
        return Optional.ofNullable(commandLine.getOptionValue("imagesPath"))
            .orElse("C:\\Users\\rafal.spryszynski\\Desktop\\automate");
    }
}
