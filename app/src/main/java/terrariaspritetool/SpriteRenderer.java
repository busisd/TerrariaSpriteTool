package terrariaspritetool;

import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import lombok.Getter;
import lombok.Setter;

public class SpriteRenderer {
  private static final int SPRITE_WIDTH = 20;
  private static final int SPRITE_HEIGHT = 29;

  @Getter
  @Setter
  private JPanel panel;
  @Getter
  @Setter
  private ImageIcon img;
  @Getter
  @Setter
  private BufferedImage bufferedImg;

  private int spriteIndex = 0;
  private int totalSprites;

  public SpriteRenderer(JPanel panel, ImageIcon img, BufferedImage bufferedImg) {
    this.panel = panel;
    this.img = img;
    this.bufferedImg = bufferedImg;

    this.totalSprites = bufferedImg.getHeight() / SPRITE_HEIGHT;
  }

  public void nextSprite() {
    spriteIndex++;
    updateSprite();
  }

  public void previousSprite() {
    spriteIndex--;
    updateSprite();
  }

  private void updateSprite() {
    spriteIndex = Math.floorMod(spriteIndex, totalSprites);
    img.setImage(bufferedImg.getSubimage(0, SPRITE_HEIGHT * spriteIndex, SPRITE_WIDTH, SPRITE_HEIGHT)
        .getScaledInstance(SPRITE_WIDTH * 10, SPRITE_HEIGHT * 10, Image.SCALE_FAST));
    panel.repaint();
  }
}
