//the following two imports are only used for demonstration purposes, do not
//transfer to new file
import java.awt.BorderLayout;
import java.awt.GridLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

@SuppressWarnings("serial")
public class Timer extends JLabel implements ActionListener
{
   /**
    * Timer class is intended to be used as a JLabel. Once it has been called
    * the caller can create a button from a public method that will be able
    * to stop and start the timer. Otherwise the timer can be started by
    * passing true to it in a constructor. Timer will not be able to display
    * any time past 99 minutes and 59 seconds.
    */
   String timerText;
   long startTime;
   JButton timerButton = new JButton();
   Counter counterThread = new Counter();
   
   //this will represent control in the MVC
   //this needs to be removed before implementing
   public static void main(String[] args)
   {
      
      JFrame gui = new JFrame();
      gui.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
      JPanel mainPanel = new JPanel();
      //set JFrame size, layout, and add JPanel
      gui.setSize(300, 200);
      gui.setLayout(new BorderLayout());
      gui.add(mainPanel);
      
      //-----------To create a button actuated timer-------------------- 
      //create a timer that is supposed to be started by a button
      Timer buttonTimer = new Timer();
      JButton timerStartButton = buttonTimer.getButtonToStartTimer();
      timerStartButton.setText("Start/Stop Timer");
      
      //-----------To create an automatically initiated timer-----------
      Timer autoTimer = new Timer(true);
      //Counter autocounterThread = autoTimer.new Counter();
      //autocounterThread.start();
      
      //set layout of JPanel and add button timer, one that is started by a 
      //button, and the other that is started automatically
      mainPanel.setLayout(new GridLayout(2, 2));
      mainPanel.add(buttonTimer, 0, 0);
      mainPanel.add(timerStartButton, 1, 0);
      mainPanel.add(autoTimer, 0, 1);
      
      //role of view, shouldn't be part of main or controller later
      gui.setVisible(true);
   }
   
   public Timer()
   {
      /**
       * Default constructor for Timer class that assigns an action listener
       * sets text alignment, and original timer value.
       */
      timerButton.addActionListener(this);
      this.setHorizontalAlignment(SwingConstants.CENTER);
      setText("00:00");
   }
   
   public Timer(boolean startTimerNow)
   {
      /**
       * Another constructor that allows the caller to create and start the 
       * time immediately.
       */
      this(); //call default constructor
      counterThread.start();
   }
   
   public JButton getButtonToStartTimer()
   {
      /**
       * Returns a JButton that is associated with the actionlistener and will
       * start and stop the timer.
       */
      return timerButton;
   }
   
   public boolean resetTimer()
   {
      /**
       * Reset timer to zero seconds for any reason.
       */
      this.counterThread.setSeconds(0);
      return true;
   }
   
   @Override
   public void actionPerformed(ActionEvent e) 
   {
      /**
       * Action listener for the instance JButton that will start and stop the 
       * timer. If the thread is alive, then stop the existing thread and 
       * create a new thread with the same time value, but stopped.
       * If the thread is not alive, then start the thread.
       */
      if (counterThread.isAlive())
      {
         counterThread.stopThread();
         counterThread = new Counter(counterThread.getSecondsPassed());
      }
      else
      {
         counterThread.start();
      }
   }
   
   private class Counter extends Thread
   {
      /**
       * Counter class is the mutlti-threaded portion of the timer and is what
       * is responsible for making the Timer class not lock up the gui.
       */
      private int seconds = 0;
      private boolean threadRunning = true;
      
      public Counter()
      {
         /**
          * Default constructor that calls the constructor of the Thread class
          */
         super();
      }
      
      public Counter(int timeStartValue)
      {
         /**
          * Another constructor that allows the caller to initialize the thread
          * with a start time. This is what gives the illusion of a paused
          * timer.
          */
         //Subtract 1 from the timeStartValue to prevent the timer incrementing
         //from every pause/start.
         this.seconds = timeStartValue - 1;
      }
      
      public void run()
      {
         /**
          * Called when the instance's method start() is called (inherited from
          * Thread class). This is where the updating of the timer takes place.
          */
         while (threadRunning)
         {
            /*
             * As long as the timer is less than 99 minutes and 59 seconds then
             * increment just one per second. In the event that the timer runs
             * longer than that, start timer over at 0 seconds.
             */
            if (this.seconds < 6000)
            {
               this.seconds += 1;
            }
            else
            {
               this.seconds = 0;
            }
            //referring to the JLabel setText method for Timer
            setText(getFormattedTime(seconds));
            doNothing((long)1000);   
         }
      }
      
      public boolean setSeconds(int seconds)
      {
         /**
          * Allow caller to set seconds of the timer. Added to allow Timer class
          * to reset timer to zero.
          */
         this.seconds = seconds;
         return true;
      }
      /*
      public int getSecondsElapsed()
      {
         return this.seconds;
      }
      
      public boolean hasNonZeroStartTime()
      {
         if (this.seconds > 0)
         {
            return true;
         }
         else
         {
            return false;
         }
      }
      */
      public boolean stopThread()
      {
         /**
          * This method will terminate the loop driving the run() method.
          */
         this.threadRunning = false;
         return true;
      }
      
      public int getSecondsPassed()
      {
         /**
          * Return the number of seconds passed since timer has started.
          */
         return this.seconds;
      }
      
      private String getFormattedTime(int totalElapsedSeconds)
      {
         /**
          * Format and return a String that can be used to set the Timer's 
          * JLabel text. The format of the timer is "MM:ss" where "M" represents
          * the minutes and "s" represents the seconds passed since timer start.
          */
         int minutes = totalElapsedSeconds / 60;
         int seconds = totalElapsedSeconds - (minutes * 60);
         String timerText = String.format("%02d", minutes)
                                       + ":" + String.format("%02d", seconds);
         return timerText;
      }

      private void doNothing(long milliseconds)
      {
         /**
          * This method will allow the thread to sleep for a number of
          * milliseconds and is crucial for keeping time.
          */
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
