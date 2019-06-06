package files;

public class Hand
{

    public static final int MAX_CARDS = 100;
    private Card[] myCards;
    private int numCards;

    /**
     * Default Constructor for Hand
     * sets myCards = Card[MAX_CARDS]
     * sets numCards = 0
     *
     */
    public Hand()
    {
       this.resetHand();
    }
    
    /**
     * remove all cards from the hand
     *
     */
    public void resetHand()
    {
       // if myCards is not empty, properly empty it?
       this.myCards = new Card[MAX_CARDS];
       this.numCards = 0;
    }

    /**
     * adds a card to the next available position in the myCards array
     * 
     * @param Card card = card to add to hand
     * @return boolean based on success of method
     */
    public boolean takeCard(Card card)
    {
        if (numCards >= MAX_CARDS)
        {
            return false;
        }
        else
        {
            myCards[numCards++] = new Card(card.getValue(), card.getSuit());
            return true;
        }
    }

    /**
     * returns and removes the card in the top occupied position of the array
     * 
     * @return Card cardToPlay
     */
    public Card playCard()
    {
      this.numCards--;
        return myCards[this.numCards];
    }
    
    /**
     * To work in the cardGameFramework class, this method will remove the 
     * card at a location and slide all of the cards down one spot in the
     *  myCards array.
     * @param cardIndex
     * @return
     */
    public Card playCard( int cardIndex )
    {
       if ( numCards == 0 ) //error
       {
          //Creates a card that does not work
          return new Card('M', Card.Suit.spades);
       }
       //Decreases numCards.
       Card card = myCards[cardIndex];
       
       numCards--;
       for( int i = cardIndex; i < numCards; i++ )
       {
          myCards[i] = myCards[i + 1];
       }
       
       myCards[numCards] = null;
       
       return card;
     }

    /**
     * stringizer that the client can use to display the entire hand
     * 
     * @return String entireHand
     */
    public String toString()
    {
       if (this.numCards == 0)
       {
          return "Your hand is empty";
       }
       String entireHand = "";
       for (int i = 0; i < (this.numCards - 1); i++) 
       { 
          entireHand += this.myCards[i] + ", ";
       }
       // last card does not need ", " after
       entireHand += this.myCards[this.numCards - 1]; 
       return entireHand;
    }

    /**
     * Accessor for numCards
     * 
     * @return int numCards
     */
    public int getNumCards()
    {
        return numCards;
    }

    /**
     * Accessor for an individual card
     * Returns a card with errorFlag = true if k is bad
     * 
     * @return String entireHand
     */
    public Card inspectCard(int k)
    {
       if (k >= numCards)
       {
          // make card with errorFlag = true
          Card badCard = new Card(true);
          return badCard;
       }
       return myCards[k];
    }
    
    // sort the hand by calling the arraySort() method in the Card class.
    public void sort()
    {
        if ( this.getNumCards() != 0 )
            Card.arraySort(myCards, numCards);
    }
    
}
