import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.PileType;
import cs3500.freecell.model.hw02.Card;
import java.util.List;

/**
 * Mock model for the freecell game that ensures that input is being transmitted from controller
 * to the model.
 */
public class MockModel implements FreecellModel<Card> {

  private final StringBuilder log;

  MockModel(StringBuilder log) {
    this.log = log;
  }

  @Override
  public List<Card> getDeck() {
    return null;
  }

  @Override
  public void startGame(List<Card> deck, int numCascadePiles, int numOpenPiles, boolean shuffle)
      throws IllegalArgumentException {
    log.append("Deck: " + deck.toString() + ", Number of cascade piles: "
        + String.valueOf(numCascadePiles) + ", Number of open piles: "
        + String.valueOf(numOpenPiles) + ", Shuffle: " + String.valueOf(shuffle) + "\n");
  }

  @Override
  public void move(PileType source, int pileNumber, int cardIndex, PileType destination,
      int destPileNumber) throws IllegalArgumentException, IllegalStateException {
    log.append("Source Pile Type: " + source.toString() + ", Source Pile Number: "
        + String.valueOf(pileNumber) + ", Card Index: " + String.valueOf(cardIndex) +
        ", Destination Pile Type: " + destination.toString() + ", Destination Pile Number: " +
        String.valueOf(destPileNumber) + "\n");
  }

  @Override
  public boolean isGameOver() {
    return false;
  }

  @Override
  public int getNumCardsInFoundationPile(int index)
      throws IllegalArgumentException, IllegalStateException {
    return 0;
  }

  @Override
  public int getNumCascadePiles() {
    return 0;
  }

  @Override
  public int getNumCardsInCascadePile(int index)
      throws IllegalArgumentException, IllegalStateException {
    return 0;
  }

  @Override
  public int getNumCardsInOpenPile(int index)
      throws IllegalArgumentException, IllegalStateException {
    return 0;
  }

  @Override
  public int getNumOpenPiles() {
    return 0;
  }

  @Override
  public Card getFoundationCardAt(int pileIndex, int cardIndex)
      throws IllegalArgumentException, IllegalStateException {
    return null;
  }

  @Override
  public Card getCascadeCardAt(int pileIndex, int cardIndex)
      throws IllegalArgumentException, IllegalStateException {
    return null;
  }

  @Override
  public Card getOpenCardAt(int pileIndex) throws IllegalArgumentException, IllegalStateException {
    return null;
  }
}
