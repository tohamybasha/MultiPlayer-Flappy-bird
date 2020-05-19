package game;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.net.*;
import javafx.util.Duration;

public class FlappyBirdAudio {
  URL url;
  MediaPlayer mp;

  FlappyBirdAudio() {
    // Sound Credit to:
    // https://www.playonloop.com/2016-music-loops/chubby-cat/
    url = getClass().getResource("chubby-cat.wav");
    mp = new MediaPlayer(new Media(url.toString()));
  }


  // Courtesy of: https://docs.oracle.com/javafx/2/api/javafx/scene/media/
                  //MediaPlayer.html#setOnEndOfMedia(java.lang.Runnable)
  public void backgroundPlay() {
    // This is to loop the audio until player quits the game
    mp.setOnEndOfMedia(new Runnable() {
          public void run() {
            mp.seek(Duration.ZERO);
          }
      });
     mp.play();
  }

}
