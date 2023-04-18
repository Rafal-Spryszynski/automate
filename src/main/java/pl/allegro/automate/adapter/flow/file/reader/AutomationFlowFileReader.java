package pl.allegro.automate.adapter.flow.file.reader;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Try;
import pl.allegro.automate.AutomationFlow;

import javax.inject.Inject;
import java.nio.file.Path;

public class AutomationFlowFileReader {

    private final ObjectMapper objectMapper;
    private final Path filesPath;

    @Inject
    AutomationFlowFileReader(ObjectMapper objectMapper, Path filesPath) {
        this.objectMapper = objectMapper;
        this.filesPath = filesPath;
    }

    public AutomationFlow readAutomationFlow() {
        Path automationFlowPath = filesPath.resolve("automation-flow.json");
        return Try.of(() -> objectMapper.readValue(automationFlowPath.toFile(), AutomationFlow.class)).get();
    }
}
