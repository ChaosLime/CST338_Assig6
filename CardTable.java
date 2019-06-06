package files;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class CardTable extends JFrame
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

