package game;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class GameOver extends Application{

	public GameOver(int result){
		this.WinOrLose = result;
	}
	 int WinOrLose ;

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
		Label label = new Label();
		
		
		
		label.setFont(Font.font("Arial",50));
		StackPane pane = new StackPane();
		pane.getChildren().add(label);
		
		if(WinOrLose == 1)//You won
		{
		label.setText("You won !");
		pane.setStyle("-fx-background-color:GREEN");
		
		}
		else {
		label.setText("You lost!");
		pane.setStyle("-fx-background-color:RED");
		}
		
		Scene scene = new Scene(pane,500,500);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Result");
		primaryStage.show();
		
	}

}
