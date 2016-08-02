package pallopeli.gui;

import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import pallopeli.peli.Pallopeli;

public class Kayttoliittyma implements Runnable {
    private Pallopeli pallopeli;
    private Piirtoalusta piirtoalusta;
    private JFrame frame;
    
    public Kayttoliittyma(Pallopeli pallopeli) {
        this.pallopeli = pallopeli;
    }

    @Override
    public void run() {
        frame = new JFrame("Pallopeli");
        frame.setPreferredSize(new Dimension(800, 600));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        luoKomponentit(frame.getContentPane());

        frame.pack();
        frame.setVisible(true);
    }
    
    private void luoKomponentit(Container container) {
        this.piirtoalusta = new Piirtoalusta(this.pallopeli);
        container.add(this.piirtoalusta);
    }
    
}
