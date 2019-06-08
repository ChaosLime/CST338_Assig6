package files;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;


@SuppressWarnings("serial")
public class CardButton extends JButton implements ActionListener
{
   public CardButton(Icon icon)
   {
      super(icon);
      addActionListener(this);
   }
   @Override
   public void actionPerformed(ActionEvent e)
   {
      if (Phase1_main.readyToPlayCard == true)
      {
         //create JLabel from the JButton clicked and add to playedCardLabels
         JButton source = (JButton)e.getSource();
         Phase1_main.playedCardLabels[1] = new JLabel(source.getIcon());
         //Choose computer card based off of computer hand
         Phase1_main.selectComputerCard();
         //add all selections to main play area
         Phase1_main.addCardsToTable();
         //display winner message and button to advance
         Phase1_main.roundEndDisplay();
         //add both cards to winnings if user won
         if (Phase1_main.didHumanWin() == 1)
         {
            Phase1_main.winnings[Phase1_main.numTimesWon] = Phase1_main.getCardFromPlayer(0);
            Phase1_main.winnings[Phase1_main.numTimesWon + 1] = Phase1_main.getCardFromPlayer(1);
            Phase1_main.numTimesWon += 2;
         }
         //remove chosen cards out of computer an player's hands
         Phase1_main.removePlayedCardsFromHands();
         Phase1_main.myCardTable.setVisible(true);
         //now that a card has just been played, change the flag so that
         //subsequent button presses do nothing
         Phase1_main.readyToPlayCard = false;
      }
   }
}
