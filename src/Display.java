import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.*;

/**
 * Represents a display on the screen
 */
public class Display extends JFrame implements ImageObserver {
    
    private Image buf; // uninitalized
    private FrameObserver fo;
    
    /**
     * Constructs a new Display
     */
    public Display() {
        setExtendedState(MAXIMIZED_BOTH);
        setUndecorated(true);
        GraphicsEnvironment.getLocalGraphicsEnvironment()
            .getScreenDevices()[0].setFullScreenWindow(this);
        setCursor(getToolkit().createCustomCursor(
            new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB)
                ,new Point(), null));
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setVisible(true);
        addKeyListener(KeyState.getKeyState());
        fo = new FrameObserver();
    }
    
    /**
     * Shows a renderer on the screen
     * @param rend the renderer to show
     */
    public void show(Renderer rend) {
        buf = rend.getScaledSurface(getSize());
        repaint();
    }
    
    /**
     * Paints the buffer on the screen
     * @param g the graphics object
     */
    public void paint(Graphics g) {
        g.drawImage(buf, 0, 0, fo);
    }
    
    /**
     * Checks whether the frame is done rendering
     * @return whether the frame is done rendering
     */
    public boolean isFrameDone() {
        return fo.isFrameDone();
    }
    
    /**
     * Resets frame status
     */
    public void resetFrameStatus() {
        fo.resetFrameStatus();
    }
    
    /**
     * Sets the mouseState for this display
     * @param ms the mouse state
     */
    public void setMouseState(MouseMotionListener ms) {
        this.addMouseMotionListener(ms);
    }
}