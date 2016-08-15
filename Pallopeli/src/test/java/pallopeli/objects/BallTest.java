package pallopeli.objects;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pallopeli.CompassDirection;
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
        this.setBallWithLegalSpeed();
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
    @Test
    public void ballBouncesUpwards() {
        this.setBallWithLegalSpeed();
        int originalXSpeed = ball.getDx();
        int originalYSpeed = ball.getDy();
        ball.bounce(CompassDirection.NORTH);
        boolean speedIsCorrect = (originalXSpeed == ball.getDx() && (originalYSpeed == -ball.getDy()));

        assertTrue("", speedIsCorrect);
    } 
    @Test
    public void ballBouncesDownwards() {
        this.setBallWithLegalSpeed();
        int originalXSpeed = ball.getDx();
        int originalYSpeed = ball.getDy();
        ball.bounce(CompassDirection.SOUTH);
        boolean speedIsCorrect = (originalXSpeed == ball.getDx() && (originalYSpeed == -ball.getDy()));
        assertTrue("", speedIsCorrect);
    }     
    @Test
    public void ballBouncesRightwards() {
        this.setBallWithLegalSpeed();
        int originalXSpeed = ball.getDx();
        int originalYSpeed = ball.getDy();
        ball.bounce(CompassDirection.EAST);
        boolean speedIsCorrect = (originalXSpeed == -ball.getDx() && (originalYSpeed == ball.getDy()));
        assertTrue("", speedIsCorrect);
    }  
    @Test
    public void ballBouncesLeftwards() {
        this.setBallWithLegalSpeed();
        int originalXSpeed = ball.getDx();
        int originalYSpeed = ball.getDy();
        ball.bounce(CompassDirection.WEST);
        boolean speedIsCorrect = (originalXSpeed == -ball.getDx() && (originalYSpeed == ball.getDy()));
        assertTrue("", speedIsCorrect);
    }      
    @Test
    public void ballIsAbleToBounceFromTopRightCornerOfAPiece() {
        this.setBallWithLegalSpeed();
        int originalXSpeed = ball.getDx();
        int originalYSpeed = ball.getDy();
        ball.bounce(CompassDirection.NORTHEAST);
        boolean speedIsCorrect = (originalXSpeed == -ball.getDx() && (originalYSpeed == -ball.getDy()));
        assertTrue("", speedIsCorrect);
    }   
    @Test    
    public void ballIsAbleToBounceFromBottomRightCornerOfAPiece() {
        this.setBallWithLegalSpeed();
        int originalXSpeed = ball.getDx();
        int originalYSpeed = ball.getDy();
        ball.bounce(CompassDirection.SOUTHEAST);
        boolean speedIsCorrect = (originalXSpeed == -ball.getDx() && (originalYSpeed == -ball.getDy()));
        assertTrue("", speedIsCorrect);
    }     
    @Test    
    public void ballIsAbleToBounceFromBottomLeftCornerOfAPiece() {
        this.setBallWithLegalSpeed();
        int originalXSpeed = ball.getDx();
        int originalYSpeed = ball.getDy();
        ball.bounce(CompassDirection.SOUTHWEST);
        boolean speedIsCorrect = (originalXSpeed == -ball.getDx() && (originalYSpeed == -ball.getDy()));
        assertTrue("", speedIsCorrect);
    }    
    @Test    
    public void ballIsAbleToBounceFromTopLeftCornerOfAPiece() {
        this.setBallWithLegalSpeed();
        int originalXSpeed = ball.getDx();
        int originalYSpeed = ball.getDy();
        ball.bounce(CompassDirection.NORTHWEST);
        boolean speedIsCorrect = (originalXSpeed == -ball.getDx() && (originalYSpeed == -ball.getDy()));
        assertTrue("", speedIsCorrect);
    }     
    
    @Test 
    public void startingPointIsSetCorrectly() {
        Board board = new Board(10, 10, 30);
        ball = new Ball(30);
        ball.setStartingPoint(board);
        int x = ball.getX();
        int y = ball.getY();
        assertTrue("", (ball.getX() == 150 && ball.getY() == 150));
    }   
    @Test 
    public void startingPointCannotLieOnWallPiece() {
        Board board = new Board(5, 5, 30);
        ball = new Ball(30);
        ball.setStartingPoint(board);
        boolean aWallPieceHasBall = false;
        for (int h = 0; h < board.getHeight(); h++) {                       
            for (int w = 0; w < board.getWidth(); w++) {
                if (board.getPiece(w, h).isWall()) {
                    if (board.getPiece(w, h).hasBall(ball)) {
                        aWallPieceHasBall = true;
                    }                    
                }
            }
        }  
        assertFalse("", aWallPieceHasBall);
    }     
         
    public void setBallWithLegalSpeed() {
        ball = new Ball(10);
        ball.setSpeed(5, 6);        
    }


}
