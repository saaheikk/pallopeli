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
        pelialue = new Pelialue(10,5);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void hello() {}
}
