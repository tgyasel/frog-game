import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

/* Steps 
 * make frogs 
 * make basket 
 * move basket with moue keys use key listener 
 * every 10 seconds faster and more frogs
 * each level up is 20 seconds aka 2 speed level chnages 
 * each lvl more frogs & faster speed (max amount of frogs each lvl)
 * if it goes under the line then you are eliminated 
 * pause or exit button
 * how many frogs you caught, and the leaderboard
 */

public class FrogGame extends JFrame {
	// private final AnimationPanel animationPanel;

	public FrogGame() {
		super("Frog-Nappers");
		setSize(640, 480);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		FrogPanel frogPanel = new FrogPanel();
		add(frogPanel);

		setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new FrogGame().setVisible(true));
		SwingUtilities.invokeLater(() -> new SimpleFrogDrawing());

	}

	// frog design
	class FrogPanel extends JPanel {

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);

			// Draw the body of the frog
			g.setColor(Color.GREEN);
			g.fillOval(100, 100, 200, 150);

			// Draw the eyes
			g.setColor(Color.BLACK);
			g.fillOval(150, 150, 30, 30);
			g.fillOval(220, 150, 30, 30);

			// Draw the mouth
			g.setColor(Color.RED);
			g.drawArc(160, 190, 80, 40, 0, -180);

			// Draw the legs
			g.setColor(Color.GREEN);
			g.fillRect(130, 240, 20, 80);
			g.fillRect(200, 240, 20, 80);
		}

	}