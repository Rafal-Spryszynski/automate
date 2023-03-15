package pl.allegro.automate.gui;

import javax.inject.Inject;
import java.awt.Robot;
import java.awt.event.InputEvent;

class SendDeviceMouseClickCommand implements SendMouseClickCommand {

    @Inject
    SendDeviceMouseClickCommand() {}

    @Override
    public void sendMouseClick(int y, int x) throws Exception {
        Robot robot = new Robot();
        robot.setAutoDelay(200);
        robot.mouseMove(x, y);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }
}
