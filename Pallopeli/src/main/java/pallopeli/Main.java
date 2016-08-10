package pallopeli;

import javax.swing.SwingUtilities;
import javax.swing.Timer;
import pallopeli.gui.UserInterface;
import pallopeli.logic.Game;
import pallopeli.objects.Board;


public class Main {

    public static void main(String[] args) { 
        Game g = new Game(30);
        UserInterface ui = new UserInterface(g);
        ui.run();

        SwingUtilities.invokeLater(ui);

        while (ui.getUpdateable() == null) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                System.out.println("Piirtoalustaa ei ole vielä luotu.");
            }
        }
        g.setUpedateable(ui.getUpdateable());
        g.start();
        

    }
    
}
