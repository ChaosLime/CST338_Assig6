package files;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

/*
 * CardButton
   CardTable
   Timer
 */

public class View
{
   @SuppressWarnings("serial")
   public static class CardButton extends JButton implements ActionListener
   {
      public CardButton(Icon icon)
      {
         super(icon);
         addActionListener(this);
      }
      @Override
      public void actionPerformed(ActionEvent e)
      {
         if (Control.readyToPlayCard == true)
         {
            //create JLabel from the JButton clicked and add to playedCardLabels
            JButton source = (JButton)e.getSource();
            Control.playedCardLabels[1] = new JLabel(source.getIcon());
            //Choose computer card based off of computer hand
            Control.selectComputerCard();
            //add all selections to main play area
            Control.addCardsToTable();
            //display winner message and button to advance
            Control.roundEndDisplay();
            //add both cards to winnings if user won
            if (Control.didHumanWin() == 1)
            {
               Control.winnings[Control.numTimesWon] = Control.getCardFromPlayer(0);
               Control.winnings[Control.numTimesWon + 1] = Control.getCardFromPlayer(1);
               Control.numTimesWon += 2;
            }
            //remove chosen cards out of computer an player's hands
            Control.removePlayedCardsFromHands();
            Control.myCardTable.setVisible(true);
            //now that a card has just been played, change the flag so that
            //subsequent button presses do nothing
            Control.readyToPlayCard = false;
         }
      }
   }
   @SuppressWarnings("serial")
   public static class CardTable extends JFrame
   {
      /*
       * the following five members are needed is to establish the grid layout 
       * for the JPanels, the organization of which depends on how many cards and
       * players will be displayed.
       */
      public static final int MAX_CARDS_PER_HAND = 56;
      public static final int MAX_PLAYERS = 2;  //for now, we only allow 2 person games
         
      private int numCardsPerHand;
      private int numPlayers;
      
      public JPanel pnlComputerHand, pnlHumanHand, pnlPlayArea, pnlMsgArea,
                     pnlCenterArea;
      
      /*
       * The constructor filters input, adds any panels to the JFrame, and 
       * establishes layouts
       */
      public CardTable( String title, int numCardsPerHand, int numPlayers )
      {
         super(title);
         
         if( numCardsPerHand <= MAX_CARDS_PER_HAND )
            this.numCardsPerHand = numCardsPerHand;
         
         if( numPlayers <= MAX_PLAYERS )
            this.numPlayers = numPlayers;
         
         frameInit();
         
         this.setLayout( new GridLayout(3, 1) );
         
         /*
          * three Public JPanels, one for each hand (player-bottom and 
          * computer-top) and a middle "playing" JPanel. 
          */
         pnlComputerHand = new JPanel( new GridLayout(1, numCardsPerHand) );
         pnlHumanHand = new JPanel( new GridLayout(1, numCardsPerHand) );
         
         pnlCenterArea = new JPanel( new GridLayout(2, 1) );
         pnlPlayArea = new JPanel( new GridLayout(2, 2) );
         pnlMsgArea = new JPanel();
         
         setLayout( new BorderLayout(20, 10));
         
         this.add( pnlComputerHand, BorderLayout.NORTH );
         //this.add( pnlPlayArea, BorderLayout.CENTER );
         pnlCenterArea.add(pnlPlayArea);
         pnlCenterArea.add(pnlMsgArea);
         this.add( pnlCenterArea, BorderLayout.CENTER );
         this.add( pnlHumanHand, BorderLayout.SOUTH );
         
         pnlComputerHand.setBorder( new TitledBorder("Computer Hand") );
         pnlPlayArea.setBorder( new TitledBorder("Playing Area") );
         pnlHumanHand.setBorder( new TitledBorder("Your Hand") );
      } 

      // Accessors for the two instance members
      public int getNumCardsPerHand()
      {
         return this.numCardsPerHand;
      }
      
      public int getNumPlayers()
      {
         return this.numPlayers;
      }
    }
   //public class Timer goes here
}
