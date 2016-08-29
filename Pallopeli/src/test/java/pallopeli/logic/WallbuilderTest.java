/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pallopeli.logic;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pallopeli.objects.Board;

/**
 *
 * @author saara
 */
public class WallbuilderTest {
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
        Board b = new Board(10, 10, 30);
        w = new Wallbuilder(b);
    }
    
    @After
    public void tearDown() {
    }


     @Test
     public void hello () {}
    
    // test reset
    // test start: legal piece / illegal piece
    // test fully succeeded contruction (horizontal / vertical)
    // test partly succeded costruction (both sides)
}
