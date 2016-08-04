package pallopeli.domain;

public class Pallo {
    private int sijaintiX;
    private int sijaintiY;
    private int liikesuunta; // x-akseli on nollakulma ja kulma kasvaa siitä vastapäivään
    
    public Pallo(int x, int y, int liikesuunta) {
        this.sijaintiX = x;
        this.sijaintiY = y;
        this.liikesuunta = liikesuunta;
    }
    
    public void kimpoaSeinasta() {

    }
    

}
