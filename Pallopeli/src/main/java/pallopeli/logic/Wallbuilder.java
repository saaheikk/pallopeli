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
    
    public void buildFirstStep(Ball ball) {
//        if (this.start.hasBall(ball)) {
//            return false;  // building fails if ball happens to lie on the piece that is about to turn into wall
//        }
        this.start.setUnderConstruction(true);
        System.out.println("Start: " + this.start);
        this.firstStep = false;
        this.firstDirectionContinues = true;
        this.secondDirectionContinues = true;
//        return true; 
    }

    
    public boolean buildOneStep(Ball ball, SimpleDirection simpleDirection) {
        if (this.firstStep) {
            this.buildFirstStep(ball);
        } 
        if (this.firstDirectionContinues) {
            Piece edge = null;
            CompassDirection buildingDirection = null;
            if (simpleDirection == SimpleDirection.HORIZONTAL) {
                buildingDirection = CompassDirection.WEST;
                edge = this.board.getPiece(this.start.getX() - this.stepsFromStart, start.getY());
            }
            if (simpleDirection == SimpleDirection.VERTICAL) {
                buildingDirection = CompassDirection.NORTH;
                edge = this.board.getPiece(this.start.getX(), start.getY() - this.stepsFromStart);
            }
            this.firstDirectionContinues = edge.setNeighborUnderConstruction(buildingDirection);
            if (!this.firstDirectionContinues) {
                this.firstEnd = edge;
            } else {
                this.piecesUnderConstruction[0][this.stepsFromStart] = edge.getNeighbor(buildingDirection);
            }
        }
        
        if (this.secondDirectionContinues) {
            Piece edge = null;
            CompassDirection buildingDirection = null;
            if (simpleDirection == SimpleDirection.HORIZONTAL) {
                buildingDirection = CompassDirection.EAST;
                edge = this.board.getPiece(this.start.getX() + this.stepsFromStart, start.getY());
                System.out.println("Second edge: " + edge);
            }
            if (simpleDirection == SimpleDirection.VERTICAL) {
                buildingDirection = CompassDirection.SOUTH;
                edge = this.board.getPiece(this.start.getX(), start.getY() + this.stepsFromStart);
                System.out.println("second edge: " + edge);
            }            
            this.secondDirectionContinues = edge.setNeighborUnderConstruction(buildingDirection);
            if (!this.secondDirectionContinues) {
                this.secondEnd = edge;
            } else {
                this.piecesUnderConstruction[1][this.stepsFromStart] = edge.getNeighbor(buildingDirection);
            }
        }
        this.stepsFromStart++;
        if (!this.firstDirectionContinues && !this.secondDirectionContinues) {
//            this.refresh(); // do not refresh yet
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
    // only if building is fully completed and all the info is NOT yet refreshed:
    public void turnAreaIntoWall(Ball ball) {
        int startX = 0;
        int endX = 0;
        int startY = 0;
        int endY = 0;
        
        if (buildingDirection == SimpleDirection.HORIZONTAL) {
            startX = this.firstEnd.getX();
            endX = this.secondEnd.getX();
            if (ball.getCoordinateY() < start.getCenterCoordinateY()) {
                startY = start.getY();
                endY = this.board.getHeight() - 1;
            } else {
                startY = 0;
                endY = start.getY();
            }
            
        }
        if (buildingDirection == SimpleDirection.VERTICAL) {
            startY = this.firstEnd.getY();
            endY = this.secondEnd.getY();
            if (ball.getCoordinateX() < start.getCenterCoordinateX()) {
                startX = start.getX();
                endY = this.board.getWidth() - 1;
            } else {
                startX = 0;
                endY = start.getX();
            }            
        }
        for (int h = startY; h <= endY; h++) {                       
            for (int w = startX; w <= endX; w++) {
                this.board.getPiece(w, h).turnIntoWall();
            }
        }  
    }
    
// trash
    
//    public boolean buildOneStepHorizontal(Ball ball) {
//        if (this.firstStep) {
//            return this.buildFirstStep(ball);
//        }    
//        // check if building continues
//        if (this.firstDirectionContinues) {
//            int w = this.start.getX() - this.stepsFromStart;
//            Piece edgeInWest = this.board.getPiece(w, start.getY());
//            this.firstDirectionContinues = edgeInWest.setNeighborUnderConstruction(CompassDirection.WEST);
//            if (!this.firstDirectionContinues) {
//                this.firstEnd = edgeInWest;
//            } else {
//                this.piecesUnderConstruction[0][this.stepsFromStart] = edgeInWest.getNeighbor(CompassDirection.WEST);
//            }
//        }
//        if (this.secondDirectionContinues) {
//            int e = this.start.getX() + this.stepsFromStart;
//            Piece edgeInEast = this.board.getPiece(e, start.getY());
//            this.secondDirectionContinues = edgeInEast.setNeighborUnderConstruction(CompassDirection.EAST);
//            if (!this.secondDirectionContinues) {
//                this.secondEnd = edgeInEast;
//            } else {
//                this.piecesUnderConstruction[1][this.stepsFromStart] = edgeInEast.getNeighbor(CompassDirection.EAST);
//            }
//        }
//        this.stepsFromStart++;
//        if (!this.firstDirectionContinues && !this.secondDirectionContinues) {
//            this.refresh();
//            return false;
//        }
//        return true;    
//    }    

}
