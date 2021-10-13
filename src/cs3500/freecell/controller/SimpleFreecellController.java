package cs3500.freecell.controller;

import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.PileType;
import cs3500.freecell.model.hw02.Card;
import cs3500.freecell.view.FreecellTextView;
import cs3500.freecell.view.FreecellView;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * Controller for a freecell game, facilitating how the game is run and viewed through sequence of
 * operations.
 */
public class SimpleFreecellController implements FreecellController<Card> {

  private final FreecellModel<Card> model;
  private final Readable in;
  private final Appendable out;
  private final FreecellView view;

  /**
   * Constructor for a freecell controller.
   *
   * @param model the freecell model
   * @param rd    the readable
   * @param ap    the appendable
   * @throws IllegalArgumentException if any of the params given are null
   */
  public SimpleFreecellController(FreecellModel<Card> model, Readable rd, Appendable ap)
      throws IllegalArgumentException {
    if (model == null || rd == null || ap == null) {
      throw new IllegalArgumentException("Model, readable, and appendable cannot be null");
    }
    this.model = model;
    this.in = rd;
    this.out = ap;
    this.view = new FreecellTextView(model, ap);

  }

  @Override
  public void playGame(List<Card> deck, int numCascades, int numOpens, boolean shuffle)
      throws IllegalStateException, IllegalArgumentException {
    //check if the deck is null
    if (deck == null) {
      throw new IllegalArgumentException("Deck cannot be null");
    }
    //try to start the game
    try {
      model.startGame(deck, numCascades, numOpens, shuffle);
    } catch (IllegalArgumentException e) {
      sendMessage("Could not start game.");
      return;
    }
    //render the board so the user can see it
    try {
      view.renderBoard();
      out.append("\n");
    } catch (IOException e) {
      throw new IllegalStateException("Transmission failed");
    }

    Scanner scan = new Scanner(this.in);

    //keep asking for moves until the game is over
    while (!model.isGameOver()) {
      //parsed strings for move method
      String sourcePileTypeString;
      String sourcePileIndexString;
      String sourcePileCardIndexString;
      String destinationPileTypeString;
      String destinationPileIndexString;

      sendMessage("Play a move: \n");

      //get source pile input, keep trying until a valid input is found
      String[] sourcePile = parseValidPile(scan, "source");
      if (sourcePile[0].equals("q")) {
        sendMessage("Game quit prematurely.");
        return;
      } else {
        sourcePileTypeString = sourcePile[0];
        sourcePileIndexString = sourcePile[1];
      }

      //get card index, keep trying until a valid input is found
      sourcePileCardIndexString = parseValidCardIndex(scan);
      if (sourcePileCardIndexString.equals("q")) {
        sendMessage("Game quit prematurely.");
        return;
      }

      //get destination pile input, keep trying until a valid input is found
      String[] destinationPile = parseValidPile(scan, "destination");
      if (destinationPile[0].equals("q")) {
        sendMessage("Game quit prematurely.");
        return;
      } else {
        destinationPileTypeString = destinationPile[0];
        destinationPileIndexString = destinationPile[1];
      }

      //convert parsed Strings into correct types for move method
      PileType sourcePileType = pileTypeLetterToPileType(sourcePileTypeString);
      int sourcePileIndex = pileTypeIndexToNumber(sourcePileIndexString);
      int sourcePileCardIndex = pileTypeIndexToNumber(sourcePileCardIndexString);
      PileType destinationPileType = pileTypeLetterToPileType(destinationPileTypeString);
      int destinationPileIndex = pileTypeIndexToNumber(destinationPileIndexString);

      //perform the move method, if a correct move is made, then tell the view to render a new
      //board otherwise tell the view to print a message as to why the move did not work
      try {
        model
            .move(sourcePileType, sourcePileIndex - 1, sourcePileCardIndex - 1, destinationPileType,
                destinationPileIndex - 1);
        try {
          view.renderBoard();
          out.append("\n");
        } catch (IOException e) {
          throw new IllegalStateException("Transmission failed");
        }
      } catch (IllegalArgumentException e) {
        try {
          view.renderMessage("Invalid move. Try again. " + e.getMessage() + "\n");
          continue;
        } catch (IOException ex) {
          throw new IllegalStateException("Transmission failed");
        }
      }
    }

    //tell the view the game is over
    try {
      view.renderMessage("Game over.");
    } catch (IOException e) {
      throw new IllegalStateException("Transmission failed");
    }
  }

  /**
   * Transmits the given message to the appendable object in the field of this class.
   *
   * @param message the message to transmit
   */
  private void sendMessage(String message) {
    try {
      view.renderMessage(message);
    } catch (IOException ex) {
      throw new IllegalStateException("Transmission failed");
    }
  }

  /**
   * Uses the given scanner to scan through the readable until a valid pile is found, and then
   * returns the letter of the pile as a String and the pile number as a String together in an array
   * or the letter q if the game was stopped. Uses the given string to render different messages
   * for the piles.
   *
   * @param scan the scanner
   * @param pile the type of pile as a string
   * @return an array of the letter of the pile as a String and the pile number as a String or the
   *     letter q
   */
  private String[] parseValidPile(Scanner scan, String pile) {
    String[] array = new String[2];
    while (true) {
      if (!scan.hasNext()) {
        throw new IllegalStateException("No more inputs to read from");
      }
      String input = scan.next();

      if (input.equals("q") || input.equals("Q")) {
        array[0] = "q";
        return array;
      }

      String pileTypeSymbol = input.substring(0, 1).toLowerCase();

      switch (pileTypeSymbol) {
        case "c":
          array[0] = "C";
          break;
        case "f":
          array[0] = "F";
          break;
        case "o":
          array[0] = "O";
          break;
        default:
          sendMessage("Invalid " + pile + " pile, please input again: \n");
          continue;
      }

      if (input.length() >= 2) {
        String maybePileIndex = input.substring(1);
        try {
          Integer.parseInt(maybePileIndex);
          array[1] = maybePileIndex;
          break;
        } catch (NumberFormatException e) {
          sendMessage("Invalid " + pile + " pile, please input again: \n");
          continue;
        }
      } else {
        sendMessage("Invalid " + pile + " pile, please input again: \n");
        continue;
      }
    }
    return array;
  }

  /**
   * Uses the given scanner to scan through the readable until a valid card index is found, and then
   * returns the card index as a String or returns the letter q if the game was stopped.
   * @param scan the scanner
   * @return the card index as a string or the letter q if the game was stopped
   */
  private String parseValidCardIndex(Scanner scan) {
    while (true) {
      if (!scan.hasNext()) {
        throw new IllegalStateException("No more inputs to read from");
      }
      String maybeSourceCardPileIndex = scan.next();
      if (maybeSourceCardPileIndex.equals("q") || maybeSourceCardPileIndex.equals("Q")) {
        return "q";
      }
      try {
        Integer.parseInt(maybeSourceCardPileIndex);
        return maybeSourceCardPileIndex;
      } catch (NumberFormatException e) {
        sendMessage("Invalid card index, please input again: \n");
        continue;
      }
    }
  }

  /**
   * Converts the given pile type letter to the corresponding pile type.
   *
   * @param letter the pile type letter
   * @return the corresponding pile type
   */
  private PileType pileTypeLetterToPileType(String letter) {
    switch (letter) {
      case "C":
        return PileType.CASCADE;
      case "F":
        return PileType.FOUNDATION;
      case "O":
        return PileType.OPEN;
      default:
        return null;
    }
  }

  /**
   * Converts the given pile index as a String to an integer.
   *
   * @param pileIndexString the pile index as a string
   * @return pile index as an integer
   */
  private int pileTypeIndexToNumber(String pileIndexString) {
    try {
      return Integer.parseInt(pileIndexString);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Pile index cannot be parsed to String");
    }
  }
}
