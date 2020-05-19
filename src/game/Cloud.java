package game;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Cloud {
  private Image img;
  private ImageView imgView;
  private int X, Y;


  Cloud(int W) {
    img = new Image("cloud.png");
    imgView = new ImageView(img);
    X = W + (int)img.getWidth();
    imgView.setX(X);
    Y = 10 + (int)(Math.random() * 100);
    imgView.setY(Y);
  }

  public ImageView getCloud() {
    return imgView;
  }

  public int getImageWidth() {
    int imgW = (int)img.getWidth();
    return imgW;
  }

}
