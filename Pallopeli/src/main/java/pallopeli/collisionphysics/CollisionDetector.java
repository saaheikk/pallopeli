package pallopeli.collisionphysics;

import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.HashMap;
import pallopeli.SimpleDirection;
import pallopeli.CompassDirection;
import pallopeli.MovementType;
import pallopeli.objects.Ball;
import pallopeli.objects.Board;
import pallopeli.objects.Border;
import pallopeli.objects.BorderLine;
import pallopeli.objects.Piece;

/**
 * CollisionDetector detects the occurred Collision over the latest step of Ball and decides which one of them determines where Ball bounces.
 * @author saara
 */

public class CollisionDetector {
    
    public CollisionDetector() {
    }
    /**
     * CollisionDetector's main method.
     * By using other methods of CollisionDetector, it determines which kind of Collision is returned to Ball.
     * @param ball Ball whose movement is being tracked.
     * @param board Board where Ball moves.
     * @return Right kind of Collision.
     */
    public Collision checkForEarliestProperCollisionAlongTrace(Ball ball, Board board) {
        ArrayList<Piece> wallPiecesNearToBall = board.getWallPiecesNearby(ball.getPreviousPosition(), 50);
        ArrayList<Collision> collisionsDetected = this.getAllCollisions(wallPiecesNearToBall, ball);
        if (collisionsDetected.isEmpty()) {
            System.out.println("Array list null");
            return null;
        }
        return this.getTheDeterminativeCollision(ball, collisionsDetected);

    }       
    /**
     * Returns a right kind of Collision, given Ball and a list of it's Collisions over the latest step.
     * @param ball Ball that might has Collisions along its trace.
     * @param collisions List of Collision.
     * @return Collision for Ball to use.
     */
    
    public Collision getTheDeterminativeCollision(Ball ball, ArrayList<Collision> collisions) {       
        double minimumDistanceToEndOfTrace = 1000; // 1000 must be safe enough
        Point earliest = null;
        HashMap<SimpleDirection, Integer> reflectingDirectionsArised = new HashMap<>(); // list of directions?
        reflectingDirectionsArised.put(SimpleDirection.HORIZONTAL, 0);
        reflectingDirectionsArised.put(SimpleDirection.VERTICAL, 0);        

        for (Collision c : collisions) {  
            double distanceToEndOfTrace = Math.abs(c.getCollisionPoint().distance(ball.getPreviousPosition()));
            if (distanceToEndOfTrace <= minimumDistanceToEndOfTrace) {
                // check for the case where ball has just bounced but previousPosition still collides!               
                if (c.getType() == MovementType.IN) {
                    minimumDistanceToEndOfTrace = distanceToEndOfTrace;
                    earliest = c.getCollisionPoint();
                    reflectingDirectionsArised.put(c.getReflectingDirection(), 1);
                }
            }
        }
        if (earliest == null) {
            return null;
        }
        return new Collision(earliest, this.decideReflectingDirection(reflectingDirectionsArised), MovementType.IN);
    }  
    /**
     * Decides the reflect direction for the Collision that will be returned.
     * Crucial when Ball collides at inner corner point.
     * @param reflectingDirections HashMap containing HORIZONTAL and VERTICAL as keys.
     * @return Direction.
     */
    public SimpleDirection decideReflectingDirection(HashMap<SimpleDirection, Integer> reflectingDirections) {
        if (reflectingDirections.get(SimpleDirection.HORIZONTAL) == 0) {
            return SimpleDirection.VERTICAL;
        } else if (reflectingDirections.get(SimpleDirection.VERTICAL) == 0) {
            return SimpleDirection.HORIZONTAL;
        } else {
            return SimpleDirection.DIAGONAL;
        }
    }
    
    /**
     * Returns the Collision of Ball on given BorderLine and null if no collision occurs.
     * @param borderLine BorderLine being examined. 
     * @param ball Ball being examined.
     * @return Collision that has occurred of null if no Collision occurs.
     */
    public Collision collisionAtBorderLine(BorderLine borderLine, Ball ball) {
        int coef = borderLine.getBouncingDirection();
        Point pointH = this.collisionPointAtHorizontalLine(borderLine.getBorderShape(), ball);
        if (pointH != null) {
            boolean ballIsGettingOut = (coef * (ball.getPreviousPosition().y - borderLine.getLineCoordinate()) <= 0);
            if (ballIsGettingOut) {
                return new Collision(pointH, SimpleDirection.HORIZONTAL, MovementType.OUT);
            } else {
                return new Collision(pointH, SimpleDirection.HORIZONTAL, MovementType.IN);                
            }                            
        }
        Point pointV = this.collisionPointAtVerticalLine(borderLine.getBorderShape(), ball);
        if (pointV != null) {
            boolean ballIsGettingOut = (coef * (ball.getPreviousPosition().x - borderLine.getLineCoordinate()) <= 0);            
            if (ballIsGettingOut) {
                return new Collision(pointV, SimpleDirection.VERTICAL, MovementType.OUT);
            } else {
                return new Collision(pointV, SimpleDirection.VERTICAL, MovementType.IN);                
            }        
        } 
        return null;
    }
        
            
    
    /**
     * Calculates the coordinates of Collision on vertical line.
     * Purely geometric calculations.
     * @param line Line2D being examined.
     * @param ball Ball whose potential collisions are being tracked.
     * @return Point of Collision or null if no collision occurs.
     */
    public Point collisionPointAtVerticalLine(Line2D line, Ball ball) {
        if (line.getX1() != line.getX2()) {
            return null; // do not accept other than vertical lines
        }
        Point collisionPoint = null;
        Line2D traceOfBall = new Line2D.Float(ball.getPreviousPosition(), ball.getCurrentPosition());
        if (line.intersectsLine(traceOfBall)) {
            double triangleX = (double) ball.getDx();
            double triangleY = (double) ball.getDy();
            double smallTriangleX = (double) (line.getX1() - ball.getPreviousPosition().x);

            double smallTriangleY = (triangleY * smallTriangleX) / triangleX;
          
            int collisionX = (int) line.getX1();
            int collisionY = ball.getPreviousPosition().y + (int) (smallTriangleY);
            collisionPoint = new Point(collisionX, collisionY);
        }
        return collisionPoint;
    }

    
    /**
     * Calculates the coordinates of Collision on horizontal line.
     * Purely geometric calculations.
     * @param line Line2D being examined.
     * @param ball Ball whose potential collisions are being tracked.
     * @return Point of Collision or null if no collision occurs.
     */
    public Point collisionPointAtHorizontalLine(Line2D line, Ball ball) {
        if (line.getY1() != line.getY2()) {
            return null; // do not accept other than horizontal lines
        }
        Point collisionPoint = null;
        Line2D traceOfBall = new Line2D.Float(ball.getPreviousPosition(), ball.getCurrentPosition());
        if (line.intersectsLine(traceOfBall)) {
            float triangleX = (float) ball.getDx();
            float triangleY = (float) ball.getDy();

            float smallTriangleY = (float) (line.getY1() - ball.getPreviousPosition().y);
            float smallTriangleX = (triangleX * smallTriangleY) / triangleY;

            int collisionX = ball.getPreviousPosition().x + (int) (smallTriangleX);
            int collisionY = (int) line.getY1();
            collisionPoint = new Point(collisionX, collisionY);
        }
        return collisionPoint;
    }   
    /**
     * Returns all Collisions with the Pieces on given list.
     * @param wallPiecesNearToBall Pieces that are tested for Collisions.
     * @param ball Ball being examined.
     * @return ArrayList of all Collisions.
     */
    public ArrayList<Collision> getAllCollisions(ArrayList<Piece> wallPiecesNearToBall, Ball ball) {
        ArrayList<Collision> collisions = new ArrayList<>();

        for (Piece p : wallPiecesNearToBall) {
            for (CompassDirection direction : p.getBorders().keySet()) {
                Border border = p.getBorders().get(direction);
                if (border != null) {
                    if (border.isActive()) {
//                        System.out.println(border);
                        BorderLine borderLine = (BorderLine) border;
                        Collision candidate = this.collisionAtBorderLine(borderLine, ball);
//                        System.out.println(candidate);
                        if (candidate != null) {
                            collisions.add(candidate);
                        }
                    }
                }
            }
        } 
        return collisions;
    }
}

