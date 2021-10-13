package cs3500.freecell.model.hw02;

/**
 * Types for the card values of a card.
 */
public enum CardValue {
  ACE("A", 1),
  TWO("2", 2),
  THREE("3", 3),
  FOUR("4", 4),
  FIVE("5", 5),
  SIX("6", 6),
  SEVEN("7", 7),
  EIGHT("8", 8),
  NINE("9", 9),
  TEN("10", 10),
  JACK("J", 11),
  QUEEN("Q", 12),
  KING("K", 13);

  private final int numericalValue;
  private final String symbol;

  /**
   * Constructs a CardValue.
   *
   * @param symbol         the symbol of the card value
   * @param numericalValue the numerical value of a card value
   */
  private CardValue(String symbol, int numericalValue) {
    this.symbol = symbol;
    this.numericalValue = numericalValue;
  }

  /**
   * Gets the symbol represented by this card.
   *
   * @return the symbol represented by this card.
   */
  public String getSymbol() {
    return symbol;
  }

  /**
   * Gets the numerical value of this card.
   *
   * @return the numerical value of the card
   */
  public int getNumericalValue() {
    return numericalValue;
  }

}
