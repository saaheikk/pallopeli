package pallopeli;

import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.SwingUtilities;
import pallopeli.collisionphysics.Collision;
import pallopeli.collisionphysics.CollisionDetector;
import pallopeli.graphics.Updateable;
import pallopeli.gui.UserInterface;
import pallopeli.logic.Game;
import pallopeli.logic.Wallbuilder;
import pallopeli.objects.Ball;
import pallopeli.objects.Board;
import pallopeli.objects.Border;
import pallopeli.objects.BorderLine;
import pallopeli.objects.Piece;

public class Main {
    /**
     * Main method of Pallopeli.
     * @param args 
     */
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
                System.out.println("Game view still under contruction.");
            }
        }  
        while (ui.getSidebar() == null) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                System.out.println("Game view still under contruction.");
            }
        }    
        g.setPaintingCanvas(ui.getPainitingCanvas());
        g.setSidebar(ui.getSidebar());
        g.start(); // Starts the Timer, causing it to start sending action events to its listeners.        
    }
    /**
     * Debugging method.
     */
    public static void debugging() {
        System.out.println("----Ball move on board----");
        CollisionDetector cd = new CollisionDetector();
        Board board = new Board(10, 10, 20);
        Ball ball = new Ball(20);
        ball.setCurrentPosition(new Point(172, 29));
        ball.setPreviousPosition(new Point(166, 32));
        ball.setDx(6);
        ball.setDy(-3);
        ball.moveOnBoard(board);         
        System.out.println("----manual----");

        ball.setCurrentPosition(new Point(172, 29));
        ball.setPreviousPosition(new Point(166, 32));
        ball.setDx(6);
        ball.setDy(-3);      
        ArrayList<Piece> wallPiecesNearToBall = board.getWallPiecesNearby(ball.getPreviousPosition(), 50);
        ArrayList<Collision> collisionsDetected = cd.getAllCollisions(wallPiecesNearToBall, ball);        

        for (Piece p : wallPiecesNearToBall) {
            for (CompassDirection direction : p.getBorders().keySet()) {
                Border border = p.getBorders().get(direction);
                if (border != null) {
                    if (border.isActive()) {
                        System.out.println(border);
                        BorderLine borderLine = (BorderLine) border;
                        Collision candindate = cd.collisionAtBorderLine(borderLine, ball);
                        System.out.println(candindate);
                        if (candindate != null) {
                            collisionsDetected.add(candindate);
                        }
                    }
                }
            }
        } 
        System.out.println(collisionsDetected.isEmpty());        
    }
}
