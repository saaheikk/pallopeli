package pallopeli.logic;

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
    
    private SimpleDirection direction;
    private boolean continues;
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
        
        this.continues = true;  
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
            return;
        }      
        this.ball.moveOnBoard(board);
//        original bouncing that uses the ugly methods in class Piece (does not work perfectly either):
//        int ballXstart = this.ball.getX();
//        int ballYstart = this.ball.getY();
//        
//        this.ball.move();
//
//        ArrayList<Piece> alarmedWallPieces = this.board.getAlarmedWallPieces(ball);
//        if (!alarmedWallPieces.isEmpty()) {
//            //this.ball.moveOneStepBackwards();
//            Piece nearest = null;
//            double minimumDistance = 10000;
//            for (int i = 0; i < alarmedWallPieces.size(); i++) {
//                if (minimumDistance > alarmedWallPieces.get(i).getDistanceToAPoint(ballXstart, ballYstart)) {
//                    nearest = alarmedWallPieces.get(i);
//                }                
//            }            
//            this.ball.bounce(nearest.getDirectionWhereToBounce(ballXstart, ballYstart));
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

    

    



    

    
}
