package files;

import java.util.Random;


public class Deck
{

   /** Define a public final int value like MAX_CARDS, and initialize it to 
   * allow a maximum of six packs (6×56 cards).
   */
   public final int MAX_CARDS = 312; // max 6 decks of 56 cards
      
   private static Card[] masterPack;
   private Card[] cards;
   private int topCard;
   private int numPacks;
   public static boolean haveAllocatedMasterPack = false;

   /** constructors that populate the arrays and assigns initial values to
    *  members. Overload so that if no parameters are passed, 1 pack is assumed.
    */ 
    public Deck()
    {
      this.numPacks = 1;
        init(numPacks);
        //allocateMasterPack();
    }

    // A constructor that populates the arrays and assigns initial values to
    // members with the assistance of init(). This constructor is an overload
    // of the default constructor allowing for an parameter being set with the 
    // number of packs the deck will contain.
    // Takes one parameter, an int numPacks, that is used to to create a deck
    // of cards that is a combination of more than one pack
    public Deck(int numPacks)
    {
      this.numPacks = numPacks;
        init(numPacks);
        //allocateMasterPack();
    }

    // This public method initializes a deck of card according to the parameter
    // numPacks which is passed to it. This method calls on private method
    // allocateMasterPack() which sets the static array with 52 cards used to 
    // create each pack that is added to the Deck.
    // Here the private members numPacks and topCard are allso set accordingly.
    // The parameter int numPacks tells the method how many packs of cards are
    // to be added to the Deck from the masterPack.
    public void init(int numPacks)
    {
        allocateMasterPack();
        this.numPacks = numPacks;
        this.topCard = (52 * this.numPacks) - 1;

        cards = new Card[52 * numPacks];

        for (int pack = 0; pack < numPacks; pack++)
        {
            for (int card = 0; card < masterPack.length; card++)
            {
                cards[(52 * pack) + card] = masterPack[card];
            }
        }
    }

    // This public method is used to randomly shuffle the cards within a deck
    // so that all card are out of sequence.
    public void shuffle()
    {
        int split = cards.length / 2;
        Random rand = new Random();
        int shufCount = 5 * (52 * numPacks);

        do
        {
            int cardA = rand.nextInt(split);
            int cardB = rand.nextInt(split) + split;

            Card temp = cards[cardA];
            cards[cardA] = cards[cardB];
            cards[cardB] = temp;

            shufCount--;
        } while (shufCount != 0);

    }

    // A public method that returns the card on the top of the deck replacing
    // its position in the array with a null, and moving the topCard private
    // member to the next card in the deck.
    public Card dealCard()
    {
        Card retVal;
        if (topCard < 0)
        {
            retVal = new Card('B', Card.Suit.clubs);
        }
        else
        {
            retVal = cards[topCard];
            cards[topCard] = null;
            topCard--;
        }
        return retVal;
    }

    // This is an accessor method used to retreive the position of the current
    // top card in the deck array.
    public int getTopCard()
    {
        return topCard;
    }
    
    // Accessor for an individual card. 
    //Returns a card with errorFlag = true if k is bad
    public Card inspectCard(int k)
    {
        if (cards[k].getErrorFlag())
            return cards[k];
        else
            return new Card();
        
    }
    
    // This private method is used to fill the masterPack array with 52 unique
    // cards, which can than be used to populate a deck as needed. This method
    // will only run when the first deck in the program is initialized. If the
    // masterPack array is not null the method simply end makeing no changes to
    // the masterPack array.
    /** this is a private method that will be called by the constructor; will not 
     *  allow itself to be executed more than once
     */
    
    private static void allocateMasterPack()
    {
        if (masterPack == null)
        {
            masterPack = new Card[52];

            for (Card.Suit sVal : Card.Suit.values())
            {
                for (int k = 1; k < 14; k++)
                {
                    Card newCard;
                    int value = k % 14;
                    
                    switch (value)
                    {
                        case 1:
                            newCard = new Card('A', sVal);
                            break;
                        case 10:
                            newCard = new Card('T', sVal);
                            break;
                        case 11:
                            newCard = new Card('J', sVal);
                            break;
                        case 12:
                            newCard = new Card('Q', sVal);
                            break;
                        case 13:
                            newCard = new Card('K', sVal);
                            break;
                        default:
                            newCard = new Card(Integer.toString(value).charAt(0), sVal);
                    }
                    
                    if (sVal == Card.Suit.clubs)
                    {
                        masterPack[k - 1] = newCard;
                    }
                    else if (sVal == Card.Suit.diamonds)
                    {
                        masterPack[(k - 1) + 13] = newCard;
                    }
                    else if (sVal == Card.Suit.hearts)
                    {
                        masterPack[(k - 1) + 26] = newCard;
                    }
                    else // default to spades
                    {
                        masterPack[(k - 1) + 39] = newCard;
                    }
                }
            }
        }
    }
    
    /**
     * Add cards to deck; ensure no extra instances of card added and
     * return false if too many
     * @param card
     * @return
     */
    public boolean addCard( Card card )
    {
      int numOccurances = 0;
      
      for( int i = 0; i <= topCard; i++ )
      {
         if( cards[i].equals(card) )
            numOccurances++;
      }
      
      if( numOccurances > numPacks )
         return false;
      
      cards[topCard++] = card;
      return true;
    }
    
    /**
     * Remove cards from deck; replace current topCard in its place
     * and return false if target card is not in deck
     */
    public boolean removeCard( Card card )
    {
     int j;
     boolean found = false;
     
     for( j = 0; j < topCard; j++ )
     {
        while( cards[j].equals(card) )
        {
           if( j >= topCard )
              break;
           
           cards[j] = cards[topCard - 1];
           topCard--;
           found = true;
        }
     }
     
     return found;
    }
    
    /**
     * return the number of cards remaining in the deck.
     * @return count
     */
    public int getNumCards()
    {
      int count = 0;
      
      for( Card card : cards )
      {
         if( card != null )
            count++;
      }
      return count;
    }
    
    /**
     * put all of the cards in the deck back into the right order 
     * according to their values; call Card class arraySort to do sort
     */
    public void sort()
    {
      if( this.getNumCards() != 0 )
      {
         Card.arraySort( cards, numPacks * 52 );
      }
    }
    
}  
