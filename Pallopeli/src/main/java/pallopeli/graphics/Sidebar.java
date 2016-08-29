/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pallopeli.graphics;

import java.awt.Color;
import java.awt.Container;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import pallopeli.logic.Game;

/**
 *
 * @author saara
 */
public class Sidebar extends JPanel implements Updateable {
    private Game game;
    
    private JLabel instructions;
    private JLabel lives;
    private JLabel direction;
    private JLabel status;
    
    
    public Sidebar(Game game) {
        super.setBackground(Color.WHITE);
        
        this.game = game;
        
        this.createSidebar(this);        
    }   
    
    public void createSidebar(Container container) {
        this.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        
        this.instructions = new JLabel("Instructions here \n");
        this.lives = new JLabel("Lives: " + game.getLives() + "\n");
        this.direction = new JLabel("Direction: " + game.getDirection() + "\n");
        this.status = new JLabel("Status: ");   
        
        container.add(this.instructions);
        container.add(this.lives);
        container.add(this.direction);
        container.add(this.status);        
    }
    
    
            

    @Override
    public void update() {
        this.lives.setText("Lives: " + game.getLives() + "\n");
    }
    
}
