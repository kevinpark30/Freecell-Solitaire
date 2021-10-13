package cs3500.freecell.model.hw02;

import java.util.Objects;

/**
 * Represents a standard playing card.
 */
public class Card {
  private final CardValue cardValue;
  private final Suit suit;

  /**
   * Constructs a card.
   * @param cardValue the card value of the card
   * @param suit the suit of the card
   * @throws IllegalArgumentException if any one of the parameters is null
   */
  public Card(CardValue cardValue, Suit suit) throws IllegalArgumentException {
    if (cardValue == null || suit == null) {
      throw new IllegalArgumentException("Card value or suit type cannot be null");
    }
    this.cardValue = cardValue;
    this.suit = suit;
  }

  /**
   * Creates a String representation of a card.
   * @return the card represented as a String
   */
  public String toString() {
    return cardValue.getSymbol() + suit.getSymbol();
  }

  /**
   * Compares extensional equality with this card and the given object.
   * @param o the object to compare
   * @return whether this card is extensionally equal to the given object
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof Card)) {
      return false;
    }

    Card that = (Card) o;

    return this.cardValue == that.cardValue
        && this.suit == that.suit;
  }

  /**
   * Produces a hashCode for this card.
   * @return the hashcode for this card
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.cardValue, this.suit);
  }

  /**
   * Gets the card value of this card.
   * @return the card value of this card
   */
  public CardValue getCardValue() {
    return cardValue;
  }

  /**
   * Gets the suit of this card.
   * @return the suit of this card
   */
  public Suit getSuit() {
    return suit;
  }
}
