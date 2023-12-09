import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// current problem:
/* background image seems to be at the top of the pag
* while the buttons and title are under it ->
* only visble when you manipulate and move around teh sizing of the window
* need to pick a new window size and chose another technique for image sizing
 */
public class FrogGame extends JFrame {

    public int gameState;
    private FrogPanel frogPanel;
    private BasketPanel basketPanel;
    private Timer frogTimer;
    private Timer gameTimer;
    private int score = 0;
    private int roundScore = 0;
    private int highestScore = 0;
    private EndScreen endScreen;
    private StartScreen startScreen;
    public Image backgroundImage;  // Accessible background image

    public static final int TITLE_STATE = 0;
    public final int PLAY_STATE = 1;
    public final int END_STATE = 2;

    private final int screenWidth = 640;
    private final int screenHeight = 480;
    private int remainingTime = 30;

    public FrogGame() {
        super("Frog-Nappers");
        initializeGame();
    }

    private void initializeGame() {
        setSize(screenWidth, screenHeight);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frogPanel = new FrogPanel();
        basketPanel = new BasketPanel();
        add(frogPanel, BorderLayout.CENTER);
        add(basketPanel, BorderLayout.SOUTH);

        setFocusable(true);
        addKeyListener(new MyKeyListener());

        frogTimer = new Timer(2000, e -> addFrog());
        frogTimer.start();

        gameTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remainingTime--;
                if (remainingTime <= 0) {
                    endGame();
                }
            }
        });

        // Add the background panel to the content pane
        BackPanel backPanel = new BackPanel();
        add(backPanel);

        setupGame();
        setVisible(true);
    }

    private void setupGame() {
        gameState = TITLE_STATE;
        startScreen = new StartScreen(this, highestScore, TITLE_STATE);
        revalidate();
        repaint();
        gameTimer.start();
    }

    public void startGame() {
        gameState = PLAY_STATE;
        getContentPane().removeAll();
        revalidate();
        repaint();
        gameTimer.start();
    }

    private void addFrog() {
        Frog newFrog = new Frog();
        frogPanel.addFrog(newFrog);
        new Timer(50, e -> {
            newFrog.moveDown();
            if (newFrog.getY() > frogPanel.getHeight()) {
                frogPanel.removeFrog(newFrog);
                ((Timer) e.getSource()).stop();
            }
        }).start();
    }

    private void endGame() {
        gameTimer.stop();
        roundScore = score;
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
        getContentPane().remove(endScreen);
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
                // Draw the background image to fill the entire panel
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

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

    class FrogPanel extends JPanel {
        private final List<Frog> frogs = new ArrayList<>();

        public void addFrog(Frog frog) {
            frogs.add(frog);
            repaint();
        }

        public void removeFrog(Frog frog) {
            frogs.remove(frog);
            repaint();
        }

        private void updateScore() {
            score++;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (gameState == TITLE_STATE) {
                startScreen.drawTitleScreen(g);
            } else {
                for (Frog frog : frogs) {
                    frog.draw(g);
                    if (frog.intersects(basketPanel.getBounds())) {
                        updateScore();
                    }
                }

                g.setColor(Color.BLACK);
                g.drawString("Score: " + score, getWidth() - 70, 20);
            }
        }
    }

    class Frog {
        private final int x = new Random().nextInt(screenWidth - 80);
        private int y = 0;
        private final int frogWidth = 80;
        private final int frogHeight = 60;

        public void draw(Graphics g) {
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

        public boolean intersects(Rectangle rect) {
            return rect.intersects(x, y, frogWidth, frogHeight);
        }
    }

    class BasketPanel extends JPanel {
        private int basketX = 100;
        private final int basketWidth = 150;
        private final int basketHeight = 50;

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (gameState == TITLE_STATE) {
                startScreen.drawTitleScreen(g);

            } else {
                g.setColor(new Color(139, 69, 19));
                g.fillRect(basketX, getHeight() - basketHeight, basketWidth, basketHeight);
            }
        }

        public Dimension getPreferredSize() {
            return new Dimension(basketWidth, basketHeight);
        }

        public void moveLeft() {
            basketX -= 10;
            if (basketX < 0) {
                basketX = 0;
            }
            repaint();
        }

        public void moveRight() {
            basketX += 10;
            if (basketX > getWidth() - basketWidth) {
                basketX = getWidth() - basketWidth;
            }
            repaint();
        }

        public Rectangle getBounds() {
            return new Rectangle(basketX, getHeight() - basketHeight, basketWidth, basketHeight);
        }
    }

    class MyKeyListener implements KeyListener {
        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) {
                basketPanel.moveLeft();
            } else if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) {
                basketPanel.moveRight();
            }
        }

        @Override
        public void keyTyped(KeyEvent e) {
            // Not used
        }

        @Override
        public void keyReleased(KeyEvent e) {
            // Not used
        }
    }

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

            if (gameState == FrogGame.TITLE_STATE) {
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
            g.drawString(text, x, y);
        }

        private int getXforCenteredText(Graphics g, String text) {
            int length = (int) g.getFontMetrics().getStringBounds(text, g).getWidth();
            return gp.getWidth() / 2 - length / 2;
        }

        private class BackgroundImagePanel extends JPanel {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                // Draw the background image to fill the entire panel
                if (gp.backgroundImage != null) {
                    g.drawImage(gp.backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        }
    }
}
