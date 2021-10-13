import static org.junit.Assert.assertEquals;

import cs3500.freecell.model.hw02.Card;

import cs3500.freecell.model.hw02.CardValue;

import cs3500.freecell.model.hw02.Suit;

import org.junit.Test;

/**
 * Class for testing the constructor and methods found in the Card class.
 */
public class CardTest {

  //tests the constructor to throw an exception when a null parameter is given (cardValue field)
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorExceptionCardValueNull() {
    Card c = new Card(null, Suit.DIAMONDS);
  }

  //tests the constructor to throw an exception when a null parameter is given (suit field)
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorExceptionSuitNull() {
    Card c = new Card(CardValue.ACE, null);
  }

  //tests that the toString() method properly returns the string representation of the card
  //four of clubs
  @Test
  public void testToStringFourOfClubs() {
    Card c = new Card(CardValue.FOUR, Suit.CLUBS);
    assertEquals("4♣", c.toString());
  }

  //tests that the toString() method properly returns the string representation of the card
  //jack of diamonds
  @Test
  public void testToStringJackOfDiamonds() {
    Card c = new Card(CardValue.JACK, Suit.DIAMONDS);
    assertEquals("J♦", c.toString());
  }

  //tests that the toString() method properly returns the string representation of the card
  //queen of spades
  @Test
  public void testToStringQueenOfSpades() {
    Card c = new Card(CardValue.QUEEN, Suit.SPADES);
    assertEquals("Q♠", c.toString());
  }

  //tests that the toString() method properly returns the string representation of the card
  //king of hearts
  @Test
  public void testToStringKingOfHearts() {
    Card c = new Card(CardValue.KING, Suit.HEARTS);
    assertEquals("K♥", c.toString());
  }

  //tests that the equals method works for extensional equality
  @Test
  public void testEquals() {
    Card c1 = new Card(CardValue.ACE, Suit.DIAMONDS);
    Card c2 = new Card(CardValue.ACE, Suit.DIAMONDS);
    assertEquals(true, c1.equals(c2));
  }

  //tests the hashCode() method (equal objects return the same hashcode and not equal objects
  //return different hash codes
  @Test
  public void testHashCodes() {
    Card c1 = new Card(CardValue.ACE, Suit.DIAMONDS);
    Card c2 = new Card(CardValue.ACE, Suit.DIAMONDS);
    Card c3 = new Card(CardValue.KING, Suit.DIAMONDS);
    assertEquals(true, c1.hashCode() == c2.hashCode());
    assertEquals(false, c1.hashCode() == c3.hashCode());
  }

  //tests that getCardValue properly returns the card value of a card
  @Test
  public void testGetCardValue() {
    Card c1 = new Card(CardValue.ACE, Suit.DIAMONDS);
    assertEquals(CardValue.ACE, c1.getCardValue());
  }

  //tests that getSuit properly returns the suit of a card
  @Test
  public void testGetSuit() {
    Card c1 = new Card(CardValue.ACE, Suit.DIAMONDS);
    assertEquals(Suit.DIAMONDS, c1.getSuit());
  }
}