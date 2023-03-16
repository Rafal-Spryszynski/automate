package pl.allegro.automate;

import java.nio.file.Paths;
import java.time.Duration;

class AutomateMain {

    public static void main(String[] args) {
        Automate automate = DaggerAutomateComponent.builder()
            .imagesPath(Paths.get("C:\\Users\\rafal.spryszynski\\Desktop\\automate"))
            .saveScreenCaptures(false)
            .defaultSleepDuration(Duration.ofMillis(200))
            .build()
            .automate();
        automate.runAutomation();
    }
}
