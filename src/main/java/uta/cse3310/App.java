
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
import java.util.Map;
import java.util.TreeMap;
import java.time.Instant;
import java.time.Duration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class App extends WebSocketServer {

  // All games currently underway on this server are stored in
  // the vector ActiveGames
  private Vector<Game> ActiveGames = new Vector<Game>();

  private Map<String, Integer> UsernameScore = new TreeMap<>();

  private Vector<String> WaitingPlayers = new Vector<String>();

  private int GameId = 0;

  private int gridSize = 50;

  private int connectionId = 0;

  private Instant startTime;

  private Statistics stats;
  
  public static String ver;

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

    // Get version for header title display
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("ver", ver);
    String jsonString = new Gson().toJson(jsonObject);
    broadcast(jsonString);
  }

  public void findGame(WebSocket conn, int numplayers) {
    // Search for a game needing a player
    ServerEvent E = new ServerEvent();
    Game G = null;
    System.err.println("ActiveGames size: " + ActiveGames.size());
    for (Game i : ActiveGames) {
      System.err.println("for loop for active games fired.");

      if (i.MaxPlayers != -1 && i.CurrPlayers < i.MaxPlayers && i.MaxPlayers == numplayers) {
        G = i;
        System.out.println("found a match");
      }
    }

    // No matches? Create a new Game.
    if (G == null) {
      G = new Game(stats);
      G.GameId = GameId;
      G.MaxPlayers = numplayers;
      GameId++;

      // Add the first player
      G.Players = uta.cse3310.PlayerType.PLAYERONE;
      G.CurrPlayers++;

      ActiveGames.add(G);
      System.out.println("creating a new Game");

      // Putting in the generated word grid
      G.wordGrid = new WordGrid();
      G.wordGrid.Grid = new char[50][50];
      G.wordGrid.GridClasses = new String[50][50];
      String filename = "words.txt";
      int numWords = (int) (gridSize * .8);
      System.out.println(numWords);
      int[][] coordinatesList = new int[4][(int) numWords];
      char[][] shownGrid = new char[gridSize][gridSize];
      String[][] gridClasses = new String[gridSize][gridSize];
      
      G.wordGrid.generateGrid(gridSize, numWords, filename, coordinatesList, shownGrid, gridClasses);
      for (var i = 0; i < 50; i++)
      {
          for (var j = 0; j < 50; j++)
          {
            G.wordGrid.Grid[j][i] = shownGrid[j][i];
            G.wordGrid.GridClasses[j][i] = gridClasses[j][i];
          }
      }

    } else {
      // Join an existing game
      System.out.println("not a new game");
      G.Players = PlayerType.values()[G.Players.ordinal() + 1];
      G.CurrPlayers++;
      G.StartGame();
    }

    // Create an event to go to only the new player
    E.YouAre = G.Players;
    E.GameId = G.GameId;

    // Allows the websocket to give us the Game when a message arrives..
    // it stores a pointer to G, and will give that pointer back to us when we ask for it
    conn.setAttachment(G);

    // Note only send to the single connection
    Gson gson = new Gson();
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

      // Submitting username
      if (message.contains("Command")) {
        if (U.username != null) {
          UsernameScore.put(U.username, 0);
          WaitingPlayers.add(U.username);
        }

        // Broadcast username list
        String userscore = gson.toJson(UsernameScore);
        String usernameList = gson.toJson(new UserEvent("username-list", userscore, U.username));
        System.err.println("return whole list after adding " + U.username);
        broadcast(usernameList);
        System.err.println("username list broadcasted");

        // Broadcast waiting list
        String waitingList = gson.toJson(new UserEvent("waiting-list", String.join(",", WaitingPlayers), U.username));
        System.err.println("return waiting list after adding " + U.username);
        broadcast(waitingList);
        System.err.println("waiting list broadcasted");
        return;
      }

      // Connct to game in desired game mode
      if (message.contains("numplayers")) {
        findGame(conn, U.numplayers);

        // Broadcast updated waiting list
        WaitingPlayers.remove(U.username);
        String waitingList = gson.toJson(new UserEvent("waiting-list", String.join(",", WaitingPlayers), U.username));
        System.err.println("return waiting list after removing " + U.username);
        broadcast(waitingList);
        System.err.println("waiting list broadcasted");
      }

      // Connect to game
      Game G = conn.getAttachment();
      G.Update(U);

      // Add usernames of players in current game
      System.err.println("message: " + message + message.contains("username") + " " + U.GameId + " "  + G.GameId);
      if (message.contains("username") && !G.PlayerUserNames.contains(U.username)) {
        G.PlayerUserNames.add(U.username);
      }

      // Send the chat message to everyone
      System.err.println(gson.toJson(U));
      if ("chat-messages".equals(U.type)) {
        String chatMessageJson = gson.toJson(new UserEvent("chat", U.text, U.username));
        System.err.println("chat message: " + chatMessageJson);
        broadcast(chatMessageJson);
        System.err.println("chat message broadcasted");
        return;
      }

      // Send word selection to everyone
      if ("word-selection".equals(U.type)) {
        boolean validWord = G.wordGrid.checkWord(U.text);
        System.err.println("valid word: " + validWord);
        if (validWord) {
          int newScore = UsernameScore.getOrDefault(U.username, 0) + G.checkAndAwardPoints(U.text);
          UsernameScore.put(U.username, newScore);
          String userscore = gson.toJson(UsernameScore);
          String usernameList = gson.toJson(new UserEvent("username-list", userscore, U.username));
          System.err.println("return whole list after adding to " + U.username + "'s score");
          broadcast(usernameList);
          System.err.println("username list broadcasted");

          String wordSelectionJson = gson.toJson(new UserEvent(G.GameId, "wordCoordinates", U.coordinates, U.text, U.username, U.useridx));
          System.err.println("word message: " + wordSelectionJson);
          broadcast(wordSelectionJson);
          System.err.println("word message broadcasted");
          return;
        }
      }

      // Send found words to everyone
      if ("found-words".equals(U.type)) {
        String foundWordsJson = gson.toJson(new UserEvent(G.GameId, "foundWords", U.coordinates, U.text, U.username, U.useridx));
        System.err.println("distribute found words");
        System.out.println(U.text);
        broadcast(foundWordsJson);
        System.err.println("found words broadcasted");
      }

      // Update html/css formatting for all players
      if ("update-format".equals(U.type)) {
        String c = U.text;
        String[] rows = c.split(";");

        String[][] tempArray = new String[gridSize][gridSize];
        for (int i = 0; i < gridSize; i++) {
          String[] elem = rows[i].split(",");
          tempArray[i] = elem;
        }

        for (int i = 0; i < gridSize; i++) {
          for (int j = 0; j < gridSize; j++) {
            G.wordGrid.GridClasses[i][j] = tempArray[i][j];
          }
        }
        return;
      }
      
      String jsonString = gson.toJson(G);
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
    if (System.getenv("VERSION") == null) {
      ver = "1.0";
    } else {
      ver = System.getenv("VERSION");
    }

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
    // trying out the code below
    App websocketServer = new App(wsPort);
    websocketServer.setReuseAddr(true);
    websocketServer.start();
    System.out.println("WebSocket Server started on port: " + wsPort);
  }
}