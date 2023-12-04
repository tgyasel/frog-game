import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class FrogGame extends JFrame {

    private FrogPanel frogPanel;
    private BasketPanel basketPanel;

    public FrogGame() {
        super("Frog-Nappers");
        setSize(640, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frogPanel = new FrogPanel();
        basketPanel = new BasketPanel(); // Add BasketPanel
        add(frogPanel, BorderLayout.CENTER);
        add(basketPanel, BorderLayout.SOUTH); // Position BasketPanel at the bottom

        setFocusable(true);
        addKeyListener(new MyKeyListener());

        new FrogGenerator().start(); // Start the frog generator thread

        setVisible(true);
    }

    private class FrogGenerator extends Thread {
        @Override
        public void run() {
            while (true) {
                addFrog();
                try {
                    Thread.sleep(2000); // Wait for 2 seconds before adding the next frog
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void addFrog() {
        Frog newFrog = new Frog(10, 10);
        frogPanel.addFrog(newFrog);
        new Thread(() -> {
            while (newFrog.getY() < getHeight()) {
                newFrog.moveDown();
                try {
                    Thread.sleep(50); // Adjust the speed of the falling frog
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            frogPanel.removeFrog(newFrog);
        }).start();
    }

    public static void main(String[] args) {
        new FrogGame();
    }

    // Frog design
    class FrogPanel extends JPanel {

        private java.util.List<Frog> frogs = new java.util.ArrayList<>();

        public void addFrog(Frog frog) {
            frogs.add(frog);
            repaint();
        }

        public void removeFrog(Frog frog) {
            frogs.remove(frog);
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (Frog frog : frogs) {
                frog.draw(g);
            }
        }
    }

    class Frog {

        private int x;
        private int y;
        private int frogWidth = 80; // Adjust the width of the frog
        private int frogHeight = 60; // Adjust the height of the frog

        public Frog(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void draw(Graphics g) {
            g.setColor(new Color(85, 214, 85)); // Light Green
            g.fillOval(x, y, frogWidth, frogHeight);

            int eyeSize = 8; // Adjust the size of the eyes
            drawEye(g, x + 20, y + 4, eyeSize);  // Left eye
            drawEye(g, x + 60, y + 4, eyeSize);  // Right eye

            g.setColor(new Color(255, 127, 127)); // Salmon
            g.drawArc(x + 22, y + 33, frogWidth / 2, frogHeight / 4, 0, -180);

            g.fillOval(x + 15, y + 30, eyeSize, eyeSize / 2);
            g.fillOval(x + 70, y + 30, eyeSize, eyeSize / 2);

            int legWidth = 6; // Adjust the width of the legs
            int legHeight = 15; // Adjust the height of the legs
            g.setColor(new Color(85, 214, 85));
            g.fillRect(x + 15, y + 50, legWidth, legHeight); // left
            g.fillRect(x + 65, y + 50, legWidth, legHeight); // right
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
            y += 5; // Adjust the speed of the falling frog
        }

        public int getY() {
            return y;
        }
    }

    // Basket design
    class BasketPanel extends JPanel {

        private int basketX = 100; // Adjust the initial position as needed
        private int basketWidth = 150;
        private int basketHeight = 50;

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(new Color(139, 69, 19));
            g.fillRect(basketX, getHeight() - basketHeight, basketWidth, basketHeight);
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
    }

    class MyKeyListener implements KeyListener {
        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) {
                ((BasketPanel) getContentPane().getComponent(1)).moveLeft();
            } else if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) {
                ((BasketPanel) getContentPane().getComponent(1)).moveRight();
            }
        }

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }
    }
}

//   import javax.swing.*;
// import java.awt.*;
// import java.awt.event.KeyEvent;
// import java.awt.event.KeyListener;

// public class FrogGame extends JFrame {

//     public FrogGame() {
//         super("Frog-Nappers");
//         setSize(640, 480);
//         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//         FrogPanel frogPanel = new FrogPanel();
//         BasketPanel basketPanel = new BasketPanel(); // Add BasketPanel
//         add(frogPanel, BorderLayout.CENTER);
//         add(basketPanel, BorderLayout.SOUTH); // Position BasketPanel at the bottom

//         setFocusable(true);
//         addKeyListener(new MyKeyListener());

//         setVisible(true);
//     }

//     public static void main(String[] args) {
//         new FrogGame();
//     }

//     // Frog design
//  class FrogPanel extends JPanel {

//      @Override
//      protected void paintComponent(Graphics g) {
//             super.paintComponent(g);
//             super.paintComponent(g);

//              // Draw the body of the frog (adjusted size)
//             int frogWidth = 80; // Adjust the width of the frog
//             int frogHeight = 60; // Adjust the height of the frog
//             g.setColor(new Color(85, 214, 85)); // Light Green
//             g.fillOval(100, 100, frogWidth, frogHeight);

//             // Draw two eyes at the top of the head (adjusted size)
//             int eyeSize = 8; // Adjust the size of the eyes
//             drawEye(g, 120, 100, eyeSize);  // Left eye
//             drawEye(g, 160, 100, eyeSize);  // Right eye

//             // Draw the mouth (smiling)
//             g.setColor(new Color(255, 127, 127)); // Salmon
//             g.drawArc(125, 135, frogWidth / 2, frogHeight / 4, 0, -180);

//             // Draw the cheeks (blush)
//             g.fillOval(115, 130, eyeSize, eyeSize / 2); // made smaller numbers
//             g.fillOval(170, 130, eyeSize, eyeSize / 2);

//             // Draw the bendy legs (adjusted size)
//             int legWidth = 6; // Adjust the width of the legs
//             int legHeight = 15; // Adjust the height of the legs
//             g.setColor(new Color(85, 214, 85));
//             g.fillRect(115, 150, legWidth, legHeight); // left
//             g.fillRect(165, 150, legWidth, legHeight); // right
//      }

//      private void drawEye(Graphics g, int x, int y, int size) {
//          // Draw the outer oval
//          g.setColor(Color.GREEN);
//          g.drawOval(x - size, y - size, 3 * size, size);
//             g.fillOval(x - size, y - size, 3 * size, 3 * size);

//          // Draw the inner circle
//           double innerCircleFactor = size/1.2;
//           int innerCircleSize = (int)(innerCircleFactor);
//           g.setColor(Color.BLACK);
//          g.fillOval(x - innerCircleSize, y - innerCircleSize, 2 * innerCircleSize, 2 * innerCircleSize);
//      }

//  }

//     // Basket design
//     class BasketPanel extends JPanel {

//         private int basketX = 100; // Adjust the initial position as needed
//         private int basketWidth = 150;
//         private int basketHeight = 50;

//         @Override
//         protected void paintComponent(Graphics g) {
//             super.paintComponent(g);
//             g.setColor(new Color(139, 69, 19));
//             g.fillRect(basketX, getHeight() - basketHeight, basketWidth, basketHeight);
//         }

//         public Dimension getPreferredSize() {
//             return new Dimension(basketWidth, basketHeight);
//         }

//         public void moveLeft() {
//             basketX -= 10;
//             if (basketX < 0) {
//                 basketX = 0;
//             }
//             repaint();
//         }

//         public void moveRight() {
//             basketX += 10;
//             if (basketX > getWidth() - basketWidth) {
//                 basketX = getWidth() - basketWidth;
//             }
//             repaint();
//         }
//     }

//     class MyKeyListener implements KeyListener {
//         @Override
//         public void keyPressed(KeyEvent e) {
//             int keyCode = e.getKeyCode();
//             if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) {
//                 ((BasketPanel) getContentPane().getComponent(1)).moveLeft();
//             } else if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) {
//                 ((BasketPanel) getContentPane().getComponent(1)).moveRight();
//             }
//         }

//         @Override
//         public void keyTyped(KeyEvent e) {
//         }

//         @Override
//         public void keyReleased(KeyEvent e) {
//         }
//     }
// }

