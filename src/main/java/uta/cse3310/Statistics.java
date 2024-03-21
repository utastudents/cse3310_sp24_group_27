package uta.cse3310;

public class Statistics {
    // this class stores global statistics of the
    // program

    /*
     * Any methods that have just return 0 in them aren't final products and I have just implemented the skelatons/frameworks as of right now. 
     * Will add the real code as project progresses.
     */
    //Keeping these fields
    private Integer player1Wins;
    private Integer player2Wins;
    private Integer Draws;
    private Integer GamesInProgress;
    //The new fields are below this 
    private Integer totalScore;
    private Integer playerRank;

    public Statistics() {
        player1Wins = 0;
        player2Wins = 0;
        Draws = 0;
        GamesInProgress = 0;
    }
    public Integer Leader_board(){
        return 0;
    }
    public Integer getPlayer1Wins() {
        return player1Wins;
    }

    public void setPlayer1Wins(Integer player1Wins) {
        player1Wins = player1Wins;
    }

    public Integer getPlayer2Wins() {
        return player2Wins;
    }

    public void setPlayer2Wins(Integer player2Wins) {
        player2Wins = player2Wins;
    }

    public Integer getDraws() {
        return Draws;
    }

    public void setDraws(Integer draws) {
        Draws = draws;
    }


    public Integer getGamesInProgress() {
        return GamesInProgress;
    }

    public void setGamesInProgress(Integer gamesInProgress) {
        gamesInProgress = gamesInProgress;
    }

    public Integer getplayerRank(){
        return 0;
    }
    public Integer setplayerRank(Integer playerRank){
        return 0;
    }

    public Integer gettotalScore(){
        return 0;
    }

    public Integer settotalScore(){
        return 0;
    }

}
