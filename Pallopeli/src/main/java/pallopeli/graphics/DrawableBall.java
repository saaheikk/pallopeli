package pallopeli.graphics;
import java.awt.Color;
import java.awt.Graphics;
import pallopeli.objects.Ball;

public class DrawableBall implements Drawable {
    private Ball ballToDraw;

    public DrawableBall(Ball ballToDraw) {
        this.ballToDraw = ballToDraw;
    }
    
    

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval(ballToDraw.getCurrentPosition().x, ballToDraw.getCurrentPosition().y, 2 * ballToDraw.getRadius(), 2 * ballToDraw.getRadius());
    }
    
}
