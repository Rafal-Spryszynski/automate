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

        AutomateComponent automateComponent = DaggerAutomateComponent.builder()
            .awtGuiComponent(awtGuiComponent)
            .defaultSleepDuration(Duration.ofMillis(200))
            .build();
        Automate automate = automateComponent.automate();
//        automate.runAutomation();
        StartChromeAutomation startChromeAutomation = automateComponent.startChromeAutomation();
//        startChromeAutomation.startChrome();
        StartFirefoxAutomation startFirefoxAutomation = automateComponent.startFirefoxAutomation();
        startFirefoxAutomation.startFirefox();
    }
}
