/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pallopeli.gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import pallopeli.logic.Game;

/**
 *
 * @author saara
 */
public class GameKeyListener implements KeyListener {
    private Game game;
    
    public GameKeyListener(Game game) {
        this.game = game;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (Character.isSpaceChar(e.getKeyChar())) {
            if (!this.game.isBuilding()) {
                this.game.changeDirection();
            }    
        }
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
    
}
