import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
 * home/start screen
 * end screen
 */

public class FrogGame extends JFrame {

	public FrogGame() {
		super("Frog-Nappers");
		setSize(840, 640);
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

			// body of the frog
			g.setColor(new Color(85, 214, 85)); // Light Green
			g.fillOval(100, 100, 100, 75);

			// eyes
			drawEye(g, 165, 100, 8);  // Left eye
			drawEye(g, 220, 100, 8);  // Right eye

			// the mouth (smiling)
			g.setColor(new Color(255, 127, 127)); // Salmon
			g.drawArc(160, 190, 40, 20, 0, -180);

			// the cheeks (blush)
			g.fillOval(140, 185, 8, 4);
			g.fillOval(245, 185, 8, 4);

			// the bendy legs
            g.setColor(new Color(85, 214, 85));
			g.fillRect(150, 220, 10, 20); // left
			g.fillRect(230, 220, 10, 20); //right

            // catcher box
            g.setColor(Color.black);
            g.drawRect(320, 442,108,85);
            g.setColor(new Color(89, 45, 29));
            g.fillRect(320, 442,108,85);
		}

		private void drawEye(Graphics g, int x, int y, int size) {
	        // the outer eye oval
	        g.setColor(Color.GREEN);
	        g.drawOval(x - size, y - size, 3 * size, size);
            g.fillOval(x - size, y - size, 3 * size, 3 * size);

	        // the inner eye circle
          double innerCircleFactor = size/1.2;
          int innerCircleSize = (int)(innerCircleFactor);
          g.setColor(Color.BLACK);
	        g.fillOval(x - innerCircleSize, y - innerCircleSize, 2 * innerCircleSize, 2 * innerCircleSize);
	    }

	}
}
