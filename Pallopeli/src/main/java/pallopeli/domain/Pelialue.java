package pallopeli.domain;

public class Pelialue {
    private int alueenLeveys; // paloina
    private int alueenKorkeus; // paloina
    
    private Pala[][] palat; // pelialueen palat "matriisitaulukkona"
    
    public Pelialue(int alueenLeveys, int alueenKorkeus) {
        // epäkelvon alueen luomisen estäminen
        if (alueenLeveys < 100 && alueenLeveys > 4 && alueenKorkeus < 100 && alueenKorkeus > 4) {
            this.alueenLeveys = alueenLeveys;
            this.alueenKorkeus = alueenKorkeus;
            this.palat = new Pala[alueenKorkeus][alueenLeveys];              
        }    
    }
    public void alustaPalat() {
        // luo eka rivi (kaikki palat seinää)
        for (int l = 0; l < this.alueenLeveys; l++) {
            this.palat[0][l] = new Pala(true, 0, l);
        }        
        // luo välirivit (seinä - ei seinää - seinä)
        for (int k = 1; k < this.alueenKorkeus-1; k++) {
            this.palat[k][0] = new Pala(true, k, 0);
            for (int l = 1; l < this.alueenLeveys-1; l++) {
                this.palat[k][l] = new Pala(false, l, k);
            }
            this.palat[k][this.alueenLeveys-1] = new Pala(true, (this.alueenLeveys-1), k);
        }
        // luo vika rivi (kaikki palat seinää)
        for (int l = 0; l < this.alueenLeveys; l++) {
            this.palat[this.alueenKorkeus-1][l] = new Pala(true, l, (this.alueenKorkeus-1));
        }
    }
    
    public Pala[][] getPalat() {
        return palat;
    }
    
    public String toString() {
        String merkkijono = "";
        for (int k = 0; k < this.alueenKorkeus; k++) {
            for (int l = 0; l < this.alueenLeveys; l++) {
                merkkijono += this.palat[k][l].toString() + " ";
            }
            merkkijono += "\n";
        }
        return merkkijono;
    }
    
    
    
    
    
    
    
    
    
    
    public void testaaTuloste() {
        for (int k = 0; k < this.alueenKorkeus; k++) {
            for (int l = 0; l < this.alueenLeveys; l++) {
                System.out.print(this.palat[k][l].toString() + " ");
            }
            System.out.println("");
        }
    }
//    public void piirraPelialue(Graphics g) {
//        for (int k = 0; k < this.alueenKorkeus; k++) {
//            for (int l = 0; l < this.alueenLeveys; l++) {
//                g.drawImage(palat[k][l].getImage(), palat[k][l].getSijaintiX(), palat[k][l].getSijaintiY(), null);                
//            }
//        }
//    }
    
}
