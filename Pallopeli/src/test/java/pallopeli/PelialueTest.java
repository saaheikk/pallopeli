package pallopeli;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pallopeli.domain.Pelialue;

/**
 *
 * @author saara
 */
public class PelialueTest {
    Pelialue pelialue;
    
    public PelialueTest() {
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
    public void konstruktoriEiHyvaksyLiianPientaLeveytta() {
        pelialue = new Pelialue(4,5);
        assertEquals("", pelialue.toString());
    }
    
    public void konstruktoriEiHyvaksyLiianPientaKorkeutta() {
        pelialue = new Pelialue(10,4);
        assertEquals("", pelialue.toString());
    }
    
    public void konstruktoriEiHyvaksyLiianSuurtaLeveytta() {
        pelialue = new Pelialue(150,15);
        assertEquals("", pelialue.toString());
    }
    
    public void konstruktoriEiHyvaksyLiianSuurtaKorkeutta() {
        pelialue = new Pelialue(10,150);
        assertEquals("", pelialue.toString());
    }
    
    public void palatAlustetaanOikein() {
        pelialue = new Pelialue(6,5);
        String pelialueString = 
                  "on-seina on-seina on-seina on-seina on-seina on-seina \n"
                + "on-seina ei-seina ei-seina ei-seina ei-seina on-seina \n"
                + "on-seina ei-seina ei-seina ei-seina ei-seina on-seina \n"
                + "on-seina ei-seina ei-seina ei-seina ei-seina on-seina \n"
                + "on-seina on-seina on-seina on-seina on-seina on-seina \n";
        assertEquals(pelialueString, pelialue.toString());
                
                
    }
    

}
