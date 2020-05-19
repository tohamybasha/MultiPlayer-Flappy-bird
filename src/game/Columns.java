package game;
// I would like to acknowledge the user MIKU:
// http://sjbuzz.net/video/watch/vid01R83xtgbO2o4 for providing a comprehensive
// tutorial on how to use JavaFX for this video game. 

import java.util.ArrayList;
import javafx.scene.shape.Rectangle;

public class Columns {

  private static ArrayList<Rectangle> columns;
  private static int space, width, W, H;

  Columns(int H, int W, int columnSize) {
    columns = new ArrayList<Rectangle>();
    space = 400;
    width = 100;
    this.W = W;
    this.H = H;
    int i = 0;

    while(i < columnSize) {
      addColumn();
      i++;
    }
  }

  private void addColumn() {
    int height = 50 + (int)(Math.random()*300);

    columns.add(new Rectangle(this.W + width + (columns.size() * 200), this.H - height - 120, width, height));
    columns.add(new Rectangle(this.W + width + (columns.size() - 1) * 200, 0, width, this.H - height - space));
  }

  public ArrayList<Rectangle> getColumns() {
    return columns;
  }

  public void test() {
    // Though we specify five columns, actually there are twice the elemnts in
    // the array because the function addColumn adds two columns.
    Columns columns = new Columns(H, W, 5);
    assert(columns.getColumns().size() == 10);
    Columns columns2 = new Columns(H, W, 10);
    assert(columns2.getColumns().size() == 20);
    Columns columns3 = new Columns(H, W, 30);
    assert(columns3.getColumns().size() == 60);
  }

}
