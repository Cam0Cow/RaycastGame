import java.awt.image.*;
import java.awt.*;

public class FrameObserver implements ImageObserver {
    
    private boolean frameDone;
    
    public FrameObserver() {
        frameDone = false;
    }
    
    public boolean imageUpdate(Image image, int flags, int x,
        int y, int width, int height) {
            //System.out.println(flags);
        if ((flags & ALLBITS) != 0) {
            //System.out.println("yes!");
            frameDone = true;
            return false;
        }
        return true;
    }
    
    public boolean isFrameDone() {
        try {Thread.sleep(1);}catch(Exception e){}
        return frameDone;
    }
    
    public void resetFrameStatus() {
        frameDone = false;
    }
}