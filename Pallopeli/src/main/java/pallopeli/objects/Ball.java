package pallopeli.objects;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;
import pallopeli.BuildingDirection;
import pallopeli.CompassDirection;
import pallopeli.collisionphysics.Collision;
import pallopeli.collisionphysics.CollisionDetector;

public class Ball {
    private Point currentPosition;
    private Point previousPosition;
    private int dx; // horizontal speed
    private int dy; // vertical speed   
        
    private int radius;
    private CollisionDetector collisionDetector;
    
//    private int x; // horizontal location
//    private int y; // vertical location
 
//    private int endOfTraceX; // ball "remembers where it came from"
//    private int endOfTraceY; // ball "remembers where it came from"
 


    public Ball(int sizeOfObjects) {
        this.radius = sizeOfObjects / 2;
        this.collisionDetector = new CollisionDetector();
    }
    
    public void setBallOnBoard(Board board) {
        int x = (board.getWidth() * board.getSizeOfPieces()) / 2;
        int y = (board.getHeight() * board.getSizeOfPieces()) / 2;
        this.currentPosition = new Point(x, y);
        this.drawSpeed();
        this.previousPosition = new Point(x - this.dx, y - this.dy);        
    }
    
    public void setSpeed(int dx, int dy) {
        if (dx > 0 && dy > 0 && dx <= this.radius && dy <= this.radius) {
            this.dx = dx;
            this.dy = dy;
        }
    }
    
    public void drawSpeed() {
        Random random = new Random();
        int horizontalSpeed = 1 + random.nextInt(this.radius);
        int verticalSpeed = 1 + random.nextInt(this.radius);
        this.setSpeed(horizontalSpeed, verticalSpeed);        
    }


    public int getRadius() {
        return radius;
    }
    
    

    
    public void moveOneStepForward() {
        this.previousPosition.translate(dx, dy);
        this.currentPosition.translate(dx, dy);
    }
    
    public void move(Board board) {
        this.moveOneStepForward();
        System.out.println(this + "\n");
        Collision collision = this.collisionDetector.checkForEarliestCollisionAlongTrace(this, board);
        if (collision == null) {
            
            System.out.println("This step has no collision");
            return;
        } else {
            System.out.println("Collision detected!");
            System.out.println(collision);
            // set current position temporary at collision point
            this.currentPosition = collision.getCollisionPosition();
            
            
            this.previousPosition = collision.getCollisionPosition();
            
            int smallTriangleX = collision.getCoordinateX() - this.getCoordinateX();
            int smallTriangleY = collision.getCoordinateY() - this.getCoordinateY();
            
            int leftoverDx = this.dx - smallTriangleX;
            int leftoverDy = this.dy - smallTriangleY;
            
            if (collision.getReflectingDirection() == BuildingDirection.VERTICAL) {  
                this.currentPosition.translate(-leftoverDx, leftoverDy);
                this.dx *= -1;
            } else if (collision.getReflectingDirection() == BuildingDirection.HORIZONTAL) {
                this.currentPosition.translate(leftoverDx, -leftoverDy);                
                this.dy *= -1;
            }
            
            // now we should have the following:
            // current position = calculated correlctly according collision point & reflecting direction
            // speed = updated according reflecting direction
            // previous position = at collision point ---> might appear probelms this is very close to current position...
            
            

            
        }
        
    }
    
    public int getCoordinateX() {
        return this.currentPosition.x;
    } 
    public int getCoordinateY() {
        return this.currentPosition.y;
    } 
    
    
    public void bounce(CompassDirection compassDirection) {
        if (compassDirection == CompassDirection.EAST || compassDirection == CompassDirection.WEST) { 
            this.dx *= -1;
        } else if (compassDirection == CompassDirection.NORTH || compassDirection == CompassDirection.SOUTH) {
            this.dy *= -1;
        } else if (compassDirection == CompassDirection.NORTHEAST || compassDirection == CompassDirection.SOUTHWEST) {
            this.dx *= -1;
            this.dy *= -1;            
        } else if (compassDirection == CompassDirection.NORTHWEST || compassDirection == CompassDirection.SOUTHEAST) {
            this.dx *= -1;
            this.dy *= -1;            
        }
    }


    public CollisionDetector getCollisionDetector() {
        return collisionDetector;
    }


    @Override
    public String toString() {
        return "My current position: (" + this.currentPosition.x + "," + this.currentPosition.y +
                "), speed vector: (" + this.dx + "," + this.dy + 
                "), and previous location: (" + this.previousPosition.x + "," + this.previousPosition.y + ")";
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
