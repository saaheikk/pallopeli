/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pallopeli.gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import pallopeli.objects.Board;
import pallopeli.objects.Piece;

/**
 *
 * @author saara
 */
public class CustomMouseListener extends Thread implements MouseListener {
    private Board board;

    public CustomMouseListener(Board board) {
        this.board = board;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        board.getPieceThatEnclosesPoint(e.getPoint()).turnIntoWall();
        
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
