package pallopeli.gui;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import pallopeli.logic.Game;

public class PaintingCanvas extends JPanel implements Updateable {
    private Game game;
    
    public PaintingCanvas(Game game) {
        super.setBackground(Color.WHITE);        
        this.game = game;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.game.getBoard().draw(g);
        this.game.getBall().draw(g);
        
    }
    @Override
    public void update() {
        this.repaint();
    }    
    
}
