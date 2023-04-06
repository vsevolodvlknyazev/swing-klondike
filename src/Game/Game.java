package Game;

import Piles.*;

public class Game {
    private final OffsetPile[] tableau = new OffsetPile[7];
    private final FoundationPile[] foundation = new FoundationPile[4];
    private final Stock stock;

    public Game() {
        Deck deck = new Deck();

        for (int i = 0; i < tableau.length; i++) {
            tableau[i] = new OffsetPile();
            for (int j = 0; j <= i; j++) {
                tableau[i].cardsAdd(deck.deal());
            }
            tableau[i].place();
            tableau[i].startingFlip();
        }

        for (int i = 0; i < foundation.length; i++) {
            foundation[i] = new FoundationPile();
        }

        stock = new Stock(deck.getCards());
    }

    public OffsetPile[] getTableau() {
        return tableau;
    }

    public StraightPile[] getFoundation() {
        return foundation;
    }

    public Stock getStock() {
        return stock;
    }

    public boolean winCheck() {
        for (var i : foundation) {
            if (!i.isComplete()) {
                return false;
            }
        }
        return true;
    }
}