package pallopeli.objects;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pallopeli.objects.Ball;

public class BallTest {
    Ball ball;
    
    public BallTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }


    @Test
    public void constructorSetsRadiusCorrectly() {
        ball = new Ball(10);
        assertEquals(5, ball.getRadius());
    } 

    @Test
    public void speedIsSetCorrectly() {
        ball = new Ball(10);
        ball.setSpeed(5, 6);
        assertEquals("5, 6", ball.getDx() + ", " + ball.getDy());
    }     
    @Test
    public void xSpeedCantBeTooSmall() {
        ball = new Ball(10);
        ball.setSpeed(0, 6);        
        assertEquals("0, 0",  ball.getDx() + ", " + ball.getDy());
    }      
    @Test
    public void ySpeedCantBeTooSmall() {
        ball = new Ball(10);
        ball.setSpeed(6, 0);        
        assertEquals("0, 0",  ball.getDx() + ", " + ball.getDy());
    }  
    @Test
    public void xSpeedCantBeTooLarge() {
        ball = new Ball(10);
        ball.setSpeed(20, 6);        
        assertEquals("0, 0",  ball.getDx() + ", " + ball.getDy());
    }      
    @Test
    public void ySpeedCantBeTooLarge() {
        ball = new Ball(10);
        ball.setSpeed(6, 20);        
        assertEquals("0, 0",  ball.getDx() + ", " + ball.getDy());
    }      
}
