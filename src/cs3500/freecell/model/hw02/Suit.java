package cs3500.freecell.model.hw02;

/**
 * Type for 4 different suits of a card.
 */
public enum Suit {
  DIAMONDS("♦", "red"),
  CLUBS("♣", "black"),
  HEARTS("♥", "red"),
  SPADES("♠", "black");

  private final String symbol;
  private final String color;

  /**
   * Constructs a Suit.
   *
   * @param symbol the symbol of the suit
   * @param color  the color of the suit
   */
  private Suit(String symbol, String color) {
    this.symbol = symbol;
    this.color = color;
  }

  /**
   * Gets the symbol represented as a String of this suit.
   *
   * @return returns the symbol of this suit
   */
  public String getSymbol() {
    return symbol;
  }

  /**
   * Gets the color represented as a String of this suit.
   *
   * @return returns the color of this suit.
   */
  public String getColor() {
    return color;
  }

}
