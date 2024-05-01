package uta.cse3310;

import java.util.ArrayList;

public class Game {

    PlayerType[] Players;
    public PlayerType CurrentTurn;
    public PlayerType[][] Button;
    // Buttons are indexed 0 to 8 in the code
    // 0 1 2
    // 3 4 5
    // 6 7 8

    public String[] Msg;
    public int GameId;
    public int GameMode; // 1, 2, 3
    public int gridDim = 50;
    public Statistics Stats; 
    public ArrayList<String> PlayerUserNames = new ArrayList<String>();
    public WordGrid wordGrid = new WordGrid();
    Game(Statistics s, int GameMode) {
        Stats = s;
        Button = new PlayerType[gridDim][gridDim];
        Players = new PlayerType[GameMode + 1];
        // initialize it
        //wordGrid = new WordGrid();
        ResetBoard();

        Players[0] = PlayerType.PLAYERONE;
        CurrentTurn = PlayerType.NOPLAYER;
        for (int i = 0; i < GameMode; i++) {
            Players[i + 1] = PlayerType.NOPLAYER;
        } 

        // Shown to the user, 0 is XPLAYER
        // 1 is OPLAYER
        Msg = new String[(GameMode + 1)];
        Msg[0] = "Waiting for other player to join";
        for (int i = 0; i < GameMode; i++) {
            Msg[i + 1] = "";
        }
    }

    //Annotate board method
    public void Annotate_Board(){
        
    }

    public void ResetBoard() {
        // initializes the board to NOPLAYER in all spots
        for (int i = 0; i < gridDim; i++) {
            for (int j = 0; j < gridDim; j++) {
                Button[i][j] = PlayerType.NOPLAYER;
            }
        }
    }

    //might remove this
    public int OpenSpots() {
        // counts the number of spots that neither
        // O or X has taken.
        int count = 0;
        for (PlayerType[] i : Button) {
            for (PlayerType j : i) {
                if (j == PlayerType.NOPLAYER) {
                    count++;
                }
            }
        }
        return count;
    }

    public void StartGame() {
        // X player goes first. Because that is how it is.
        Msg[0] = "You are Player 1. Your turn";
        for (int i = 0; i < GameMode; i++) {
            Msg[i + 1] = "You are Player " + (GameMode + 1) + ". Other players turn";
        }
        CurrentTurn = PlayerType.PLAYERONE;
        Stats.setGamesInProgress(Stats.getGamesInProgress() + 1);
    }

    // private boolean CheckLine(int i, int j, int k, PlayerType player) {
    //     // Checks to see if 3 squares are the same player
    //     return player == Button[i] && player == Button[j] && player == Button[k];
    // }

    // private boolean CheckHorizontal(PlayerType player) {
    //     //would need to change the requirements for this function but keeping it the same as tic tac toe for now
    //     return CheckLine(0, 1, 2, player) || CheckLine(3, 4, 5, player) || CheckLine(6, 7, 8, player);
    // }
    
    // private boolean CheckVertical(PlayerType player) {
    //     //would need to change the requirements for this function but keeping it the same as tic tac toe for now
    //     return CheckLine(0, 3, 6, player) || CheckLine(1, 4, 7, player) || CheckLine(2, 5, 8, player);
    // }
    
    // private boolean CheckDiagonal(PlayerType player) {
    //     //would need to change the requirements for this function but keeping it the same as tic tac toe for now
    //     return CheckLine(0, 4, 8, player) || CheckLine(2, 4, 6, player);
    // }
    
    // public boolean CheckBoard(PlayerType player) {
    //     //would need to change the requirements for this function but keeping it the same as tic tac toe for now
    //     return CheckHorizontal(player) || CheckVertical(player) || CheckDiagonal(player);
    // }

    // public boolean CheckDraw(PlayerType player) {
    //     // It is a draw if neither player has won.
    //     boolean retval = false;

    //     // More specifically, it is a draw if no-one has won, and there
    //     // are not spots that have not been taken.
    //     if (OpenSpots() == 0 && !(CheckBoard(uta.cse3310.PlayerType.PLAYERTWO)
    //             || CheckBoard(uta.cse3310.PlayerType.PLAYERONE))) {
    //         retval = true;
    //     }

    //     return retval;
    // }

    // This function returns an index for each player
    // It does not depend on the representation of Enums
    public int PlayerToIdx(PlayerType P) {
        int retval = 0;
        if (P == PlayerType.PLAYERONE) {
            retval = 0;
        } else if (P == PlayerType.PLAYERTWO) {
            retval = 1;
        } else if (P == PlayerType.PLAYERTHREE) {
            retval = 2;
        } else if (P == PlayerType.PLAYERFOUR) {
            retval = 3;
        }
        return retval;
    }

    // public void Update(UserEvent U) {
    //     // System.out.println("The user event is " + U.PlayerIdx + " " + U.Button);

    //     if ((CurrentTurn == U.PlayerIdx) && (CurrentTurn == PlayerType.PLAYERONE || CurrentTurn == PlayerType.PLAYERTWO|| CurrentTurn == PlayerType.PLAYERTHREE || CurrentTurn == PlayerType.PLAYERFOUR)) {
    //         // Move is legitimate, lets do what was requested

    //         // Note that a button is going to be set for every UserEvent !

    //         // Is the button not taken by X or O?
    //         if (Button[U.Button] == PlayerType.NOPLAYER) {
    //             // System.out.println("the button was 0, setting it to" + U.PlayerIdx);
    //             Button[U.Button] = U.PlayerIdx;
    //             if (U.PlayerIdx == PlayerType.PLAYERTWO) {
    //                 CurrentTurn = PlayerType.PLAYERONE;
    //                 Msg[1] = "Other Players Move.";
    //                 Msg[0] = "Your Move.";
    //             } else {
    //                 CurrentTurn = PlayerType.PLAYERONE;
    //                 Msg[0] = "Other Players Move.";
    //                 Msg[1] = "Your Move.";
    //             }
    //         } else {
    //             Msg[PlayerToIdx(U.PlayerIdx)] = "Not a legal move.";
    //         }

    //         // Check for winners, losers, and a draw

    //         if (CheckBoard(PlayerType.PLAYERONE)) {
    //             Msg[0] = "You Win!";
    //             Msg[1] = "You Lose!";
    //             CurrentTurn = PlayerType.NOPLAYER;
    //             Stats.setPlayer1Wins(Stats.getPlayer1Wins() + 1); 
    //             Stats.setGamesInProgress(Stats.getGamesInProgress() - 1); 
    //         } else if (CheckBoard(PlayerType.PLAYERTWO)) {
    //             Msg[1] = "You Win!";
    //             Msg[0] = "You Lose!";
    //             CurrentTurn = PlayerType.NOPLAYER;
    //             Stats.setPlayer2Wins(Stats.getPlayer2Wins() + 1);
    //             Stats.setGamesInProgress(Stats.getGamesInProgress() - 1); 
    //         } else if (CheckBoard(PlayerType.PLAYERTHREE)) {
    //             Msg[2] = "You Win!";
    //             Msg[3] = "You Lose!";
    //             CurrentTurn = PlayerType.NOPLAYER;
    //             Stats.setPlayer3Wins(Stats.getPlayer3Wins() + 1); 
    //             Stats.setGamesInProgress(Stats.getGamesInProgress() - 1); 
    //         } else if (CheckBoard(PlayerType.PLAYERFOUR)) {
    //             Msg[3] = "You Win!";
    //             Msg[2] = "You Lose!";
    //             CurrentTurn = PlayerType.NOPLAYER;
    //             Stats.setPlayer4Wins(Stats.getPlayer4Wins() + 1); 
    //             Stats.setGamesInProgress(Stats.getGamesInProgress() - 1); 
                
    //         } else if (CheckDraw(U.PlayerIdx)) {
    //             Msg[0] = "Draw";
    //             Msg[1] = "Draw";
    //             Msg[2] = "Draw";
    //             Msg[3] = "Draw";
    //             CurrentTurn = PlayerType.NOPLAYER;
    //             Stats.setDraws(Stats.getDraws() + 1); 
    //             Stats.setGamesInProgress(Stats.getGamesInProgress() - 1); 
            
    //         }
                
    //     }
    // }
    
//*************** Method to check words in the grid and award points based on word length ******************************
public int checkAndAwardPoints(String[][] shownGrid, int[][] coordinatesList) {
    int totalPoints = 0;

    // Iterate through the entire grid
    for (int i = 0; i < shownGrid.length; i++) {
        for (int j = 0; j < shownGrid[i].length; j++) {
            // Check if a word is present at the current cell
            if (!shownGrid[i][j].equals("*")) {
                String word = shownGrid[i][j];
                // Get length of word
                int wordLength = word.length();
                // Calculate the points based on word length
                int points = calculatePoints(wordLength);
                // Add points to total
                totalPoints += points;
            }
        }
    }
    // Return total points
    return totalPoints;
}

//***********  Method to set points based on word length ***************
private int calculatePoints(int wordLength) {
    return wordLength * 5;
}
//********************************************************************************

public String Leader_board() {
    return Stats.leaderBoard();
}

    public String[] Messaging(){
        //will return a string array
        String return_value[] = {};
        return return_value;
    }

}
