package Game;

import Card.Card;

import java.util.Collections;
import java.util.Stack;

public class Deck {
    private final Stack<Card> cards = new Stack<>();

    public Deck() {
        Card.Suit suit = null;
        do {
            if (suit == null) {
                suit = Card.Suit.CLUBS;
            }
            else {
                switch (suit) {
                    case CLUBS -> suit = Card.Suit.DIAMONDS;
                    case DIAMONDS -> suit = Card.Suit.HEARTS;
                    case HEARTS -> suit = Card.Suit.SPADES;
                }
            }

            for (int value = Card.Royalty.ACE;
                 value <= Card.Royalty.KING; value++) {
                cards.push(new Card(suit,value));
            }
        }
        while (suit != Card.Suit.SPADES);

        Collections.shuffle(cards);
    }

    public Card deal() {
        return cards.pop();
    }

    public Stack<Card> getCards() {
        return cards;
    }
}