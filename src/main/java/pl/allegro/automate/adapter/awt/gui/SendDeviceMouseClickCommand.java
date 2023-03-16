package pl.allegro.automate.adapter.awt.gui;

import io.vavr.control.Try;
import pl.allegro.automate.gui.SendMouseClickCommand;

import javax.inject.Inject;
import java.awt.Robot;
import java.awt.event.InputEvent;

class SendDeviceMouseClickCommand implements SendMouseClickCommand {

    @Inject
    SendDeviceMouseClickCommand() {}

    @Override
    public void sendMouseClick(int y, int x) {
        Robot robot = Try.of(Robot::new).get();
        robot.setAutoDelay(200);
        robot.mouseMove(x, y);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }
}
