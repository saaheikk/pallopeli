package pallopeli.logic;

import java.awt.Point;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pallopeli.CompassDirection;
import pallopeli.SimpleDirection;
import pallopeli.objects.Ball;
import pallopeli.objects.Board;
import pallopeli.objects.Piece;


public class WallbuilderTest {
    Board b;
    Wallbuilder w;
    
    public WallbuilderTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        b = new Board(10, 10, 30);
        w = new Wallbuilder(b);
    }
    
    @After
    public void tearDown() {
    }
    
    
    public void setStartInTheMiddleOfBoard(SimpleDirection sd) {
        w.resetStart(new Point(151, 151), sd);
    }
    @Test
    public void buildFirstStepReturnsFalseIfStartIsWall() {
        b.getPieceThatEnclosesPoint(new Point(151, 151)).turnIntoWall();
        this.setStartInTheMiddleOfBoard(SimpleDirection.VERTICAL);
        boolean continues = w.buildFirstStep();
        assertFalse("Returns true.", continues);
    }     
    
    @Test
    public void firstStepFailsIfStartIsWall() {
        b.getPieceThatEnclosesPoint(new Point(151, 151)).turnIntoWall();
        this.setStartInTheMiddleOfBoard(SimpleDirection.VERTICAL);
        w.buildFirstStep();
        assertTrue("Start did not fail.", w.startFailed());
    } 
    
    @Test
    public void buildFirstStepReturnsTrueWhenEverythingIsOk() {
        this.setStartInTheMiddleOfBoard(SimpleDirection.HORIZONTAL);
        boolean continues = w.buildFirstStep();
        assertTrue("Return false.", continues);
    }    

    @Test
    public void buildOneStepAfterSetsFirstNextPieceUnderConstructionVertical() {
        this.setStartInTheMiddleOfBoard(SimpleDirection.VERTICAL);
        w.buildOneStep();
        w.buildOneStep();
        assertTrue("Next piece not under construction.", b.getPiece(5, 4).isUnderConstruction());
    } 
    @Test
    public void buildOneStepAfterSetsSecondNextPieceUnderConstructionVertical() {
        this.setStartInTheMiddleOfBoard(SimpleDirection.VERTICAL);
        w.buildOneStep();
        w.buildOneStep();
        assertTrue("Next piece not under construction.", b.getPiece(5, 6).isUnderConstruction());
    }   
    @Test
    public void buildOneStepAfterSetsFirstNextPieceUnderConstructionHorizontal() {
        this.setStartInTheMiddleOfBoard(SimpleDirection.HORIZONTAL);
        w.buildOneStep();
        w.buildOneStep();
        assertTrue("Next piece not under construction.", b.getPiece(4, 5).isUnderConstruction());
    }      
    @Test
    public void buildOneStepAfterSetsSecondNextPieceUnderConstructionHorizontal() {
        this.setStartInTheMiddleOfBoard(SimpleDirection.HORIZONTAL);
        w.buildOneStep();
        w.buildOneStep();
        assertTrue("Next piece not under construction.", b.getPiece(6, 5).isUnderConstruction());
    }  
    
    @Test
    public void buildOneStepStillReturnsTrueIfFirstConstructionEndsButSecondContinues() {
        w.resetStart(new Point(100, 100), SimpleDirection.HORIZONTAL);
        w.buildOneStep(); //start: (3,3)
        w.buildOneStep(); // pieces: (2,3), (4,3)
        w.buildOneStep(); // pieces: (1,3), (5,3)   
        boolean continues = w.buildOneStep(); // pieces: (2,3), (4,3)
        int loopsRequiredToReachWall = 3;
        while (loopsRequiredToReachWall > 0) {
            w.buildOneStep(); // start + 2 rounds
            loopsRequiredToReachWall--;
        }
        assertTrue("Construction should continue.", continues);
    }      
    
    @Test
    public void buildOneStepReturnsFalseWhenBothEndsOfConstructionAreFinished() {
        w.resetStart(new Point(100, 100), SimpleDirection.HORIZONTAL);
        //start: (3,3)
        for (int i = 3; i <= 8; i++) {
            w.buildOneStep();
        }
        boolean continues = w.buildOneStep(); // last piece in east: (8,3)
        assertFalse("Construction should end.", continues);
    }  
    
    
    
    
    
    
    
    // tests for recursive turnAreaIntoWall
    @Test
    public void testTurnAreaIntoWallStartingFromUpperLeftCorner() {
        w.turnAreaBoundedByWallIntoWall(b.getPiece(1, 1));
        boolean allIsWall = true;
        for (int h = 0; h < b.getHeight(); h++) {                       
            for (int w = 0; w < b.getWidth(); w++) {
                if (!b.getPiece(w, h).isWall()) {                    
                    allIsWall = false;
                }
            }
        } 
         assertTrue("", allIsWall);
    }
 
    @Test
    public void testTurnAreaIntoWallStartingFromAboutCenter() {
        w.turnAreaBoundedByWallIntoWall(b.getPiece(5, 4));
        boolean allIsWall = true;
        for (int h = 0; h < b.getHeight(); h++) {                       
            for (int w = 0; w < b.getWidth(); w++) {
                if (!b.getPiece(w, h).isWall()) {                    
                    allIsWall = false;
                }
            }
        } 
         assertTrue("", allIsWall);
    }  
     
    @Test
    public void turnsRightAreaIntoWallComplexCase() {
        for (int i = 0; i < b.getWidth(); i++) {
            b.getPiece(i, 5).turnIntoWall();
        }
        w.turnAreaBoundedByWallIntoWall(b.getPiece(5, 6));
        assertFalse("", b.getPiece(3, 3).isWall());
    }   

    
    @Test
    public void somePieceUnderConstructionHasBallReturnsTrue() {
        this.prepareConstruction(SimpleDirection.HORIZONTAL);
        Ball b = new Ball(30);
        b.setCurrentPosition(new Point(100, 152));
        assertTrue("Piece should alarm for ball.", w.somePieceUnderConstructionHasBall(1, b));
    } 
    @Test
    public void somePieceUnderConstructionHasBallReturnsFalse() {
        this.prepareConstruction(SimpleDirection.HORIZONTAL);
        Ball b = new Ball(30);
        b.setCurrentPosition(new Point(100, 152));
        assertFalse("No piece should alarm for ball.", w.somePieceUnderConstructionHasBall(2, b));
    }     

    
    // test getting the right direction
    @Test
    public void directionIsCorrectForTurningAreaIntoWallHorizontalCaseAndBallAboveStart() {
        Ball b = new Ball(30);
        b.setCurrentPosition(new Point(100, 100));
        this.prepareConstruction(SimpleDirection.HORIZONTAL);
        int code = w.getDirectionCodeForTurningAreaIntoWall(b);
        assertEquals(CompassDirection.SOUTH, SimpleDirection.HORIZONTAL.getPerpendicularCompassDirection(code));
    }   
    @Test
    public void directionIsCorrectForTurningAreaIntoWallHorizontalCaseAndBallUnderStart() {
        Ball b = new Ball(30);
        b.setCurrentPosition(new Point(220, 220));
        this.prepareConstruction(SimpleDirection.HORIZONTAL);
        int code = w.getDirectionCodeForTurningAreaIntoWall(b);
        assertEquals(CompassDirection.NORTH, SimpleDirection.HORIZONTAL.getPerpendicularCompassDirection(code));
    }      
    @Test
    public void directionIsCorrectForTurningAreaIntoWallVerticalCaseAndBallLeftFromStart() {
        Ball b = new Ball(30);
        b.setCurrentPosition(new Point(100, 100));
        this.prepareConstruction(SimpleDirection.VERTICAL);
        int code = w.getDirectionCodeForTurningAreaIntoWall(b);
        assertEquals(CompassDirection.EAST, SimpleDirection.VERTICAL.getPerpendicularCompassDirection(code));
    }   
    @Test
    public void directionIsCorrectForTurningAreaIntoWallVerticalCaseAndBallRightFromStart() {
        Ball b = new Ball(30);
        b.setCurrentPosition(new Point(220, 220));
        this.prepareConstruction(SimpleDirection.VERTICAL);
        int code = w.getDirectionCodeForTurningAreaIntoWall(b);
        assertEquals(CompassDirection.WEST, SimpleDirection.VERTICAL.getPerpendicularCompassDirection(code));
    }    
    public void prepareConstruction(SimpleDirection sd) {
        w.resetStart(new Point(152, 152), sd);
        w.buildFirstStep();
        while (true) {
            boolean continues = w.buildOneStep();
            if (!continues) {
                break;
            }
        }
    }
    
    
    
    
    // test reset
    // test start: legal piece / illegal piece
    // test fully succeeded contruction (horizontal / vertical)
    // test partly succeded costruction (both sides)
}
