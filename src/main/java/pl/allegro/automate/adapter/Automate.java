package pl.allegro.automate.adapter;

import pl.allegro.automate.AutomationStepsRegistry;
import pl.allegro.automate.flow.SleepCommand;
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
import java.time.Duration;

class Automate {

    private final AutomationStepsRegistry registry;

    @Inject
    Automate(AutomationStepsRegistry registry) {
        this.registry = registry;
    }

    void runAutomation() {
        ConsoleAutomationStep consoleAutomationStep = registry.get(ConsoleAutomationStep.class);
        TypeCharsAutomationStep typeCharsAutomationStep = registry.get(TypeCharsAutomationStep.class);
        StartProcessAutomationStep startProcessAutomationStep = registry.get(StartProcessAutomationStep.class);
        LoadImageCommand loadImageCommand = registry.get(LoadImageCommand.class);
        FindImageOnScreenAutomationStep findImageOnScreen = registry.get(FindImageOnScreenAutomationStep.class);
        SendMouseClickCommand sendMouseClickCommand = registry.get(SendMouseClickCommand.class);
        SleepCommand sleepCommand = registry.get(SleepCommand.class);

        Password domainPassword = consoleAutomationStep.promptPassword("Domain password: ");

        startProcessAutomationStep.startProcess(Paths.get("C:\\Program Files (x86)\\Cisco\\Cisco Secure Client\\UI\\csc_ui.exe"));

        Image vpnWindow1 = loadImageCommand.loadImage("vpn\\cisco client window 1.png");
        Image vpnWindow2 = loadImageCommand.loadImage("vpn\\cisco client window 2.png");

        ImageOnScreen vpnWindowOnScreen = findImageOnScreen.findImageOnScreen(vpnWindow1, vpnWindow2);

        Image connectButton1 = loadImageCommand.loadImage("vpn\\cisco connect button 1.png");
        Image connectButton2 = loadImageCommand.loadImage("vpn\\cisco connect button 2.png");

        ImageOnScreen connectButtonOnScreen = findImageOnScreen.findImageOnScreen(vpnWindowOnScreen, connectButton1, connectButton2);

        ScreenLocation connectButtonLocation = connectButtonOnScreen.screenLocation();
        Image connectButton = connectButtonOnScreen.image();
        sendMouseClickCommand.sendMouseClick(connectButtonLocation.withOffset(connectButton.center()));

        Image passwordWindow1 = loadImageCommand.loadImage("vpn\\cisco password window 1.png");
        Image passwordWindow2 = loadImageCommand.loadImage("vpn\\cisco password window 2.png");

        sleepCommand.sleep(Duration.ofSeconds(1));

        ImageOnScreen passwordWindowOnScreen = findImageOnScreen.findImageOnScreen(passwordWindow1, passwordWindow2);

//        Password domainPassword = ImmutablePassword.of("#$%^&*()-=_+[]{}\\|;':\",./<>?".toCharArray());
        typeCharsAutomationStep.typeChars(domainPassword);
        typeCharsAutomationStep.typeChars("\n");

        Image okButton = loadImageCommand.loadImage("vpn\\cisco ok button.png");

        ImageOnScreen okButtonOnScreen = findImageOnScreen.findImageOnScreen(passwordWindowOnScreen, okButton);

        ScreenLocation okButtonLocation = okButtonOnScreen.screenLocation();
        sendMouseClickCommand.sendMouseClick(okButtonLocation.withOffset(okButton.center()));
    }
}
