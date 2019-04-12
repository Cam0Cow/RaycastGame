import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;

/**
 * Represents a game renderer
 */
public class Renderer {
    
    private BufferedImage surface, back;
    private double[] wallDistances;
    private int width, height;
    public static final double FOV = 0.9;
    
    /**
     * Creates a new renderer with a given width and height
     * @param w the width
     * @param h the height
     */
    public Renderer(int w, int h) {
        width = w;
        height = h;
        surface = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        back    = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        wallDistances = new double[w];
    }
    
    /**
     * renders the given GameState to a BufferedImage
     * @param game the current GameState
     */
    public void render(GameState game) {
        Graphics2D g = back.createGraphics();
        g.setBackground(Color.BLUE.brighter());
        g.clearRect(0,0,width,height/2);
        g.setBackground(Color.GRAY);
        g.clearRect(0,height/2,width,height/2);
        Player p = game.getPlayer();
        MapTile[][] mt = game.getLevelMap().getMap();
        
        double posX = p.getPosX();
        double posY = p.getPosY();
        double dirX = p.getDirX();
        double dirY = p.getDirY();
        double planeX = -dirY*FOV;
        double planeY = dirX*FOV;
        
        for (int x=0; x<width; x++) {
            double cameraX = 2 * x / ((double)width) - 1;
            double rayDirX = dirX + planeX * cameraX;
            double rayDirY = dirY + planeY * cameraX;
            int mapX = (int) posX;
            int mapY = (int) posY;
            double sideDistX, sideDistY;
            double deltaDistX = Math.abs(1 / rayDirX);
            double deltaDistY = Math.abs(1 / rayDirY);
            double perpWallDist;
            int stepX, stepY;
            boolean hit = false;
            boolean side = false;
            
            if (rayDirX < 0) {
                stepX = -1;
                sideDistX = (posX - mapX) * deltaDistX;
            } else {
                stepX = 1;
                sideDistX = (mapX + 1.0 - posX) * deltaDistX;
            }
            if (rayDirY < 0) {
                stepY = -1;
                sideDistY = (posY - mapY) * deltaDistY;
            } else {
                stepY = 1;
                sideDistY = (mapY + 1.0 - posY) * deltaDistY;
            }
            
            while (!hit) {
                if (sideDistX < sideDistY) {
                    sideDistX += deltaDistX;
                    mapX += stepX;
                    side = false;
                } else {
                    sideDistY += deltaDistY;
                    mapY += stepY;
                    side = true;
                }
                if (mt[mapY][mapX].ordinal() > 0) hit = true;
            }
            
            if (side) {
                perpWallDist = (mapY - posY + (1 - stepY) / 2) / rayDirY;
            } else {
                perpWallDist = (mapX - posX + (1 - stepX) / 2) / rayDirX;     
            }
            
            int lineHeight = (int) (height / perpWallDist);
            wallDistances[x] = perpWallDist;
            // System.out.println(x + ": " + perpWallDist);
            if (lineHeight > height) lineHeight = height;
            if (lineHeight < 0) lineHeight = 0;
            int start = (height - lineHeight) / 2;
            int end = start + lineHeight;
            Color c = MapTile.getColor(mt[mapY][mapX]);
            if (!side) c = c.darker();
            g.setColor(c);
            g.drawLine(x,start,x,end);
        }
        
        surface = back;
        
        // System.out.println("Frame was rendered");
    }
    
    /**
     * Returns the rendered surface
     * @return the rendered surface
     */
    public BufferedImage getSurface() {
        return surface;
    }
    
    /**
     * Returns a scaled version of the rendered surface
     * @param desired the new dimensions of the surface
     * @return a scaled version of the rendered surface
     */
    public Image getScaledSurface (Dimension desired) {
    	if (desired.equals(getSize())) return getSurface();
        return surface.getScaledInstance(
            desired.width, desired.height, Image.SCALE_FAST);
    }
    
    /**
     * Returns the size of the Renderer
     * @return the size of the Renderer
     */
    public Dimension getSize() {
        return new Dimension(width, height);
    }
}
