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
         
         String word = getWord(filename);

         // while space for the word hasn't been found
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
               coordinatesList[1][wordsNum] = randX;
               coordinatesList[wordsNum][1] = randY;
            }

            // if taken, restart loop - this chooses all new coordinates and starts everything again
         }
      }

   }


   // public String getWord() {
   //    //get file
   //    //duplicate file
   //    //read duplicate file
      
   //    //get random letter
   //    //get random index
   //    //find word starting with random letter in duplicate file
   //       //if word is shorter than 3 letters, move on
   //    //increase index counter and move on until out of words beginning with the selected letter
   //    //return as "word"
   //    return word;
   // }

   public String getWord(String filename) {
      String word = null;
      try {
         // String filename = "words.txt";
         String fileDUP = "words.txt";

         // Create a duplicate file
         BufferedReader reader = new BufferedReader(new FileReader(filename));
         PrintWriter writer = new PrintWriter(new FileWriter(fileDUP));
         String line;
         while ((line = reader.readLine()) != null) {
               writer.println(line);
         }
         reader.close();
         writer.close();

         // Read from the duplicate file
         BufferedReader duplicateReader = new BufferedReader(new FileReader(fileDUP));

         // Get a random letter
         char startLetter = (char) (rand.nextInt(26) + 'A');

         // Find a word starting with the random letter
         boolean found = false;
         while (!found && (line = duplicateReader.readLine()) != null) {
               if (line.charAt(0) == startLetter && line.length() >= 3) {
                  word = line;
                  found = true;
               }
         }
         duplicateReader.close();
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

   public static void main(String[] args) {
      WordGrid wordGrid = new WordGrid();
      
      String filename = "words.txt";      
      int gridSize = 27;
      double numWords = 5;
      int[][] coordinatesList = new int[2][gridSize];
      String[][] shownGrid = new String[gridSize][gridSize];
      
      wordGrid.printGrid(gridSize, shownGrid);

      // wordGrid.generateGrid(gridSize, numWords, filename, coordinatesList, shownGrid);
      
   }

}

