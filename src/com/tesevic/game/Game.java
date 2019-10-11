package com.tesevic.game;

import com.tesevic.interaction.Input;
import com.tesevic.text.TextHolder;

import java.util.*;

import static com.tesevic.game.Card.*;

/*
    This class holds the main board, the 2 decks of played cards.
 */
public class Game {
    private Deck deck = new Deck();

    private List<Player> players = new ArrayList<>();

    public Game() {
        init();
    }

    private void gameLogic(Player p) {
        deck.printRecentlyPlayedCards();
        p.printInfo();
        System.out.println(TextHolder.CARD_INFO_PLAYING_A_CARD);

        Card lastPlayed = deck.peekPlayCard();

        if (lastPlayed != null) {

            // handle last played card logic, like attack.
            switch (lastPlayed.getType()) {
            }
        }

        int input = Input.getInput(0, p.getCards().size());

        if (input == 0) {
            // player draw card from deck.
            // TODO: Handle drawing exploding kitten
        } else {
            // player plays a card.
            p.playCard(input - 1, deck.getPlayCards());
        }

    }

    public void start() {
        int turn = 1;

        while (players.size() > 0) {
            System.out.println("Turn " + turn++);

            for (Player p : players) {
                gameLogic(p);
            }
        }
    }


    private void init() {
        // init players
        String[] names = {"Walter", "BOT1", "BOT2"};
        final int START_CARD_AMOUNT = 8;

        for (String name : names) {
            players.add(new Player(this, name));
        }

        // shuffle the cards to the players
        deck.initCards();

        // give player at least 1 defuse card
        for (Player p  : players) {
            for (Card c : deck.getDrawCards()) {
                if (c.getType() == CardType.DEFUSE) {
                    deck.getDrawCards().remove(c);
                    p.addCard(c);
                    break;
                }
            }
        }

        // first loop through cards, then though players
        for (int i = START_CARD_AMOUNT - 1; i > 0; i--) {
            for (Player p : players) {
                List<Card> cardsDrawn = new ArrayList<>();

                // exploding kitten detected
                while (deck.peekDrawCard().getType() == CardType.EXPLODING_KITTEN) {
                    cardsDrawn.add(deck.drawCard());
                }

                p.addCard(deck.drawCard());

                // shuffle the cards again, by making an arraylist of the cards
                // and shuffling, adding back to queue after. TODO: Optimize this!
                if (cardsDrawn.size() > 0) {
                    deck.getDrawCards().addAll(cardsDrawn);

                    Collections.shuffle(deck.getDrawCards());
                }
            }
        }

        // assert that none of the players have a exploding card

        for (Player p : players) {
            for (Card c : p.getCards()) {
                assert c.getType() != CardType.EXPLODING_KITTEN;
            }
        }
        int bombs = 0;

        // aseert that there are 4 bombs in the deck.
        for (Card c : deck.getDrawCards()) {
            if (c.getType() == CardType.EXPLODING_KITTEN)
                bombs++;
        }

        assert bombs == 4;

        int cardAmountPlayers = players.size() * START_CARD_AMOUNT;

        assert cardAmountPlayers + deck.getDrawCards().size() == 56;
    }
}
