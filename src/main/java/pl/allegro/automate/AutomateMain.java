package pl.allegro.automate;

import java.nio.file.Paths;

class AutomateMain {

    public static void main(String[] args) throws Exception {
        Automate automate = DaggerAutomateComponent.builder()
            .imagesPath(Paths.get("C:\\Users\\rafal.spryszynski\\Desktop\\automate"))
            .saveScreenCaptures(true)
            .build()
            .automate();
        automate.runAutomation();
    }
}
