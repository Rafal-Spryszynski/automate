package pl.allegro.automate.gui;

import io.vavr.collection.Iterator;
import io.vavr.collection.List;
import io.vavr.control.Option;
import pl.allegro.automate.AutomationStep;
import pl.allegro.automate.flow.LoopCommand;

import javax.inject.Inject;

public class FindImageOnScreenAutomationStep implements AutomationStep {

    private final TakeScreenCaptureCommand takeScreenCaptureCommand;
    private final FindImageInImageCommand findImageInImageCommand;
    private final LoopCommand loopCommand;

    @Inject
    FindImageOnScreenAutomationStep(
        TakeScreenCaptureCommand takeScreenCaptureCommand,
        FindImageInImageCommand findImageInImageCommand,
        LoopCommand loopCommand
    ) {
        this.takeScreenCaptureCommand = takeScreenCaptureCommand;
        this.findImageInImageCommand = findImageInImageCommand;
        this.loopCommand = loopCommand;
    }

    public ImageOnScreen findImageOnScreen(Image... images) {
        List<Image> imagesList = List.of(images);
        return loopCommand.loop(() -> findImageOnScreen(imagesList));
    }

    private Option<ImageOnScreen> findImageOnScreen(List<Image> imagesList) {
        Image screenCapture = takeScreenCaptureCommand.takeScreenCapture();
        return imagesList.iterator()
            .map(image ->
                findImageInImageCommand.findImageInImage(screenCapture, image)
                    .map(screenLocation -> ImmutableImageOnScreen.builder()
                        .screenCapture(screenCapture)
                        .screenLocation(screenLocation)
                        .image(image)
                        .build()
                    )
            )
            .find(Option::isDefined)
            .flatMap(imageOnScreen -> imageOnScreen);
    }

    public ImageOnScreen findImageOnScreen(ImageOnScreen foundImage, Image... images) {
        Image screenCapture = foundImage.screenCapture();
        ScreenLocation startLocation = foundImage.screenLocation();
        RectangleSize size = foundImage.image().size();
        return Iterator.of(images)
            .map(image ->
                findImageInImageCommand.findImageInImage(screenCapture, image, startLocation, size)
                    .map(screenLocation -> ImmutableImageOnScreen.builder()
                        .screenCapture(screenCapture)
                        .screenLocation(screenLocation)
                        .image(image)
                        .build()
                    )
            )
            .find(Option::isDefined)
            .flatMap(imageOnScreen -> imageOnScreen)
            .get();
    }
}
