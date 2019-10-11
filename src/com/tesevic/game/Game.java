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
        Card lastPlayed = deck.peekPlayCard();

        if (lastPlayed != null) {
            // handle last played card logic, like attack.
            switch (lastPlayed.getType()) {
                case ATTACK:
                    // force the player to play an attack card
                    break;
            }
        }

        int input = Input.getInput(0, p.getCards().size());

        // Card Drawing Functionality.
        if (input == 0) {
            Card drawCard = deck.drawCard();

            // If draw card is exploding kitten
            if (drawCard.getType() == CardType.EXPLODING_KITTEN) {
                System.out.println(TextHolder.DRAW_EXPLODING_KITTEN);

                if (p.removeCard(CardType.DEFUSE) == null) {
                    System.out.println(TextHolder.NO_DEFUSE_CARDS);

                    p.setDead(true);
                } else {
                    // let player put cat card somewhere in deck.
                    System.out.println(TextHolder.PLACE_EXPLODING_KITTEN_IN_DECK);

                    int cardPosition = Input.getInput(1, deck.getDrawCards().size());
                    cardPosition = deck.getDrawCards().size() - cardPosition - 1;

                    deck.getDrawCards().add(cardPosition, drawCard);
                }
            } else {
                p.addCard(drawCard);
            }
        } else {
            // player plays a card.
            p.playCard(input - 1, deck.getPlayCards());
        }
    }

    public void start() {
        // Keep track of the dead players.
        int deadPlayers = 0;

        while (true) {
            if (deadPlayers == players.size() - 1) {
                break;
            }

            deadPlayers = 0;

            for (Player p : players) {
                if (p.getDead()) {
                    deadPlayers++;
                    continue;
                }

                deck.printRecentlyPlayedCards();
                p.printInfo();
                System.out.println(TextHolder.CARD_INFO_PLAYING_A_CARD);

                gameLogic(p);
            }
        }

        System.out.println("game is over. thanks for playing.");
    }


    private void init() {
        // init players
        String[] names = {"Walter", "BOT1", "BOT2"};
        final int START_CARD_AMOUNT = 8;

        for (String name : names) {
            players.add(new Player(name));
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

        // assert that there are 4 bombs in the deck.
        for (Card c : deck.getDrawCards()) {
            if (c.getType() == CardType.EXPLODING_KITTEN)
                bombs++;
        }

        assert bombs == 4;

        int cardAmountPlayers = players.size() * START_CARD_AMOUNT;

        assert cardAmountPlayers + deck.getDrawCards().size() == 56;
    }
}
