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
        assertEquals(expectedCollisionPoint, detected.getCollisionPoint());
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
        assertEquals(expectedCollisionPoint, detected.getCollisionPoint());
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
        assertEquals(expectedCollisionPoint, detected.getCollisionPoint());
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
        assertEquals(expectedCollisionPoint, detected.getCollisionPoint());
        assertEquals(SimpleDirection.HORIZONTAL, detected.getReflectingDirection());
    }   
}
