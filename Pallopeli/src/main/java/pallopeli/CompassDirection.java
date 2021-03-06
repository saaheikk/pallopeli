package pallopeli;

/**
 * CompassDirection is used to manage Piece's neighbors and build walls on Board.
 * @author saara
 */

public enum CompassDirection {
    NORTH(0, -1),
    NORTHEAST(1, -1),
    EAST(1, 0),
    SOUTHEAST(1, 1),
    SOUTH(0, 1),
    SOUTHWEST(-1, 1),
    WEST(-1, 0),
    NORTHWEST(-1, -1);
    
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
    
    /**
     * Returns the CompassDirection associated with a vector.
     * @param x First coordinate.
     * @param y Second coordinate.
     * @return CompassDirection.
     */   
    public CompassDirection getCompassDirection(int x, int y) {
        if (x == 0 && y == -1) {
            return CompassDirection.NORTH;
        } else if (x == 1 && y == -1) {
            return CompassDirection.NORTHEAST;
        } else if (x == 1 && y == 0) {
            return CompassDirection.EAST;
        } else if (x == 1 && y == 1) {
            return CompassDirection.SOUTHEAST;
        } else if (x == 0 && y == 1) {
            return CompassDirection.SOUTH;
        } else if (x == -1 && y == 1) {
            return CompassDirection.SOUTHWEST;
        } else if (x == -1 && y == 0) {
            return CompassDirection.WEST;
        } else if (x == -1 && y == -1) {
            return CompassDirection.NORTHWEST;
        }
        return null;
    }
    /**
     * Returns the opposite direction, for example, for NORTH, this method would return SOUTH.
     * @return CompassDirection.
     */
    public CompassDirection getOppositeCompassDirection() {
        return this.getCompassDirection(this.x * -1, this.y * -1);
    }
    /**
     * Method used to eliminate copy paste in Wallbuilder.
     * @return Intercardinal direction closer to origin.
     */
    public CompassDirection getFirstSideOfMainDirection() {
        if (this == CompassDirection.NORTH) {
            return CompassDirection.NORTHWEST;
        } else if (this == CompassDirection.EAST) {
            return CompassDirection.NORTHEAST;
        } else if (this == CompassDirection.SOUTH) {
            return CompassDirection.SOUTHWEST;
        } else if (this == CompassDirection.WEST) {
            return CompassDirection.NORTHWEST;
        }
        return null;
    }
    /**
     * Method used to eliminate copy paste in Wallbuilder.
     * @return Intercardinal direction further from origin.
     */
    public CompassDirection getSecondSideOfMainDirection() {
        if (this == CompassDirection.NORTH) {
            return CompassDirection.NORTHEAST;
        } else if (this == CompassDirection.EAST) {
            return CompassDirection.SOUTHEAST;
        } else if (this == CompassDirection.SOUTH) {
            return CompassDirection.SOUTHEAST;
        } else if (this == CompassDirection.WEST) {
            return CompassDirection.SOUTHWEST;
        }
        return null;
    }    
    /**
     * Returns the cardinal directions.
     * @return Table of cardinal directions.
     */
    public CompassDirection[] allMainDirections() {
        CompassDirection[] mainDirections = new CompassDirection[4];
        mainDirections[0] = CompassDirection.NORTH;
        mainDirections[1] = CompassDirection.EAST;
        mainDirections[2] = CompassDirection.SOUTH;
        mainDirections[3] = CompassDirection.WEST;
        return mainDirections;
    }

}
