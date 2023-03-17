package pl.allegro.automate.adapter;

import io.vavr.Tuple;
import pl.allegro.automate.AutomationStepsRegistry;
import pl.allegro.automate.flow.LoopCommand;
import pl.allegro.automate.flow.SleepCommand;
import pl.allegro.automate.gui.FindImageInImageCommand;
import pl.allegro.automate.gui.Image;
import pl.allegro.automate.gui.LoadImageCommand;
import pl.allegro.automate.gui.ScreenLocation;
import pl.allegro.automate.gui.SendMouseClickCommand;
import pl.allegro.automate.gui.TakeScreenCaptureCommand;
import pl.allegro.automate.os.StartProcessCommand;

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
        StartProcessCommand startProcessCommand = registry.get(StartProcessCommand.class);
        LoadImageCommand loadImageCommand = registry.get(LoadImageCommand.class);
        LoopCommand loopCommand = registry.get(LoopCommand.class);
        TakeScreenCaptureCommand takeScreenCaptureCommand = registry.get(TakeScreenCaptureCommand.class);
        FindImageInImageCommand findImageInImageCommand = registry.get(FindImageInImageCommand.class);
        SendMouseClickCommand sendMouseClickCommand = registry.get(SendMouseClickCommand.class);
        SleepCommand sleepCommand = registry.get(SleepCommand.class);

        startProcessCommand.startProcess(Paths.get("C:\\Program Files (x86)\\Cisco\\Cisco Secure Client\\UI\\csc_ui.exe"));

        Image vpnWindow = loadImageCommand.loadImage("cisco client window.png");

        var findVpnWindowResult = loopCommand.loop(() -> {
            Image screenCapture = takeScreenCaptureCommand.takeScreenCapture();
            return findImageInImageCommand.findImageInImage(screenCapture, vpnWindow)
                .map(screenLocation -> Tuple.of(screenCapture, screenLocation));
        });
        Image screenCapture1 = findVpnWindowResult._1;
        ScreenLocation vpnWindowLocation = findVpnWindowResult._2;

        Image connectButton = loadImageCommand.loadImage("cisco connect button.png");

        ScreenLocation connectButtonLocation = loopCommand.loop(
            () -> findImageInImageCommand.findImageInImage(
                screenCapture1,
                connectButton,
                vpnWindowLocation,
                vpnWindow.size()
            )
        );
        sendMouseClickCommand.sendMouseClick(connectButtonLocation.withOffset(connectButton.center()));

        Image passwordWindow1 = loadImageCommand.loadImage("cisco password window 1.png");
        Image passwordWindow2 = loadImageCommand.loadImage("cisco password window 2.png");
        Image okButton = loadImageCommand.loadImage("cisco ok button.png");

        sleepCommand.sleep(Duration.ofSeconds(1));

        var findPasswordWindowResult = loopCommand.loop(() -> {
            Image screenCapture = takeScreenCaptureCommand.takeScreenCapture();
            return findImageInImageCommand.findImageInImage(screenCapture, passwordWindow1)
                .orElse(() -> findImageInImageCommand.findImageInImage(screenCapture, passwordWindow2))
                .map(screenLocation -> Tuple.of(screenCapture, screenLocation));
        });
        Image screenCapture2 = findPasswordWindowResult._1;
        ScreenLocation passwordWindowLocation = findPasswordWindowResult._2;

        ScreenLocation okButtonLocation = loopCommand.loop(
            () -> findImageInImageCommand.findImageInImage(
                screenCapture2,
                okButton,
                passwordWindowLocation,
                passwordWindow2.size()
            )
        );
        sendMouseClickCommand.sendMouseClick(okButtonLocation.withOffset(okButton.center()));
    }
}
