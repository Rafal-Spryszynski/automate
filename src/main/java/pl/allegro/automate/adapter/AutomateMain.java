package pl.allegro.automate.adapter;

import pl.allegro.automate.Automate;
import pl.allegro.automate.AutomationFlow;
import pl.allegro.automate.adapter.awt.gui.AwtGuiComponent;
import pl.allegro.automate.adapter.awt.gui.DaggerAwtGuiComponent;
import pl.allegro.automate.adapter.flow.file.reader.AutomationFlowFileReader;
import pl.allegro.automate.adapter.system.cli.ArgsParser;
import pl.allegro.automate.adapter.system.cli.DaggerCommandLineComponent;
import pl.allegro.automate.adapter.system.cli.Params;
import pl.allegro.automate.record.AutomationRecorder;

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
            .imagesPath(params.filesPath())
            .saveScreenCaptures(params.saveScreenCaptures())
            .autoDelay(params.autoDelay())
            .build();
        AutomateComponent automateComponent = DaggerAutomateComponent.builder()
            .awtGuiComponent(awtGuiComponent)
            .defaultSleepDuration(params.defaultSleepDuration())
            .filesPath(params.filesPath())
            .automationFileName(params.automationFileName())
            .build();

        if (params.record()) {
            AutomationRecorder automationRecorder = automateComponent.automationRecorder();
            automationRecorder.recordAutomation();
            return;
        }
        AutomationFlowFileReader automationFlowFileReader = automateComponent.automationFlowFileReader();
        AutomationFlow automationFlow = automationFlowFileReader.readAutomationFlow();
        Automate automate = automateComponent.automate();
        automate.runAutomation(automationFlow);
    }
}
