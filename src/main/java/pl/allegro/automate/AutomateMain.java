package pl.allegro.automate;

class AutomateMain {

    public static void main(String[] args) throws Exception {
        Automate automate = DaggerAutomateComponent.builder()
            .saveScreenCaptures(true)
            .build()
            .automate();
        automate.runAutomation();
    }
}
