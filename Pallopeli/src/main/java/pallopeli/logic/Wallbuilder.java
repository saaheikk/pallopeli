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
import pallopeli.objects.Ball;
import pallopeli.objects.Board;
import pallopeli.objects.Piece;

/**
 * Wallbuilder is in charge of building walls by turning pieces into wall one by one.
 * @author saaheikk
 */
public class Wallbuilder {
    private Board board;  
    
    // "refreshable":
    private Piece start;  
    private Piece firstEnd;
    private Piece secondEnd;
    
    private SimpleDirection buildingDirection;
    private boolean firstStep;
    private boolean firstDirectionContinues;
    private boolean secondDirectionContinues;
    
    private int stepsFromStart;
    
    private Piece[][] piecesUnderConstruction;

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
        this.firstEnd = null;
        this.secondEnd = null;
        this.buildingDirection = null;
        this.firstStep = false;
        this.stepsFromStart = 0;  
        this.piecesUnderConstruction = new Piece[2][Math.max(board.getHeight(), board.getWidth())];
    }   
    
    public boolean buildFirstStep(Ball ball) {
       if (this.start.hasBall(ball)) {
            return false;  // building fails if ball happens to lie on the piece that is about to turn into wall
        }
        this.start.setUnderConstruction(true);
        this.firstStep = false;
        this.firstDirectionContinues = true;
        this.secondDirectionContinues = true;
        return true; 
    }
    
    public boolean buildOneStepHorizontal(Ball ball) {
        if (this.firstStep) {
            return this.buildFirstStep(ball);
        }    
        // check if building continues
        if (this.firstDirectionContinues) {
            int w = this.start.getX() - this.stepsFromStart;
            Piece edgeInWest = this.board.getPiece(w, start.getY());
//            if (edgeInWest != null) { // edge in west should not be able to be null... }
            this.firstDirectionContinues = edgeInWest.setNeighborUnderConstruction(CompassDirection.WEST);
            if (!this.firstDirectionContinues) {
                this.firstEnd = edgeInWest;
            } else {
                this.piecesUnderConstruction[0][this.stepsFromStart] = edgeInWest.getNeighbor(CompassDirection.WEST);
            }
        }
        if (this.secondDirectionContinues) {
            int e = this.start.getX() + this.stepsFromStart;
            Piece edgeInEast = this.board.getPiece(e, start.getY());
            this.secondDirectionContinues = edgeInEast.setNeighborUnderConstruction(CompassDirection.EAST);
            if (!this.secondDirectionContinues) {
                this.secondEnd = edgeInEast;
            } else {
                this.piecesUnderConstruction[1][this.stepsFromStart] = edgeInEast.getNeighbor(CompassDirection.EAST);
            }
        }
        this.stepsFromStart++;
        if (!this.firstDirectionContinues && !this.secondDirectionContinues) {
            this.refresh();
            return false;
        }
        return true;    
    }

    public boolean anyPieceUnderConstructionHasBall(Ball ball) {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < Math.max(board.getHeight(), board.getWidth()); j++) {
                if (this.piecesUnderConstruction[i][j] != null) {
                    if (this.piecesUnderConstruction[i][j].hasBall(ball)) {
                        return true;
                    }
                }
            }
        }
        return false;
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
    // if building is fully completed:
    public void turnAreaIntoWall(Piece start, SimpleDirection direction, Ball ball) {
        if (direction == SimpleDirection.HORIZONTAL) {
            
        }
        if (direction == SimpleDirection.VERTICAL) {
            
        }
        for (int h = 0; h < this.board.getHeight(); h++) {                       
            for (int w = 0; w < this.board.getWidth(); w++) {
                
            }
        }  
    }

}
