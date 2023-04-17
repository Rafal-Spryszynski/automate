package pl.allegro.automate.adapter;

import pl.allegro.automate.adapter.awt.gui.AwtGuiComponent;
import pl.allegro.automate.adapter.awt.gui.DaggerAwtGuiComponent;
import pl.allegro.automate.adapter.system.cli.ArgsParser;
import pl.allegro.automate.adapter.system.cli.DaggerCommandLineComponent;
import pl.allegro.automate.adapter.system.cli.Params;

class AutomateMain {

    public static void main(String[] args) {
        ArgsParser argsParser = DaggerCommandLineComponent.factory()
            .create(args)
            .argsParser();
        Params params = argsParser.parseArgs();

        if (params.displayHelp()) {
            argsParser.displayHelp();
            return;
        }
        AwtGuiComponent awtGuiComponent = DaggerAwtGuiComponent.builder()
            .imagesPath(params.imagesPath())
            .saveScreenCaptures(params.saveScreenCaptures())
            .autoDelay(params.autoDelay())
            .build();
        AutomateComponent automateComponent = DaggerAutomateComponent.builder()
            .awtGuiComponent(awtGuiComponent)
            .defaultSleepDuration(params.defaultSleepDuration())
            .automationFlowsPath(params.imagesPath())
            .build();
        Automate automate = automateComponent.automate();
//        automate.runAutomation();
        pl.allegro.automate.Automate automate2 = automateComponent.automate2();
        automate2.runAutomation();
    }
}
