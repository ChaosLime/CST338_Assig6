package files;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Control
{
   public static View.CardTable myCardTable;
   public static Model.CardGameFramework highCardGame;
   public static final int NUM_CARDS_PER_HAND = 7;
   public static final int NUM_PLAYERS = 2;
   public static JLabel[] computerLabels = new JLabel[NUM_CARDS_PER_HAND];
   public static JButton[] humanLabels = new JButton[NUM_CARDS_PER_HAND];
   public static JLabel[] playedCardLabels  = new JLabel[NUM_PLAYERS];
   public static JLabel[] playLabelText  = new JLabel[NUM_PLAYERS];
   public static Model.Card[] winnings = new Model.Card[1 * 52]; //change 1 for # of decks
   public static int numTimesWon = 0;
   public static boolean readyToPlayCard = true;
   
   // static for the card icons and their corresponding labels
   public static final char[] CARD_NUMBERS = new char[]
   {'A', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'X'};
   public static final char[] SUITS = new char[]
   {'C', 'D', 'H', 'S'};
   public static final int NUM_CARD_IMAGES = (CARD_NUMBERS.length *
         SUITS.length) + 1;
   // + 1 for back card
   public static Icon[] icon = new ImageIcon[NUM_CARD_IMAGES];
   
   public static void main(String[] args)
   {
      Model.GUICard.loadCardIcons();

      // Create CardGameFramework
      int numPacksPerDeck = 1;
      int numJokersPerPack = 0;
      int numUnusedCardsPerPack = 0;
      Model.Card[] unusedCardsPerPack = null;
      
      int k;

      /**
       * CardFramework object to leverage the dealing of cards to the GUI
       * display
       */
      
      highCardGame = new Model.CardGameFramework(numPacksPerDeck,
              numJokersPerPack, numUnusedCardsPerPack, unusedCardsPerPack,
              NUM_PLAYERS, NUM_CARDS_PER_HAND);
      
      highCardGame.deal();
      myCardTable = new View.CardTable("CardTable", NUM_CARDS_PER_HAND, NUM_PLAYERS);
      myCardTable.setSize(800, 800);
      myCardTable.setLocationRelativeTo(null);
      myCardTable.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
      myCardTable.setVisible(true);
      
      //The computer and player hands will be built up from highCardGame hands
      buildHands();
      
      //Create labels for computer and player
      for (k = 0; k < NUM_PLAYERS; k++)
      {
         if(k == 0)
            playLabelText[k] = new JLabel("Computer", JLabel.CENTER);
         
         if(k == 1)
            playLabelText[k] = new JLabel("You", JLabel.CENTER);    
       }
      
      //Call helper method to put the playLabelText onto the myCardTable
      resetPlayArea();
      
      // show everything to the user 
      myCardTable.repaint();
      myCardTable.setVisible(true);
   }
   
   private static void buildHands()
   {
      /**
       * Generate the JLabels (for computer) and JButtons (for player)
       * based off of the hands stored in highGameCard and add them to 
       * myCardTable to be visible.
       */
      myCardTable.pnlComputerHand.removeAll();
      myCardTable.pnlHumanHand.removeAll();
      Icon tempIcon;
      // Create labels and add them to myCardTable for computer-----------------
      for (int k = 0; k < highCardGame.getHand(0).getNumCards(); k++)
      {
         if (k < highCardGame.getHand(0).getNumCards())
         {
            computerLabels[k] = new JLabel(Model.GUICard.getBackCardIcon());
         }
         myCardTable.pnlComputerHand.add(computerLabels[k]);
      }
      
      // Create labels and add them to myCardTable for human-----------------
      for (int k = 0; k < highCardGame.getHand(1).getNumCards(); k++)
      {
         if (k < highCardGame.getHand(1).getNumCards())
         {
            tempIcon = Model.GUICard.getIcon(highCardGame.getHand(1)
                                                   .inspectCard(k));
            humanLabels[k] = new JButton(tempIcon);
            myCardTable.pnlHumanHand.add(new View.CardButton(humanLabels[k]
                                                               .getIcon()));
         }           
      }
      myCardTable.setVisible(true); 
   }
   
   public static void addCardsToTable()
   {
      /**
       * For both computer and player, add the chosen cards (stored in 
       * playedCardLabels) to the myCardTable middle JPanel
       */
      //if there are no panels currently on the frame, then add some
      if (myCardTable.pnlPlayArea.getComponents().length <= NUM_PLAYERS)
      {
         // adding cards to the play area panel
         for(int k = 0; k < NUM_PLAYERS; k++ )
         {
            myCardTable.pnlPlayArea.add(playedCardLabels[k]);
            myCardTable.repaint();
         }
      }
   }
   
   public static void selectComputerCard()
   {
      /**
       * Generate the choice of card (from the computer's hand) for the
       * computer based off of the value of the card in the hand. The computer
       * will always choose the highest value of card in the hand.
       */
      Model.Hand computerHand = highCardGame.getHand(0);
      Model.Card tempCard;
      int highestValueIndex = -1;
      int tempCardValue = -1; 
      for (int i = 0; i < computerHand.getNumCards(); i++)
      {
         tempCard = computerHand.inspectCard(i);
         
         if (Model.Card.valueAsInt(tempCard) > tempCardValue)
         {
            tempCardValue = Model.Card.valueAsInt(tempCard);
            highestValueIndex = i;
         }
      }
      playedCardLabels[0] = new JLabel(Model.GUICard.getIcon(computerHand.
                                             inspectCard(highestValueIndex)));
   }
   
   public static int didHumanWin()
   {
      /**
       * Determine if human or computer won the game. Return -1 if computer,
       * 0 if tie, and 1 if player.
       */
      Model.Card computerCard = getCardFromPlayer(0);
      Model.Card humanCard = getCardFromPlayer(1);
      
      int valueOfComputerCard = Model.Card.valueAsInt(computerCard);
      int valueOfHumanCard = Model.Card.valueAsInt(humanCard);
      
      if (valueOfHumanCard == valueOfComputerCard)
      {
         return 0;
      }
      else if (valueOfHumanCard > valueOfComputerCard)
      {
         return 1;
      }
      else
      {
         return -1;
      }
   }
   
   private static String getWinMessage()
   {
      /**
       * Based off of the results of didHumanWin() method, return an
       * Appropriate message to be displayed in a JLabel later.
       */
      int winStatus = didHumanWin();
      
      switch (winStatus)
      {
         case -1: return "Computer Wins!";
         case 0: return "Tie!";
         case 1: return "You Win!";
         default: return "NO WINNER - ERROR";
      }
   }
   
   public static Model.Card getCardFromPlayer(int playerIndex)
   {
      /**
       * When provided a player number (0 for computer, 1 or more for player)
       * this will return the card that the entity had most recently chosen
       * to play.
       */
      String cardString = playedCardLabels[playerIndex].getIcon().toString();
      cardString = cardString.substring(cardString.indexOf('/') + 1);
      Model.Card tempCard = getCardFromFilename(cardString);
      return tempCard;
   }
   
   private static Model.Card getCardFromFilename(String filename)
   {
      /**
       * When provided the filename of an icon for a card image, this function
       * will return a card instance that has the same suit and value.
       * Note: The filename be in a standard format and must not have a folder
       * listed before it. The first two characters must be like "A8", where
       * "A" is the suit, and "8" is the value of the card.
       */
      char suitChar = filename.charAt(1);
      char valueChar = filename.charAt(0);
      Model.Card tempCard = new Model.Card();
      switch (suitChar)
      {
         case 'C': tempCard.suit = Model.Card.Suit.clubs;
            break; 
         case 'D': tempCard.suit = Model.Card.Suit.diamonds;
            break;
         case 'H': tempCard.suit = Model.Card.Suit.hearts;
            break;
         case 'S': tempCard.suit = Model.Card.Suit.spades;
            break;
         default: tempCard.suit = Model.Card.Suit.spades;
            break;
      }
      tempCard.value = valueChar;
      return tempCard;
   }
   
   private static int getIndexOfCardInHand(int playerIndex, Model.Card card)
   {
      /**
       * When provided with both the player index and a card object, this will
       * return the index of the equivalent card (card with same suit and value)
       * that resides in the hand of that entity.
       */
      Model.Hand tempHand = highCardGame.getHand(playerIndex);
      Model.Card tempCard;
      for (int i = 0; i < tempHand.getNumCards(); i++)
      {
         tempCard = tempHand.inspectCard(i);
         if (card.equals(tempCard))
         {
            return i;
         }
      }
      return -1;
   }
   
   public static void removePlayedCardsFromHands()
   {
      /**
       * This method will gather the most recently played card from previously
       * defined methods and will play card using Hand.playCard() method.
       * Then will call buildHands() which will reconstruct the JLabels
       * and JButtons in the myCardTable JPanels.
       */
      //find the index of the card in the hand, then remove it via playCard()
      Model.Card tempCard = getCardFromPlayer(0);
      int cardInHandIndex = getIndexOfCardInHand(0, tempCard);
      highCardGame.getHand(0).playCard(cardInHandIndex);
      
      tempCard = getCardFromPlayer(1);
      cardInHandIndex = getIndexOfCardInHand(1, tempCard);
      highCardGame.getHand(1).playCard(cardInHandIndex);
      
      buildHands();
      myCardTable.repaint();
      myCardTable.setVisible(true);
   }

   private static void resetPlayArea()
   {
      /**
       * This is responsible for clearing and preparing the play area of 
       * myCardTable for the next round of cards. This will clear and then 
       * re-write JLabels back into the main play area. 
       */
      myCardTable.pnlPlayArea.removeAll();
      // adding labels to the PA panel under the cards
      myCardTable.pnlPlayArea.add(playLabelText[0]);
      myCardTable.pnlPlayArea.add(playLabelText[1]);
   }
   
   public static void roundEndDisplay()
   {
      /**
       * When the round has ended (signaled by the selection of cards by
       * all players), generate a win/lose/tie message and paste it along side
       * a JButton that allows the player to advance to the next round.
       * This method also comes with an anonymous action listener attached
       * to the JButton. When the JButton is pressed, the message JPanel
       * is cleared out, play area is reset and hands are rebuilt.
       */
      //determine winner via determineRoundWinner()
      JLabel roundEndLabel = new JLabel(getWinMessage());
      JButton nextRoundBtn = new JButton("Click for next round");
      nextRoundBtn.addActionListener(new ActionListener()
         {
            @Override
            public void actionPerformed(ActionEvent arg0)
            {
               myCardTable.pnlMsgArea.removeAll();
               myCardTable.repaint();
               //reset play area for next round
               resetPlayArea();
               buildHands();
               //now that the reset button has been pressed, card buttons can
               //be pressed again
               readyToPlayCard = true;
            }
         });
      myCardTable.pnlMsgArea.add(roundEndLabel);
      myCardTable.pnlMsgArea.add(nextRoundBtn);
   }
}
