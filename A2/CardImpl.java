package a2;

public class CardImpl implements Card{
	private int rank;
	private Card.Suit suite;
	
	public CardImpl(int rank, Card.Suit suite) {
		this.rank = rank;
		this.suite = suite;
		
		if(rank <2 || rank >14) {
			throw new RuntimeException("Rank out of range");
		}
	}

	@Override
	public int getRank() {
		return rank;
	}

	@Override
	public Suit getSuit() {
		return suite;
	}
	
	@Override
	public boolean equals(Card other) {
		if((this.getRank() == other.getRank()) && (this.getSuit() == other.getSuit())) {
			return true;
		}else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		String s = "";
		switch (this.rank) 
		{
		case 2: s = "2 of " + Card.suitToString(this.suite);
		break;
		case 3: s = "3 of " + Card.suitToString(this.suite);
		break;
		case 4: s = "4 of " + Card.suitToString(this.suite);
		break;
		case 5: s = "5 of " + Card.suitToString(this.suite);
		break;
		case 6: s = "6 of " + Card.suitToString(this.suite);
		break;
		case 7: s = "7 of " + Card.suitToString(this.suite);
		break;
		case 8: s = "8 of " + Card.suitToString(this.suite);
		break;
		case 9: s = "9 of " + Card.suitToString(this.suite);
		break;
		case 10: s = "10 of " + Card.suitToString(this.suite);
		break;
		case 11: s = "Jack of " + Card.suitToString(this.suite);
		break;
		case 12: s = "Queen of " + Card.suitToString(this.suite);
		break;
		case 13: s = "King of " + Card.suitToString(this.suite);
		break;
		case 14: s = "Ace of " + Card.suitToString(this.suite);
		break;
		}
		return s;
	}
}
