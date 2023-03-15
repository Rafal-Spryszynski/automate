package pl.allegro.automate;

class AutomateMain {

    public static void main(String[] args) throws Exception {
        Automate automate = DaggerAutomateComponent.create()
            .automate();
        automate.runAutomation();
    }
}
