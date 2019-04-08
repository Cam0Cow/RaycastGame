import java.awt.event.*;

public class Keyboard implements KeyListener {
	
	public void keyPressed(KeyEvent e) {
		KeyState.getKeyState().keyPressed(e);
	}
	
	public void keyReleased(KeyEvent e) {
		KeyState.getKeyState().keyReleased(e);
	}
	
	public void keyTyped(KeyEvent e) {}
}