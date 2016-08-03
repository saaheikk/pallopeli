package pallopeli;

import javax.swing.SwingUtilities;
import pallopeli.domain.Pelialue;
import pallopeli.gui.Kayttoliittyma;
import pallopeli.peli.Pallopeli;

public class Main {

    public static void main(String[] args) { 
        System.out.println("Hello world!");
        Pelialue pelialue = new Pelialue(6, 5);
        pelialue.alustaPalat();
        String pelialueString = 
                  "on-seinä on-seinä on-seinä on-seinä on-seinä on-seinä \n"
                + "on-seinä ei-seinä ei-seinä ei-seinä ei-seinä on-seinä \n"
                + "on-seinä ei-seinä ei-seinä ei-seinä ei-seinä on-seinä \n"
                + "on-seinä ei-seinä ei-seinä ei-seinä ei-seinä on-seinä \n"
                + "on-seinä on-seinä on-seinä on-seinä on-seinä on-seinä \n";
        System.out.println(pelialue.toString());
        System.out.println(pelialueString);
        
//        Pallopeli pallopeli = new Pallopeli();
//        
//        Kayttoliittyma kayttoliittyma = new Kayttoliittyma(pallopeli);
//        SwingUtilities.invokeLater(kayttoliittyma); // "Causes kayttoliittyma.run() to be executed asynchronously on the AWT event dispatching thread."
    }
    
}
