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
        this.pieces = new Piece[2][10];
    }
    
    public void createListOfPiecesToBuild(Piece start) {
        this.start = start;

        
    }
    
    public void build(Piece start, SimpleDirection sd) {
        int x = start.getX();
        int y = start.getY();
        
        // check if ball lies on Piece start
        start.turnIntoWall();
        if (sd == SimpleDirection.HORIZONTAL) {
            int e = x;
            int w = x;

            while (true) {
                // sleep
                
                board.getPiece(e, y).turnNeighborIntoWall(CompassDirection.EAST);
                board.getPiece(w, y).turnNeighborIntoWall(CompassDirection.WEST);
                
                e--;
                w++;
                        
            }

        }
    }
    
    public void checkForUpdates() {
        
    }
    
    
    
    
}
