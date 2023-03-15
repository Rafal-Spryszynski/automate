package pl.allegro.automate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

class AutomateMain {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public static void main(String[] args) throws Exception {
        Automate automate = DaggerAutomateComponent.create()
            .automate();
        automate.runAutomation();
    }
}
