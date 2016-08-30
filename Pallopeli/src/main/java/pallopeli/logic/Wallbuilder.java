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
    private Piece firstEnd; // default: null
    private Piece secondEnd; // default: null
    
    private SimpleDirection buildingDirection;
    
    private boolean firstStep;
    private boolean firstDirectionContinues; // indicates if wallpiece has stopped the construction
    private boolean secondDirectionContinues; // indicates if wallpiece has stopped the construction
    
    private boolean startFailed;
    private boolean firstConstructionFailed; // indicates if ball has stopped the construction
    private boolean secondConstructionFailed; // indicates if ball has stopped the construction
    
    private int stepsFromStart;
    
    private Piece[][] piecesUnderConstruction;
    

    
    /**
     * Constructor for a new Wallbuilder.
     * @param board The Board where Wallbuilder builds walls.
     */
    public Wallbuilder(Board board) {
        this.board = board;
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
        this.firstEnd = null;
        this.secondEnd = null;
        this.buildingDirection = null;
        this.firstStep = false;
        this.stepsFromStart = 0;  
        this.piecesUnderConstruction = new Piece[2][Math.max(board.getHeight(), board.getWidth())];
        
        this.startFailed = false;
        this.firstConstructionFailed = false;
        this.secondConstructionFailed = false;
    }   

    
    public boolean buildFirstStep() {
        if (this.start.isWall()) {
            this.startFailed = true;
            return false;
        }
        this.start.setUnderConstruction(true);
        this.firstStep = false;
        this.startFailed = false;
        this.firstDirectionContinues = true;
        this.secondDirectionContinues = true;
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

    
    public boolean buildOneStep(SimpleDirection simpleDirection) {
        if (this.firstStep) {
            return this.buildFirstStep();
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
    public void cancelConstruction(int direction) {
        for (int j = 0; j < Math.max(board.getHeight(), board.getWidth()); j++) {
            if (this.piecesUnderConstruction[direction - 1][j] != null) {
                this.piecesUnderConstruction[direction - 1][j].setUnderConstruction(false);
            }
        }
    }
 
    // method returns which direction has collision with ball or 0 if no collision
    public int directionHasBall(Ball ball) {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < Math.max(board.getHeight(), board.getWidth()); j++) {
                if (this.piecesUnderConstruction[i][j] != null) {
                    if (this.piecesUnderConstruction[i][j].hasBall(ball)) {
                        return i + 1;
                    }
                }
            }
        }
        return 0;      
    }

    public boolean firstConstructionFailed() {
        return firstConstructionFailed;
    }

    public void setFirstConstructionFailed(boolean constructionFailed) {
        this.firstConstructionFailed = constructionFailed;
    }    
    public boolean secondConstructionFailed() {
        return secondConstructionFailed;
    }

    public void setSecondConstructionFailed(boolean constructionFailed) {
        this.secondConstructionFailed = constructionFailed;
    }

   
    // only if building is fully completed and all the info is NOT yet refreshed:
    public void turnAreaIntoWall(Ball ball) {
        if (this.startFailed) {
            return;
        }
        if (!this.firstConstructionFailed && !this.secondConstructionFailed) {
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
                    endX = start.getX();
                }            
            }
            for (int h = startY; h <= endY; h++) {                       
                for (int w = startX; w <= endX; w++) {
                    this.board.getPiece(w, h).turnIntoWall();
                }
            }  
        } else if (this.firstConstructionFailed) {
            if (!this.secondConstructionFailed) {
                for (int j = 0; j < Math.max(board.getHeight(), board.getWidth()); j++) {
                    if (this.piecesUnderConstruction[1][j] != null) {
                        this.piecesUnderConstruction[1][j].turnIntoWall();
                    }
                }
                
            }
        } else if (this.secondConstructionFailed) {
            if (!this.firstConstructionFailed) {
                for (int j = 0; j < Math.max(board.getHeight(), board.getWidth()); j++) {
                    if (this.piecesUnderConstruction[0][j] != null) {
                        this.piecesUnderConstruction[0][j].turnIntoWall();
                    }
                }                
            }           
        }

    }
    public void setFirstDirectionContinues(boolean firstDirectionContinues) {
        this.firstDirectionContinues = firstDirectionContinues;
    }

    public void setSecondDirectionContinues(boolean secondDirectionContinues) {
        this.secondDirectionContinues = secondDirectionContinues;
    }    
    
    
// trash
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
