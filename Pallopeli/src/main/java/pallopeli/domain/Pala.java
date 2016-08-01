package pallopeli.domain;

import java.awt.Image;
import javax.swing.ImageIcon;

public class Pala {
    private int sijaintiX; // vasen yläkulma
    private int sijaintiY; // vasen yläkulma
    private int leveys; // pikseleinä
    private int korkeus; // pikseleinä
    
    private boolean onSeina;
    
    private final Image tavallinenPala = new ImageIcon("tavallinenpala.png").getImage();
    private final Image seinaPala = new ImageIcon("seinapala.png").getImage();
    
    
    public Pala(boolean onSeina, int x, int y) {
        this.onSeina = onSeina;
        this.sijaintiX = x;
        this.sijaintiY = y;
        // oletus: tavallinen pala ja seinäpala ovat samankokoisia 
        this.leveys = tavallinenPala.getWidth(null); 
        this.korkeus = tavallinenPala.getHeight(null);
    }
    
    public Image getImage() {
        if (this.onSeina) {
            return seinaPala;
        }
        return this.tavallinenPala;
    }
    
    public void muutaSeinaksi() {
        this.onSeina = true;
    }
    
    @Override
    public String toString() {
        if (this.onSeina) {
            return "on-seinä";
        }
        return "ei-seinä";
    }

    public int getSijaintiX() {
        return sijaintiX;
    }

    public int getSijaintiY() {
        return sijaintiY;
    }

}
