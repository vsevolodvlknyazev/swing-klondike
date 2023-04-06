package Piles;
import Card.Card;

import javax.swing.*;
import java.awt.*;

public abstract class StraightPile extends Pile {
    public StraightPile() {
        setSize(Card.WIDTH,Card.HEIGHT);
    }

    @Override
    public void place() {
        // remove previously drawn card
        removeAll();
        if (!cards.isEmpty()) {
            add(getLastCard(), JLayeredPane.DEFAULT_LAYER);
        }
        repaint();
    }

    @Override
    public OffsetPile move(Point point) {
        if (!cards.isEmpty()) {
            OffsetPile subset = new OffsetPile();
            Card top = getLastCard();
            subset.cardsAdd(top);
            cards.remove(top);

            subset.setLocation(point);
            subset.place();
            place();
            return subset;
        }
        else {
            return null;
        }
    }
}