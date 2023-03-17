package pl.allegro.automate.adapter.awt.gui;

import io.vavr.control.Try;
import pl.allegro.automate.gui.ScreenLocation;
import pl.allegro.automate.gui.SendMouseClickCommand;

import javax.inject.Inject;
import java.awt.Robot;
import java.awt.event.InputEvent;

class SendDeviceMouseClickCommand implements SendMouseClickCommand {

    @Inject
    SendDeviceMouseClickCommand() {}

    @Override
    public void run(Object... arguments) {
        ScreenLocation screenLocation = (ScreenLocation) arguments[0];
        Robot robot = Try.of(Robot::new).get();
        robot.setAutoDelay(200);
        robot.mouseMove(screenLocation.x(), screenLocation.y());
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }
}
