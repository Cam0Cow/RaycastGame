import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Test extends JFrame implements KeyListener {
    
    private Renderer rend;
    private Dimension dim;
    private GameState game;
    
    public Test(Renderer rend, GameState g, Dimension d) {
        super("Test");
        this.rend = rend;
        dim = d;
        game = g;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        Insets in = getInsets();
        setSize(in.left+dim.width, in.bottom+dim.height);
        this.addKeyListener(this);
    }
    
    public void paint(Graphics g) {
    	rend.render(game);
        g.drawImage(rend.getSurface(),0,0,null);
    }
    
    public void keyTyped(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}
    
    public void keyPressed(KeyEvent e) {
    	Player p = game.getPlayer();
    	switch (e.getKeyCode()) {
    		case KeyEvent.VK_W: {
    			p.setPosX(p.getPosX()+p.getDirX()/10.0);
    			p.setPosY(p.getPosY()+p.getDirY()/10.0);
    			repaint();
    			break;
    		}
    		case KeyEvent.VK_S: {
    			p.setPosX(p.getPosX()-p.getDirX()/10.0);
    			p.setPosY(p.getPosY()-p.getDirY()/10.0);
    			repaint();
    			break;
    		}
    		case KeyEvent.VK_D: {
    			p.setPosX(p.getPosX()-p.getDirY()/10.0);
    			p.setPosY(p.getPosY()+p.getDirX()/10.0);
    			repaint();
    			break;
    		}
    		case KeyEvent.VK_A: {
    			p.setPosX(p.getPosX()+p.getDirY()/10.0);
    			p.setPosY(p.getPosY()-p.getDirX()/10.0);
    			repaint();
    			break;
    		}
    		case KeyEvent.VK_RIGHT: {
    			double x = p.getDirX();
    			double y = p.getDirY();
    			double angle = Math.PI/20;
    			p.setDirX(x*Math.cos(angle)-y*Math.sin(angle));
    			p.setDirY(x*Math.sin(angle)+y*Math.cos(angle));
    			repaint();
    			break;
    		}
    		case KeyEvent.VK_LEFT: {
    			double x = p.getDirX();
    			double y = p.getDirY();
    			double angle = -Math.PI/50;
    			p.setDirX(x*Math.cos(angle)-y*Math.sin(angle));
    			p.setDirY(x*Math.sin(angle)+y*Math.cos(angle));
    			repaint();
    			break;
    		}
    	}
    }
}