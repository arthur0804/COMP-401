package a2;

import java.util.Scanner;
public class HandEvaluator {
	
	public static void processCard(Scanner sc) {
		String result = "";
		while(sc.hasNext() == true) {
			Card[] cards = new Card[5];
			int NumberOpponents = sc.nextInt(); 
			if(NumberOpponents != 0) {
				// get rank and suit
				int[] rank = new int[5];
				String[] suit = new String[5];
				for(int i=0; i<5; i++) {
					rank[i] = sc.nextInt();
					suit[i] = sc.next();
				}
		
				
				// construct five cards
				for(int i=0; i<cards.length; i++) {
					if (suit[i].equals("S")) {
						cards[i]  = new CardImpl(rank[i], Card.Suit.SPADES);
						continue;
					}else if (suit[i].equals("H")) {
						cards[i] = new CardImpl(rank[i], Card.Suit.HEARTS);
						continue;
					}else if (suit[i].equals("C")) {
						cards[i] = new CardImpl(rank[i], Card.Suit.CLUBS);
						continue;
					}else if (suit[i].equals("D")) {
						cards[i] = new CardImpl(rank[i], Card.Suit.DIAMONDS);
						continue;
					}	
				}
				
				PokerHand hand = new PokerHandImpl(cards);
				// calculate and print out
				int winning = calculatePercentage(hand, NumberOpponents);
				
				// store into string
				result = result + winning + ",";
				//System.out.println(winning);
				
			}if (NumberOpponents == 0) {
				// print the result
				result = result.substring(0, result.length()-1);
				String[] resultarray = result.split(",");
				for(int i=0; i<resultarray.length;i++) {
					System.out.println(Integer.parseInt(resultarray[i]));
				}
				
				// input of 0 indicates the end	
				System.exit(1);
			} 
		}
		

	}
	
	// method to calculate the winning percentage
	public static int calculatePercentage(PokerHand cards, int NumberOpponents) {
		int winning = 0;
		double percentage = 0.0;
		for(int j = 1; j<= 10000; j++) {
			// create deck
			Deck deck = new DeckImpl();
			
			//remove the current 5 cards
			for(Card card : cards.getCards()) {
				deck.findAndRemove(card);
			}
			
			// array to store opponent's 5 cards
			PokerHand[] opponent = new PokerHand[NumberOpponents];
			
			// start to deal
			if(deck.hasHand()) {
				for(int i=0; i<NumberOpponents; i++) {
					opponent[i] = deck.dealHand();
					// remove card dealt from the deck
					for(Card card : opponent[i].getCards()) {
						deck.findAndRemove(card);
					}
				}
			}else {
				throw new RuntimeException("Deck does not have enough cards to deal another hand");
			}
			// now every opponent got their 5 cards
			
			// start to compare
			boolean result = true;
			for(int m = 0; m < NumberOpponents; m++) {
				if(cards.compareTo(opponent[m]) == 1) {
					result = result && true;
				}else {
					result = result && false;
				}
			}
			
			if(result) {
				winning += 1;
			}
			
		}
		
		percentage = winning * 100 / 10000;
		int percentage_as_int = (int) (percentage + 0.5);
		return percentage_as_int;
		
	}
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 Scanner sc = new Scanner(System.in);
	     processCard(sc);
	}

}
