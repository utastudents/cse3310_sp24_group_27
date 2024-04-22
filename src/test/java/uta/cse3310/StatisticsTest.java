// package uta.cse3310;

// import junit.framework.TestCase;
// import org.junit.Test;

// public class StatisticsTest extends TestCase {

//     @Test
//     public void testInitialValues() {
//         Statistics stats = new Statistics();
//         assertEquals(0, stats.getPlayer1Wins());
//         assertEquals(0, stats.getPlayer2Wins());
//         assertEquals(0, stats.getPlayer3Wins());
//         assertEquals(0, stats.getPlayer4Wins());
//         assertEquals(0, stats.getDraws());
//         assertEquals(0, stats.getGamesInProgress());
//         assertEquals(0, stats.getPlayerRank(PlayerType.PLAYERONE));
//         assertEquals(0, stats.getPlayerRank(PlayerType.PLAYERTWO));
//         assertEquals(0, stats.getPlayerRank(PlayerType.PLAYERTHREE));
//         assertEquals(0, stats.getPlayerRank(PlayerType.PLAYERFOUR));
//         assertEquals(0, stats.gettotalScore());
//     }

//     @Test
//     public void testUpdateTotalScore() {
//         Statistics stats = new Statistics();
//         stats.updateTotalScore(50);
//         assertEquals(50, stats.gettotalScore());
//     }

//     @Test
//     public void testCheckAndAwardPoints() {
//         Statistics stats = new Statistics();
//         // Add test cases for checkAndAwardPoints method
//         // For example:
//         //int[][] coordinatesList = {{1, 2}, {3, 4}};
//         //String[][] shownGrid = {{"X", "O"}, {"O", "X"}};
//         //assertEquals(5, stats.checkAndAwardPoints(shownGrid, coordinatesList));
//     }

//     @Test
//     public void testPlayerRankUpdate() {
//         Statistics stats = new Statistics();
//         // Set some player wins to have a basis for ranking them
//         stats.setPlayer1Wins(5);
//         stats.setPlayer2Wins(3);
//         stats.setPlayer3Wins(2);
//         stats.setPlayer4Wins(7);

//         // Call method 
//         stats.updateTotalScore(50);

//         // Assert the expected ranks
//         assertEquals(1, stats.getPlayerRank(PlayerType.PLAYERFOUR));
//         assertEquals(2, stats.getPlayerRank(PlayerType.PLAYERONE));
//         assertEquals(3, stats.getPlayerRank(PlayerType.PLAYERTWO));
//         assertEquals(4, stats.getPlayerRank(PlayerType.PLAYERTHREE));
//     }

//     @Test
//     public void testLeaderBoard() {
//         Statistics stats = new Statistics();
//         // Set some player wins to have a basis for ranking them
//         stats.setPlayer1Wins(5);
//         stats.setPlayer2Wins(3);
//         stats.setPlayer3Wins(2);
//         stats.setPlayer4Wins(7);

//         // Call the method
//         stats.updateTotalScore(50);

//         // Assert the expected leaderboard string
//         String expectedLeaderboard = "Leaderboard:\n" +
//                 "Player\tWins\tRank\n" +
//                 "Player 1\t5\t1\n" +
//                 "Player 2\t3\t2\n" +
//                 "Player 3\t2\t3\n" +
//                 "Player 4\t7\t4\n";
//         assertEquals(expectedLeaderboard, stats.leaderBoard());
//     }
// }
