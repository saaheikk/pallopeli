package pallopeli.objects;

import java.awt.Point;
import static java.lang.Math.sqrt;
import java.util.HashMap;
import pallopeli.CompassDirection;

public class Piece {
    private int x; // location relative to the board
    private int y; // location relative to the board
    private int size; // pixels
    private Point anchor;
    
    private boolean wall;
    
    private HashMap<CompassDirection, Piece> neighbors; 
    
    private HashMap<CompassDirection, Point> corners;

    public Piece(int x, int y, boolean wall, int sizeOfObjects) {
        this.x = x;
        this.y = y;
        this.wall = wall;
        this.size = sizeOfObjects;
        this.neighbors = new HashMap<>();
        this.anchor = new Point(x * sizeOfObjects, y * sizeOfObjects);


        this.setCorners();
    
    }
    public void turnIntoWall() {
        this.wall = true;
    }     
 
    public boolean hasBall(Ball ball) {
        int leftBorder = anchor.x - ball.getRadius();
        int rightBorder = anchor.x + this.size + ball.getRadius();
        
        if (leftBorder <= ball.getCoordinateX() 
                && ball.getCoordinateX() <= rightBorder
                && anchor.y <= ball.getCoordinateY()
                && ball.getCoordinateY() <= anchor.y + size) {
            return true;
        } 
        int topBorder = anchor.x - ball.getRadius();
        int bottomBorder = anchor.x + this.size + ball.getRadius();        
        
        if (topBorder <= ball.getCoordinateY() 
                && ball.getCoordinateY() <= bottomBorder
                && anchor.x <= ball.getCoordinateX()
                && ball.getCoordinateX() <= anchor.x + size) {
            return true;
        } 
        
        if (anchor.distance(ball.getCurrentPosition()) <= ball.getRadius()) {
            return true;
        }
        Point cornerNorthEast = new Point(anchor.x + size, anchor.y);
        if (cornerNorthEast.distance(ball.getCurrentPosition()) <= ball.getRadius()) {
            return true;
        }

        Point cornerSouthEast = new Point(anchor.x + size, anchor.y + size);
        if (cornerSouthEast.distance(ball.getCurrentPosition()) <= ball.getRadius()) {
            return true;
        }      
        Point cornerSouthWest = new Point(anchor.x, anchor.y + size);
        if (cornerSouthWest.distance(ball.getCurrentPosition()) <= ball.getRadius()) {
            return true;
        }  
        return false;
    }
 
    // this method is used to build walls during the game
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
    

    
    // helper methods
    protected void setCorners() {
        this.corners = new HashMap<>();
        this.corners.put(CompassDirection.NORTHEAST, new Point((x + 1) * size, y * size));
        this.corners.put(CompassDirection.SOUTHEAST, new Point((x + 1) * size, (y + 1) * size));
        this.corners.put(CompassDirection.SOUTHWEST, new Point(x * size, (y + 1) * size));
        this.corners.put(CompassDirection.NORTHWEST, new Point(x * size, y * size));
    }
    
    @Override
    public String toString() {
        if (this.wall) {
            return "(" + this.x + "," + this. y + "): is-wall, coordinates: " + this.anchor;
        }
        return "(" + this.x + "," + this. y + "): no-wall, coordinates: " + this.anchor;
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
    
    public Point getAnchor() {
        return this.anchor;
    }
    
    public int getCenterCoordinateX() {
        return this.x * this.size + this.size / 2;
    }
    public int getCenterCoordinateY() {
        return this.y * this.size + this.size / 2;
    }    
            
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    // trash
    
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
    
    public CompassDirection getDirectionWhereToBounce(int ballX, int ballY) {
        // this code will be imporved... i am aware that currenlty it's very ugly :(
        int topLeftCornerX = this.size * this.x;
        int topLeftCornerY = this.size * this.y;
        int topRightCornerX  = topLeftCornerX + this.size;
        int topRightCornerY = topLeftCornerY;
        int bottomLeftCornerX = topLeftCornerX;
        int bottomLeftCornerY = topLeftCornerY + this.size;
        int bottomRightCornerX = topRightCornerX;
        int bottomRightCornerY = bottomLeftCornerY;
                
        if (ballX - topRightCornerX == ballY - topRightCornerY) {
            return CompassDirection.NORTHEAST;
        } else if (ballX - topLeftCornerX == ballY - topLeftCornerY) {
            return CompassDirection.NORTHWEST;
        } else if (ballY < topLeftCornerY) {
            if (ballX < topLeftCornerX && topLeftCornerX - ballX > topLeftCornerY - ballY) {
                return CompassDirection.WEST;
            } else if (ballX > topRightCornerX && ballX - topRightCornerX > topLeftCornerY - ballY) {
                return CompassDirection.EAST;
            }
            return CompassDirection.NORTH;
        } else if (ballY > bottomLeftCornerY) {
            if (ballX < bottomLeftCornerX && bottomLeftCornerX - ballX > bottomLeftCornerY - ballY) {
                return CompassDirection.WEST;
            } else if (ballX > bottomRightCornerX && ballX - bottomRightCornerX > bottomRightCornerY - ballY) {
                return CompassDirection.EAST;
            }                        
            return CompassDirection.SOUTH;
        } else if (ballX < topLeftCornerX) {
            if (ballY < topLeftCornerY && topLeftCornerY - ballY > topLeftCornerX - ballX) {
                return CompassDirection.NORTH;
            } else if (ballY > bottomLeftCornerY && ballY - bottomLeftCornerY > bottomLeftCornerX - ballX) {
                return CompassDirection.SOUTH;
            }              
            return CompassDirection.WEST;
        } else if (ballX > topRightCornerX) {
            if (ballY < topRightCornerY && topRightCornerY - ballY > ballX - topRightCornerX) {
                return CompassDirection.NORTH;
            } else if (ballY > bottomRightCornerY && ballX - bottomRightCornerX > ballY - bottomRightCornerY) {
                return CompassDirection.SOUTH;
            }              
            return CompassDirection.EAST;
        }
        return null;
    }



    
    
    
    
}
