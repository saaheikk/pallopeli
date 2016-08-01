package pallopeli.gui;

import java.awt.Graphics;
import javax.swing.JPanel;
import pallopeli.peli.Pallopeli;

class Piirtoalusta extends JPanel {
        private Pallopeli pallopeli;
        
        public Piirtoalusta(Pallopeli pallopeli) {
            this.pallopeli = pallopeli;
        }
    
        @Override
        protected void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            this.pallopeli.getPelialue().piirraPelialue(graphics);
        }

    
}
