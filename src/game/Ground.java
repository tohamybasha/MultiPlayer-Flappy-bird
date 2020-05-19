package game;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Ground {

  Rectangle ground;

  Ground(int H, int W) {
    ground = new Rectangle(0, H - 120, W, 120);
    ground.setFill(Color.DARKGREEN);
  }

  public Rectangle getGround() {
    return ground;
  }

}
