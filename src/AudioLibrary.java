import javax.sound.sampled.*;
import java.io.*;

public class AudioLibrary {
    
    private static AudioLibrary al;
    private boolean disabled;
    
    private AudioLibrary() {
        disabled = false;
    }
    
    public static AudioLibrary getAudioLibrary() {
        if (al == null) al = new AudioLibrary();
        return al;
    }
    
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
            }
        }
    }
    
    public void setDisabled(boolean d) {
        disabled = d;
    }
}