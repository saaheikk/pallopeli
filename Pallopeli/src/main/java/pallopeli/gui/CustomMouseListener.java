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
 *
 * @author saara
 */
public class CustomMouseListener implements MouseListener {
    private Game game;

    public CustomMouseListener(Game game) {
        this.game = game;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!this.game.isBuilding()) {
            this.game.buildWall(e.getPoint());
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
