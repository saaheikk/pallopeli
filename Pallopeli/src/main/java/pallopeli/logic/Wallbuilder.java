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
    private Piece start;
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
     * Resets the parameters to build a new wall on Board.
     * @param p Point where start is tried to set.
     * @param simpleDirection Set Wallbuilder's building direction.
     * @return True if there exists a Piece where to set start and false if not.
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
    /**
     * Try to set start under construction.
     * Changes the attributes of this Wallbuilder so that normal building can begin.
     * @return False if start is Wall already and true if not.
     */
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
    /**
     * Checks if start and Ball collide.
     * @param ball Ball being examined.
     * @return True if collision occurs and false if not.
     */
    public boolean startHasBall(Ball ball) {
        return this.start.hasBall(ball);
    }
    /**
     * Turns start into wall.
     */  
    public void turnStartIntoWall() {
        this.start.turnIntoWall();
    }
    /**
     * Sets start back into normal mode (not under construction).
     * Sets startFailed = true;
     */
    public void cancelConstructionOfStart() {
        this.start.setUnderConstruction(false);
        this.startFailed = true;
    }
    /**
     * Builds one step in both directions of construction (if construction continues).
     * @return False is both constructions have reached wall or failed or start has failed.
     */
    public boolean buildOneStep() {
        if (this.firstStep) {
            return this.buildFirstStep();
        } 
        this.makeCurrentEndsSetNeighborUnderConstruction();
        this.stepsFromStart++;
        if (!this.buildContinues[0] && !this.buildContinues[1]) {
            return false;
        }
        return true;  
    }
    /**
     * Makes current end set their neighbor under construction.
     * Both ends are checked separately.
     * If build does not continue in the end, do nothing.
     * If it does, turn one neighbor under construction and check if build still continues after this.
     * Reset buildContinues.
     */
    public void makeCurrentEndsSetNeighborUnderConstruction() {
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
    }
    /**
     * Determines the direction of the area which needs to be turned into wall. 
     * @param ball Ball being examined.
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
     * Tries to get neighbor of start for the first piece for recursion.
     * @param ball Ball being examined.
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
    /**
     * Tries to find the first piece of recursion from somewhere else along the builded wall.
     * Used if neighbor of start is already wall.
     * @param ball Ball being examined.
     * @return Piece where recursion can start or null if there is no piece to start recursion.
     */
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
    /**
     * Cancels the construction.
     * Used if Ball collides with a Piece that is under construction.
     * @param direction 
     */
    public void cancelConstruction(int direction) {
        for (int j = 0; j < Math.max(board.getHeight(), board.getWidth()); j++) {
            if (this.piecesUnderConstruction[direction - 1][j] != null) {
                this.piecesUnderConstruction[direction - 1][j].setUnderConstruction(false); 
            }
        }
    }
    
    /**
     * Refreshes the table of Pieces that are under construction.
     * @param i 
     */
    public void setPiecesUnderConstructionNull(int i) {
        for (int j = 0; j < Math.max(board.getHeight(), board.getWidth()); j++) {
            this.piecesUnderConstruction[i - 1][j] = null;
        }        
    }
    /**
     * Tracks if some some Piece that is under construction has ball.
     * @param direction Closer to / further from origin.
     * @param ball Ball being examined.
     * @return True if collision occurs and false if not.
     */
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
    /**
     * Turns the area into wall that cannot have any connection to Ball anymore.
     * Used only if construction is fully completed and attributes are NOT yet refreshed.
     * @param ball 
     */
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
    /**
     * Turn all reachable pieces into wall recursively.
     * @param firstPiece Start of recursion.
     */
    public void turnAreaBoundedByWallIntoWall(Piece firstPiece) {
        ArrayList<Piece> neighborsTurnedIntoWall = firstPiece.turnAllNeighborsIntoWall();
        if (!neighborsTurnedIntoWall.isEmpty()) {
            for (Piece p : neighborsTurnedIntoWall) {
                this.turnAreaBoundedByWallIntoWall(p);
            }            
        }
    }
    /**
     * Turns one construction into wall.
     * @param i Closer to / further from origin.
     */
    public void turnConstructionIntoWall(int i) {
        for (Piece p : this.piecesUnderConstruction[i - 1]) {
            if (p != null) {
                p.turnIntoWall();
            }
        }
    }    
    // getters and setters
    public void setStartFailed() {
        this.startFailed = true;        
    }   
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
}
