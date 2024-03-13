package uta.cse3310;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

//import java.lang.annotation.Native;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class WholeGameTest
        extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     * @return
     */
    public WholeGameTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(WholeGameTest.class);
    }

    ////////////////////////////////////////////////////////////////////////////
    // This test is very close to a system level test.
    // Well, the system without the UI.
    // Inputs and Outputs are provided by JSON strings.
    //
    //
    // Should be able to test all of the logic in the program
    // with these tests.
    //
    // The challenge is doing it without repeating a lot of code, or making
    // it tightly coupled to the specific implementation.
    // To minimize coupling, the majority of the tests should deal with
    // json strings.
    ////////////////////////////////////////////////////////////////////////////
    // Routines to replace those in App.java
    ///////////////////////////////////////////////////////////////////////////

    private String update(Game G, String msg) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        UserEvent U = gson.fromJson(msg, UserEvent.class);
        G.Update(U);
        String jsonString = gson.toJson(G);
        return jsonString;
    }

    ////////////////////////////////////////////////////////////////////////////
    public void testXWinGame() {
        Game game = new Game(new Statistics());
        String msg = new String();
        String result = new String();

        // > 7707 1 {\"YouAre\":\"XPLAYER\",\"GameId\":1}
        // < 7746 *
        // {\"Players\":\"XPLAYER\",\"CurrentTurn\":\"NOPLAYER\",\"Button\":[\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\"],\"Msg\":[\"Waiting
        // for other player to join\",\"\"],\"GameId\":1}

        // > 17987 2 {\"YouAre\":\"OPLAYER\",\"GameId\":1}
        game.Players = PlayerType.OPLAYER;
        game.StartGame();

        msg = "{\"Players\":\"OPLAYER\",\"CurrentTurn\":\"XPLAYER\",\"Button\":[\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\"],\"Msg\":[\"You are X. Your turn\",\"You are O. Other players turn\"],\"GameId\":1}";
        assertTrue(msg.indexOf("\"Msg\":[\"You are X. Your turn\"")>-1);
         
        result = update(game, "{\"Button\":0,\"PlayerIdx\":\"XPLAYER\",\"GameId\":1}");
       
        // {\"Players\":\"OPLAYER\",\"CurrentTurn\":\"OPLAYER\",\"Button\":[\"XPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\"],\"Msg\":[\"Other
       
        // Players Move.\",\"Your Move.\"],\"GameId\":1}
        result = update(game,"{\"Button\":1,\"PlayerIdx\":\"OPLAYER\",\"GameId\":1}");
        // > 24067 *
        // {\"Players\":\"OPLAYER\",\"CurrentTurn\":\"XPLAYER\",\"Button\":[\"XPLAYER\",\"OPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\"],\"Msg\":[\"Your
        // Move.\",\"Other Players Move.\"],\"GameId\":1}
        result = update(game,"{\"Button\":4,\"PlayerIdx\":\"XPLAYER\",\"GameId\":1}");
        // > 25126 *
        // {\"Players\":\"OPLAYER\",\"CurrentTurn\":\"OPLAYER\",\"Button\":[\"XPLAYER\",\"OPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"XPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\"],\"Msg\":[\"Other
        // Players Move.\",\"Your Move.\"],\"GameId\":1}
        result = update(game,"{\"Button\":2,\"PlayerIdx\":\"OPLAYER\",\"GameId\":1}");
        // > 26285 *
        // {\"Players\":\"OPLAYER\",\"CurrentTurn\":\"XPLAYER\",\"Button\":[\"XPLAYER\",\"OPLAYER\",\"OPLAYER\",\"NOPLAYER\",\"XPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\"],\"Msg\":[\"Your
        // Move.\",\"Other Players Move.\"],\"GameId\":1}
        result = update(game,"{\"Button\":8,\"PlayerIdx\":\"XPLAYER\",\"GameId\":1}");
        
        assertTrue(result.indexOf("[\"You Win!\",\"You Lose!\"]")>-1);
       
        // > 27683 *
        // {\"Players\":\"OPLAYER\",\"CurrentTurn\":\"NOPLAYER\",\"Button\":[\"XPLAYER\",\"OPLAYER\",\"OPLAYER\",\"NOPLAYER\",\"XPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"NOPLAYER\",\"XPLAYER\"],\"Msg\":[\"You
        // Win!\",\"You Lose!\"],\"GameId\":1}

    }
}
