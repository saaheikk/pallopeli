package pallopeli.peli;

import pallopeli.domain.Pelialue;

public class Pallopeli {
    private Pelialue pelialue;
    private boolean jatkuu;
    
    public Pallopeli() {
        this.pelialue = new Pelialue(20, 15);
        this.pelialue.alustaPalat();
        this.jatkuu = true;
    }
    
    public void muutaAlkupalaSeinaksi(int x, int y) {
//      tarkista, ettei pallo ole seinäksi muutettavan palan alueella
//      if () {
//          this.jatkuu = false;
//      } else {
//            this.pelialue.getPalat()[x][y].muutaSeinaksi();
//      }
    }
    
    public void rakennaYlos(int x, int y) {
        // koordinaattien oikeellisuustarkistukset??
        int i = 1;
        while (y - i > 0) {
//            tarkista, ettei pallo ole seinäksi muutettavan palan alueella
//            if () {
//                break; // return false??
//            }
            this.pelialue.getPalat()[x][y - i].muutaSeinaksi();
            i++;
        }
    }

    public void rakennaAlas(int x, int y) {
        // koordinaattien oikeellisuustarkistukset??
        int i = 1;
        while (y + i < 15) {
//            tarkista, ettei pallo ole seinäksi muutettavan palan alueella
//            if () {
//                break; // return false??
//            }
            this.pelialue.getPalat()[x][y + i].muutaSeinaksi();
            i++;
        }
    }
    
    public void rakennaOikealle(int x, int y) {
        // koordinaattien oikeellisuustarkistukset??
        int i = 1;
        while (x + i < 20) {
//            tarkista, ettei pallo ole seinäksi muutettavan palan alueella
//            if () {
//                break; // return false??
//            }
            this.pelialue.getPalat()[x + i][y].muutaSeinaksi();
            i++;
        }
    }
    public void rakennaVasemmalle(int x, int y) {
        // koordinaattien oikeellisuustarkistukset??
        int i = 1;
        while (x - i > 0) {
//            tarkista, ettei pallo ole seinäksi muutettavan palan alueella
//            if () {
//                break; // return false??
//            }
            this.pelialue.getPalat()[x - i][y].muutaSeinaksi();
            i++;
        }
    }
    
    public void muutaAlueSeinamassaksi() {
        // koordinaattien oikeellisuustarkistukset??
        // pala palalta...?
        
    }
    
    
    
}
