package cs3500.freecell.view;

import cs3500.freecell.model.FreecellModelState;
import java.io.IOException;

/**
 * Class that represents a text view of a freecell game.
 */
public class FreecellTextView implements FreecellView {

  private final FreecellModelState<?> model;
  private final Appendable ap;

  /**
   * Constructor that takes in the given model and uses the console as the appendable.
   * @param model the freecell model
   * @throws IllegalArgumentException if the given model is null
   */
  public FreecellTextView(FreecellModelState<?> model) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Model given is null");
    }
    this.model = model;
    this.ap = System.out;
  }

  /**
   * Constructor that takes in the given model and the given appendable.
   * @param model the freecell model
   * @param ap the appendable object
   * @throws IllegalArgumentException if the given model is null
   */
  public FreecellTextView(FreecellModelState<?> model, Appendable ap) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    this.model = model;
    if (ap == null) {
      this.ap = System.out;
    }
    else {
      this.ap = ap;
    }
  }

  @Override
  public String toString() {
    int numCascadePiles = model.getNumCascadePiles();
    int numOpenPiles = model.getNumOpenPiles();
    if (numCascadePiles == -1 || numOpenPiles == -1) {
      return "";
    } else {
      String viewSoFar = "";
      return viewSoFar.concat(foundationPilesString()).concat(openPilesString())
          .concat(cascadePilesString());
    }
  }

  @Override
  public void renderBoard() throws IOException {
    ap.append(toString());
  }

  @Override
  public void renderMessage(String message) throws IOException {
    if (message == null) {
      return;
    }
    ap.append(message);
  }

  /**
   * Constructs a string formatted to represent the foundation piles.
   *
   * @return string representation of the foundation piles
   */
  private String foundationPilesString() {
    String stringSoFar = "";
    int i;
    for (i = 0; i < 4; i++) {
      String rowSoFar = "F" + (String.valueOf(i + 1)) + ":";
      int j;
      for (j = 0; j < model.getNumCardsInFoundationPile(i); j++) {
        if (j == model.getNumCardsInFoundationPile(i) - 1) {
          rowSoFar += " " + model.getFoundationCardAt(i, j).toString();
        } else {
          rowSoFar += " " + model.getFoundationCardAt(i, j).toString() + ",";
        }
      }
      stringSoFar += rowSoFar + "\n";
    }
    return stringSoFar;
  }

  /**
   * Constructs a string formatted to represent the open piles.
   *
   * @return string representation of the open piles
   */
  private String openPilesString() {
    String stringSoFar = "";
    int i;
    for (i = 0; i < model.getNumOpenPiles(); i++) {
      String rowSoFar = "O" + (String.valueOf(i + 1)) + ":";
      if (model.getNumCardsInOpenPile(i) == 1) {
        rowSoFar += " " + model.getOpenCardAt(i).toString();
      }
      stringSoFar += rowSoFar + "\n";
    }
    return stringSoFar;
  }

  /**
   * Constructs a string formatted to represent the cascade piles.
   *
   * @return string representation of the cascade piles
   */
  private String cascadePilesString() {
    String stringSoFar = "";
    int i;
    for (i = 0; i < model.getNumCascadePiles(); i++) {
      String rowSoFar = "C" + (String.valueOf(i + 1)) + ":";
      int j;
      for (j = 0; j < model.getNumCardsInCascadePile(i); j++) {
        if (j == model.getNumCardsInCascadePile(i) - 1) {
          rowSoFar += " " + model.getCascadeCardAt(i, j).toString();
        } else {
          rowSoFar += " " + model.getCascadeCardAt(i, j).toString() + ",";
        }
      }
      if (i == model.getNumCascadePiles() - 1) {
        stringSoFar += rowSoFar;
      } else {
        stringSoFar += rowSoFar + "\n";
      }
    }
    return stringSoFar;
  }

}
