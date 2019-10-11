package com.tesevic.game;

import com.tesevic.interaction.Input;
import com.tesevic.text.TextHolder;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private List<Card> cards = new ArrayList<>();
    private Game game;

    public Player(Game game, String name) {
        this.name = name;
        this.game = game;
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

    public Card playCard(int i, List<Card> playCards) {
        Card cardPlayed = cards.get(i);
        cards.remove(i);
        playCards.add(cardPlayed);
        return cardPlayed;
    }
}
