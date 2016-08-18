package pallopeli.graphics;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import pallopeli.CompassDirection;
import pallopeli.objects.Piece;

/**
 * DrawablePiece provides draw-method for Piece and it draws different picture depending on whether Piece is wall or not.
 * @author saara
 */


public class DrawablePiece implements Drawable {
    private Piece pieceToDraw;
    
    private final Image pieceImage = new ImageIcon("tavallinenpala.png").getImage();
    private final Image wallImage = new ImageIcon("seinapala.png").getImage();    

    public DrawablePiece(Piece pieceToDraw) {
        this.pieceToDraw = pieceToDraw;
    }
    
    

    @Override
    public void draw(Graphics graphics) {
        int x = this.pieceToDraw.getCornerPoint(CompassDirection.NORTHWEST).x;
        int y = this.pieceToDraw.getCornerPoint(CompassDirection.NORTHWEST).y;

        if (this.pieceToDraw.isWall()) {
            graphics.drawImage(wallImage, x, y, null);         
        } else if (!this.pieceToDraw.isWall()) {
            graphics.drawImage(pieceImage, x, y, null);
        }       
    }
    
}
