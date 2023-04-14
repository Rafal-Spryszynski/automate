package pl.allegro.automate.adapter;

import pl.allegro.automate.adapter.awt.gui.AwtGuiComponent;
import pl.allegro.automate.adapter.awt.gui.DaggerAwtGuiComponent;
import pl.allegro.automate.adapter.system.cli.ArgsParser;
import pl.allegro.automate.adapter.system.cli.DaggerCommandLineComponent;
import pl.allegro.automate.adapter.system.cli.Params;

import java.time.Duration;

class AutomateMain {

    public static void main(String[] args) {
        ArgsParser argsParser = DaggerCommandLineComponent.factory()
            .create(args)
            .argsParser();
        Params params = argsParser.parseArgs();

        AwtGuiComponent awtGuiComponent = DaggerAwtGuiComponent.builder()
            .imagesPath(params.imagesPath())
            .saveScreenCaptures(true)
            .autoDelay(Duration.ofMillis(100))
            .build();
        AutomateComponent automateComponent = DaggerAutomateComponent.builder()
            .awtGuiComponent(awtGuiComponent)
            .defaultSleepDuration(Duration.ofMillis(200))
            .build();
        Automate automate = automateComponent.automate();
//        automate.runAutomation();
    }
}
