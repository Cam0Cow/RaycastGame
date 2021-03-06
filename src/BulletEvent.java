/*
 * Roie Gal
 * 2019-5-13
 * Raycast Game
 */

import java.time.*;
import java.util.*;

/**
 * Represents the firing of a bullet
 */
public class BulletEvent extends GameEvent {
    
    /**
     * Handles the bullet event
     * @param state the current game state
     * @param dt the frame time
     */
    public void handle(GameState state, Duration dt) {
        HashSet<Entity> entities = state.getLevelMap().getEntities();
        Player p = state.getPlayer();
        Weapon w = p.getWeapon();
        GameLoop loop = state.getGameLoop();
        double dirX = p.getDirX();
        double dirY = p.getDirY();
        double x = p.getPosX();
        double y = p.getPosY();
        double mag = Math.sqrt(dirX*dirX+dirY*dirY);
        for (Entity e : entities) {
            double ex = e.getX() - x;
            double ey = e.getY() - y;
            double distance = Math.sqrt(ex*ex+ey*ey);
            double dot = (dirX*ex+dirY*ey)/(mag*distance);
            double width = e.getWidth() / distance;
            if (dot > 0.98 && distance < loop.getWallDistance()) {
                e.damage(w.getDamageAtRange(distance));
                if (e.isDead()) state.getGameLoop().queueEvent(new EntityCleanupEvent(e));
            }
        }
    }
}
