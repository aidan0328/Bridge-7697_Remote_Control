package ControllerManager;

import java.awt.AWTException;
import java.awt.Robot;

public class KeyboardRobot {
	Robot robot;
	
	public KeyboardRobot() {
		try {
			robot = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void click(int keyCode) {
		try {

			robot.keyPress(keyCode);
			robot.delay(3);
			robot.keyRelease(keyCode);
		} catch (IllegalArgumentException e) {
			System.out.println("Undefined KeyCode");
		}
		
	}
}
