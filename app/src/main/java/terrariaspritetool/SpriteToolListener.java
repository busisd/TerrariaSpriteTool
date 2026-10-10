package terrariaspritetool;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SpriteToolListener implements KeyListener {
  private JFrame frame;
  private JPanel panel;

  public SpriteToolListener(JFrame frame, JPanel panel) {
    this.frame = frame;
    this.panel = panel;
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_ESCAPE && frame != null) {
      frame.dispose();
    } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
      JLabel label = new JLabel("Test content");
      panel.add(label);
      panel.revalidate();
      panel.repaint();
      System.out.println("!!");
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
  }

  @Override
  public void keyTyped(KeyEvent e) {
  }
}
