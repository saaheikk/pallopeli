package pallopeli;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pallopeli.domain.Pala;

public class PalaTest {
    Pala pala;
    
    public PalaTest() {

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
    public void konstruktoriAsettaaSeinaominaisuudenOikeinTavallisellePalalle() {
        pala = new Pala(false, 3, 3);
        assertFalse("", pala.isSeina());
    }    
    
    @Test
    public void konstruktoriAsettaaSeinaominaisuudenOikeinSeinapalalle() {
        pala = new Pala(true, 3, 3);
        assertTrue("", pala.isSeina());
    }  
    
    @Test
    public void toStringToimiiTavallisellaPalalla() {
        pala = new Pala(false, 3, 3);
        assertEquals("ei-seinä", pala.toString());
    }
    
    @Test
    public void toStringToimiiSeinapalalla() {
        pala = new Pala(true, 3, 3);
        assertEquals("on-seinä", pala.toString());
    }
    
    @Test
    public void muutaSeinaksiToimiiTavallisellaPalalla() {
        pala = new Pala(false, 3, 3);
        pala.muutaSeinaksi();
        assertTrue("", pala.isSeina());
    }
    
    @Test
    public void muutaSeinaksiToimiiSeinapalalla() {
        pala = new Pala(true, 3, 3);
        pala.muutaSeinaksi();
        assertTrue("", pala.isSeina());
    }    
}
