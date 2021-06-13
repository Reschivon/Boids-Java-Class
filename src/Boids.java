import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Boids {
    public static void main(String[] args) throws InterruptedException {
        ArrayList<Boid> boids = new ArrayList<>();

        JFrame window = new JFrame("Boids Apocalypse");
        ModdedPanel canvas = new ModdedPanel(boids);
        canvas.setBackground(new Color(170, 200, 220));

        window.add(canvas);

        window.setVisible(true);
        window.setSize(1000, 500);

        for(int i = 0; i < 100; i++)
            boids.add(new Boid((int) (Math.random() * 500), (int) (Math.random() * 500), boids));

        while (true){
            Thread.sleep(10);

            for(Boid boid : boids)
                boid.update();

            canvas.repaint();
        }
    }
}
