package pallopeli.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import pallopeli.logic.Game;

public class UserInterface implements Runnable {

    private JFrame frame;
    private Game game;
    private int sizeOfObjects;
    
    private Updateable paintingCanvas;

    public UserInterface(Game game) {
        this.game = game;
        this.sizeOfObjects = game.getSizeOfObjects();
    }

    @Override
    public void run() {
        frame = new JFrame("Pallopeli");
        frame.setPreferredSize(new Dimension(400, 400));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        createComponents(frame.getContentPane());

        frame.pack();
        frame.setVisible(true);
    }

    public void createComponents(Container container) {
        // Huom! Luo ensin piirtoalusta jonka lisäät container-olioon
        // Luo vasta tämän jälkeen näppäimistönkuuntelija, jonka lisäät frame-oliolle
        this.paintingCanvas = new PaintingCanvas(this.game);
        container.add((Component) paintingCanvas);

    }


    public JFrame getFrame() {
        return frame;
    }
    public Updateable getUpdateable() {
        return this.paintingCanvas;
    }
}
