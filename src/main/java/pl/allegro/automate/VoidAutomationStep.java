package pl.allegro.automate;

public interface VoidAutomationStep extends AutomationStep {

    @Override
    default Void execute(Object... arguments) {
        run(arguments);
        return null;
    }

    void run(Object... arguments);
}
