/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pallopeli.graphics;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import pallopeli.logic.Game;

/**
 * Sidebar represents the info bar on the side of actual game board.
 * Sidebar is updated during the game.
 * @author saara
 */
public class Sidebar extends JPanel implements Updateable {
    private Game game;
    
    private JLabel instructions;
    private ArrayList<JLabel> instructionsContent;
    private JLabel lives;
    private JLabel direction;
    private JLabel status;
    private JLabel statusContent;
    
    public Sidebar(Game game) {
        super.setBackground(Color.WHITE);
        this.game = game;
        this.createContent(this);        
    }  
    /**
     * Creates the content of Sidebar and adds it to Container.
     * @param container Container (of JFrame).
     */   
    public void createContent(Container container) {
        this.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        this.instructions = new JLabel();
        this.instructionsContent = new ArrayList<>();
        this.lives = new JLabel();
        this.direction = new JLabel();
        this.status = new JLabel();  
        this.statusContent = new JLabel();
        
        container.add(this.instructions);
        this.addInstructionsContent(container);
        container.add(this.lives);
        container.add(this.direction);
        container.add(this.status);   
        container.add(this.statusContent);   
        this.setPreferredSize(new Dimension(200, 322));
        this.setMaximumSize(this.getPreferredSize()); 
        this.setMinimumSize(this.getPreferredSize());
    }
    
    
            

    @Override
    public void update() {
        this.instructions.setText("INSTRUCTIONS:");
        this.lives.setText("LIVES: " + game.getLives());
        this.direction.setText("DIRECTION: " + game.getDirection() + "\n");
        this.status.setText("GAME STATUS: ");
        this.statusContent.setText(this.statusOfGame());
    }
    /**
     * Returns the current status of Game.
     * @return Status as String to be inserted into JLabel.
     */
    
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
    /**
     * Adds instructions as JLabels.
     * @param container Container (of JFrame).
     */
    
    public void addInstructionsContent(Container container) {
        JLabel firstRow = new JLabel("Turn areas into wall by");
        container.add(firstRow);
        JLabel secondRow = new JLabel("clicking pieces. You need");
        container.add(secondRow);
        JLabel thirdRow = new JLabel("to get 90% of board covered");
        container.add(thirdRow);        
        JLabel fourthRow = new JLabel("to win the game. Change");
        container.add(fourthRow);        
        JLabel fifthRow = new JLabel("building direction by");       
        container.add(fifthRow);        
        JLabel sixthRow = new JLabel("pressing space key. Watch");  
        container.add(sixthRow);        
        JLabel seventhRow = new JLabel("out for the ball!"); 
        container.add(seventhRow);
    }
    
}
