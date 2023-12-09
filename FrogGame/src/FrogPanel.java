import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FrogPanel extends JPanel {
    private final List<Frog> frogs = new ArrayList<>();
    private final FrogGame frogGame;

    public FrogPanel(FrogGame frogGame) {
        this.frogGame = frogGame;
    }

    public void addFrog(Frog frog) {
        frogs.add(frog);
        repaint();
    }

    public void removeFrog(Frog frog) {
        frogs.remove(frog);
        repaint();
    }

    private void updateScore() {
        frogGame.score++;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (frogGame.gameState == FrogGame.TITLE_STATE) {
            frogGame.startScreen.drawTitleScreen(g);
        } else {
            for (Frog frog : frogs) {
                frog.draw((Graphics2D) g);
                if (frog.intersects(frogGame.basketPanel.getBounds())) {
                    updateScore();
                }
            }

            g.setColor(Color.BLACK);
            g.drawString("Score: " + frogGame.score, getWidth() - 70, 20);
        }
    }
}

