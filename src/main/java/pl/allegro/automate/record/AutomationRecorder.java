package pl.allegro.automate.record;

import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;
import java.awt.MouseInfo;
import java.awt.Point;

import static io.vavr.API.printf;
import static io.vavr.API.println;
import static org.apache.commons.lang3.StringUtils.equalsAny;
import static org.apache.commons.lang3.StringUtils.startsWith;
import static org.apache.commons.lang3.StringUtils.substringAfter;

public class AutomationRecorder {

    private static final String WRITE_QUIT = "wq";
    private static final String QUIT = "q";
    private static final String MOUSE_MOVE = "m";
    private static final String CLICK = "c";
    private static final String TYPE_CHARS = "t";

    @Inject
    AutomationRecorder() {}

    public void recordAutomation() {
        String command;
        do {
            println("Select step:");
            printf(" [%s] - move mouse to current position\n", MOUSE_MOVE);
            printf(" [%s] - click current position\n", CLICK);
            printf(" [%s] [chars] - type chars\n", TYPE_CHARS);
            printf(" [%s] - save & quit\n", WRITE_QUIT);
            printf(" [%s] - quit without saving\n", QUIT);

            command = System.console().readLine();

            if (StringUtils.equals(command, WRITE_QUIT)) {
                println("Saving and quitting...");
                // Save the recorded steps to a file or perform any other necessary actions

            } else if (StringUtils.equals(command, QUIT)) {
                println("Quit without saving? [yes]");
                String confirmation = System.console().readLine();

                if (StringUtils.equals(confirmation, "yes")) {
                    println("Quitting without saving...");
                } else {
                    command = null;
                }
            } else if (startsWith(command, MOUSE_MOVE)) {
                Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
                printf("Recording moving mouse to: %s\n", mouseLocation);

            } else if (startsWith(command, CLICK)) {
                Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
                printf("Recording click at: %s\n", mouseLocation);
                // Implement click logic here

            } else if (startsWith(command, TYPE_CHARS)) {
                String chars = substringAfter(command, TYPE_CHARS + " ");
                printf("Recording typing chars: %s\n", chars);
                // Implement key press logic here
            } else {
                println("Unknown command: " + command);
            }
            println();
        } while (!equalsAny(command, WRITE_QUIT, QUIT));
    }
}
