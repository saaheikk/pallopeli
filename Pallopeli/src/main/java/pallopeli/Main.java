package pallopeli;

import javax.swing.SwingUtilities;
import pallopeli.gui.UserInterface;
import pallopeli.logic.Game;

public class Main {

    public static void main(String[] args) { 

        Game g = new Game(30);
        
        UserInterface ui = new UserInterface(g);
        SwingUtilities.invokeLater(ui); // Causes ui.run() to be executed asynchronously on the AWT event dispatching thread.

        while (ui.getUpdateable() == null) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                System.out.println("Piirtoalustaa ei ole vielä luotu.");
            }
        }
        g.setUpedateable(ui.getUpdateable());
        g.start(); // Starts the Timer, causing it to start sending action events to its listeners.
        

    }
    
}
