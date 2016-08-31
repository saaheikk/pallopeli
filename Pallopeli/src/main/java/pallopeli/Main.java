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
import pallopeli.objects.BorderLine;
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
                
// this does not work, why?        
//        for (Updateable u : ui.getUpdateables()) {
//            while (u == null) {
//                try {
//                    Thread.sleep(100);
//                } catch (InterruptedException ex) {
//                    System.out.println("Game view still under contruction.");
//                }
//            }            
//        }
//        g.setUpdateables(ui.getUpdateables());

    }
    
    public static void tests() {
        
        Board b = new Board(10, 10, 30);
        Wallbuilder w = new Wallbuilder(b);
        Ball ball = new Ball(30);
        ball.setCurrentPosition(new Point(100, 100));        
        
        w.resetStart(new Point(152, 182), SimpleDirection.HORIZONTAL);
        while (true) {
            boolean continues = w.buildOneStep();
            if (!continues) {
                
                break;
            }
        }  
        w.turnStartIntoWall();
        w.turnConstructionIntoWall(1);
        w.turnConstructionIntoWall(2);
        w.refresh();
        for (int y = 0; y < b.getHeight(); y++) {
            for (int x = 0; x < b.getWidth(); x++) {
                if (b.getPiece(x, y).isWall()) {
                    System.out.println(b.getPiece(x, y));
                }
            }
        }
        System.out.println("-------");
        
        w.resetStart(new Point(152, 152), SimpleDirection.HORIZONTAL);
        while (true) {
            boolean continues = w.buildOneStep();
            if (!continues) {
                
                break;
            }
        }  
        int directionCode = w.getDirectionCodeForTurningAreaIntoWall(ball);
        CompassDirection cd = SimpleDirection.HORIZONTAL.getPerpendicularCompassDirection(directionCode);
        System.out.println("DIRECTION CODE: " + directionCode);
        System.out.println(cd);
        for (int i = 0; i < 2; i++) {
            for (Piece p : w.getPiecesUnderConstruction()[i]) {
                System.out.println("Piece: " + p);
                
                Piece neighbor = p.getNeighbor(cd); 
                System.out.println("Neighbor:" + neighbor);
//                if (!neighbor.isWall()) {
//                    return neighbor;
//                }
            }            
        }
        
        
            
    }

}
