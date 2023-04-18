package pl.allegro.automate.gui;

import io.vavr.collection.Iterator;
import io.vavr.collection.List;
import io.vavr.control.Option;
import pl.allegro.automate.AutomationStep;
import pl.allegro.automate.Exchange;
import pl.allegro.automate.flow.LoopAutomationStep;

import javax.inject.Inject;

public class FindImageOnScreenAutomationStep implements AutomationStep {

    private final TakeScreenCaptureAutomationStep takeScreenCaptureAutomationStep;
    private final FindImageInImageAutomationStep findImageInImageAutomationStep;
    private final LoopAutomationStep loopAutomationStep;

    @Inject
    FindImageOnScreenAutomationStep(
        TakeScreenCaptureAutomationStep takeScreenCaptureAutomationStep,
        FindImageInImageAutomationStep findImageInImageAutomationStep,
        LoopAutomationStep loopAutomationStep
    ) {
        this.takeScreenCaptureAutomationStep = takeScreenCaptureAutomationStep;
        this.findImageInImageAutomationStep = findImageInImageAutomationStep;
        this.loopAutomationStep = loopAutomationStep;
    }

    @Override
    public void execute(Exchange exchange) {
        List<Image> images = exchange.getAllParams(Image.class);
        loopAutomationStep.loop(() -> findImageOnScreen(images));
    }

    public ImageOnScreen findImageOnScreen(Image... images) {
        List<Image> imagesList = List.of(images);
        return loopAutomationStep.loop(() -> findImageOnScreen(imagesList));
    }

    private Option<ImageOnScreen> findImageOnScreen(List<Image> imagesList) {
        Image screenCapture = takeScreenCaptureAutomationStep.takeScreenCapture();
        return imagesList.iterator()
            .map(image ->
                findImageInImageAutomationStep.findImageInImage(screenCapture, image)
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
                findImageInImageAutomationStep.findImageInImage(screenCapture, image, startLocation, size)
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
