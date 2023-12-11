import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class FrogGame extends JFrame implements ActionListener {

    public int gameState;
    public FrogPanel frogPanel;
    protected BasketPanel basketPanel;
    private Timer gameTimer;
    protected int score = 0;
    private int highestScore = 0;
    private EndScreen endScreen;
    protected StartScreen startScreen;
    public Image backgroundImage;  // Accessible background image

    public static final int TITLE_STATE = 0;
    public final int PLAY_STATE = 1;
    public final int END_STATE = 2;

    protected final int screenWidth = 800; // Adjusted the screen width
    private final int screenHeight = 600; // Adjusted the screen height
    private int remainingTime = 30;

    Timer mainTimer;

    public FrogGame() {
        super("Frog-Nappers");
        initializeGame();
    }
    // public void actionPerformed(ActionEvent)
    public void actionPerformed(ActionEvent e) {
        frogPanel.repaint();
    }

    private void initializeGame() {
        setSize(screenWidth, screenHeight);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setPreferredSize(new Dimension(screenWidth, screenHeight));

        frogPanel = new FrogPanel(this);
        basketPanel = new BasketPanel(this); // Pass the FrogGame instance to BasketPanel
        add(frogPanel, BorderLayout.CENTER);
        add(basketPanel, BorderLayout.SOUTH);

        setFocusable(true);
        addKeyListener(new MyKeyListener(basketPanel));

        Timer frogTimer = new Timer(2000, e -> addFrog());
        frogTimer.start();

        gameTimer = new Timer(1000, e -> {
            remainingTime--;
            if (remainingTime <= 0) {
                endGame();
            }
        });

        mainTimer = new Timer(10, this);
        mainTimer.setInitialDelay(100);
        mainTimer.start(); 

        // Add the background panel to the content pane
        BackPanel backPanel = new BackPanel();
        add(backPanel);

        setupGame();
        setVisible(true);
    }

    private void setupGame() {
        gameState = TITLE_STATE;
        startScreen = new StartScreen(this, highestScore, TITLE_STATE);

        // Add the StartScreen to the content pane
        getContentPane().add(startScreen, BorderLayout.CENTER);

        revalidate();
        repaint();
        gameTimer.start();
    }


    public void startGame() {
        gameState = PLAY_STATE;
        getContentPane().add(frogPanel, BorderLayout.CENTER);
        getContentPane().add(basketPanel, BorderLayout.SOUTH);
        revalidate();
        repaint();
        gameTimer.start();
    }

    private void addFrog() {
        Frog newFrog = new Frog(this);
        frogPanel.addFrog(newFrog);
        new Timer(50, e -> {
            newFrog.moveDown();
            // System.out.println("froggy position:" + newFrog.getY());
            if (newFrog.getY() > frogPanel.getHeight()) {
                frogPanel.removeFrog(newFrog);
                ((Timer) e.getSource()).stop();
            }
        }).start();
    }

    private void endGame() {
        gameTimer.stop();
        int roundScore = score;
        if (roundScore > highestScore) {
            highestScore = roundScore;
        }
        endScreen = new EndScreen(this, roundScore, highestScore);
        gameState = END_STATE;
        getContentPane().add(endScreen, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public void restartGame() {
        score = 0;
        remainingTime = 30;
        gameState = TITLE_STATE;
        getContentPane().remove(startScreen); // new 
        setupGame();
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        new FrogGame();
    }

    class BackPanel extends JPanel {
        BackPanel() {
            setPreferredSize(new Dimension(screenWidth, screenHeight));
            setBackground(Color.BLUE);

            try {
                backgroundImage = new ImageIcon(getClass().getResource("/images/forest.png")).getImage();
            } catch (Exception e) {
                System.err.println("Error loading background image: " + e.getMessage());
            }
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (backgroundImage != null) {
                // Draw the background image to fill the entire panel while maintaining aspect ratio
                double aspectRatio = (double) backgroundImage.getWidth(null) / backgroundImage.getHeight(null);
                int newWidth = (int) (screenHeight * aspectRatio);
                g.drawImage(backgroundImage, (screenWidth - newWidth) / 2, 0, newWidth, screenHeight, this);
            }
        }
    }
}

