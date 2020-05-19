package game;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


public class RestartButton {

  private Button btn;

  RestartButton() {
    btn = new Button();
    btn.setText("Restart");
    btn.setTranslateX(590);
    btn.setTranslateY(600);
    btn.setPrefSize(200, 50);
    btn.setTextFill(Color.BLUE);
    btn.setFont(new Font("Arial", 20));
  }

  public Button getButton() {
    return this.btn;
  }

}
