package pallopeli.logic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.Timer;
import pallopeli.BuildingDirection;
import pallopeli.CompassDirection;
import pallopeli.gui.Updateable;
import pallopeli.objects.Ball;
import pallopeli.objects.Board;
import pallopeli.objects.Piece;

public class Game extends Timer implements ActionListener {    
    private Board board;
    private Ball ball;
    int sizeOfObjects;
    
    private BuildingDirection direction;
    private boolean continues;
    private Updateable upedateable;

    public Game(int sizeOfObjects) {
        super(100, null);
        this.sizeOfObjects = sizeOfObjects;
        this.board = new Board(10, 10, sizeOfObjects);        
        this.ball = new Ball(sizeOfObjects);
        this.ball.setStartingPoint(10 + 5*sizeOfObjects, 10 + 5*sizeOfObjects);
        this.drawSpeedOfBall();
        
        this.direction = BuildingDirection.HORIZONTAL;        
        this.continues = true;  
        addActionListener(this);
        setInitialDelay(2000);        
    }

    public void setNewDirection(BuildingDirection direction) {
        this.direction = direction;
    } 

    
    public void drawSpeedOfBall() {
        Random random = new Random();
        int horizontalSpeed = random.nextInt(6) - 1;
        int verticalSpeed = random.nextInt(6) - 1;
        this.ball.setSpeed(horizontalSpeed, verticalSpeed);        
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
        this.upedateable = upedateable;
    }
    

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!continues) {
            return;
        }        
        int ballXstart = this.ball.getX();
        int ballYstart = this.ball.getY();
        
        this.ball.move();

        ArrayList<Piece> alarmedWallPieces = this.board.getAlarmedWallPieces(ball);
        if (!alarmedWallPieces.isEmpty()) {
            //this.ball.moveOneStepBackwards();
            Piece nearest = null;
            double minimumDistance = 10000;
            for (int i = 0; i < alarmedWallPieces.size(); i++) {
                if (minimumDistance > alarmedWallPieces.get(i).getDistanceToPoint(ballXstart, ballYstart)) {
                    nearest = alarmedWallPieces.get(i);
                }                
            }
            
            this.ball.bounce(nearest.getDirectionWhereToBounce(ballXstart, ballYstart));
        }
        this.upedateable.update();
        
        
    }
    

    



    

    
}
