import java.awt.*;
import java.awt.image.*;

public class Renderer {
    
    private BufferedImage surface;
    private int width, height;
    public static final double FOV = 0.66;
    
    public Renderer(int w, int h) {
        width = w;
        height = h;
        surface = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    }
    
    public void render(GameState game) {
        Graphics2D g = surface.createGraphics();
        g.setColor(Color.BLACK);
        g.clearRect(0,0,width,height);
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
                if (x == 0) System.out.printf("%d %f %d %f%n",mapY,posY,stepY,rayDirY);
            } else {
                perpWallDist = (mapX - posX + (1 - stepX) / 2) / rayDirX;
                if (x == 0) System.out.printf("%d %f %d %f%n",mapX,posX,stepX,rayDirX);
            }
            
            int lineHeight = (int) (height / perpWallDist);
            System.out.println(x + ": " + perpWallDist);
            if (lineHeight > height) lineHeight = height;
            if (lineHeight < 0) lineHeight = 0;
            int start = (height - lineHeight) / 2;
            int end = start + lineHeight;
            Color c = Color.GREEN; // set color here
            if (!side) c = c.darker();
            g.setColor(c);
            g.drawLine(x,start,x,end);
        }
    }
    
    public BufferedImage getSurface() {
        return surface;
    }
    
    public Dimension getSize() {
        return new Dimension(width, height);
    }
}
