package pallopeli;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pallopeli.domain.Pelialue;


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
    
    @Test
    public void konstruktoriEiHyvaksyLiianPientaKorkeutta() {
        pelialue = new Pelialue(5,4);
        assertEquals("", pelialue.toString());
    }

    @Test    
    public void konstruktoriEiHyvaksyLiianSuurtaLeveytta() {
        pelialue = new Pelialue(101,10);
        assertEquals("", pelialue.toString());
    }
    
    @Test
    public void konstruktoriEiHyvaksyLiianSuurtaKorkeutta() {
        pelialue = new Pelialue(10,101);
        assertEquals("", pelialue.toString());
    }
    @Test
    public void palatAlustetaanOikein() {
        pelialue = new Pelialue(6,5);
        pelialue.alustaPalat();
        String pelialueString = 
                  "on-seinä on-seinä on-seinä on-seinä on-seinä on-seinä \n"
                + "on-seinä ei-seinä ei-seinä ei-seinä ei-seinä on-seinä \n"
                + "on-seinä ei-seinä ei-seinä ei-seinä ei-seinä on-seinä \n"
                + "on-seinä ei-seinä ei-seinä ei-seinä ei-seinä on-seinä \n"
                + "on-seinä on-seinä on-seinä on-seinä on-seinä on-seinä \n";
        assertEquals(pelialueString, pelialue.toString());           
    }
    

}
