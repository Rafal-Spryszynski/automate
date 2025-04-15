package pl.allegro.automate.adapter.system.cli;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Set;

import static java.util.stream.Collectors.toUnmodifiableSet;

@Module
interface CommandLineModule {

    @Provides
    @IntoSet
    static CommandLineArg<?> help() {
        Option option = Option.builder()
            .longOpt("help")
            .desc("Prints all available options")
            .build();
        return ImmutableCommandLineArgNoValue.<Boolean>builder()
            .option(option)
            .setter(builder -> builder::displayHelp)
            .build();
    }

    @Provides
    static Params defaultParams() {
        return ImmutableParams.of();
    }

    @Provides
    @IntoSet
    static CommandLineArg<?> imagesPath(Params params) {
        Option option = Option.builder()
            .longOpt("filesPath")
            .hasArg()
            .desc("Files path location. Default: " + params.filesPath())
            .build();
        return ImmutableCommandLineArgWithValue.<Path>builder()
            .option(option)
            .mapper(Paths::get)
            .setter(builder -> builder::filesPath)
            .build();
    }

    @Provides
    @IntoSet
    static CommandLineArg<?> saveScreenCaptures(Params params) {
        Option option = Option.builder()
            .longOpt("saveScreenCaptures")
            .desc("Save screen captures to the \"screen captures\" directory. Default: " + params.saveScreenCaptures())
            .build();
        return ImmutableCommandLineArgNoValue.<Boolean>builder()
            .option(option)
            .setter(builder -> builder::saveScreenCaptures)
            .build();
    }

    @Provides
    @IntoSet
    static CommandLineArg<?> autoDelay(Params params) {
        Option option = Option.builder()
            .longOpt("autoDelay")
            .hasArg()
            .desc("Auto delay duration. Default: " + params.autoDelay())
            .build();
        return ImmutableCommandLineArgWithValue.<Duration>builder()
            .option(option)
            .mapper(Duration::parse)
            .setter(builder -> builder::autoDelay)
            .build();
    }

    @Provides
    @IntoSet
    static CommandLineArg<?> defaultSleepDuration(Params params) {
        Option option = Option.builder()
            .longOpt("defaultSleepDuration")
            .hasArg()
            .desc("Default sleep duration. Default: " + params.defaultSleepDuration())
            .build();
        return ImmutableCommandLineArgWithValue.<Duration>builder()
            .option(option)
            .mapper(Duration::parse)
            .setter(builder -> builder::defaultSleepDuration)
            .build();
    }

    @Provides
    @IntoSet
    static CommandLineArg<?> record(Params params) {
        Option option = Option.builder()
            .longOpt("record")
            .desc("Record automation flow to the file. Default: " + params.record())
            .build();
        return ImmutableCommandLineArgNoValue.<Boolean>builder()
            .option(option)
            .setter(builder -> builder::record)
            .build();
    }

    @Provides
    static Set<Option> optionsSet(Set<CommandLineArg<?>> commandLineArgs) {
        return commandLineArgs.stream()
            .map(CommandLineArg::option)
            .collect(toUnmodifiableSet());
    }

    @Provides
    static Options options(Set<Option> optionsSet) {
        Options options = new Options();
        optionsSet.forEach(options::addOption);
        return options;
    }

    @Provides
    static CommandLineParser commandLineParser() {
        return new DefaultParser();
    }
}
