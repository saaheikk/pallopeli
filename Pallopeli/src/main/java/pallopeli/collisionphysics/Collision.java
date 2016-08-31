package pallopeli.collisionphysics;

import java.awt.Point;
import pallopeli.MovementType;
import pallopeli.SimpleDirection;

/**
 * Collision represents a single collision event.
 * @author saara
 */

public class Collision {
    private Point collisionPosition;
    private SimpleDirection reflectingDirection;
    private MovementType type;
    
    /**
     * Creates new Collision.
     * @param collisionLocation
     * @param reflectingDirection 
     */
    public Collision(Point collisionLocation, SimpleDirection reflectingDirection, MovementType type) {
        this.collisionPosition = collisionLocation;
        this.reflectingDirection = reflectingDirection;
        this.type = type;
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

    public MovementType getType() {
        return type;
    }
    
    @Override
    public String toString() {
        return this.reflectingDirection + " collision at " + this.collisionPosition + ", type: " + this.type;
    }
    
    

}
