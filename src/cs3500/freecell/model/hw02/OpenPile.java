package cs3500.freecell.model.hw02;

/**
 * Class representing an open pile.
 */
public class OpenPile extends APile {

  /**
   * Default constructor for an open pile. Creates an empty open pile.
   */
  public OpenPile() {
    super();
  }

  @Override
  public void gameAdd(Card card, Pile<Card> sourcePile) throws IllegalArgumentException {
    if (this.size() == 0) {
      this.add(card);
    }
    else {
      sourcePile.add(card);
      throw new IllegalArgumentException("Cannot add card to an open pile if there already"
          + "is a card in that pile.");
    }
  }
}
