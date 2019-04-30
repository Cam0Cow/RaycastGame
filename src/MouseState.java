import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * Represents the current MouseState
 */
public class MouseState implements MouseMotionListener, MouseListener {
	
	private double deltaAngle;
	private double multiplier;
	private boolean hasFired;
	private int xOrigin, yOrigin;
	private Robot robot;
	
	/**
	 * Constructs a new mouse state given the screen size
	 * @param screenSize the screen size
	 */
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
	
	/**
	 * triggered by the OS when the mouse is moved
	 * @param e the mouse event
	 */
	public void mouseMoved(MouseEvent e) {
		int x = e.getXOnScreen();
		deltaAngle += (x - xOrigin) * multiplier;
		robot.mouseMove(xOrigin, yOrigin);
	}
	
	/**
	 * Triggered by the OS when the mouse is dragged
	 * @param e the mouse event
	 */
	public void mouseDragged(MouseEvent e) {}
	
	public void mouseExited(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseClicked(MouseEvent e) {}
	
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			hasFired = true;
		}
	}
	
	/**
	 * Returns how much the player should turn
	 * @return how much the player should turn
	 */
	public double getDeltaAngle() {
		double da = deltaAngle;
		deltaAngle = 0;
		return da;
	}
	
	/**
	 * Determines whether the player has shot
	 */
	public boolean hasFired() {
		boolean b = hasFired;
		hasFired = false;
		return b;
	}
}