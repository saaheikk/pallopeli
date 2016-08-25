/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pallopeli.logic;

import java.util.ArrayList;
import pallopeli.CompassDirection;
import pallopeli.SimpleDirection;
import pallopeli.objects.Board;
import pallopeli.objects.Piece;

/**
 *
 * @author saaheikk
 */
public class Wallbuilder {
    private Board board;    
    private Piece start;
    private Piece[][] pieces;

    
    public Wallbuilder(Board board) {
        this.board = board;
        this.pieces = new Piece[2][Math.max(board.getHeight(), board.getWidth())];
    }
    
    public void createListOfPiecesToBuild(Piece start, SimpleDirection simpleDirection) {
        this.start = start;
        int x = start.getX();
        int y = start.getY();
        int length = Math.max(board.getHeight(), board.getWidth());
        // aware that here we have some copypaste...
        if (simpleDirection == SimpleDirection.HORIZONTAL) {
            int w = x - 1;
            int e = x + 1;
            for (int i = 0; i < length; i++) {
                this.pieces[0][i] = board.getPiece(w, y);
                this.pieces[1][i] = board.getPiece(e, y);
                w--; 
                e++;               
            }
        }
        if (simpleDirection == SimpleDirection.VERTICAL) {
            int n = y - 1;
            int s = y + 1;
            for (int i = 0; i < length; i++) {
                this.pieces[0][i] = board.getPiece(x, n);
                this.pieces[0][i] = board.getPiece(x, s);
                n--;
                s++;                
            }
        }        
    }
    
    public void build() {


    }
    
    public void checkForUpdates() {
        
    }
    
    public int calculateSteps() {
        int steps = 0;
        int length = Math.max(board.getHeight(), board.getWidth());        
        for (int i = 0; i < length; i++) {
            if (!this.pieces[0][i].equals(null) || !this.pieces[1][i].equals(null)) {
                steps++;
            }
        }   
        return steps;
    }
    
    
    
    
}
