package pl.allegro.automate;

import javax.inject.Inject;

public class Automate {

    private final AutomationStepsRegistry registry;

    @Inject
    Automate(AutomationStepsRegistry registry) {
        this.registry = registry;
    }

    public void runAutomation(AutomationFlow automationFlow) {
        Exchange exchange = new Exchange();
        automationFlow.steps()
            .forEach(step -> {
                AutomationStep automationStep = registry.get(step.code());

                step.args().forEach(arg -> {
                    switch (arg.type()) {
                        case CONST:
                            exchange.addInput(arg.value());
                    }
                });
                automationStep.execute(exchange);
            });
    }
}
