package cs3500.freecell.model.hw02;

/**
 * The interface for what operations a pile of cards should support.
 */
public interface Pile<K> {

  /**
   * Adds the given card to the pile.
   * @param card the card to add to the pile
   */
  void add(K card);

  /**
   * Gets the card at the given index.
   * @param index the index of the pile
   * @return the card at the given index
   * @throws IndexOutOfBoundsException when the index is not a valid index inside the bounds
   */
  K get(int index) throws IndexOutOfBoundsException;

  /**
   * Calculates the size of this pile.
   * @return the size of the pile
   */
  int size();

  /**
   * Removes the last card from this pile and returns it or throws an exception if the pile
   * is empty.
   * @return the last card of this pile
   * @throws IllegalStateException when the pile is empty
   */
  K takeLast() throws IllegalStateException;

  /**
   * Adds the given card to the top of the pile according to the rules of the freecell game if
   * the move is valid. If the move is invalid, add this card back to the source pile then throw
   * an exception.
   * @param card the given card
   * @param sourcePile the source pile where the card came from
   * @throws IllegalArgumentException when adding to the pile was an invalid move
   */
  void gameAdd(K card, Pile<K> sourcePile) throws IllegalArgumentException;
}
