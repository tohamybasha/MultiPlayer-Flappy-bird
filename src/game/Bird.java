package game;
import javafx.scene.shape.Ellipse;
import javafx.scene.paint.ImagePattern;
import javafx.scene.image.Image;
import javafx.scene.media.MediaPlayer;
import java.net.*;
import javafx.util.*;
import javafx.scene.media.Media;


public class Bird {

  private Ellipse bird;
  private URL url;
  private MediaPlayer mp;
  private Image img;
  private ImagePattern ip;

  Bird(int H, int W) {
    img = new Image("bird.png");
    if (img != null) {
      ip = new ImagePattern(img);
    } else {
        System.err.println("Couldn't find file: bird.png");
    }

    // convert image into a pattern to be pasted onto the ellipse
    bird = new Ellipse();
    bird.setCenterX(W / 2 - 10);
    bird.setCenterY(H / 2 - 10);
    bird.setFill(ip);
    bird.setRadiusX((img.getWidth() / 2) + 2);
    bird.setRadiusY((img.getHeight() / 2) + 2);

    // Sound Credit to:
    //https://www.freesound.org/people/Rudmer_Rotteveel/sounds/316920/
    url = getClass().getResource("birdSound.wav");
    mp = new MediaPlayer(new Media(url.toString()));
  }

  public Ellipse getBird() {
    return bird;
  }

  public void birdPlay() {
    mp.play();
  }

}
