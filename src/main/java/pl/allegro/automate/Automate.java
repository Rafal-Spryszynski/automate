package pl.allegro.automate;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Try;
import pl.allegro.automate.system.process.StartProcessAutomationStep;

import javax.inject.Inject;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Automate {

    private final Path automationFlowsPath;
    private final AutomationStepsRegistry registry;

    @Inject
    Automate(Path automationFlowsPath, AutomationStepsRegistry registry) {
        this.automationFlowsPath = automationFlowsPath;
        this.registry = registry;
    }

    public void runAutomation() {
        ObjectMapper objectMapper = new ObjectMapper();
        AutomationFlow automationFlow = Try.of(() -> objectMapper.readValue(automationFlowsPath.resolve("automation-flow.json").toFile(), AutomationFlow.class)).get();
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
