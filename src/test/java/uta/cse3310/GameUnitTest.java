// package uta.cse3310;

// import junit.framework.Test;
// import junit.framework.TestCase;
// import junit.framework.TestSuite;

// /**
//  * Unit test for simple App.
//  */
// public class GameUnitTest
//         extends TestCase {

//             private Game game;
//             private Statistics stats;
//     /**
//      * Create the test case
//      *
//      * @param testName name of the test case
//      */
//     public GameUnitTest(String testName) {
//         super(testName);
//     }

//     /**
//      * @return the suite of tests being tested
//      */
//     public static Test suite() {
//         return new TestSuite(GameUnitTest.class);
//     }

//     public void setUp() {
//         stats = new Statistics();
//         game = new Game(stats);
//     }
//     //////////////////////////////////////////////////////////////////////////
//     // These are unit tests, to check methods in the Game class.
//     //////////////////////////////////////////////////////////////////////////

//     public void testResetBoard() { // first create a game object
//         game.ResetBoard();
//         for (PlayerType pt : game.Button) {
//             assertEquals("Board should reset to NOPLAYER", PlayerType.NOPLAYER, pt);
//         }
//     }

//     public void testOpenSpots() {
//         assertEquals("Initially, all spots should be open", 4, game.OpenSpots());
//     }

//     public void testOpenSpotsAfterMove() {
//         game.Button[0] = PlayerType.PLAYERONE;
//         assertEquals("After one move, there should be 3 open spots", 3, game.OpenSpots());
//     }

//     public void testBoardForHorizontalWin() {
//         PlayerType[] players = {PlayerType.PLAYERONE, PlayerType.PLAYERTWO, PlayerType.PLAYERTHREE, PlayerType.PLAYERFOUR};
//         for (PlayerType player : players) {
//             game.ResetBoard();
//             game.Button[0] = player;
//             game.Button[1] = player;
//             game.Button[2] = player;
//             assertTrue("With three in a row horizontally, should return true for" + player, game.CheckBoard(player));
//         }
//     }

//     public void testBoardForVerticalWin() {
//         PlayerType[] players = {PlayerType.PLAYERONE, PlayerType.PLAYERTWO, PlayerType.PLAYERTHREE, PlayerType.PLAYERFOUR};
//         for (PlayerType player : players) {
//             game.ResetBoard();
//             game.Button[0] = player;
//             game.Button[3] = player;
//             game.Button[6] = player;
//             assertTrue("With three in a row vertically, should return true for" + player, game.CheckBoard(player));
//         }
//     }

//     public void testBoardForDiagonalWin() {
//         PlayerType[] players = {PlayerType.PLAYERONE, PlayerType.PLAYERTWO, PlayerType.PLAYERTHREE, PlayerType.PLAYERFOUR};
//         for (PlayerType player : players) {
//             game.ResetBoard();
//             game.Button[0] = player;
//             game.Button[4] = player;
//             game.Button[8] = player;
//             assertTrue("With three in a row diagonally from top to left to bottom right, should return true for" + player, game.CheckBoard(player));
//             game.ResetBoard();
//             game.Button[2] = player;
//             game.Button[4] = player;
//             game.Button[6] = player;
//             assertTrue("With three in a row diagonally from top right to bottom left, should return true for" + player, game.CheckBoard(player));
//         }
//     }

//     public void testBoardForNoWin() {
//         game.ResetBoard();
//         game.Button[0] = PlayerType.PLAYERONE;
//         game.Button[1] = PlayerType.PLAYERTWO;
//         game.Button[2] = PlayerType.PLAYERONE;
//         assertFalse("With no three in a row, should return false", game.CheckBoard(PlayerType.PLAYERONE));
//     }

//     public void testNoFalsePositivesInWinCheck() {
//         PlayerType[] players = {PlayerType.PLAYERONE, PlayerType.PLAYERTWO, PlayerType.PLAYERTHREE, PlayerType.PLAYERFOUR};
//         for (PlayerType player : players) {
//             game.ResetBoard();
//             game.Button[0] = player;
//             game.Button[1] = player;
//             assertFalse("Should not report a win with only two in a row", game.CheckBoard(player));
//         }
//     }
// }
// // mvn -Dtest=WholeGameTest test