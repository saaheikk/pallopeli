package pallopeli;

import java.awt.Point;
import java.util.ArrayList;
import javax.swing.SwingUtilities;
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
                System.out.println("Piirtoalustaa ei ole vielä luotu.");
            }
        }
        g.setUpedateable(ui.getUpdateable());
        g.start(); // Starts the Timer, causing it to start sending action events to its listeners.
    }
    
    
    
    
    
    // testailu-/debuggausmetodeja:
    
    public static void tutkiTormaysta() {
        Board board = new Board(10, 10, 30);
        Ball ball = new Ball(30);
        
        // initialize situation
        ball.setCurrentPosition(new Point(254, 202));
        ball.setPreviousPosition(new Point(250, 200));
        ball.setSpeed(4, 2);
        System.out.println(ball);
        System.out.println("...");
              
        ball.moveOnBoard(board);        
    }
    
    public static void tutkiCollisionDetectorinToiminnallisuutta() {
        Ball ball = new Ball(30);
        ball.setCurrentPosition(new Point(258, 204));
        ball.setPreviousPosition(new Point(254, 202));
        ball.setSpeed(4, 2);
        System.out.println(ball);
        System.out.println("...");
              
        CollisionDetector cd = new CollisionDetector();
        Point p = cd.collisionOnVerticalSegment(ball, 255, 165, 225);
        System.out.println(p);
    }
    
}
