package cs3500.freecell.model;

import cs3500.freecell.model.hw02.Card;
import cs3500.freecell.model.hw02.SimpleFreecellModel;
import cs3500.freecell.model.hw04.MultiMoveFreecellModel;

/**
 * Class with a factory method to produce different freecell models.
 */
public class FreecellModelCreator {

  /**
   * Represents the two types of freecell model, one that only allows single moves and another
   * that allows multimove.
   */
  public enum GameType {
    SINGLEMOVE, MULTIMOVE
  }

  /**
   * Factory method that creates different freecell models depending on the game type given.
   * @param type the type of game
   * @return the freecell model
   */
  public static FreecellModel<Card> create(GameType type) {
    if (type == null) {
      throw new IllegalArgumentException("Game type cannot be null");
    }
    else if (type == GameType.SINGLEMOVE) {
      return new SimpleFreecellModel();
    }
    else {
      return new MultiMoveFreecellModel();
    }
  }
}
