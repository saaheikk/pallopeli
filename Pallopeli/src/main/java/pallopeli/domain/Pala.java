package pallopeli.domain;

import java.awt.Image;
import javax.swing.ImageIcon;

public class Pala {
    private int koordinaattiX;
    private int koordinaattiY; 
    
    private boolean seina;
    
    // private final Image tavallinenPala = new ImageIcon("tavallinenpala.png").getImage();
    // private final Image seinaPala = new ImageIcon("seinapala.png").getImage();
    
    
    public Pala(boolean seina, int x, int y) {
        this.seina = seina;
        this.koordinaattiX = x;
        this.koordinaattiY = y;
    }
    
    public void muutaSeinaksi() {
        this.seina = true;
    }
    
    @Override
    public String toString() {
        if (this.seina) {
            return "on-seinä";
        }
        return "ei-seinä";
    }

    public int getKoordinaattiX() {
        return koordinaattiX;
    }

    public int getKoordinaattiY() {
        return koordinaattiY;
    }
    
    public boolean muutaYlempiPalaSeinaksi() {
        // oikeellisuustarkistukset!
        // palan pitäisi tuntea naapuripalat!!!
        return false;
    }

    public boolean isSeina() {
        return seina;
    }
    



}
