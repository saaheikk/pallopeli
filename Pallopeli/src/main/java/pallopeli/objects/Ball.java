package pallopeli.objects;

import java.util.ArrayList;
import java.util.Random;
import pallopeli.BuildingDirection;
import pallopeli.CompassDirection;
import pallopeli.collisionphysics.Collision;
import pallopeli.collisionphysics.CollisionDetector;

public class Ball {
    private int x; // horizontal location
    private int y; // vertical location
    private int dx; // horizontal speed
    private int dy; // vertical speed
    private int radius;
    private CollisionDetector collisionDetector;
    
    private int endOfTraceX; // ball "remembers where it came from"
    private int endOfTraceY; // ball "remembers where it came from"
    


    public Ball(int sizeOfObjects) {
        this.radius = sizeOfObjects / 2;
        this.collisionDetector = new CollisionDetector();
    }
    
    public void setStartingPoint(Board board) {
        this.x = (board.getWidth() * board.getSizeOfPieces()) / 2;
        this.y = (board.getHeight() * board.getSizeOfPieces()) / 2;
    }
    
    public void setSpeed(int dx, int dy) {
        if (dx > 0 && dy > 0 && dx < 2 * this.radius && dy < 2 * this.radius) {
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

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public int getRadius() {
        return radius;
    }
    

    
    public void moveOneStepForward() {
        this.endOfTraceX = this.x;
        this.endOfTraceY = this.y;
        this.x += this.dx;
        this.y += this.dy;        
    }
    
    public void move(Board board) {
        this.moveOneStepForward();
        System.out.println(this);
        Collision collision = this.collisionDetector.checkForEarliestCollisionAlongTrace(this, board);
        if (collision == null) {
            
            System.out.println("This step has no collision");
            return;
        } else {
            this.x = collision.getX();
            this.y = collision.getY();        
            if (collision.getDirection() == BuildingDirection.HORIZONTAL) {
                this.dy *= -1;
            } else if (collision.getDirection() == BuildingDirection.VERTICAL) {
                this.dx *= -1;
            }
            System.out.println("Collision detected!");
            
        }
        
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
    
    

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getEndOfTraceX() {
        return endOfTraceX;
    }

    public int getEndOfTraceY() {
        return endOfTraceY;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
    @Override
    public String toString() {
        return "My location: (" + this.x + "," + this.y + 
                "), speed: (" + this.dx + "," + this.dy + 
                "), and end of trace: (" + this.endOfTraceX + "," + this.endOfTraceY + ")";
    }

}
