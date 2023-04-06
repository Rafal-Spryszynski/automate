package pl.allegro.automate.adapter;

import pl.allegro.automate.adapter.awt.gui.AwtGuiComponent;
import pl.allegro.automate.adapter.awt.gui.DaggerAwtGuiComponent;
import pl.allegro.automate.adapter.system.cli.ArgsParser;

import java.nio.file.Paths;
import java.time.Duration;

class AutomateMain {

    public static void main(String[] args) {
        ArgsParser argsParser = new ArgsParser();
        argsParser.parse(args);
        AwtGuiComponent awtGuiComponent = DaggerAwtGuiComponent.builder()
            .imagesPath(Paths.get(argsParser.imagesPath()))
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
