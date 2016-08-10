package pallopeli;

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
    public void toStringWorksIfPieceIsWall() {
        piece = new Piece(3, 3, true, 30);
        assertEquals("is-wall", piece.toString());    
    }  
    @Test
    public void toStringWorksIfPieceIsNotWall() {
        piece = new Piece(3, 3, false, 30);
        assertEquals("no-wall", piece.toString());    
    }     
}
