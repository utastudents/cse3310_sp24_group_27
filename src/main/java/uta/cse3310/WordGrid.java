package uta.cse3310;
import java.util.ArrayList;
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
   public String[][] GridClasses;
   public int GridSize;
   public ArrayList<String> words = new ArrayList<String>();
   // the grid needs to be added to the grid object (public char[][] Grid) 

   // generate both grids (coordinatesList and shownGrid)
   public void generateGrid(int gridSize, int numWords, String filename, int[][] coordinatesList, char[][] shownGrid, String[][] gridClasses){
      this.GridSize = gridSize;
      Random rand = new Random();
      // initial fill with * in every spot to show availability
      for(int i = 0; i < gridSize; i++){
         for(int k = 0; k < gridSize; k++){
            shownGrid[i][k] = '*';
         }
      }

      for(int i = 0; i < gridSize; i++){
         for(int k = 0; k < gridSize; k++){
            gridClasses[i][k] = "cell";
         }
      }

      // test words
      // String tempword;
      // int randw = rand.nextInt(2);
      // if(randw == 0) {tempword = "agreement";}
      // else if(randw == 1) {tempword = "banana";}
      // else {tempword = "parliament";}
      // for(int i = 20, index = 0; i < (20+tempword.length()); i++, index++){
      //    shownGrid[0][i] = (char) tempword.charAt(index);
      // }
      // coordinatesList[0][0] = 0;
      // coordinatesList[1][0] = 20;
      // coordinatesList[2][0] = 0;
      // coordinatesList[3][0] = 20+tempword.length();
      // words.add(tempword);
      // end of test

      // while you're not done filling in all the words yet
      for(int wordsNum = 0; wordsNum < numWords; wordsNum++) {
         boolean placed = false, done = false, entireWord = false;
         String word = getWord("words.txt");
         while (words.contains(word)) {
            word = getWord("words.txt");
         }
         int   randX = rand.nextInt(gridSize-1), 
               randY = rand.nextInt(gridSize-1), 
               xVal = randX, 
               yVal = randY, 
               direction = rand.nextInt(4); // up, down, right, up-right, up-right
         int index = 0, sizeSoFar;
         words.add(word);
         // while space for the word hasn't been placed
         while(!placed) {
            sizeSoFar = 1;
            boolean taken = false;

            // while the space for the word at the random coordinate isn't taken
            while(sizeSoFar <= word.length() && !taken && !entireWord) {

               // up
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

               // down
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

               // right
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

               // up-right
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

               // down-right
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
               coordinatesList[2][wordsNum] = yVal;
               coordinatesList[3][wordsNum] = xVal;
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

      randomizeSpaces(gridSize, shownGrid);
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
   public void printGrid(char shownGrid[][]) {
      for(int i = 0; i < GridSize; i++) {
         for(int k = 0; k < GridSize; k++) {
            if(shownGrid[i][k] == '*') System.out.print("  ");
            else System.out.print(shownGrid[i][k] + " ");
         }
         System.out.println();
      }
   }

   public void printCoordinatesList(int[][] coordinatesList) {
      for(int set = 0; set < coordinatesList[0].length; set++) {
         System.out.println(coordinatesList[0][set] + " " + coordinatesList[1][set] + " " + coordinatesList[2][set] + " " + coordinatesList[3][set]);
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

   // Check if word is in word bank
   public boolean checkWord(String word){
      boolean found = false;
      for(int i = 0; i < words.size(); i++){
         if(words.get(i).equals(word)){
            words.remove(i);
            found = true;
         }
      }
      return found;
   }

   // to test WordGrid
   // public static void main(String[] args) {
   //    WordGrid wordGrid = new WordGrid();
      
   //    String filename = "words.txt";
   //    // int gridSize = 50;
   //    int gridSize = 35;
   //    int numWords = (int) (gridSize * .8);
   //    System.out.println(numWords);
   //    int[][] coordinatesList = new int[4][(int) numWords];
   //    char[][] shownGrid = new char[gridSize][gridSize];
      
   //    wordGrid.generateGrid(gridSize, numWords, filename, coordinatesList, shownGrid);
      
   //    wordGrid.printGrid(gridSize, shownGrid);

   //    wordGrid.printCoordinatesList(coordinatesList);
   // }
}