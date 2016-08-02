package pallopeli.domain;

import java.awt.Image;
import javax.swing.ImageIcon;

public class Pala {
    private int koordinaattiX;
    private int koordinaattiY; 
    
    private boolean onSeina;
    
    // private final Image tavallinenPala = new ImageIcon("tavallinenpala.png").getImage();
    // private final Image seinaPala = new ImageIcon("seinapala.png").getImage();
    
    
    public Pala(boolean onSeina, int x, int y) {
        this.onSeina = onSeina;
        this.koordinaattiX = x;
        this.koordinaattiY = y;
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

    public int getKoordinaattiX() {
        return koordinaattiX;
    }

    public int getKoordinaattiY() {
        return koordinaattiY;
    }

    public boolean OnSeina() {
        return onSeina;
    }
    



}
