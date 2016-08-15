package pallopeli.graphics;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import pallopeli.objects.Piece;


public class DrawablePiece implements Drawable {
    private Piece pieceToDraw;
    
    private final Image pieceImage = new ImageIcon("tavallinenpala.png").getImage();
    private final Image wallImage = new ImageIcon("seinapala.png").getImage();    

    public DrawablePiece(Piece pieceToDraw) {
        this.pieceToDraw = pieceToDraw;
    }
    
    

    @Override
    public void draw(Graphics graphics) {
        if (this.pieceToDraw.isWall()) {
            graphics.drawImage(wallImage, this.pieceToDraw.getAnchor().x, this.pieceToDraw.getAnchor().y, null);         
        } else if (!this.pieceToDraw.isWall()) {
            graphics.drawImage(pieceImage, this.pieceToDraw.getAnchor().x, this.pieceToDraw.getAnchor().y, null);
        }       
    }
    
}
