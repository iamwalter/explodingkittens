package com.tesevic.game;

import com.tesevic.interaction.Input;
import com.tesevic.text.TextHolder;

import javax.smartcardio.ATR;
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
            if (lastPlayed.getType() == CardType.ATTACK) {
                //do something
            }
        }

        boolean playerDone = false;

        while (!playerDone) {
            p.printInfo();
            System.out.println(TextHolder.CARD_INFO_PLAYING_A_CARD);

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
                        deck.getPlayCards().add(drawCard);
                    } else {
                        // let player put cat card somewhere in deck.
                        System.out.println(TextHolder.PLACE_EXPLODING_KITTEN_IN_DECK);

                        // TODO: There is a bug in the placing of a cat card..
                        int cardPosition = Input.getInput(1, deck.getDrawCards().size()) - 1;
                        cardPosition = deck.getDrawCards().size() - cardPosition;

                        deck.getDrawCards().add(cardPosition, drawCard);
                    }
                } else {
                    System.out.println("You drew a " + drawCard.getType() + " card.");

                    p.addCard(drawCard);
                }

                playerDone = true;
            } else {
                // player plays a card. card must not be of type defuse card
                Card playCard = p.playCard(input - 1, deck.getPlayCards());

                if (playCard != null)
                    playerDone = handleCardPlayed(playCard, p);
            }
        }
    }

    private boolean handleCardPlayed(Card playCard, Player p) {
        switch (playCard.getType()) {
            case ATTACK:
            case SKIP:
            case NOPE:
                return true;
            case SHUFFLE:
                Collections.shuffle(deck.getDrawCards());
                return false;
            case SEETHEFUTURE:
                System.out.println("Cards ahead: ");
                for (Card c : deck.peekDrawCards(3))
                    System.out.println("\t- " + c);

                return false;
            case FAVOR:
                handleCardStealing(p, false);
                break;
            case CATCARD:
                handleCardStealing(p, true);
                return false;
            case DEFUSE:
                System.out.println(TextHolder.PLAY_DEFUSE_CARD);
                return false;
        }

        return false;
    }

    // choice is whether player has a choice in the card he is stealing.
    private void handleCardStealing(Player player, boolean choice) {
        // Get player to steal from
        System.out.println("Steal a card from player? ");

        ArrayList<Player> availablePlayers = new ArrayList<>();

        int counter = 1;
        for (Player p : players) {
            // if player is not the current player, and other player is not dead.
            if (p != player && (!p.getDead())) {
                availablePlayers.add(p);

                System.out.println(counter++ + " - " + p.getName());
            }
        }

        int input = Input.getInput(1, availablePlayers.size());
        Player chosenPlayer = availablePlayers.get(input - 1);

        // If player has only one card.
        if (chosenPlayer.getCards().size() == 1) {
            System.out.println("Stealing the only card " + chosenPlayer.getName() + " has.");
        } else if (chosenPlayer.getCards().size() == 0) {
            System.out.println(chosenPlayer.getName() + " has no cards!");
        } else {
            // if player has a choice (catcard)
            if (choice) {
                System.out.println("Steal card 1 to " + chosenPlayer.getCards().size());

                input = Input.getInput(1, chosenPlayer.getCards().size());

                Card c = chosenPlayer.getCard(input - 1);
                chosenPlayer.getCards().remove(c);
                player.addCard(c);

                System.out.println("Stole a " + c.getType());
            } else {
                // if player has no choice (favor)
                // use random card for now?
                Random r = new Random();

                int cardIndex = r.nextInt(chosenPlayer.getCards().size());

                Card c = chosenPlayer.getCard(cardIndex);
                chosenPlayer.getCards().remove(c);
                player.addCard(c);

                System.out.println("Stole a " + c.getType());
            }
        }
    }

    // Check if there is a winner in the game.
    private boolean checkIsAWinner() {
        List<Player> deadPlayers = new ArrayList<>();

        for (Player p : players) {
            if (p.getDead()) {
                deadPlayers.add(p);
            }
        }

        return deadPlayers.size() == players.size() - 1;
    }

    public void start() {
        // Keep track of the last players played.
        Player lastPlayer = new Player("UNKNOWN");

        while (!checkIsAWinner()) {
            for (Player p : players) {
                if (checkIsAWinner()) {
                    // If there is a winner, don't ask input for the next turn.
                    break;
                }

                // Only ask for input if a player is not dead.
                if (!p.getDead()) {
                    deck.printRecentlyPlayedCards();
                    gameLogic(p);
                    lastPlayer = p;
                }
            }
        }

        System.out.println("game is over. " + lastPlayer.getName() + " won");
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

                // If exploding kitten is detected shuffle the cards again.
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
