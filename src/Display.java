import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.*;

public class Display extends JFrame implements ImageObserver {
    
    private Image buf; // uninitalized
    private FrameObserver fo;
    
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
    
    public void show(Renderer rend) {
        buf = rend.getScaledSurface(getSize());
        repaint();
    }
    
    public void paint(Graphics g) {
        g.drawImage(buf, 0, 0, fo);
    }
    
    public boolean isFrameDone() {
        return fo.isFrameDone();
    }
    
    public void resetFrameStatus() {
        fo.resetFrameStatus();
    }
}