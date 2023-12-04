import javax.swing.*;
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
    private Timer frogTimer;

    public FrogGame() {
        super("Frog-Nappers");
        setSize(640, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frogPanel = new FrogPanel();
        basketPanel = new BasketPanel();
        add(frogPanel, BorderLayout.CENTER);
        add(basketPanel, BorderLayout.SOUTH);

        setFocusable(true);
        addKeyListener(new MyKeyListener());

    //    public class Forest extends JPanel {
      //      final int Panel_Width = 600;
       //     final int panel_height = 480;
     //   }

        frogTimer = new Timer(2000, e -> addFrog());
        frogTimer.start();

        setVisible(true);
    }

    private void addFrog() {
        Frog newFrog = new Frog();
        frogPanel.addFrog(newFrog);
        new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newFrog.moveDown();
                if (newFrog.getY() > getHeight()) {
                    frogPanel.removeFrog(newFrog);
                    ((Timer) e.getSource()).stop();
                }
            }
        }).start();
    }

    public static void main(String[] args) {
        new FrogGame();
    }

    class FrogPanel extends JPanel {

        private List<Frog> frogs = new ArrayList<>();

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

        private int x = new Random().nextInt(getWidth() - 80); // Random initial X position
        private int y = 0;
        private int frogWidth = 80;
        private int frogHeight = 60;

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
            y += 5; // Adjust the speed of the falling frog
        }

        public int getY() {
            return y;
        }

        public void endGame(){    // end game if frog missed
            if (y <= 49) {
                System.exit(0);
            }
            System.out.println (" Game Over");
        }
    }

    class BasketPanel extends JPanel {

        private int basketX = 100;
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

    class FrogCounter {
        private List<FrogCounter> added = new ArrayList<>();

        if(frogs.collision + basket.collision) {

            public void added(Added added) {
                added.add());
            }
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