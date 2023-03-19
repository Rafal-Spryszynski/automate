package pl.allegro.automate.adapter;

import pl.allegro.automate.adapter.awt.gui.AwtGuiComponent;
import pl.allegro.automate.adapter.awt.gui.DaggerAwtGuiComponent;

import java.nio.file.Paths;
import java.time.Duration;

class AutomateMain {

    public static void main(String[] args) {
        AwtGuiComponent awtGuiComponent = DaggerAwtGuiComponent.builder()
            .imagesPath(Paths.get("C:\\Users\\rafal.spryszynski\\Desktop\\automate"))
            .saveScreenCaptures(true)
            .autoDelay(Duration.ofMillis(100))
            .build();

        Automate automate = DaggerAutomateComponent.builder()
            .awtGuiComponent(awtGuiComponent)
            .defaultSleepDuration(Duration.ofMillis(200))
            .build()
            .automate();
        automate.runAutomation();
    }
}
