import javax.swing.*;

public class Boids {
    public static void main(String[] args) {
        JFrame window = new JFrame("Boids Apocalypse");
        ModdedPanel canvas = new ModdedPanel();

        window.add(canvas);

        window.setVisible(true);
        window.setSize(500, 500);
    }
}
