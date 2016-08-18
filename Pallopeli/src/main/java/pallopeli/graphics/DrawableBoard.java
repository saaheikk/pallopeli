package pallopeli.graphics;

import java.awt.Graphics;
import pallopeli.objects.Board;

/**
 * DrawableBoard provides draw-method for Board and it draws every Piece on Board by using DrawablePiece.
 * @author saara
 */

public class DrawableBoard implements Drawable {
    private Board boardToDraw;
    
    public DrawableBoard(Board boardToDraw) {
        this.boardToDraw = boardToDraw;
    }    
    
    @Override
    public void draw(Graphics g) {
        DrawablePiece drawablePiece = null;
        for (int h = 0; h < boardToDraw.getHeight(); h++) {                       
            for (int w = 0; w < boardToDraw.getWidth(); w++) { 
                drawablePiece = new DrawablePiece(boardToDraw.getPiece(w, h));
                drawablePiece.draw(g);
            }   
        }        
    }    
    
}
