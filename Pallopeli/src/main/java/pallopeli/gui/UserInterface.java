package pallopeli.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import pallopeli.graphics.Updateable;
import pallopeli.graphics.PaintingCanvas;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import pallopeli.graphics.Sidebar;
import pallopeli.logic.Game;

/**
 * UserInterface contains JFrame that further contains the graphical user interface for this game.
 * @author saara
 */

public class UserInterface implements Runnable {
    private JFrame frame;
    private Game game; // Starting new game after winning/losing? --> reset game?
    private int sizeOfObjects; 

    private Updateable paintingCanvas;
    private Updateable sidebar;

    public UserInterface(Game game) {
        this.game = game;
        this.sizeOfObjects = game.getSizeOfObjects();
    }
    
    /**
     * Method inherited from Runnable; When an object implementing interface Runnable is used to create a thread, starting the thread causes the object's run method to be called in that separately executing thread.
     */
    @Override
    public void run() {
        frame = new JFrame("Pallopeli");
//        frame.setPreferredSize(new Dimension(300, 322)); // just right for 10 x 10 board
        frame.setPreferredSize(new Dimension(610, 322));        
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // createStartingView(frame.getContentPane());
        createComponents(frame.getContentPane());

        frame.pack();
        frame.setVisible(true);
    }
    
    // This method will later add other ui-components to Container!
    public void createComponents(Container container) { 
        container.setLayout(new BorderLayout());
        
        this.paintingCanvas = new PaintingCanvas(this.game);
        container.add((Component) paintingCanvas, "Center");
        
        this.sidebar = new Sidebar(this.game);
        container.add((Component) sidebar, "East");
        
        GameMouseListener mouseListener = new GameMouseListener(this.game);
        this.frame.getContentPane().addMouseListener(mouseListener);
        GameKeyListener keylistener = new GameKeyListener(this.game);
        this.frame.addKeyListener(keylistener); //???!!!
    }
    
    public void createStartingView(Container container) { 
        GameMouseListener mouseListener = new GameMouseListener(this.game);
        this.frame.getContentPane().addMouseListener(mouseListener);
    }    

    // getters and setters
    
    public JFrame getFrame() {
        return frame;
    }
    public Updateable getPainitingCanvas() {
        return this.paintingCanvas;
    }
    public Updateable getSidebar() {
        return this.sidebar;
    }    
}
