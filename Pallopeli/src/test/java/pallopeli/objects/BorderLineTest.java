package pallopeli.objects;

import java.awt.geom.Line2D;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pallopeli.CompassDirection;
import pallopeli.objects.BorderLine;

public class BorderLineTest {
    Piece p;
    BorderLine bl;    
    
    public BorderLineTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        p = new Piece(1, 1, true, 10);
    }
    
    @After
    public void tearDown() {
    }


    @Test
    public void northBorderIsSetCorrectly() {
        bl = new BorderLine(p, CompassDirection.NORTH, 5);
        Line2D expectedBorderLine = new Line2D.Float(10, 5, 20, 5);
        assertEquals(expectedBorderLine.getP1(), bl.getBorderShape().getP1());
        assertEquals(expectedBorderLine.getP2(), bl.getBorderShape().getP2());
    }
    @Test
    public void westBorderIsSetCorrectly() {
        bl = new BorderLine(p, CompassDirection.WEST, 5);
        Line2D expectedBorderLine = new Line2D.Float(5, 10, 5, 20);
        assertEquals(expectedBorderLine.getP1(), bl.getBorderShape().getP1());
        assertEquals(expectedBorderLine.getP2(), bl.getBorderShape().getP2());
    }
    @Test
    public void eastBorderIsSetCorrectly() {
        bl = new BorderLine(p, CompassDirection.EAST, 5);
        Line2D expectedBorderLine = new Line2D.Float(25, 10, 25, 20);
        assertEquals(expectedBorderLine.getP1(), bl.getBorderShape().getP1());
        assertEquals(expectedBorderLine.getP2(), bl.getBorderShape().getP2());
    }
    @Test
    public void southBorderIsSetCorrectly() {
        bl = new BorderLine(p, CompassDirection.SOUTH, 5);
        Line2D expectedBorderLine = new Line2D.Float(10, 25, 20, 25);
        assertEquals(expectedBorderLine.getP1(), bl.getBorderShape().getP1());
        assertEquals(expectedBorderLine.getP2(), bl.getBorderShape().getP2());
    }    
    @Test
    public void extendLineWorksInNorth() {
        bl = new BorderLine(p, CompassDirection.NORTH, 5);
        bl.extendLine();
        Line2D expectedBorderLine = new Line2D.Float(5, 5, 25, 5);
        assertEquals(expectedBorderLine.getP1(), bl.getBorderShape().getP1());
        assertEquals(expectedBorderLine.getP2(), bl.getBorderShape().getP2());
    }      
    @Test
    public void extendLineWorksInEast() {
        bl = new BorderLine(p, CompassDirection.EAST, 5);
        bl.extendLine();
        Line2D expectedBorderLine = new Line2D.Float(25, 5, 25, 25);
        assertEquals(expectedBorderLine.getP1(), bl.getBorderShape().getP1());
        assertEquals(expectedBorderLine.getP2(), bl.getBorderShape().getP2());
    } 
    @Test
    public void extendLineWorksInSouth() {
        bl = new BorderLine(p, CompassDirection.SOUTH, 5);
        bl.extendLine();
        Line2D expectedBorderLine = new Line2D.Float(5, 25, 25, 25);
        assertEquals(expectedBorderLine.getP1(), bl.getBorderShape().getP1());
        assertEquals(expectedBorderLine.getP2(), bl.getBorderShape().getP2());
    } 
    @Test
    public void extendLineWorksInWest() {
        bl = new BorderLine(p, CompassDirection.WEST, 5);
        bl.extendLine();
        Line2D expectedBorderLine = new Line2D.Float(5, 5, 5, 25);
        assertEquals(expectedBorderLine.getP1(), bl.getBorderShape().getP1());
        assertEquals(expectedBorderLine.getP2(), bl.getBorderShape().getP2());
    }   
    
    @Test
    public void getLineCoordinateWorksForNorthBorder() {
        bl = new BorderLine(p, CompassDirection.NORTH, 5);
        bl.extendLine();
        assertEquals(5, bl.getLineCoordinate());
    }     
    
    
}
