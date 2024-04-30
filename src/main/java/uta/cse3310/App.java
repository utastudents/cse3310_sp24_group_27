
// This is example code provided to CSE3310 Fall 2022
// You are free to use as is, or changed, any of the code provided

// Please comply with the licensing requirements for the
// open source packages being used.

// This code is based upon, and derived from the this repository
//            https:/thub.com/TooTallNate/Java-WebSocket/tree/master/src/main/example

// http server include is a GPL licensed package from
//            http://www.freeutils.net/source/jlhttp/

/*
 * Copyright (c) 2010-2020 Nathan Rajlich
 *
 *  Permission is hereby granted, free of charge, to any person
 *  obtaining a copy of this software and associated documentation
 *  files (the "Software"), to deal in the Software without
 *  restriction, including without limitation the rights to use,
 *  copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the
 *  Software is furnished to do so, subject to the following
 *  conditions:
 *
 *  The above copyright notice and this permission notice shall be
 *  included in all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 *  EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 *  OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 *  NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 *  HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 *  WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 *  FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 *  OTHER DEALINGS IN THE SOFTWARE.
 */

package uta.cse3310;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Collections;

import org.java_websocket.WebSocket;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.time.Instant;
import java.time.Duration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class App extends WebSocketServer {

  // All games currently underway on this server are stored in
  // the vector ActiveGames
  private Vector<Game> ActiveGames = new Vector<Game>();

  private int GameId = 1;

  private int connectionId = 0;

  private Instant startTime;

  private Statistics stats;

  public App(int port) {
    super(new InetSocketAddress(port));
  }

  public App(InetSocketAddress address) {
    super(address);
  }

  public App(int port, Draft_6455 draft) {
    super(new InetSocketAddress(port), Collections.<Draft>singletonList(draft));
  }

  @Override
  public void onOpen(WebSocket conn, ClientHandshake handshake) {

    connectionId++;

    System.out.println(conn.getRemoteSocketAddress().getAddress().getHostAddress() + " connected");

    ServerEvent E = new ServerEvent();

    // search for a game needing a player
    Game G = null;
    for (Game i : ActiveGames) {
      if (i.Players == uta.cse3310.PlayerType.PLAYERONE ||
          i.Players == uta.cse3310.PlayerType.PLAYERTWO ||
          i.Players == uta.cse3310.PlayerType.PLAYERTHREE) {
        G = i;
        System.out.println("found a match");
      }
    }

    // No matches ? Create a new Game.
     // No matches ? Create a new Game.
     if (G == null) {
      G = new Game(stats);
      G.GameId = GameId;
      GameId++;
      // Add the first player
      G.Players = PlayerType.PLAYERONE;
      ActiveGames.add(G);
      System.out.println(" creating a new Game");

      //putting in the generated word grid
      G.wordGrid = new WordGrid();
      G.wordGrid.Grid = new char[50][50]; 
      String filename = "words.txt";
      int gridSize = 50;
      int numWords = (int) (gridSize * .8);
      System.out.println(numWords);
      int[][] coordinatesList = new int[4][(int) numWords];
      char[][] shownGrid = new char[gridSize][gridSize];
      
      G.wordGrid.generateGrid(gridSize, numWords, filename, coordinatesList, shownGrid);
      for (var i = 0; i < 50; i++)
      {
          for (var j = 0; j < 50; j++)
          {
            G.wordGrid.Grid[j][i] = shownGrid[j][i];
          }
      }
    } else {
      // join an existing game
      System.out.println(" not a new game");
      G.Players = PlayerType.values()[G.Players.ordinal() + 1];
      G.StartGame();
    }

    // create an event to go to only the new player
    E.YouAre = G.Players;
    E.GameId = G.GameId;

    // allows the websocket to give us the Game when a message arrives..
    // it stores a pointer to G, and will give that pointer back to us
    // when we ask for it
    conn.setAttachment(G);

    Gson gson = new Gson();

    // Note only send to the single connection
    String jsonString = gson.toJson(E);
    conn.send(jsonString);
    System.out
        .println("> " + Duration.between(startTime, Instant.now()).toMillis() + " " + connectionId + " "
            + escape(jsonString));

    

    // The state of the game has changed, so lets send it to everyone
    jsonString = gson.toJson(G);
    System.out
        .println("< " + Duration.between(startTime, Instant.now()).toMillis() + " " + "*" + " " + escape(jsonString));
    broadcast(jsonString);

  }

  @Override
  public void onClose(WebSocket conn, int code, String reason, boolean remote) {
 System.out.println(conn + " has closed");
    // Retrieve the game tied to the websocket connection
    Game G = conn.getAttachment();
    G = null;
  }

  @Override
  public void onMessage(WebSocket conn, String message) {
    System.out
        .println("< " + Duration.between(startTime, Instant.now()).toMillis() + " " + "-" + " " + escape(message));
    GsonBuilder builder = new GsonBuilder();
    Gson gson = builder.create();
    try{
      UserEvent U = gson.fromJson(message, UserEvent.class);
      Game G = conn.getAttachment();
      G.Update(U);

      System.err.println("message: " + message + message.contains("username") + U.GameId + G.GameId);
      if (message.contains("username")){ //  && U.GameId == G.GameId
        G.PlayerUserNames.add(U.username);
      }
      System.err.println(gson.toJson(U));
      if ("chat-messages".equals(U.type)) {
        // Chat message
        // Send the message to everyone
        String chatMessageJson = gson.toJson(new UserEvent("chat", U.text, U.username));
        System.err.println("chat message: " + chatMessageJson);
        broadcast(chatMessageJson);
        System.err.println("chat message broadcasted");
        return;
      }
      if ("word-selection".equals(U.type)) {
        // word selection
        // Send the selection to everyone
        boolean validWord = G.wordGrid.checkWord(U.text);
        System.err.println("valid word: " + validWord);
        if (validWord) {
          String wordSelectionJson = gson.toJson(new UserEvent("wordCoordinates", U.coordinates, U.username));
          System.err.println("word message: " + wordSelectionJson);
          broadcast(wordSelectionJson);
          System.err.println("word message broadcasted");
          return;
        }
      }
      String jsonString;
      jsonString = gson.toJson(G);
  
      System.out.println("> " + Duration.between(startTime, Instant.now()).toMillis() + " " + "*" + " " + escape(jsonString));
      broadcast(jsonString);
    }
    catch (Exception e) {
      System.err.println("Received message that isn't a valid JSON: " + e);
    }
  }

  @Override
  public void onMessage(WebSocket conn, ByteBuffer message) {
    System.out.println(conn + ": " + message);
  }

  @Override
  public void onError(WebSocket conn, Exception ex) {
    ex.printStackTrace();
    if (conn != null) {
      // some errors like port binding failed may not be assignable to a specific
      // websocket
    }
  }

  @Override
  public void onStart() {
    setConnectionLostTimeout(0);
    stats = new Statistics();
    startTime = Instant.now();
  }

  private String escape(String S) {
      // turns " into \"
      String retval = new String();
      // this routine is very slow.
      // but it is not called very often
      for (int i = 0; i < S.length(); i++) {
        Character ch = S.charAt(i);
        if (ch == '\"') {
          retval = retval + '\\';
        }
        retval = retval + ch;
      }
      return retval;
  }

  public static void main(String[] args) {
    
    // Set up the http server
    String HttpPortEnv = System.getenv("HTTP_PORT");
    int httpPort = 9027;

    if (HttpPortEnv!=null) {
      httpPort = Integer.valueOf(HttpPortEnv);
    }

    HttpServer H = new HttpServer(httpPort, "./html");
    H.start();
    System.out.println("http Server started on port: " + httpPort);

    String WSPortEnv = System.getenv("WEBSOCKET_PORT");
    int wsPort = 9127;
    if (WSPortEnv!=null) {
      wsPort = Integer.valueOf(WSPortEnv);
    }

    // create and start the websocket server
    //trying out the code below
    App websocketServer = new App(wsPort);
    websocketServer.setReuseAddr(true);
    websocketServer.start();
    System.out.println("WebSocket Server started on port: " + wsPort);


    //this is the old code
    // port = 9880;
    // App A = new App(port);
    // A.setReuseAddr(true);
    // A.start();
    // System.out.println("websocket Server started on port: " + port);

  }
}
