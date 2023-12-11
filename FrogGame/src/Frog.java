import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Random;

public class Frog {
    private final int x;
    private int y = 0;
    private final int frogWidth = 80;
    private final int frogHeight = 60;
    public boolean collided = false;

    private int offset = 15;

    private FrogGame frogGame;

    public Frog(FrogGame frogGame) {
        this.x = new Random().nextInt(frogGame.screenWidth - 80);
        this.frogGame = frogGame;
    }

    public void draw(Graphics2D g) {
        g.setColor(new Color(85, 214, 85));
        g.fillOval(x, y, frogWidth, frogHeight);

        int eyeSize = 8;
        drawEye(g, x + 20, y + 4, eyeSize);
        drawEye(g, x + 60, y + 4, eyeSize);

        g.setColor(new Color(255, 127, 127));
        g.drawArc(x + 25, y + 35, frogWidth / 2, frogHeight / 4, 0, -180);

        g.fillOval(x + 15, y + 30, eyeSize, eyeSize / 2);
        g.fillOval(x + 70, y + 30, eyeSize, eyeSize / 2);

        int legWidth = 6;
        int legHeight = 15;
        g.setColor(new Color(85, 214, 85));
        g.fillRect(x + 15, y + 50, legWidth, legHeight);
        g.fillRect(x + 65, y + 50, legWidth, legHeight);

        // g.fillRect(x, y, frogWidth, frogHeight); // for debug

    }

    private void drawEye(Graphics g, int x, int y, int size) {
        g.setColor(Color.GREEN);
        g.drawOval(x - size, y - size, 3 * size, size);
        g.fillOval(x - size, y - size, 3 * size, 3 * size);

        double innerCircleFactor = size / 1.2;
        int innerCircleSize = (int) (innerCircleFactor);
        g.setColor(Color.BLACK);
        g.fillOval(x - innerCircleSize, y - innerCircleSize, 2 * innerCircleSize, 2 * innerCircleSize);
    }

    public void moveDown() {
        y += 5;
    }

    public int getY() {
        return y;
    }
    public int getX() {
        return x;
    }

    public boolean intersects(Rectangle basketRect) {
        Rectangle newBasket = new Rectangle((int)basketRect.getX()+offset, (int)(basketRect.getY() + frogGame.screenHeight - 50), 150-offset*2, 50);
        return newBasket.intersects(x, y, frogWidth, frogHeight);
     
    }
}
