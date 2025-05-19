package pl.allegro.automate;

import pl.allegro.automate.flow.SleepAutomationStep;

import javax.inject.Inject;
import java.time.Duration;
import java.util.Optional;

public class Automate {

    private final AutomationStepsRegistry registry;
    private final SleepAutomationStep sleepAutomationStep;
    private final Duration defaultSleepDuration;

    @Inject
    Automate(AutomationStepsRegistry registry, Duration defaultSleepDuration) {
        this.registry = registry;
        this.defaultSleepDuration = defaultSleepDuration;
        sleepAutomationStep = registry.get(SleepAutomationStep.class);
    }

    public void runAutomation(AutomationFlow automationFlow) {
        Exchange exchange = new Exchange();
        automationFlow.steps()
            .forEach(step -> {
                AutomationStep automationStep = registry.get(step.code());
                addInputs(step, exchange);
                exchange.setLabel(step.label());
                automationStep.execute(exchange);
                exchange.setLabel(Optional.empty());
                sleepAutomationStep.sleep(defaultSleepDuration);
            });
    }

    private void addInputs(AutomationFlow.Step step, Exchange exchange) {
        step.args().forEach(arg -> {
            switch (arg.type()) {
                case CONST:
                    exchange.addInput(arg.value());
            }
        });
    }
}
