package pl.allegro.automate.adapter.awt.gui;

import io.vavr.collection.CharSeq;
import io.vavr.control.Try;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.allegro.automate.Exchange;
import pl.allegro.automate.gui.TypeCharsAutomationStep;
import pl.allegro.automate.system.console.Password;

import javax.inject.Inject;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.lang.invoke.MethodHandles;

class TypeKeyboardCharsAutomationStep implements TypeCharsAutomationStep {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final Robot robot;

    @Inject
    TypeKeyboardCharsAutomationStep(Robot robot) {
        this.robot = robot;
    }

    @Override
    public void execute(Exchange exchange) {
        Object param = exchange.getSingleParam();

        if (param instanceof Password) {
            typeChars((Password) param);
        } else if (param instanceof String) {
            typeChars((String) param);
        } else {
            throw new RuntimeException("Unsupported parameter " + param);
        }
    }

    @Override
    public void typeChars(Password password) {
        typeChars(password.chars());
    }

    @Override
    public void typeChars(String text) {
        typeChars(text.toCharArray());
    }

    private void typeChars(char[] chars) {
        CharSeq.of(chars)
            .forEach(character -> {
                int keyCode = KeyEvent.getExtendedKeyCodeForChar(character);
                if (keyCode == KeyEvent.VK_UNDEFINED) {
                    LOG.info("Cannot find {} key", character);
                    return;
                }
                if (Character.isUpperCase(character)) {
                    robot.keyPress(KeyEvent.VK_SHIFT);
                }
                Try.run(() -> {
                        robot.keyPress(keyCode);
                        robot.keyRelease(keyCode);
                    })
                    .onFailure(e -> LOG.error("Cannot press key {} found {} key code", character, Integer.toHexString(keyCode), e));
                if (Character.isUpperCase(character)) {
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                }
            });
    }
}
