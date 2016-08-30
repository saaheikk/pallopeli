package pallopeli.collisionphysics;

import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import pallopeli.SimpleDirection;
import pallopeli.CompassDirection;
import pallopeli.objects.Ball;
import pallopeli.objects.Board;
import pallopeli.objects.Piece;

/**
 * CollisionDetector detects for any occurred Collision over the latest step of Ball.
 * @author saara
 */

public class CollisionDetector {
    
    public CollisionDetector() {
    }
    /**
     * CollisionDetector's main method.
     * This method checks if Ball has collided on wall over the latest step.
     * @param ball Ball that have to bounce according to the Board's current state.
     * @param board Board where Ball moves.
     * @return The earliest proper collision point (as Collision).
     */
    
    public Collision checkForEarliestProperCollisionAlongTrace(Ball ball, Board board) {
        ArrayList<Piece> wallPiecesNearToBall = board.getWallPiecesNearby(ball.getPreviousPosition(), 50);
        ArrayList<Collision> collisions = new ArrayList<>();

        for (Piece p : wallPiecesNearToBall) {
            System.out.println("Wallpiece detected nearby: " + p);

            int leftBorder = p.getCornerPoint(CompassDirection.NORTHWEST).x - ball.getRadius();
            int topBorder = p.getCornerPoint(CompassDirection.NORTHWEST).y - ball.getRadius();
            int rightBorder = p.getCornerPoint(CompassDirection.SOUTHEAST).x + ball.getRadius();
            int bottomBorder = p.getCornerPoint(CompassDirection.SOUTHEAST).y + ball.getRadius();
            System.out.println("Left border: " + leftBorder + ", topBorder: "  + topBorder + ", right border: " + rightBorder + ", bottom border: " + bottomBorder);

            System.out.println("Collisions out:");
            
//             FIX BORDERS HERE!!!
            
            Point collisionOnTopBorder = this.collisionOnHorizontalSegment(ball, topBorder, leftBorder, rightBorder);
            if (collisionOnTopBorder != null) {
                System.out.println(collisionOnTopBorder);
                collisions.add(new Collision(collisionOnTopBorder, SimpleDirection.HORIZONTAL));
            }
            Point collisionOnRightBorder = this.collisionOnVerticalSegment(ball, rightBorder, topBorder, bottomBorder);
            if (collisionOnRightBorder != null) {
                collisions.add(new Collision(collisionOnRightBorder, SimpleDirection.VERTICAL));
                System.out.println(collisionOnRightBorder);
            }
            Point collisionOnBottomBorder = this.collisionOnHorizontalSegment(ball, bottomBorder, leftBorder, rightBorder);
            if (collisionOnBottomBorder != null) {
                collisions.add(new Collision(collisionOnBottomBorder, SimpleDirection.HORIZONTAL));    
                System.out.println(collisionOnBottomBorder);
            }
            Point collisionOnLeftBorder = this.collisionOnVerticalSegment(ball, leftBorder, topBorder, bottomBorder);
            if (collisionOnLeftBorder != null) {
                collisions.add(new Collision(collisionOnLeftBorder, SimpleDirection.VERTICAL));            
                System.out.println(collisionOnLeftBorder);
            } 
            System.out.println("");
        }
        if (collisions.isEmpty()) {
            return null; // no collisions
        }
        return this.getEarliestProperCollision(ball, collisions);   
        
    } 
    /**
     * Helper method.
     * @param ball
     * @param segmentX
     * @param segmentStartY
     * @param segmentEndY
     * @return The Point where Ball has crossed the given segment over its latest step.
     */

    public Point collisionOnVerticalSegment(Ball ball, int segmentX, int segmentStartY, int segmentEndY) {
        // this method returns the collision point on vertical segment or null if no collision happens

        if (ball.getCurrentPosition().x < segmentX && ball.getPreviousPosition().x < segmentX) {
            return null; // ball moves on the left side of segmentX
        }
        if (ball.getCurrentPosition().x > segmentX && ball.getPreviousPosition().x > segmentX) {
            return null; // ball moves on the right side of segmentX
        }
        if (ball.getDx() == 0) {
            return null; // no horizontal speed
        }

        // now that we know that ball intersects segmentX, we can make some calculations     
        double triangleX = (double) ball.getDx();
        double triangleY = (double) ball.getDy();
        
        double smallTriangleX = (double) (segmentX - ball.getPreviousPosition().x);     
        double smallTriangleY = (triangleY * smallTriangleX) / triangleX;
        
        int collisionY = ball.getPreviousPosition().y + (int) (smallTriangleY);
        
        if (segmentStartY <= collisionY && collisionY <= segmentEndY) {
            return new Point(segmentX, collisionY); // actual collision with segment (including ending points)
        }
        return null;
        
    }
    
    /**
     * Helper method.
     * @param ball
     * @param segmentY
     * @param segmentStartX
     * @return The Point where Ball has crossed the given segment over its latest step.

     */
    
    public Point collisionOnHorizontalSegment(Ball ball, int segmentY, int segmentStartX, int segmentEndX) {
        // this method returns the collision point on vertival segment or null if no collision happens

        if (ball.getCurrentPosition().y < segmentY && ball.getPreviousPosition().y < segmentY) {
            System.out.println("above segmentY");  
            return null; // ball moves above segmentY

        }
        if (ball.getCurrentPosition().y > segmentY && ball.getPreviousPosition().y > segmentY) {
            System.out.println("under segmentY");              
            return null; // ball moves under segmentY
        }
        if (ball.getDy() == 0) {
            return null; // no vertical speed
        }
        
        // now that we know that ball intersects segmentY, we can make some calculations     
        float triangleX = (float) ball.getDx();
        float triangleY = (float) ball.getDy();
        
        float smallTriangleY = (float) (segmentY - ball.getPreviousPosition().y);
        float smallTriangleX = (triangleX * smallTriangleY) / triangleY;
        
        int collisionX = ball.getPreviousPosition().x + (int) (smallTriangleX);
        if (segmentStartX <= collisionX && collisionX <= segmentEndX) {
            return new Point(collisionX, segmentY); // actual collision with segment (including ending points)
        }
        System.out.println("no proper collision");          
        return null;        
    }
    
    /**
     * Helper method that checks which collision in given list is the earliest proper one.
     * @param ball
     * @param collisions
     * @return Collision that is used to reset parameters of Ball.
     */   
    public Collision getEarliestProperCollision(Ball ball, ArrayList<Collision> collisions) {
        int minimumDistanceToEndOfTrace = 1000; // 1000 must be safe enough
        Collision earliest = null;

        for (Collision c : collisions) {           
            int distanceToEndOfTrace = Math.abs(c.getCoordinateX() - ball.getPreviousPosition().x);
            if (distanceToEndOfTrace < minimumDistanceToEndOfTrace) {
                // check for the case where ball has just bounced but previousPosition still collides!
                if (!c.getCollisionPosition().equals(ball.getPreviousPosition())) { 
                    minimumDistanceToEndOfTrace = distanceToEndOfTrace;
                    earliest = c;                    
                }
            }
        }
        return earliest;
    }
    
    // trash
    
//    
//    public Point collisionPointAtVerticalLine(Line2D line, Ball ball) {
//        if (line.getX1() != line.getX2()) {
//            return null; // do not accept other than vertical lines
//        }
//        Point collisionPoint = null;
//        Line2D traceOfBall = new Line2D.Float(ball.getPreviousPosition(), ball.getCurrentPosition());
//        if (line.intersectsLine(traceOfBall)) {
//            double triangleX = (double) ball.getDx();
//            double triangleY = (double) ball.getDy();
//
//            double smallTriangleX = (double) (line.getX1() - ball.getPreviousPosition().x);
//            double smallTriangleY = (triangleY * smallTriangleX) / triangleX;
//            
//            int collisionX = (int) line.getX1();
//            int collisionY = ball.getPreviousPosition().y + (int) (smallTriangleY);
//            collisionPoint = new Point(collisionX, collisionY);
//        }
//        return collisionPoint;
//    }
//    public Point collisionPointAtHorizontalLine(Line2D line, Ball ball) {
//        if (line.getY1() != line.getY2()) {
//            return null; // do not accept other than horizontal lines
//        }
//        Point collisionPoint = null;
//        Line2D traceOfBall = new Line2D.Float(ball.getPreviousPosition(), ball.getCurrentPosition());
//        if (line.intersectsLine(traceOfBall)) {
//            float triangleX = (float) ball.getDx();
//            float triangleY = (float) ball.getDy();
//
//            float smallTriangleY = (float) (line.getY1() - ball.getPreviousPosition().y);
//            float smallTriangleX = (triangleX * smallTriangleY) / triangleY;
//
//            int collisionX = ball.getPreviousPosition().x + (int) (smallTriangleX);
//            int collisionY = (int) line.getY1();
//            collisionPoint = new Point(collisionX, collisionY);
//        }
//        return collisionPoint;
//    }     
    
//    public Collision alternativeCheckForEarliestProperCollisionAlongTrace(Ball ball, Board board) {
//        ArrayList<Piece> wallPiecesNearToBall = board.getWallPiecesNearby(ball.getPreviousPosition(), 50);
//
//        ArrayList<Collision> collisions = new ArrayList<>();
//
//        for (Piece p : wallPiecesNearToBall) {
//            System.out.println("Wallpiece detected nearby: " + p);
//            for (Shape border : this.getActiveBordersForBall(ball, p)) {
//                System.out.println("Found active borders!!!!");
//                if (border.getClass().isInstance(new Line2D.Float())) {
//                    Point pointH = this.collisionPointAtHorizontalLine((Line2D) border, ball);
//                    collisions.add(new Collision(pointH, SimpleDirection.HORIZONTAL));
//                    Point pointV = this.collisionPointAtVerticalLine((Line2D) border, ball);
//                    collisions.add(new Collision(pointV, SimpleDirection.VERTICAL));
//                }
//            }    
//        }
//        if (collisions.isEmpty()) {
//            return null; // no collisions
//        }
//        return this.getEarliestProperCollision(ball, collisions);   
//    }    
    
    // this method could be in Piece
//    public ArrayList<Shape> getActiveBordersForBall(Ball ball, Piece piece) {
//        int r = ball.getRadius();
//        ArrayList<Shape> borders = new ArrayList<>();
//        if (piece.borderIsActive(CompassDirection.NORTH)) {
//            Line2D north = new Line2D.Float(piece.getCornerPoint(CompassDirection.NORTHWEST).x, piece.getCornerPoint(CompassDirection.NORTHWEST).y - r,
//                    piece.getCornerPoint(CompassDirection.NORTHEAST).x, piece.getCornerPoint(CompassDirection.NORTHEAST).y - r);
//            borders.add(north);
//        }
//        if (piece.borderIsActive(CompassDirection.EAST)) {
//            Line2D east = new Line2D.Float(piece.getCornerPoint(CompassDirection.NORTHEAST).x + r, piece.getCornerPoint(CompassDirection.NORTHEAST).y,
//                    piece.getCornerPoint(CompassDirection.SOUTHEAST).x + r, piece.getCornerPoint(CompassDirection.SOUTHEAST).y);
//            borders.add(east);
//        }
//        if (piece.borderIsActive(CompassDirection.SOUTH)) {
//            Line2D south = new Line2D.Float(piece.getCornerPoint(CompassDirection.SOUTHEAST).x, piece.getCornerPoint(CompassDirection.SOUTHEAST).y + r,
//                    piece.getCornerPoint(CompassDirection.SOUTHWEST).x, piece.getCornerPoint(CompassDirection.SOUTHWEST).y + r);
//            borders.add(south);
//        }
//        if (piece.borderIsActive(CompassDirection.WEST)) {
//            Line2D west = new Line2D.Float(piece.getCornerPoint(CompassDirection.SOUTHWEST).x - r, piece.getCornerPoint(CompassDirection.SOUTHWEST).y,
//                    piece.getCornerPoint(CompassDirection.NORTHWEST).x - r, piece.getCornerPoint(CompassDirection.NORTHWEST).y);
//            borders.add(west);
//        }
//        // add corners later
//        return borders;
//    }
    
}

