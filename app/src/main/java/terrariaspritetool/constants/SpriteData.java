package terrariaspritetool.constants;

public record SpriteData(String filepath, int w, int h, int extraFrames) {
  // TODO: Ingest from JSON so users can provide their own data for custom sprites
  public static final SpriteData ANGLER = new SpriteData("Angler_Default.png", 40, 58, 9);
  public static final SpriteData ARMS_DEALER = new SpriteData("ArmsDealer_Default.png", 40, 56, 9);
  public static final SpriteData GUIDE = new SpriteData("Guide_Default.png", 40, 56, 10);
  public static final SpriteData DOG = new SpriteData("Dog_Beagle.png", 70, 38, 11);
}
