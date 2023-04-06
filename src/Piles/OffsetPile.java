package Piles;
import Card.Card;
import Frame.DndManager;

import java.awt.Point;
import java.util.List;

/**
 * It is used by the tableau piles (7 piles that make up the main table),
 * and to drag&drop from other piles (Stock, Foundation).
 * Cards are placed with an offset along the Y axis
 */
public class OffsetPile extends Pile {
    public final static int STARTING_OFFSET = 5;

    @Override
    public void place() {
        final int offset = getOffset();
        // remove all previously drawn cards
        removeAll();
        if (!cards.isEmpty()) {
            for (int i = 0; i < cards.size(); i++) {
                cards.get(i).setLocation(0,i*offset);
                add(cards.get(i),Integer.valueOf(i));
            }

            // free the top flipped card
            // after piles above were moved
            if (getLastCard().isBackSided()) {
                getLastCard().flip();
            }
        }
        setSize(Card.WIDTH, Card.HEIGHT+(cards.size()*offset));
    }

    @Override
    public boolean merge(Pile pile) {
        if (!cards.isEmpty()) {
            if (!isPlaceable(getLastCard(), pile.getFirstCard())) {
                return true;
            }
        }
        else {
            if (pile.getFirstCard().getValue() != Card.Royalty.KING) {
                return true;
            }
        }

        cardsAdd(pile);
        return false;
    }

    @Override
    public OffsetPile move(Point point) {
        Card card = getClickedCard(point);
        if (card == null || card.isBackSided()) {
            return null;
        }
        else {
            OffsetPile subset = new OffsetPile();
            final List<Card> cardsSubList = cards.subList(
                    cards.indexOf(card), cards.size());
            subset.cards.addAll(cardsSubList);
            cardsSubList.clear();

            DndManager.setRelativeComponentPos(
                    subset,card.getLocation(),point.getLocation());
            subset.place();
            return subset;
        }
    }

    public void startingFlip() {
        for (int i = 0; i < cards.size()-1; i++) {
            cards.get(i).flip();
        }
    }

    private boolean isPlaceable(Card parent, Card child) {
        if (parent.isRed()) {
            if (child.isRed()) {
                return false;
            }
        }
        else {
            if (!child.isRed()) {
                return false;
            }
        }

        return (parent.getValue() - 1) == child.getValue();
    }

    private Card getClickedCard(Point point) {
        if (!cards.isEmpty()) {
            int clickedIndex = (int)(point.getY() / getOffset());
            if (clickedIndex > cards.size() - 1) {
                // the last card clicked, it has the
                // largest area than the rest
                clickedIndex = cards.size() - 1;
            }
            return cards.get(clickedIndex);
        }
        else {
            return null;
        }
    }

    private int getOffset() {
        int offset = STARTING_OFFSET;
        // 7 is the amount of cards in the last tableau pile
        // if the number becomes too high, change the offset,
        // so cards will still fit the screen
        if (cards.size() > 7) {
            // change the offset after adding 2 cards
            offset += ((cards.size()-7) / 2);
        }
        return (Card.HEIGHT / offset);
    }
}