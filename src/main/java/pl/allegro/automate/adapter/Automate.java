package pl.allegro.automate.adapter;

import javax.inject.Inject;

class Automate {

    private final TurnOnVpnAutomation turnOnVpnAutomation;
    private final StartChromeAutomation startChromeAutomation;
    private final StartFirefoxAutomation startFirefoxAutomation;

    @Inject
    Automate(
        TurnOnVpnAutomation turnOnVpnAutomation,
        StartChromeAutomation startChromeAutomation,
        StartFirefoxAutomation startFirefoxAutomation
    ) {
        this.turnOnVpnAutomation = turnOnVpnAutomation;
        this.startChromeAutomation = startChromeAutomation;
        this.startFirefoxAutomation = startFirefoxAutomation;
    }

    void runAutomation() {
        turnOnVpnAutomation.turnOnVpn();
        startChromeAutomation.startChrome();
        startFirefoxAutomation.startFirefox();
    }
}
