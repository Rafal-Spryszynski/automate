package pl.allegro.automate.adapter.flow.file.reader;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Try;
import pl.allegro.automate.AutomationFlow;

import javax.inject.Inject;
import java.nio.file.Path;

import static io.vavr.API.printf;

public class AutomationFlowFileReader {

    private final ObjectMapper objectMapper;
    private final Path filesPath;
    private final String automationFileName;

    @Inject
    AutomationFlowFileReader(ObjectMapper objectMapper, Path filesPath, String automationFileName) {
        this.objectMapper = objectMapper;
        this.filesPath = filesPath;
        this.automationFileName = automationFileName;
    }

    public AutomationFlow readAutomationFlow() {
        Path automationFlowPath = filesPath.resolve(automationFileName);
        printf("Reading for execution %s ...\n", automationFlowPath);
        return Try.of(() -> objectMapper.readValue(automationFlowPath.toFile(), AutomationFlow.class)).get();
    }
}
