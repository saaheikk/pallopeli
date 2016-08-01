package pallopeli.peli;

import pallopeli.domain.Pelialue;

public class Pallopeli {
    private Pelialue pelialue;
    
    public Pallopeli() {
        this.pelialue = new Pelialue(20, 15);
        this.pelialue.alustaPalat();
    }
    
    public Pallopeli(int l, int k) {
        if (k>4 && l>4) {
            this.pelialue = new Pelialue(l, k);
            this.pelialue.alustaPalat();
        }
    }

    public Pelialue getPelialue() {
        return pelialue;
    }
    
    
}
