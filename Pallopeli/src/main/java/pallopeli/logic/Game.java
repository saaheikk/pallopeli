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
    private boolean building;
    
    private Updateable updateable;
    
    /**
     * Constructor for a new Game ready to start.
     * @param sizeOfObjects 
     */
    public Game(int sizeOfObjects) {
        super(600, null);
        this.sizeOfObjects = sizeOfObjects;
        this.board = new Board(10, 10, sizeOfObjects);        
        this.ball = new Ball(sizeOfObjects);
        this.ball.setBallOnBoard(this.board);
        
        this.direction = SimpleDirection.HORIZONTAL;  
        
        this.wallbuilder = new Wallbuilder(this.board);
        
        this.continues = true;
        this.building = false;
        
        addActionListener(this);
        setInitialDelay(2000);        
    }
    /**
     * Method inherited from ActionListener; invoked when an action occurs.
     * @param e 
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!continues) {
            // GAME OVER
            return;
        }       
        if (building) {
            this.building = this.wallbuilder.build();
           // here we have to somehow check that ball does not lie on the piece that just turned into wall...
        }
//        this.ball.moveOnBoard(board);
//        if (this.ball.liesOnWall(board)) {
//            continues = false;
//        }
        this.updateable.update();
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

    public void setUpedateable(Updateable upedateable) {
        this.updateable = upedateable;
    }

    public void setBuilding(boolean building) {
        this.building = building;
    }

    public boolean isBuilding() {
        return building;
    }

    public Wallbuilder getWallbuilder() {
        return wallbuilder;
    }

    public SimpleDirection getDirection() {
        return direction;
    }
    
    
    
    

    

    



    

    
}
