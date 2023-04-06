package Piles;
import Card.Card;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public abstract class Pile extends JLayeredPane {
    protected final ArrayList<Card> cards = new ArrayList<>();

    protected Card getFirstCard() {
        if (!cards.isEmpty()) {
            return cards.get(0);
        }
        else {
            return null;
        }
    }

    protected Card getLastCard() {
        if (!cards.isEmpty()) {
            return cards.get(cards.size() - 1);
        }
        else {
            return null;
        }
    }

    public abstract void place();
    public abstract boolean merge(Pile pile);

    /**
     * used for drag and drop
     */
    public abstract OffsetPile move(Point point);

    public void cardsAdd(Card card) {
        cards.add(card);
    }

    public void cardsAdd(Pile pile) {
        cards.addAll(pile.cards);
    }
}