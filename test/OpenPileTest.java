import static org.junit.Assert.assertEquals;

import cs3500.freecell.model.hw02.Card;
import cs3500.freecell.model.hw02.CardValue;
import cs3500.freecell.model.hw02.OpenPile;
import cs3500.freecell.model.hw02.Pile;
import cs3500.freecell.model.hw02.Suit;
import org.junit.Before;
import org.junit.Test;

/**
 * Class that tests the methods for open piles.
 */
public class OpenPileTest {

  Pile<Card> pile;

  //Initializes the pile to be an empty pile.
  @Before
  public void init() {
    pile = new OpenPile();
  }

  //tests the add method is working correctly for piles
  @Test
  public void testAdd() {
    assertEquals(0, pile.size());
    pile.add(new Card(CardValue.ACE, Suit.SPADES));
    assertEquals(1, pile.size());
    assertEquals(new Card(CardValue.ACE, Suit.SPADES), pile.get(0));
  }

  //tests that an exception is thrown when get is called on a negative index
  @Test(expected = IndexOutOfBoundsException.class)
  public void testGetNegativeIndex() {
    pile.get(-1);
  }

  //tests that an exception is thrown when get an index greater than its size
  @Test(expected = IndexOutOfBoundsException.class)
  public void testGetTooHighIndex() {
    pile.get(0);
  }

  //tests that the get method works correctly
  @Test
  public void testGet() {
    pile.add(new Card(CardValue.ACE, Suit.SPADES));
    assertEquals(new Card(CardValue.ACE, Suit.SPADES), pile.get(0));
  }

  //tests that the size method works correctly
  @Test
  public void testSize() {
    assertEquals(0, pile.size());
    pile.add(new Card(CardValue.ACE, Suit.SPADES));
    assertEquals(1, pile.size());
  }

  //tests than an exception is thrown when takeLast is called on a pile that is empty
  @Test(expected = IllegalStateException.class)
  public void testTakeLastEmptyPile() {
    pile.takeLast();
  }

  //tests than the takeLast method works correctly
  @Test
  public void testTakeLast() {
    pile.add(new Card(CardValue.ACE, Suit.SPADES));
    assertEquals(new Card(CardValue.ACE, Suit.SPADES), pile.takeLast());
  }

  //tests to see that an exception is thrown when attempting to add a card to a pile
  //that already has a card in that open pile
  @Test(expected = IllegalArgumentException.class)
  public void testGameAddInvalidBuild() {
    Pile<Card> pileFrom = new OpenPile();
    pile.add(new Card(CardValue.ACE, Suit.DIAMONDS));
    pile.gameAdd(new Card(CardValue.FIVE, Suit.SPADES), pileFrom);
  }

  //tests that the gameAdd method correctly adds a card to the pile for valid foundation pile builds
  @Test
  public void testGameAddValidBuild() {
    Pile<Card> pileFrom = new OpenPile();
    pile.gameAdd(new Card(CardValue.ACE, Suit.DIAMONDS), pileFrom);
    assertEquals(1, pile.size());
  }
}