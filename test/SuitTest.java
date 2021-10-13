import static org.junit.Assert.assertEquals;

import cs3500.freecell.model.hw02.Suit;

import org.junit.Test;

/**
 * Class for testing methods found in the Suit class.
 */
public class SuitTest {

  //tests that the correct symbol is returned for DIAMONDS suit
  @Test
  public void testGetSymbolDiamonds() {
    assertEquals("♦", Suit.DIAMONDS.getSymbol());
  }

  //tests that the correct symbol is returned for CLUBS suit
  @Test
  public void testGetSymbolClubs() {
    assertEquals("♣", Suit.CLUBS.getSymbol());
  }

  //tests that the correct symbol is returned for HEARTS suit
  @Test
  public void testGetSymbolHearts() {
    assertEquals("♥", Suit.HEARTS.getSymbol());
  }

  //tests that the correct symbol is returned for SPADES suit
  @Test
  public void testGetSymbolSpades() {
    assertEquals("♠", Suit.SPADES.getSymbol());
  }

  //tests the the correct color is returned for DIAMONDS suit
  @Test
  public void testGetColorDiamonds() {
    assertEquals("red", Suit.DIAMONDS.getColor());
  }

  //tests the the correct color is returned for CLUBS suit
  @Test
  public void testGetColorClubs() {
    assertEquals("black", Suit.CLUBS.getColor());
  }

  //tests the the correct color is returned for HEARTS suit
  @Test
  public void testGetColorHearts() {
    assertEquals("red", Suit.HEARTS.getColor());
  }

  //tests the the correct color is returned for SPADES suit
  @Test
  public void testGetColorSpades() {
    assertEquals("black", Suit.SPADES.getColor());
  }


}