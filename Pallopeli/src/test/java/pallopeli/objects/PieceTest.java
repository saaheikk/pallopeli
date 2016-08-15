package pallopeli.objects;

import java.util.Random;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pallopeli.objects.Piece;

public class PieceTest {
    Piece piece;    

    public PieceTest() {

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
    public void constructorSetsBooleanWallTrueCorrectly() {
        piece = new Piece(3, 3, true, 30);
        assertTrue("", piece.isWall());    
    }
    
    @Test
    public void constructorSetsBooleanWallFalseCorrectly() {
        piece = new Piece(3, 3, false, 30);
        assertFalse("", piece.isWall());    
    }  
    
    @Test
    public void turnIntoWallWorksIfPieceIsNotYetWall() {
        piece = new Piece(3, 3, false, 30);
        piece.turnIntoWall();
        assertTrue("", piece.isWall());    
    } 
    
    @Test
    public void turnIntoWallWorksIfPieceIsWall() {
        piece = new Piece(3, 3, true, 30);
        piece.turnIntoWall();
        assertTrue("", piece.isWall());    
    }     
    
    @Test
    public void hasBallWorksIfBallTouchesThePiece() {
        piece = new Piece(3, 3, true, 30);
        Ball ballThatTouchesThePiece = new Ball(30);
        Random random = new Random(); // location of the ball is set randomly within the limits
        int randomX = 3 * 30 - 15 + random.nextInt(60 + 1);
        int randomY = 3 * 30 - 15 + random.nextInt(60 + 1);
        ballThatTouchesThePiece.setX(randomX);
        ballThatTouchesThePiece.setY(randomY);
        assertTrue("", piece.hasBall(ballThatTouchesThePiece));   
    }   
    @Test
    public void hasBallWorksIfBallDoesNotTouchThePiece() {
        piece = new Piece(3, 3, true, 30);
        Ball ballThatTouchesThePiece = new Ball(30);
        ballThatTouchesThePiece.setX(30);
        ballThatTouchesThePiece.setY(40);
        assertFalse("", piece.hasBall(ballThatTouchesThePiece));   
    }   
    @Test
    public void getDistanceToAPointWorks() {
        piece = new Piece(3, 3, true, 30);
        int x = 250;
        int y = 50;
        double middleX = (double) piece.getSize() * piece.getX() + piece.getSize() / 2;
        double middleY = (double) piece.getSize() * piece.getY() + piece.getSize() / 2;
        double distance = Math.sqrt((middleX - x)*(middleX - x) + (middleY - y)*(middleY - y));        
        assertTrue("", distance==piece.getDistanceToAPoint(x, y));    
    }
    
    
    
    @Test
    public void toStringWorksIfPieceIsWall() {
        piece = new Piece(3, 3, true, 30);
        assertEquals("(3,3) is-wall", piece.toString());    
    }  
    @Test
    public void toStringWorksIfPieceIsNotWall() {
        piece = new Piece(3, 3, false, 30);
        assertEquals("(3,3) no-wall", piece.toString());    
    }     
    @Test
    public void getCenterXWorksForEvenSize() {
        piece = new Piece(3, 3, false, 30);
        int centerX = piece.getCenterX();
        assertTrue("", centerX == 105);    
    }       
    @Test
    public void getCenterXWorksForOddSize() {
        piece = new Piece(3, 3, false, 25);
        int centerX = piece.getCenterX();
        assertTrue("", centerX == 87);    
    } 
    @Test
    public void getCenterYWorksForEvenSize() {
        piece = new Piece(3, 3, false, 30);
        int centerY = piece.getCenterY();
        assertTrue("", centerY == 105);    
    } 
    @Test
    public void getCenterYWorksForOddSize() {
        piece = new Piece(3, 3, false, 25);
        int centerY = piece.getCenterY();
        assertTrue("", centerY == 87);    
    } 
}
