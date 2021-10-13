import static org.junit.Assert.assertTrue;

import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.FreecellModelCreator;
import cs3500.freecell.model.FreecellModelCreator.GameType;
import cs3500.freecell.model.hw02.Card;
import cs3500.freecell.model.hw02.SimpleFreecellModel;
import cs3500.freecell.model.hw04.MultiMoveFreecellModel;
import org.junit.Test;

/**
 * Testing class for the FreecellModelCreator class.
 */
public class FreecellModelCreatorTest {

  //tests that an exception is thrown when null is passed as a gametype in the create method
  @Test(expected = IllegalArgumentException.class)
  public void testCreateNullGameType() {
    FreecellModelCreator.create(null);
  }

  //tests that a SimpleFreecellModel object is created when SINGLEMOVE gametype is passed into the
  //create method
  @Test
  public void testCreateSingleMove() {
    FreecellModel<Card> model = FreecellModelCreator.create(GameType.SINGLEMOVE);
    assertTrue(model instanceof SimpleFreecellModel);
  }

  //tests that a MultiMoveFreecellModel object is created when MULTIMOVE gametype is passed into the
  //create method
  @Test
  public void testCreateMultiMove() {
    FreecellModel<Card> model = FreecellModelCreator.create(GameType.MULTIMOVE);
    assertTrue(model instanceof MultiMoveFreecellModel);
  }
}