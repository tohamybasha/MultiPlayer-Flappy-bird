package game;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Ellipse;
import java.util.ArrayList;
import java.util.*;


public class FlappyBirdModel {

  private int ticks, birdYmotion, W, H, score, level, columnSize, coinXmotion;
  private Rectangle collidedColumn = new Rectangle();

  FlappyBirdModel(int W, int H) {
    this.W = W;
    this.H = H;
    this.ticks = 0;
    this.birdYmotion = 0;
    this.score = 0;
    this.level = 1;
  }

  public int gravity(Ellipse bird) {
    // By having ticks here, one can either speed up the jumo of slow it down
    // depends on how hard one wants the game to be
    this.ticks++;
    if(this.ticks % 2 == 0 && this.birdYmotion < 15){
      // was 2, lets see what we can do about this
      this.birdYmotion = this.birdYmotion +1;
    }
    return  (int)bird.getCenterY()+ 1;//this.birdYmotion +
  }

  public int jump() {
    // Reset the ymotion so the jump is smooth
    if(this.birdYmotion > 0) {
      this.birdYmotion = 0;
    }
    this.birdYmotion = this.birdYmotion - 10;
    return this.birdYmotion;
  }

  public boolean collision(Ellipse bird, ArrayList<Rectangle> columns) {
    // Check for Sky and Ground
    if(bird.getCenterY() > H - 120 || bird.getCenterY() < 0) {
      return true;
    }
    for(Rectangle column:columns) {
      // Check for each columns collision by finding the intersection of bird
      // and column
      if((column.getBoundsInParent().intersects(bird.getBoundsInParent()))) {
        collidedColumn = column;
        return true;
      }
    }
    return false;
  }

  public int coinMovement(Ellipse coin) {
    int X = (int)coin.getCenterX() - 3;
    return X;
  }

  public boolean coinCollision(Ellipse coin, Ellipse bird) {
    if((coin.getBoundsInParent().intersects(bird.getBoundsInParent()))) {
      this.score = this.score + 100;
      return true;
    }
    return false;
  }

  public int cloudMove(int X) {
    X = X - 2;
    return X;
  }

  public int cloudRespawn(int X, int imgW) {
    X = W + imgW;
    return X;
  }

  public int columnMove(Rectangle column) {
    return (int)column.getX() - 5;
  }

  public int getScore() {
    this.score = this.score + 1;
    return this.score;
  }

  public int getLevel() {
    this.level = this.level + 1;
    return this.level;
  }

  public void test() {
    // Tests the gravity of the bird
    Ellipse bird = new Ellipse();
    bird.setCenterY(200);
    this.birdYmotion = 0;
    assert(gravity(bird) == 200);
    this.birdYmotion = 2;
    assert(gravity(bird) == 204);
    this.birdYmotion = 3;
    assert(gravity(bird) == 203);

    // Tests the jumping of the bird
    assert(jump() == -10);
    this.birdYmotion = 0;
    assert(jump() == -10);

    // Tests the collision of bird and column
    Columns columnObject = new Columns(H, W, 2);
    ArrayList<Rectangle> columns = columnObject.getColumns();
    assert(collision(bird, columns) == false);
    bird.setCenterY(-1);
    assert(collision(bird, columns) == true);
    bird.setCenterY(800);
    assert(collision(bird, columns) == true);
    bird.setCenterY(10);
    bird.setCenterX(10);
    columns.get(1).setX(10);
    columns.get(1).setY(10);
    assert(collision(bird, columns) == true);

    // Tests the movement of the coin through the screen
    Ellipse coin = new Ellipse();
    coin.setCenterX(100);
    assert(coinMovement(coin) == 97);
    coin.setCenterX(0);
    assert(coinMovement(coin) == -3);

    // Tests the collision of bird and coin
    coin.setCenterY(100);
    coin.setCenterX(100);
    bird.setCenterX(100);
    bird.setCenterY(100);
    assert(coinCollision(coin, bird) == true);
    assert(this.score == 100);

    // Tests the movement of the cloud
    assert(cloudMove(10) == 8);

    // Tests where the cloud shoud respawn (note that the second integer is the
    // size of the image being rendered. I.e: a PNG)
    assert(cloudRespawn(0, 50) == 850);

    // Tests the movement of a column
    Rectangle rectangle = new Rectangle();
    rectangle.setX(100);
    assert(columnMove(rectangle) == 95);

    assert(getScore() == 101);

    assert(getLevel() == 2);


  }

}
