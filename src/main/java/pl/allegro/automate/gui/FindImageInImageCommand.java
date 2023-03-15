package pl.allegro.automate.gui;

import io.vavr.control.Option;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.lang.invoke.MethodHandles;

public class FindImageInImageCommand {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Inject
    FindImageInImageCommand() {}

    public Option<FindImageResult> findImageInImage(Image image, Image imageToFind, int yStart, int xStart, int height, int width) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        for (int y = yStart + imageToFind.bottom(); y < yStart + height; y++) {
            for (int x = xStart + imageToFind.right(); x < xStart + width; x++) {
                int yCheck = y;
                imageLoop:
                for (int imageY = imageToFind.bottom(); 0 <= imageY; --imageY, --yCheck) {
                    int xCheck = x;
                    for (int imageX = imageToFind.right(); 0 <= imageX; --imageX, --xCheck) {
                        if (image.getPixel(yCheck, xCheck) != imageToFind.getPixel(imageY, imageX)) {
                            break imageLoop;
                        }
                        if (imageY == 0 && imageX == 0) {
                            stopWatch.stop();
                            LOG.info("hit {},{} {}", yCheck, xCheck, stopWatch);
                            return Option.of(ImmutableFindImageResult.of(yCheck, xCheck));
                        }
                    }
                }
            }
        }
        stopWatch.stop();
        LOG.info("no hit {}", stopWatch);
        return Option.none();
    }
}