import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

@SuppressWarnings("serial")
public class Timer extends JLabel implements ActionListener
{
   //JLabel timer;
   String timerText;
   long startTime;
   JButton timerButton = new JButton();
   
   public static void main(String[] args)
   {
      JFrame gui = new JFrame();
      JPanel mainPanel = new JPanel();
      
      //create a timer that is supposed to be started by a button
      Timer buttonTimer = new Timer();
      JButton timerStartButton = buttonTimer.getButtonToStartTimer();
      timerStartButton.setText("Press Button To Start Timer");
      
      //create a timer that is supposed to start without button press
      Timer autoTimer = new Timer();
      
      //set JFrame size, layout, and add JPanel
      gui.setSize(300, 200);
      gui.setLayout(new BorderLayout());
      gui.add(mainPanel);
      
      //set layout of JPanel and add button timer, one that is started by a 
      //button, and the other that is started automatically
      mainPanel.setLayout(new GridLayout(2, 2));
      mainPanel.add(buttonTimer, 0, 0);
      mainPanel.add(timerStartButton, 1, 0);
      
      mainPanel.add(autoTimer, 0, 1);
      
      //start the time on the timer that needs to start with the program
      Packer autoPackerThread = autoTimer.new Packer();
      autoPackerThread.start();
      
      gui.setVisible(true);
   }
   
   public Timer()
   {
      timerButton.addActionListener(this);
   }
   
   public JButton getButtonToStartTimer()
   {
      return timerButton;
   }

   @Override
   public void actionPerformed(ActionEvent e) 
   {
      Packer packerThread = new Packer();
      packerThread.start();
   }
   
   private class Packer extends Thread
   {
      private long startTime;
      private long timeDifference;
      public void run()
      {
         startTime = System.currentTimeMillis();
         while (true)
         {
            timeDifference = System.currentTimeMillis() - startTime;
            //referring to the Timer class JLabel object
            setText(String.valueOf(timeDifference));
            doNothing((long)1000);   
         }
      }
      
      public void doNothing(long milliseconds)
      {
         try
         {
            Thread.sleep(milliseconds);
         }
         catch (InterruptedException e)
         {
            System.out.println("Unexpeced interrupt");
            System.exit(0);
         }
      }
   }
}
