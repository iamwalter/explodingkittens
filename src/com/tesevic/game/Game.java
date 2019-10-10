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

    public void start() {
        for (Player p : players) {
            deck.printRecentlyPlayedCards();
            p.printInfo();
            System.out.println(TextHolder.CARD_INFO_PLAYING_A_CARD);

            Input.getInput();
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

        // first loop through cards, then though players
        for (int i = START_CARD_AMOUNT; i > 0; i--) {
            for (Player p : players) {
                Deque<Card> cardsDrawn = new ArrayDeque<>();

                Card cardToAdd;

                // exploding kitten detected
                while (deck.getDrawCards().peek().getType() == CardType.EXPLODING_KITTEN) {
                    cardsDrawn.add(deck.getDrawCards().pop());
//                    System.err.println("EXPLODING KITTEN!");
                }


                cardToAdd = deck.getDrawCards().pop();
                p.addCard(cardToAdd);

                for (Card c : cardsDrawn) {
                    deck.getDrawCards().addLast(c);
                }

                // shuffle the cards again, by making an arraylist of the cards
                // and shuffling, adding back to queue after. TODO: Optimize this!
                if (cardsDrawn.size() != 0) {
                    List<Card> newDrawDeck = new ArrayList<>();

                    newDrawDeck.addAll(deck.getDrawCards());

                    Collections.shuffle(newDrawDeck);

                    Deque<Card> newDrawDeckShuffled = new ArrayDeque<>();

                    newDrawDeckShuffled.addAll(newDrawDeck);

                    deck.setDrawCards(newDrawDeckShuffled);
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
