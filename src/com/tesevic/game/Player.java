package com.tesevic.game;

import com.tesevic.interaction.Input;
import com.tesevic.text.TextHolder;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private List<Card> cards = new ArrayList<>();
    private boolean dead = false;

    public Player(String name) {
        this.name = name;
    }

    public void addCard(Card card) {
       cards.add(card);
    }

    /**Remove a card from the players cardlist.
     *
     * @param cardType - type of card to be removed.
     * @return card that has been removed or null if no card has been removed.
     */
    public Card removeCard(Card.CardType cardType) {
        for (Card c : cards) {
            if (c.getType() == cardType) {
                cards.remove(c);
                return c;
            }
        }

        return null;
    }


    public List<Card> getCards() {
        return cards;
    }

    public String getName() {
        return name;
    }

    public void printInfo() {
        System.out.println("---- " + this.name + "'s Hand ----");

        printCards();
    }

    private void printCards() {
        int i = 1;

        for (Card card : cards) {
            System.out.println(i++ + ": " + card);
        }
    }

    public Card getCard(int i) {
        return cards.get(i);
    }

    /**
     *
     * @param i index of the card
     * @param playCards deck of playcards
     * @return the card that has been played, or null if card cannot be played
     */
    public Card playCard(int i, List<Card> playCards) {
        Card cardPlayed = cards.get(i);
        cards.remove(i);

        if (cardPlayed.getType() == Card.CardType.CATCARD) {
            // check if player has 2 cat cards
             for (Card c : cards) {
                 if (c.getType() == Card.CardType.CATCARD) {
                    cards.remove(c);
                    return cardPlayed;
                 }
             }

            // if end of the for loop reached without finding another cat card
            // return original card to deck and return null
            System.out.println(TextHolder.NOT_ENOUGH_CAT_CARDS);
            cards.add(i, cardPlayed);
            return null;
        }

        // Cannot play card of type defuse.
        if (cardPlayed.getType() == Card.CardType.DEFUSE) {
            System.out.println(TextHolder.PLAY_DEFUSE_CARD);
            cards.add(i, cardPlayed);
            return null;
        }

        playCards.add(cardPlayed);
        return cardPlayed;
    }

    public boolean getDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }
}
