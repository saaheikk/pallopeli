package pallopeli.graphics;
import java.awt.Color;
import java.awt.Graphics;
import pallopeli.objects.Ball;

/**
 * DrawablePiece provides draw-method for Ball.
 * @author saara
 */

public class DrawableBall implements Drawable {
    private Ball ballToDraw;

    public DrawableBall(Ball ballToDraw) {
        this.ballToDraw = ballToDraw;
    }
    
    

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.RED);
        int r = ballToDraw.getRadius();
        g.fillOval(ballToDraw.getCurrentPosition().x - r, ballToDraw.getCurrentPosition().y - r, 2 * r, 2 * r);
        System.out.println("");
        System.out.println("BALL DRAWN AT " + ballToDraw.getCurrentPosition());
        System.out.println("");
    }
    
}
