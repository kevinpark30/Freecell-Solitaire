package cs3500.freecell.model.hw02;

import cs3500.freecell.model.PileType;

/**
 * Represents a simple Freecell model of the Freecell game.
 */
public class SimpleFreecellModel extends AbstractFreecellModel {

  /**
   * Default constructor for a simple freecell model.
   */
  public SimpleFreecellModel() {
    super();
  }

  @Override
  public void move(PileType source, int pileNumber, int cardIndex, PileType destination,
      int destPileNumber) throws IllegalArgumentException, IllegalStateException {
    checkGameStarted();
    if (source == null || destination == null) {
      throw new IllegalArgumentException("Card type cannot be null.");
    }
    switch (source) {
      case FOUNDATION:
        throw new IllegalArgumentException("Cannot move a card from a foundation pile.");
      case CASCADE:
        AbstractFreecellModel.validIndexCheck(pileNumber, numCascadePiles - 1);
        Pile<Card> sourceCascadePile = cascadePiles.get(pileNumber);
        AbstractFreecellModel.validIndexCheck(cardIndex, sourceCascadePile.size() - 1);
        if (cardIndex != sourceCascadePile.size() - 1) {
          throw new IllegalArgumentException("The source card is not from the end of the pile.");
        }
        Card cardToMoveCascade = sourceCascadePile.takeLast();

        moveTo(destination, destPileNumber, cardToMoveCascade, sourceCascadePile);
        break;
      case OPEN:
        AbstractFreecellModel.validIndexCheck(pileNumber, numOpenPiles - 1);
        Pile<Card> sourceOpenPile = openPiles.get(pileNumber);
        AbstractFreecellModel.validIndexCheck(cardIndex, sourceOpenPile.size() - 1);
        if (cardIndex != sourceOpenPile.size() - 1) {
          throw new IllegalArgumentException("The source card is not from the end of the pile.");
        }
        Card cardToMoveOpen = sourceOpenPile.takeLast();

        moveTo(destination, destPileNumber, cardToMoveOpen, sourceOpenPile);
        break;
      default:
        throw new IllegalArgumentException("Card type cannot be null");
    }
  }
}
