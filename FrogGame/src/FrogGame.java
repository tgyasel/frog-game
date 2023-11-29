import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class FrogGame extends JFrame {
 // private final AnimationPanel animationPanel;

  public FrogGame() {
    super("Frog-Nappers");
    setSize(640, 480);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> new FrogGame().setVisible(true));
  }
}
///