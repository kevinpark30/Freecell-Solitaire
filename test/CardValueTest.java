import static org.junit.Assert.assertEquals;

import cs3500.freecell.model.hw02.CardValue;

import org.junit.Test;

/**
 * Class for testing the methods found in the CardValue class.
 */
public class CardValueTest {

  //tests that the correct symbol is returned for the ACE card
  @Test
  public void testGetSymbolAce() {
    assertEquals("A", CardValue.ACE.getSymbol());
  }

  //tests that the correct symbol is returned for the TWO card
  @Test
  public void testGetSymbolTwo() {
    assertEquals("2", CardValue.TWO.getSymbol());
  }

  //tests that the correct symbol is returned for the THREE card
  @Test
  public void testGetSymbolThree() {
    assertEquals("3", CardValue.THREE.getSymbol());
  }

  //tests that the correct symbol is returned for the FOUR card
  @Test
  public void testGetSymbolFour() {
    assertEquals("4", CardValue.FOUR.getSymbol());
  }

  //tests that the correct symbol is returned for the FIVE card
  @Test
  public void testGetSymbolFive() {
    assertEquals("5", CardValue.FIVE.getSymbol());
  }

  //tests that the correct symbol is returned for the SIX card
  @Test
  public void testGetSymbolSix() {
    assertEquals("6", CardValue.SIX.getSymbol());
  }

  //tests that the correct symbol is returned for the SEVEN card
  @Test
  public void testGetSymbolSeven() {
    assertEquals("7", CardValue.SEVEN.getSymbol());
  }

  //tests that the correct symbol is returned for the EIGHT card
  @Test
  public void testGetSymbolEight() {
    assertEquals("8", CardValue.EIGHT.getSymbol());
  }

  //tests that the correct symbol is returned for the NINE card
  @Test
  public void testGetSymbolNine() {
    assertEquals("9", CardValue.NINE.getSymbol());
  }

  //tests that the correct symbol is returned for the TEN card
  @Test
  public void testGetSymbolTen() {
    assertEquals("10", CardValue.TEN.getSymbol());
  }

  //tests that the correct symbol is returned for the JACK card
  @Test
  public void testGetSymbolJack() {
    assertEquals("J", CardValue.JACK.getSymbol());
  }

  //tests that the correct symbol is returned for the QUEEN card
  @Test
  public void testGetSymbolQueen() {
    assertEquals("Q", CardValue.QUEEN.getSymbol());
  }

  //tests that the correct symbol is returned for the KING card
  @Test
  public void testGetSymbolKing() {
    assertEquals("K", CardValue.KING.getSymbol());
  }

  //tests that the correct numerical value is returned for the ACE card
  @Test
  public void testGetNumericalValueAce() {
    assertEquals(1, CardValue.ACE.getNumericalValue());
  }

  //tests that the correct numerical value is returned for the TWO card
  @Test
  public void testGetNumericalValueTwo() {
    assertEquals(2, CardValue.TWO.getNumericalValue());
  }

  //tests that the correct numerical value is returned for the Three card
  @Test
  public void testGetNumericalValueThree() {
    assertEquals(3, CardValue.THREE.getNumericalValue());
  }

  //tests that the correct numerical value is returned for the FOUR card
  @Test
  public void testGetNumericalValueFour() {
    assertEquals(4, CardValue.FOUR.getNumericalValue());
  }

  //tests that the correct numerical value is returned for the FIVE card
  @Test
  public void testGetNumericalValueFive() {
    assertEquals(5, CardValue.FIVE.getNumericalValue());
  }

  //tests that the correct numerical value is returned for the SIX card
  @Test
  public void testGetNumericalValueSix() {
    assertEquals(6, CardValue.SIX.getNumericalValue());
  }

  //tests that the correct numerical value is returned for the SEVEN card
  @Test
  public void testGetNumericalValueSeven() {
    assertEquals(7, CardValue.SEVEN.getNumericalValue());
  }

  //tests that the correct numerical value is returned for the EIGHT card
  @Test
  public void testGetNumericalValueEight() {
    assertEquals(8, CardValue.EIGHT.getNumericalValue());
  }

  //tests that the correct numerical value is returned for the NINE card
  @Test
  public void testGetNumericalValueNine() {
    assertEquals(9, CardValue.NINE.getNumericalValue());
  }

  //tests that the correct numerical value is returned for the TEN card
  @Test
  public void testGetNumericalValueTen() {
    assertEquals(10, CardValue.TEN.getNumericalValue());
  }

  //tests that the correct numerical value is returned for the JACK card
  @Test
  public void testGetNumericalValueJack() {
    assertEquals(11, CardValue.JACK.getNumericalValue());
  }

  //tests that the correct numerical value is returned for the QUEEN card
  @Test
  public void testGetNumericalValueQueen() {
    assertEquals(12, CardValue.QUEEN.getNumericalValue());
  }

  //tests that the correct numerical value is returned for the KING card
  @Test
  public void testGetNumericalValueKing() {
    assertEquals(13, CardValue.KING.getNumericalValue());
  }

}