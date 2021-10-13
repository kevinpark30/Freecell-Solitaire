import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.PileType;
import cs3500.freecell.model.hw02.Card;
import cs3500.freecell.model.hw02.CardValue;
import cs3500.freecell.model.hw02.SimpleFreecellModel;
import cs3500.freecell.model.hw02.Suit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 * Class for testing constructor and methods found in the SimpleFreeCellModel class.
 */
public abstract class AbstractFreecellModelTest {

  protected FreecellModel<Card> model;

  //initializes fields for testing
  @Before
  public void init() {
    model = modelCreate();
  }

  //tests that a valid deck is returned by getDeck() by testing that it has the correct size
  //of 52 cards
  @Test
  public void testGetDeckSize() {
    assertEquals(52, model.getDeck().size());
  }


  //tests that a valid is returned by getDeck() by testing that the deck has no duplicates
  @Test
  public void testGetDeckNoDuplicates() {
    List<Card> deck = model.getDeck();
    HashSet<Card> set = new HashSet<>(deck); //creates a set representation of the deck
    //of cards, removing duplicate values
    assertEquals(deck.size(), set.size()); //check if the size of the list
    //and set are the same, meaning
    //no duplicates were found
  }

  //tests that the getDeck method does not create any cards that are null
  @Test
  public void testGetDeckNoNullCards() {
    List<Card> deck = model.getDeck();
    int i;
    for (i = 0; i < deck.size(); i++) {
      assertNotEquals(null, deck.get(i));
    }
  }

  //tests that an exception is thrown when a null deck is passed to startGame
  @Test(expected = IllegalArgumentException.class)
  public void testStartGameNullDeck() {
    model.startGame(null, 4, 1, true);
  }

  //tests that an exception is thrown when a deck with a null card is passed to startGame
  @Test(expected = IllegalArgumentException.class)
  public void testStartGameNullCardInDeck() {
    List<Card> deck = new ArrayList<>();
    deck.add(null);
    model.startGame(deck, 4, 1, true);
  }

  //tests that an exception is thrown when a deck that is too small is passed to startGame
  @Test(expected = IllegalArgumentException.class)
  public void testStartGameInvalidDeckTooSmall() {
    List<Card> deck = new ArrayList<>();
    deck.add(new Card(CardValue.ACE, Suit.DIAMONDS));
    model.startGame(deck, 4, 1, true);
  }

  //tests that an exception is thrown when a deck with duplicates is passed to startGame
  @Test(expected = IllegalArgumentException.class)
  public void testStartGameInvalidDeckDuplicates() {
    List<Card> deck = new ArrayList<>();
    int i;
    for (i = 0; i < 52; i++) {
      deck.add(new Card(CardValue.ACE, Suit.DIAMONDS));
    }
    assertEquals(52, deck.size());
    model.startGame(deck, 4, 1, true);
  }

  //tests that an exception is thrown when less than 4 cascade piles is inputted into startGame
  @Test(expected = IllegalArgumentException.class)
  public void testStartGameInvalidNumberOfCascadePiles() {
    model.startGame(model.getDeck(), 3, 1, true);
  }

  //tests that an exception is thrown when less than 1 open is inputted into startGame
  @Test(expected = IllegalArgumentException.class)
  public void testStartGameInvalidNumberOfOpenPiles() {
    model.startGame(model.getDeck(), 4, 0, true);
  }

  //tests that the startGame method has started the game properly
  @Test
  public void testStartGame() {
    model.startGame(model.getDeck(), 8, 4, false);
    assertEquals(7, model.getNumCardsInCascadePile(0));
    assertEquals(7, model.getNumCardsInCascadePile(1));
    assertEquals(7, model.getNumCardsInCascadePile(2));
    assertEquals(7, model.getNumCardsInCascadePile(3));
    assertEquals(6, model.getNumCardsInCascadePile(4));
    assertEquals(6, model.getNumCardsInCascadePile(5));
    assertEquals(6, model.getNumCardsInCascadePile(6));
    assertEquals(6, model.getNumCardsInCascadePile(7));

    assertEquals(8, model.getNumCascadePiles());
    assertEquals(4, model.getNumOpenPiles());
    assertEquals(new Card(CardValue.KING, Suit.SPADES), model.getCascadeCardAt(3, 6));
  }

  //tests that startGame can be called when the game has already started and be set to new
  //game states (properly resetted)
  @Test
  public void testStartGameReset() {
    model.startGame(model.getDeck(), 8, 4, false);
    model.startGame(model.getDeck(), 4, 4, false);

    assertEquals(13, model.getNumCardsInCascadePile(0));
    assertEquals(13, model.getNumCardsInCascadePile(1));
    assertEquals(13, model.getNumCardsInCascadePile(2));
    assertEquals(13, model.getNumCardsInCascadePile(3));

    assertEquals(4, model.getNumCascadePiles());
    assertEquals(4, model.getNumOpenPiles());

    assertEquals(new Card(CardValue.KING, Suit.SPADES), model.getCascadeCardAt(3, 12));
  }

  //tests that a deck is actually shuffled when the cards are dealt
  @Test
  public void testStartGameShuffle() {
    FreecellModel<Card> notShuffled = new SimpleFreecellModel();
    model.startGame(model.getDeck(), 52, 4, true);
    notShuffled.startGame(notShuffled.getDeck(), 52, 4, false);

    boolean atLeastOneCardShuffled = false;
    int i;
    for (i = 0; i < 52; i++) {
      boolean differentCard = !(model.getCascadeCardAt(i, 0)
          .equals(notShuffled.getCascadeCardAt(i, 0)));
      atLeastOneCardShuffled = atLeastOneCardShuffled || differentCard;
    }

    assertTrue(atLeastOneCardShuffled);
  }

  //tests that an exception is thrown when a negative index is requested for
  //getNumCardsInFoundationPile(index)
  @Test(expected = IllegalArgumentException.class)
  public void testGetNumCardsInFoundationPileNegativeIndex() {
    model.startGame(model.getDeck(), 8, 4, true);
    model.getNumCardsInFoundationPile(-1);
  }

  //tests that an exception is thrown when an index greater than or equal to the number of
  //foundation piles is requested for getNumCardsInFoundationPile(index)
  @Test(expected = IllegalArgumentException.class)
  public void testGetNumCardsInFoundationPileTooHighIndex() {
    model.startGame(model.getDeck(), 8, 4, true);
    model.getNumCardsInFoundationPile(4);
  }

  //tests that an exception is thrown when getNumCardsInFoundationPile is called when the
  //game has not started yet
  @Test(expected = IllegalStateException.class)
  public void testGetNumCardsInFoundationPileGameNotStartedYet() {
    model.getNumCardsInFoundationPile(3);
  }

  //tests that the number of cards in a foundation pile is correctly returned
  @Test
  public void testGetNumCardsInFoundationPile() {
    model.startGame(model.getDeck(), 52, 4, false);
    assertEquals(0, model.getNumCardsInFoundationPile(0));
    model.move(PileType.CASCADE, 0, 0, PileType.FOUNDATION, 0);
    assertEquals(1, model.getNumCardsInFoundationPile(0));
  }

  //tests that the getNumCascadePiles returns -1 if the game has not started yet
  @Test
  public void testGetNumCascadePilesGameNotStarted() {
    assertEquals(-1, model.getNumCascadePiles());
  }

  //tests that the number of cascade piles is returned correctly for 8 cascade piles
  @Test
  public void testGetNumCascadePilesEight() {
    model.startGame(model.getDeck(), 8, 4, true);
    assertEquals(8, model.getNumCascadePiles());
  }

  //tests that the number of cascade piles is returned correctly for 4 cascade piles
  @Test
  public void testGetNumCascadePilesFour() {
    model.startGame(model.getDeck(), 4, 4, true);
    assertEquals(4, model.getNumCascadePiles());
  }

  //tests that an exception is thrown when a negative index is requested for
  //getNumCardsInCascadePile(index)
  @Test(expected = IllegalArgumentException.class)
  public void testNumCardsInCascadePile() {
    model.startGame(model.getDeck(), 8, 4, true);
    model.getNumCardsInCascadePile(-1);
  }

  //tests that an exception is thrown when an index greater than or equal to the number of
  //cascade piles is requested for getNumCardsInCascadePile(index)
  @Test(expected = IllegalArgumentException.class)
  public void testNumCardsInCascadePileTooHighIndex() {
    model.startGame(model.getDeck(), 8, 4, true);
    model.getNumCardsInCascadePile(8);
  }

  //tests that an exception is thrown when getNumCardsInCascadePile is called when the
  //game has not started yet
  @Test(expected = IllegalStateException.class)
  public void testGetNumCardsInCascadePileGameNotStartedYet() {
    model.getNumCardsInCascadePile(4);
  }

  //tests that getNumCardsInCascadePile returns the correct number
  @Test
  public void testGetNumCardsInCascadePile() {
    model.startGame(model.getDeck(), 8, 4, true);
    assertEquals(7, model.getNumCardsInCascadePile(2));
  }

  //tests that an exception is thrown when a negative index is requested for
  //getNumCardsInOpenPile(index)
  @Test(expected = IllegalArgumentException.class)
  public void testGetNumCardsInOpenPileNegativeIndex() {
    model.startGame(model.getDeck(), 8, 4, true);
    model.getNumCardsInOpenPile(-1);
  }

  //tests that an exception is thrown when an index greater than or equal to the number of
  //open piles is requested for getNumCardsInOpenPile(index)
  @Test(expected = IllegalArgumentException.class)
  public void testGetNumCardsInOpenPileTooHighIndex() {
    model.startGame(model.getDeck(), 8, 4, true);
    model.getNumCardsInOpenPile(4);
  }

  //tests that an exception is thrown when getNumCardsInOpenPile is called when the
  //game has not started yet
  @Test(expected = IllegalStateException.class)
  public void testGetNumCardsInOpenPileGameNotStartedYet() {
    model.getNumCardsInOpenPile(3);
  }

  //tests that the getNumCardsInOpenPile returns the correct number
  @Test
  public void testGetNumCardsInOpenPile() {
    model.startGame(model.getDeck(), 52, 4, false);
    model.move(PileType.CASCADE, 0, 0, PileType.OPEN, 0);
    assertEquals(1, model.getNumCardsInOpenPile(0));
  }

  //tests that the getNumOpenPiles returns -1 if the game has not started yet
  @Test
  public void testGetNumOpenPilesGameNotStarted() {
    assertEquals(-1, model.getNumOpenPiles());
  }

  //tests that the number of cascade piles is returned correctly for 4 cascade piles
  @Test
  public void testGetNumOpenPilesFour() {
    model.startGame(model.getDeck(), 8, 4, true);
    assertEquals(4, model.getNumOpenPiles());
  }


  //tests that an exception is thrown when the getFoundationCardAt is called when the game
  //has not started yet
  @Test(expected = IllegalStateException.class)
  public void testGetFoundationCardAtGameNotStartedYet() {
    model.getFoundationCardAt(3, 3);
  }

  //tests that an exception is thrown when a negative index is requested for the pileIndex in
  //getFoundationCardAt(pileIndex, cardIndex)
  @Test(expected = IllegalArgumentException.class)
  public void testGetFoundationCardAtNegativePileIndex() {
    model.startGame(model.getDeck(), 8, 4, true);
    model.getFoundationCardAt(-1, 2);
  }

  //tests that an exception is thrown when a negative index is requested for the cardIndex in
  //getFoundationCardAt(pileIndex, cardIndex)
  @Test(expected = IllegalArgumentException.class)
  public void testGetFoundationCardAtNegativeCardIndex() {
    model.startGame(model.getDeck(), 8, 4, true);
    model.getFoundationCardAt(1, -2);
  }

  //tests that an exception is thrown when a pileIndex greater than or equal to the number of
  //foundation piles is requested for getFoundationCardAt(pileIndex, cardIndex)
  @Test(expected = IllegalArgumentException.class)
  public void testGetFoundationCardAtTooHighPileIndex() {
    model.startGame(model.getDeck(), 8, 4, true);
    model.getFoundationCardAt(4, 1);
  }

  //tests that an exception is thrown when a cardIndex greater than or equal to the number of
  //cards in a pile is requested for getFoundationCardAt(pileIndex, cardIndex)
  @Test(expected = IllegalArgumentException.class)
  public void testGetFoundationCardAtTooHighCardIndex() {
    model.startGame(model.getDeck(), 8, 4, true);
    model.getFoundationCardAt(1, 1);
  }

  //tests that a card is returned when getFoundationCardAt for a valid indices is called
  @Test
  public void testGetFoundationCardAt() {
    model.startGame(model.getDeck(), 52, 4, false);
    model.move(PileType.CASCADE, 0, 0, PileType.FOUNDATION, 0);
    assertEquals(new Card(CardValue.ACE, Suit.DIAMONDS), model.getFoundationCardAt(0, 0));
  }

  //tests that an exception is thrown when the getCascadeCardAt is called when the game
  //has not started yet
  @Test(expected = IllegalStateException.class)
  public void testGetCascadeCardAtGameNotStartedYet() {
    model.getCascadeCardAt(3, 3);
  }

  //tests that an exception is thrown when a negative index is requested for the pileIndex in
  //getCascadeCardAt(pileIndex, cardIndex)
  @Test(expected = IllegalArgumentException.class)
  public void testGetCascadeCardAtNegativePileIndex() {
    model.startGame(model.getDeck(), 8, 4, true);
    model.getCascadeCardAt(-1, 2);
  }

  //tests that an exception is thrown when a negative index is requested for the cardIndex in
  //getCascadeCardAt(pileIndex, cardIndex)
  @Test(expected = IllegalArgumentException.class)
  public void testGetCascadeCardAtNegativeCardIndex() {
    model.startGame(model.getDeck(), 8, 4, true);
    model.getCascadeCardAt(1, -2);
  }

  //tests that an exception is thrown when a pileIndex greater than or equal to the number of
  //cascade piles is requested for getCascadeCardAt(pileIndex, cardIndex)
  @Test(expected = IllegalArgumentException.class)
  public void testGetCascadeCardAtTooHighPileIndex() {
    model.startGame(model.getDeck(), 8, 4, true);
    model.getCascadeCardAt(8, 1);
  }

  //tests that an exception is thrown when a cardIndex greater than or equal to the number of
  //cards in a pile is requested for getCascadeCardAt(pileIndex, cardIndex)
  @Test(expected = IllegalArgumentException.class)
  public void testGetCascadeCardAtTooHighCardIndex() {
    model.startGame(model.getDeck(), 8, 4, true);
    model.getFoundationCardAt(1, 8);
  }

  //tests that a card is correctly returned when calling getCascadeCardAt with valid indices
  @Test
  public void testGetCascadeCardAt() {
    model.startGame(model.getDeck(), 8, 4, false);
    assertEquals(new Card(CardValue.KING, Suit.SPADES), model.getCascadeCardAt(3, 6));
  }

  //tests that an exception is thrown when the getOpenCardAt is called when the game
  //has not started yet
  @Test(expected = IllegalStateException.class)
  public void testGetOpenCardAtGameNotStartedYet() {
    model.getOpenCardAt(1);
  }

  //tests that an exception is thrown when a negative index is requested for the pileIndex in
  //getOpenCardAt(pileIndex)
  @Test(expected = IllegalArgumentException.class)
  public void testGetOpenCardAtNegativePileIndex() {
    model.startGame(model.getDeck(), 8, 4, true);
    model.getOpenCardAt(-1);
  }

  //tests that an exception is thrown when a pileIndex greater than or equal to the number of
  //open piles is requested in getOpenCardAt(pileIndex)
  @Test(expected = IllegalArgumentException.class)
  public void testGetOpenCardAtTooHighIndex() {
    model.startGame(model.getDeck(), 8, 4, true);
    model.getOpenCardAt(4);
  }

  //tests that null is returned when there is no card at the given open pile index
  @Test
  public void testGetOpenCardAtEmptyOpenPile() {
    model.startGame(model.getDeck(), 8, 4, true);
    assertNull(model.getOpenCardAt(0));
  }

  //tests that a card is correctly returned when calling getOpenCardAt with a valid index
  @Test
  public void testGetOpenCardAt() {
    model.startGame(model.getDeck(), 8, 4, false);
    model.move(PileType.CASCADE, 3, 6, PileType.OPEN, 0);
    assertEquals(new Card(CardValue.KING, Suit.SPADES), model.getOpenCardAt(0));
  }

  //tests that an exception is thrown when the move method is invoked before the game has started
  @Test(expected = IllegalStateException.class)
  public void testMoveGameNotStarted() {
    model.move(PileType.CASCADE, 3, 6, PileType.OPEN, 0);
  }

  //tests that an exception is thrown when the move method is given a null source pile type
  @Test(expected = IllegalArgumentException.class)
  public void testMoveSourcePileIsNull() {
    model.startGame(model.getDeck(), 8, 4, false);
    model.move(null, 3, 6, PileType.OPEN, 0);
  }

  //tests that an exception is thrown when the move method is given a null destination pile type
  @Test(expected = IllegalArgumentException.class)
  public void testMoveDestinationPileIsNull() {
    model.startGame(model.getDeck(), 8, 4, false);
    model.move(PileType.CASCADE, 3, 6, null, 0);
  }


  //tests that a card can be moved from a cascade pile to an open pile
  @Test
  public void testMoveCascadePileToOpenPile() {
    model.startGame(model.getDeck(), 8, 4, false);
    assertEquals(7, model.getNumCardsInCascadePile(3));
    model.move(PileType.CASCADE, 3, 6, PileType.OPEN, 0);
    assertEquals(6, model.getNumCardsInCascadePile(3));
    assertEquals(1, model.getNumCardsInOpenPile(0));
    assertEquals(new Card(CardValue.KING, Suit.SPADES), model.getOpenCardAt(0));
  }

  //tests that an exception is thrown when a card is moved from a cascade pile to an open pile
  //that is not empty
  @Test(expected = IllegalArgumentException.class)
  public void testMoveCascadePileToFullOpenPile() {
    model.startGame(model.getDeck(), 8, 4, false);
    model.move(PileType.CASCADE, 3, 6, PileType.OPEN, 0);
    model.move(PileType.CASCADE, 3, 5, PileType.OPEN, 0);
  }

  //tests that a card can be moved from a cascade pile to an empty foundation pile (only ACES)
  @Test
  public void testMoveCascadePileToFoundationPileEmptyValidBuild() {
    model.startGame(model.getDeck(), 8, 4, false);
    assertEquals(6, model.getNumCardsInCascadePile(7));
    model.move(PileType.CASCADE, 7, 5, PileType.OPEN, 0);
    assertEquals(5, model.getNumCardsInCascadePile(7));
    model.move(PileType.CASCADE, 7, 4, PileType.FOUNDATION, 0);
    assertEquals(4, model.getNumCardsInCascadePile(7));
    assertEquals(1, model.getNumCardsInFoundationPile(0));
    assertEquals(new Card(CardValue.ACE, Suit.SPADES), model.getFoundationCardAt(0, 0));
  }

  //tests that a card can be moved from a cascade pile to any foundation pile, the foundation piles
  //are not binded by suits
  @Test
  public void testMoveCascadePileToFoundationPileAny() {
    model.startGame(model.getDeck(), 8, 4, false);
    assertEquals(6, model.getNumCardsInCascadePile(7));
    model.move(PileType.CASCADE, 7, 5, PileType.OPEN, 0);
    assertEquals(5, model.getNumCardsInCascadePile(7));
    model.move(PileType.CASCADE, 7, 4, PileType.FOUNDATION, 1);
    assertEquals(4, model.getNumCardsInCascadePile(7));
    assertEquals(1, model.getNumCardsInFoundationPile(1));
    assertEquals(new Card(CardValue.ACE, Suit.SPADES), model.getFoundationCardAt(1, 0));
  }

  //tests that an exception is thrown when trying to move a from the foundation pile to a cascade
  //pile, once a card enters a foundation pile it cannot be moved outside of it
  @Test(expected = IllegalArgumentException.class)
  public void testMoveFoundationPileToCascadePile() {
    model.startGame(model.getDeck(), 8, 4, false);
    model.move(PileType.CASCADE, 7, 5, PileType.OPEN, 0);
    model.move(PileType.CASCADE, 7, 4, PileType.FOUNDATION, 0);
    model.move(PileType.FOUNDATION, 0, 0, PileType.CASCADE, 3);
  }

  //tests that an exception is thrown when trying to move a from the foundation pile to an open
  //pile, once a card enters a foundation pile it cannot be moved outside of it
  @Test(expected = IllegalArgumentException.class)
  public void testMoveFoundationPileToOpenPile() {
    model.startGame(model.getDeck(), 8, 4, false);
    model.move(PileType.CASCADE, 7, 5, PileType.OPEN, 0);
    model.move(PileType.CASCADE, 7, 4, PileType.FOUNDATION, 0);
    model.move(PileType.FOUNDATION, 0, 0, PileType.OPEN, 3);
  }

  //tests that an exception is thrown when trying to move a from the foundation pile to a foundation
  //pile, once a card enters a foundation pile it cannot be moved outside of it
  @Test(expected = IllegalArgumentException.class)
  public void testMoveFoundationPileToFoundationPile() {
    model.startGame(model.getDeck(), 8, 4, false);
    model.move(PileType.CASCADE, 7, 5, PileType.OPEN, 0);
    model.move(PileType.CASCADE, 7, 4, PileType.FOUNDATION, 0);
    model.move(PileType.FOUNDATION, 0, 0, PileType.FOUNDATION, 3);
  }

  //tests that an exception is thrown when trying to move a card from a cascade pile to another
  //cascade pile with value not one less to the top card in the cascade pile
  //that it is trying to move to
  @Test(expected = IllegalArgumentException.class)
  public void testMoveCascadePileToCascadePileInvalidBuildNumber() {
    model.startGame(model.getDeck(), 52, 4, false);
    model.move(PileType.CASCADE, 1, 0, PileType.CASCADE, 13);
  }

  //tests that an exception is thrown when trying to move a card from a cascade pile to another
  //cascade pile with suit color not the opposite of the top card in the cascade pile
  //that it is trying to move to
  @Test(expected = IllegalArgumentException.class)
  public void testMoveCascadePileToCascadePileInvalidBuildColor() {
    model.startGame(model.getDeck(), 52, 4, false);
    model.move(PileType.CASCADE, 0, 0, PileType.CASCADE, 1);
  }

  //tests that a card can be moved from a cascade pile to another cascade pile if the build is
  //correct
  @Test
  public void testMoveCascadePileToCascadePileValidBuild() {
    model.startGame(model.getDeck(), 52, 4, false);
    assertEquals(1, model.getNumCardsInCascadePile(0));
    model.move(PileType.CASCADE, 0, 0, PileType.CASCADE, 14);
    assertEquals(2, model.getNumCardsInCascadePile(14));
    assertEquals(0, model.getNumCardsInCascadePile(0));
    assertEquals(new Card(CardValue.ACE, Suit.DIAMONDS), model.getCascadeCardAt(14, 1));
  }

  //tests that a card can be moved from a cascade pile to an empty cascade pile (regardless of
  //build)
  @Test
  public void testMoveCascadePileToEmptyCascadePile() {
    model.startGame(model.getDeck(), 52, 4, false);
    assertEquals(1, model.getNumCardsInCascadePile(0));
    model.move(PileType.CASCADE, 0, 0, PileType.CASCADE, 14);
    assertEquals(0, model.getNumCardsInCascadePile(0));
    model.move(PileType.CASCADE, 15, 0, PileType.CASCADE, 0);
    assertEquals(0, model.getNumCardsInCascadePile(15));
    assertEquals(1, model.getNumCardsInCascadePile(0));
    assertEquals(new Card(CardValue.THREE, Suit.CLUBS), model.getCascadeCardAt(0, 0));
  }

  //tests that an exception is thrown when moving a card from a cascade pile to the same cascade
  //pile that it is in if the build is invalid
  @Test(expected = IllegalArgumentException.class)
  public void testMoveCascadePileToSameCascadePileInvalid() {
    model.startGame(model.getDeck(), 8, 4, false);
    model.move(PileType.CASCADE, 3, 6, PileType.CASCADE, 3);
  }

  //tests that a card can be moved from a cascade pile to the same cascade pile that it is in
  //so long as the build is valid
  @Test
  public void testMoveCascadePileToSameCascadePileValid() {
    model.startGame(model.getDeck(), 52, 4, false);
    assertEquals(1, model.getNumCardsInCascadePile(0));
    model.move(PileType.CASCADE, 0, 0, PileType.CASCADE, 0);
    assertEquals(1, model.getNumCardsInCascadePile(0));
    assertEquals(new Card(CardValue.ACE, Suit.DIAMONDS), model.getCascadeCardAt(0, 0));
  }

  //tests that a card can be moved from a cascade pile to a non-empty foundation pile (follows
  //the build)
  @Test
  public void testMoveCascadePileToFoundationPileNonEmpty() {
    model.startGame(model.getDeck(), 52, 4, false);
    assertEquals(1, model.getNumCardsInCascadePile(0));
    model.move(PileType.CASCADE, 0, 0, PileType.FOUNDATION, 0);
    assertEquals(0, model.getNumCardsInCascadePile(0));
    assertEquals(1, model.getNumCardsInFoundationPile(0));
    model.move(PileType.CASCADE, 1, 0, PileType.FOUNDATION, 0);
    assertEquals(0, model.getNumCardsInCascadePile(1));
    assertEquals(2, model.getNumCardsInFoundationPile(0));
    assertEquals(new Card(CardValue.ACE, Suit.DIAMONDS), model.getFoundationCardAt(0, 0));
    assertEquals(new Card(CardValue.TWO, Suit.DIAMONDS), model.getFoundationCardAt(0, 1));
  }

  //tests that an exception is thrown when moving a card from a cascade pile to a foundation
  //pile with an invalid build where the number in the foundation pile is not one less
  //than the card given
  @Test(expected = IllegalArgumentException.class)
  public void testMoveCascadePileToFoundationPileInvalidBuildNumber() {
    model.startGame(model.getDeck(), 52, 4, false);
    model.move(PileType.CASCADE, 0, 0, PileType.FOUNDATION, 0);
    model.move(PileType.CASCADE, 2, 0, PileType.FOUNDATION, 0);
  }

  //tests that an exception is thrown when moving a card from a cascade pile to a foundation
  //pile with an invalid build where the number in the foundation pile is not the same suit
  //as the card given
  @Test(expected = IllegalArgumentException.class)
  public void testMoveCascadePileToFoundationPileInvalidBuildSuit() {
    model.startGame(model.getDeck(), 52, 4, false);
    model.move(PileType.CASCADE, 0, 0, PileType.FOUNDATION, 0);
    model.move(PileType.CASCADE, 14, 0, PileType.FOUNDATION, 0);
  }

  //tests that an exception is thrown when trying to move a card from a cascade pile to an
  //empty foundation file where the card to be moved is not an ace
  @Test(expected = IllegalArgumentException.class)
  public void testMoveCascadePileToFoundationPileEmptyInvalidBuild() {
    model.startGame(model.getDeck(), 52, 4, false);
    model.move(PileType.CASCADE, 1, 0, PileType.FOUNDATION, 0);
  }

  //tests that a card can move from an open pile to a different open pile
  @Test
  public void testMoveOpenPileToDifferentOpenPile() {
    model.startGame(model.getDeck(), 52, 4, false);
    model.move(PileType.CASCADE, 0, 0, PileType.OPEN, 0);
    assertEquals(0, model.getNumCardsInCascadePile(0));
    assertEquals(1, model.getNumCardsInOpenPile(0));
    model.move(PileType.OPEN, 0, 0, PileType.OPEN, 1);
    assertEquals(0, model.getNumCardsInOpenPile(0));
    assertEquals(1, model.getNumCardsInOpenPile(1));
  }

  //tests that a card can move from an open pile to the same open pile
  @Test
  public void testMoveOpenPileToSameOpenPile() {
    model.startGame(model.getDeck(), 52, 4, false);
    model.move(PileType.CASCADE, 0, 0, PileType.OPEN, 0);
    assertEquals(0, model.getNumCardsInCascadePile(0));
    assertEquals(1, model.getNumCardsInOpenPile(0));
    model.move(PileType.OPEN, 0, 0, PileType.OPEN, 0);
    assertEquals(1, model.getNumCardsInOpenPile(0));
  }

  //tests that an exception is thrown when moving a card in an open pile to a different open pile
  //that is already occupied by a card
  @Test(expected = IllegalArgumentException.class)
  public void testMoveOpenPileToOpenPileInvalid() {
    model.startGame(model.getDeck(), 52, 4, false);
    model.move(PileType.CASCADE, 0, 0, PileType.OPEN, 0);
    model.move(PileType.CASCADE, 1, 0, PileType.OPEN, 1);
    model.move(PileType.OPEN, 0, 0, PileType.OPEN, 1);
  }

  //tests that a card can be moved from an open pile to a non-empty cascade pile
  @Test
  public void testMoveOpenPileToNonEmptyCascadePile() {
    model.startGame(model.getDeck(), 52, 4, false);
    model.move(PileType.CASCADE, 0, 0, PileType.OPEN, 0);
    assertEquals(0, model.getNumCardsInCascadePile(0));
    assertEquals(1, model.getNumCardsInOpenPile(0));
    model.move(PileType.OPEN, 0, 0, PileType.CASCADE, 14);
    assertEquals(0, model.getNumCardsInOpenPile(0));
    assertEquals(2, model.getNumCardsInCascadePile(14));
    assertEquals(new Card(CardValue.TWO, Suit.CLUBS), model.getCascadeCardAt(14, 0));
    assertEquals(new Card(CardValue.ACE, Suit.DIAMONDS), model.getCascadeCardAt(14, 1));
  }

  //tests that an exception is thrown when moving a card from an open pile to a cascade pile
  //with value that is not one less than the top card in the cascade pile
  @Test(expected = IllegalArgumentException.class)
  public void testMoveOpenPileToCascadePileInvalidBuildNumber() {
    model.startGame(model.getDeck(), 52, 4, false);
    model.move(PileType.CASCADE, 1, 0, PileType.OPEN, 0);
    model.move(PileType.OPEN, 0, 0, PileType.CASCADE, 13);
  }

  //tests that an exception is thrown when moving a card from an open pile to a cascade pile
  //with suit color not opposite of the top card in the cascade pile
  @Test(expected = IllegalArgumentException.class)
  public void testMoveOpenPileToCascadePileInvalidBuildColor() {
    model.startGame(model.getDeck(), 52, 4, false);
    model.move(PileType.CASCADE, 0, 0, PileType.OPEN, 0);
    model.move(PileType.OPEN, 0, 0, PileType.CASCADE, 1);
  }

  //tests that a card can be moved from an open pile to an empty cascade pile
  @Test
  public void testMoveOpenPileToEmptyCascadePile() {
    model.startGame(model.getDeck(), 52, 4, false);
    model.move(PileType.CASCADE, 0, 0, PileType.OPEN, 0);
    assertEquals(0, model.getNumCardsInCascadePile(0));
    assertEquals(1, model.getNumCardsInOpenPile(0));
    model.move(PileType.OPEN, 0, 0, PileType.CASCADE, 0);
    assertEquals(0, model.getNumCardsInOpenPile(0));
    assertEquals(1, model.getNumCardsInCascadePile(0));
  }

  //tests that a card can be moved from an open pile to an empty foundation pile so long as
  //it is a valid build for the foundation pile (an ace)
  @Test
  public void testMoveOpenPileToEmptyFoundationPile() {
    model.startGame(model.getDeck(), 52, 4, false);
    model.move(PileType.CASCADE, 0, 0, PileType.OPEN, 0);
    assertEquals(0, model.getNumCardsInCascadePile(0));
    assertEquals(1, model.getNumCardsInOpenPile(0));
    model.move(PileType.OPEN, 0, 0, PileType.FOUNDATION, 0);
    assertEquals(0, model.getNumCardsInOpenPile(0));
    assertEquals(1, model.getNumCardsInFoundationPile(0));
    assertEquals(new Card(CardValue.ACE, Suit.DIAMONDS), model.getFoundationCardAt(0, 0));
  }

  //tests that a card can be moved from an open pile to a non-empty foundation pile so long as
  //it is a valid build for the foundation pile (same suit and one greater in value than top
  //card in pile)
  @Test
  public void testMoveOpenPileToNonEmptyFoundationPile() {
    model.startGame(model.getDeck(), 52, 4, false);
    model.move(PileType.CASCADE, 0, 0, PileType.FOUNDATION, 0);
    assertEquals(0, model.getNumCardsInCascadePile(0));
    assertEquals(1, model.getNumCardsInFoundationPile(0));
    model.move(PileType.CASCADE, 1, 0, PileType.OPEN, 0);
    assertEquals(0, model.getNumCardsInCascadePile(1));
    assertEquals(1, model.getNumCardsInOpenPile(0));
    model.move(PileType.OPEN, 0, 0, PileType.FOUNDATION, 0);
    assertEquals(0, model.getNumCardsInOpenPile(0));
    assertEquals(2, model.getNumCardsInFoundationPile(0));
    assertEquals(new Card(CardValue.ACE, Suit.DIAMONDS), model.getFoundationCardAt(0, 0));
    assertEquals(new Card(CardValue.TWO, Suit.DIAMONDS), model.getFoundationCardAt(0, 1));
  }

  //tests that an exception is thrown when trying to add a card from an open pile to an empty
  //foundation with value not an ace
  @Test(expected = IllegalArgumentException.class)
  public void testMoveOpenPileToEmptyFoundationPileInvalid() {
    model.startGame(model.getDeck(), 52, 4, false);
    model.move(PileType.CASCADE, 1, 0, PileType.OPEN, 0);
    model.move(PileType.OPEN, 0, 0, PileType.FOUNDATION, 0);
  }

  //tests that an exception is thrown when trying to add a card from an open pile to a non-empty
  //foundation with value not being one greater that the card at the top of the foundation pile
  @Test(expected = IllegalArgumentException.class)
  public void testMoveOpenPileToFoundationPileInvalidNumber() {
    model.startGame(model.getDeck(), 52, 4, false);
    model.move(PileType.CASCADE, 0, 0, PileType.FOUNDATION, 0);
    model.move(PileType.CASCADE, 2, 0, PileType.OPEN, 0);
    model.move(PileType.OPEN, 0, 0, PileType.FOUNDATION, 0);
  }

  //tests that an exception is thrown when trying to add a card from an open pile to a non-empty
  //foundation with a different suit than the card at the top of the foundation pile
  @Test(expected = IllegalArgumentException.class)
  public void testMoveOpenPileToFoundationPileInvalidSuit() {
    model.startGame(model.getDeck(), 52, 4, false);
    model.move(PileType.CASCADE, 0, 0, PileType.FOUNDATION, 0);
    model.move(PileType.CASCADE, 14, 0, PileType.OPEN, 0);
    model.move(PileType.OPEN, 0, 0, PileType.FOUNDATION, 0);
  }

  //tests that an exception is thrown when trying to move a card that is not at the end of a
  //pile
  @Test(expected = IllegalArgumentException.class)
  public void testMoveNotLastCardOfPile() {
    model.startGame(model.getDeck(), 8, 4, false);
    model.move(PileType.CASCADE, 3, 5, PileType.OPEN, 0);
  }

  //tests that an exception is thrown when trying to move a card at a negative index pile
  @Test(expected = IllegalArgumentException.class)
  public void testMoveNegativeIndexPileNumber() {
    model.startGame(model.getDeck(), 8, 4, false);
    model.move(PileType.CASCADE, -1, 5, PileType.OPEN, 0);
  }

  //tests that an exception is thrown when trying to move a card at a too large of an index pile
  @Test(expected = IllegalArgumentException.class)
  public void testMoveTooLargeIndexPileNumber() {
    model.startGame(model.getDeck(), 8, 4, false);
    model.move(PileType.CASCADE, 8, 5, PileType.OPEN, 0);
  }

  //tests that an exception is thrown when trying to move at a negative index in a card
  @Test(expected = IllegalArgumentException.class)
  public void testMoveNegativeIndexCardNumberInPile() {
    model.startGame(model.getDeck(), 8, 4, false);
    model.move(PileType.CASCADE, 3, -1, PileType.OPEN, 0);
  }

  //tests that an exception is thrown when trying to move at a too large of an index in a card
  @Test(expected = IllegalArgumentException.class)
  public void testMoveTooLargeIndexCardNumberInPile() {
    model.startGame(model.getDeck(), 8, 4, false);
    model.move(PileType.CASCADE, 3, 7, PileType.OPEN, 0);
  }

  //tests that an exception is thrown when trying to move a card to a destination pile of a
  //negative index
  @Test(expected = IllegalArgumentException.class)
  public void testMoveNegativeIndexDestinationPileNumber() {
    model.startGame(model.getDeck(), 8, 4, false);
    model.move(PileType.CASCADE, 3, 6, PileType.OPEN, -1);
  }

  //tests that an exception is thrown when trying to move a card to a destination pile of a
  //negative index
  @Test(expected = IllegalArgumentException.class)
  public void testMoveTooLargeIndexDestinationPileNumber() {
    model.startGame(model.getDeck(), 8, 4, false);
    model.move(PileType.CASCADE, 3, 6, PileType.OPEN, 4);
  }

  //tests that the game is not over when the game has not started yet
  @Test
  public void testIsGameOverWhenGameHasNotStarted() {
    assertFalse(model.isGameOver());
  }

  //tests that the game is not over when the game has started but all the foundation piles are
  //not full
  @Test
  public void testIsGameOverFoundationPilesAllNotFull() {
    model.startGame(model.getDeck(), 8, 4, false);
    assertFalse(model.isGameOver());
  }

  //tests that the game is over since the game has started and all the foundation piles are full
  @Test
  public void testIsGameOver() {
    model.startGame(model.getDeck(), 52, 4, false);
    int i;
    int j;
    for (i = 0; i < Suit.values().length; i++) {
      for (j = 0; j < CardValue.values().length; j++) {
        model.move(PileType.CASCADE, i * CardValue.values().length + j,
            0, PileType.FOUNDATION, i);
      }
    }
    assertTrue(model.isGameOver());
  }

  /**
   * Creates the concrete implementation of a freecell model for testing.
   * @return the freecell model object
   */
  protected abstract FreecellModel<Card> modelCreate();
}