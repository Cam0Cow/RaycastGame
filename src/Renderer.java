import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.awt.font.*;
import java.awt.event.*;
import java.util.*;

/**
 * Represents a game renderer
 */
public class Renderer {
    
    private BufferedImage surface, back;
    private GameState state;
    private boolean inMenu;
    private boolean inHelp;
    private boolean inBackstory;
    private boolean gameOver;
    private double[] wallDistances;
    private int width, height;
    public static final double FOV = 0.9;
    public static final Dimension HEALTH_BAR_SIZE = new Dimension(200,30);
    public static final long MENU_TIME_DELAY = 70; // in ms
    
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
        inMenu = true;
        inHelp = false;
        inBackstory = false;
        gameOver = false;
        state = null;
    }
    
    /**
     * renders the given GameState to a BufferedImage
     * @param game the current GameState
     */
    public boolean render(GameState game) {
    	state = game;
        Graphics2D g = back.createGraphics();
        g.setBackground(new Color(130, 171, 237));
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
                if (mt[mapY][mapX] != MapTile.NOTHING) hit = true;
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
        // g.drawString("view edge: "+edgeView, 50, 50);
        for (Entity e : entities) {
        	// game.getGameLoop().queueEvent(new EnemyAIEvent(e));
        	double enemyX = e.getX() - posX;
        	double enemyY = e.getY() - posY;
        	double enemyDistance = e.getDistance(p);
        	double enemyView = (dirX*enemyX + dirY*enemyY)/(Math.sqrt(dirX2+dirY2)*Math.sqrt(enemyX*enemyX+enemyY*enemyY));
        	double enemyAngle = Math.acos(enemyView);
        	double enemySideBias = Math.sin(e.getWidth()/width*FOV/enemyDistance/2);
        	// g.drawString("view enemy: "+enemyView, 50, 100);
        	// g.drawString("enemy side bias: "+enemySideBias, 50, 75);
        	if (enemyView+enemySideBias > edgeView) { // or is it 1 - edgeView?
        		// g.setColor(Color.GREEN);
        		if (dirX*enemyY-enemyX*dirY < 0) enemyAngle = -enemyAngle;
        		BufferedImage tex = toBuf(e.getUnclippedTexture(p));
        		int xwd = tex.getWidth(null);
        		int yht = tex.getHeight(null);
        		int xLeft = (int)(width/2+enemyAngle*spanScale);
        		int yTop  = (height-yht)/2;
        		boolean show = true;
        		// if there's a wall in front and to the left of the enemy,
        		// clip the left side, otherwise, the right side
        		if (xLeft > 0 && xLeft < width && wallDistances[xLeft] < enemyDistance) {
        			int clipLeft = 0;
        			while (xLeft < width-1 && wallDistances[++xLeft] < enemyDistance && clipLeft < xwd) clipLeft++;
        			show = (clipLeft != xwd);
        			if (show) tex = tex.getSubimage(clipLeft, 0, xwd-clipLeft, yht);
        		} else if (xLeft+xwd > 0 && xLeft+xwd < width && wallDistances[xLeft+xwd] < enemyDistance){
        			while (--xwd > 0 && xLeft+xwd < width && xLeft+xwd > 0 && wallDistances[xLeft+xwd] < enemyDistance);
        			show = (xwd != 0);
        			if (show) tex =  tex.getSubimage(0, 0, xwd, yht);
        		}
        		
        		if (show) g.drawImage(tex, xLeft, yTop, null);
        	}
        	// g.drawString("If Green, enemy should be visible", 50, 150);
        }
        
        // draw weapon to screen
        Image weapon = p.getWeapon().getTexture();
        g.drawImage(weapon, width/2, height-weapon.getHeight(null), null);
        
        // draw healthbar
        int pHealth = p.getHealth();
        if (pHealth < 0) pHealth = 0;
        int barWidth = HEALTH_BAR_SIZE.width / 10 * pHealth;
        g.setColor(Color.BLACK);
        g.fillRect(width/2-HEALTH_BAR_SIZE.width-20,height-HEALTH_BAR_SIZE.height-20,HEALTH_BAR_SIZE.width,HEALTH_BAR_SIZE.height);
        g.setColor(Color.RED);
        g.fillRect(width/2-HEALTH_BAR_SIZE.width-20,height-HEALTH_BAR_SIZE.height-20,barWidth,HEALTH_BAR_SIZE.height);
        
        // draw debug information
        g.setColor(Color.GRAY);
        g.drawString(""+game.getFPS().getFPS()+" FPS", 50, 25);
        g.drawString(String.format("XY: %.2f / %.2f", posX, posY), 50, 50);
        long memUsage = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        g.drawString("Memory usage: " + (memUsage >> 10) + " Kb", 50, 75);
        
        // draw the menu
        if (inMenu) {
        	KeyState ks = KeyState.getKeyState();
        	g.setBackground(Color.BLACK);
        	g.clearRect(0,0,width,height);
	        g.setColor(Color.RED);
	        g.setFont(g.getFont().deriveFont(Font.BOLD, 72.0f));
	        FontMetrics fm = g.getFontMetrics();
	        
	        // draw the help screen if requested
	        if (inHelp) {
	        	LinkedList<String> options = new LinkedList<String>();
		        options.add("Use WASD to move forwards, to the left,");
		        options.add("backwards, and to the right, respectively.");
		        options.add("Use the mouse or arrow keys to look left and right");
		        options.add("Shoot the enemies by left clicking with the mouse");
		        options.add("If the enemies get too close, they will damage you!");
		        int d = height / (options.size()+1);
		        for (int i=1; i<=options.size(); i++) {
		        	int sWidth = fm.stringWidth(options.get(i-1));
		        	int x = (width-sWidth)/2;
		        	int y = d*i;
		        	g.drawString(options.get(i-1), x, y);
		        }
		        
	        	if (ks.isDown(KeyEvent.VK_ESCAPE)) {
	        		inHelp = false;
	        		ks.purge();
	        	}
	        } else if (inBackstory) {
	        	LinkedList<String> options = new LinkedList<String>();
		        options.add("You must find the treasure and escape the maze");
		        options.add("The radioactive guards will try to stop you");
		        options.add("Let no one be between you and the booty");
		        options.add("Try not to die");
		        options.add("The booty beckons");
		        int d = height / (options.size()+1);
		        for (int i=1; i<=options.size(); i++) {
		        	int sWidth = fm.stringWidth(options.get(i-1));
		        	int x = (width-sWidth)/2;
		        	int y = d*i;
		        	g.drawString(options.get(i-1), x, y);
		        }
		        
		        if (ks.isDown(KeyEvent.VK_ESCAPE)) {
		        	inBackstory = false;
		        	ks.purge();
		        }
	        } else {
		        LinkedList<String> options = new LinkedList<String>();
		        options.add("Press Esc to return to Game");
		        options.add("Press B for Backstory");
		        options.add("Press H for Help");
		        options.add("Press Q to Quit");
		        int d = height / (options.size()+1);
		        for (int i=1; i<=options.size(); i++) {
		        	int sWidth = fm.stringWidth(options.get(i-1));
		        	int x = (width-sWidth)/2;
		        	int y = d*i;
		        	g.drawString(options.get(i-1), x, y);
		        }
		        if (ks.isDown(KeyEvent.VK_ESCAPE)) {
		        	inMenu = false;
		        	ks.purge();
		        	game.getGameLoop().getMouseState().unfreeze();
		        }
		        if (ks.isDown(KeyEvent.VK_H)) {
		        	inHelp = true;
		        	ks.purge();
		        }
		        if (ks.isDown(KeyEvent.VK_B)) {
		        	inBackstory = true;
		        	ks.purge();
		        }
		        if (ks.isDown(KeyEvent.VK_Q)) return true;
	        }
        }
        
        if (gameOver) {
        	KeyState ks = KeyState.getKeyState();
        	g.setBackground(Color.BLACK);
        	g.clearRect(0,0,width,height);
	        g.setColor(Color.RED);
	        g.setFont(g.getFont().deriveFont(Font.BOLD, 144.0f));
	        FontMetrics fm = g.getFontMetrics();
	        g.drawString("GAME OVER", (width-fm.stringWidth("GAME OVER"))/2, (height-fm.getAscent())/2);
	        
	        if (ks.getNumKeysDown() > 0) return true;
        }
        
        surface = back; // for double buffering
        
        g.dispose();
        
        return false;
    }
    
    /**
     * Converts an image into a buffered image
     * @param img the given image
     * @return the converted buffered image
     */
    public static BufferedImage toBuf(Image img) {
    	if (img instanceof BufferedImage) return (BufferedImage) img;
    	BufferedImage bi = new BufferedImage(
    		img.getWidth(null), img.getHeight(null), 
    			BufferedImage.TYPE_INT_ARGB);
    	
    	Graphics g = bi.getGraphics();
    	g.drawImage(img, 0, 0, null);
    	g.dispose();
    	return bi;
    }
    
    /**
     * Returns a scaled version of the rendered surface
     * @param desired the new dimensions of the surface
     * @return a scaled version of the rendered surface
     */
    public Image getScaledSurface (Dimension desired) {
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
    
    /**
     * Returns the distance to the wall in front
     * @return the distance to the wall in front
     */
    public double getWallDistance() {
    	return wallDistances[width/2];
    }
    
    /**
     * Draws text with a white background
     * @param g the graphics context
     * @param txt the text to display
     * @param x the x coordinate to draw at
     * @param y the y coordinate to draw at
     */
    public static void drawText(Graphics2D g, String txt, int x, int y) {
    	FontMetrics info = g.getFontMetrics();
    	int width = info.stringWidth(txt);
    	int height = info.getHeight();
    	Color c = g.getColor();
    	g.setColor(Color.WHITE);
    	g.fillRect(x, y, width, height);
    	g.setColor(c);
    	g.drawString(txt, x, y);
    }
    
    /**
     * Toggles the menu
     */
    public void toggleMenu() {
    	// GameLoop.delay(MENU_TIME_DELAY);
    	inMenu = true;
    	KeyState.getKeyState().purge();
    	state.getGameLoop().getMouseState().freeze();
    }
    
    /**
     * Checks whether the game should be paused
     * @return whether the game should be paused
     */
    public boolean isPaused() {
    	return inMenu;
    }
    
    /**
     * Called when the player dies
     */
    public void setGameOver() {
    	gameOver = true;
    	KeyState.getKeyState().purge();
    }
}
