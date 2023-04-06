package Piles;

import Card.Card;

public class FoundationPile extends StraightPile {
    public boolean isComplete() {
        if (cards.isEmpty()) {
            return false;
        }
        else {
            return (getLastCard().getValue() == Card.Royalty.KING);
        }
    }

    @Override
    public boolean merge(Pile pile) {
        if (pile.cards.size() != 1) {
            return false;
        }

        if (!cards.isEmpty()) {
            if (!isPlaceable(getLastCard(),pile.getFirstCard())) {
                return true;
            }
        }
        else {
            if (pile.getFirstCard().getValue() != Card.Royalty.ACE) {
                return true;
            }
        }

        cardsAdd(pile);
        return false;
    }

    private boolean isPlaceable(Card parent, Card child) {
        if (parent.getSuit() == child.getSuit()) {
            return ((parent.getValue() + 1) == child.getValue());
        }
        else {
            return false;
        }
    }
}