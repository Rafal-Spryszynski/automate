package pl.allegro.automate.adapter;

import pl.allegro.automate.AutomationStepsRegistry;
import pl.allegro.automate.gui.FindImageOnScreenAutomationStep;
import pl.allegro.automate.gui.Image;
import pl.allegro.automate.gui.ImageOnScreen;
import pl.allegro.automate.gui.LoadImageCommand;
import pl.allegro.automate.gui.ScreenLocation;
import pl.allegro.automate.gui.SendMouseClickCommand;
import pl.allegro.automate.gui.TypeCharsAutomationStep;
import pl.allegro.automate.system.console.ConsoleAutomationStep;
import pl.allegro.automate.system.console.Password;
import pl.allegro.automate.system.process.StartProcessAutomationStep;

import javax.inject.Inject;
import java.nio.file.Paths;

class StartFirefoxAutomation {

    private final AutomationStepsRegistry registry;

    @Inject
    StartFirefoxAutomation(AutomationStepsRegistry registry) {
        this.registry = registry;
    }

    void startFirefox() {
        ConsoleAutomationStep consoleAutomationStep = registry.get(ConsoleAutomationStep.class);
        StartProcessAutomationStep startProcessAutomationStep = registry.get(StartProcessAutomationStep.class);
        LoadImageCommand loadImageCommand = registry.get(LoadImageCommand.class);
        FindImageOnScreenAutomationStep findImageOnScreen = registry.get(FindImageOnScreenAutomationStep.class);
        SendMouseClickCommand sendMouseClickCommand = registry.get(SendMouseClickCommand.class);
        TypeCharsAutomationStep typeCharsAutomationStep = registry.get(TypeCharsAutomationStep.class);

        Password passwordManagerPassword = consoleAutomationStep.promptPassword("Password manager password: ");

        startProcessAutomationStep.startProcess(Paths.get("C:\\Program Files\\Mozilla Firefox\\firefox.exe"));

        Image bitwardenPlugin = loadImageCommand.loadImage("firefox\\bitwarden plugin.png");
        Image bitwardenIsOpen = loadImageCommand.loadImage("firefox\\bitwarden open.png");

        ImageOnScreen chromeBitwardenOnScreen = findImageOnScreen.findImageOnScreen(bitwardenPlugin);

        ScreenLocation chromeBitwardenScreenLocation = chromeBitwardenOnScreen.screenLocation();
        sendMouseClickCommand.sendMouseClick(chromeBitwardenScreenLocation.withOffset(bitwardenPlugin.center()));

        findImageOnScreen.findImageOnScreen(bitwardenIsOpen);

        typeCharsAutomationStep.typeChars(passwordManagerPassword);
        typeCharsAutomationStep.typeChars("\n");

        sendMouseClickCommand.sendMouseClick(chromeBitwardenScreenLocation.withOffset(bitwardenPlugin.center()));
    }
}
