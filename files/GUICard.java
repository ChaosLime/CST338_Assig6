package files;

import javax.swing.Icon;
import javax.swing.ImageIcon;


public class GUICard
{
   /*
    * The 52 + 4 jokers Icons will be read and stored into the iconCards[][] array.
    * The card-back image in the iconBack member.  None of these data need to be 
    * stored more than once, so this is a class without instance data.  This class 
    * is used is to produce an image icon when the client needs one. 
    */
   // 14 = A thru K + joker
   private static Icon[][] iconCards = new ImageIcon[14][4];
   
   private static Icon iconBack;
   private static boolean iconsLoaded = false;
   
   /*
    * loadCardIcons method similar to Phase 1, but storing the Icons in a 2-D array
    * Doesn't require the client to call this method. 
    */
   public static void loadCardIcons()
   {
      /*
       * Build the file names in a loop such as AC.gif, 2C.gif, TC.gif, etc.
       * For each file name, read it in and use it to instantiate each of
       * the 57 Icons in the Icon 2-D array
       */
      if( !iconsLoaded )
      {
         for( int i = 0; i < 4; i++ )
         {
            for( int j = 0; j < 14; j++ )
            {
               String file = "images/" + turnIntIntoCardValue(j) +
                     turnIntIntoCardSuit(i) + ".gif";
               iconCards[j][i] = new ImageIcon(file);
            }
         }
         
         iconBack = new ImageIcon("images/BK.gif");
         iconsLoaded = true;
      }
   }
   
   // converts int to card value (0 - 14 --> "A", "2",..., "Q", "K", etc)
   public static String turnIntIntoCardValue( int j )
   {
      String[] cardValues = 
         {"A", "2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K",
               "X"};
      
      if( j >= 0 && j <= 13 )
         return cardValues[j];
      
      return "";
   }
   
   // converts int to card suits ("D", "C", "H", "S")
   public static String turnIntIntoCardSuit( int k )
   {
      String[] suites = {"C", "D", "H", "S"};
      
      if( k >= 0 && k <= 3 )
         return suites[k];
      
      return "";
   }
   
   public static Icon getIcon( Card card )
   {
      return iconCards[Card.valueAsInt(card)][Card.suitAsInt(card)];
   }
   
   public static Icon getBackCardIcon()
   {
      return iconBack;
   }
   
}
