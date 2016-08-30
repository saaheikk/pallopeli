/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pallopeli.graphics;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
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
    private JLabel statusContent;
    
    public Sidebar(Game game) {
        super.setBackground(Color.WHITE);
        this.game = game;
        this.createSidebar(this);        
    }   
    
    public void createSidebar(Container container) {
        this.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        
        this.instructions = new JLabel();
        this.lives = new JLabel();
        this.direction = new JLabel();
        this.status = new JLabel();  
        this.statusContent = new JLabel();
        
        container.add(this.instructions);
        container.add(this.lives);
        container.add(this.direction);
        container.add(this.status);   
        container.add(this.statusContent);   
        this.setPreferredSize(new Dimension(155, 322));
        this.setMaximumSize(this.getPreferredSize()); 
        this.setMinimumSize(this.getPreferredSize());
    }
    
    
            

    @Override
    public void update() {
        this.instructions.setText("Instructions here:\n");
        this.lives.setText("Lives: " + game.getLives() + "\n");
        this.direction.setText("Direction: " + game.getDirection() + "\n");
        this.status.setText("Game status: ");
        this.statusContent.setText(this.statusOfGame());
    }
    
    public String statusOfGame() {
        String status = "";
        if (game.continues()) {
            status += "Game continues.";
        } else {
            if (game.win()) {
                status += "Game over! You win!";
            } else {
                status += "Game over! You lost!";
            }
        }
        return status;
    }
    
}
