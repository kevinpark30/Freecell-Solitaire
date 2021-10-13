package cs3500.freecell.model.hw02;

/**
 * Class representing a foundation pile.
 */
public class FoundationPile extends APile {

  /**
   * Default constructor for a foundation pile. Creates an empty foundation pile.
   */
  public FoundationPile() {
    super();
  }

  @Override
  public void gameAdd(Card card, Pile<Card> sourcePile) throws IllegalArgumentException {
    if (this.size() == 0) {
      if (card.getCardValue() == CardValue.ACE) {
        this.add(card);
      } else {
        sourcePile.add(card);
        throw new IllegalArgumentException("Cannot add a \"non-ace\" value to an empty foundation"
            + "pile");
      }
    } else {
      Card lastCardInPile = this.get(this.size() - 1);

      if ((card.getCardValue().getNumericalValue() ==
          lastCardInPile.getCardValue().getNumericalValue() + 1)
          && (card.getSuit() == lastCardInPile.getSuit())) {
        this.add(card);
      } else {
        sourcePile.add(card);
        throw new IllegalArgumentException("Cannot move a card that is not one more in value"
            + "and same suit as the last card of the pile.");
      }
    }
  }
}
