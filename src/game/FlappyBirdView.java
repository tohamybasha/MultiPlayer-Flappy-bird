package game;
// I would like to acknowledge the user MIKU:
// http://sjbuzz.net/video/watch/vid01R83xtgbO2o4 for providing a comprehensive
// tutorial on how to use JavaFX for this video game.

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import javafx.util.Duration;
import javafx.util.converter.IntegerStringConverter;
import multicast.NewUDPClientSelfBroadCasting;
import javafx.animation.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.input.*;
import javafx.stage.*;
import javafx.event.*;
import javafx.geometry.Insets;

public class FlappyBirdView extends Application  implements Runnable{
	public static void main(String[] args) {
		Application.launch(args);
	}
	public FlappyBirdView(NewUDPClientSelfBroadCasting player, Socket s) {
		this.socket = s;
		this.player = player;
		//th = new Thread(this.player);
	}
	public FlappyBirdView(){
		
	}
  Thread th;
  Socket socket =null;
  Stage stage =null;
  NewUDPClientSelfBroadCasting player;
  int W = 800, H = 700, columnSize = 5, X, columnTicks, tempColNum = 5;
  Scene scene;
  public static String enemyScor ="0",enemyLastScore="0";
  boolean enemyLost = false;
  Group root = new Group();
  Ellipse bird = new Ellipse();
  Timeline tim = new Timeline();
  IntegerStringConverter str = new IntegerStringConverter();;
  ArrayList<Rectangle> columns;
  Columns columnsObject = new Columns(H, W, columnSize);
  boolean gameOver = false;
  FlappyBirdModel model;
  Stage primaryStage = new Stage();
  Bird birdObject = new Bird(H, W);
  Ground groundObject = new Ground(H, W);
  RestartButton button = new RestartButton();
  ImageView cloud;
  Cloud cloudObject = new Cloud(W);
  GameLabels labelObject = new GameLabels(W, H);
  Label scoreLabel, menuLabel, levelLabel,enemyScore;
  Coin coinObject = new Coin(H, W);
  Ellipse coin = new Ellipse();
  FlappyBirdAudio backgroundAudio = new FlappyBirdAudio();

  @Override
  public void start (Stage stage){
    primaryStage = stage;
    setupStage();
    // They are here and not at mainMenu() because these are never removed
    root.getChildren().add(groundObject.getGround());
    root.getChildren().add(cloud);
    root.getChildren().addAll(scoreLabel,enemyScore);
    enemyScore.setPadding(new Insets(0,0,0,350));
    root.getChildren().addAll(coin);

    backgroundAudio.backgroundPlay();

    scene = new Scene(root);
    primaryStage.setScene(scene);
    primaryStage.show();

    launchTimer();
    mainMenu();
    }

    public void setupStage() {
      // Create all of the objects required to run the program
      columns = columnsObject.getColumns();
      bird = birdObject.getBird();
      cloud = cloudObject.getCloud();
      scoreLabel = labelObject.getScoreLabel();
      menuLabel = labelObject.getMenuLabel();
      levelLabel = labelObject.getLevelLabel();
      enemyScore = labelObject.getEnemyLabel();
      coin = coinObject.getCoin();

      primaryStage.setTitle("Flappy Bird");
      primaryStage.setHeight(H);
      primaryStage.setWidth(W);
      primaryStage.setResizable(false); // Resizing window not allowed
    }

    // This is the Game's main loop
    private void launchTimer() {
      tim.setCycleCount(Animation.INDEFINITE);
      KeyFrame kf = new KeyFrame(Duration.millis(20), arg0 -> {
		try {
			listen(arg0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	});
      tim.getKeyFrames().add(kf);
    }

    // Set up the main menu. I.e: either at the beginning or when the player
    // collides with an object in the game
    private void mainMenu() {
      birdObject = new Bird(H, W);
      bird = birdObject.getBird();
      gameOver = false;
      columnSize = 5;
      model = new FlappyBirdModel(W, H);
      scoreLabel.setText("Score:" + " 0" );
      enemyScore.setText("Enemy:" + " 0" );
      levelLabel.setText( "Level:" + " 1" );
      root.getChildren().removeAll(columns);
      root.getChildren().add(bird);
      root.getChildren().add(menuLabel);
      root.getChildren().add(levelLabel);
      resetColumns();
      tim.pause();
      scene.setOnKeyReleased(this::pressToStart);
    }

    private void gameOver() throws IOException {
      gameOver = model.collision(bird, columns);
      if(gameOver || enemyLost) {
    	 player.setGameOver(true);
    	 gameOver = true;
        root.getChildren().remove(bird);
        root.getChildren().remove(levelLabel);
        // Make sure this has been added once, else, just listen for click.
        if(!root.getChildren().contains(button.getButton())) {
          root.getChildren().addAll(button.getButton());
          birdObject.birdPlay();
          GameOver over=null;
          if(enemyLost)
        	  over = new GameOver(1);
          else if (gameOver)
        	  over = new GameOver(2);
          try {
			over.start(primaryStage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        }
        // Listens to the button click to reset the game.
        button.getButton().setOnMouseClicked(this::click);
        
      }
    }

    private void resetColumns() {
      columnsObject = new Columns(H, W, columnSize);
      columns = new ArrayList<Rectangle>();
      columns = columnsObject.getColumns();
      root.getChildren().addAll(columns);
    }

    private void moveColumns() {
      for(int i = 0; i < columns.size(); i++) {
        Rectangle column = columns.get(i);
        int columnX = model.columnMove(column);
        column.setX(columnX);
        // If this column has gone past the screen, remove it to open up memory
        if(columnX + column.getWidth() == 0) {
          columns.remove(i);
          // If the array of columns is empty, double the array size.
          // This is to increse Level Difficulty
          if(columns.size() == 0) {
            nextLevel();
            resetColumns();
          }
        }
      }
    }

    private void nextLevel() {
      int level = model.getLevel();
      levelLabel.setText("Level:" + " " + str.toString(level));
      columnSize = level * tempColNum;
    }

    private void updateScore(NewUDPClientSelfBroadCasting player) throws IOException {
      if(!gameOver) {
        // Fetch the score from the model. Divide by twenty
        // so that it looks as if it increses slow.
    	  String myScore = str.toString(model.getScore()/20);
//    	  if(player.getPlayerNumber() == 1) {
//    		  enemyScor = player.passingDataFromSecond(socket, myScore);
//    	  }
//    	  else {
//    		  enemyScor = player.passingDataFromFirst(socket, myScore);
//    	  }
    	player.setMyScore(myScore);
    	if (!player.getSocket().isClosed())
    	{
    		th = new Thread(player);
    		th.start();
    	}
    	
    	if(enemyScor.equalsIgnoreCase("-1") && !player.getSocket().isClosed()){
    		System.out.println("Enemy Lost");
    		enemyLost = true;
    		gameOver();
    		//player.getSocket().close();
    		//th.stop();
    	}else enemyLastScore = enemyScor;
    	
    	
        scoreLabel.setText("Score:" + " " + myScore);
        enemyScore.setText("Enemy:" + " " +enemyScor);
      }
    }

    private void coinBehaviour() {
      coin.setCenterX(model.coinMovement(coin));
      boolean eraseCoin = model.coinCollision(coin, bird);
      // It shouldnt sound when it leaves the screen
      if(eraseCoin || coin.getCenterX() < 0) {
        root.getChildren().remove(coin);
        coinObject = new Coin(H, W);
        coin = coinObject.getCoin();
        root.getChildren().add(coin);
      }
      // This is so that the coin only makes a sound when bird touches it
      if(eraseCoin) {coinObject.coinPlay();}
    }

    private void updateCloud() {
      int X = (int)cloud.getX();
      // Get the size of the PNG image
      int imgW = cloudObject.getImageWidth();
      X = model.cloudMove(X);

      // Bring the cloud back once it leaves the window.
      if( X < (0 - cloudObject.getImageWidth()) ) {
        X = model.cloudRespawn(X, imgW);
        cloud.setX(X);
      } else { cloud.setX(X); }
    }

/*----------------------These are the Listening Events------------------------*/

// Listen for every single KeyFrame
  private void listen(ActionEvent e) throws IOException {
    gameOver();
    // Keep falling until key is pressed and released
    bird.setCenterY(this.model.gravity(bird));
    // Has anyone pressed the UP key?
    scene.setOnKeyReleased(this::pressUP);
    moveColumns();
    coinBehaviour();
    updateCloud();
    try {
		updateScore(player);
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
  }

  private void click(MouseEvent event) {
    // Changes the window's layout
    root.getChildren().remove(button.getButton());
    mainMenu();
  }

  private void pressUP(KeyEvent event) {
    String code = event.getCode().toString();
    if(code == "UP") {
      bird.setCenterY((int)bird.getCenterY() + this.model.jump());
    }
  }

  private void pressToStart(KeyEvent event) {
    String code = event.getCode().toString();
    if(code == "UP"){
      // root.getChildren().addAll(columns);
      root.getChildren().remove(menuLabel);
      tim.play();
    }
  }
public Stage getStage() {
	return stage;
}
public void setStage(Stage stage) {
	this.stage = stage;
}

public String getEnemyScor() {
	return enemyScor;
}
public void setEnemyScor(String enemyScor) {
	this.enemyScor = enemyScor;
}
@Override
public void run() {
	// TODO Auto-generated method stub
	if(stage!=null)
	this.start(stage);
}
public boolean isGameOver() {
	return gameOver;
}


}
