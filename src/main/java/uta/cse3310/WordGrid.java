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
   public void generateGrid(int gridSize, double numWords, String filename, int[][] coordinatesList, char[][] shownGrid){
      
      // initial fill with * in every spot to show availability
      for(int i = 0; i < gridSize; i++){
         for(int k = 0; k < gridSize; k++){
            shownGrid[i][k] = '*';
         }
      }


      // while you're not done filling in all the words yet
      for(int wordsNum = 0; wordsNum < numWords; wordsNum++) {
         
         boolean placed = false, done = false, entireWord = false;
         String word = getWord("words.txt");
         System.out.println(word);
         // int   randX = rand.nextInt(gridSize-1), 
         //       randY = rand.nextInt(gridSize-1), 
         int   randX = rand.nextInt(49), 
               randY = rand.nextInt(49), 
               xVal = randX, 
               yVal = randY, 
               direction = rand.nextInt(4); //up, down, right, up-right, up-right
         int index = 0, sizeSoFar;

         // while space for the word hasn't been placed
         while(!placed) {
            // randX = rand.nextInt(gridSize-1);
            // randY = rand.nextInt(gridSize-1);
            sizeSoFar = 1;
            boolean taken = false;

            // while the space for the word at the random coordinate isn't taken
            while(sizeSoFar <= word.length() && !taken && !entireWord) {
               // if(xVal + 1 < 50 && xVal - 1 > -1 && yVal + 1 < 50 && yVal - 1 > -1) {

               // }
               //up
               if(direction == 0) {
                  if(shownGrid[xVal][yVal] == '*') { //  || shownGrid[xVal][yVal].equals(word.charAt(sizeSoFar))
                     if(yVal - 1 > -1) {
                        yVal--;
                     }
                     else if(sizeSoFar == word.length()) {
                        taken = true;
                     }
                     else {
                        taken = true;
                     }
                  }
                  else {
                     taken = true;
                  }
               }

               //down
               else if(direction == 1) {
                  if(shownGrid[xVal][yVal] == '*') {
                     if(yVal + 1 < gridSize) {
                        yVal++;
                     }
                     else if(sizeSoFar == word.length()) {
                        entireWord = true;
                     }
                     else {
                        taken = true;
                     }
                  }
                  else {
                     taken = true;
                  }
               }

               //right
               else if(direction == 2) {
                  if(shownGrid[xVal][yVal] == '*') {
                     if(xVal + 1 < gridSize) {
                        xVal++;
                     }
                     else if(sizeSoFar == word.length()) {
                        entireWord = true;
                     }
                     else {
                        taken = true;
                     }
                  }
                  else {
                     taken = true;
                  }
               }

               //up-right
               else if(direction == 3) {
                  if(shownGrid[xVal][yVal] == '*') {
                     if(yVal + 1 < gridSize && xVal + 1 < gridSize) {
                        yVal++;
                        xVal++;
                     }
                     else if(sizeSoFar == word.length()) {
                        entireWord = true;
                     }
                     else {
                        taken = true;
                     }
                  }
                  else {
                     taken = true;
                  }
               }

               //down-right
               else {
                  if(shownGrid[xVal][yVal] == '*') {
                     if(yVal - 1 < -1 && xVal - 1 < -1) {
                        yVal--;
                        xVal--;
                     }
                     else if(sizeSoFar == word.length()) {
                        entireWord = true;
                     }
                     else {
                        taken = true;
                     }
                  }
                  else {
                     taken = true;
                  }
               }

               sizeSoFar++;
            }

            if(!taken) {
               placed = true;
               coordinatesList[0][wordsNum] = randX;
               coordinatesList[1][wordsNum] = randY;
            }
            else {
               // randX = rand.nextInt(gridSize-1);
               // randY = rand.nextInt(gridSize-1);
               randX = rand.nextInt(49);
               randY = rand.nextInt(49);
                  xVal = randX;
                  yVal = randY;
            }

            // if taken, restart loop - this chooses all new coordinates and starts everything again
         }

         // System.out.println("direction" + direction);
         while(!done) {

            // System.out.println("randx " + randX);
            // System.out.println("rand y " + randY);
            // System.out.println("index " + index);
            
            shownGrid[randX][randY] = word.charAt(index);

            // up
            if(direction == 0) {
               randY--;
               index++;
            }

            // down
            else if(direction == 1) {
               randY++;
               index++;
            }

            // right
            else if(direction == 2) {
               randX++;
               index++;
            }

            // up-right
            else if(direction == 3) {
               randX++;
               randY++;
               index++;
            }

            // down-right
            else {
               randX--;
               randY--;
               index++;
            }

            if(index == word.length()){
               done = true;
            }
            
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

   public String getWord(String filename) {
      String word = null;
      try {
         // Open the file for reading
         BufferedReader reader = new BufferedReader(new FileReader(filename));
         
         // Count the number of lines in the file
         int lineCount = 0;
         while (reader.readLine() != null) {
               lineCount++;
         }
         reader.close();
         
         // Generate a random index
         Random rand = new Random();
         int randomIndex = rand.nextInt(lineCount);
         
         // Open the file again to read the word at the random index
         reader = new BufferedReader(new FileReader(filename));
         for (int i = 0; i < randomIndex; i++) {
               reader.readLine(); // Skip lines until reaching the random index
         }
         word = reader.readLine(); // Read the word at the random index
         reader.close();
      } catch (IOException e) {
         e.printStackTrace();
      }
      return word;
   }

   
   // print the grid
   public void printGrid(int gridSize, char shownGrid[][]) {
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
      int numWords = 100;
      int[][] coordinatesList = new int[2][(int) numWords];
      char[][] shownGrid = new char[gridSize][gridSize];
      
      wordGrid.generateGrid(gridSize, numWords, filename, coordinatesList, shownGrid);
      
      wordGrid.printGrid(gridSize, shownGrid);

      wordGrid.printCoordinatesList(coordinatesList);

   }

}
