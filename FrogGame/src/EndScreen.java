import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EndScreen extends JPanel {
    private final FrogGame frogGame;
    private final int roundScore;
    private final int highestScore;

    public EndScreen(FrogGame frogGame, int roundScore, int highestScore) {
        this.frogGame = frogGame;
        this.roundScore = roundScore;
        this.highestScore = highestScore;

        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        // Game Over message
        JLabel gameOverLabel = new JLabel("Game Over", SwingConstants.CENTER);
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 40));
        add(gameOverLabel, BorderLayout.NORTH);

        // Frogs caught message
        JLabel frogsCaughtLabel = new JLabel("Frogs Caught: " + roundScore, SwingConstants.CENTER);
        frogsCaughtLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        add(frogsCaughtLabel, BorderLayout.CENTER);

        // Highest score message
        JLabel highestScoreLabel = new JLabel("Highest Score: " + highestScore, SwingConstants.CENTER);
        highestScoreLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        add(highestScoreLabel, BorderLayout.CENTER);

        // Restart button
        JButton restartButton = new JButton("Restart");
        restartButton.setFont(new Font("Arial", Font.PLAIN, 20));
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frogGame.restartGame();
            }
        });
        add(restartButton, BorderLayout.SOUTH);
    }
}

