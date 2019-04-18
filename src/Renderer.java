import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.util.*;

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
        HashSet<Entity> entities = game.getLevelMap().getEntities();
        
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
        
        double dirX2 = dirX*dirX; double dirY2 = dirY*dirY;
        double eX = dirX - planeX; double eY = dirY - planeY;
        double edgeView = (dirX*eX+dirY*eY)/(Math.sqrt(dirX2+dirY2)*Math.sqrt(eX*eX+eY*eY)); // player . edge
        double spanScale = width/2/Math.acos(edgeView); // defines how fast the enemies turn out of view
        g.setColor(Color.GRAY);
        g.drawString("view edge: "+edgeView, 50, 50);
        for (Entity e : entities) {
        	double enemyX = e.getX() - posX;
        	double enemyY = e.getY() - posY;
        	double enemyView = (dirX*enemyX + dirY*enemyY)/(Math.sqrt(dirX2+dirY2)*Math.sqrt(enemyX*enemyX+enemyY*enemyY));
        	double enemyAngle = Math.acos(enemyView);
        	g.drawString("view enemy: "+enemyView, 50, 100);
        	if (enemyView > edgeView) { // or is it 1 - edgeView?
        		g.setColor(Color.GREEN);
        		if (toTheLeft(p, e)) enemyAngle = -enemyAngle;
        		Image tex = e.getUnclippedTexture(p);
        		g.drawImage(tex, (int)(width/2+enemyAngle*spanScale), (height-tex.getHeight(null))/2, null);
        	}
        	g.drawString("If Green, enemy should be visible", 50, 150);
        }
        
        surface = back; // for double buffering
    }
    
    private boolean toTheLeft(Player p, Entity e) {
    	// Use a cross product between their vectors. If the z-component is negative
    	// then it's on the right, Otherwise on the left
    	return false;
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
