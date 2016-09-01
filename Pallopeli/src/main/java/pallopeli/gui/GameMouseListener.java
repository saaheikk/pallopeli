/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pallopeli.gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import pallopeli.logic.Game;
import pallopeli.objects.Board;
import pallopeli.objects.Piece;

/**
 * CustomMouseListener listens to mouse clicks and sets Game into building mode (if it's not in building mode already) .
 * @author saara
 */
public class GameMouseListener implements MouseListener {
    private Game game;
    /**
     * Constructor for a new GameMouseListener attached to given Game.
     * @param game 
     */
    public GameMouseListener(Game game) {
        this.game = game;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!this.game.isBuilding()) {
            boolean startIdentified = this.game.getWallbuilder().resetStart(e.getPoint(), this.game.getDirection());
            this.game.setBuilding(startIdentified);
        }      
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    
}
