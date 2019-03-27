import javax.swing.*;
import java.awt.*;

public class Test extends JFrame {
    
    private Image img;
    private Dimension dim;
    
    public Test(Image img, Dimension d) {
        super("Test");
        this.img = img;
        dim = d;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        Insets in = getInsets();
        setSize(in.left+in.right+dim.width, in.bottom+dim.height);
    }
    
    public void paint(Graphics g) {
        g.drawImage(img,0,0,null);
    }
}