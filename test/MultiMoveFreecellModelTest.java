import static org.junit.Assert.assertEquals;

import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.FreecellModelCreator;
import cs3500.freecell.model.FreecellModelCreator.GameType;
import cs3500.freecell.model.PileType;
import cs3500.freecell.model.hw02.Card;
import cs3500.freecell.model.hw02.CardValue;
import cs3500.freecell.model.hw02.Suit;
import org.junit.Test;

/**
 * Testing class for multimove freecell model.
 */
public class MultiMoveFreecellModelTest extends AbstractFreecellModelTest {

  @Override
  protected FreecellModel<Card> modelCreate() {
    return FreecellModelCreator.create(GameType.MULTIMOVE);
  }

  //tests that an exception is thrown when multimove is trying to move more than 1 card to an
  //open pile
  @Test(expected = IllegalArgumentException.class)
  public void testMultimoveCascadeToOpen() {
    model.startGame(model.getDeck(), 52, 4, false);
    model.move(PileType.CASCADE, 12, 0, PileType.CASCADE, 1);
    model.move(PileType.CASCADE, 1, 0, PileType.OPEN, 0);
  }

  //tests that an exception is thrown when multimove is trying to move more than 1 card to a
  //foundation pile
  @Test(expected = IllegalArgumentException.class)
  public void testMultimoveCascadeToFoundation() {
    model.startGame(model.getDeck(), 52, 4, false);
    model.move(PileType.CASCADE, 12, 0, PileType.CASCADE, 1);
    model.move(PileType.CASCADE, 1, 0, PileType.FOUNDATION, 0);
  }

  //tests that a multimove has no effect when moving multiple cards from a cascade pile to the
  //same cascade pile
  @Test
  public void testMultimoveCascadeToSameCascadePile() {
    model.startGame(model.getDeck(), 52, 4, false);
    model.move(PileType.CASCADE, 0, 0, PileType.CASCADE, 0);
    assertEquals(1, model.getNumCardsInCascadePile(0));
    assertEquals(new Card(CardValue.ACE, Suit.DIAMONDS), model.getCascadeCardAt(0, 0));
  }

  //tests that an exception is thrown when a multimove is attempted but not enough open
  //intermediate empty open and cascade piles exist
  @Test(expected = IllegalArgumentException.class)
  public void testMultimoveNotEnoughIntermediateSlots() {
    model.startGame(model.getDeck(), 26, 1, false);
    model.move(PileType.CASCADE, 13, 1, PileType.CASCADE, 1);
    model.move(PileType.CASCADE, 1, 1, PileType.CASCADE, 15);
    model.move(PileType.CASCADE, 15, 1, PileType.CASCADE, 3);
  }

  //tests that an exception is thrown when a multimove is attempted on cards in a cascade pile
  //that has an invalid build
  @Test(expected = IllegalArgumentException.class)
  public void testMultimoveInvalidBuild() {
    model.startGame(model.getDeck(), 26, 1, false);
    model.move(PileType.CASCADE, 0, 0, PileType.CASCADE, 1);
  }

  //tests that multimove works correctly from moving cards from a cascade pile to another cascade
  //pile
  @Test
  public void testMultimoveCascadeToCascade() {
    model.startGame(model.getDeck(), 52, 4, false);
    assertEquals(1, model.getNumCardsInCascadePile(13));
    model.move(PileType.CASCADE, 13, 0, PileType.CASCADE, 1);
    assertEquals(0, model.getNumCardsInCascadePile(13));
    assertEquals(2, model.getNumCardsInCascadePile(1));
    model.move(PileType.CASCADE, 1, 0, PileType.CASCADE, 15);
    assertEquals(0, model.getNumCardsInCascadePile(1));
    assertEquals(3, model.getNumCardsInCascadePile(15));
  }
}
