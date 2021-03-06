package pallopeli.objects;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;
import pallopeli.SimpleDirection;
import pallopeli.CompassDirection;
import pallopeli.collisionphysics.Collision;
import pallopeli.collisionphysics.CollisionDetector;

/**
 * Ball represents the ball in game and its main task is to move itself on board so that its movement is fairly smooth and it bounces from pieces that are wall. 
 * @author saara
 */

public class Ball {
    private Point currentPosition;
    private Point previousPosition;
    private int dx; // horizontal speed
    private int dy; // vertical speed   
    private int radius;
    private CollisionDetector collisionDetector;
    /**
     * Constructor for a new Ball.
     * @param sizeOfObjects 
     */
    public Ball(int sizeOfObjects) {
        this.radius = sizeOfObjects / 2;
        this.collisionDetector = new CollisionDetector();
    }
    /**
     * Sets ball at the middle point of given board, draws a starting speed randomly and sets the imaginary previous position depending on the drawn speed.
     * @param board Board where Ball is set on.
     */
    public void setBallOnBoard(Board board) {
        int x = (board.getWidth() * board.getSizeOfPieces()) / 2;
        int y = (board.getHeight() * board.getSizeOfPieces()) / 2;
        this.currentPosition = new Point(x, y);
        this.drawSpeed();
        this.previousPosition = new Point(x - this.dx, y - this.dy);        
    }
    /**
     * The main method of Ball; relocates Ball on given Board according to its speed.
     * @param board  Board where Ball moves. 
     */      
    public void moveOnBoard(Board board) {
        this.moveOneStepForward();        
        while (this.liesOnWall(board)) {
            Collision collision = this.collisionDetector.checkForEarliestProperCollisionAlongTrace(this, board);
            if (collision == null) {            
                System.out.println("ERROR!!!!!!!");
                return;
            } else {
                this.resetAfterCollision(collision);
            }            

        }          
    }

    /**
     * Resets the parameters of Ball after given Collision.
     * @param collision 
     */
    public void resetAfterCollision(Collision collision) {
        int legalTranslationX = collision.getCoordinateX() - this.previousPosition.x;
        int legalTranslationY = collision.getCoordinateY() - this.previousPosition.y;

        int illegalTranslationX = this.currentPosition.x - collision.getCoordinateX();
        int illegalTranslationY = this.currentPosition.y - collision.getCoordinateY();

        if (collision.getReflectingDirection() == SimpleDirection.VERTICAL) {  
            Point previousPositionReset = new Point(collision.getCoordinateX() + legalTranslationX, collision.getCoordinateY() - legalTranslationY);
            Point currentPositionReset = new Point(collision.getCoordinateX() - illegalTranslationX, collision.getCoordinateY() + illegalTranslationY);
            this.previousPosition.setLocation(previousPositionReset);
            this.currentPosition.setLocation(currentPositionReset);
            this.dx *= -1;
        } else if (collision.getReflectingDirection() == SimpleDirection.HORIZONTAL) {
            Point previousPositionReset = new Point(collision.getCoordinateX() - legalTranslationX, collision.getCoordinateY() + legalTranslationY);
            Point currentPositionReset = new Point(collision.getCoordinateX() + illegalTranslationX, collision.getCoordinateY() - illegalTranslationY);
            this.previousPosition.setLocation(previousPositionReset);
            this.currentPosition.setLocation(currentPositionReset);                
            this.dy *= -1;
        } else if (collision.getReflectingDirection() == SimpleDirection.DIAGONAL) {
            // FIXED BUT NOT TESTED
            Point previousPositionReset = new Point(collision.getCoordinateX() + legalTranslationX, collision.getCoordinateY() + legalTranslationY);
            Point currentPositionReset = new Point(collision.getCoordinateX() - illegalTranslationX, collision.getCoordinateY() - illegalTranslationY);
            this.previousPosition.setLocation(previousPositionReset);
            this.currentPosition.setLocation(currentPositionReset);

            this.dx *= -1;
            this.dy *= -1;
        }
    }
    
    /**
     * Helper method that gives Ball a random speed (within the speed limits).
     */
    public void drawSpeed() {
        Random random = new Random();
        int horizontalSpeed = 1 + random.nextInt(this.radius);
        int verticalSpeed = 1 + random.nextInt(this.radius);
        int signDx = random.nextInt(2);
        int signDy = random.nextInt(2);
        if (signDx == 1) {
            horizontalSpeed *= -1;
        }
        if (signDy == 1) {
            verticalSpeed *= -1;
        }
        this.setSpeed(horizontalSpeed, verticalSpeed);        
    }    
    /**
     * Helper method that simply translates the position parameters according to the speed of Ball.
     */
    public void moveOneStepForward() {
        this.previousPosition.translate(dx, dy);
        this.currentPosition.translate(dx, dy);
    }
    
    @Override
    public String toString() {
        return "My current position: (" + this.currentPosition.x + "," + this.currentPosition.y +
                "), speed vector: (" + this.dx + "," + this.dy + 
                "), and previous location: (" + this.previousPosition.x + "," + this.previousPosition.y + ")";
    } 
    /**
     * Tracks if circle of Ball intersects any wall piece.
     * @param board
     * @return True is ball collides with wall.
     */
    public boolean liesOnWall(Board board) {
        boolean liesOnWall = false;
        for (Piece p : board.getWallPiecesNearby(currentPosition, 50)) {
            if (p.hasBall(this)) {
                liesOnWall = true;
            }
        }
        return liesOnWall;
    }      
    
    // getter & setters 
    public void setSpeed(int dx, int dy) {
        if (dx != 0 && dy != 0 && Math.abs(dx) <= this.radius && Math.abs(dy) <= this.radius) {
            this.dx = dx;
            this.dy = dy;
        }
    }

    public int getRadius() {
        return radius;
    }
    
    public int getCoordinateX() {
        return this.currentPosition.x;
    } 
    public int getCoordinateY() {
        return this.currentPosition.y;         
    } 
    
    public Point getCurrentPosition() {
        return currentPosition;
    }

    public Point getPreviousPosition() {
        return previousPosition;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }
    public CollisionDetector getCollisionDetector() {
        return collisionDetector;
    }    

    public void setCurrentPosition(Point currentPosition) {
        this.currentPosition = currentPosition;
    }

    public void setPreviousPosition(Point previousPosition) {
        this.previousPosition = previousPosition;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }  
    
}
