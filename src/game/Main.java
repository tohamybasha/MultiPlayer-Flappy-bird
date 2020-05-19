package game;
import javafx.application.Application;
import java.util.*;

public class Main {
    int W = 800, H = 700;

  private void test() {
    Columns columns = new Columns(H, W, 5);
    columns.test();
    System.out.println("All tests have passed!");
    FlappyBirdModel model = new FlappyBirdModel(W, H);
    model.test();
  }

  public static void main(String[] args) {
    boolean testing = false;
    assert(testing = true);
    Main program = new Main();
    if (testing) program.test();
    else if(!testing) {
	  Application.launch(FlappyBirdView.class,args);
  }
      
    }
//  }
}
