package uta.cse3310;

 
import junit.framework.Test;

import junit.framework.TestCase;

import junit.framework.TestSuite;

 

import com.google.gson.Gson;

import com.google.gson.GsonBuilder;

 
public class WholeGameTest extends TestCase {

 
    private static String testName= "testWordFound";

 

    public WholeGameTest() {

        super(testName);

    }

 

    public static Test suite() {

        return new TestSuite(WholeGameTest.class);

    }



    private String update(Game game, String msg) {

        GsonBuilder builder = new GsonBuilder();

        Gson gson = builder.create();

        UserEvent U = gson.fromJson(msg, UserEvent.class);

        game.Update(U);

        String jsonString = gson.toJson(game);

        return jsonString;

    }





    public void testWordFound() {

        Game game = new Game(new Statistics());

        String msg = "{\"board\": [[\"A\",\"B\",\"C\",\"D\"],[\"E\",\"F\",\"G\",\"H\"],[\"I\",\"J\",\"K\",\"L\"],[\"M\",\"N\",\"O\",\"P\"]],\"words\": [\"WORD\",\"TEST\"],\"score\": 0}";

        String result = this.update(game, msg);

 

        assertTrue(result.indexOf("\"score\": 0") > -1);

 

        result = update(game, "{\"row\": 0,\"col\": 0}");

        result = update(game, "{\"row\": 0,\"col\": 1}");

        result = update(game, "{\"row\": 0,\"col\": 2}");

        result = update(game, "{\"row\": 0,\"col\": 3}");

 

        assertTrue(result.indexOf("\"score\": 1") > -1);

    }

}