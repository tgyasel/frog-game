import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartScreen extends JPanel {
    private final FrogGame gp;
    private final Font inter;

    public StartScreen(FrogGame frogGame, int highestScore, int gameState) {
        this.gp = frogGame;
        this.inter = new Font("Inter", Font.PLAIN, 12);

        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        // Background image panel
        BackgroundImagePanel backgroundImagePanel = new BackgroundImagePanel();
        add(backgroundImagePanel, BorderLayout.CENTER);

        // Title message
        JLabel titleLabel = new JLabel("Frog Nappers", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
        add(titleLabel, BorderLayout.NORTH);

        // Start button
        JButton startButton = new JButton("Start");
        startButton.setFont(new Font("Arial", Font.PLAIN, 20));
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gp.startGame();
            }
        });
        add(startButton, BorderLayout.SOUTH);
    }

    public void drawTitleScreen(Graphics g) {
        g.setFont(inter);

        if (gp.gameState == FrogGame.TITLE_STATE) {
            drawTitleScreenComponents(g);
        }
        // TODO: Add other conditions if needed
    }

    private void drawTitleScreenComponents(Graphics g) {
        g.setFont(g.getFont().deriveFont(Font.BOLD, 90F));
        String text = "Frog Nappers";
        int x = getXforCenteredText(g, text);
        int y = gp.getHeight() / 2;

        g.setColor(Color.black);
        g.drawString(text, x + 5, y + 5);

        g.setColor(new Color(107, 73, 43));
//        g.drawString(text, x, y);
        g.drawString(text,553,228);
    }

    private int getXforCenteredText(Graphics g, String text) {
        int length = (int) g.getFontMetrics().getStringBounds(text, g).getWidth();
        return gp.getWidth() / 2 - length / 2;
    }


    private class BackgroundImagePanel extends JPanel {

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (gp.backgroundImage != null) {
                // Calculate the scaling factors to fit the window while maintaining aspect ratio
                double scaleX = (double) getWidth() / gp.backgroundImage.getWidth(null);
                double scaleY = (double) getHeight() / gp.backgroundImage.getHeight(null);
                double scale = Math.max(scaleX, scaleY);

                // Calculate the new dimensions

                int newWidth = (int) (gp.backgroundImage.getWidth(null) * scale);
                int newHeight = (int) (gp.backgroundImage.getHeight(null) * scale);

                // Calculate the center position to draw the image
                int x = (getWidth() - newWidth) / 2;
                int y = (getHeight() - newHeight) / 2;

                // Draw the image with the new dimensions
                g.drawImage(gp.backgroundImage, x, y, newWidth, newHeight, this);
            }
        }
    }
}

