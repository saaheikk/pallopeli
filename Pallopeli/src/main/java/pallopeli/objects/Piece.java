package pallopeli.objects;

import java.awt.Graphics;
import java.awt.Image;
import static java.lang.Math.sqrt;
import javax.swing.ImageIcon;
import pallopeli.CompassDirection;

public class Piece {
    private int x; // location relative to the board
    private int y; // location relative to the board
    
    private int size; // pixels
    
    private boolean wall;
    
    private Piece northNeighbor;
    private Piece westNeighbor;
    private Piece southNeighbor;
    private Piece eastNeighbor;
    
    private final Image pieceImage = new ImageIcon("tavallinenpala.png").getImage();
    private final Image wallImage = new ImageIcon("seinapala.png").getImage();     

    public Piece(int x, int y, boolean wall, int size) {
        this.x = x;
        this.y = y;
        this.wall = wall;
        this.size = size;
    }
    
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
    
    public void turnIntoWall() {
        this.wall = true;
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
    @Override
    public String toString() {
        if (this.wall) {
            return "is-wall";
        }
        return "no-wall";
    }
    
    public void draw(Graphics graphics) {
        if (wall) {
            graphics.drawImage(wallImage, size*x, size*y, null);         
        } else if (!wall) {
            graphics.drawImage(pieceImage, size*x, size*y, null);
        }       
    }
    
    public boolean hasBall(Ball ball) {
        int ballX = ball.getX();
        int ballY = ball.getY();
        int ballR = ball.getRadius();        
        int upperLeftCornerX = this.size * this.x;
        int upperLeftCornerY = this.size * this.y;        
        boolean leftLimit = ballX > (upperLeftCornerX - ballR);
        boolean rightLimit = ballX < (upperLeftCornerX + this.size + ballR);
        boolean topLimit = ballY > (upperLeftCornerY - ballR);
        boolean bottomLimit = ballY < (upperLeftCornerY + this.size + ballR);
                          
        if (leftLimit && rightLimit && topLimit && bottomLimit) {
            return true;
        }
        return false;
    }
    public double getDistanceToPoint(int x, int y) {
        double middleX = (double) this.size * this.x + this.size / 2;
        double middleY = (double) this.size * this.y + this.size / 2;
        return sqrt(Math.pow((middleX - x), 2) + Math.pow((middleY - y), 2));
    }
    
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
        } else if (ballX > topRightCornerX){
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
