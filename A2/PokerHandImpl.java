package a2;

public class PokerHandImpl implements PokerHand {
	private Card[] Hand;
	private int value;
	private int rank;
	
	public PokerHandImpl(Card[] cards) {
		if(cards == null || cards[0] == null || cards[1] == null || cards[2] == null
				|| cards[3] == null || cards[4] == null) {
			throw new RuntimeException("Null values passed");
		}else {
			// copy and store
			this.Hand = cards.clone();
		}
	}
	
	public void setValue(int value) {
		this.value = value;
		// setter for value
	}

	public void setRank(int rank) {
		this.rank = rank;
		// setter for rank
	}
	
	public Card[] sortByRank (Card[] cards){
		// bubble sorted
		for (int i=0; i<cards.length; i++) {
				  for (int j=i+1; j<cards.length; j++) {
				    if (cards[i].getRank() > cards[j].getRank()) {
				      Card tmp = cards [i];
				      cards[i] = cards[j];
				      cards[j] = tmp;
				    }
				  }
				}
		return cards;
	}
	
	
	@Override
	public Card[] getCards() {
		return Hand.clone();
	}

	@Override
	public boolean contains(Card c) {
		Card[] bubblesorted = sortByRank(this.Hand);
		boolean containOrNot = false;
		for(int i=0; i<5; i++) {
			if (bubblesorted[i].equals(c)) {
				containOrNot = true;
				break;
			}else {
				containOrNot = false;
			}
		}
		return containOrNot;
	}

	@Override
	public boolean isOnePair() {
		Card[] bubblesorted = sortByRank(this.Hand);
		boolean isOnePair = false;
		// store the result into a new array
		int ranks[] = new int[5];
		int ranks2[] = new int[5];
		for(int i=0; i<5; i++) {
			ranks[i] = bubblesorted[i].getRank();
			ranks2[i] = bubblesorted[i].getRank();
		}
		// remove duplicates
		int j = 0;
		int i = 1;
		while (i < ranks.length) {
			if (ranks[i] == ranks[j])
			{
				i++;
			}
			else
			{
				ranks[++j] = ranks[i++];
			}
		}
		int[] ranksWithoutDuplicate = new int[j + 1];
		for (int k = 0; k < ranksWithoutDuplicate.length; k++)
		{
			ranksWithoutDuplicate[k] = ranks[k];
		}
		// get value and rank
		if (ranksWithoutDuplicate.length != 4) {
			isOnePair = false;
		}else {
			isOnePair = true;
			this.setValue(2);
			// get rank of the pair
			for(int m = 0; m<ranksWithoutDuplicate.length;m++) {
				int count = 0;
				for(int n = 0; n<ranks2.length;n++) {
					if(ranksWithoutDuplicate[m] == ranks2[n]) {
						count++;
					}
				}
				if(count == 2) {
					this.setRank(ranksWithoutDuplicate[m]);
					break;
				}
			}
		}
		return isOnePair;
	}

	@Override
	public boolean isTwoPair() {
		Card[] bubblesorted = sortByRank(this.Hand);
		boolean isTwoPair = false;
		if(!isThreeOfAKind()) {
			// store the result into a new array
			int ranks[] = new int[5];
			int ranks2[] = new int[5];
			for(int i=0; i<5; i++) {
				ranks[i] = bubblesorted[i].getRank();
				ranks2[i] = bubblesorted[i].getRank();
			}
			// remove duplicates
			int j = 0;
			int i = 1;
			while (i < ranks.length) {
				if (ranks[i] == ranks[j])
				{
					i++;
				}
				else
				{
					ranks[++j] = ranks[i++];
				}
			}
			int[] ranksWithoutDuplicate = new int[j + 1];
			for (int k = 0; k < ranksWithoutDuplicate.length; k++)
			{
				ranksWithoutDuplicate[k] = ranks[k];
			}
			// get value and rank
			if (ranksWithoutDuplicate.length != 3) {
				isTwoPair = false;
			}else {
				
				// get rank of the higher pair
				int[] paired = new int[2];
				for(int x = 0 ; x<paired.length; x++) {
					for(int m = 0; m<ranksWithoutDuplicate.length;m++) {
						int count = 0;
						for(int n = 0; n<ranks2.length;n++) {
							if(ranksWithoutDuplicate[m] == ranks2[n]) {
								count += 1;
							}
						}
						if(count == 2) {
							isTwoPair = true;
							this.setValue(3);
							paired[x] = ranksWithoutDuplicate[m];
						}
					}
				}
				if(paired[0] > paired[1]) {
					this.setRank(paired[0]);
				}else {
					this.setRank(paired[1]);
				}
			}
		}

		return isTwoPair;
	}

	@Override
	public boolean isThreeOfAKind() {
		Card[] bubblesorted = sortByRank(this.Hand);
		boolean isThreeOfAKind = false;
		if(!isFourOfAKind()) {
			if((bubblesorted[0].getRank() == bubblesorted[1].getRank()) &&
					  (bubblesorted[0].getRank() == bubblesorted[2].getRank()) &&
					  (bubblesorted[3].getRank() != bubblesorted[4].getRank())) {
						isThreeOfAKind = true;
						this.setValue(4);
						this.setRank(bubblesorted[0].getRank());
					 }else if(
					  (bubblesorted[1].getRank() == bubblesorted[2].getRank()) &&
					  (bubblesorted[1].getRank() == bubblesorted[3].getRank()) &&
					  (bubblesorted[0].getRank() != bubblesorted[4].getRank()))
					  {
						isThreeOfAKind = true;
						this.setValue(4);
						this.setRank(bubblesorted[1].getRank());
					  }else if(
					   (bubblesorted[2].getRank() == bubblesorted[3].getRank()) &&
					   (bubblesorted[2].getRank() == bubblesorted[4].getRank()) &&
					   (bubblesorted[0].getRank() != bubblesorted[1].getRank()))
					  {
						isThreeOfAKind = true;
						this.setValue(4);
						this.setRank(bubblesorted[1].getRank());
					  }else {
						isThreeOfAKind = false;
					}
		}
		return isThreeOfAKind;
	}

	@Override
	public boolean isStraight() {
		Card[] bubblesorted = sortByRank(this.Hand);
		boolean isStraight = false;
			if(
				((bubblesorted[4].getRank() - bubblesorted[3].getRank()) == 1) &&
				((bubblesorted[3].getRank() - bubblesorted[2].getRank()) == 1) &&
				((bubblesorted[2].getRank() - bubblesorted[1].getRank()) == 1) &&
				((bubblesorted[1].getRank() - bubblesorted[0].getRank()) == 1) 
				){
				isStraight = true;
				this.setValue(5);
				this.setRank(bubblesorted[4].getRank());
			}else if(
					(bubblesorted[4].getRank()==14) && (bubblesorted[3].getRank()==5)&&
					(bubblesorted[2].getRank()==4) && (bubblesorted[1].getRank()==3)&&
					(bubblesorted[0].getRank()==2) 
					) {
				isStraight = true;
				this.setValue(5);
				this.setRank(5);
			}
		return isStraight;
	}

	@Override
	public boolean isFlush() {
		Card[] bubblesorted = sortByRank(this.Hand);
		boolean isFlush = false;
		if(
			(bubblesorted[0].getSuit() == bubblesorted[1].getSuit())&&
			(bubblesorted[0].getSuit() == bubblesorted[2].getSuit())&&
			(bubblesorted[0].getSuit() == bubblesorted[3].getSuit())&&
			(bubblesorted[0].getSuit() == bubblesorted[4].getSuit())
				) {
			isFlush = true;
			this.setValue(6);
			this.setRank(bubblesorted[4].getRank());
		}
		return isFlush;
	}

	
	@Override
	public boolean isFullHouse() {
		Card[] bubblesorted = sortByRank(this.Hand);
		boolean isFullHouse = false;
		if((bubblesorted[0].getRank() == bubblesorted [1].getRank()) &&
		   (bubblesorted[2].getRank() == bubblesorted [3].getRank()) &&
		   (bubblesorted[2].getRank() == bubblesorted [4].getRank())) {
			isFullHouse = true;
			this.setValue(7);
			this.setRank(bubblesorted[2].getRank());
		}else if((bubblesorted[0].getRank() == bubblesorted [1].getRank()) &&
				 (bubblesorted[0].getRank() == bubblesorted [2].getRank()) &&
				 (bubblesorted[3].getRank() == bubblesorted [4].getRank())){
			isFullHouse = true;
			this.setValue(7);
			this.setRank(bubblesorted[0].getRank());
		}else {
			isFullHouse = false;
		}
		return isFullHouse;
	}

	@Override
	public boolean isFourOfAKind() {
		Card[] bubblesorted = sortByRank(this.Hand);
		boolean isFourOfAKind = false;
		for(int i=0;i<2;i++) {
			int count = 1;
			int tmp = bubblesorted[i].getRank();
			for(int j=i+1; j<5; j++) {
				if(bubblesorted[j].getRank() == tmp) {
					count += 1;
				}
			}
			if(count == 4) {
				isFourOfAKind = true;
				this.setValue(8);
				this.setRank(tmp);
				break;
			}
		}
		return isFourOfAKind;
	}

	@Override
	public boolean isStraightFlush() {
		Card[] bubblesorted = sortByRank(this.Hand);
		boolean isStraightFlush = false;
		if(this.isFlush() && this.isStraight()) {
			isStraightFlush = true;
			this.setValue(9);
			if(bubblesorted[4].getRank() == 14) {
				this.setRank(5);
			}else {
				this.setRank(bubblesorted[4].getRank());
			}
		}
		return isStraightFlush;
	}

	@Override
	public int getHandTypeValue() {
		if( this.isStraightFlush()) {
			return this.value;
		}else if(this.isOnePair() || this.isTwoPair() || this.isThreeOfAKind() || this.isStraight()
				|| this.isFlush() || this.isFullHouse() || this.isFourOfAKind())
		{
			return this.value;
		}
		else {
			return 1;
		}
	}

	@Override
	public int getHandRank() {
		if(this.isStraightFlush()) {
			return this.rank;
		}else if(this.isOnePair() || this.isTwoPair() || this.isThreeOfAKind() || this.isStraight()
				|| this.isFlush() || this.isFullHouse() || this.isFourOfAKind())
		{
			return this.rank;
		}
		else{
			Card[] bubblesorted = sortByRank(this.Hand);
			return bubblesorted[4].getRank();
		}
	}

	@Override
	public int compareTo(PokerHand other) {
		if(this.getHandTypeValue() < other.getHandTypeValue()) {
			return -1;
		}else if(this.getHandTypeValue() > other.getHandTypeValue()) {
			return 1;
		}else {
			if(this.getHandRank() < other.getHandRank()) {
				return -1;
			}else if(this.getHandRank() > other.getHandRank()) {
				return 1;
			}else {
				return 0;
			}
		}
	}
}
