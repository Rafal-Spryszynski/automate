package pl.allegro.automate.adapter.awt.gui;

import pl.allegro.automate.gui.ScreenLocation;
import pl.allegro.automate.gui.SendMouseClickCommand;

import javax.inject.Inject;
import java.awt.Robot;
import java.awt.event.InputEvent;

class SendDeviceMouseClickAutomationStep implements SendMouseClickCommand {

    private final Robot robot;

    @Inject
    SendDeviceMouseClickAutomationStep(Robot robot) {
        this.robot = robot;
    }

    @Override
    public void sendMouseClick(ScreenLocation screenLocation) {
        robot.mouseMove(screenLocation.x(), screenLocation.y());
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }
}
