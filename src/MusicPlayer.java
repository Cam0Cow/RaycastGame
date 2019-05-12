import javax.sound.sampled.*;
import java.io.*;

public class MusicPlayer {
    
    private static final String FILENAME = "music.wav";
    private AudioInputStream ais;
    private Clip clip;
    private boolean isPlaying;
    private long time;
    
    public MusicPlayer() {
        try {
            loadStuff();
            time = 0L;
            isPlaying = false;
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
    
    private void loadStuff() {
        try {
            BufferedInputStream bis = new BufferedInputStream(getClass().getClassLoader().getResourceAsStream(FILENAME));
            ais = AudioSystem.getAudioInputStream(bis);
            clip = AudioSystem.getClip();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.open(ais);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
    
    public void play() {
        if (!isPlaying) {
            clip.close();
            loadStuff();
            clip.setMicrosecondPosition(time);
            clip.start();
            isPlaying = true;
            System.out.println("Playing");
        }
    }
    
    public void pause() {
        if (isPlaying) {
            time = clip.getMicrosecondPosition();
            clip.stop();
            isPlaying = false;
            System.out.println("Pausing");
        }
    }
}