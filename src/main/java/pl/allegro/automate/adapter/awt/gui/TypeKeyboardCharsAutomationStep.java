package pl.allegro.automate.adapter.awt.gui;

import io.vavr.collection.CharSeq;
import pl.allegro.automate.gui.TypeCharsAutomationStep;
import pl.allegro.automate.system.console.Password;

import javax.inject.Inject;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

class TypeKeyboardCharsAutomationStep implements TypeCharsAutomationStep {

    private final Robot robot;

    @Inject
    TypeKeyboardCharsAutomationStep(Robot robot) {
        this.robot = robot;
    }

    @Override
    public void typeChars(Password password) {
        CharSeq.of(password.chars())
            .forEach(character -> {
                int keyCode = KeyEvent.getExtendedKeyCodeForChar(character);
                if (Character.isUpperCase(character)) {
                    robot.keyPress(InputEvent.SHIFT_DOWN_MASK);
                }
                robot.keyPress(keyCode);
                robot.keyRelease(keyCode);
                if (Character.isUpperCase(character)) {
                    robot.keyRelease(InputEvent.SHIFT_DOWN_MASK);
                }
            });
    }
}
