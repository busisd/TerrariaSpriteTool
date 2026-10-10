package terrariaspritetool;

import java.awt.Component;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import lombok.Getter;
import lombok.SneakyThrows;
import terrariaspritetool.constants.SpriteData;

public class SpriteRenderer {
  private static final int SCALE = 5;

  private SpriteData spriteData;
  @Getter
  private JPanel panel;
  private ImageIcon img;
  private BufferedImage bufferedImg;
  private JLabel indexLabel;

  private int frameIndex = 2;
  private int totalFrames;

  @SneakyThrows
  public SpriteRenderer(SpriteData spriteData) {
    this.spriteData = spriteData;

    this.panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

    URL imgUrl = this.getClass().getClassLoader().getResource(spriteData.filepath());
    this.bufferedImg = ImageIO.read(imgUrl);

    this.img = new ImageIcon();
    JLabel imgLabel = new JLabel(img);
    imgLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    panel.add(imgLabel);

    JLabel indexLabel = new JLabel("0");
    indexLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    panel.add(indexLabel);

    this.indexLabel = indexLabel;

    this.totalFrames = bufferedImg.getHeight() / spriteData.h();
  }

  public void nextSprite() {
    frameIndex++;
    updateSprite();
  }

  public void previousSprite() {
    frameIndex--;
    updateSprite();
  }

  public void updateSprite() {
    frameIndex = moduloClamp(frameIndex, 2, totalFrames - spriteData.extraFrames());

    img.setImage(bufferedImg.getSubimage(0, spriteData.h() * frameIndex, spriteData.w(), spriteData.h())
        .getScaledInstance(spriteData.w() * SCALE, spriteData.h() * SCALE, Image.SCALE_FAST));
    indexLabel.setText("" + frameIndex);
    panel.repaint();
  }

  private static int moduloClamp(int number, int minIncl, int maxExcl) {
    if (minIncl >= maxExcl) {
      throw new IllegalArgumentException(String.format("Min %d must be strictly less than Max %d", minIncl, maxExcl));
    }

    int range = maxExcl - minIncl;
    while (number >= maxExcl)
      number -= range;
    while (number < minIncl)
      number += range;
    return number;
  }
}
