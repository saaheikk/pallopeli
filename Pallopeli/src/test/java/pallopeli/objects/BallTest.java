package pallopeli.objects;

import java.awt.Point;
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
    public void constructorSetsCollisionDetector() {
        ball = new Ball(10);
        assertFalse("", ball.getCollisionDetector() == null);
    } 
    @Test 
    public void startingPointIsSetCorrectly() {
        Board board = new Board(10, 10, 30);
        ball = new Ball(30);
        ball.setBallOnBoard(board);
        Point expectedStartingPoint = new Point(150, 150);
        assertTrue("", ball.getCurrentPosition().equals(expectedStartingPoint));
    }   
    
    @Test 
    public void startingPointCannotLieOnWallPiece() {
        Board board = new Board(5, 5, 30);
        ball = new Ball(30);
        ball.setBallOnBoard(board);
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

    @Test
    public void startingPointIsSetCorrectlyInSmallBoard() {
        ball = new Ball(10);
        Board b = new Board(6, 5, 10);
        ball.setBallOnBoard(b);
        Point expectedStartingPoint = new Point(30, 25);
        assertTrue("", ball.getCurrentPosition().equals(expectedStartingPoint));
    }    
    @Test
    public void startingPointIsSetCorrectlyInLargeBoard() {
        ball = new Ball(10);
        Board b = new Board(30, 20, 10);
        ball.setBallOnBoard(b);
        Point expectedStartingPoint = new Point(150, 100);
        assertTrue("", ball.getCurrentPosition().equals(expectedStartingPoint));        
    }     
    
    @Test
    public void speedIsSetCorrectly() {
        this.setBallWithLegalSpeed();
        assertEquals("5, 3", ball.getDx() + ", " + ball.getDy());
    }     
    @Test
    public void xSpeedCantBeTooSmall() {
        ball = new Ball(10);
        ball.setSpeed(0, 4);        
        assertEquals("0, 0",  ball.getDx() + ", " + ball.getDy());
    }      
    @Test
    public void ySpeedCantBeTooSmall() {
        ball = new Ball(10);
        ball.setSpeed(4, 0);        
        assertEquals("0, 0",  ball.getDx() + ", " + ball.getDy());
    }  
    @Test
    public void xSpeedCantBeTooLarge() {
        ball = new Ball(10);
        ball.setSpeed(6, 4);        
        assertEquals("0, 0",  ball.getDx() + ", " + ball.getDy());
    }      
    @Test
    public void ySpeedCantBeTooLarge() {
        ball = new Ball(10);
        ball.setSpeed(4, 6);        
        assertEquals("0, 0",  ball.getDx() + ", " + ball.getDy());
    }   
    @Test
    public void ballDrawsLegalSpeedForItself() {
        ball = new Ball(10);
        ball.drawSpeed();
        boolean dxIsLegal = (0 != ball.getDx() && Math.abs(ball.getDx()) <=10);
        boolean dyIsLegal = (0 != ball.getDy() && Math.abs(ball.getDy()) <=10);
        
        assertTrue("", dxIsLegal && dyIsLegal);
    }   
    
    @Test
    public void previousPositionIsSetAccordingToSpeed() {
        Board board = new Board(10, 10, 30);
        ball = new Ball(30);
        ball.setBallOnBoard(board);
        Point expectedPreviousPosition = new Point(150 - ball.getDx(), 150 - ball.getDy());
        assertTrue("", ball.getPreviousPosition().equals(expectedPreviousPosition));                
    }

    @Test
    public void ballMovesOneStepForward() {
        Board b = new Board(10, 10, 30);
        ball = new Ball(30);
        ball.setBallOnBoard(b);
        ball.moveOneStepForward();
        Point expectedPosition = new Point(150 + ball.getDx(), 150 + ball.getDy());
        assertTrue("", ball.getCurrentPosition().equals(expectedPosition));
    }
    
    @Test
    public void liesOnWallWorksIfBallDoesNotLieOnWall() {
        Board board = new Board(10, 10, 30);
        ball = new Ball(30);
        ball.setCurrentPosition(new Point(80, 170));
        assertFalse("", ball.liesOnWall(board));
    }
    @Test
    public void liesOnWallWorksIfBallLiesOnWall() {
        Board board = new Board(10, 10, 30);
        ball = new Ball(30);
        ball.setCurrentPosition(new Point(80, 20));
        assertTrue("", ball.liesOnWall(board));
    }    
    @Test
    public void liesOnWallWorksIfBallTouchesWall() {
        Board board = new Board(10, 10, 30);
        ball = new Ball(30);
        ball.setCurrentPosition(new Point(80, 45));
        assertTrue("", ball.liesOnWall(board));
    }  
    
    // tests for moving by using collision detector - important tests!
    public void createNormalVerticalCollision() {
        Board board = new Board(10, 10, 30);
        ball = new Ball(30);
        ball.setCurrentPosition(new Point(47, 100));
        ball.setPreviousPosition(new Point(50, 103));
        ball.setDx(-3);
        ball.setDy(-3);
        ball.moveOnBoard(board);
    }
    
    
    @Test
    public void newPositionIsSetCorrectlyWhenCollisionOnVerticalWall() {
        this.createNormalVerticalCollision();
        assertEquals(new Point(46, 97), ball.getCurrentPosition());
    }
    @Test
    public void traceIsSetCorrectlyWhenCollisionOnVerticalWall() {
        this.createNormalVerticalCollision();
        assertEquals(new Point(43, 100), ball.getPreviousPosition());
    }    
    @Test
    public void newSpeedXIsSetCorrectlyWhenCollisionOnVerticalWall() {
        this.createNormalVerticalCollision();
        assertEquals(3, ball.getDx());
    }      
    @Test
    public void newSpeedYIsSetCorrectlyWhenCollisionOnVerticalWall() {
        this.createNormalVerticalCollision();
        assertEquals(-3, ball.getDy());
    } 
    
    public void createNormalHorizontalCollision() {
        Board board = new Board(10, 10, 30);
        ball = new Ball(30);
        ball.setCurrentPosition(new Point(100, 47));
        ball.setPreviousPosition(new Point(103, 50));
        ball.setDx(-3);
        ball.setDy(-3);
        ball.moveOnBoard(board);        
    }
    
    
    @Test
    public void newPositionIsSetCorrectlyWhenCollisionOnHorizontalWall() {
        this.createNormalHorizontalCollision();
        assertEquals(new Point(97, 46), ball.getCurrentPosition());
    }    

    @Test
    public void traceIsSetCorrectlyWhenCollisionOnHorizontalWall() {
        this.createNormalHorizontalCollision();
        assertEquals(new Point(100, 43), ball.getPreviousPosition());
    }    
    @Test
    public void newSpeedXIsSetCorrectlyWhenCollisionOnHorizontalWall() {
        this.createNormalHorizontalCollision();
        assertEquals(-3, ball.getDx());
    }      
    @Test
    public void newSpeedYIsSetCorrectlyWhenCollisionOnHorizontalWall() {
        this.createNormalHorizontalCollision();
        assertEquals(3, ball.getDy());
    }     
    
    public void createCollisionOnVerticalEdge() {
        Board board = new Board(10, 10, 30);
        ball = new Ball(30);
        ball.setCurrentPosition(new Point(48, 100));
        ball.setPreviousPosition(new Point(51, 103));
        ball.setDx(-3);
        ball.setDy(-3);
        ball.moveOnBoard(board);        
    }
    
    @Test
    public void newPositionIsSetCorrectlyWhenCollisionOnVerticalEdge() {
        this.createCollisionOnVerticalEdge();
        assertEquals(new Point(45, 97), ball.getCurrentPosition());
    }     
    @Test
    public void traceIsSetCorrectlyWhenCollisionOnVerticalEdge() {
        this.createCollisionOnVerticalEdge();
        assertEquals(new Point(42, 100), ball.getPreviousPosition());
    }    
    @Test
    public void SpeedXIsSetCorrectlyWhenCollisionOnVerticalEdge() {
        this.createCollisionOnVerticalEdge();
        assertEquals(3, ball.getDx());
    } 
    @Test
    public void speedYIsSetCorrectlyWhenCollisionOnVerticalEdge() {
        this.createCollisionOnVerticalEdge();
        assertEquals(-3, ball.getDy());
    }     
    
    public void createCollisionOnHorizontalEdge() {
        Board board = new Board(10, 10, 30);
        ball = new Ball(30);
        ball.setCurrentPosition(new Point(100, 48));
        ball.setPreviousPosition(new Point(103, 51));
        ball.setDx(-3);
        ball.setDy(-3);
        ball.moveOnBoard(board);       
    }    
    
    @Test
    public void newPositionIsSetCorrectlyWhenCollisionOnHorizontalEdge() {
        this.createCollisionOnHorizontalEdge();
        assertEquals(new Point(97, 45), ball.getCurrentPosition());
    }    
    @Test
    public void traceIsSetCorrectlyWhenCollisionOnHorizontalEdge() {
        this.createCollisionOnHorizontalEdge();
        assertEquals(new Point(100, 42), ball.getPreviousPosition());
    } 
    @Test
    public void speedXIsSetCorrectlyWhenCollisionOnHorizontalEdge() {
        this.createCollisionOnHorizontalEdge();
        assertEquals(-3, ball.getDx());
    }     
    @Test
    public void speedYIsSetCorrectlyWhenCollisionOnHorizontalEdge() {
        this.createCollisionOnHorizontalEdge();
        assertEquals(3, ball.getDy());
    }     
    
    
    public void createVerticalCollisionAtPointWhereBordersTouch() {
        Board board = new Board(10, 10, 30);
        ball = new Ball(30);
        ball.setCurrentPosition(new Point(47, 77));
        ball.setPreviousPosition(new Point(50, 80));
        ball.setDx(-3);
        ball.setDy(-3);
        ball.moveOnBoard(board);        
    }    
    @Test
    public void newPositionIsSetCorrectlyWhenVerticalCollisionAtPointWhereBordersTouch() {
        this.createVerticalCollisionAtPointWhereBordersTouch();
        assertEquals(new Point(46, 74), ball.getCurrentPosition());
    }    
    @Test
    public void traceIsSetCorrectlyWhenVerticalCollisionAtPointWhereBordersTouch() {
        this.createVerticalCollisionAtPointWhereBordersTouch();
        assertEquals(new Point(43, 77), ball.getPreviousPosition());
    } 
    @Test
    public void speedXIsSetCorrectlyWhenVerticalCollisionAtPointWhereBordersTouch() {
        this.createVerticalCollisionAtPointWhereBordersTouch();
        assertEquals(3, ball.getDx());
    }     
    @Test
    public void speedYIsSetCorrectlyWhenVerticalCollisionAtPointWhereBordersTouch() {
        this.createVerticalCollisionAtPointWhereBordersTouch();
        assertEquals(-3, ball.getDy());
    }       

    public void createHorizontalCollisionAtPointWhereBordersTouch() {
        Board board = new Board(10, 10, 30);
        ball = new Ball(30);
        ball.setCurrentPosition(new Point(77, 47));
        ball.setPreviousPosition(new Point(80, 50));
        ball.setDx(-3);
        ball.setDy(-3);
        ball.moveOnBoard(board);        
    }    
//    @Test
//    public void newPositionIsSetCorrectlyWhenHorizontalCollisionAtPointWhereBordersTouch() {
//        this.createHorizontalCollisionAtPointWhereBordersTouch();
//        assertEquals(new Point(74, 46), ball.getCurrentPosition());
//    }    
//    @Test
//    public void traceIsSetCorrectlyWhenHorizontalCollisionAtPointWhereBordersTouch() {
//        this.createHorizontalCollisionAtPointWhereBordersTouch();
//        assertEquals(new Point(77, 43), ball.getPreviousPosition());
//    } 
//    @Test
//    public void speedXIsSetCorrectlyWhenHorizontalCollisionAtPointWhereBordersTouch() {
//        this.createHorizontalCollisionAtPointWhereBordersTouch();
//        assertEquals(-3, ball.getDx());
//    }     
//    @Test
//    public void speedYIsSetCorrectlyWhenHorizontcalCollisionAtPointWhereBordersTouch() {
//        this.createHorizontalCollisionAtPointWhereBordersTouch();
//        assertEquals(3, ball.getDy());
//    }       

    // corners!!
    
    
    
    @Test
    public void testResetting() {
        
    }    
    
//    @Test
//    public void toStringWorks() {
//        this.setBallWithLegalSpeed();
//        Board b = new Board(30, 20, 10);
//        ball.setBallOnBoard(b);
//        String s = "My current position: (155,103), speed vector: (5,3), and previous location: (150,100)";
//        assertEquals(s, ball.toString());
//    }     
    
    
    
    // tests for bouncing (earlier idea)
//    @Test
//    public void ballBouncesUpwards() {
//        this.setBallWithLegalSpeed();
//        int originalXSpeed = ball.getDx();
//        int originalYSpeed = ball.getDy();
//        ball.bounce(CompassDirection.NORTH);
//        boolean speedIsCorrect = (originalXSpeed == ball.getDx() && (originalYSpeed == -ball.getDy()));
//
//        assertTrue("", speedIsCorrect);
//    } 
//    @Test
//    public void ballBouncesDownwards() {
//        this.setBallWithLegalSpeed();
//        int originalXSpeed = ball.getDx();
//        int originalYSpeed = ball.getDy();
//        ball.bounce(CompassDirection.SOUTH);
//        boolean speedIsCorrect = (originalXSpeed == ball.getDx() && (originalYSpeed == -ball.getDy()));
//        assertTrue("", speedIsCorrect);
//    }     
//    @Test
//    public void ballBouncesRightwards() {
//        this.setBallWithLegalSpeed();
//        int originalXSpeed = ball.getDx();
//        int originalYSpeed = ball.getDy();
//        ball.bounce(CompassDirection.EAST);
//        boolean speedIsCorrect = (originalXSpeed == -ball.getDx() && (originalYSpeed == ball.getDy()));
//        assertTrue("", speedIsCorrect);
//    }  
//    @Test
//    public void ballBouncesLeftwards() {
//        this.setBallWithLegalSpeed();
//        int originalXSpeed = ball.getDx();
//        int originalYSpeed = ball.getDy();
//        ball.bounce(CompassDirection.WEST);
//        boolean speedIsCorrect = (originalXSpeed == -ball.getDx() && (originalYSpeed == ball.getDy()));
//        assertTrue("", speedIsCorrect);
//    }      
//    @Test
//    public void ballIsAbleToBounceFromTopRightCornerOfAPiece() {
//        this.setBallWithLegalSpeed();
//        int originalXSpeed = ball.getDx();
//        int originalYSpeed = ball.getDy();
//        ball.bounce(CompassDirection.NORTHEAST);
//        boolean speedIsCorrect = (originalXSpeed == -ball.getDx() && (originalYSpeed == -ball.getDy()));
//        assertTrue("", speedIsCorrect);
//    }   
//    @Test    
//    public void ballIsAbleToBounceFromBottomRightCornerOfAPiece() {
//        this.setBallWithLegalSpeed();
//        int originalXSpeed = ball.getDx();
//        int originalYSpeed = ball.getDy();
//        ball.bounce(CompassDirection.SOUTHEAST);
//        boolean speedIsCorrect = (originalXSpeed == -ball.getDx() && (originalYSpeed == -ball.getDy()));
//        assertTrue("", speedIsCorrect);
//    }     
//    @Test    
//    public void ballIsAbleToBounceFromBottomLeftCornerOfAPiece() {
//        this.setBallWithLegalSpeed();
//        int originalXSpeed = ball.getDx();
//        int originalYSpeed = ball.getDy();
//        ball.bounce(CompassDirection.SOUTHWEST);
//        boolean speedIsCorrect = (originalXSpeed == -ball.getDx() && (originalYSpeed == -ball.getDy()));
//        assertTrue("", speedIsCorrect);
//    }    
//    @Test    
//    public void ballIsAbleToBounceFromTopLeftCornerOfAPiece() {
//        this.setBallWithLegalSpeed();
//        int originalXSpeed = ball.getDx();
//        int originalYSpeed = ball.getDy();
//        ball.bounce(CompassDirection.NORTHWEST);
//        boolean speedIsCorrect = (originalXSpeed == -ball.getDx() && (originalYSpeed == -ball.getDy()));
//        assertTrue("", speedIsCorrect);
//    }     
    
   
    
    
    // helper methods
         
    public void setBallWithLegalSpeed() {
        ball = new Ball(10);
        ball.setSpeed(5, 3);        
    }


}
