package main;

import java.util.*;

public class War {
	/**
	 * This class is the model of the game of war
	 * The game is a simple one, each player takes half of the shuffled deck face down.
	 * Both players then play the top card of their deck at the same time. Suit is irrelevant.
	 * The winner of the round is the player with the highest card value, with Aces being the highest value.
	 * The round winner then takes both cards, with the winning card first and adds them to the bottom of his own deck.
	 * This repeats until one person has run out of cards.
	 * If there is a tie then a "War" is triggered. This means that both players place three cards from their deck
	 * face down and then a fourth card face up. This then works like a normal round, with the difference being the
	 * winner takes all the cards, not simply the last two, and adds them to the bottom of his deck.
	 */

	// fields
	Deck gameDeck;
	Queue<Card> player1Hand, player2Hand;
//	these Arraylists will be used to store list of cards if a "War" is triggered
	ArrayList<Card> tempP1Stack = new ArrayList<>(), tempP2Stack = new ArrayList<>();
	boolean rigged;
	private Players riggedPlayer;

	public enum Players {PLAYER1, PLAYER2}

	// constructor
	// this is the default constructor
	public War() {
		gameDeck = new Deck();
		player1Hand = new LinkedList<>();
		player2Hand = new LinkedList<>();
		rigged = false;

	}

	// this constructor is for testing such that a designated winner can be chosen
	public War(Players player) {
		gameDeck = new Deck();
		this.player1Hand = new LinkedList<>();
		this.player2Hand = new LinkedList<>();
		rigged = true;
		riggedPlayer = player;

	}

	public void start() {
		if (rigged) {
			startNewRiggedGame(riggedPlayer, gameDeck);
		}
		else {
			startNewGame(gameDeck);
		}
	}

	//	methods
	private void startNewGame(Deck curDeck) {
		curDeck.ShuffleDeck();
		deal(curDeck);
		play();
		// todo finish
	}

	private void play() {
		while (player1Hand.size() > 0 && player2Hand.size() > 0) {
			takeTurn();
		}
		Players winner = (player1Hand.size() == 0) ? Players.PLAYER2 : Players.PLAYER1;
		printWinner(winner);
	}

	private void takeTurn() {
		Card player1CurCard, player2CurCard;
		player1CurCard = player1Hand.remove();
		player2CurCard = player2Hand.remove();

		if (player1CurCard.cardNum > player2CurCard.cardNum) {
			player1Hand.add(player1CurCard);
			player1Hand.add(player2CurCard);
			String formattedTurn = formatTurn(player1CurCard.formattedCardName, player2CurCard.formattedCardName,
					"Player One Wins this round");
			printTurn(formattedTurn);
		}
		else if (player2CurCard.cardNum > player1CurCard.cardNum) {
			player2Hand.add(player2CurCard);
			player2Hand.add(player1CurCard);
			String formattedTurn = formatTurn(player1CurCard.formattedCardName, player2CurCard.formattedCardName,
					"Player Two Wins this round");
			printTurn(formattedTurn);
		}
		else {
			String formattedTurn = formatTurn(player1CurCard.formattedCardName ,player2CurCard.formattedCardName,
					"It's a tie! War!");
			printTurn(formattedTurn);

			if (player1Hand.size() > 1 && player2Hand.size() > 1){
				startWarCondition();
			}
			else {
				if (player1Hand.size() == 1) {
					tempP1Stack.add(player1Hand.remove());
				}
				if (player2Hand.size() == 1){
					tempP2Stack.add(player2Hand.remove());
				}
				startWarCondition();
			}
		}
	}

	public void printTurn(String turn) {
		System.out.println(turn);
	}

	public String formatTurn(String player1CurCard, String player2CurCard, String str) {
		String format = "|%1$-25s|%2$-25s|%3$-15s|\n";
		return String.format(format, player1CurCard, player2CurCard, str);
	}

	private void startWarCondition() {
		ArrayList<Card> player1Stack, player2Stack;
		if (tempP1Stack.size() == 0 && tempP2Stack.size() == 0) {
			player1Stack = new ArrayList<>();
			player2Stack = new ArrayList<>();
		}
		else {
			player1Stack = tempP1Stack;
			player2Stack = tempP2Stack;
			tempP1Stack.clear();
			tempP2Stack.clear();
		}

		Card player1CurCard, player2CurCard;

		if (player1Hand.size() >= 4) {
			for (int i = 0; i < 4; i++) {
				player1Stack.add(player1Hand.remove());
			}
		}
		else {
			for (int i = 0; i < player1Hand.size(); i++) {
				player1Stack.add(player1Hand.remove());
			}
		}

		if (player2Hand.size() >= 4) {
			for (int i = 0; i < 4; i++) {
				player2Stack.add(player2Hand.remove());
			}
		}
		else {
			for (int i = 0; i < player2Hand.size(); i++) {
				player2Stack.add(player2Hand.remove());
			}
		}


		player1CurCard = player1Stack.get(player1Stack.size() - 1);
		player2CurCard = player2Stack.get(player2Stack.size() - 1);

		printTurn("One");
		printTurn("Two");
		printTurn("Three");
		printTurn("War!");
		printTurn("");

		if (player1CurCard.cardNum > player2CurCard.cardNum) {
			player1Hand.addAll(player1Stack);
			player1Hand.addAll(player2Stack);

			String formattedTurn = formatTurn(player1CurCard.formattedCardName, player2CurCard.formattedCardName,
					"Player One Wins this round");
			printTurn(formattedTurn);
		}
		else if (player2CurCard.cardNum > player1CurCard.cardNum) {
			player2Hand.addAll(player2Stack);
			player2Hand.addAll(player1Stack);

			String formattedTurn = formatTurn(player1CurCard.formattedCardName, player2CurCard.formattedCardName,
					"Player Two Wins this round");
			printTurn(formattedTurn);
		}
		else {
			tempP1Stack = player1Stack;
			tempP2Stack = player2Stack;

			String formattedTurn = formatTurn(player1CurCard.formattedCardName, player2CurCard.formattedCardName,
					"It's a tie! War!");
			printTurn(formattedTurn);
			startWarCondition();
		}
	}

	private void printWinner(Players winner) {
		String formattedWinner = (winner == Players.PLAYER1) ? "Player 1" : "Player 2";
		System.out.println("Player " + formattedWinner + " wins!");

	}



	private void startNewRiggedGame(Players player, Deck curDeck) {
		riggedDeal(player, gameDeck);
		play();
		// todo finish
	}



	private void riggedDeal(Players player, Deck curDeck) {
		Queue<Card> preferred;
		Queue<Card> other;
		switch (player) {
			case PLAYER1 -> {
				preferred = this.player1Hand;
				other = this.player2Hand;
			}
			case PLAYER2 -> {
				preferred = this.player2Hand;
				other = this.player1Hand;
			}
			default -> throw new IllegalStateException("Unexpected value: " + player);
		}

		for (Card c : curDeck.deck) {
			if (c.cardNum > 6) {
				preferred.add(c);
			} else {
				other.add(c);
			}
		}
	}

	private void deal(Deck curDeck) {
		for (int i = 0; i < curDeck.deck.size(); i++) {
			if (i % 2 == 0) {
				player1Hand.add(curDeck.deck.remove(i));
			} else {
				player2Hand.add(curDeck.deck.remove(i));
			}
		}

	}
}

