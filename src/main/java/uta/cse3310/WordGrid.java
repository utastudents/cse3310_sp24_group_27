package uta.cse3310;
import java.util.List;
import java.util.Random;


public class WordGrid {

   Random rand = new Random();

   // generate both grids (asteriskGrid and shownGrid)
   public void generateGrid(int gridSize, double numWords, List<String> words, String[][] asteriskGrid, String[][] shownGrid){
      
      /* 
         key to the shown grid, ex: * * * * * w o r d * *
            char asteriskGrid[gridSize][gridSize]

         the word grid actually shown to the players ex: h j l o y w o r d q r
            char shownGrid[gridSize][gridSize]
      */

      // initial fill of the asteriskGrid with * in every spot
      for(int i = 0; i < gridSize; i++){
         for(int k = 0; k < gridSize; k++){
            asteriskGrid[i][k] = "*";
         }
      }

      for(int i = 0; i < gridSize; i++) {
         for(int k = 0; k < gridSize; k++){
            for(int p = 1; p <= numWords; p++){
               // String word = getWord();

               // up, down, right, upright, downright
               int direction = rand.nextInt(4);

               // switch(direction) {
               //    case 0: 
               // }
               
            }
         }
      }

   }

   public String getWord() {
      // int randNum = rand.nextInt(25);
      // String word;


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
      // System.out.print("k");
   }

}

