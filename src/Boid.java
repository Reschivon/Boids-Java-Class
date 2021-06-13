import java.awt.*;
import java.util.List;

public class Boid {
    double x, y, speedX, speedY;
    List<Boid> allBoids;

    public Boid(double x, double y, List<Boid> allBoids){
        this.x = x;
        this.y = y;

        speedX = Math.random() * 3 - 1.5;
        speedY = Math.random() * 3 - 1.5;

        this.allBoids = allBoids;
    }

    void update() {
        // move to center (cohesion)
        double xSum = 0, ySum = 0;
        double numNeighbors = 0;
        for (Boid buddy : allBoids) {
            if (Math.hypot(buddy.x - x, buddy.y - y) < 43) {
                xSum += buddy.x;
                ySum += buddy.y;
                numNeighbors++;
            }
        }

        double averageX = xSum / numNeighbors;
        double averageY = ySum / numNeighbors;

        speedX = speedX + (averageX - x) * 0.003;
        speedY = speedY + (averageY - y) * 0.003;


        // move at same speed and direction (alignment)
        double speedXSum = 0, speedYSum = 0;
        numNeighbors = 0;
        for (Boid buddy : allBoids) {
            if (Math.hypot(buddy.x - x, buddy.y - y) < 43) {
                speedXSum += buddy.speedX;
                speedYSum += buddy.speedY;
                numNeighbors++;
            }
        }
        double averageSpeedX = speedXSum / numNeighbors;
        double averageSpeedY = speedYSum / numNeighbors;

        speedX = speedX + (averageSpeedX - speedX) * 0.005;
        speedY = speedY + (averageSpeedY - speedY) * 0.005;



        // move away from very close neighbors (separation)
        for (Boid buddy : allBoids) {
            if (Math.hypot(buddy.x - x, buddy.y - y) < 17) {
                speedX -= (buddy.x - x) * 0.003;
                speedY -= (buddy.y - y) * 0.003;
            }
        }



        // limit speed
        double speed = Math.hypot(speedY, speedX);
        if (speed > 5) {
            speedX *= 5 / speed;
            speedY *= 5 / speed;
        }

        // actually move
        x += speedX;
        y += speedY;

        // push boids away from edges
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
        g.setColor(
                new Color(limit(254, speedX * 42 + 128),
                          limit(254, speedY * 42 + 128),
                        120));

        g.fillOval((int) x, (int) y, 10, 10);
    }

    int limit(int limit, double num){
        if(num > limit)
            return limit;

        if(num < 0)
            return 0;

        return (int) num;
    }
}
