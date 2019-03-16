import javax.imageio.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        GameState game = new GameState();
        game.setLevelMap(new LevelMap("maps/Map1.txt"));
        game.setPlayer(new Player(5,3,0,-1));
        Renderer rend = new Renderer(640,480);
        rend.render(game);
        ImageIO.write(rend.getSurface(), "PNG", new File("Output.png"));
    }
}