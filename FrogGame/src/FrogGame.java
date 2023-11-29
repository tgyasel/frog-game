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
 * each level up is 20 seconds aka 2 speed level changes
 * each lvl more frogs & faster speed (max amount of frogs each lvl)
 * if it goes under the line then you are eliminated 
 * pause or exit button
 * how many frogs you caught, and the leaderboard
 */

public class FrogGame extends JFrame {

	public FrogGame() {
		super("Frog-Nappers");
		setSize(640, 480);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		FrogPanel frogPanel = new FrogPanel();
		add(frogPanel);

		setVisible(true);
	}

	public static void main(String[] args) {
		//SwingUtilities.invokeLater(() -> new FrogGame().setVisible(true));
		new FrogGame();


	}

	// frog design
	class FrogPanel extends JPanel {

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);

			// Draw the body of the frog
			g.setColor(new Color(85, 214, 85)); // Light Green
			g.fillOval(100, 100, 200, 150);

			// Draw two eyes at the top of the head
			drawEye(g, 165, 120, 15);  // Left eye
			drawEye(g, 220, 120, 15);  // Right eye

			// Draw the mouth (smiling)
			g.setColor(new Color(255, 127, 127)); // Salmon
			g.drawArc(160, 200, 80, 40, 0, -180);

			// Draw the cheeks (blush)
			g.fillOval(140, 185, 15, 10);
			g.fillOval(245, 185, 15, 10);

			// Draw the bendy legs
			g.setColor(Color.GREEN);
			g.fillRect(130, 240, 20, 80);
			g.fillRect(200, 240, 20, 80);
		}

		private void drawEye(Graphics g, int x, int y, int size) {
	        // Draw the outer oval
	        g.setColor(Color.BLACK);
	        g.drawOval(x - size, y - size, 2 * size, size);

	        // Draw the inner circle
	        int innerCircleSize = size / 2;
	        g.fillOval(x - innerCircleSize, y - innerCircleSize, 2 * innerCircleSize, 2 * innerCircleSize);
	    }

	}
}
