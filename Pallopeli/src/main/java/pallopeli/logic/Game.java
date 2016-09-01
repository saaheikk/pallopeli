package pallopeli.logic;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Timer;
import pallopeli.SimpleDirection;
import pallopeli.CompassDirection;
import pallopeli.graphics.Updateable;
import pallopeli.objects.Ball;
import pallopeli.objects.Board;
import pallopeli.objects.Piece;

/**
 * Game contains the main application logic of Pallopeli.
 * @author saara
 */

public class Game extends Timer implements ActionListener {    
    private Board board;
    private Ball ball;
    int sizeOfObjects;
    
    private Wallbuilder wallbuilder;
    
    private SimpleDirection direction;
    private boolean continues;
    private boolean buildingNewWall;
    
    private Updateable paintingCanvas;
    private Updateable sidebar;
    
    private int lives;
    private boolean win;
    
    /**
     * Constructor for a new Game.
     * @param sizeOfObjects 
     */
    public Game(int sizeOfObjects) {
        super(500, null); // set update for every 600 milliseconds
        
        // create objects
        this.sizeOfObjects = sizeOfObjects;
        this.board = new Board(15, 10, sizeOfObjects);        
        this.ball = new Ball(sizeOfObjects);
        this.ball.setBallOnBoard(this.board);
        
        // create wallbuilder
        this.wallbuilder = new Wallbuilder(this.board);
        
        // set game parametres
        this.direction = SimpleDirection.HORIZONTAL;        
        this.continues = true;
        this.buildingNewWall = false;
        this.lives = 3;
        this.win = false;
        
        addActionListener(this);
        setInitialDelay(2000);        
    }
    /**
     * Method inherited from ActionListener; invoked when an action occurs.
     * Represents the game loop.
     * @param e Action event sent by Game itself.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!continues) {
            return; // GAME OVER --> check for win / lost
        }   
        this.board.resetActiveBordersOfWallpieces();         
        this.ball.moveOnBoard(board); // always move ball on board
        if (buildingNewWall) {
            buildingNewWall = this.performBuildingActAndReturnTrueIfBuildingContinues();
        }  
        if (this.lives < 1) { // lost game
            this.continues = false; 
        }
        if (this.boardIsCovered()) { // win game
            this.continues = false;
            this.win = true;
        }
        this.paintingCanvas.update();
        this.sidebar.update();        
    }
    /**
     * Performs one building action.
     * @return True if building continues after the act and false if not.
     */
    public boolean performBuildingActAndReturnTrueIfBuildingContinues() {
        this.wallbuilder.buildOneStep();
        // take care of start
        if (this.wallbuilder.startHasBall(ball)) {
            this.wallbuilder.cancelConstructionOfStart();
            this.wallbuilder.setStartFailed(); // may not need this
            this.lives--; 
            this.wallbuilder.refresh();
            return false;
        } else {
            this.wallbuilder.turnStartIntoWall();
        } 
        // check for other collisions with ball
        for (int i = 1; i <= 2; i++) {
            if (wallbuilder.somePieceUnderConstructionHasBall(i, ball)) {
                wallbuilder.setConstructionFailed(i, true);
                wallbuilder.setBuildContinues(i, false);
                wallbuilder.cancelConstruction(i);
                wallbuilder.setPiecesUnderConstructionNull(i);
                lives--;
            }
        }
        // turn some area into wall if needed (construction/the whole area)
        for (int i = 1; i <= 2; i++) {
            if (!wallbuilder.getConstructionFailed(i) && !wallbuilder.getBuildContinues(i)) {
                wallbuilder.turnConstructionIntoWall(i);
            }
        }
        
        if (!wallbuilder.getBuildContinues(1) && !wallbuilder.getBuildContinues(2)) {
            if (!wallbuilder.getConstructionFailed(1) && !wallbuilder.getConstructionFailed(2)) {
                // case: both constructions finished succesfully
                wallbuilder.turnAreaIntoWall(ball);
            }
            this.wallbuilder.refresh();            
            return false;
        }       
        return true;
    }
    
    
    /**
     * Checks if over 90% of board is covered by wall.
     * @return True if 90% of board is covered by wall and false if not.
     */
    public boolean boardIsCovered() {
        int allPieces = this.board.numberOfAllPieces();
        double enough =  0.90 * (double) allPieces;
        if (board.numberOfWallPieces() > enough) {
            return true;
        }   
        return false;
    }
    /**
     * Changes building direction in Game from horizontal to vertical and vice versa.
     */
    public void changeDirection() {
        if (this.direction == SimpleDirection.HORIZONTAL) {
            this.direction = SimpleDirection.VERTICAL;
            return;
        }
        if (this.direction == SimpleDirection.VERTICAL) {
            this.direction = SimpleDirection.HORIZONTAL;
            return;
        }        
    }

    // getters and setters
    public boolean continues() {
        return continues;
    }

    public boolean win() {
        return win;
    }    
    
    public void setNewDirection(SimpleDirection direction) {
        this.direction = direction;
    } 

    public Board getBoard() {
        return board;
    }

    public Ball getBall() {
        return ball;
    }

    public int getSizeOfObjects() {
        return this.sizeOfObjects;
    }

    public void setPaintingCanvas(Updateable upedateable) {
        this.paintingCanvas = upedateable;
    }
    public void setSidebar(Updateable upedateable) {
        this.sidebar = upedateable;
    }    

    public void setBuilding(boolean building) {
        this.buildingNewWall = building;
    }

    public boolean isBuilding() {
        return buildingNewWall;
    }

    public Wallbuilder getWallbuilder() {
        return wallbuilder;
    }

    public SimpleDirection getDirection() {
        return direction;
    }
    public int getLives() {
        return this.lives;
    }

}
