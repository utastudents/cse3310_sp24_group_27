package uta.cse3310;


public class UserEvent {
    int GameId; // the game ID on the server
    PlayerType PlayerIdx; // either an XPLAYER or an OPLAYER
    int numplayers;
    int Button; // button number from 0 to 8
    String type; // The chat message 
    String text;
    String username;
    int useridx;
    int[][] coordinates; // The coordinates of the word found

    // Constructor for chat messages
    public UserEvent(String _Type, String _Text, String _Username) {
        type = _Type;
        text = _Text;
        username = _Username;
    }

    // Your existing constructor for other types of events
    public UserEvent(int _GameId, PlayerType _PlayerIdx, int _Button, String _Type, String _Text, String _Username) {
        GameId = _GameId;
        PlayerIdx = _PlayerIdx;
        Button = _Button;
        type = _Type;
        text = _Text;
        username = _Username;
    }

    //constructor for word selection
    public UserEvent(int _GameId, String _Type, int[][] _Coordinates, String _Text, String _Username, int _UserIdx) {
        GameId = _GameId;
        type = _Type;
        text = _Text;
        coordinates = _Coordinates;
        username = _Username;
        useridx = _UserIdx;
    }

    public UserEvent(int _NumPlayers, String _Type, String _Username) {
        numplayers = _NumPlayers;
        type = _Type;
        username = _Username;
    }

    public UserEvent(int i, PlayerType playerone, int j, int k) {
        
    }
}

