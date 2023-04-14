package pl.allegro.automate.adapter.system.cli;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import java.util.Set;

@Module
interface CommandLineModule {

    @Provides
    @IntoSet
    static Option help() {
        return Option.builder()
            .longOpt("help")
            .desc("Prints all available options")
            .build();
    }

    @Provides
    static Params defaultParams() {
        return ImmutableParams.of();
    }

    @Provides
    @IntoSet
    static Option imagesPath(Params params) {
        return Option.builder()
            .longOpt("imagesPath")
            .hasArg()
            .desc("Images path location. Default: " + params.imagesPath())
            .build();
    }

    @Provides
    @IntoSet
    static Option saveScreenCaptures(Params params) {
        return Option.builder()
            .longOpt("saveScreenCaptures")
            .desc("Save screen captures to the \"screen captures\" directory. Default: " + params.saveScreenCaptures())
            .build();
    }

    @Provides
    @IntoSet
    static Option autoDelay(Params params) {
        return Option.builder()
            .longOpt("autoDelay")
            .hasArg()
            .desc("Auto delay duration. Default: " + params.autoDelay())
            .build();
    }

    @Provides
    @IntoSet
    static Option defaultSleepDuration(Params params) {
        return Option.builder()
            .longOpt("defaultSleepDuration")
            .hasArg()
            .desc("Default sleep duration. Default: " + params.defaultSleepDuration())
            .build();
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
