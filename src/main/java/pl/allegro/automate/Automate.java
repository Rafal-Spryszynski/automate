package pl.allegro.automate;

import pl.allegro.automate.system.process.StartProcessAutomationStep;

import javax.inject.Inject;
import java.nio.file.Paths;

public class Automate {

    private final AutomationStepsRegistry registry;

    @Inject
    Automate(AutomationStepsRegistry registry) {
        this.registry = registry;
    }

    public void runAutomation(AutomationFlow automationFlow) {
        automationFlow.flow()
            .forEach(step -> {
                switch (step.step()) {
                    case START_PROCESS: {
                        StartProcessAutomationStep startProcessAutomationStep = registry.get(StartProcessAutomationStep.class);
                        step.args().forEach(arg -> {
                            switch (arg.type()) {
                                case CONST:
                                    startProcessAutomationStep.startProcess(Paths.get(arg.value()));
                            }
                        });
                    }
                }
            });
    }
}
