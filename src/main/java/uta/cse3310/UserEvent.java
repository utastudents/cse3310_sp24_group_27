package uta.cse3310;


public class UserEvent {
    int GameId; // the game ID on the server
    int GameMode;
    PlayerType PlayerIdx; // either an XPLAYER or an OPLAYER
    int Button; // button number from 0 to 8
    String type; // The chat message 
    String text;
    String username;
    int[][] coordinates; // The coordinates of the word found

    // Constructor for chat messages
    public UserEvent(String _Type, String _Text, String _Username) {
        type = _Type;
        text = _Text;
        username = _Username;
    }

    // Your existing constructor for other types of events
    public UserEvent(int _GameId, int _GameMode, PlayerType _PlayerIdx, int _Button, String _Type, String _Text, String _Username) {
        GameId = _GameId;
        GameMode = _GameMode;
        PlayerIdx = _PlayerIdx;
        Button = _Button;
        type = _Type;
        text = _Text;
        username = _Username;
    }

    //constructor for word selection
    public UserEvent(String _Type, int[][] Coordinates, String _Username) {
        type = _Type;
        coordinates = Coordinates;
        username = _Username;
    }

    public UserEvent(int i, PlayerType playerone, int j, int k) {
        
    }
}

