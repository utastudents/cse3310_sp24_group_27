package uta.cse3310;
import java.util.List;
import java.util.Random;


public class WordGrid {

   Random rand = new Random();

   // generate both grids (coordinatesList and shownGrid)
   public void generateGrid(int gridSize, double numWords, List<String> words, String[][] coordinatesList, String[][] shownGrid){
      
      // initial fill with * in every spot to show availability
      for(int i = 0; i < gridSize; i++){
         for(int k = 0; k < gridSize; k++){
            shownGrid[i][k] = "*";
         }
      }

      boolean found = false;

      // while you're not done filling in all the words yet
      for(int wordsNum = 0; wordsNum < numWords; wordsNum++) {
         
         String word = getWord();

         while(!found) {
            int randX = rand.nextInt(49);
            int randY = rand.nextInt(49);
            int xVal = randX;
            int yVal = randY;
            int sizeSoFar = 1;
            int direction = rand.nextInt(4);
            boolean taken = false;

            // while the space for the word at the random coordinate isn't taken
            while(sizeSoFar <= word.length() && !taken) {
               //up
               if(direction == 0) {
                  if(shownGrid[xVal][yVal] == "*") {
                     yVal++;
                  }
                  else {
                     taken = true;
                  }
               }

               //down
               else if(direction == 1) {
                  if(shownGrid[xVal][yVal] == "*") {
                     yVal--;
                  }
                  else {
                     taken = true;
                  }
               }

               //left
               else if(direction == 2) {
                  if(shownGrid[xVal][yVal] == "*") {
                     xVal++;
                  }
                  else {
                     taken = true;
                  }
               }

               //up-right
               else if(direction == 3) {
                  if(shownGrid[xVal][yVal] == "*") {
                     xVal++;
                     yVal++;
                  }
                  else {
                     taken = true;
                  }
               }

               //down-left
               else {
                  if(shownGrid[xVal][yVal] == "*") {
                     xVal--;
                     yVal--;
                  }
                  else {
                     taken = true;
                  }
               }

               sizeSoFar++;
            }

            if(!taken) {
               found = true;
            }

            // if taken, restart loop - this chooses all new coordinates and starts everything again
         }
      }

   }


   public String getWord() {
   //    // int randNum = rand.nextInt(25);
   //    // String word;


      // while(!EOF) {
      //    if(getchar(line) == startLetter) {
      //       if(i == index)
      //          return line;
      //       else {
      //          index++;
      //       }

      //    }
      // }
      return "a";
   }

   public static void main(String[] args){

      // generateGrid(50, 10, List<String> words, String coordinatesList[][], shownGrid[][]);
      // for(int i = 0; i < 50; i++){
      //    for(int k = 0; k < 50; k++){
      //       System.out.print(shownGrid[i][k]);
      //    }
      // }
      
   }

}

