package pl.allegro.automate.record;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Try;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.Failable;
import pl.allegro.automate.AutomationFlow;
import pl.allegro.automate.AutomationFlow.Step.Code;
import pl.allegro.automate.ImmutableArg;
import pl.allegro.automate.ImmutableAutomationFlow;
import pl.allegro.automate.ImmutableStep;
import pl.allegro.automate.gui.ImmutableScreenLocation;

import javax.inject.Inject;
import java.awt.MouseInfo;
import java.awt.Point;
import java.nio.file.Path;
import java.time.Duration;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static io.vavr.API.printf;
import static io.vavr.API.println;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.equalsAny;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.startsWith;
import static org.apache.commons.lang3.StringUtils.strip;
import static org.apache.commons.lang3.StringUtils.stripToNull;
import static org.apache.commons.lang3.StringUtils.substringAfter;

public class AutomationRecorder {

    private static final String WRITE_QUIT = "wq";
    private static final String QUIT = "q";
    private static final String MOUSE_CLICK = "m";
    private static final String TYPE_CHARS = "t";
    private static final String LABEL = "l";
    private static final String SLEEP = "s";

    private final Path filesPath;
    private final ObjectMapper objectMapper;
    private final List<AutomationFlow.Step> stepsList = new LinkedList<>();

    @Inject
    AutomationRecorder(Path filesPath, ObjectMapper objectMapper) {
        this.filesPath = filesPath;
        this.objectMapper = objectMapper;
    }

    public void recordAutomation() {
        String command;
        do {
            println("Select step:");
            printf(" [%s] [label?] - move mouse to current position and click\n", MOUSE_CLICK);
            printf(" [%s] [chars] - type chars\n", TYPE_CHARS);
            printf(" [%s] [duration] - sleep (PT3s)\n", SLEEP);
            printf(" [%s] [label?] - set label on previous step\n", LABEL);
            printf(" [%s] - save & quit\n", WRITE_QUIT);
            printf(" [%s] - quit without saving\n", QUIT);

            command = System.console().readLine();

            if (StringUtils.equals(command, WRITE_QUIT)) {
                writeToFile();

            } else if (StringUtils.equals(command, QUIT)) {
                command = quitWithoutSaving(command);

            } else if (startsWith(command, MOUSE_CLICK)) {
                recordMovingMouseAndClicking(command);

            } else if (startsWith(command, TYPE_CHARS)) {
                recordTypingChars(command);

            } else if (startsWith(command, SLEEP)) {
                recordSleeping(command);

            } else if (startsWith(command, LABEL)) {
                setLabelOnPreviousStep(command);

            } else {
                println("Unknown command: " + command);
            }
            println();
        } while (!equalsAny(command, WRITE_QUIT, QUIT));
    }

    private void writeToFile() {
        if (stepsList.isEmpty()) {
            println("Steps list is empty. Doing nothing.");
            return;
        }
        Path newFlowPath = filesPath.resolve("new-automation-flow.json");
        printf("Saving to %s and quitting...\n", newFlowPath);
        AutomationFlow automationFlow =
            ImmutableAutomationFlow.builder()
                .steps(stepsList)
                .build();
        Failable.run(() -> objectMapper.writeValue(newFlowPath.toFile(), automationFlow));
    }

    private static String quitWithoutSaving(String command) {
        println("Quit without saving? [yes]");
        String confirmation = System.console().readLine();

        if (StringUtils.equals(confirmation, "yes")) {
            println("Quitting without saving...");
        } else {
            command = null;
        }
        return command;
    }

    private void recordMovingMouseAndClicking(String command) {
        Optional<String> label = getLabel(command, MOUSE_CLICK);
        String labelLog = label.map(" with label: "::concat).orElse(EMPTY);

        Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
        printf("Recording moving mouse and clicking at: %s%s\n", mouseLocation, labelLog);

        AutomationFlow.Step step =
            ImmutableStep.builder()
                .code(Code.MOUSE_CLICK)
                .addArgs(
                    ImmutableArg.of(ImmutableScreenLocation.of(mouseLocation.y, mouseLocation.x))
                )
                .label(label)
                .build();
        stepsList.add(step);
    }

    private void recordTypingChars(String command) {
        String chars = substringAfter(command, TYPE_CHARS + " ");
        printf("Recording typing chars: %s\n", chars);

        AutomationFlow.Step step =
            ImmutableStep.builder()
                .code(Code.TYPE_CHARS)
                .addArgs(ImmutableArg.of(chars))
                .build();
        stepsList.add(step);
    }

    private void recordSleeping(String command) {
        String durationString = strip(substringAfter(command, SLEEP + " "));

        if (isBlank(durationString)) {
            println("Ignoring blank duration.");
            return;
        }
        Try<Duration> durationTry = Try.of(() -> Duration.parse(durationString));

        if (durationTry.isFailure()) {
            durationTry.onFailure(e -> printf("%s - [%s]\n", e, durationString));
            return;
        }
        Duration duration = durationTry.get();
        printf("Recording sleeping: %s\n", duration);
        AutomationFlow.Step step =
            ImmutableStep.builder()
                .code(Code.SLEEP)
                .addArgs(ImmutableArg.of(duration))
                .build();
        stepsList.add(step);
    }

    private void setLabelOnPreviousStep(String command) {
        Optional<String> label = getLabel(command, LABEL);

        if (label.isEmpty()) {
            println("Ignoring blank label");
            return;
        }
        if (stepsList.isEmpty()) {
            println("Steps list is empty. Cannot set label.");
            return;
        }
        AutomationFlow.Step step = stepsList.remove(stepsList.size() - 1);
        printf("Setting label [%s] on the last step %s\n", label.get(), step);
        AutomationFlow.Step stepWithLabel =
            ImmutableStep.builder()
                .from(step)
                .label(label)
                .build();
        stepsList.add(stepWithLabel);
    }

    private static Optional<String> getLabel(String command, String commandCode) {
        return Optional.ofNullable(
            stripToNull(substringAfter(command, commandCode + " "))
        );
    }
}
