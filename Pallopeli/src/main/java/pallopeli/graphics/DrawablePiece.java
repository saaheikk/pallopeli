package pallopeli.graphics;

import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import pallopeli.CompassDirection;
import pallopeli.objects.Piece;

/**
 * DrawablePiece provides draw-method for Piece and it draws different picture depending on whether Piece is wall or not.
 * @author saara
 */


public class DrawablePiece implements Drawable {
    private Piece pieceToDraw;

    private final Image pieceImage = new ImageIcon(getClass().getResource("/tavallinenpala.png")).getImage();
    private final Image wallImage = new ImageIcon(getClass().getResource("/seinapala.png")).getImage();    
    private final Image underConstruction = new ImageIcon(getClass().getResource("/underconstruction.png")).getImage();
    
    public DrawablePiece(Piece pieceToDraw) {
        this.pieceToDraw = pieceToDraw;
    }


    @Override
    public void draw(Graphics graphics) {
        int x = this.pieceToDraw.getCornerPoint(CompassDirection.NORTHWEST).x;
        int y = this.pieceToDraw.getCornerPoint(CompassDirection.NORTHWEST).y;

        if (this.pieceToDraw.isWall()) {
            graphics.drawImage(wallImage, x, y, null);         
        } else if (this.pieceToDraw.isUnderConstruction()) {
            graphics.drawImage(underConstruction, x, y, null);
        } else {
            graphics.drawImage(pieceImage, x, y, null);
        }       
    }
    
}
