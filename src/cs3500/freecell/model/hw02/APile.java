package cs3500.freecell.model.hw02;

import java.util.ArrayList;
import java.util.List;

abstract class APile implements Pile<Card> {

  private final List<Card> pile;

  protected APile() {
    this.pile = new ArrayList<>();
  }

  @Override
  public void add(Card card) {
    this.pile.add(card);
  }

  @Override
  public Card get(int index) throws IndexOutOfBoundsException {
    if (index < 0 || index >= this.size()) {
      throw new IndexOutOfBoundsException("Index is out of bounds");
    }
    return this.pile.get(index);
  }

  @Override
  public int size() {
    return this.pile.size();
  }

  @Override
  public Card takeLast() throws IllegalStateException {
    if (this.size() == 0) {
      throw new IllegalStateException("Cannot take the last card of a pile in an empty pile");
    }
    return this.pile.remove(this.pile.size() - 1);
  }

  public abstract void gameAdd(Card card, Pile<Card> sourcePile);
}
