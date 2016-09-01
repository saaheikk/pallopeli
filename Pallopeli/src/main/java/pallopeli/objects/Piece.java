package pallopeli.objects;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import pallopeli.CompassDirection;

/**
 * Piece represents a piece on board and it can be either wall (ball bounces from it) or "transparent" (ball does not see it).
 * @author saara
 */

public class Piece {
    private int x; // location relative to the board
    private int y; // location relative to the board
    private int size; // pixels    
    private boolean wall;
    private boolean underConstruction;
    
    private HashMap<CompassDirection, Piece> neighbors;  
    private HashMap<CompassDirection, Point> cornerPoints;
    private HashMap<CompassDirection, Border> borders;
    /**
     * Constructor for a new Piece.
     * @param x Location relative to the board.
     * @param y Location relative to the board.
     * @param wall Wall or not.
     * @param sizeOfObjects Size of Piece (in pixels).
     */
    public Piece(int x, int y, boolean wall, int sizeOfObjects) {
        this.x = x;
        this.y = y;
        this.wall = wall;
        this.underConstruction = false;
        this.size = sizeOfObjects;
        this.neighbors = new HashMap<>();
        this.borders = new HashMap<>();
        this.setCorners();
    }
    
    /**
     * Turns Piece into wall so that ball bounces from it.
     */
    public void turnIntoWall() {
        this.wall = true;
    } 
    /**
     * Sets all borders for piece (does not depend on whether it's wall or not).
     * @param radiusOfBall 
     */

    public void setMainBorders(int radiusOfBall) {
        for (CompassDirection direction : CompassDirection.NORTH.allMainDirections()) {
            Piece neighbor = this.getNeighbor(direction);
            if (neighbor != null) {
                BorderLine border = new BorderLine(this, direction, radiusOfBall); 
                border.extendLine(); // no time for arcs
                this.borders.put(direction, border);
            }
        } 
    }
    
    /**
     * Checks if given Ball touches Piece or not.
     * @param ball Ball in game.
     * @return True if given ball touches the piece and false if not.
     */
    public boolean hasBall(Ball ball) { 
        int leftBorder = cornerPoints.get(CompassDirection.NORTHWEST).x - ball.getRadius();
        int rightBorder = cornerPoints.get(CompassDirection.NORTHEAST).x + ball.getRadius();        
        if (leftBorder <= ball.getCoordinateX() 
                && ball.getCoordinateX() <= rightBorder
                && cornerPoints.get(CompassDirection.NORTHWEST).y <= ball.getCoordinateY()
                && ball.getCoordinateY() <= cornerPoints.get(CompassDirection.SOUTHEAST).y) {
            return true;
        } 
        int topBorder = cornerPoints.get(CompassDirection.NORTHWEST).y - ball.getRadius();
        int bottomBorder = cornerPoints.get(CompassDirection.SOUTHEAST).y + ball.getRadius();                
        if (topBorder <= ball.getCoordinateY() 
                && ball.getCoordinateY() <= bottomBorder
                && cornerPoints.get(CompassDirection.NORTHWEST).x <= ball.getCoordinateX()
                && ball.getCoordinateX() <= cornerPoints.get(CompassDirection.NORTHEAST).x) {
            return true;
        }
        for (CompassDirection direction : CompassDirection.values()) {
            if (cornerPoints.get(direction) != null) {
                if (cornerPoints.get(direction).distance(ball.getCurrentPosition()) <= ball.getRadius()) {
                    return true;
                }                
            }
        }
        return false;
    }

    /**
     * Turns neighbor in given direction into wall.
     * This method is used to build walls during the game.
     * @param compassDirection Direction where to build.
     * @return False if neighbor is  null or already wall and true if not. 
     */
    public boolean turnNeighborIntoWall(CompassDirection compassDirection) {
        Piece neighbor = this.neighbors.get(compassDirection);
        if (neighbor == null) {
            return false;
        } else if (neighbor.isWall()) {          
            return false;
        } else {
            neighbor.turnIntoWall();
            return true;
        }
    }
    // this method is used to set other pieces under construction during the game 
    /**
     * Sets neighbor in given direction under construction.
     * This method is used to build walls during the game.
     * @param compassDirection Direction where to construct.
     * @return True if neighbor can be set under construction and false if not.
     */
    public boolean setNeighborUnderConstruction(CompassDirection compassDirection) {
        Piece neighbor = this.neighbors.get(compassDirection);
        if (neighbor == null) {
            return false;
        } else if (neighbor.isWall()) {          
            return false;
        } else {
            // (here we have to check that Ball does not collide with neigbor - game over otherwise!)
            neighbor.setUnderConstruction(true);
            return true;
        }
    } 
    /**
     * Turn all neighbors into wall that can be turned into wall.
     * Used to fill areas by wall.
     * @return ArrayList of Pieces turned into wall.
     */
    public ArrayList<Piece> turnAllNeighborsIntoWall() {
        ArrayList<Piece> piecesTurnedIntoWall = new ArrayList<>();
        for (CompassDirection cd : this.neighbors.keySet()) {
            boolean turnedNeighborIntoWall = this.turnNeighborIntoWall(cd);
            if (turnedNeighborIntoWall) {
                piecesTurnedIntoWall.add(this.neighbors.get(cd));
            }
        }
        return piecesTurnedIntoWall;
    }

    /**
     * Helper method for setting corner points.
     */
    protected void setCorners() {
        this.cornerPoints = new HashMap<>();
        this.cornerPoints.put(CompassDirection.NORTHEAST, new Point((x + 1) * size, y * size));
        this.cornerPoints.put(CompassDirection.SOUTHEAST, new Point((x + 1) * size, (y + 1) * size));
        this.cornerPoints.put(CompassDirection.SOUTHWEST, new Point(x * size, (y + 1) * size));
        this.cornerPoints.put(CompassDirection.NORTHWEST, new Point(x * size, y * size));
    }
    /**
     * Resets the activity of Borders according to the current state of neighbors.
     */
    public void resetActivityOfBorders() {
        for (CompassDirection direction : this.borders.keySet()) {
            if (this.borders.get(direction) != null) {
                Piece neighbor = this.getNeighbor(direction);
                this.borders.get(direction).setActivity(!neighbor.isWall());
            }
        }
    }
    
    @Override
    public String toString() {
        if (this.wall) {
            return "(" + this.x + "," + this.y + "): is-wall, anchor coordinates: " + this.cornerPoints.get(CompassDirection.NORTHWEST);
        }
        return "(" + this.x + "," + this.y + "): no-wall, anchor coordinates: " + this.cornerPoints.get(CompassDirection.NORTHWEST);
    }

    // getter & setters
    
    public boolean isWall() {
        return this.wall;
    }
    
    public void setNeighbor(CompassDirection direction, Piece piece) {
        this.neighbors.put(direction, piece);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSize() {
        return size; 
    }
    
    public Point getCornerPoint(CompassDirection direction) {
        return this.cornerPoints.get(direction);
    }
    
    public int getCenterCoordinateX() {
        return this.x * this.size + this.size / 2;
    }
    public int getCenterCoordinateY() {
        return this.y * this.size + this.size / 2;
    }
    public boolean isUnderConstruction() {
        return underConstruction;
    }

    public void setUnderConstruction(boolean underConstruction) {
        this.underConstruction = underConstruction;
    }
    public Piece getNeighbor(CompassDirection direction) {
        return this.neighbors.get(direction);
    }
    public HashMap<CompassDirection, Border> getBorders() {
        return borders;
    }

}
