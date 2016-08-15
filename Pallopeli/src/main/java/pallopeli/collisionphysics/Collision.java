package pallopeli.collisionphysics;

import pallopeli.BuildingDirection;

public class Collision {
    private int x;
    private int y;
    private BuildingDirection direction;

    public Collision(int x, int y, BuildingDirection direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public BuildingDirection getDirection() {
        return direction;
    }
    
    
    

}
