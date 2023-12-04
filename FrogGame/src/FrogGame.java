import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FrogGame extends JFrame {

    private FrogPanel frogPanel;
    private BasketPanel basketPanel;
    private Basket basket;
    private List<Frog> frogs;

    public FrogGame() {
        super("Frog-Nappers");
        setSize(840, 640);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frogPanel = new FrogPanel();
        basketPanel = new BasketPanel();
        basket = new Basket();
        frogs = new ArrayList<>();

        add(frogPanel, BorderLayout.CENTER);
        add(basketPanel, BorderLayout.SOUTH);
        addKeyListener(new MyKeyListener());
        setFocusable(true);

        Timer timer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveFrogs();
                checkCollisions();
                frogPanel.repaint();
                basketPanel.repaint();
            }
        });
        timer.start();

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FrogGame());
    }

    class FrogPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Draw the frogs
            for (Frog frog : frogs) {
                frog.draw(g);
            }
        }
    }

    class BasketPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
			//basket = new Basket();
            basket.draw(g);
        }
    }

    class Frog {
        private int x;
        private int y;
        private int size = 20;

        public Frog(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void move() {
            y += 5; // Adjust the speed as needed
        }

        public void draw(Graphics g) {
            g.setColor(new Color(85, 214, 85)); // Light Green
            g.fillOval(x, y, size, size);
        }

        public Rectangle getBounds() {
            return new Rectangle(x, y, size, size);
        }
    }

    class Basket {
        private int x;
        private int y;
        private int width = 50;
        private int height = 30;

        public Basket() {
            x = getWidth() / 2 - width / 2;
            y = getHeight() - height;
        }

        public void moveLeft() {
            x -= 10;
            if (x < 0) {
                x = 0;
            }
        }

        public void moveRight() {
            x += 10;
            if (x > getWidth() - width) {
                x = getWidth() - width;
            }
        }

        public void draw(Graphics g) {
            g.setColor(Color.BLUE);
            g.fillRect(x, y, width, height);
        }

        public Rectangle getBounds() {
            return new Rectangle(x, y, width, height);
        }
    }

    class MyKeyListener implements KeyListener {
        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            if (keyCode == KeyEvent.VK_LEFT) {
                basket.moveLeft();
            } else if (keyCode == KeyEvent.VK_RIGHT) {
                basket.moveRight();
            }
            repaint();
        }

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }
    }

    private void moveFrogs() {
        for (Frog frog : frogs) {
            frog.move();
        }

        // Generate new frogs at the top
        if (Math.random() < 0.05) { // Adjust the probability as needed
            int x = new Random().nextInt(getWidth() - 20); // 




// import javax.swing.*;
// import java.awt.*;
// import java.awt.event.KeyEvent;
// import java.awt.event.KeyListener;

// public class FrogGame extends JFrame {

//     private FrogPanel frogPanel;
//     private Basket basket;

//     public FrogGame() {
//         super("Frog-Nappers");
//         setSize(840, 640);
//         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//         frogPanel = new FrogPanel();
//         basket = new Basket();
// 		basketPanel = new BasketPanel();

//         add(frogPanel);
//         addKeyListener(new MyKeyListener());
//         setFocusable(true);

//         setVisible(true);

// 		add(BasketPanel);
//         addKeyListener(new MyKeyListener());
//         setFocusable(true);

//         setVisible(true);
//     }

//     public static void main(String[] args) {
//         SwingUtilities.invokeLater(() -> new FrogGame());
//     }

//     class FrogPanel extends JPanel {

//         @Override
//         protected void paintComponent(Graphics g) {
//             super.paintComponent(g);

//             // body of the frog
//             g.setColor(new Color(85, 214, 85)); // Light Green
//             g.fillOval(100, 100, 100, 75);

//             // eyes
//             drawEye(g, 165, 100, 8);  // Left eye
//             drawEye(g, 220, 100, 8);  // Right eye

//             // the mouth (smiling)
//             g.setColor(new Color(255, 127, 127)); // Salmon
//             g.drawArc(160, 190, 40, 20, 0, -180);

//             // the cheeks (blush)
//             g.fillOval(140, 185, 8, 4);
//             g.fillOval(245, 185, 8, 4);

//             // the bendy legs
//             g.setColor(new Color(85, 214, 85));
//             g.fillRect(150, 220, 10, 20); // left
//             g.fillRect(230, 220, 10, 20); //right

//             // Draw the basket
//             basket.draw(g);
//         }

//         private void drawEye(Graphics g, int x, int y, int size) {
//             // the outer eye oval
//             g.setColor(Color.GREEN);
//             g.drawOval(x - size, y - size, 3 * size, size);
//             g.fillOval(x - size, y - size, 3 * size, 3 * size);

//             // the inner eye circle
//             double innerCircleFactor = size / 1.2;
//             int innerCircleSize = (int) (innerCircleFactor);
//             g.setColor(Color.BLACK);
//             g.fillOval(x - innerCircleSize, y - innerCircleSize, 2 * innerCircleSize, 2 * innerCircleSize);
//         }
//     }

//     class Basket {
//         private int x;
//         private int y;
//         private int width = 50;
//         private int height = 30;

//         public Basket() {
//             x = getWidth() / 2 - width / 2;
//             y = getHeight() - height;
//         }

//         public void moveLeft() {
//             x -= 10;
//             if (x < 0) {
//                 x = 0;
//             }
//         }

//         public void moveRight() {
//             x += 10;
//             if (x > getWidth() - width) {
//                 x = getWidth() - width;
//             }
//         }

//         public void draw(Graphics g) {
//             g.setColor(Color.BLUE);
//             g.fillRect(x, y, width, height);
//         }
//     }

//     class MyKeyListener implements KeyListener {
//         @Override
//         public void keyPressed(KeyEvent e) {
//             int keyCode = e.getKeyCode();
//             if (keyCode == KeyEvent.VK_LEFT) {
//                 basket.moveLeft();
//             } else if (keyCode == KeyEvent.VK_RIGHT) {
//                 basket.moveRight();
//             }
//             repaint();
//         }

//         @Override
//         public void keyTyped(KeyEvent e) {
//         }

//         @Override
//         public void keyReleased(KeyEvent e) {
//         }
//     }
// }

