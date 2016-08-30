package pallopeli.collisionphysics;

import java.awt.Point;
import pallopeli.SimpleDirection;

/**
 * Collision represents a single collision event.
 * @author saara
 */

public class Collision {
    private Point collisionPosition;
    private SimpleDirection reflectingDirection;
    // private "TYPE"???
    
    /**
     * Creates new Collision.
     * @param collisionLocation
     * @param reflectingDirection 
     */
    public Collision(Point collisionLocation, SimpleDirection reflectingDirection) {
        this.collisionPosition = collisionLocation;
        this.reflectingDirection = reflectingDirection;
    }

    public int getCoordinateX() {
        return this.collisionPosition.x;
    }

    public int getCoordinateY() {
        return this.collisionPosition.y;
    }

    public SimpleDirection getReflectingDirection() {
        return reflectingDirection;
    }

    public Point getCollisionPosition() {
        return collisionPosition;
    }
    @Override
    public String toString() {
        return "Collision at (" + this.collisionPosition.x + "," + this.collisionPosition.y + ")";
    }
    
    

}
