package uta.cse3310;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class IntegrationTest
        extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public IntegrationTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(IntegrationTest.class);
    }

    public void singleGame(Game G) {

            // Create a WordGrid instance
            WordGrid wordGrid = new WordGrid();
        
            // Define parameters for the game
            int gridSize = 50;
            int numWords = 150;
            int[][] coordinatesList = new int[2][numWords];
            char[][] shownGrid = new char[gridSize][gridSize];
        
            // Generate the grid
            wordGrid.generateGrid(gridSize, numWords, "words.txt", coordinatesList, shownGrid);
        
            // Create a new Game instance
            Game game = new Game(new Statistics());
        
            // Simulate moves and perform assertions
            // For example:
            // Player 1 selects the first letter of a word
            // Player 2 selects the last letter of that word
            // Repeat until the game is completed
        
            // Simulate Player 1 selecting the first letter of a word
            UserEvent player1StartEvent = new UserEvent(1, PlayerType.PLAYERONE, coordinatesList[0][0], coordinatesList[1][0]);
            game.Update(player1StartEvent);
        
            // Perform assertions
            // For example, check if the selected word is correctly highlighted
            // and if the scores are updated correctly
            assertEquals("Highlighted word color should match player's color", expectedColor, shownGrid[coordinatesList[0][0]][coordinatesList[1][0]]);
            assertEquals("Player 1 score should be incremented", expectedPlayer1Score, game.Stats.getPlayer1Score());
        
            // Simulate Player 2 selecting the last letter of the same word
            UserEvent player2EndEvent = new UserEvent(1, PlayerType.PLAYERTWO, coordinatesList[0][1], coordinatesList[1][1]);
            game.Update(player2EndEvent);
        
            // Perform assertions (again for P2)
            assertEquals("Highlighted word color should match player's color", expectedColor, shownGrid[coordinatesList[0][1]][coordinatesList[1][1]]);
            assertEquals("Player 2 score should be incremented", expectedPlayer2Score, game.Stats.getPlayer2Score());

        
            // Perform assertions to verify the final game state
            // check if all words are correctly highlighted
            // and if the scores are updated correctly
        
            // Check if game grid and coordinates list are not null or empty
            assertNotNull(wordGrid); // Check if the WordGrid object is not null
            assertNotNull(coordinatesList); // Check if the coordinatesList is not null
            assertNotNull(shownGrid); // Check if the shownGrid is not null
            assertTrue(coordinatesList.length > 0); // Check if the coordinatesList is not empty
            assertTrue(shownGrid.length > 0); // Check if the shownGrid is not empty
        }

    public void testOneGame() {
        Game G=new Game(new Statistics());
        singleGame(G);
    }

    public void testTwoGames() {
        // this test does not do much,
        // but it seemed like something to
        // write quickly.
        Game G0 = new Game(new Statistics());
        Game G1 = new Game(new Statistics());
        singleGame(G0);
        singleGame(G1);
    }
}
