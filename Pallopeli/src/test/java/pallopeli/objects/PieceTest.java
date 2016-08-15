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
    public void constructorSetsLocationXCorrectly() {
        piece = new Piece(5, 8, true, 30);
        assertTrue("", piece.getX() == 5);    
    }    
    @Test
    public void constructorSetsLocationYCorrectly() {
        piece = new Piece(10, 12, true, 30);
        assertTrue("", piece.getY() == 12);    
    }
        @Test
    public void constructorSetsSizeCorrectly() {
        piece = new Piece(2, 0, true, 30);
        assertTrue("", piece.getSize() == 30);    
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
        int centerX = piece.getCenterCoordinateX();
        assertTrue("", centerX == 105);    
    }       
    @Test
    public void getCenterXWorksForOddSize() {
        piece = new Piece(3, 3, false, 25);
        int centerX = piece.getCenterCoordinateX();
        assertTrue("", centerX == 87);    
    } 
    @Test
    public void getCenterYWorksForEvenSize() {
        piece = new Piece(3, 3, false, 30);
        int centerY = piece.getCenterCoordinateY();
        assertTrue("", centerY == 105);    
    } 
    @Test
    public void getCenterYWorksForOddSize() {
        piece = new Piece(3, 3, false, 25);
        int centerY = piece.getCenterCoordinateY();
        assertTrue("", centerY == 87);    
    } 
}
