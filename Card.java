package files;


public class Card
{
   private static final char DEFAULT_VALUE = 'A';
   private static final Suit DEFAULT_SUIT = Suit.spades;
      
   // Suit enum of possible suits for cards
   public enum Suit
    {
        clubs, diamonds, hearts, spades;
    }
    
    // private data members
    public char value;
    public Suit suit;
    private boolean errorFlag;
    
    public static char[] valuRanks = 
      {'A', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'X'};
    
    public static Suit[] suitRanks = 
      { Suit.clubs, Suit.diamonds, Suit.hearts, Suit.spades };

    /**
     * Default Constructor for Card
     * Sets value = DEFAULT_VALUE, Suit = DEFAULT_SUIT
     * errorFlag set based on default constants
     *
     */
    public Card()
    {
       this.set(DEFAULT_VALUE, DEFAULT_SUIT); // default
    }
    
    /**
     * Constructor for Card with only errorFlag argument
     * Mainly used if errorFlag needs to be set
     * Sets value = DEFAULT_VALUE, Suit = DEFAULT_SUIT
     *
     */
    public Card( boolean errorFlag )
    {
       this.set(DEFAULT_VALUE, DEFAULT_SUIT); // default
       this.errorFlag = errorFlag;
    }
    
    /**
     * Constructor for Card
     * Sets value, Suit, and errorFlag based on arguments
     *
     * @param char value = set card value
     * @param Suit suit = set card suit
     */
    public Card(char value, Suit suit)
    {
        this.set(Character.toUpperCase(value), suit);
    }

    /**
     * Override method toString for Card object
     *
     * @return String (value + " of " + suit) or INVALID if errorFlag == true
     */
    @Override
    public String toString()
    {
       if (errorFlag)
       {
          return "[ INVALID ]";
       }
       return this.value + " of " + this.suit;
    }

    /**
     * Mutator for Card object
     * sets object.errorFlag based on validity
     *
     * @param char value = set card value
     * @param Suit suit = set card suit
     * @return Boolean based on success
     */
    public boolean set(char value, Suit suit)
    {
        if (isValid(value, suit))
        {
            this.value = value;
            this.suit = suit;
            errorFlag = false;
            return true;
        }
        errorFlag = true;
        return false;
    }

    /**
     * Accessor for errorFlag
     *
     * @return boolean errorFlag
     */
    public boolean getErrorFlag()
    {
        return errorFlag;
    }

    /**
     * Accessor for suit
     *
     * @return Suit suit
     */
    public Suit getSuit()
    {
        return suit;
    }

    /**
     * Accessor for value
     *
     * @return char value
     */
    public char getValue()
    {
        return value;
    }

    /**
     * Method to test if two cards are equal
     *
     * @param Card card = card to test object against
     * @return Boolean true if equal, false otherwise
     */
    public boolean equals(Card card)
    {
        if (value == card.getValue() && suit == card.getSuit())
        {
            return true;
        }
        return false;
    }

    //Checks if the value is a digit (2 - 9) or (T(10), J, Q, K, A)
    /**
     * Constuctor for Card
     * Sets value, Suit, and errorFlag based on arguments
     *
     * @param char value = value to check
     * @param Suit suit = suit to check (does not check at this time)
     */
    private boolean isValid( char value, Suit suit )
    {
       String goodChars = "TJQKA";
       boolean valueGood = goodChars.contains(Character.toString(value));
       if ((Character.isDigit(value) && value > 1) || valueGood)
       {
          return true;
       }
       return false;
    }
    
    // sort the incoming array of cards using a bubble sort routine. 
   public static void arraySort( Card[] cards, int arraySize )
   {
      boolean changed = true;
      
      while( changed )
      {
         changed = false;
         
         for( int i = 0; i < arraySize; i++ )
         {
            if( suitAsInt(cards[i]) > suitAsInt(cards[i + 1]) )
            {
               swapCards( cards, i, i + 1 );
               changed = true;
            }
            else if( suitAsInt(cards[i]) == suitAsInt(cards[i + 1]) )
            {
               if( valueAsInt(cards[i]) > valueAsInt(cards[i + 1]) )
               {
                  swapCards( cards, i, i + 1 );
                  changed = true;
               }
            }
         }
      }  
   }
    
    public static int valueAsInt(Card card)
    {
      char testValue = card.getValue();
        switch ((char)testValue)
        {
            case 'A':
                return 0;
            case '2':
                return 1;
            case '3':
                return 2;
            case '4':
                return 3;
            case '5':
                return 4;
            case '6':
                return 5;
            case '7':
                return 6;
            case '8':
                return 7;
            case '9':
                return 8;
            case 'T':
                return 9;
            case 'J':
                return 10;
            case 'Q':
                return 11;
            case 'K':
                return 12;
            default:
                return 13;
        }
    }
    
    public static int suitAsInt( Card card )
   {  
       // handles nullPointerExepction when card becomes invalid
       if(card.getSuit() != null) 
       {
          switch( card.getSuit() )
          {
             case clubs:
                return 0;
             case diamonds:
                return 1;
             case hearts:
                return 2;
             default:
                return 3;
          }
       }
       else
          return 0;
        
   }
    
    public static void swapCards( Card[] array, int card1, int card2 )
   {
      Card temp;
      
      temp = array[card1];
      array[card1] = array[card2];
      array[card2] = temp;
   }
}
