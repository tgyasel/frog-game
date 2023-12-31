import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class FrogPanel extends JPanel  {
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
                
                System.out.println("box:" + frogGame.basketPanel.getBounds());
                System.out.println("\t" + frog.getX() + " " + frog.getY() + "\n");

                // if (frog.intersects(frogGame.basketPanel.getBounds()) && !frog.collided) {
                if (frog.intersects(frogGame.basketPanel.getBounds()) && !frog.collided) {
                    System.out.println("COLLISION: " + frogGame.basketPanel.getBounds());
                    System.out.println("\t" + frog.getX() + " " + frog.getY());
                    System.out.println("");
                    updateScore();
                    frog.collided = true;
                }
            }

            g.setColor(Color.BLACK);
            g.drawString("Score: " + frogGame.score, getWidth() - 70, 20);
        }
    }
}

