package pallopeli.objects;

import java.awt.Graphics;
import pallopeli.CompassDirection;

public class Ball {
    private int x;
    private int y;
    private int dx;
    private int dy;
    private int radius;

    public Ball(int sizeOfObjects) {
        if (sizeOfObjects > 5 && sizeOfObjects < 51) {
            this.radius = sizeOfObjects / 2;            
        }
    }
    
    public void setStartingPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public void setSpeed(int dx, int dy) {
        if (dx > 0 && dy > 0 && dx < 2 * this.radius && dy < 2 * this.radius) {
            this.dx = dx;
            this.dy = dy;            
        }
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
    
    
    public void move() {
        this.x += this.dx;
        this.y += this.dy;        
    }
    public void moveOneStepBackwards() {
        this.x -= this.dx;
        this.y -= this.dy;          
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
    
    public void draw(Graphics graphics) {
        graphics.fillOval(x, y, 2*radius, 2*radius);
    }
    
    
    
    

}
