package Frame;

import Game.Game;
import Card.Card;
import Piles.OffsetPile;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    private final Game game;

    public GameFrame(Game game) {
        this.game = game;
        frameConfigure();
        add(new Table(game));
        setVisible(true);
    }

    private void frameConfigure() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("SwingKlondike");
        getContentPane().setPreferredSize(new Dimension(countFrameDimension()));
        pack();
        setResizable(false);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(6, 73, 19));
    }

    private Dimension countFrameDimension() {
        final int x = ((Card.WIDTH + Table.HORIZONTAL_PADDING) * game.getTableau().length)
                + Table.HORIZONTAL_PADDING;
        final int y = ((Card.HEIGHT+Table.VERTICAL_PADDING)*2) +
                ((Card.HEIGHT*game.getTableau().length) / OffsetPile.STARTING_OFFSET);
        return new Dimension(x,y);
    }
}