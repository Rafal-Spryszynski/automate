package pl.allegro.automate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.allegro.automate.gui.FindImageInImageCommand;
import pl.allegro.automate.gui.Image;
import pl.allegro.automate.gui.LoadImageCommand;
import pl.allegro.automate.gui.TakeScreenCaptureCommand;
import pl.allegro.automate.os.ProcessCommand;

import javax.inject.Inject;
import java.lang.invoke.MethodHandles;
import java.nio.file.Paths;

class Automate {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final ProcessCommand processCommand;
    private final LoadImageCommand loadImageCommand;
    private final TakeScreenCaptureCommand takeScreenCaptureCommand;
    private final FindImageInImageCommand findImageInImageCommand;

    @Inject
    Automate(
        ProcessCommand processCommand,
        LoadImageCommand loadImageCommand,
        TakeScreenCaptureCommand takeScreenCaptureCommand,
        FindImageInImageCommand findImageInImageCommand
    ) {
        this.processCommand = processCommand;
        this.loadImageCommand = loadImageCommand;
        this.takeScreenCaptureCommand = takeScreenCaptureCommand;
        this.findImageInImageCommand = findImageInImageCommand;
    }

    void runAutomation() throws Exception {
        processCommand.startProcess(Paths.get("C:\\Program Files (x86)\\Cisco\\Cisco Secure Client\\UI\\csc_ui.exe"));
        Image vpnWindowImage = loadImageCommand.loadImage(Paths.get("C:\\Users\\rafal.spryszynski\\Desktop\\automate\\cisco client.png"));
        Image screenCaptureImage = takeScreenCaptureCommand.takeScreenCapture();
        findImageInImageCommand.findImageInImage(screenCaptureImage, vpnWindowImage);
    }
}
