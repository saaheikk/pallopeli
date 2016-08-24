package pallopeli;

import java.awt.Point;
import java.util.ArrayList;
import javax.swing.SwingUtilities;
import pallopeli.collisionphysics.Collision;
import pallopeli.collisionphysics.CollisionDetector;
import pallopeli.gui.UserInterface;
import pallopeli.logic.Game;
import pallopeli.objects.Ball;
import pallopeli.objects.Board;
import pallopeli.objects.Piece;

public class Main {

    public static void main(String[] args) { 
        startProgram();

        

  

    }
    
    public static void startProgram() {
        Game g = new Game(30);
        
        UserInterface ui = new UserInterface(g);
        SwingUtilities.invokeLater(ui); // Causes ui.run() to be executed asynchronously on the AWT event dispatching thread.

        while (ui.getUpdateable() == null) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                System.out.println("PaintingCanvas under contruction.");
            }
        }
        g.setUpedateable(ui.getUpdateable());
        g.start(); // Starts the Timer, causing it to start sending action events to its listeners.
    }
    
    
    
    
    

    

    
}
