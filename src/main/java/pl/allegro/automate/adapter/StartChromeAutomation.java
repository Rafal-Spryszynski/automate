package pl.allegro.automate.adapter;

import pl.allegro.automate.AutomationStepsRegistry;
import pl.allegro.automate.gui.FindImageOnScreenAutomationStep;
import pl.allegro.automate.gui.Image;
import pl.allegro.automate.gui.ImageOnScreen;
import pl.allegro.automate.gui.LoadImageAutomationStep;
import pl.allegro.automate.gui.ScreenLocation;
import pl.allegro.automate.gui.SendMouseClickAutomationStep;
import pl.allegro.automate.gui.TypeCharsAutomationStep;
import pl.allegro.automate.system.console.ConsoleAutomationStep;
import pl.allegro.automate.system.console.Password;
import pl.allegro.automate.system.process.StartProcessAutomationStep;

import javax.inject.Inject;
import java.nio.file.Paths;

class StartChromeAutomation {

    private final AutomationStepsRegistry registry;

    @Inject
    StartChromeAutomation(AutomationStepsRegistry registry) {
        this.registry = registry;
    }

    void startChrome() {
        ConsoleAutomationStep consoleAutomationStep = registry.get(ConsoleAutomationStep.class);
        StartProcessAutomationStep startProcessAutomationStep = registry.get(StartProcessAutomationStep.class);
        LoadImageAutomationStep loadImageAutomationStep = registry.get(LoadImageAutomationStep.class);
        FindImageOnScreenAutomationStep findImageOnScreen = registry.get(FindImageOnScreenAutomationStep.class);
        SendMouseClickAutomationStep sendMouseClickAutomationStep = registry.get(SendMouseClickAutomationStep.class);
        TypeCharsAutomationStep typeCharsAutomationStep = registry.get(TypeCharsAutomationStep.class);

        Password passwordManagerPassword = consoleAutomationStep.promptPassword("Password manager password: ");

        startProcessAutomationStep.startProcess(Paths.get("C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe"));

        Image bitwardenPlugin = loadImageAutomationStep.loadImage("chrome\\bitwarden plugin.png");
        Image bitwardenIsOpen = loadImageAutomationStep.loadImage("chrome\\bitwarden open.png");

        ImageOnScreen chromeBitwardenOnScreen = findImageOnScreen.findImageOnScreen(bitwardenPlugin);

        ScreenLocation chromeBitwardenScreenLocation = chromeBitwardenOnScreen.screenLocation();
        sendMouseClickAutomationStep.sendMouseClick(chromeBitwardenScreenLocation.withOffset(bitwardenPlugin.center()));

        findImageOnScreen.findImageOnScreen(bitwardenIsOpen);

        typeCharsAutomationStep.typeChars(passwordManagerPassword);
        typeCharsAutomationStep.typeChars("\n");

        sendMouseClickAutomationStep.sendMouseClick(chromeBitwardenScreenLocation.withOffset(bitwardenPlugin.center()));
    }
}
