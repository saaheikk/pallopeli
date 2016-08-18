package pallopeli;

/**
 * CompassDirection is mainly used to manage Piece's neighbors.
 * @author saara
 */

public enum CompassDirection {
    NORTH(0, 1),
    NORTHEAST(1, 1),
    EAST(1, 0),
    SOUTHEAST(1, -1),
    SOUTH(0, -1),
    SOUTHWEST(-1, -1),
    WEST(-1, 0),
    NORTHWEST(-1, 1);
    
    int x;
    int y;
    
    CompassDirection(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    
    
}
