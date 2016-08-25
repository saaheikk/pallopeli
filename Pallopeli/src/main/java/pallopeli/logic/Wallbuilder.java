/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pallopeli.logic;

import java.awt.Point;
import java.util.ArrayList;
import pallopeli.CompassDirection;
import pallopeli.SimpleDirection;
import pallopeli.objects.Board;
import pallopeli.objects.Piece;

/**
 * Wallbuilder is in charge of building walls by turning pieces into wall one by one.
 * @author saaheikk
 */
public class Wallbuilder {
    private Board board;  
    private Piece start;    
    private SimpleDirection buildingDirection;
    private boolean firstStep;
    int stepsFromStart;

    public Wallbuilder(Board board) {
        this.board = board;
        this.refresh();
    }

    public void resetStart(Point p, SimpleDirection simpleDirection) {  
        this.start = this.board.getPieceThatEnclosesPoint(p);
        this.buildingDirection = simpleDirection;
        this.firstStep = true;
    }
        
    public void refresh() {
        this.start = null;
        this.buildingDirection = null;
        this.firstStep = false;
        this.stepsFromStart = 0;        
    }        
    
    // this method is very ugly...
    public boolean build() {
        if (this.firstStep) {
            this.start.turnIntoWall();
            this.firstStep = false;
        }
        if (buildingDirection == SimpleDirection.HORIZONTAL) {
            int e = this.start.getX() + this.stepsFromStart;
            int w = this.start.getX() - this.stepsFromStart;
            
            Piece edgeInEast = this.board.getPiece(e, start.getY());
            Piece edgeInWest = this.board.getPiece(w, start.getY());
            
            boolean buildingInEastCompleted = false;
            boolean buildingInWestCompleted = false;
            
            if (edgeInEast != null) {
                buildingInEastCompleted = edgeInEast.turnNeighborIntoWall(CompassDirection.EAST);
            }
            if (edgeInWest != null) {
                buildingInWestCompleted = edgeInWest.turnNeighborIntoWall(CompassDirection.WEST);
            }
            this.stepsFromStart++;
            if (!buildingInEastCompleted && !buildingInWestCompleted) {
                this.refresh();
                return false;
            }
            return true;
        }
        return false;
    }    

}
