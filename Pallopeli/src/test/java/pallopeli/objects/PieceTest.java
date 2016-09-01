package pallopeli.objects;

import java.awt.Point;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pallopeli.CompassDirection;
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
    public void constructorSetsCornerPointNorthwestCorrectly() {
        piece = new Piece(3, 4, false, 30);
        Point expectedNorthwest = new Point(90, 120);
        assertEquals(expectedNorthwest, piece.getCornerPoint(CompassDirection.NORTHWEST));    
    }     
    @Test
    public void constructorSetsCornerPointNortheastCorrectly() {
        piece = new Piece(3, 4, false, 30);
        Point expectedNorthwest = new Point(120, 120);
        assertEquals(expectedNorthwest, piece.getCornerPoint(CompassDirection.NORTHEAST));    
    } 
    @Test
    public void constructorSetsCornerPointSoutheastCorrectly() {
        piece = new Piece(3, 4, false, 30);
        Point expectedNorthwest = new Point(120, 150);
        assertEquals(expectedNorthwest, piece.getCornerPoint(CompassDirection.SOUTHEAST));    
    } 
    @Test
    public void constructorSetsCornerPointSouthwestCorrectly() {
        piece = new Piece(3, 4, false, 30);
        Point expectedNorthwest = new Point(90, 150);
        assertEquals(expectedNorthwest, piece.getCornerPoint(CompassDirection.SOUTHWEST));    
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
    public void hasBallWorksIfBallLiesOnPiece() {
        piece = new Piece(3, 3, true, 30);
        Ball ballLyingOnPiece = new Ball(30);
        ballLyingOnPiece.setCurrentPosition(new Point(95, 95));
        assertTrue("", piece.hasBall(ballLyingOnPiece));    
    }  
    
    @Test
    public void hasBallWorksIfBallLiesOnLeftBorder() {
        piece = new Piece(3, 3, true, 30);
        Ball ballLyingOnPiece = new Ball(30);
        ballLyingOnPiece.setCurrentPosition(new Point(75, 95));
        assertTrue("", piece.hasBall(ballLyingOnPiece));    
    }      
    @Test
    public void hasBallWorksIfBallLiesOnTopBorder() {
        piece = new Piece(3, 3, true, 30);
        Ball ballLyingOnPiece = new Ball(30);
        ballLyingOnPiece.setCurrentPosition(new Point(100, 75));
        assertTrue("", piece.hasBall(ballLyingOnPiece));    
    }    
    @Test
    public void hasBallWorksIfBallLiesOnRightBorder() {
        piece = new Piece(3, 3, true, 30);
        Ball ballLyingOnPiece = new Ball(30);
        ballLyingOnPiece.setCurrentPosition(new Point(135, 105));
        assertTrue("", piece.hasBall(ballLyingOnPiece));    
    }       
    @Test
    public void hasBallWorksIfBallLiesOnBottomBorder() {
        piece = new Piece(3, 3, true, 30);
        Ball ballLyingOnPiece = new Ball(30);
        ballLyingOnPiece.setCurrentPosition(new Point(95, 135));
        assertTrue("", piece.hasBall(ballLyingOnPiece));    
    }       
    @Test
    public void hasBallWorksIfBallLiesOnLeftTopCorner() {
        piece = new Piece(3, 3, true, 30);
        Ball ballLyingOnPiece = new Ball(30);
        ballLyingOnPiece.setCurrentPosition(new Point(80, 80));
        assertTrue("", piece.hasBall(ballLyingOnPiece));    
    }       
    @Test
    public void hasBallWorksIfBallLiesOnRightTopCorner() {
        piece = new Piece(3, 3, true, 30);
        Ball ballLyingOnPiece = new Ball(30);
        ballLyingOnPiece.setCurrentPosition(new Point(130, 80));
        assertTrue("", piece.hasBall(ballLyingOnPiece));    
    }      
    @Test
    public void hasBallWorksIfBallLiesOnRightBottomCorner() {
        piece = new Piece(3, 3, true, 30);
        Ball ballLyingOnPiece = new Ball(30);
        ballLyingOnPiece.setCurrentPosition(new Point(130, 130));
        assertTrue("", piece.hasBall(ballLyingOnPiece));    
    }         
    @Test
    public void hasBallWorksIfBallLiesOnLeftBottomCorner() {
        piece = new Piece(3, 3, true, 30);
        Ball ballLyingOnPiece = new Ball(30);
        ballLyingOnPiece.setCurrentPosition(new Point(80, 130));
        assertTrue("", piece.hasBall(ballLyingOnPiece));    
    }   
    @Test
    public void hasBallWorksIfBallDoesNotTouchPiece() {
        piece = new Piece(2, 3, true, 30);
        Ball ballNotLyingOnPiece = new Ball(30);
        ballNotLyingOnPiece.setCurrentPosition(new Point(150, 171));
        assertFalse("", piece.hasBall(ballNotLyingOnPiece));    
    }    
    @Test
    public void hasBallWorksIfBallAlmostTouchesPiece() {
        piece = new Piece(3, 3, true, 30);
        Ball ballNotLyingOnPiece = new Ball(30);
        ballNotLyingOnPiece.setCurrentPosition(new Point(79, 131));
        assertFalse("", piece.hasBall(ballNotLyingOnPiece));    
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
    
    @Test
    public void northeastCornerIsSetCorrectly() {
        piece = new Piece(1, 1, false, 10);
        Point expectedCorner = new Point(20, 10);
        assertEquals(expectedCorner, piece.getCornerPoint(CompassDirection.NORTHEAST));
    } 
    @Test
    public void northwestCornerIsSetCorrectly() {
        piece = new Piece(1, 1, false, 10);
        Point expectedCorner = new Point(10, 10);
        assertEquals(expectedCorner, piece.getCornerPoint(CompassDirection.NORTHWEST));
    } 
    @Test
    public void southeastCornerIsSetCorrectly() {
        piece = new Piece(1, 1, false, 10);
        Point expectedCorner = new Point(20, 20);
        assertEquals(expectedCorner, piece.getCornerPoint(CompassDirection.SOUTHEAST));
    }
    @Test
    public void southwestCornerIsSetCorrectly() {
        piece = new Piece(1, 1, false, 10);
        Point expectedCorner = new Point(10, 20);
        assertEquals(expectedCorner, piece.getCornerPoint(CompassDirection.SOUTHWEST));
    }     
    
}
