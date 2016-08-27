package pallopeli.objects;

import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Arc2D;
import java.awt.geom.Line2D;
import static java.lang.Math.sqrt;
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
    
    private HashMap<CompassDirection, Piece> neighbors;
    
//    private HashMap<CompassDirection, Line2D> borders;   
    private HashMap<CompassDirection, Point> cornerPoints;
//    private HashMap<CompassDirection, Boolean> activeBorders;

    public Piece(int x, int y, boolean wall, int sizeOfObjects) {
        this.x = x;
        this.y = y;
        this.wall = wall;
        this.size = sizeOfObjects;
        this.neighbors = new HashMap<>();
//        this.activeBorders = new HashMap<>();
        this.setCorners();
//        this.setBorders();
    }
    
    /**
     * Turns Piece into wall so that ball bounces from it.
     */
    public void turnIntoWall() {
        this.wall = true;
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
    


    // this method is used to build walls during the game and it's still under consturction
    public boolean turnNeighborIntoWall(CompassDirection compassDirection) {
        Piece neighbor = this.neighbors.get(compassDirection);
        System.out.println("Neighbor: (" + neighbor.x + ", " + neighbor.y + ")");
        if (neighbor == null) {
            return false;
        } else if (neighbor.isWall()) {          
            return false;
        } else {
            // here we have to check that Ball does not collide with neigbor - game over otherwise!
            neighbor.turnIntoWall();
            return true;
        }
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
    // just trying smthg
    protected void setBorders() {
        Line2D northBorder = new Line2D.Float(cornerPoints.get(CompassDirection.NORTHWEST), cornerPoints.get(CompassDirection.NORTHEAST));
        Line2D eastBorder = new Line2D.Float(cornerPoints.get(CompassDirection.NORTHEAST), cornerPoints.get(CompassDirection.SOUTHEAST));
        Line2D southBorder = new Line2D.Float(cornerPoints.get(CompassDirection.SOUTHEAST), cornerPoints.get(CompassDirection.SOUTHWEST));
        Line2D westBorder = new Line2D.Float(cornerPoints.get(CompassDirection.SOUTHWEST), cornerPoints.get(CompassDirection.NORTHWEST));
    }    
    // just trying smthg
//    public ArrayList<Object> getActiveCornersAndBorders() {
//        ArrayList<Object> activeBorders = new ArrayList<>();
//        for (CompassDirection direction : this.cornerPoints.keySet()) {
//            if (this.activeBorders.get(direction)) {
//                activeBorders.add(this.cornerPoints.get(direction));
//            }
//        }
//        for (CompassDirection direction : this.borders.keySet()) {
//            if (this.activeBorders.get(direction)) {
//                activeBorders.add(this.borders.get(direction));
//            }
//        }
//        return activeBorders;
//    }
    
    
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

    // trash   
    // reset borders for bouncing 
//    public void resetBorders() {
//        // only for wall?
//        for (CompassDirection direction : CompassDirection.values()) {
//            Piece neighbor = this.neighbors.get(direction);
//            if (neighbor.isWall()) {
//                this.borders.put(direction, null); // unguarded borders are null
//            } else {
//                // set border (either line/arc)
//            }
//        }
//    }
    // too much copypaste
//    public void setBorder(CompassDirection d) {
//        if (d == CompassDirection.NORTH) {
//            Line2D borderLine = new Line2D.Float(x * size, y * size - size / 2, 
//                    (x + 1) * size, (y + 1) * size - size / 2);
//            this.borders.put(d, borderLine);
//        }      
//    }

//    public boolean borderIsActive(CompassDirection direction) {
//        return this.activeBorders.get(direction);
//    }
//    
//    public void resetActiveBorders() {
//        if (this.wall) { // only for wallpieces
//            for (CompassDirection direction : CompassDirection.values()) {
//                Piece neighbor = this.neighbors.get(direction);
//                if (neighbor != null) {
//                    if (neighbor.isWall()) {
//                        this.activeBorders.put(direction, false);
//                    } else {
//                        this.activeBorders.put(direction, true);
//                    }
//                } else {
//                    this.activeBorders.put(direction, false);
//                }
//
//            }
//        }
//    }

//    public boolean isSurroundedByWallPieces() {
//        // returns true for pieces that have no neighbors!!!
//        if (this.northNeighbor == null || this.northNeighbor.isWall()) {
//            if (this.eastNeighbor == null || this.eastNeighbor.isWall()) {
//                if (this.southNeighbor == null || this.southNeighbor.isWall()) {
//                    if (this.southNeighbor == null || this.southNeighbor.isWall()) {
//                        return true;
//                    }
//                }
//            }
//        }
//        return false; // returns true for pieces that have no neighbors!!!
//    }
//    public CompassDirection getDirectionWhereToBounce(int ballX, int ballY) {
//        // this code will be imporved... i am aware that currenlty it's very ugly :(
//        int topLeftCornerX = this.size * this.x;
//        int topLeftCornerY = this.size * this.y;
//        int topRightCornerX  = topLeftCornerX + this.size;
//        int topRightCornerY = topLeftCornerY;
//        int bottomLeftCornerX = topLeftCornerX;
//        int bottomLeftCornerY = topLeftCornerY + this.size;
//        int bottomRightCornerX = topRightCornerX;
//        int bottomRightCornerY = bottomLeftCornerY;
//                
//        if (ballX - topRightCornerX == ballY - topRightCornerY) {
//            return CompassDirection.NORTHEAST;
//        } else if (ballX - topLeftCornerX == ballY - topLeftCornerY) {
//            return CompassDirection.NORTHWEST;
//        } else if (ballY < topLeftCornerY) {
//            if (ballX < topLeftCornerX && topLeftCornerX - ballX > topLeftCornerY - ballY) {
//                return CompassDirection.WEST;
//            } else if (ballX > topRightCornerX && ballX - topRightCornerX > topLeftCornerY - ballY) {
//                return CompassDirection.EAST;
//            }
//            return CompassDirection.NORTH;
//        } else if (ballY > bottomLeftCornerY) {
//            if (ballX < bottomLeftCornerX && bottomLeftCornerX - ballX > bottomLeftCornerY - ballY) {
//                return CompassDirection.WEST;
//            } else if (ballX > bottomRightCornerX && ballX - bottomRightCornerX > bottomRightCornerY - ballY) {
//                return CompassDirection.EAST;
//            }                        
//            return CompassDirection.SOUTH;
//        } else if (ballX < topLeftCornerX) {
//            if (ballY < topLeftCornerY && topLeftCornerY - ballY > topLeftCornerX - ballX) {
//                return CompassDirection.NORTH;
//            } else if (ballY > bottomLeftCornerY && ballY - bottomLeftCornerY > bottomLeftCornerX - ballX) {
//                return CompassDirection.SOUTH;
//            }              
//            return CompassDirection.WEST;
//        } else if (ballX > topRightCornerX) {
//            if (ballY < topRightCornerY && topRightCornerY - ballY > ballX - topRightCornerX) {
//                return CompassDirection.NORTH;
//            } else if (ballY > bottomRightCornerY && ballX - bottomRightCornerX > ballY - bottomRightCornerY) {
//                return CompassDirection.SOUTH;
//            }              
//            return CompassDirection.EAST;
//        }
//        return null;
//    }
}
