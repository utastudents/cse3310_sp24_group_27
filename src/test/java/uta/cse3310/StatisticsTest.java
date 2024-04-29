package uta.cse3310;

import junit.framework.TestCase;

 public class StatisticsTest extends TestCase {
    
     public void testInitialValues() {
         Statistics stats = new Statistics();
         assertEquals(0, stats.getPlayer1Wins().intValue());
         assertEquals(0, stats.getPlayer2Wins().intValue());
         assertEquals(0, stats.getPlayer3Wins().intValue());
         assertEquals(0, stats.getPlayer4Wins().intValue());
         assertEquals(0, stats.getDraws().intValue());
         assertEquals(0, stats.getGamesInProgress().intValue());
         assertEquals(0, stats.getPlayerRank(PlayerType.PLAYERONE).intValue());
         assertEquals(0, stats.getPlayerRank(PlayerType.PLAYERTWO).intValue());
         assertEquals(0, stats.getPlayerRank(PlayerType.PLAYERTHREE).intValue());
         assertEquals(0, stats.getPlayerRank(PlayerType.PLAYERFOUR).intValue());
         assertEquals(0, stats.gettotalScore().intValue());
     }

     
     public void testUpdateTotalScore() {
         Statistics stats = new Statistics();
         stats.updateTotalScore(50);
         assertEquals(50, stats.gettotalScore().intValue());
     }

     
     public void testCheckAndAwardPoints() {
         new Statistics();
     }

     
     public void testPlayerRankUpdate() {
         Statistics stats = new Statistics();
         // Set some player wins to have a basis for ranking them
         stats.setPlayer1Wins(5);
         stats.setPlayer2Wins(3);
         stats.setPlayer3Wins(2);
         stats.setPlayer4Wins(7);

         // Call method 
         stats.updateTotalScore(50);

         // Assert the expected ranks
         assertEquals(1, stats.getPlayerRank(PlayerType.PLAYERFOUR).intValue());
         assertEquals(2, stats.getPlayerRank(PlayerType.PLAYERONE).intValue());
         assertEquals(3, stats.getPlayerRank(PlayerType.PLAYERTWO).intValue());
         assertEquals(4, stats.getPlayerRank(PlayerType.PLAYERTHREE).intValue());
     }

     
     public void testLeaderBoard() {
         Statistics stats = new Statistics();
         // Set some player wins to have a basis for ranking them
         stats.setPlayer1Wins(5);
         stats.setPlayer2Wins(3);
         stats.setPlayer3Wins(2);
         stats.setPlayer4Wins(7);

         // Call the method
         stats.updateTotalScore(50);

         // Assert the expected leaderboard string
         String expectedLeaderboard = "Leaderboard:\n" +
                 "Player\tWins\tRank\n" +
                 "Player 1\t5\t1\n" +
                 "Player 2\t3\t2\n" +
                 "Player 3\t2\t3\n" +
                 "Player 4\t7\t4\n";
         assertEquals(expectedLeaderboard, stats.leaderBoard());
     }
}
