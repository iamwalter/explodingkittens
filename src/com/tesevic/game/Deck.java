package com.tesevic.game;

import java.util.*;

public class Deck {
    private List<Card> drawCards = new ArrayList<>();
    private List<Card> playCards = new ArrayList<>();

    public Card peekDrawCard() {
        return drawCards.get(drawCards.size() - 1);
    }

    public List<Card> peekDrawCards(int amount) {
        List<Card> peekList = new ArrayList<>();

        for (int i = drawCards.size() - 1; i >= drawCards.size() - amount && i >= 0; i--) {
            peekList.add(drawCards.get(i));
        }

        return peekList;
    }

    public Card peekPlayCard() {
        if (playCards.size() == 0)
            return null;

        return playCards.get(playCards.size() - 1);
    }

    public List<Card> peekPlayCards(int amount) {
        List<Card> peekList = new ArrayList<>();

        for (int i = playCards.size() - 1; i >= playCards.size() - amount && i >= 0; i--) {
            peekList.add(playCards.get(i));
        }

        return peekList;
    }

    public List<Card> getDrawCards() {
        return drawCards;
    }

    public List<Card> getPlayCards() {
        return playCards;
    }
    public void printDrawCards() {
        for (Card c : drawCards) {
            System.out.println(c);
        }
    }

    public void initCards() {
        // 4 exploding kittens, attack, skip, favor, shuffle
        for (int i = 0; i < 4; i++) {
            drawCards.add(Card.generateCard(Card.CardType.EXPLODING_KITTEN));
            drawCards.add(Card.generateCard(Card.CardType.ATTACK));
            drawCards.add(Card.generateCard(Card.CardType.SKIP));
            drawCards.add(Card.generateCard(Card.CardType.FAVOR));
            drawCards.add(Card.generateCard(Card.CardType.SHUFFLE));
        }

        //5 nopes, 5 seethefuture, 20 cat cards
        for (int i = 0; i < 5; i++) {
            drawCards.add(Card.generateCard(Card.CardType.NOPE));
            drawCards.add(Card.generateCard(Card.CardType.SEETHEFUTURE));

            for (int y = 0; y < 4; y++) {
                drawCards.add(Card.generateCard(Card.CardType.CATCARD));
            }
        }

        // 6 defuse
        for (int i = 0; i < 6; i++) {
            drawCards.add(Card.generateCard(Card.CardType.DEFUSE));
        }

        Collections.shuffle(drawCards);
    }

    // TODO: Specify the amount of cards peeked.
    public void printRecentlyPlayedCards() {
        System.out.println("--- Recently Played Cards --- ");

        List<Card> recentPlayedList;

        recentPlayedList = peekPlayCards(3);

        if (recentPlayedList.size() == 0) {
            System.out.println("No card recently played.");
        } else {
           for (Card c : recentPlayedList) {
                System.out.println(c);
            }
        }
    }

    public Card drawCard() {
        return drawCards.remove(drawCards.size() - 1);
    }
}
