package terrariaspritetool;

import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
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
  @Getter
  @Setter
  private JLabel indexLabel;

  private int spriteIndex = 0;
  private int totalSprites;

  public SpriteRenderer(JPanel panel, ImageIcon img, BufferedImage bufferedImg, JLabel indexLabel) {
    this.panel = panel;
    this.img = img;
    this.bufferedImg = bufferedImg;
    this.indexLabel = indexLabel;

    this.totalSprites = bufferedImg.getHeight() / SPRITE_HEIGHT;
  }

  public void nextSprite() {
    spriteIndex++;
    spriteIndex = moduloClamp(spriteIndex, 2, 14);
    updateSprite();
  }

  public void previousSprite() {
    spriteIndex--;
    spriteIndex = moduloClamp(spriteIndex, 2, 14);
    updateSprite();
  }

  private void updateSprite() {
    spriteIndex = Math.floorMod(spriteIndex, totalSprites);
    img.setImage(bufferedImg.getSubimage(0, SPRITE_HEIGHT * spriteIndex, SPRITE_WIDTH, SPRITE_HEIGHT)
        .getScaledInstance(SPRITE_WIDTH * 10, SPRITE_HEIGHT * 10, Image.SCALE_FAST));
    indexLabel.setText("" + spriteIndex);
    panel.repaint();
  }

  private static int moduloClamp(int number, int minIncl, int maxExcl) {
    if (minIncl >= maxExcl) {
      throw new IllegalArgumentException(String.format("Min %d must be strictly less than Max %d", minIncl, maxExcl));
    }

    int range = maxExcl - minIncl;
    while (number >= maxExcl) number -= range;
    while (number < minIncl) number += range;
    return number;
  }
}
