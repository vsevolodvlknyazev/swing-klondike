package Piles;

import Card.Card;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Stock {
    private final LinkedList<Card> stockPile = new LinkedList<>();
    private final StraightPile wastePile = new StraightPile() {
        @Override
        public boolean merge(Pile pile) {
            // merge with the wastePile is not allowed
            return true;
        }
    };

    private final JPanel stockPileButton = new JPanel();
    // value does not matter, it's just for the cover image
    private final Card cover = new Card(Card.Suit.CLUBS,1);

    public Pile getWastePile() {
        return wastePile;
    }

    public JPanel getStockPileButton() {
        return stockPileButton;
    }

    public Stock(List<Card> cards) {
        stockPile.addAll(cards);
        stockPileButtonConfigure();
    }

    public void deal() {
        if (!stockPile.isEmpty()) {
            wastePile.cardsAdd(stockPile.pop());
        }
        else {
            stockPile.addAll(wastePile.cards);
            wastePile.cards.clear();
            Collections.reverse(stockPile);
        }

        wastePile.place();
        cover.setVisible(!stockPile.isEmpty());
    }

    private void stockPileButtonConfigure() {
        stockPileButton.setLayout(null);
        stockPileButton.setOpaque(false);
        stockPileButton.setSize(Card.WIDTH,Card.HEIGHT);
        cover.flip();
        stockPileButton.add(cover);

        stockPileButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                deal();
            }
        });
    }
}