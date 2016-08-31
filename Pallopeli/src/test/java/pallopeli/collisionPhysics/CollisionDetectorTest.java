package pallopeli.collisionPhysics;

import java.awt.Point;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pallopeli.SimpleDirection;
import pallopeli.objects.Ball;
import pallopeli.objects.Board;
import pallopeli.collisionphysics.CollisionDetector;
import pallopeli.collisionphysics.Collision;
import pallopeli.objects.Piece;

public class CollisionDetectorTest {
    CollisionDetector cd;
    
    public CollisionDetectorTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        cd = new CollisionDetector();
    }
    
    @After
    public void tearDown() {
    }

   
    public Ball setBallA() {
        Ball ball = new Ball(20);
        ball.setCurrentPosition(new Point (6, 5));
        ball.setPreviousPosition(new Point(3, 2));
        ball.setDx(3);
        ball.setDy(3);
        return ball;
    }
    public Ball setBallB() {
        Ball ball = new Ball(10);
        ball.setCurrentPosition(new Point (9, 6));
        ball.setPreviousPosition(new Point(11, 2));
        ball.setDx(-2);
        ball.setDy(4);
        return ball;
    }  
    
    @Test
    public void collisionPointOnVerticalLineReturnsTheRightPoint() {
        Ball ball = this.setBallA();
        Line2D line = new Line2D.Float(5, 2, 5, 6);
        Point expectedPoint = new Point(5, 4);
        assertEquals(expectedPoint, cd.collisionPointAtVerticalLine(line, ball));
    }
    @Test
    public void collisionPointOnHorizontalLineReturnsTheRightPoint() {
        Ball ball = this.setBallA();
        Line2D line = new Line2D.Float(1, 4, 6, 4);
        Point expectedPoint = new Point(5, 4);
        assertEquals(expectedPoint, cd.collisionPointAtHorizontalLine(line, ball));
    }
    

    
    @Test
    public void getAllCollisionsDoesNotReturnEmptyList() {
        Board b = new Board(10, 10, 20);
        Ball ball = new Ball(10);
        ball.setCurrentPosition(new Point (28, 44));
        ball.setPreviousPosition(new Point(31, 41));
        ball.setDx(-3);
        ball.setDy(3);
        ArrayList<Piece> wallPiecesNearToBall = b.getWallPiecesNearby(ball.getPreviousPosition(), 50);        
        ArrayList<Collision> collisions = cd.getAllCollisions(wallPiecesNearToBall, ball);
        assertFalse("", collisions.isEmpty());
    }
    
    // continue from this :)
    
    public Board setBoardForCases() {
        Board b = new Board(10, 10, 20);
        return b;
    }
    public Ball setEasyHorizontalCase() {
        Ball ball = new Ball(20);
        ball.setCurrentPosition(new Point (49, 29));
        ball.setPreviousPosition(new Point(52, 32));
        ball.setDx(-3);
        ball.setDy(-3); 
        return ball;
    }    
    
    
    @Test
    public void mainMethodReturnsRightCollisionInEasyHorizontalCase() {
        Board b = this.setBoardForCases();
        Ball ball = this.setEasyHorizontalCase();
        Point expectedCollisionPoint = new Point(50, 30);
        Collision detected = cd.checkForEarliestProperCollisionAlongTrace(ball, b);
        assertEquals(expectedCollisionPoint, detected.getCollisionPosition());
        assertEquals(SimpleDirection.HORIZONTAL, detected.getReflectingDirection());
    }
    public Ball setEasyVerticalCase() {
        Ball ball = new Ball(20);
        ball.setCurrentPosition(new Point (29, 61));
        ball.setPreviousPosition(new Point(31, 59));
        ball.setDx(-2);
        ball.setDy(2); 
        return ball;
    }     
    @Test
    public void mainMethodReturnsRightCollisionInEasyVerticalCase() {
        Board b = this.setBoardForCases();
        Ball ball = this.setEasyVerticalCase();
        Point expectedCollisionPoint = new Point(30, 60);
        Collision detected = cd.checkForEarliestProperCollisionAlongTrace(ball, b);
        assertEquals(expectedCollisionPoint, detected.getCollisionPosition());
        assertEquals(SimpleDirection.VERTICAL, detected.getReflectingDirection());
    }    
    
    @Test
    public void mainMethodReturnsRightCollisionInCornerCase() {
        Board b = this.setBoardForCases();
        Ball ball = new Ball(20);
        ball.setCurrentPosition(new Point (29, 29));
        ball.setPreviousPosition(new Point(32, 32));
        ball.setDx(-3);
        ball.setDy(-3);
        Point expectedCollisionPoint = new Point(30, 30);
        Collision detected = cd.checkForEarliestProperCollisionAlongTrace(ball, b);
        assertEquals(expectedCollisionPoint, detected.getCollisionPosition());
        assertEquals(SimpleDirection.DIAGONAL, detected.getReflectingDirection());
    } 
    @Test
    public void mainMethodReturnsRightCollisionInCaseOfTwoCollisions() {
        Board b = this.setBoardForCases();
        Ball ball = new Ball(20);
        ball.setCurrentPosition(new Point (33, 29));
        ball.setPreviousPosition(new Point(29, 33));
        ball.setDx(4);
        ball.setDy(-4);
        Point expectedCollisionPoint = new Point(32, 30);
        Collision detected = cd.checkForEarliestProperCollisionAlongTrace(ball, b);
        assertEquals(expectedCollisionPoint, detected.getCollisionPosition());
        assertEquals(SimpleDirection.HORIZONTAL, detected.getReflectingDirection());
    }     
//
//    @Test
//    public void collisionOnVerticalSegmentWorksIfBallHasFullyCrossedSegment() {
//        Ball ball = this.setBallA();
//        Point expectedCollisionPoint = new Point(4, 3);
//        assertEquals(expectedCollisionPoint, cd.collisionOnVerticalSegment(ball, 4, 1, 4));
//    }
//
//    @Test
//    public void collisionOnVerticalSegmentWorksIfBallsCurrentPositionLiesOnSegment() {
//        Ball ball = this.setBallA();
//        Point expectedCollisionPoint = new Point(6, 5);
//        assertEquals(expectedCollisionPoint, cd.collisionOnVerticalSegment(ball, 6, 3, 6));
//    } 
//    @Test
//    public void collisionOnVerticalSegmentWorksIfBallsPreviousPositionLiesOnSegment() {
//        Ball ball = this.setBallA();
//        Point expectedCollisionPoint = new Point(3, 2);
//        assertEquals(expectedCollisionPoint, cd.collisionOnVerticalSegment(ball, 3, 1, 3));
//    } 
//    @Test
//    public void collisionOnVerticalSegmentWorksIfBallHitsTheTopEnd() {
//        Ball ball = this.setBallA();
//        Point expectedCollisionPoint = new Point(5, 4);
//        assertEquals(expectedCollisionPoint, cd.collisionOnVerticalSegment(ball, 5, 4, 8));
//    } 
//    
//    @Test
//    public void collisionOnVerticalSegmentWorksIfBallHitsTheBottomEnd() {
//        Ball ball = this.setBallB();
//        Point expectedCollisionPoint = new Point(10, 4);
//        assertEquals(expectedCollisionPoint, cd.collisionOnVerticalSegment(ball, 10, 1, 4));
//    }  
//    
//    @Test
//    public void collisionOnVerticalSegmentWorksIfBallLiesAtTheTopEnd() {
//        Ball ball = this.setBallB();
//        Point expectedCollisionPoint = new Point(9, 6);
//        assertEquals(expectedCollisionPoint, cd.collisionOnVerticalSegment(ball, 9, 6, 10));
//    } 
//    @Test
//    public void collisionOnVerticalSegmentWorksIfBallLiesAtTheBottomEnd() {
//        Ball ball = this.setBallB();
//        Point expectedCollisionPoint = new Point(9, 6);
//        assertEquals(expectedCollisionPoint, cd.collisionOnVerticalSegment(ball, 9, 1, 6));
//    }   
//    
//    @Test
//    public void collisionOnVerticalSegmentWorksIfBallADoesNotCrossSegment() {
//        Ball ball = this.setBallA();
//        assertEquals(null, cd.collisionOnVerticalSegment(ball, 1, 6, 10));
//    }       
//    @Test
//    public void collisionOnVerticalSegmentWorksIfBallBDoesNotCrossSegment() {
//        Ball ball = this.setBallB();
//        assertEquals(null, cd.collisionOnVerticalSegment(ball, 11, 6, 10));
//    }     
//    
//    
//    @Test
//    public void collisionOnHorizontalSegmentWorksIfBallHasFullyCrossedSegment() {
//        Ball ball = this.setBallA();
//        Point expectedCollisionPoint = new Point(5, 4);
//        assertEquals(expectedCollisionPoint, cd.collisionOnHorizontalSegment(ball, 4, 1, 10));
//    }
//
//    @Test
//    public void collisionOnHorizontalSegmentWorksIfBallsCurrentPositionLiesOnSegment() {
//        Ball ball = this.setBallA();
//        Point expectedCollisionPoint = new Point(6, 5);
//        assertEquals(expectedCollisionPoint, cd.collisionOnHorizontalSegment(ball, 5, 3, 10));
//    } 
//    @Test
//    public void collisionOnHorizontalSegmentWorksIfBallsPreviousPositionLiesOnSegment() {
//        Ball ball = this.setBallA();
//        Point expectedCollisionPoint = new Point(3, 2);
//        assertEquals(expectedCollisionPoint, cd.collisionOnHorizontalSegment(ball, 2, 1, 4));
//    } 
//    @Test
//    public void collisionOnHorizontalSegmentWorksIfBallHitsTheRightEnd() {
//        Ball ball = this.setBallA();
//        Point expectedCollisionPoint = new Point(5, 4);
//        assertEquals(expectedCollisionPoint, cd.collisionOnHorizontalSegment(ball, 4, 0, 5));
//    } 
//    
//    @Test
//    public void collisionOnHorizontalSegmentWorksIfBallHitsTheLeftEnd() {
//        Ball ball = this.setBallB();
//        Point expectedCollisionPoint = new Point(9, 6);
//        assertEquals(expectedCollisionPoint, cd.collisionOnHorizontalSegment(ball, 6, 9, 10));
//    }  
//    
//    @Test
//    public void collisionOnHorizontalSegmentWorksIfBallLiesAtTheRightEnd() {
//        Ball ball = this.setBallB();
//        Point expectedCollisionPoint = new Point(9, 6);
//        assertEquals(expectedCollisionPoint, cd.collisionOnHorizontalSegment(ball, 6, 5, 9));
//    } 
//    @Test
//    public void collisionOnHorizontalSegmentWorksIfBallLiesAtTheLeftEnd() {
//        Ball ball = this.setBallB();
//        Point expectedCollisionPoint = new Point(9, 6);
//        assertEquals(expectedCollisionPoint, cd.collisionOnHorizontalSegment(ball, 6, 9, 15));
//    }   
//    
//    @Test
//    public void collisionOnHorizontalSegmentWorksIfBallADoesNotCrossSegment() {
//        Ball ball = this.setBallA();
//        assertEquals(null, cd.collisionOnHorizontalSegment(ball, 1, 6, 10));
//    }       
//    @Test
//    public void collisionOnHorizontalSegmentWorksIfBallBDoesNotCrossSegment() {
//        Ball ball = this.setBallB();
//        assertEquals(null, cd.collisionOnHorizontalSegment(ball, 11, 6, 10));
//    }     
//     
    


    
        
    
    


}
