package pl.allegro.automate;

class AutomateMain {

    public static void main(String[] args) throws Exception {
        Automate automate = DaggerAutomateComponent.builder()
            .saveScreenCaptures(false)
            .build()
            .automate();
        automate.runAutomation();
    }
}
