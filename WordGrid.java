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

   public char[][] Grid;

   //the grid needs to be added to the grid object (public char[][] Grid) 

   // generate both grids (coordinatesList and shownGrid)
   public void generateGrid(int gridSize, int numWords, String filename, int[][] coordinatesList, char[][] shownGrid){
      
      Random rand = new Random();
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
         int   randX = rand.nextInt(gridSize-1), 
               randY = rand.nextInt(gridSize-1), 
               xVal = randX, 
               yVal = randY, 
               direction = rand.nextInt(4); //up, down, right, up-right, up-right
         int index = 0, sizeSoFar;

         // while space for the word hasn't been placed
         while(!placed) {
            sizeSoFar = 1;
            boolean taken = false;

            // while the space for the word at the random coordinate isn't taken
            while(sizeSoFar <= word.length() && !taken && !entireWord) {

               //up
               if(direction == 0) {
                  if(shownGrid[xVal][yVal] == '*' || shownGrid[xVal][yVal] == (word.charAt(sizeSoFar-1))) { //  || shownGrid[xVal][yVal].equals(word.charAt(sizeSoFar-1))
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
                  if(shownGrid[xVal][yVal] == '*' || shownGrid[xVal][yVal] == (word.charAt(sizeSoFar-1))) {
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
                  if(shownGrid[xVal][yVal] == '*' || shownGrid[xVal][yVal] == (word.charAt(sizeSoFar-1))) {
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
                  if(shownGrid[xVal][yVal] == '*' || shownGrid[xVal][yVal] == (word.charAt(sizeSoFar-1))) {
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
                  if(shownGrid[xVal][yVal] == '*' || shownGrid[xVal][yVal] == (word.charAt(sizeSoFar-1))) {
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
               coordinatesList[0][wordsNum] = randY;
               coordinatesList[1][wordsNum] = randX;
            }
            else {
               randX = rand.nextInt(gridSize-1);
               randY = rand.nextInt(gridSize-1);
                  xVal = randX;
                  yVal = randY;
            }

            // if taken, restart loop - this chooses all new coordinates and starts everything again
         }

         while(!done) {
            
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

      // randomizeSpaces(gridSize, shownGrid);

   }

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
            if(shownGrid[i][k] == '*') System.out.print("  ");
            else System.out.print(shownGrid[i][k] + " ");
         }
         System.out.println();
      }
   }

   public void printCoordinatesList(int[][] coordinatesList) {
      for(int set = 0; set < coordinatesList[0].length; set++) {
         System.out.println(coordinatesList[0][set] + " " + coordinatesList[1][set]);
      }
   }

   public void randomizeSpaces(int gridSize, char shownGrid[][]) {
      
      Random rand = new Random();
      for(int i = 0; i < gridSize; i++) {
         for(int k = 0; k < gridSize; k++) {
            if(shownGrid[i][k] == '*') shownGrid[i][k] = (char) (rand.nextInt(25)+97);
         }
      }
   }


   // // to test WordGrid
   // public static void main(String[] args) {
   //    WordGrid wordGrid = new WordGrid();
      
   //    String filename = "words.txt";
   //    // int gridSize = 50;
   //    int gridSize = 35;
   //    int numWords = (int) (gridSize * .8);
   //    System.out.println(numWords);
   //    int[][] coordinatesList = new int[2][(int) numWords];
   //    char[][] shownGrid = new char[gridSize][gridSize];
      
   //    wordGrid.generateGrid(gridSize, numWords, filename, coordinatesList, shownGrid);
      
   //    wordGrid.printGrid(gridSize, shownGrid);

   //    wordGrid.printCoordinatesList(coordinatesList);


   // }

}
