package pl.allegro.automate;

import io.vavr.control.Option;
import pl.allegro.automate.gui.FindImageInImageCommand;
import pl.allegro.automate.gui.Image;
import pl.allegro.automate.gui.LoadImageCommand;
import pl.allegro.automate.gui.ScreenLocation;
import pl.allegro.automate.gui.SendMouseClickCommand;
import pl.allegro.automate.gui.TakeScreenCaptureCommand;
import pl.allegro.automate.os.StartProcessCommand;

import javax.inject.Inject;
import java.nio.file.Path;
import java.nio.file.Paths;

class Automate {

    private final StartProcessCommand startProcessCommand;
    private final LoadImageCommand loadImageCommand;
    private final TakeScreenCaptureCommand takeScreenCaptureCommand;
    private final FindImageInImageCommand findImageInImageCommand;
    private final SendMouseClickCommand sendMouseClickCommand;
    private final Path imagesPath;

    @Inject
    Automate(
        StartProcessCommand startProcessCommand,
        LoadImageCommand loadImageCommand,
        TakeScreenCaptureCommand takeScreenCaptureCommand,
        FindImageInImageCommand findImageInImageCommand,
        SendMouseClickCommand sendMouseClickCommand,
        Path imagesPath
    ) {
        this.startProcessCommand = startProcessCommand;
        this.loadImageCommand = loadImageCommand;
        this.takeScreenCaptureCommand = takeScreenCaptureCommand;
        this.findImageInImageCommand = findImageInImageCommand;
        this.sendMouseClickCommand = sendMouseClickCommand;
        this.imagesPath = imagesPath;
    }

    void runAutomation() throws InterruptedException {
        startProcessCommand.startProcess(Paths.get("C:\\Program Files (x86)\\Cisco\\Cisco Secure Client\\UI\\csc_ui.exe"));
        Image vpnWindow = loadImageCommand.loadImage(imagesPath.resolve("cisco client.png"));
        Image screenCapture1 = takeScreenCaptureCommand.takeScreenCapture();
        Option<ScreenLocation> findVpnWindowResult = findImageInImageCommand.findImageInImage(screenCapture1, vpnWindow);

        if (findVpnWindowResult.isDefined()) {
            ScreenLocation vpnWindowLocation = findVpnWindowResult.get();
            Image connectButton = loadImageCommand.loadImage(imagesPath.resolve("cisco connect button.png"));
            Option<ScreenLocation> findConnectButtonResult = findImageInImageCommand.findImageInImage(
                screenCapture1,
                connectButton,
                vpnWindowLocation.y(),
                vpnWindowLocation.x(),
                vpnWindow.height(),
                vpnWindow.width()
            );
            if (findConnectButtonResult.isDefined()) {
                ScreenLocation connectButtonLocation = findConnectButtonResult.get();
                sendMouseClickCommand.sendMouseClick(
                    connectButtonLocation.y() + connectButton.verticalCenter(),
                    connectButtonLocation.x() + connectButton.horizontalCenter()
                );
                Image passwordWindow = loadImageCommand.loadImage(imagesPath.resolve("cisco password window 2.png"));
                Image okButton = loadImageCommand.loadImage(imagesPath.resolve("cisco ok button.png"));
                Thread.sleep(1000);
                Image screenCapture2 = takeScreenCaptureCommand.takeScreenCapture();
                Option<ScreenLocation> findPasswordWindowResult = findImageInImageCommand.findImageInImage(screenCapture2, passwordWindow);

                if (findPasswordWindowResult.isDefined()) {
                    ScreenLocation passwordWindowLocation = findPasswordWindowResult.get();
                    Option<ScreenLocation> findOkButtonResult = findImageInImageCommand.findImageInImage(
                        screenCapture2,
                        okButton,
                        passwordWindowLocation.y(),
                        passwordWindowLocation.x(),
                        passwordWindow.height(),
                        passwordWindow.width()
                    );
                    if (findOkButtonResult.isDefined()) {
                        ScreenLocation okButtonLocation = findOkButtonResult.get();
                        sendMouseClickCommand.sendMouseClick(
                            okButtonLocation.y() + okButton.verticalCenter(),
                            okButtonLocation.x() + okButton.horizontalCenter()
                        );
                    }
                }
            }
        }
    }
}
