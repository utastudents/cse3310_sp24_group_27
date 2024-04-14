package uta.cse3310;
import java.util.List;
import java.util.Random;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.File;


public class WordGrid {

   Random rand = new Random();

   // generate both grids (coordinatesList and shownGrid)
   public void generateGrid(int gridSize, double numWords, String filename, int[][] coordinatesList, String[][] shownGrid){
      
      // initial fill with * in every spot to show availability
      for(int i = 0; i < gridSize; i++){
         for(int k = 0; k < gridSize; k++){
            shownGrid[i][k] = "*";
         }
      }

      boolean found = false;

      // while you're not done filling in all the words yet
      for(int wordsNum = 0; wordsNum < numWords; wordsNum++) {
         
         String word = getWord("words.txt");
         System.out.println(word);
         // String word = "a";
         found = false;

         // while space for the word hasn't been found
         while(!found) {
            int randX = rand.nextInt(gridSize-1);
            int randY = rand.nextInt(gridSize-1);
            int xVal = randX;
            int yVal = randY;
            int sizeSoFar = 1;
            int direction = rand.nextInt(4); //up, down, right, up-right, up-right
            boolean taken = false;

            // while the space for the word at the random coordinate isn't taken
            while(sizeSoFar <= word.length() && !taken) {
               //up
               if(direction == 0) {
                  if(shownGrid[xVal][yVal] == "*") { //  || shownGrid[xVal][yVal].equals(word.charAt(sizeSoFar))
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

               //right
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

               //down-right
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
               coordinatesList[0][wordsNum] = randX;
               coordinatesList[1][wordsNum] = randY;
            }

            // if taken, restart loop - this chooses all new coordinates and starts everything again
         }
      }

   }

   /*
   getWord pseudocode
      getWord(filename)
         //get file
         //duplicate file
         //read duplicate file
         
         //get random letter
         //get random index
         //find word starting with random letter in duplicate file
            //if word is shorter than 3 letters, move on
         //increase index counter and move on until out of words beginning with the selected letter
         //return as "word"
         return word;
   */

   /*
   public String getWord(String filename) {
      String word = null;
      char startLetter = (char) (rand.nextInt(25) + 'a');

      try {
         // String filename = "words.txt";
         String fileDUP = "words_duplicate.txt";

         // Create a duplicate file
         BufferedReader reader = new BufferedReader(new FileReader(filename));
         PrintWriter writer = new PrintWriter(new FileWriter(fileDUP));
         String line;
         // File file = new File(fileDUP);
         // if(file.length() == 0){
            while ((line = reader.readLine()) != null) {
                  if (line.charAt(0) == startLetter && line.length() >= 3) {
                     word = line;
                     writer.println();
                  }
                  else {
                     writer.println(line);
                  }
            }
         // }
         // else {
         //    while ((line = reader.readLine()) != null) {
         //       if (line.equals(word)) {
         //          writer.println();
         //       }
         //    }
         // }
         reader.close();
         writer.close();

      } catch (IOException e) {
         e.printStackTrace();
      }
      return word;
   }
   */

   public String getWord(String filename) {
      String word = null;
      char startLetter = (char) (rand.nextInt(25) + 'a');

      try {
         String fileDUP = "words_duplicate.txt";

         // Create a duplicate file
         BufferedReader reader = new BufferedReader(new FileReader(filename));
         PrintWriter writer = new PrintWriter(new FileWriter(fileDUP));
         String line;
         while ((line = reader.readLine()) != null) {
               if (line.charAt(0) == startLetter && line.length() >= 3) {
                  word = line;
                  //nothing, skip it
               }
               else {
                  writer.println(line);
               }
         }
         reader.close();
         writer.close();

      } catch (IOException e) {
         e.printStackTrace();
      }
      return word;
   }
   // print the grid
   public void printGrid(int gridSize, String shownGrid[][]) {
      for(int i = 0; i < gridSize; i++) {
         for(int k = 0; k < gridSize; k++) {
            System.out.print(shownGrid[i][k] + " ");
         }
         System.out.println();
      }
   }

   public void printCoordinatesList(int[][] coordinatesList) {
      for(int set = 0; set < coordinatesList[0].length; set++) {
         System.out.println(coordinatesList[0][set] + " " + coordinatesList[1][set]);
      }
   }


   public static void main(String[] args) {
      WordGrid wordGrid = new WordGrid();
      
      String filename = "words.txt";      
      int gridSize = 50;
      int numWords = 5;
      int[][] coordinatesList = new int[2][(int) numWords];
      String[][] shownGrid = new String[gridSize][gridSize];
      
      wordGrid.generateGrid(gridSize, numWords, filename, coordinatesList, shownGrid);
      wordGrid.printGrid(gridSize, shownGrid);

      wordGrid.printCoordinatesList(coordinatesList);
      // System.out.println(coordinatesList[0][numWords], numWords);


   }

}

