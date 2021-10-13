import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.FreecellModelCreator;
import cs3500.freecell.model.FreecellModelCreator.GameType;
import cs3500.freecell.model.hw02.Card;

/**
 * Testing class for simple freecell model.
 */
public class SimpleFreecellModelTest extends AbstractFreecellModelTest {

  @Override
  protected FreecellModel<Card> modelCreate() {
    return FreecellModelCreator.create(GameType.SINGLEMOVE);
  }
}
