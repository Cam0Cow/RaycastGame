/*
 * Roie Gal
 * 2019-5-13
 * Raycast Game
 */

import javax.sound.sampled.*;
import java.io.*;

/**
 * Represents a class that can play audio clips
 */
public class AudioLibrary {
    
    private static AudioLibrary al;
    private boolean disabled;
    
    /**
     * Constructs a new audio library   
     */
    private AudioLibrary() {
        disabled = false;
    }
    
    /**
     * Returns a reference to an audio library
     * @return a reference to an audio library
     */
    public static AudioLibrary getAudioLibrary() {
        if (al == null) al = new AudioLibrary();
        return al;
    }
    
    /**
     * Plays the specified sound
     * @param sound the specified sound
     */
    public void play(String sound) {
        if (!disabled) {
            String filename = sound+".wav";
            try {
                BufferedInputStream bis = new BufferedInputStream(getClass().getClassLoader().getResourceAsStream(filename));
                AudioInputStream ais = AudioSystem.getAudioInputStream(bis);
                Clip clip = AudioSystem.getClip();
                clip.open(ais);
                clip.start();
            } catch (Exception e) {
                // No sound will be played
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Set whether sounds should be played at all
     * @param d whether sounds to be played at all
     */
    public void setDisabled(boolean d) {
        disabled = d;
    }
}