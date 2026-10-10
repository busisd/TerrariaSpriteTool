package terrariaspritetool;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import terrariaspritetool.App.GlobalContent;

public class SpriteToolListener implements KeyListener {
  private GlobalContent global;

  public SpriteToolListener(GlobalContent global) {
    this.global = global;
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_ESCAPE && global.frame() != null) {
      global.frame().dispose();
    } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
      global.renderer().nextSprite();
    } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
      global.renderer().previousSprite();
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
  }

  @Override
  public void keyTyped(KeyEvent e) {
  }
}
