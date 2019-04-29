import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class MouseState implements MouseMotionListener {
	
	private double deltaAngle;
	private double multiplier;
	private int xOrigin, yOrigin;
	private Robot robot;
	
	public MouseState(Dimension screenSize) {
		deltaAngle = 0.0;
		xOrigin = screenSize.width / 2;
		yOrigin = screenSize.height / 2;
		multiplier = 1.0 / xOrigin;
		try {
			robot = new Robot();
			robot.mouseMove(xOrigin, yOrigin);
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
	
	public void mouseMoved(MouseEvent e) {
		int x = e.getXOnScreen();
		deltaAngle += (x - xOrigin) * multiplier;
		robot.mouseMove(xOrigin, yOrigin);
	}
	
	public void mouseDragged(MouseEvent e) {}
	
	public double getDeltaAngle() {
		double da = deltaAngle;
		deltaAngle = 0;
		return da;
	}
}