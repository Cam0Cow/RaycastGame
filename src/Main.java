import javax.imageio.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        GameState game = new GameState();
        game.setLevelMap(new LevelMap("maps/Map4.txt"));
        game.setPlayer(new Player(
        	game.getLevelMap().getLocX()+0.5,
        	game.getLevelMap().getLocY()+0.5,
        	-0.5,-1));
        Renderer rend = new Renderer(1280,960);
        new Test(rend, game, rend.getSize());
    }
}
