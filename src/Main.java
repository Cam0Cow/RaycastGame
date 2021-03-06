/*
 * Roie Gal
 * 2019-5-13
 * Raycast Game
 */

import javax.imageio.*;
import java.io.*;
import java.awt.*;

/**
 * The main project file
 */
public class Main {
    /**
     * Entry point
     * @param args unused
     */
    public static void main(String[] args) throws IOException {
        GameState game = new GameState();
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        Renderer rend = new Renderer(size.width, size.height);
        GameLoop gl = new GameLoop(game, rend, new Display());
        game.setLevelMap(new LevelMap("Maze2.txt"));
        game.setPlayer(new Player(
            game.getLevelMap().getLocX()+0.5,
            game.getLevelMap().getLocY()+0.5,
            -0.5,-1));
        gl.loop();
        System.exit(0);
    }
}
