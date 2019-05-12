import javax.sound.sampled.*;
import java.io.*;

/**
 * Represents a class that can play music from a file
 */
public class MusicPlayer {
    
    private static final String FILENAME = "music.wav";
    private AudioInputStream ais;
    private Clip clip;
    private boolean isPlaying;
    private long time;
    
    /**
     * Constructs a new music player 
     */
    public MusicPlayer() {
        loadStuff();
        time = 0L;
        isPlaying = false;
        
    }
    
    /**
     * Handles initialization for various audio things   
     */
    private void loadStuff() {
        try {
            BufferedInputStream bis = new BufferedInputStream(getClass().getClassLoader().getResourceAsStream(FILENAME));
            ais = AudioSystem.getAudioInputStream(bis);
            clip = AudioSystem.getClip();
            //clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.open(ais);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
    
    /**
     * Plays or resumes the audio playback   
     */
    public void play() {
        if (!isPlaying) {
            clip.close();
            loadStuff();
            clip.setMicrosecondPosition(time);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            isPlaying = true;
        }
    }
    
    /**
     * Pauses the audio playback   
     */
    public void pause() {
        if (isPlaying) {
            time = clip.getMicrosecondPosition();
            clip.stop();
            isPlaying = false;
        }
    }
}