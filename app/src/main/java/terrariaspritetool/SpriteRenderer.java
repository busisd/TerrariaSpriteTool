package terrariaspritetool;

import java.awt.Component;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import lombok.Getter;
import terrariaspritetool.constants.SpriteData;

public class SpriteRenderer {
  public enum Animation {
    WALK,
    JUMP,
    RAISE_ARM,
    SIT,
    TALK,
    BLINK,
    ATTACK,
  }

  private Animation currentAnimation = Animation.RAISE_ARM;

  private static final int SCALE = 5;

  private SpriteData spriteData;
  @Getter
  private JPanel panel;
  private ImageIcon img;
  private BufferedImage bufferedImg;
  private JLabel indexLabel;

  private int sequenceIndex = 0;
  private int totalFrames;

  private Map<Animation, List<Integer>> animationSequences;

  public SpriteRenderer(SpriteData spriteData) throws Exception {
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

    animationSequences = new HashMap<>();
    animationSequences.put(Animation.WALK, IntStream.range(2, totalFrames - spriteData.extraFrames()).boxed().toList());
    animationSequences.put(Animation.JUMP, List.of(0, 1));
    animationSequences.put(Animation.RAISE_ARM,
        List.of(totalFrames - spriteData.extraFrames(), totalFrames - spriteData.extraFrames() + 1));
    animationSequences.put(Animation.SIT, List.of(totalFrames - spriteData.extraFrames() + 2));
    animationSequences.put(Animation.TALK, List.of(0, totalFrames - spriteData.extraFrames() + 3));
    animationSequences.put(Animation.BLINK, List.of(0, totalFrames - spriteData.extraFrames() + 4));
    animationSequences.put(Animation.ATTACK,
        IntStream.range(totalFrames - spriteData.extraFrames() + 5, totalFrames).boxed().toList());

    String[] animationNames = Stream.of(Animation.values()).map(Animation::name).toArray(String[]::new);
    JComboBox<String> animationChoice = new JComboBox<>(animationNames);
    animationChoice.setMaximumSize(animationChoice.getPreferredSize());
    animationChoice.setSelectedIndex(0);
    animationChoice.addActionListener(e -> {
      if (animationChoice.getSelectedIndex() != -1) {
        panel.requestFocusInWindow();
      }
    });
    panel.add(animationChoice);
  }

  public void nextSprite() {
    sequenceIndex++;
    updateSprite();
  }

  public void previousSprite() {
    sequenceIndex--;
    updateSprite();
  }

  public void updateSprite() {
    List<Integer> currentSequence = animationSequences.get(currentAnimation);
    sequenceIndex = Math.floorMod(sequenceIndex, currentSequence.size());
    int frameIndex = currentSequence.get(sequenceIndex);

    img.setImage(bufferedImg.getSubimage(0, spriteData.h() * frameIndex, spriteData.w(), spriteData.h())
        .getScaledInstance(spriteData.w() * SCALE, spriteData.h() * SCALE, Image.SCALE_FAST));
    indexLabel.setText(Integer.toString(frameIndex));

    panel.revalidate();
    panel.repaint();
  }
}
