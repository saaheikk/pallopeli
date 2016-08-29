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
        super(600, null); // set update for every 600 millseconds
        
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
     * @param e Action event sent by Game itself.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!continues) {
            return; // GAME OVER --> check for win / lost
        }      
        this.ball.moveOnBoard(board);
        if (buildingNewWall) {
            boolean buildingIsOn = this.wallbuilder.buildOneStep(this.direction);
            if (this.wallbuilder.startHasBall(ball)) {
                this.wallbuilder.cancelConstructionOfStart();
                this.lives--;
                buildingIsOn = false;
            } else {
                this.wallbuilder.turnStartIntoWall();
            }
            if (this.wallbuilder.directionHasBall(ball) == 1) {
                this.wallbuilder.setFirstDirectionContinues(false);
                this.wallbuilder.cancelConstruction(1);
                this.wallbuilder.setFirstConstructionFailed(true);
                this.lives--;
            }
            if (this.wallbuilder.directionHasBall(ball) == 2) {
                this.wallbuilder.setSecondDirectionContinues(false);
                this.wallbuilder.cancelConstruction(2);
                this.wallbuilder.setSecondConstructionFailed(true);
                this.lives--;
            }            

            if (!buildingIsOn) {
                this.wallbuilder.turnAreaIntoWall(ball); 
                this.wallbuilder.refresh();
            }
            this.buildingNewWall = buildingIsOn;
        }
        if (this.lives < 1) {
            this.continues = false; 
        }
        if (this.boardIsCovered()) {
            this.continues = false;
            this.win = true;
        }
        this.paintingCanvas.update();
    }

    /**
     * Checks if over 85% of board is covered by wall.
     * @return 
     */
    public boolean boardIsCovered() {
        int allPieces = this.board.numberOfAllPieces();
        double enough =  0.85 * (double) allPieces;
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
