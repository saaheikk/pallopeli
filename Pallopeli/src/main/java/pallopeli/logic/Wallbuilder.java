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
    // "refreshable" parametres:
    private Piece start; // default: null
    private SimpleDirection buildingDirection;
    
    private Piece currentEnds[];
    private boolean buildContinues[];
    private boolean constructionFailed[];

    private boolean firstStep;
    private boolean startFailed;
    private int stepsFromStart;
    
    private Piece[][] piecesUnderConstruction;   
    /**
     * Constructor for a new Wallbuilder.
     * @param board The Board where Wallbuilder builds walls.
     */
    public Wallbuilder(Board board) {
        this.board = board;        
        this.buildContinues = new boolean[2];
        this.constructionFailed = new boolean[2];                
        this.refresh();
    }
    
    /**
     * Resets the parameters to build new wall on Board.
     * @param p Point where start is tried to set.
     * @param simpleDirection Set Wallbuilder's building direction.
     * @return 
     */
    public boolean resetStart(Point p, SimpleDirection simpleDirection) {  
        this.start = this.board.getPieceThatEnclosesPoint(p);
        if (start == null) {
            return false;
        }
        this.buildingDirection = simpleDirection;
        this.firstStep = true;
        return true;
    }
    /**
     * Refreshes the reusable parameters.
     */    
    public void refresh() {
        this.start = null;
        this.buildingDirection = null;
        
        this.firstStep = false;
        this.startFailed = false;
        this.stepsFromStart = 0;  
        
        this.currentEnds = new Piece[2];
        this.constructionFailed[0] = false;
        this.constructionFailed[1] = false;
        
        this.piecesUnderConstruction = new Piece[2][Math.max(board.getHeight(), board.getWidth())];
    }   

    public boolean buildFirstStep() {
        if (this.start.isWall()) {
            this.startFailed = true;
            return false;
        }
        this.start.setUnderConstruction(true);
        this.firstStep = false;
        this.startFailed = false;
        this.currentEnds[0] = this.start;
        this.currentEnds[1] = this.start;
        this.buildContinues[0] = true;
        this.buildContinues[1] = true;
        return true; 
    }

    public boolean startHasBall(Ball ball) {
        return this.start.hasBall(ball);
    }
    public void turnStartIntoWall() {
        this.start.turnIntoWall();
    }
    public void cancelConstructionOfStart() {
        this.start.setUnderConstruction(false);
        this.startFailed = true;
    }
    public void setStartFailed() {
        this.startFailed = true;        
    }
    public boolean buildOneStep() {
        if (this.firstStep) {
            return this.buildFirstStep();
        } 
        for (int i = 1; i <= 2; i++) {
            if (this.buildContinues[i - 1]) {
                Piece currentEnd = this.currentEnds[i - 1];
                CompassDirection directionOfConstruction = this.buildingDirection.getAssociatedCompassDirection(i);
                boolean continues = currentEnd.setNeighborUnderConstruction(directionOfConstruction);
                if (continues) {
                    currentEnds[i - 1] = currentEnd.getNeighbor(directionOfConstruction);
                    this.piecesUnderConstruction[i - 1][this.stepsFromStart] = currentEnd.getNeighbor(directionOfConstruction);
                }
                this.buildContinues[i - 1] = continues;
            }
        }
        this.stepsFromStart++;
        if (!this.buildContinues[0] && !this.buildContinues[1]) {
            return false;
        }
        return true;  
    }
    /**
     * Determines the direction of the area which needs to be turned into wall. 
     * @param ball
     * @return Direction code of CompassDirection.
     */
    public int getDirectionCodeForTurningAreaIntoWall(Ball ball) {
        int directionCode = 0;
        if (this.buildingDirection == SimpleDirection.HORIZONTAL) {
            if (ball.getCoordinateY() < start.getCenterCoordinateY()) {
                directionCode = 2;
            } else {
                directionCode = 1;
            }
        }
        if (this.buildingDirection == SimpleDirection.VERTICAL) {
            if (ball.getCoordinateX() < start.getCenterCoordinateX()) {
                directionCode = 2;
            } else {
                directionCode = 1;
            }            
        } 
        return directionCode;
    }
    /**
     * Tries to get neighbor of start for first piece for recursion.
     * @param ball
     * @return Neighbor of start or null if it's wall already.
     */
    public Piece getNeighborOfStartForFirstPieceOfRecursion(Ball ball) {
        int directionCode = this.getDirectionCodeForTurningAreaIntoWall(ball);
        Piece first = start.getNeighbor(this.buildingDirection.getPerpendicularCompassDirection(directionCode));
        if (first.isWall()) {
            return null;
        }
        return first;
    }
    
    public Piece seekForFirstPieceOfRecursion(Ball ball) {
        int directionCode = this.getDirectionCodeForTurningAreaIntoWall(ball);
        for (int i = 0; i < 2; i++) {
            for (Piece p : this.piecesUnderConstruction[i]) {
                if (p != null) {
                    Piece neighbor = p.getNeighbor(this.buildingDirection.getPerpendicularCompassDirection(directionCode)); 
                    if (!neighbor.isWall()) {
                        return neighbor;
                    }                    
                }

            }            
        }
        return null;
    }    
    
    public void cancelConstruction(int direction) {
        for (int j = 0; j < Math.max(board.getHeight(), board.getWidth()); j++) {
            if (this.piecesUnderConstruction[direction - 1][j] != null) {
                this.piecesUnderConstruction[direction - 1][j].setUnderConstruction(false);
                
            }
        }
    }
    public void setPiecesUnderConstructionNull(int i) {
        for (int j = 0; j < Math.max(board.getHeight(), board.getWidth()); j++) {
            this.piecesUnderConstruction[i - 1][j] = null;
        }        
    }
    public boolean somePieceUnderConstructionHasBall(int direction, Ball ball) {
        for (int j = 0; j < Math.max(board.getHeight(), board.getWidth()); j++) {
            if (this.piecesUnderConstruction[direction - 1][j] != null) {
                if (this.piecesUnderConstruction[direction - 1][j].hasBall(ball)) {
                    return true;
                }
            }
        }
        return false;      
    }    
    // only if building is fully completed and all the info is NOT yet refreshed:
    public void turnAreaIntoWall(Ball ball) {
        Piece firstPiece = this.getNeighborOfStartForFirstPieceOfRecursion(ball);
        if (firstPiece == null) {
            firstPiece = this.seekForFirstPieceOfRecursion(ball);
        }
        if (firstPiece == null) { // no firstPiece found for recursion
            return;
        }
        this.turnAreaBoundedByWallIntoWall(firstPiece);
    }

    // turn all reachable pieces into wall recursively
    public void turnAreaBoundedByWallIntoWall(Piece firstPiece) {
        ArrayList<Piece> neighborsTurnedIntoWall = firstPiece.turnAllNeighborsIntoWall();
        if (!neighborsTurnedIntoWall.isEmpty()) {
            for (Piece p : neighborsTurnedIntoWall) {
                this.turnAreaBoundedByWallIntoWall(p);
            }            
        }
    }
    
    public void turnConstructionIntoWall(int i) {
        for (Piece p : this.piecesUnderConstruction[i - 1]) {
            if (p != null) {
                p.turnIntoWall();
            }
        }
    }    
    // getters and setters
    public boolean getConstructionFailed(int i) {
        return this.constructionFailed[i - 1];
    }

    public void setConstructionFailed(int i, boolean constructionFailed) {
        this.constructionFailed[i - 1] = constructionFailed;
    }
    
    public void setBuildContinues(int i, boolean continues) {
        this.buildContinues[i - 1] = continues;
    }
    
    public boolean getBuildContinues(int i) {
        return this.buildContinues[i - 1];
    }
    
    

    public boolean startFailed() {
        return startFailed;
    }
    // for testing
    public Piece[][] getPiecesUnderConstruction() {
        return this.piecesUnderConstruction;
    }

   


    
    
    
// trash
    // trash
//    private Piece firstEnd; // default: null
//    private Piece secondEnd; // default: null
//    
//    private boolean firstDirectionContinues; // indicates if wallpiece has stopped the construction
//    private boolean secondDirectionContinues; // indicates if wallpiece has stopped the construction
//
//    private boolean firstConstructionFailed; // indicates if ball has stopped the construction
//    private boolean secondConstructionFailed; // indicates if ball has stopped the construction
    
    
//    public int directionHasBall(Ball ball) {
//        for (int i = 0; i < 2; i++) {
//            for (int j = 0; j < Math.max(board.getHeight(), board.getWidth()); j++) {
//                if (this.piecesUnderConstruction[i][j] != null) {
//                    if (this.piecesUnderConstruction[i][j].hasBall(ball)) {
//                        return i + 1;
//                    }
//                }
//            }
//        }
//        return 0;      
//    }    
    
//    // only if building is fully completed and all the info is NOT yet refreshed:
//    public void turnAreaIntoWall(Ball ball) {
//        if (this.startFailed) {
//            return;
//        }
//        if (!this.firstConstructionFailed && !this.secondConstructionFailed) {
//            int startX = 0;
//            int endX = 0;
//            int startY = 0;
//            int endY = 0;
//
//            if (buildingDirection == SimpleDirection.HORIZONTAL) {
//                startX = this.firstEnd.getX();
//                endX = this.secondEnd.getX();
//                if (ball.getCoordinateY() < start.getCenterCoordinateY()) {
//                    startY = start.getY();
//                    endY = this.board.getHeight() - 1;
//                } else {
//                    startY = 0;
//                    endY = start.getY();
//                }
//
//            }
//            if (buildingDirection == SimpleDirection.VERTICAL) {
//                startY = this.firstEnd.getY();
//                endY = this.secondEnd.getY();
//                if (ball.getCoordinateX() < start.getCenterCoordinateX()) {
//                    startX = start.getX();
//                    endX = this.board.getWidth() - 1;
//                } else {
//                    startX = 0;
//                    endX = start.getX();
//                }            
//            }
//            for (int h = startY; h <= endY; h++) {                       
//                for (int w = startX; w <= endX; w++) {
//                    this.board.getPiece(w, h).turnIntoWall();
//                }
//            }  
//        } else if (this.firstConstructionFailed) {
//            if (!this.secondConstructionFailed) {
//                for (int j = 0; j < Math.max(board.getHeight(), board.getWidth()); j++) {
//                    if (this.piecesUnderConstruction[1][j] != null) {
//                        this.piecesUnderConstruction[1][j].turnIntoWall();
//                    }
//                }
//                
//            }
//        } else if (this.secondConstructionFailed) {
//            if (!this.firstConstructionFailed) {
//                for (int j = 0; j < Math.max(board.getHeight(), board.getWidth()); j++) {
//                    if (this.piecesUnderConstruction[0][j] != null) {
//                        this.piecesUnderConstruction[0][j].turnIntoWall();
//                    }
//                }                
//            }           
//        }
//
//    }    
    
//    public void setFirstDirectionContinues(boolean firstDirectionContinues) {
//        this.firstDirectionContinues = firstDirectionContinues;
//    }
//
//    public void setSecondDirectionContinues(boolean secondDirectionContinues) {
//        this.secondDirectionContinues = secondDirectionContinues;
//    }     
    
//public boolean buildOneStep(SimpleDirection simpleDirection) {
//        if (this.firstStep) {
//            return this.buildFirstStep();
//        } 
//
//        
//        if (this.firstDirectionContinues) {
//            Piece edge = null;
//            CompassDirection buildingDirection = null;
//            if (simpleDirection == SimpleDirection.HORIZONTAL) {
//                buildingDirection = CompassDirection.WEST;
//                edge = this.board.getPiece(this.start.getX() - this.stepsFromStart, start.getY());
//            }
//            
//            if (simpleDirection == SimpleDirection.VERTICAL) {
//                buildingDirection = CompassDirection.NORTH;
//                edge = this.board.getPiece(this.start.getX(), start.getY() - this.stepsFromStart);
//            }
//            this.firstDirectionContinues = edge.setNeighborUnderConstruction(buildingDirection);
//            if (!this.firstDirectionContinues) {
//                this.firstEnd = edge;
//            } else {
//                this.piecesUnderConstruction[0][this.stepsFromStart] = edge.getNeighbor(buildingDirection);
//            }
//        }
//        
//        if (this.secondDirectionContinues) {
//            Piece edge = null;
//            CompassDirection buildingDirection = null;
//            if (simpleDirection == SimpleDirection.HORIZONTAL) {
//                buildingDirection = CompassDirection.EAST;
//                edge = this.board.getPiece(this.start.getX() + this.stepsFromStart, start.getY());
//                System.out.println("Second edge: " + edge);
//            }
//            if (simpleDirection == SimpleDirection.VERTICAL) {
//                buildingDirection = CompassDirection.SOUTH;
//                edge = this.board.getPiece(this.start.getX(), start.getY() + this.stepsFromStart);
//                System.out.println("second edge: " + edge);
//            }            
//            this.secondDirectionContinues = edge.setNeighborUnderConstruction(buildingDirection);
//            if (!this.secondDirectionContinues) {
//                this.secondEnd = edge;
//            } else {
//                this.piecesUnderConstruction[1][this.stepsFromStart] = edge.getNeighbor(buildingDirection);
//            }
//        }
//        this.stepsFromStart++;
//        if (!this.firstDirectionContinues && !this.secondDirectionContinues) {
////            this.refresh(); // do not refresh yet
//            return false;
//        }
//        return true;          
//        
//    }    
    
    
    
//    public boolean firstConstructionFailed() {
//        return firstConstructionFailed;
//    }
//
//    public void setFirstConstructionFailed(boolean constructionFailed) {
//        this.firstConstructionFailed = constructionFailed;
//    }    
//    public boolean secondConstructionFailed() {
//        return secondConstructionFailed;
//    }
//
//    public void setSecondConstructionFailed(boolean constructionFailed) {
//        this.secondConstructionFailed = constructionFailed;
//    }    
//    public boolean anyPieceUnderConstructionHasBall(Ball ball) {
//        for (int i = 0; i < 2; i++) {
//            for (int j = 0; j < Math.max(board.getHeight(), board.getWidth()); j++) {
//                if (this.piecesUnderConstruction[i][j] != null) {
//                    if (this.piecesUnderConstruction[i][j].hasBall(ball)) {
//                        return true;
//                    }
//                }
//            }
//        }
//        return false;
//    }    
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
    
        // this method is very ugly...
//    public boolean build() {
//        if (this.firstStep) {
//            this.start.turnIntoWall();
//            this.firstStep = false;
//        }
//        if (buildingDirection == SimpleDirection.HORIZONTAL) {
//            int e = this.start.getX() + this.stepsFromStart;
//            int w = this.start.getX() - this.stepsFromStart;
//            
//            Piece edgeInEast = this.board.getPiece(e, start.getY());
//            Piece edgeInWest = this.board.getPiece(w, start.getY());
//            
//            boolean buildingInEastCompleted = false;
//            boolean buildingInWestCompleted = false;
//            
//            if (edgeInEast != null) {
//                buildingInEastCompleted = edgeInEast.turnNeighborIntoWall(CompassDirection.EAST);
//            }
//            if (edgeInWest != null) {
//                buildingInWestCompleted = edgeInWest.turnNeighborIntoWall(CompassDirection.WEST);
//            }
//            this.stepsFromStart++;
//            if (!buildingInEastCompleted && !buildingInWestCompleted) {
//                this.refresh();
//                return false;
//            }
//            return true;
//        }
//        return false;
//    } 





}
