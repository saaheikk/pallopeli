package pallopeli.collisionphysics;

import java.util.ArrayList;
import pallopeli.BuildingDirection;
import pallopeli.CompassDirection;
import pallopeli.objects.Ball;
import pallopeli.objects.Board;
import pallopeli.objects.Piece;

public class CollisionDetector {
    
    public CollisionDetector() {
    }
    
    public Collision checkForEarliestCollisionAlongTrace(Ball ball, Board board) {
        ArrayList<Piece> wallPiecesNearToBall = board.getWallPiecesNearby(ball.getX(), ball.getY(), 
                30);
        
        
        ArrayList<Collision> collisions = new ArrayList<>();
       

        for (Piece p : wallPiecesNearToBall) {
            int leftBorder = p.getX() * p.getSize() - ball.getRadius();
            int topBorder = p.getY() * p.getSize() - ball.getRadius();
            int rightBorder = p.getX() * p.getSize() + p.getSize() + ball.getRadius();
            int bottomBorder = p.getY() * p.getSize() + p.getSize() + ball.getRadius();
            
            int collisionOnTopBorder = this.xCoordinateOfCollisionOnHorizontalSegment(ball, topBorder, leftBorder, rightBorder);
            if (collisionOnTopBorder != -100) {
                collisions.add(new Collision(collisionOnTopBorder, topBorder, BuildingDirection.HORIZONTAL));

            }
            int collisionOnRightBorder = this.yCoordinateOfCollisionOnVerticalSegment(ball, rightBorder, topBorder, bottomBorder);
            if (collisionOnRightBorder != -100) {
                collisions.add(new Collision(rightBorder, collisionOnRightBorder, BuildingDirection.VERTICAL));
            }
            int collisionOnBottomBorder = this.xCoordinateOfCollisionOnHorizontalSegment(ball, bottomBorder, leftBorder, rightBorder);
            if (collisionOnBottomBorder != -100) {
                collisions.add(new Collision(collisionOnBottomBorder, bottomBorder, BuildingDirection.HORIZONTAL));                
            }
            int collisionOnLeftBorder = this.yCoordinateOfCollisionOnVerticalSegment(ball, leftBorder, topBorder, bottomBorder);
            if (collisionOnLeftBorder != -100) {
                collisions.add(new Collision(leftBorder, collisionOnLeftBorder, BuildingDirection.VERTICAL));                
            } 
        }
        if (collisions.isEmpty()) {
            return null; // no collisions
        }
        return this.getEarliestCollision(ball, collisions);   
        
    }
        
       
    

    public int yCoordinateOfCollisionOnVerticalSegment(Ball ball, int segmentX, int segmentStartY, int segmentEndY) {
        // this method returns the y-coordinate of collision or -100 if collision does not happen
        // so the actual collision point is (lineX, [return value of this method])

        if (ball.getX() < segmentX && ball.getEndOfTraceX() < segmentX) {
            return -100; // ball moves on the left side of lineX
        }
        if (ball.getX() > segmentX && ball.getEndOfTraceX() > segmentX) {
            return -100; // ball moves on the right side of lineX
        }
        if (ball.getX() == ball.getEndOfTraceX()) {
            return -100; // no horizontal speed
        }
        
        // now that we know that ball intersects lineX and we can make some calculations     
        float triangleX = (float) ball.getDx();
        float triangleY = (float) ball.getDy();
        
        float smallTriangleX = (float) (segmentX - ball.getEndOfTraceX());
        float smallTriangleY = (triangleY * smallTriangleX) / triangleX;
        
        int collisionY = ball.getEndOfTraceY() + (int) (smallTriangleY);
        
        if (segmentStartY < collisionY && collisionY < segmentEndY) {
            return collisionY; // actual collision with segment
        }
        return -100;
    }
    
    public int xCoordinateOfCollisionOnHorizontalSegment(Ball ball, 
            int segmentY, int segmentStartX, int segmentEndX) {
        // this method returns the x-coordinate of collision or -1000 if collision does not happen
        // so the actual collision point is ([return value of this method], lineY)

        if (ball.getY() < segmentY && ball.getEndOfTraceY() < segmentY) {
            return -100; // ball moves above lineY
        }
        if (ball.getY() > segmentY && ball.getEndOfTraceY() > segmentY) {
            return -100; // ball moves under lineY
        }
        if (ball.getX() == ball.getEndOfTraceX()) {
            return -100; // no vertical speed
        }
        
        // now that we know that ball intersects lineY and we can make some calculations     
        float triangleX = (float) ball.getDx();
        float triangleY = (float) ball.getDy();
        
        float smallTriangleY = (float) (segmentY - ball.getEndOfTraceY());
        float smallTriangleX = (triangleX * smallTriangleY) / triangleY;
        
        int collisionX = ball.getEndOfTraceY() + (int) (smallTriangleX);
        if (segmentStartX < collisionX && collisionX < segmentEndX) {
            return collisionX; // actual collision with segment
        }
        return -100;        
    }
        
    private Collision getEarliestCollision(Ball ball, ArrayList<Collision> collisions) {
        // idea is to "reset coordinates" - new origin at the end of trace

        int minimumDistanceToEndOfTrace = 1000; // 1000 must be safe enough
        Collision earliest = null;

        for (Collision c : collisions) {           
            int distanceToEndOfTrace = Math.abs(c.getX() - ball.getEndOfTraceX());
            if (distanceToEndOfTrace < minimumDistanceToEndOfTrace) {
                minimumDistanceToEndOfTrace = distanceToEndOfTrace;
                earliest = c;
            }
        }
        return earliest;
    }
    

}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
   


    
