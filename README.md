# CST338_Assig6
(M6) Write a Java program (Timed High-Card Game) (8 hrs)
Timed High-Card Game
This is a team assignment
Understand the Classes and Problem

Change the program into a Model-View-Controller Design Pattern.
Add a new part to the High-Card game by putting a timer on the side of the screen.  It will be on a timer to update every second, but in order for you to still play the game, you will need to use multithreading.  (Timer class)
Design a new game.
Redraw the UML diagram so that it represents your new structure.
Phase 1: Model-View-Controller Design Pattern
Phase 2: Multithreading Implimentation
Display the timer box and numbers
Create start and stop buttons to control the timer. (extra challenge: merge the two buttons into one start/stop button)
Make a call to a doNothing() method that will use the sleep() method of the Thread class. 
Phase 3: Make a new Game!
Take turns with the computer putting a card on one of two stacks in the middle of the table.  
You can put on a card that is one value higher or one value lower.  (6 on a 5 OR 4 on a 5, Q on a J OR T on a J, etc.) 
After you play, you get another card from the deck in your hand.
Keep going until the single deck is out of cards.
If you cannot play, click a button that says "I cannot play".  The the computer gets a second turn.  Same for you, if the computer cannot play.  If neither of you can play, then the deck puts a new card on each stack in the middle of the table.
Who ever has the least number of "cannot plays", is the winner.  Declare this at the end, when the deck is exhausted.
Last week we made a GUI card game.  Now it is time to modify it even further.  Redesign Phase 3 from the last module by adding a timer and put it into a design pattern.

Here are the phases:

You need to redesign last week's code into the Model-View-Controller Design Pattern.

There is a sample for #3 on pg 709.

This will help you as you take a look at what code needs to be moved around.

Make sure that the game still works as it did last week.

Multithreading is nice to implement inside of a GUI program, so let's give it a try.

Time to add a timer to the High-Card game.  It should sit on the side of the screen and count up from 0:00, updating every second. 

Of course the use multithreading will be needed to make sure you can still play the game with the timer running. 

Timer class (extends Thread)

Overrides the run() method.  Put all of the needed timer code in the run() method.

Note: The method Thread.sleep can throw an InterruptedException , which is a checked exception— that is, it must be either caught in a catch block or declared in a throws clause. The InterruptedException has to do with one thread interrupting another thread. The book simply notes that an InterruptedException may be thrown by Thread.sleep and so must be accounted for.  The example uses a simple catch block. The class InterruptedException is in the java. lang package and so requires no import statement.

You will need an actionPerformed() method in the main() class to create an object of the Timer class and call start().

Time to create a new game called "BUILD".  The timer created above will not directly impact the game, but will just be running on the side.

Here is how the game works:

Have the game end when there are no more cards to draw from the deck.  You will still have cards in your hand, but don't worry about that.

First use playCard() to get the card you want to play from playerIndex and at the cardIndex location.  Then use the takeCard() to get a new card for end of the array.  You will then need to reorder the labels by using setIcon().

The playCard() method that we added to the Hand class last week will now be used to remove the card at a location and slide all of the cards down one spot in the myCards array.  

Phase 4: Create the UML diagram

Redraw the UML diagram so that it represents your new structure.  Be sure to clearly mark the Model portion, the View portion, and the Controller portions.
