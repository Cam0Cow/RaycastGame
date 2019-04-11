import javax.imageio.*;
import java.io.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) throws IOException {
        GameState game = new GameState();
        game.setLevelMap(new LevelMap("maps/Map4.txt"));
        game.setPlayer(new Player(
        	game.getLevelMap().getLocX()+0.5,
        	game.getLevelMap().getLocY()+0.5,
        	-0.5,-1));
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        Renderer rend = new Renderer(size.width/2, size.height/2);
        //new Test(rend, game);
        GameLoop gl = new GameLoop(game, rend, new Display());
        gl.loop();
    }
}
