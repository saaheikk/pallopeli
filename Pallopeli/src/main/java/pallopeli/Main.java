package pallopeli;

import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import javax.swing.SwingUtilities;
import pallopeli.collisionphysics.Collision;
import pallopeli.collisionphysics.CollisionDetector;
import pallopeli.gui.UserInterface;
import pallopeli.logic.Game;
import pallopeli.logic.Wallbuilder;
import pallopeli.objects.Ball;
import pallopeli.objects.Board;
import pallopeli.objects.Piece;

public class Main {

    public static void main(String[] args) { 
        startProgram();
    }
    
    /**
     * Start Pallopeli.
     */
    public static void startProgram() {
        Game g = new Game(30);
        
        UserInterface ui = new UserInterface(g);
        SwingUtilities.invokeLater(ui); // Causes ui.run() to be executed asynchronously on the AWT event dispatching thread.

        while (ui.getPainitingCanvas() == null) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                System.out.println("PaintingCanvas still under contruction.");
            }
        }
        while (ui.getSidebar() == null) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                System.out.println("Sidebar still under contruction.");
            }
        }        
        g.setPaintingCanvas(ui.getPainitingCanvas());
        g.setSidebar(ui.getSidebar());
        g.start(); // Starts the Timer, causing it to start sending action events to its listeners.
    }

}
