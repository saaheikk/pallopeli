package pallopeli.collisionphysics;

import java.awt.Point;
import pallopeli.BuildingDirection;

public class Collision {
    private Point collisionPosition;
    private BuildingDirection reflectingDirection;

    public Collision(Point collisionLocation, BuildingDirection reflectingDirection) {
        this.collisionPosition = collisionLocation;
        this.reflectingDirection = reflectingDirection;
    }

    public int getCoordinateX() {
        return this.collisionPosition.x;
    }

    public int getCoordinateY() {
        return this.collisionPosition.y;
    }

    public BuildingDirection getReflectingDirection() {
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
