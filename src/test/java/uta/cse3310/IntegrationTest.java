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

    //////////////////////////////////////////////////////////////////////
    // These are integration tests / component tests.
    // Notice how they call methods directly. In the system tests, the
    // data in and out is json strings.
    //
    // (the program is very small, it is hard to differentiate between an
    // integration / component test and a system level test)
    //////////////////////////////////////////////////////////////////////

    public void singleGame(Game G) {

        int GameID = 1;

        G.GameId = 1;
        G.Players = PlayerType.OPLAYER;
        G.StartGame();

        // play a game

        G.Update(new UserEvent(GameID, PlayerType.XPLAYER, 0));
        // X__
        // ___
        // ___

        G.Update(new UserEvent(GameID, PlayerType.OPLAYER, 2));
        // X0_
        // ___
        // ___

        G.Update(new UserEvent(GameID, PlayerType.XPLAYER, 3));
        // X0_
        // X__
        // ___

        G.Update(new UserEvent(GameID, PlayerType.OPLAYER, 4));
        // X0_
        // X0_
        // ___

        G.Update(new UserEvent(GameID, PlayerType.XPLAYER, 6));
        // X0_
        // X0_
        // X__

        // System.out.println(G.Msg[0]);
        // System.out.println(G.Msg[1]);
        // G.PrintGame();

        // X wins
        assertTrue(G.Msg[0] == "You Win!");
        assertTrue(G.Msg[1] == "You Lose!");

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
