package pallopeli.collisionphysics;

import java.awt.Point;
import pallopeli.MovementType;
import pallopeli.SimpleDirection;

/**
 * Collision represents a single collision event.
 * @author saara
 */

public class Collision {
    private Point collisionPoint;
    private SimpleDirection reflectingDirection;
    private MovementType type;
    
    /**
     * Creates new Collision.
     * @param collisionPoint The point where Collision occurs.
     * @param reflectingDirection SimpleDirection that determines the reflecting of Ball.
     * @param type Type of Collision (IN / OUT).
     */
    public Collision(Point collisionPoint, SimpleDirection reflectingDirection, MovementType type) {
        this.collisionPoint = collisionPoint;
        this.reflectingDirection = reflectingDirection;
        this.type = type;
    }

    public int getCoordinateX() {
        return this.collisionPoint.x;
    }

    public int getCoordinateY() {
        return this.collisionPoint.y;
    }

    public SimpleDirection getReflectingDirection() {
        return reflectingDirection;
    }

    public Point getCollisionPoint() {
        return collisionPoint;
    }

    public MovementType getType() {
        return type;
    }
    
    @Override
    public String toString() {
        return this.reflectingDirection + " collision at " + this.collisionPoint + ", type: " + this.type;
    }
    
    

}
