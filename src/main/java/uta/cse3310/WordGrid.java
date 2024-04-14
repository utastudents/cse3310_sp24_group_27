package uta.cse3310;
import java.util.List;
import java.util.Random;


public class WordGrid {

   Random rand = new Random();

   // generate both grids (coordinatesList and shownGrid)
   public void generateGrid(int gridSize, double numWords, List<String> words, String[][] coordinatesList, String[][] shownGrid){
      
      // initial fill of the asteriskGrid with * in every spot
      // for(int i = 0; i < gridSize; i++){
      //    for(int k = 0; k < gridSize; k++){
      //       shownGrid[i][k] = "*";
      //    }
      // }

      boolean found = false;

      for(int i = 0; i < gridSize; i++) {
         for(int k = 0; k < gridSize; k++){
            for(int p = 1; p <= numWords; p++){
               // String word = getWord();

               // up, down, right, up-right, down-right
               // int direction = rand.nextInt(4);


            }
         }
      }

      while(!found) {
         int randX = rand.nextInt(49);
         int randY = rand.nextInt(49);
         int xVal = randX;
         int yVal = randY;
         int sizeSoFar = 1;
         int direction = rand.nextInt(4);
         boolean taken = false;

         // while(sizeSoFar <= strlen(word) && !taken) {
         while(!taken) {
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
      // generateGrid(50, 10, )
      System.out.print("k");
   }

}

