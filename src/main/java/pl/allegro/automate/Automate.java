package pl.allegro.automate;

import io.vavr.Tuple;
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

    private final StartProcessCommand startProcessCommand;
    private final LoadImageCommand loadImageCommand;
    private final TakeScreenCaptureCommand takeScreenCaptureCommand;
    private final FindImageInImageCommand findImageInImageCommand;
    private final SendMouseClickCommand sendMouseClickCommand;
    private final LoopCommand loopCommand;
    private final SleepCommand sleepCommand;

    @Inject
    Automate(
        StartProcessCommand startProcessCommand,
        LoadImageCommand loadImageCommand,
        TakeScreenCaptureCommand takeScreenCaptureCommand,
        FindImageInImageCommand findImageInImageCommand,
        SendMouseClickCommand sendMouseClickCommand,
        LoopCommand loopCommand,
        SleepCommand sleepCommand
    ) {
        this.startProcessCommand = startProcessCommand;
        this.loadImageCommand = loadImageCommand;
        this.takeScreenCaptureCommand = takeScreenCaptureCommand;
        this.findImageInImageCommand = findImageInImageCommand;
        this.sendMouseClickCommand = sendMouseClickCommand;
        this.loopCommand = loopCommand;
        this.sleepCommand = sleepCommand;
    }

    void runAutomation() {
        startProcessCommand.startProcess(Paths.get("C:\\Program Files (x86)\\Cisco\\Cisco Secure Client\\UI\\csc_ui.exe"));
        Image vpnWindow = loadImageCommand.loadImage("cisco client.png");

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
