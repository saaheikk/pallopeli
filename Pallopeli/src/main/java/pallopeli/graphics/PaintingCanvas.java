package pallopeli.graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.List;
import java.util.ArrayList;
import javax.swing.JPanel;
import pallopeli.logic.Game;

public class PaintingCanvas extends JPanel implements Updateable {
    private Game game;
    private ArrayList<Drawable> drawables;
    
    public PaintingCanvas(Game game) {
        super.setBackground(Color.WHITE);        
        this.game = game;
        this.drawables = this.createDrawables(game);
    }
    
    
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
    
    protected ArrayList<Drawable> createDrawables(Game game) {
        ArrayList<Drawable> drawables = new ArrayList<>();
        drawables.add(new DrawableBoard(game.getBoard()));
        drawables.add(new DrawableBall(game.getBall()));
        return drawables;
    }
    
}
