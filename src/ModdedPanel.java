import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ModdedPanel extends JPanel {
    ArrayList<Boid> boids;

    public ModdedPanel(ArrayList<Boid> boids){
        this.boids = boids;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for(Boid goon : boids){
            goon.draw(g);
        }

    }
}
