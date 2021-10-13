import static org.junit.Assert.assertEquals;

import cs3500.freecell.model.FreecellModel;

import cs3500.freecell.model.PileType;
import cs3500.freecell.model.hw02.Card;
import cs3500.freecell.model.hw02.SimpleFreecellModel;
import cs3500.freecell.model.hw04.MultiMoveFreecellModel;
import cs3500.freecell.view.FreecellTextView;
import cs3500.freecell.view.FreecellView;
import java.io.IOException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Class that tests the freecell text view.
 */
public class FreecellTextViewTest {

  private FreecellModel<Card> model;
  private FreecellView view;

  //initializes the model to use the SimpleFreecellModel object and FreecelTextView object
  @Before
  public void init() {
    model = new SimpleFreecellModel();
    view = new FreecellTextView(model);
  }

  //tests that an exception is thrown when a null model is passed into the unary constructor
  @Test(expected = IllegalArgumentException.class)
  public void testUnaryConstructorNullModel() {
    new FreecellTextView(null);
  }

  //tests that an exception is thrown when a null model is passed into the binary constructor
  @Test(expected = IllegalArgumentException.class)
  public void testBinaryConstructorNullModel() {
    new FreecellTextView(null, System.out);
  }

  //tests that if the game has not started yet that the view returns an empty string
  @Test
  public void testToStringGameNotStarted() {
    assertEquals("", view.toString());
  }

  //tests that the view returns an empty string if start game throws an exception
  @Test
  public void testToStringStartGameThrowsException() {
    try {
      model.startGame(model.getDeck(), 3, 4, false);
      Assert.fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals("", view.toString());
    }
  }

  //tests that the string is formatted correctly for a game that has just started and no moving
  //has been done
  @Test
  public void testToStringGameJustStarted() {
    model.startGame(model.getDeck(), 8, 4, false);
    assertEquals("F1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: A♦, 9♦, 4♣, Q♣, 7♥, 2♠, 10♠\n"
        + "C2: 2♦, 10♦, 5♣, K♣, 8♥, 3♠, J♠\n"
        + "C3: 3♦, J♦, 6♣, A♥, 9♥, 4♠, Q♠\n"
        + "C4: 4♦, Q♦, 7♣, 2♥, 10♥, 5♠, K♠\n"
        + "C5: 5♦, K♦, 8♣, 3♥, J♥, 6♠\n"
        + "C6: 6♦, A♣, 9♣, 4♥, Q♥, 7♠\n"
        + "C7: 7♦, 2♣, 10♣, 5♥, K♥, 8♠\n"
        + "C8: 8♦, 3♣, J♣, 6♥, A♠, 9♠", view.toString());
  }

  //tests that the string is formatted correctly for a game where a card has been moved to
  //an open pile
  @Test
  public void testToStringCardsInOpenPile() {
    model.startGame(model.getDeck(), 8, 4, false);
    model.move(PileType.CASCADE, 3, 6, PileType.OPEN, 1);
    assertEquals("F1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2: K♠\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: A♦, 9♦, 4♣, Q♣, 7♥, 2♠, 10♠\n"
        + "C2: 2♦, 10♦, 5♣, K♣, 8♥, 3♠, J♠\n"
        + "C3: 3♦, J♦, 6♣, A♥, 9♥, 4♠, Q♠\n"
        + "C4: 4♦, Q♦, 7♣, 2♥, 10♥, 5♠\n"
        + "C5: 5♦, K♦, 8♣, 3♥, J♥, 6♠\n"
        + "C6: 6♦, A♣, 9♣, 4♥, Q♥, 7♠\n"
        + "C7: 7♦, 2♣, 10♣, 5♥, K♥, 8♠\n"
        + "C8: 8♦, 3♣, J♣, 6♥, A♠, 9♠", view.toString());

  }

  //tests that the string is formatted correctly for a game where cards have been moved to
  //a foundation pile
  @Test
  public void testToStringCardsInFoundationPile() {
    model.startGame(model.getDeck(), 52, 4, false);
    model.move(PileType.CASCADE, 0, 0, PileType.FOUNDATION, 2);
    model.move(PileType.CASCADE, 1, 0, PileType.FOUNDATION, 2);
    assertEquals("F1:\n"
        + "F2:\n"
        + "F3: A♦, 2♦\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1:\n"
        + "C2:\n"
        + "C3: 3♦\n"
        + "C4: 4♦\n"
        + "C5: 5♦\n"
        + "C6: 6♦\n"
        + "C7: 7♦\n"
        + "C8: 8♦\n"
        + "C9: 9♦\n"
        + "C10: 10♦\n"
        + "C11: J♦\n"
        + "C12: Q♦\n"
        + "C13: K♦\n"
        + "C14: A♣\n"
        + "C15: 2♣\n"
        + "C16: 3♣\n"
        + "C17: 4♣\n"
        + "C18: 5♣\n"
        + "C19: 6♣\n"
        + "C20: 7♣\n"
        + "C21: 8♣\n"
        + "C22: 9♣\n"
        + "C23: 10♣\n"
        + "C24: J♣\n"
        + "C25: Q♣\n"
        + "C26: K♣\n"
        + "C27: A♥\n"
        + "C28: 2♥\n"
        + "C29: 3♥\n"
        + "C30: 4♥\n"
        + "C31: 5♥\n"
        + "C32: 6♥\n"
        + "C33: 7♥\n"
        + "C34: 8♥\n"
        + "C35: 9♥\n"
        + "C36: 10♥\n"
        + "C37: J♥\n"
        + "C38: Q♥\n"
        + "C39: K♥\n"
        + "C40: A♠\n"
        + "C41: 2♠\n"
        + "C42: 3♠\n"
        + "C43: 4♠\n"
        + "C44: 5♠\n"
        + "C45: 6♠\n"
        + "C46: 7♠\n"
        + "C47: 8♠\n"
        + "C48: 9♠\n"
        + "C49: 10♠\n"
        + "C50: J♠\n"
        + "C51: Q♠\n"
        + "C52: K♠", view.toString());
  }


  //tests that the renderBoard method transmits any empty string to the appendable object for
  //a game that has not started
  @Test
  public void testRenderBoardGameNotStarted() throws IOException {
    Appendable ap = new StringBuilder();
    FreecellView view2 = new FreecellTextView(model, ap);
    view2.renderBoard();
    assertEquals("", ap.toString());
  }

  //tests that the renderBoard method transmits any empty string to the appendable object for
  //a game that threw an exception when startGame was called
  @Test
  public void testRenderBoardStartGameThrowsException() throws IOException {
    Appendable ap = new StringBuilder();
    FreecellView view2 = new FreecellTextView(model, ap);
    try {
      model.startGame(model.getDeck(), 3, 4, false);
      Assert.fail();
    }
    catch (IllegalArgumentException e) {
      view2.renderBoard();
      assertEquals("", ap.toString());
    }
  }

  //tests that the renderBoard method properly transmits the board of the freecell to the
  //appendable for a game that just started
  @Test
  public void testRenderBoardFreshGame() throws IOException {
    Appendable ap = new StringBuilder();
    FreecellView view2 = new FreecellTextView(model, ap);
    model.startGame(model.getDeck(), 8, 4, false);
    view2.renderBoard();
    assertEquals("F1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: A♦, 9♦, 4♣, Q♣, 7♥, 2♠, 10♠\n"
        + "C2: 2♦, 10♦, 5♣, K♣, 8♥, 3♠, J♠\n"
        + "C3: 3♦, J♦, 6♣, A♥, 9♥, 4♠, Q♠\n"
        + "C4: 4♦, Q♦, 7♣, 2♥, 10♥, 5♠, K♠\n"
        + "C5: 5♦, K♦, 8♣, 3♥, J♥, 6♠\n"
        + "C6: 6♦, A♣, 9♣, 4♥, Q♥, 7♠\n"
        + "C7: 7♦, 2♣, 10♣, 5♥, K♥, 8♠\n"
        + "C8: 8♦, 3♣, J♣, 6♥, A♠, 9♠", ap.toString());
  }

  //tests that the renderBoard method properly transmits the board of the freecell to the
  //appendable for a game where one move to an open and foundation pile has happened.
  @Test
  public void testRenderBoardAfterMoves() throws IOException {
    Appendable ap = new StringBuilder();
    FreecellView view2 = new FreecellTextView(model, ap);
    model.startGame(model.getDeck(), 8, 4, false);
    model.move(PileType.CASCADE, 7,5, PileType.OPEN, 1);
    model.move(PileType.CASCADE, 7,4, PileType.FOUNDATION, 2);
    view2.renderBoard();
    assertEquals("F1:\n"
        + "F2:\n"
        + "F3: A♠\n"
        + "F4:\n"
        + "O1:\n"
        + "O2: 9♠\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: A♦, 9♦, 4♣, Q♣, 7♥, 2♠, 10♠\n"
        + "C2: 2♦, 10♦, 5♣, K♣, 8♥, 3♠, J♠\n"
        + "C3: 3♦, J♦, 6♣, A♥, 9♥, 4♠, Q♠\n"
        + "C4: 4♦, Q♦, 7♣, 2♥, 10♥, 5♠, K♠\n"
        + "C5: 5♦, K♦, 8♣, 3♥, J♥, 6♠\n"
        + "C6: 6♦, A♣, 9♣, 4♥, Q♥, 7♠\n"
        + "C7: 7♦, 2♣, 10♣, 5♥, K♥, 8♠\n"
        + "C8: 8♦, 3♣, J♣, 6♥", ap.toString());
  }

  //tests that the renderMessage properly transmits messages to the appendable for an empty string
  @Test
  public void testRenderMessageValidEmptyString() throws IOException {
    Appendable ap = new StringBuilder();
    FreecellView view2 = new FreecellTextView(model, ap);
    model.startGame(model.getDeck(), 8, 4, false);
    view2.renderMessage("");
    assertEquals("", ap.toString());
  }

  //tests that the renderMessage properly transmits messages to the appendable for a string
  //if an exception is thrown when startGame is called
  @Test
  public void testRenderMessageStartGameThrowsException() throws IOException {
    Appendable ap = new StringBuilder();
    FreecellView view2 = new FreecellTextView(model, ap);
    try {
      model.startGame(model.getDeck(), 8, 4, false);
    }
    catch (IllegalArgumentException e) {
      view2.renderMessage("hello");
      assertEquals("hello", ap.toString());
    }
  }

  //tests that the renderMessage properly transmits messages to the appendable
  @Test
  public void testRenderMessageValid() throws IOException {
    Appendable ap = new StringBuilder();
    FreecellView view2 = new FreecellTextView(model, ap);
    view2.renderMessage("hello ");
    view2.renderMessage("hello");
    assertEquals("hello hello", ap.toString());
  }

  //tests that the renderMessage ignores null inputs
  @Test
  public void testRenderMessageIgnoresNull() throws IOException {
    Appendable ap = new StringBuilder();
    FreecellView view2 = new FreecellTextView(model, ap);
    view2.renderMessage(null);
    assertEquals("", ap.toString());
  }

  //tests that an IOException is thrown when renderBoard is called
  @Test(expected = IOException.class)
  public void testRenderBoardException() throws IOException {
    Appendable ap = new MockAppendable();
    FreecellView view2 = new FreecellTextView(model, ap);
    view2.renderBoard();
  }

  //tests that an IOException is thrown when renderMessage is called
  @Test(expected = IOException.class)
  public void testRenderMessageException() throws IOException {
    Appendable ap = new MockAppendable();
    FreecellView view2 = new FreecellTextView(model, ap);
    view2.renderMessage("hello");
  }

  //tests that when the multimove model throws an exception in the move method for an invalid move
  //that the view does not change
  @Test
  public void testRenderBoardInvalidMultiMove() throws IOException {
    FreecellModel<Card> multiMoveModel = new MultiMoveFreecellModel();
    Appendable ap = new StringBuilder();
    FreecellView viewMulti = new FreecellTextView(multiMoveModel, ap);
    multiMoveModel.startGame(multiMoveModel.getDeck(), 8, 4, false);
    try {
      multiMoveModel.move(PileType.CASCADE, 0, 5, PileType.CASCADE, 1);
    } catch (IllegalArgumentException e) {
      viewMulti.renderBoard();
      assertEquals("F1:\n"
          + "F2:\n"
          + "F3:\n"
          + "F4:\n"
          + "O1:\n"
          + "O2:\n"
          + "O3:\n"
          + "O4:\n"
          + "C1: A♦, 9♦, 4♣, Q♣, 7♥, 2♠, 10♠\n"
          + "C2: 2♦, 10♦, 5♣, K♣, 8♥, 3♠, J♠\n"
          + "C3: 3♦, J♦, 6♣, A♥, 9♥, 4♠, Q♠\n"
          + "C4: 4♦, Q♦, 7♣, 2♥, 10♥, 5♠, K♠\n"
          + "C5: 5♦, K♦, 8♣, 3♥, J♥, 6♠\n"
          + "C6: 6♦, A♣, 9♣, 4♥, Q♥, 7♠\n"
          + "C7: 7♦, 2♣, 10♣, 5♥, K♥, 8♠\n"
          + "C8: 8♦, 3♣, J♣, 6♥, A♠, 9♠", ap.toString());
    }
  }

  //tests that the view updates correctly for a multimove
  @Test
  public void testRenderBoardValidMultimove() throws IOException {
    FreecellModel<Card> multiMoveModel = new MultiMoveFreecellModel();
    Appendable ap = new StringBuilder();
    FreecellView viewMulti = new FreecellTextView(multiMoveModel, ap);
    multiMoveModel.startGame(multiMoveModel.getDeck(), 52, 4, false);
    multiMoveModel.move(PileType.CASCADE, 13, 0, PileType.CASCADE, 1);
    multiMoveModel.move(PileType.CASCADE, 1, 0, PileType.CASCADE, 15);
    viewMulti.renderBoard();
    assertEquals("F1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: A♦\n"
        + "C2:\n"
        + "C3: 3♦\n"
        + "C4: 4♦\n"
        + "C5: 5♦\n"
        + "C6: 6♦\n"
        + "C7: 7♦\n"
        + "C8: 8♦\n"
        + "C9: 9♦\n"
        + "C10: 10♦\n"
        + "C11: J♦\n"
        + "C12: Q♦\n"
        + "C13: K♦\n"
        + "C14:\n"
        + "C15: 2♣\n"
        + "C16: 3♣, 2♦, A♣\n"
        + "C17: 4♣\n"
        + "C18: 5♣\n"
        + "C19: 6♣\n"
        + "C20: 7♣\n"
        + "C21: 8♣\n"
        + "C22: 9♣\n"
        + "C23: 10♣\n"
        + "C24: J♣\n"
        + "C25: Q♣\n"
        + "C26: K♣\n"
        + "C27: A♥\n"
        + "C28: 2♥\n"
        + "C29: 3♥\n"
        + "C30: 4♥\n"
        + "C31: 5♥\n"
        + "C32: 6♥\n"
        + "C33: 7♥\n"
        + "C34: 8♥\n"
        + "C35: 9♥\n"
        + "C36: 10♥\n"
        + "C37: J♥\n"
        + "C38: Q♥\n"
        + "C39: K♥\n"
        + "C40: A♠\n"
        + "C41: 2♠\n"
        + "C42: 3♠\n"
        + "C43: 4♠\n"
        + "C44: 5♠\n"
        + "C45: 6♠\n"
        + "C46: 7♠\n"
        + "C47: 8♠\n"
        + "C48: 9♠\n"
        + "C49: 10♠\n"
        + "C50: J♠\n"
        + "C51: Q♠\n"
        + "C52: K♠", ap.toString());
  }
}