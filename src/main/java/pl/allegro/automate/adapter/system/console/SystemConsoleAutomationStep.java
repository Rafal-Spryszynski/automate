package pl.allegro.automate.adapter.system.console;

import pl.allegro.automate.system.console.ConsoleAutomationStep;
import pl.allegro.automate.system.console.ImmutablePassword;
import pl.allegro.automate.system.console.Password;

import javax.inject.Inject;

class SystemConsoleAutomationStep implements ConsoleAutomationStep {

    @Inject
    SystemConsoleAutomationStep() {}

    @Override
    public Password promptPassword(String prompt) {
        char[] password = System.console().readPassword(prompt);
        return ImmutablePassword.of(password);
    }
}
