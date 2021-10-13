import cs3500.freecell.controller.FreecellController;
import cs3500.freecell.controller.SimpleFreecellController;
import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.FreecellModelCreator;
import cs3500.freecell.model.FreecellModelCreator.GameType;
import cs3500.freecell.model.hw02.Card;
import java.io.InputStreamReader;

/**
 * Class containing a main method that runs the program of a freecell game.
 */
public class FreecellMain {
  /**
   * Main method to run a freecell game.
   * @param args command line arguments
   */
  public static void main(String[] args) {
    FreecellModel<Card> model = FreecellModelCreator.create(GameType.MULTIMOVE);
    FreecellController<Card> controller = new SimpleFreecellController(model,
        new InputStreamReader(System.in), System.out);
    controller.playGame(model.getDeck(), 26, 1, false);
  }
}
