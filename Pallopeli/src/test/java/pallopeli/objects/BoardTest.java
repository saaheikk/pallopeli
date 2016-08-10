package pallopeli.objects;

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
    public void neighborsAreSetCorrectly() {}   
    
    
    
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
