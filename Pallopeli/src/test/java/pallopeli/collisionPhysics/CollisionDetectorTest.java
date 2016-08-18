package pallopeli.collisionPhysics;

import java.awt.Point;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pallopeli.objects.Ball;
import pallopeli.objects.Board;
import pallopeli.collisionphysics.CollisionDetector;
import pallopeli.collisionphysics.Collision;

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


    @Test
    public void collisionOnVerticalSegmentWorksIfBallHasFullyCrossedSegment() {
        Ball ball = this.setBallA();
        Point expectedCollisionPoint = new Point(4, 3);
        assertEquals(expectedCollisionPoint, cd.collisionOnVerticalSegment(ball, 4, 1, 4));
    }

    @Test
    public void collisionOnVerticalSegmentWorksIfBallsCurrentPositionLiesOnSegment() {
        Ball ball = this.setBallA();
        Point expectedCollisionPoint = new Point(6, 5);
        assertEquals(expectedCollisionPoint, cd.collisionOnVerticalSegment(ball, 6, 3, 6));
    } 
    @Test
    public void collisionOnVerticalSegmentWorksIfBallsPreviousPositionLiesOnSegment() {
        Ball ball = this.setBallA();
        Point expectedCollisionPoint = new Point(3, 2);
        assertEquals(expectedCollisionPoint, cd.collisionOnVerticalSegment(ball, 3, 1, 3));
    } 
    @Test
    public void collisionOnVerticalSegmentWorksIfBallHitsTheTopEnd() {
        Ball ball = this.setBallA();
        Point expectedCollisionPoint = new Point(5, 4);
        assertEquals(expectedCollisionPoint, cd.collisionOnVerticalSegment(ball, 5, 4, 8));
    } 
    
    @Test
    public void collisionOnVerticalSegmentWorksIfBallHitsTheBottomEnd() {
        Ball ball = this.setBallB();
        Point expectedCollisionPoint = new Point(10, 4);
        assertEquals(expectedCollisionPoint, cd.collisionOnVerticalSegment(ball, 10, 1, 4));
    }  
    
    @Test
    public void collisionOnVerticalSegmentWorksIfBallLiesAtTheTopEnd() {
        Ball ball = this.setBallB();
        Point expectedCollisionPoint = new Point(9, 6);
        assertEquals(expectedCollisionPoint, cd.collisionOnVerticalSegment(ball, 9, 6, 10));
    } 
    @Test
    public void collisionOnVerticalSegmentWorksIfBallLiesAtTheBottomEnd() {
        Ball ball = this.setBallB();
        Point expectedCollisionPoint = new Point(9, 6);
        assertEquals(expectedCollisionPoint, cd.collisionOnVerticalSegment(ball, 9, 1, 6));
    }   
    
    @Test
    public void collisionOnVerticalSegmentWorksIfBallADoesNotCrossSegment() {
        Ball ball = this.setBallA();
        assertEquals(null, cd.collisionOnVerticalSegment(ball, 1, 6, 10));
    }       
    @Test
    public void collisionOnVerticalSegmentWorksIfBallBDoesNotCrossSegment() {
        Ball ball = this.setBallB();
        assertEquals(null, cd.collisionOnVerticalSegment(ball, 11, 6, 10));
    }     
    
    
    @Test
    public void collisionOnHorizontalSegmentWorksIfBallHasFullyCrossedSegment() {
        Ball ball = this.setBallA();
        Point expectedCollisionPoint = new Point(5, 4);
        assertEquals(expectedCollisionPoint, cd.collisionOnHorizontalSegment(ball, 4, 1, 10));
    }

    @Test
    public void collisionOnHorizontalSegmentWorksIfBallsCurrentPositionLiesOnSegment() {
        Ball ball = this.setBallA();
        Point expectedCollisionPoint = new Point(6, 5);
        assertEquals(expectedCollisionPoint, cd.collisionOnHorizontalSegment(ball, 5, 3, 10));
    } 
    @Test
    public void collisionOnHorizontalSegmentWorksIfBallsPreviousPositionLiesOnSegment() {
        Ball ball = this.setBallA();
        Point expectedCollisionPoint = new Point(3, 2);
        assertEquals(expectedCollisionPoint, cd.collisionOnHorizontalSegment(ball, 2, 1, 4));
    } 
    @Test
    public void collisionOnHorizontalSegmentWorksIfBallHitsTheRightEnd() {
        Ball ball = this.setBallA();
        Point expectedCollisionPoint = new Point(5, 4);
        assertEquals(expectedCollisionPoint, cd.collisionOnHorizontalSegment(ball, 4, 0, 5));
    } 
    
    @Test
    public void collisionOnHorizontalSegmentWorksIfBallHitsTheLeftEnd() {
        Ball ball = this.setBallB();
        Point expectedCollisionPoint = new Point(9, 6);
        assertEquals(expectedCollisionPoint, cd.collisionOnHorizontalSegment(ball, 6, 9, 10));
    }  
    
    @Test
    public void collisionOnHorizontalSegmentWorksIfBallLiesAtTheRightEnd() {
        Ball ball = this.setBallB();
        Point expectedCollisionPoint = new Point(9, 6);
        assertEquals(expectedCollisionPoint, cd.collisionOnHorizontalSegment(ball, 6, 5, 9));
    } 
    @Test
    public void collisionOnHorizontalSegmentWorksIfBallLiesAtTheLeftEnd() {
        Ball ball = this.setBallB();
        Point expectedCollisionPoint = new Point(9, 6);
        assertEquals(expectedCollisionPoint, cd.collisionOnHorizontalSegment(ball, 6, 9, 15));
    }   
    
    @Test
    public void collisionOnHorizontalSegmentWorksIfBallADoesNotCrossSegment() {
        Ball ball = this.setBallA();
        assertEquals(null, cd.collisionOnHorizontalSegment(ball, 1, 6, 10));
    }       
    @Test
    public void collisionOnHorizontalSegmentWorksIfBallBDoesNotCrossSegment() {
        Ball ball = this.setBallB();
        assertEquals(null, cd.collisionOnHorizontalSegment(ball, 11, 6, 10));
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
    

}
