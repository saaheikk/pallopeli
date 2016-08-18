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

    public Ball(int sizeOfObjects) {
        this.radius = sizeOfObjects / 2;
        this.collisionDetector = new CollisionDetector();
    }
    /**
     * Sets ball at the middle point of given board, draws a starting speed randomly and sets the imaginary previous position depending on the drawn speed.
     * @param board 
     */
    public void setBallOnBoard(Board board) {
        int x = (board.getWidth() * board.getSizeOfPieces()) / 2;
        int y = (board.getHeight() * board.getSizeOfPieces()) / 2;
        this.currentPosition = new Point(x, y);
        this.drawSpeed();
        this.previousPosition = new Point(x - this.dx, y - this.dy);        
    }
    /**
     * The main method of Ball; relocates the ball on given Board according to its speed.
     * @param board 
     */
    
    public void moveOnBoard(Board board) {
        this.moveOneStepForward();
        System.out.println(this + "\n");
                           
        Collision collision = this.collisionDetector.checkForEarliestProperCollisionAlongTrace(this, board);
        if (collision == null) {
            
            System.out.println("This step has no collision");
            return;
        } else {
            System.out.println("Collision detected!");
            System.out.println(collision);
            this.resetAfterCollision(collision);
            
            // check if ball collides after resetting...inner corners might cause this!
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
        }
        
        // check for case where moveOneStepForward does not kill this collision event
        if (collision.getCollisionPosition() == this.currentPosition) {
        }

    }
    
    /**
     * Helper method that gives Ball a random speed (within the speed limits).
     */
    public void drawSpeed() {
        Random random = new Random();
        int horizontalSpeed = 1 + random.nextInt(this.radius);
        int verticalSpeed = 1 + random.nextInt(this.radius);
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
    
    // getter & setters 

    public void setSpeed(int dx, int dy) {
        if (dx > 0 && dy > 0 && dx <= this.radius && dy <= this.radius) {
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
    
    
    
    
    
    
    
    
    
    // trash 
    
//    public void bounce(CompassDirection compassDirection) {
//        if (compassDirection == CompassDirection.EAST || compassDirection == CompassDirection.WEST) { 
//            this.dx *= -1;
//        } else if (compassDirection == CompassDirection.NORTH || compassDirection == CompassDirection.SOUTH) {
//            this.dy *= -1;
//        } else if (compassDirection == CompassDirection.NORTHEAST || compassDirection == CompassDirection.SOUTHWEST) {
//            this.dx *= -1;
//            this.dy *= -1;            
//        } else if (compassDirection == CompassDirection.NORTHWEST || compassDirection == CompassDirection.SOUTHEAST) {
//            this.dx *= -1;
//            this.dy *= -1;            
//        }
//    }








    

}
