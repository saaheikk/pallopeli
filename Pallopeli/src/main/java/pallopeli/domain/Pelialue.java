package pallopeli.domain;

import java.awt.Graphics;

public class Pelialue {
    private int alueenLeveys; // paloina
    private int alueenKorkeus; // paloina
    private int palanLeveys; // pikseleinä
    private int palanKorkeus; // pikseleinä
    
    private Pala[][] palat; // pelialueen palat "matriisitaulukkona"
    
    public Pelialue(int alueenLeveys, int alueenKorkeus) {
        this.alueenLeveys = alueenLeveys;
        this.alueenKorkeus = alueenKorkeus;
        this.palat = new Pala[alueenKorkeus][alueenLeveys];
        
    }
    public void alustaPalat() {
        // luo eka rivi (kaikki palat seinää)
        for (int l = 0; l < this.alueenLeveys; l++) {
            this.palat[0][l] = new Pala(true, 0, 30*l);
        }        
        // luo välirivit (seinä - ei seinää - seinä)
        for (int k = 1; k < this.alueenKorkeus-1; k++) {
            this.palat[k][0] = new Pala(true, 30*k, 0);
            for (int l = 1; l < this.alueenLeveys-1; l++) {
                this.palat[k][l] = new Pala(false, 30*l, 30*k);
            }
            this.palat[k][this.alueenLeveys-1] = new Pala(true, 30*(this.alueenLeveys-1), 30*k);
        }
        // luo vika rivi (kaikki palat seinää)
        for (int l = 0; l < this.alueenLeveys; l++) {
            this.palat[this.alueenKorkeus-1][l] = new Pala(true, 30*l, 30*(this.alueenKorkeus-1));
        }
    }
    
    public void piirraPelialue(Graphics g) {
        for (int k = 0; k < this.alueenKorkeus; k++) {
            for (int l = 0; l < this.alueenLeveys; l++) {
                g.drawImage(palat[k][l].getImage(), palat[k][l].getSijaintiX(), palat[k][l].getSijaintiY(), null);                
            }
        }
    }

    public Pala[][] getPalat() {
        return palat;
    }
    
    
    
    
    
    
    
    
    
    
    public void testaaTuloste() {
        for (int k = 0; k < this.alueenKorkeus; k++) {
            for (int l = 0; l < this.alueenLeveys; l++) {
                System.out.print(this.palat[k][l].toString() + " ");
            }
            System.out.println("");
        }
    }
    
}
