package com.tesevic.game;

import java.util.*;

public class Deck {
    private Deque<Card> drawCards = new ArrayDeque<>();
    private Deque<Card> playCards = new ArrayDeque<>();

    public Deque<Card> getDrawCards() {
        return drawCards;
    }

    public Deque<Card> getPlayCards() {
        return playCards;
    }

    public void setDrawCards(Deque<Card> drawCards) {
        this.drawCards = drawCards;
    }

    public void printDrawCards() {
        for (Card c : drawCards) {
            System.out.println(c);
        }
    }

    public void initCards() {
        ArrayList<Card> cards = new ArrayList<>();
        // 4 exploding kittens, attack, skip, favor, shuffle
        for (int i = 0; i < 4; i++) {
            cards.add(Card.generateCard(Card.CardType.EXPLODING_KITTEN));
            cards.add(Card.generateCard(Card.CardType.ATTACK));
            cards.add(Card.generateCard(Card.CardType.SKIP));
            cards.add(Card.generateCard(Card.CardType.FAVOR));
            cards.add(Card.generateCard(Card.CardType.SHUFFLE));
        }

        //5 nopes, 5 seethefuture, 20 cat cards
        for (int i = 0; i < 5; i++) {
            cards.add(Card.generateCard(Card.CardType.NOPE));
            cards.add(Card.generateCard(Card.CardType.SEETHEFUTURE));

            for (int y = 0; y < 4; y++) {
                cards.add(Card.generateCard(Card.CardType.CATCARD));
            }
        }

        // 6 defuse
        for (int i = 0; i < 6; i++) {
            cards.add(Card.generateCard(Card.CardType.DEFUSE));
        }

        Collections.shuffle(cards);

        for (Card card : cards) {
            drawCards.push(card);
        }
    }

    public void printRecentlyPlayedCards() {
        System.out.println("--- Recently Played Cards --- ");

        String recentlyPlayedCard = (this.getPlayCards().peek() == null) ? "Nothing Played Yet." : this.getPlayCards().peek().toString();

        System.out.println(recentlyPlayedCard);
    }
}
