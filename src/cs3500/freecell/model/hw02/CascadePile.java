package cs3500.freecell.model.hw02;

/**
 * Class representing of a cascade pile.
 */
public class CascadePile extends APile {

  /**
   * Default constructor for a cascade pile. Creates an empty cascade pile.
   */
  public CascadePile() {
    super();
  }

  @Override
  public void gameAdd(Card card, Pile<Card> sourcePile) throws IllegalArgumentException {
    if (this.size() == 0) {
      this.add(card);
    } else {
      Card lastCardInPile = this.get(this.size() - 1);

      if ((card.getCardValue().getNumericalValue() ==
          lastCardInPile.getCardValue().getNumericalValue() - 1)
          && (!card.getSuit().getColor().equals(lastCardInPile.getSuit().getColor()))) {
        this.add(card);
      } else {
        sourcePile.add(card);
        throw new IllegalArgumentException("Cannot move a card that is not one less in value"
            + "and opposite color to the last card in a pile.");
      }
    }
  }
}
