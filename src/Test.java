import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

public class Test extends JFrame implements KeyListener {
    
    private Renderer rend;
    private GameState game;
    
    public Test(Renderer rend, GameState g) {
        super("Test");
        this.rend = rend;
        game = g;
        setExtendedState(MAXIMIZED_BOTH);
        setUndecorated(true);
        GraphicsEnvironment.getLocalGraphicsEnvironment()
            .getScreenDevices()[0].setFullScreenWindow(this);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setCursor(getToolkit().createCustomCursor(
            new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB)
                ,new Point(), null));
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setVisible(true);
        this.addKeyListener(this);
    }
    
    public void paint(Graphics g) {
    	rend.render(game);
        g.drawImage(rend.getScaledSurface(getSize()),0,0,null);
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
            case KeyEvent.VK_ESCAPE: {
                System.exit(0);
            }
    	}
    }
}