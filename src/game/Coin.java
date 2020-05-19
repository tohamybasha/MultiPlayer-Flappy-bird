package game;
import javafx.scene.shape.Ellipse;
import javafx.scene.paint.ImagePattern;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.net.*;
import javafx.util.Duration;

public class Coin {

  private Ellipse coin;
  private URL url;
  private MediaPlayer mp;
  private Image img;
  private ImagePattern ip;


  Coin(int H, int W) {
    img = new Image("coin.png");
    if (img != null) {
      ip = new ImagePattern(img);
    } else {
        System.err.println("Couldn't find file: coin.png");
    }
    coin = new Ellipse();
    // Always away from the bird
    coin.setCenterX(900);
    // Set it somewhere in the middle
    coin.setCenterY((int)(Math.random() * 251 + 200));
    coin.setFill(ip);
    coin.setRadiusX(11);
    coin.setRadiusY(11);

    // Sound Credit to:
    // https://www.freesound.org/people/Rudmer_Rotteveel/sounds/316920/
    url = getClass().getResource("coinSound.wav");
    mp = new MediaPlayer(new Media(url.toString()));
  }

  public Ellipse getCoin() {
    return coin;
  }

  public void coinPlay() {
    mp.play();
  }

}
