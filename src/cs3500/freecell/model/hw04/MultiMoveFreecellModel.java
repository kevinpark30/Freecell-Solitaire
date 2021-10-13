package cs3500.freecell.model.hw04;

import cs3500.freecell.model.PileType;
import cs3500.freecell.model.hw02.AbstractFreecellModel;
import cs3500.freecell.model.hw02.Card;
import cs3500.freecell.model.hw02.CascadePile;
import cs3500.freecell.model.hw02.Pile;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents a freecell model that allows multimoves.
 */
public class MultiMoveFreecellModel extends AbstractFreecellModel {

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

        //if card index is the last card, do regular single move
        if (cardIndex == sourceCascadePile.size() - 1) {
          Card cardToMoveCascade = sourceCascadePile.takeLast();
          moveTo(destination, destPileNumber, cardToMoveCascade, sourceCascadePile);
        }
        else {
          if (destination != PileType.CASCADE) {
            throw new IllegalArgumentException("Can only perform multimove from cascade pile to a "
                + "cascade pile.");
          }

          if (pileNumber == destPileNumber) {
            return;
          }

          int numEmptyOpenPiles = getNumberEmptyOpenPiles();
          int numEmptyCascadePiles = getNumberEmptyCascadePiles();

          int check = (numEmptyOpenPiles + 1) * (int) Math.pow(2, numEmptyCascadePiles);

          if (sourceCascadePile.size() - cardIndex > check) {
            throw new IllegalArgumentException("Cannot move this many cards in multimove.");
          }

          AbstractFreecellModel.validIndexCheck(destPileNumber, numCascadePiles - 1);
          Card lastCardDest = cascadePiles.get(destPileNumber)
              .get(cascadePiles.get(destPileNumber).size() - 1);
          List<Card> build = new ArrayList<>();
          build.add(lastCardDest);

          int i;
          for (i = cardIndex; i < cascadePiles.get(pileNumber).size(); i++) {
            build.add(cascadePiles.get(pileNumber).get(i));
          }

          if (MultiMoveFreecellModel.validBuild(build)) {
            Pile<Card> tempPile = new CascadePile();
            int j;
            int cardsToTake = sourceCascadePile.size() - cardIndex;
            for (j = 0; j < cardsToTake; j++) {
              tempPile.add(sourceCascadePile.takeLast());
            }
            int k;
            int cardsToPut = tempPile.size();
            for (k = 0; k < cardsToPut; k++) {
              cascadePiles.get(destPileNumber).add(tempPile.takeLast());
            }
          }
          else {
            throw new IllegalArgumentException("Not a valid build for multimove.");
          }
        }
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

  /**
   * Determines if the list of cards forms a valid cascade build.
   * @param build the list of cards as a build
   * @return whether the list of cards forms a valid cascade build
   */
  private static boolean validBuild(List<Card> build) {
    int i;
    boolean validBuildSoFar = true;
    for (i = 0; i < build.size(); i++) {
      if (i == build.size() - 1) {
        return validBuildSoFar;
      }
      Card topCard = build.get(i);
      Card bottomCard = build.get(i + 1);

      if (!((topCard.getCardValue().getNumericalValue() ==
          bottomCard.getCardValue().getNumericalValue() + 1)
          && (!topCard.getSuit().getColor().equals(bottomCard.getSuit().getColor())))) {
        validBuildSoFar = false;
      }
    }
    return validBuildSoFar;
  }

  /**
   * Gets the number of empty open piles in this freecell model.
   * @return the number of empty open piles
   */
  private int getNumberEmptyOpenPiles() {
    int i = 0;
    for (Pile p : openPiles) {
      if (p.size() == 0) {
        i += 1;
      }
    }
    return i;
  }

  /**
   * Gets the number of empty cascade piles in this freecell model.
   * @return the number of empty cascade piles
   */
  private int getNumberEmptyCascadePiles() {
    int i = 0;
    for (Pile p : cascadePiles) {
      if (p.size() == 0) {
        i += 1;
      }
    }
    return i;
  }
}
