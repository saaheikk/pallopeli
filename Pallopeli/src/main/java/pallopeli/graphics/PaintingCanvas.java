package pallopeli.graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.List;
import java.util.ArrayList;
import javax.swing.JPanel;
import pallopeli.logic.Game;

/**
 * PaintingCanvas represents the background where all the drawable components are painted.
 * @author saara
 */

public class PaintingCanvas extends JPanel implements Updateable {
    private Game game;
    private ArrayList<Drawable> drawables;
    
    public PaintingCanvas(Game game) {
        super.setBackground(Color.WHITE);        
        this.game = game;
        this.drawables = this.createDrawables(game);
    }
    
    /**
     * Method inherited from JPanel; Calls the UI delegate's paint method, if the UI delegate is non-null.
     * @param g 
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < this.drawables.size(); i++) {
            this.drawables.get(i).draw(g);
        }
        
    }
    @Override
    public void update() {
        this.repaint();
    }  
    /**
     * Creates the Drawable components of the given game.
     * @param game
     * @return List containing DrawableBoard and DrawableBall.
     */
    protected ArrayList<Drawable> createDrawables(Game game) {
        ArrayList<Drawable> drawables = new ArrayList<>();
        drawables.add(new DrawableBoard(game.getBoard()));
        drawables.add(new DrawableBall(game.getBall()));
        return drawables;
    }
    
}
