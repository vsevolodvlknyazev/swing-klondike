package Frame;

import Card.Card;
import Game.Game;
import Piles.OffsetPile;
import Piles.Pile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Table extends JLayeredPane {
    public static final int HORIZONTAL_PADDING = 5;
    public static final int VERTICAL_PADDING = 30;
    private static final int TOP_PILE_Y = VERTICAL_PADDING / 2;
    private static final int TABLEAU_Y = Card.HEIGHT + VERTICAL_PADDING;
    // the foundation is placed on top of the 4th pile of the tableau
    private final int foundationStartingIndex = 3;

    private static final Integer LAYER_BACKGROUND = 0;
    private static final Integer LAYER_DEFAULT = 1;
    private static final Integer LAYER_DRAG = 2;

    private final Game game;
    private final DndManager dndManager = new DndManager(this);
    private OffsetPile dragPile = null;

    private static class PileBackground extends JLabel {
        private PileBackground(Point point) {
            setBounds(new Rectangle(Card.WIDTH, Card.HEIGHT));
            setOpaque(true);
            setBackground(new Color(66, 101, 35));
            setLocation(point);
        }
    }

    private final MouseAdapter mouseAdapter = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent mouseEvent) {
            dragStart((Pile)mouseEvent.getSource(), mouseEvent.getPoint());
        }

        @Override
        public void mouseReleased(MouseEvent mouseEvent) {
            dragStop((Pile)mouseEvent.getSource());
        }
    };

    public Table(Game game) {
        this.game = game;
        stockButtonPlace();
        stockWastePlace();
        foundationPlace();
        tableauPlace();
    }

    private void pilePlace(Component component, Point location) {
        add(new PileBackground(location), LAYER_BACKGROUND);
        component.setLocation(location);
        add(component, LAYER_DEFAULT);
    }

    private void stockButtonPlace() {
        final Point location = new Point(getPilePosX(0), TOP_PILE_Y);
        final JPanel stock = game.getStock().getStockPileButton();
        pilePlace(stock, location);
    }

    private void stockWastePlace() {
        final Point location = new Point(getPilePosX(1), TOP_PILE_Y);
        final Pile waste = game.getStock().getWastePile();
        pilePlace(waste, location);
        waste.addMouseListener(mouseAdapter);
    }

    private void foundationPlace() {
        // the foundation is aligned to the right
        final int pileOffsetX = getPilePosX(foundationStartingIndex)
                - HORIZONTAL_PADDING;

        for (int i = 0; i < game.getFoundation().length; i++) {
            final Point location = new Point(
                    pileOffsetX + getPilePosX(i), TOP_PILE_Y);
            final Pile[] foundation = game.getFoundation();
            pilePlace(foundation[i], location);
            foundation[i].addMouseListener(mouseAdapter);
        }
    }

    private void tableauPlace() {
        for (int i = 0; i < game.getTableau().length; i++) {
            final Point location = new Point(getPilePosX(i), TABLEAU_Y);
            final Pile[] tableau = game.getTableau();
            pilePlace(tableau[i], location);
            tableau[i].addMouseListener(mouseAdapter);
        }
    }

    private int getPilePosX(int index) {
        // index+1 to add padding between the first pile
        // and the border of the table
        return ((Card.WIDTH * index) + (HORIZONTAL_PADDING * (index + 1)));
    }

    private void dragStart(Pile parentPile, Point point) {
        dragPile = parentPile.move(point);
        if (dragPile != null) {
            dndManager.drag(dragPile, dragPile.getLocation());
            add(dragPile, LAYER_DRAG);
        }
    }

    private void dragStop(Pile parentPile) {
        if (dragPile != null) {
            dndManager.stop();
            final Pile mergePile = getPile(dragPile.getLocation());
            if (mergePile != null && !mergePile.merge(dragPile)) {
                // merged successfully, redraw the mergePile
                mergePile.place();
            } else {
                // failed to merge, move cards back to the parent
                parentPile.cardsAdd(dragPile);
            }

            parentPile.place();
            remove(dragPile);
            dragPile = null;
            repaint();
            if (game.winCheck()) {
                JOptionPane.showMessageDialog(getParent(),
                        "Congratulations! You won!");
                System.exit(0);
            }
        }
    }

    private Pile getPile(Point point) {
        int pileIndex = (int) ((point.getX() + HORIZONTAL_PADDING)
                / (Card.WIDTH + HORIZONTAL_PADDING));
        if (pileIndex > game.getTableau().length - 1) {
            //cursor is on the empty space or off the screen
            pileIndex = game.getTableau().length - 1;
        }

        if (point.getY() < TABLEAU_Y) {
            if (pileIndex >= foundationStartingIndex) {
                return game.getFoundation()[
                        pileIndex - foundationStartingIndex];
            }
            return null;
        } else {
            return game.getTableau()[pileIndex];
        }
    }
}