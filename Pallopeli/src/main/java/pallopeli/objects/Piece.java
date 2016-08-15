package pallopeli.objects;

import java.awt.Point;
import static java.lang.Math.sqrt;
import pallopeli.CompassDirection;

public class Piece {
    private int x; // location relative to the board
    private int y; // location relative to the board
    private int size; // pixels
    private Point anchor;
    
    private boolean wall;
    
    private Piece northNeighbor;
    private Piece westNeighbor;
    private Piece southNeighbor;
    private Piece eastNeighbor;  

    public Piece(int x, int y, boolean wall, int sizeOfObjects) {
        this.x = x;
        this.y = y;
        this.wall = wall;
        this.size = sizeOfObjects;
        this.anchor = new Point(x * sizeOfObjects, y * sizeOfObjects);
    }
    public void turnIntoWall() {
        this.wall = true;
    }     
 
    
    
    
    
    
    
    // this method is used to build walls during the game
    public boolean turnNeighborIntoWall(CompassDirection compassDirection) {
        if (compassDirection == CompassDirection.NORTH) {
            return this.turnNeighborIntoWall(this.northNeighbor);
        } else if (compassDirection == CompassDirection.EAST) {
            return this.turnNeighborIntoWall(this.eastNeighbor);            
        } else if (compassDirection == CompassDirection.SOUTH) {
            return this.turnNeighborIntoWall(this.southNeighbor);            
        } else if (compassDirection == CompassDirection.WEST) {
            return this.turnNeighborIntoWall(this.westNeighbor);            
        }
        return false;
    }
    
    protected boolean turnNeighborIntoWall(Piece neighbor) {
        if (neighbor == null) {
            return false;
        } else if (neighbor.isWall()) {
            return false;
        } else {
            neighbor.turnIntoWall();
            return true;
        }
    }
    
    public boolean isWall() {
        return this.wall;
    }
    


    public void setNorthNeighbor(Piece northNeighbor) {
            this.northNeighbor = northNeighbor;  
    }

    public void setWestNeighbor(Piece westNeighbor) {
            this.westNeighbor = westNeighbor;           
    }

    public void setSouthNeighbor(Piece southNeighbor) {
            this.southNeighbor = southNeighbor;           
    }

    public void setEastNeighbor(Piece eastNeighbor) {
            this.eastNeighbor = eastNeighbor;           
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
            
    @Override
    public String toString() {
        if (this.wall) {
            return "(" + this.x + "," + this. y + "): is-wall, coordinates: " + this.anchor;
        }
        return "(" + this.x + "," + this. y + "): no-wall, coordinates: " + this.anchor;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public boolean isSurroundedByWallPieces() {
        // returns true for pieces that have no neighbors!!!
        if (this.northNeighbor == null || this.northNeighbor.isWall()) {
            if (this.eastNeighbor == null || this.eastNeighbor.isWall()) {
                if (this.southNeighbor == null || this.southNeighbor.isWall()) {
                    if (this.southNeighbor == null || this.southNeighbor.isWall()) {
                        return true;
                    }
                }
            }
        }
        return false; // returns true for pieces that have no neighbors!!!
    }
    

    public double getDistanceToAPoint(int x, int y) {
        double middleX = (double) this.size * this.x + this.size / 2;
        double middleY = (double) this.size * this.y + this.size / 2;
        return Math.sqrt((middleX - x) * (middleX - x) + (middleY - y) * (middleY - y));
    }
    
    // this method has problem with corners! --> no tests
//    public boolean hasBall(Ball ball) {       
//        int upperLeftCornerX = this.size * this.x;
//        int upperLeftCornerY = this.size * this.y;        
//        boolean leftLimit = ball.getX() > (upperLeftCornerX - ball.getRadius());
//        boolean rightLimit = ball.getX() < (upperLeftCornerX + this.size + ball.getRadius());
//        boolean topLimit = ball.getY() > (upperLeftCornerY - ball.getRadius());
//        boolean bottomLimit = ball.getY() < (upperLeftCornerY + this.size + ball.getRadius());                          
//        if (leftLimit && rightLimit && topLimit && bottomLimit) {
//            return true;
//        }
//        return false;
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
