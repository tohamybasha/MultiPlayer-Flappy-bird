package game;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;

public class GameLabels {
  private Label scoreLabel,enemyLabel;
  private Label menuLabel;
  private Label levelLabel;

  GameLabels(int W, int H) {
    scoreLabel = new Label();
    scoreLabel.setFont(new Font("Arial", 50));
    scoreLabel.setLayoutY(600);
    
    enemyLabel = new Label();
    enemyLabel.setFont(new Font("Arial", 50));
    enemyLabel.setLayoutY(600);

    menuLabel = new Label();
    menuLabel.setText("Press UP key!");
    menuLabel.setFont(new Font("Arial", 80));
    menuLabel.setLayoutX(W / 2 - 250);
    menuLabel.setLayoutY(H / 2 - 100);
    menuLabel.setTextFill(Color.RED);
    
    levelLabel = new Label();
    levelLabel.setFont(new Font("Arial", 50));
    levelLabel.setLayoutX(300);
    levelLabel.setLayoutY(0);
    levelLabel.setTextFill(Color.BLUE);
  }

  public Label getScoreLabel() {
    return this.scoreLabel;
  }

  public Label getMenuLabel() {
    return this.menuLabel;
  }

  public Label getLevelLabel() {
    return this.levelLabel;
  }

public Label getEnemyLabel() {
	return enemyLabel;
}
  
}
