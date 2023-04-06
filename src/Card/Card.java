package Card;

import javax.swing.*;
import java.awt.*;

public class Card extends JPanel {
    private final Suit suit;
    private final int value;
    private boolean backSided = false;

    public final static int WIDTH = 100;
    public final static int HEIGHT = 150;
    private static final int BORDER_THICKNESS = 5;
    private static final Dimension SIDE_DIMENSION = new Dimension(
            WIDTH-BORDER_THICKNESS, HEIGHT-BORDER_THICKNESS);

    private final JPanel contentPanel = new JPanel();
    private final JPanel frontPanel = new JPanel();
    private final JPanel backPanel = new JPanel();

    public enum Suit {
        CLUBS,
        DIAMONDS,
        HEARTS,
        SPADES
    }

    public static abstract class Royalty {
        public static final int ACE = 1;
        public static final int JACK = 11;
        public static final int QUEEN = 12;
        public static final int KING = 13;
    }

    public Card(Suit suit, int value) {
        this.suit = suit;
        this.value = value;
        panelsConfigure();
    }

    public Suit getSuit() {
        return suit;
    }

    public int getValue() {
        return value;
    }

    public boolean isRed() {
        return (suit == Suit.DIAMONDS || suit == Suit.HEARTS);
    }

    public boolean isBackSided() {
        return backSided;
    }

    public void flip() {
        frontPanel.setVisible(backSided);
        backPanel.setVisible(!backSided);
        backSided = !backSided;
        repaint();
    }

    private void panelsConfigure() {
        frontConfigure();
        backConfigure();
        setOpaque(true);
        setSize(WIDTH, HEIGHT);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, BORDER_THICKNESS));
        // put both sides on the separate panel, so there will be no
        // need to create 2 borders for each side
        contentPanel.setLayout(null);
        add(contentPanel);
        contentPanel.add(frontPanel);
        contentPanel.add(backPanel);
    }

    private void frontConfigure() {
        final String textValue = valueToText();
        final String textSuit = suitToText();

        final JLabel labelValue = new JLabel(textValue);
        final JLabel labelSuitSmall = new JLabel(textSuit);
        final JLabel labelSuitBig = new JLabel(textSuit);

        final Font fontSmall = new Font(null,Font.PLAIN,40);
        labelValue.setFont(fontSmall);
        labelSuitSmall.setFont(fontSmall);
        labelSuitBig.setFont(new Font(null, Font.PLAIN, 70));

        final Color foregroundColor;
        if (isRed()) {
            foregroundColor = new Color(218, 0, 0);
        }
        else {
            foregroundColor = new Color(5, 5, 5);
        }

        labelValue.setForeground(foregroundColor);
        labelSuitSmall.setForeground(foregroundColor);
        labelSuitBig.setForeground(foregroundColor);

        final JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.add(labelValue, BorderLayout.WEST);
        headerPanel.add(labelSuitSmall, BorderLayout.EAST);

        final JPanel suitPanel = new JPanel();
        suitPanel.add(labelSuitBig);

        frontPanel.setOpaque(true);
        frontPanel.setBackground(Color.WHITE);
        frontPanel.setLayout(new BorderLayout());
        frontPanel.setSize(SIDE_DIMENSION);
        frontPanel.add(headerPanel, BorderLayout.NORTH);
        frontPanel.add(suitPanel);
    }

    private void backConfigure() {
        final JLabel cover = new JLabel("SK");
        cover.setOpaque(true);
        cover.setBackground(new Color(99, 154, 161));
        cover.setForeground(Color.WHITE);
        cover.setHorizontalAlignment(SwingConstants.CENTER);
        cover.setFont(new Font(null, Font.BOLD, 44));

        backPanel.setLayout(new BorderLayout());
        backPanel.setSize(SIDE_DIMENSION);
        backPanel.add(cover);
    }

    private String valueToText() {
        switch (value) {
            case Royalty.ACE -> { return "A"; }
            case 2,3,4,5,6,7,8,9,10 -> {
                return Integer.toString(value);
            }
            case Royalty.JACK -> { return "J"; }
            case Royalty.QUEEN -> { return "Q"; }
            case Royalty.KING -> { return "K"; }
        }
        return null;
    }

    private String suitToText() {
        switch (suit) {
            case CLUBS -> { return "♣"; }
            case DIAMONDS -> { return "♦"; }
            case HEARTS -> { return "♥"; }
            case SPADES -> { return "♠"; }
        }
        return null;
    }
}