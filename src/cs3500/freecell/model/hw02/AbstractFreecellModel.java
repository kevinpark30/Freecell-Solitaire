package cs3500.freecell.model.hw02;

import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.PileType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**
 * Abstract class for freecell models.
 */
public abstract class AbstractFreecellModel implements FreecellModel<Card> {
  protected int numCascadePiles;
  protected int numOpenPiles;
  protected final List<Pile<Card>> cascadePiles;
  protected final List<Pile<Card>> foundationPiles;
  protected final List<Pile<Card>> openPiles;
  protected boolean gameStarted;

  /**
   * Default constructor for an abstract freecell model.
   */
  protected AbstractFreecellModel() {
    this.numCascadePiles = 0;
    this.numOpenPiles = 0;
    this.cascadePiles = new ArrayList<>();
    this.foundationPiles = new ArrayList<>();
    this.openPiles = new ArrayList<>();
    this.gameStarted = false;
  }

  @Override
  public List<Card> getDeck() {
    List<Card> deck = new ArrayList<>();
    for (Suit s : Suit.values()) { //for each suit
      for (CardValue cv : CardValue.values()) { //for each card value
        deck.add(new Card(cv, s)); //add a card with the suit and card value to the deck
      }
    }
    return deck;
  }

  @Override
  public void startGame(List<Card> deck, int numCascadePiles, int numOpenPiles, boolean shuffle)
      throws IllegalArgumentException {
    if (!(AbstractFreecellModel.isValid(deck))) {
      throw new IllegalArgumentException("Deck must be a valid deck");
    }
    if (numCascadePiles < 4) {
      throw new IllegalArgumentException("Number of cascade piles must be at least 4");
    }

    if (numOpenPiles < 1) {
      throw new IllegalArgumentException("Number of open piles must be at least 1");
    }

    reset();

    if (shuffle) {
      List<Card> copy = new ArrayList<>();
      int i;
      for (i = 0; i < deck.size(); i++) {
        copy.add(deck.get(i));
      }

      Collections.shuffle(copy);
      dealCards(copy, numCascadePiles, numOpenPiles);
    } else {
      dealCards(deck, numCascadePiles, numOpenPiles);
    }
  }

  /**
   * Determines if a deck of cards is valid, where a valid deck has 52 cards and contains no
   * duplicates (invalid cards, where cards are given an invalid suit or invalid card value, are not
   * checked for as invalid cards cannot be created by the Card class since enumeration types are
   * used).
   *
   * @param deck the deck of cards
   * @return whether the given deck is valid
   * @throws IllegalArgumentException if deck is null
   * @throws IllegalArgumentException if any card in the deck is null
   */
  private static boolean isValid(List<Card> deck) throws IllegalArgumentException {
    if (deck == null) {
      throw new IllegalArgumentException("The deck cannot be null");
    }

    for (Card c : deck) {
      if (c == null) {
        throw new IllegalArgumentException("Any card of the deck cannot be null");
      }
    }

    return deck.size() == 52
        && AbstractFreecellModel.noDuplicates(deck);
  }

  /**
   * Determines if a deck of cards contains any duplicates.
   *
   * @param deck the deck of cards
   * @return whether the deck of cards has no duplicate cards
   */
  private static boolean noDuplicates(List<Card> deck) {
    HashSet<Card> set = new HashSet<>(deck);
    return deck.size() == set.size();
  }

  /**
   * Resets the state of the game, putting the game back to its default values.
   */
  private void reset() {
    this.numCascadePiles = 0;
    this.numOpenPiles = 0;
    this.cascadePiles.clear();
    this.foundationPiles.clear();
    this.openPiles.clear();
    this.gameStarted = false;
  }

  /**
   * Deals the cards with the given deck into number of numCascadePiles and creates open piles equal
   * to the number of numOpenPiles.
   *
   * @param copy            the copy of the shuffled deck
   * @param numCascadePiles number of cascade piles to deal into
   * @param numOpenPiles    number of open piles
   */
  private void dealCards(List<Card> copy, int numCascadePiles, int numOpenPiles) {
    this.numCascadePiles = numCascadePiles;
    this.numOpenPiles = numOpenPiles;

    int i;
    for (i = 0; i < numCascadePiles; i++) {
      this.cascadePiles.add(new CascadePile());
    }

    int j;
    for (j = 0; j < copy.size(); j++) {
      this.cascadePiles.get(j % numCascadePiles).add(copy.get(j));
    }

    int k;
    for (k = 0; k < Suit.values().length; k++) {
      this.foundationPiles.add(new FoundationPile());
    }

    int l;
    for (l = 0; l < numOpenPiles; l++) {
      this.openPiles.add(new OpenPile());
    }

    this.gameStarted = true;
  }

  /**
   * Moves the given card to the appropriate pile if it is valid.
   *
   * @param destination    the pile type of the destination pile
   * @param destPileNumber the index of the pile in the pile type
   * @param cardToMove     the card to move into the pile
   * @throws IllegalArgumentException when the pile type is null, when the destination pile has an
   *                                  invalid index, or when the move is invalid for moving that
   *                                  card to that pile
   */
  protected void moveTo(PileType destination, int destPileNumber, Card cardToMove,
      Pile<Card> sourcePile) throws IllegalArgumentException {
    switch (destination) {
      case FOUNDATION:
        AbstractFreecellModel.validIndexCheck(destPileNumber, Suit.values().length - 1);
        foundationPiles.get(destPileNumber).gameAdd(cardToMove, sourcePile);
        break;
      case CASCADE:
        AbstractFreecellModel.validIndexCheck(destPileNumber, numCascadePiles - 1);
        cascadePiles.get(destPileNumber).gameAdd(cardToMove, sourcePile);
        break;
      case OPEN:
        AbstractFreecellModel.validIndexCheck(destPileNumber, numOpenPiles - 1);
        openPiles.get(destPileNumber).gameAdd(cardToMove, sourcePile);
        break;
      default:
        throw new IllegalArgumentException("Card type cannot be null");
    }
  }

  @Override
  public boolean isGameOver() {
    if (!gameStarted) {
      return false;
    } else {
      int i;
      boolean gameOverSoFar = true;
      for (i = 0; i < Suit.values().length; i++) {
        gameOverSoFar = gameOverSoFar
            && (getNumCardsInFoundationPile(i) == CardValue.values().length);
      }
      return gameOverSoFar;
    }
  }

  @Override
  public int getNumCardsInFoundationPile(int index)
      throws IllegalArgumentException, IllegalStateException {
    checkGameStarted();
    AbstractFreecellModel.validIndexCheck(index, Suit.values().length - 1);
    return this.foundationPiles.get(index).size();
  }

  @Override
  public int getNumCascadePiles() {
    if (!this.gameStarted) {
      return -1;
    } else {
      return this.numCascadePiles;
    }
  }

  @Override
  public int getNumCardsInCascadePile(int index)
      throws IllegalArgumentException, IllegalStateException {
    checkGameStarted();
    AbstractFreecellModel.validIndexCheck(index, numCascadePiles - 1);
    return this.cascadePiles.get(index).size();
  }


  @Override
  public int getNumCardsInOpenPile(int index)
      throws IllegalArgumentException, IllegalStateException {
    checkGameStarted();
    AbstractFreecellModel.validIndexCheck(index, numOpenPiles - 1);
    return openPiles.get(index).size();
  }


  @Override
  public int getNumOpenPiles() {
    if (!this.gameStarted) {
      return -1;
    } else {
      return this.numOpenPiles;
    }
  }

  @Override
  public Card getFoundationCardAt(int pileIndex, int cardIndex)
      throws IllegalArgumentException, IllegalStateException {
    checkGameStarted();
    AbstractFreecellModel.validIndexCheck(pileIndex, Suit.values().length - 1);
    AbstractFreecellModel.validIndexCheck(cardIndex,
        this.foundationPiles.get(pileIndex).size() - 1);
    return this.foundationPiles.get(pileIndex).get(cardIndex);
  }

  @Override
  public Card getCascadeCardAt(int pileIndex, int cardIndex)
      throws IllegalArgumentException, IllegalStateException {
    checkGameStarted();
    AbstractFreecellModel.validIndexCheck(pileIndex, this.numCascadePiles - 1);
    AbstractFreecellModel.validIndexCheck(cardIndex, this.cascadePiles.get(pileIndex).size() - 1);
    return this.cascadePiles.get(pileIndex).get(cardIndex);
  }

  @Override
  public Card getOpenCardAt(int pileIndex) throws IllegalArgumentException, IllegalStateException {
    checkGameStarted();
    AbstractFreecellModel.validIndexCheck(pileIndex, this.openPiles.size() - 1);
    if (this.openPiles.get(pileIndex).size() == 0) {
      return null;
    } else {
      return this.openPiles.get(pileIndex).get(0);
    }

  }

  /**
   * Checks that the given index is a valid index (not negative and less than given maximum index).
   *
   * @param index    index to check for
   * @param maxIndex maximum index allowed
   * @throws IllegalStateException when index is negative or greater than maximum index
   */
  protected static void validIndexCheck(int index, int maxIndex) {
    if (index < 0) {
      throw new IllegalArgumentException("Index cannot be negative.");
    }
    if (index > maxIndex) {
      throw new IllegalArgumentException("Index is greater than maximum index.");
    }
  }

  /**
   * Checks if the game has started.
   *
   * @throws IllegalStateException when the game has not started
   */
  protected void checkGameStarted() throws IllegalStateException {
    if (!this.gameStarted) {
      throw new IllegalStateException("The game has not started yet");
    }
  }
}
