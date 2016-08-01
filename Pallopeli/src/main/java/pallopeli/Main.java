package pallopeli;

import javax.swing.SwingUtilities;
import pallopeli.domain.Pelialue;
import pallopeli.gui.Kayttoliittyma;
import pallopeli.peli.Pallopeli;

public class Main {

    public static void main(String[] args) {        
        Pallopeli pallopeli = new Pallopeli();
        
        Kayttoliittyma kayttoliittyma = new Kayttoliittyma(pallopeli);
        SwingUtilities.invokeLater(kayttoliittyma); // "Causes kayttoliittyma.run() to be executed asynchronously on the AWT event dispatching thread."
    }
    
}
