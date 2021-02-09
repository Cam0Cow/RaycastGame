/*
 * Roie Gal
 * 2019-5-13
 * Raycast Game
 */

import java.awt.image.*;
import java.awt.*;

/**
 * Represents an image observer for async rendering
 */
public class FrameObserver implements ImageObserver {
    
    private boolean frameDone;
    
    /**
     * Constructs a new frame observer
     */
    public FrameObserver() {
        frameDone = true;
    }
    
    /**
     * Called as the image is updated
     * @param image the image object
     * @param flags store image metadata
     * @param x the x coordinate of the image
     * @param y the y coordinate of the image
     * @param width the width of the image
     * @param height the height of the image
     * @return whether the image is done drawing
     */
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
    
    /**
     * Waits 1 ms, then checks whether the frame is done
     * @return whether the frame is done
     */
    public boolean isFrameDone() {
        try {Thread.sleep(1);}catch(Exception e){}
        return frameDone;
    }
    
    /**
     * Resets frame done flag
     * Should be called after the frame is done
     */
    public void resetFrameStatus() {
        frameDone = false;
    }
}
