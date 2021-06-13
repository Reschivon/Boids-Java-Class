import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.List;

public class Boid {
    double x, y, speedX, speedY;
    List<Boid> allBoids;
    Polygon shape = new Polygon();

    public Boid(double x, double y, List<Boid> allBoids){
        this.x = x;
        this.y = y;

        speedX = Math.random() * 3 - 1.5;
        speedY = Math.random() * 3 - 1.5;

        this.allBoids = allBoids;

        shape.addPoint(0, 7);
        shape.addPoint(-6, -8);
        shape.addPoint(6, -8);
    }

    void update() {
        double centerOfMassX = 0, centerOfMassY = 0;
        double averageDx = 0, averageDy = 0;
        double numNeighbors = 0;

        for (Boid buddy : allBoids) {
            double buddyDistance = Math.hypot(buddy.x - x, buddy.y - y);

            if (buddyDistance < 50 && buddyDistance > 0.01) {
                centerOfMassX += buddy.x;
                centerOfMassY += buddy.y;

                averageDx += buddy.speedX;
                averageDy += buddy.speedY;
                numNeighbors++;
            }

            // move away from very close neighbors (separation)
            if (buddyDistance < 17 && buddyDistance > 0.01) {
                speedX -= (buddy.x - x) * 0.05 * 1/buddyDistance;
                speedY -= (buddy.y - y) * 0.05 * 1/buddyDistance;
            }
        }

        centerOfMassX /= numNeighbors;
        centerOfMassY /= numNeighbors;

        averageDx /= numNeighbors;
        averageDy /= numNeighbors;

        // move to center (cohesion)
        speedX += (centerOfMassX - x) * 0.001;
        speedY += (centerOfMassY - y) * 0.001;

        // move at same speed and direction (alignment)
        speedX += (averageDx - speedX) * 0.005;
        speedY += (averageDy - speedY) * 0.005;

        // limit speed
        double speed = Math.hypot(speedY, speedX);
        if (speed > 5) {
            speedX *= 5 / speed;
            speedY *= 5 / speed;
        }

        x += speedX;
        y += speedY;

        if(x < 130)
            speedX += 0.1;
        if(y < 60)
            speedY += 0.1;
        if(x > 870)
            speedX -= 0.1;
        if(y > 440)
            speedY -= 0.1;
    }

    void draw(Graphics g){
        Graphics2D g2D = (Graphics2D)g;
        AffineTransform old = g2D.getTransform();

        double heading = Math.atan2(speedY, speedX) - Math.PI/2;
        g2D.rotate(heading, x, y);
        shape.translate((int) x, (int) y);

        g2D.setColor(
                new Color(limit(254, speedX * 42 + 128),
                          limit(254, speedY * 42 + 128),
                        100));

        g2D.fillPolygon(shape);
        g2D.setColor(g2D.getColor().brighter());
        g2D.drawPolygon(shape);

        shape.translate((int) -x, (int) -y);
        g2D.setTransform(old);

    }

    int limit(int limit, double num){
        if(num > limit)
            return limit;

        if(num < 0)
            return 0;

        return (int) num;
    }
}
