package uta.cse3310;

public class Game {

    PlayerType Players;
    public PlayerType CurrentTurn;
    public PlayerType[] Button;
    // Buttons are indexed 0 to 8 in the code
    // 0 1 2
    // 3 4 5
    // 6 7 8

    public String[] Msg;
    public int GameId;
    public Statistics Stats;

    Game(Statistics s) {
        Stats = s;
        Button = new PlayerType[9];
        // initialize it
        ResetBoard();

        Players = PlayerType.XPLAYER;
        CurrentTurn = PlayerType.NOPLAYER;
        // Shown to the user, 0 is XPLAYER
        // 1 is OPLAYER
        Msg = new String[2];
        Msg[0] = "Waiting for other player to join";
        Msg[1] = "";
    }

    public void ResetBoard() {
        // initializes the board to NOPLAYER in all spots
        for (int i = 0; i < Button.length; i++) {
            Button[i] = PlayerType.NOPLAYER;
        }
    }

    public int OpenSpots() {
        // counts the number of spots that neither
        // O or X has taken.
        int count = 0;
        for (PlayerType i : Button) {
            if (i == PlayerType.NOPLAYER) {
                count++;
            }
        }
        return count;
    }

    public void PrintGame() {
        // this method is used for debugging only
        // sometimes you want to see a picture of what is going on
        System.out.println(Button[0].toString() + " " + Button[1].toString() + " " + Button[2].toString());
        System.out.println(Button[3].toString() + " " + Button[4].toString() + " " + Button[5].toString());
        System.out.println(Button[6].toString() + " " + Button[7].toString() + " " + Button[8].toString());
    }

    public void SetBoard(PlayerType p, int[] b) {
        // this method is only used for testing purposes
        // p is the player to give the square to, and b
        // is an array of button numbers
        for (int i : b) {
            Button[i] = p;
        }

    }

    public void StartGame() {
        // X player goes first. Because that is how it is.
        Msg[0] = "You are X. Your turn";
        Msg[1] = "You are O. Other players turn";
        CurrentTurn = PlayerType.XPLAYER;
        Stats.setGamesInProgress(Stats.getGamesInProgress() + 1);
    }

    private boolean CheckLine(int i, int j, int k, PlayerType player) {
        // Checks to see if 3 squares are the same player
        return player == Button[i] && player == Button[j] && player == Button[k];
    }

    private boolean CheckHorizontal(PlayerType player) {
        return CheckLine(0, 1, 2, player) || CheckLine(3, 4, 5, player) || CheckLine(6, 7, 8, player);
    }

    private boolean CheckVertical(PlayerType player) {
        return CheckLine(0, 3, 6, player) || CheckLine(1, 4, 7, player) || CheckLine(2, 5, 8, player);
    }

    private boolean CheckDiagonal(PlayerType player) {
        return CheckLine(0, 4, 8, player) || CheckLine(2, 4, 6, player);
    }

    public boolean CheckBoard(PlayerType player) {
        return CheckHorizontal(player) || CheckVertical(player) || CheckDiagonal(player);
    }

    public boolean CheckDraw(PlayerType player) {
        // It is a draw if neither player has won.
        boolean retval = false;

        // More specifically, it is a draw if no-one has won, and there
        // are not spots that have not been taken.
        if (OpenSpots() == 0 && !(CheckBoard(uta.cse3310.PlayerType.OPLAYER)
                || CheckBoard(uta.cse3310.PlayerType.XPLAYER))) {
            retval = true;
        }

        return retval;
    }

    // This function returns an index for each player
    // It does not depend on the representation of Enums
    public int PlayerToIdx(PlayerType P) {
        int retval = 0;
        if (P == PlayerType.XPLAYER) {
            retval = 0;
        } else {
            retval = 1;
        }
        return retval;
    }

    public void Update(UserEvent U) {
        // System.out.println("The user event is " + U.PlayerIdx + " " + U.Button);

        if ((CurrentTurn == U.PlayerIdx) && (CurrentTurn == PlayerType.OPLAYER || CurrentTurn == PlayerType.XPLAYER)) {
            // Move is legitimate, lets do what was requested

            // Note that a button is going to be set for every UserEvent !

            // Is the button not taken by X or O?
            if (Button[U.Button] == PlayerType.NOPLAYER) {
                // System.out.println("the button was 0, setting it to" + U.PlayerIdx);
                Button[U.Button] = U.PlayerIdx;
                if (U.PlayerIdx == PlayerType.OPLAYER) {
                    CurrentTurn = PlayerType.XPLAYER;
                    Msg[1] = "Other Players Move.";
                    Msg[0] = "Your Move.";
                } else {
                    CurrentTurn = PlayerType.OPLAYER;
                    Msg[0] = "Other Players Move.";
                    Msg[1] = "Your Move.";
                }
            } else {
                Msg[PlayerToIdx(U.PlayerIdx)] = "Not a legal move.";
            }

            // Check for winners, losers, and a draw

            if (CheckBoard(PlayerType.XPLAYER)) {
                Msg[0] = "You Win!";
                Msg[1] = "You Lose!";
                CurrentTurn = PlayerType.NOPLAYER;
                Stats.setXWins(Stats.getXWins() + 1);
                Stats.setGamesInProgress(Stats.getGamesInProgress() - 1);
                Stats.setTotalGames(Stats.getTotalGames() + 1);
            } else if (CheckBoard(PlayerType.OPLAYER)) {
                Msg[1] = "You Win!";
                Msg[0] = "You Lose!";
                CurrentTurn = PlayerType.NOPLAYER;
                Stats.setOWins(Stats.getOWins() + 1);
                Stats.setGamesInProgress(Stats.getGamesInProgress() - 1);
                Stats.setTotalGames(Stats.getTotalGames() + 1);
            } else if (CheckDraw(U.PlayerIdx)) {
                Msg[0] = "Draw";
                Msg[1] = "Draw";
                CurrentTurn = PlayerType.NOPLAYER;
                Stats.setDraws(Stats.getDraws() + 1);
                Stats.setGamesInProgress(Stats.getGamesInProgress() - 1);
                Stats.setTotalGames(Stats.getTotalGames() + 1);
            }
        }
    }

    public void Tick() {
        // this function can be called periodically if a
        // timer is needed.

    }
}
// In windows, shift-alt-F formats the source code
// In linux, it is ctrl-shift-I