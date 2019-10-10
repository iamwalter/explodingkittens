package com.tesevic.game;

import com.tesevic.text.TextHolder;
import static com.tesevic.game.Card.CardType.*;

public class Card {
    enum CardType {
        EXPLODING_KITTEN,
        DEFUSE,
        NOPE,
        ATTACK,
        SKIP,
        FAVOR,
        SHUFFLE,
        SEETHEFUTURE,
        CATCARD,
        UNKNOWN
    }

    private CardType type;
    private String cardInfo;

    private Card(CardType type, String cardInfo) {
        this.type = type;
        this.cardInfo = cardInfo;
    }

    public CardType getType() {
        return type;
    }

    public String getCardInfo() {
        return cardInfo;
    }

    @Override
    public String toString() {
        return type + " - " + cardInfo;
    }

    public static Card generateCard(CardType type) {
        Card returnCard;

        switch (type) {
            case EXPLODING_KITTEN:
                returnCard = new Card(type, TextHolder.CARD_INFO_EXPLODING_KITTEN);
                break;
            case DEFUSE:
                returnCard = new Card(type, TextHolder.CARD_INFO_DEFUSE);
                break;
            case NOPE:
                returnCard = new Card(type, TextHolder.CARD_INFO_NOPE);
                break;
            case ATTACK:
                returnCard = new Card(type, TextHolder.CARD_INFO_ATTACK);
                break;
            case SKIP:
                returnCard = new Card(type, TextHolder.CARD_INFO_SKIP);
                break;
            case FAVOR:
                returnCard = new Card(type, TextHolder.CARD_INFO_FAVOR);
                break;
            case SHUFFLE:
                returnCard = new Card(type, TextHolder.CARD_INFO_SHUFFLE);
                break;
            case SEETHEFUTURE:
                returnCard = new Card(type, TextHolder.CARD_INFO_SEETHEFUTURE);
                break;
            case CATCARD:
                returnCard = new Card(type, TextHolder.CARD_INFO_CATCARD);
                break;
            default:
                returnCard = new Card(UNKNOWN, "Unknown");
        }

        return returnCard;
    }

}
