package pallopeli.objects;

import java.awt.Point;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pallopeli.objects.Board;

public class BoardTest {
    Board board;
    
    public BoardTest() {
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
    public void constructorDoesNotAcceptTooSmallWidth() {
        board = new Board(4, 10, 10);
        assertEquals("", board.toString());
    }

    @Test
    public void constructorDoesNotAcceptTooSmallHeight() {
        board = new Board(10, 4, 10);
        assertEquals("", board.toString());
    }    
    @Test
    public void constructorDoesNotAcceptTooLargeWidth() {
        board = new Board(10, 31, 10);
        assertEquals("", board.toString());
    }  
    @Test
    public void constructorDoesNotAcceptTooLargeHeight() {
        board = new Board(31, 10, 10);
        assertEquals("", board.toString());
    }    
    @Test
    public void constructorAcceptsMimimumWidth() {
        board = new Board(5, 10, 10);
        assertEquals(this.testBoardToString(board), board.toString());        
    }  
    @Test
    public void constructorAcceptsMimimumHeight() {
        board = new Board(10, 5, 10);
        assertEquals(this.testBoardToString(board), board.toString()); 
    }
    @Test
    public void constructorAcceptsMaximiumWidth() {
        board = new Board(30, 10, 10);
        assertEquals(this.testBoardToString(board), board.toString()); 
    }  
    @Test
    public void constructorAcceptsMaximumHeight() {
        board = new Board(10, 30, 10);
        assertEquals(this.testBoardToString(board), board.toString()); 
    }
    
    @Test
    public void neighborsAreSetCorrectly() {
        // coming...
    }   
    
    @Test
    public void getPieceReturnsTheRightPiece() {
        board = new Board(10, 10, 30);
        Piece piece = board.getPiece(3, 4);
        assertTrue("", (piece.getX() == 3 && piece.getY() == 4));
    }   
    
    @Test
    public void getWallPiecesNearbyWorksIfThereAreNone() {
        board = new Board(10, 10, 30);
        ArrayList<Piece> wallPiecesNearby = board.getWallPiecesNearby(new Point(100, 100), 30);
        assertTrue("", wallPiecesNearby.isEmpty());
    }     
    @Test
    public void getWallPiecesNearbyWorksIfThereAreSome() {
        board = new Board(10, 10, 30);
        ArrayList<Piece> wallPiecesNearby = board.getWallPiecesNearby(new Point(40, 80), 30);
        assertFalse("", wallPiecesNearby.isEmpty());
    }   
    
    @Test
    public void positionIsInBoundsWorksIfPositionIsLegal() {
        board = new Board(10, 10, 30);
        assertTrue("", board.positionIsInBounds(5, 8));
    }   
    @Test
    public void positionIsInBoundsWorksIfPositionIsFurthestLeft() {
        board = new Board(10, 10, 30);
        assertTrue("", board.positionIsInBounds(0, 8));
    }      
    @Test
    public void positionIsInBoundsWorksIfPositionIsFurthestRight() {
        board = new Board(10, 10, 30);
        assertTrue("", board.positionIsInBounds(9, 8));
    }   
    @Test
    public void positionIsInBoundsWorksIfPositionIsOnTop() {
        board = new Board(10, 10, 30);
        assertTrue("", board.positionIsInBounds(2, 0));
    }       
    @Test
    public void positionIsInBoundsWorksIfPositionIsOnBottom() {
        board = new Board(10, 10, 30);
        assertTrue("", board.positionIsInBounds(4, 9));
    }       
    @Test
    public void positionIsInBoundsWorksIfPositionXIsNegative() {
        board = new Board(10, 10, 30);
        assertFalse("", board.positionIsInBounds(-1, 9));
    }  
    @Test
    public void positionIsInBoundsWorksIfPositionYIsNegative() {
        board = new Board(10, 10, 30);
        assertFalse("", board.positionIsInBounds(4, -1));
    }      
    @Test
    public void positionIsInBoundsWorksIfPositionXIsTooLarge() {
        board = new Board(10, 10, 30);
        assertFalse("", board.positionIsInBounds(10, 9));
    }  
    @Test
    public void positionIsInBoundsWorksIfPositionYIsTooLarge() {
        board = new Board(10, 10, 30);
        assertFalse("", board.positionIsInBounds(4, 10));
    }      
    
    @Test
    public void topLeftCornerPositionIsInBounds() {
        board = new Board(10, 10, 30);
        assertTrue("", board.positionIsInBounds(0, 0));
    }  
    @Test
    public void topRightCornerPositionIsInBounds() {
        board = new Board(10, 10, 30);
        assertTrue("", board.positionIsInBounds(9, 0));
    }   
    @Test
    public void bottomRightCornerPositionIsInBounds() {
        board = new Board(10, 10, 30);
        assertTrue("", board.positionIsInBounds(9, 9));
    }   
    
    @Test
    public void bottomLeftCornerPositionIsInBounds() {
        board = new Board(10, 10, 30);
        assertTrue("", board.positionIsInBounds(0, 9));
    }       
    
    
    
    
    public String testBoardToString(Board b) {
        String boardToString = "";
        for (int h = 0; h < b.getHeight(); h++) {                       
            for (int w = 0; w < b.getWidth(); w++) {
                boardToString += b.getPiece(w, h).toString() + " ";
            }
            boardToString += "\n";
        }
        return boardToString;        
    }
}
