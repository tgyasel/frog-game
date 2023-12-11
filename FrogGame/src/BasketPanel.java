import javax.swing.*;
import java.awt.*;

public class BasketPanel extends JPanel {
    private final FrogGame frogGame; // Add this line
    private int basketX = 100;
    private final int basketWidth = 150;
    private final int basketHeight = 50;
    private final int speed = 18;

    // Add this constructor
    public BasketPanel(FrogGame frogGame) {
        this.frogGame = frogGame;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (frogGame.gameState == FrogGame.TITLE_STATE) {
            frogGame.startScreen.drawTitleScreen(g);
        } else {
            g.setColor(new Color(139, 69, 19));
            g.fillRect(basketX, getHeight() - basketHeight, basketWidth, basketHeight);
        }
    }

    public Dimension getPreferredSize() {
        return new Dimension(basketWidth, basketHeight);
    }

    public void moveLeft() {
        basketX -= speed;
        if (basketX < 0) {
            basketX = 0;
        }
        repaint();
    }

    public void moveRight() {
        basketX += speed;
        if (basketX > getWidth() - basketWidth) {
            basketX = getWidth() - basketWidth;
        }
        repaint();
    }

    public Rectangle getBounds() {
        return new Rectangle(basketX, getHeight() - basketHeight, basketWidth, basketHeight);
    }
}

