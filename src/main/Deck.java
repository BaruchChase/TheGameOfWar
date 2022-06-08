package main;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
	/**
	 * This class is a classic 52 card deck with no Jokers
	 */

	//	fields
	ArrayList<Card> deck;

	// constructor
	public Deck() {
		this.deck = createNewDeck();
	}

	// methods
	// In order to keep the constructor clean, this method creates a new deck
	private ArrayList<Card> createNewDeck() {
		ArrayList<Card> newDeck = new ArrayList<>();
		for(Suit suit: Suit.values()) {
			for (int cardNum = 2; cardNum <= 13; cardNum++) {
				Card c = new Card(cardNum, suit);
				newDeck.add(c);
			}
		}
		return newDeck;
	}

	// This method shuffles the deck (it uses the Collections method 'shuffle' to do this)
	public void ShuffleDeck() {
		Collections.shuffle(this.deck);
		Collections.shuffle(this.deck);
	}
}

class Card {
	/***
	 * This class describes the cards that will be used in the deck class
	 */

	// fields
	int cardNum;
	Suit cardSuit;
	String formattedCardName;

	// constructor
	public Card(int cardNum, Suit cardSuit) {
		setCardNum(cardNum);
		setCardSuit(cardSuit);
		formatCardName(cardNum, cardSuit);
	}


	// getters
	public int getCardNum() {
		return this.cardNum;
	}

	public Suit getCardSuit() {
		return this.cardSuit;
	}

	public String getFormattedCardName() {
		return formattedCardName;
	}

	// setters
	private void setCardNum(int cardNum) {
		this.cardNum = cardNum;
	}

	private void setCardSuit(Suit cardSuit) {
		this.cardSuit = cardSuit;
	}

	private void setFormattedCardName(String formattedCardName) {
		this.formattedCardName = formattedCardName;
	}

	// methods

	/**
	 *
	 * @param cardNum
	 * @param cardSuit
	 *
	 * This method takes in the card number and the suit and formats it nicely
	 */
	private void formatCardName(int cardNum, Suit cardSuit) {
		String name = switch (cardNum) {
			case (2) -> "Two of ";
			case (3) -> "Three of ";
			case (4) -> "Four of ";
			case (5) -> "Five of ";
			case (6) -> "Six of ";
			case (7) -> "Seven of ";
			case (8) -> "Eight of ";
			case (9) -> "Nine of ";
			case (10) -> "Ten of ";
			case (11) -> "Jack of ";
			case (12) -> "Queen of ";
			case (13) -> "King of ";
			case (14) -> "Ace of";
			default -> throw new IllegalArgumentException("This argument is invalid: " + cardNum);
		};

		name += cardSuit;
		setFormattedCardName(name);
	}

}

enum Suit {
	/***
	 * This enum class allows for only one of four types, as listed below
	 */
	DIAMONDS, HEARTS, CLUBS, SPADES
}