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
    private Integer player3Wins;
    private Integer player4Wins;
    private Integer Draws;
    private Integer GamesInProgress;
    //The new fields are below this 
    private Integer totalScore;
    private Integer player1Rank;
    private Integer player2Rank;
    private Integer player3Rank;
    private Integer player4Rank;



    public Statistics() {
        player1Wins = 0;
        player2Wins = 0;
        player3Wins = 0;
        player4wins = 0;
        Draws = 0;
        GamesInProgress = 0;
        player1Rank = 0;
        player2Rank = 0;
        player3Rank = 0;
        player4Rank = 0;
        totalScore = 0;
    }
    public Integer Leader_board(){
        return 0;
    }
    public Integer getPlayer1Wins() {
        return player1Wins;
    }

    public void setPlayer1Wins(Integer player1Wins) {
        this.player1Wins = player1Wins;
    }

    public Integer getPlayer2Wins() {
        return player2Wins;
    }

    public void setPlayer2Wins(Integer player2Wins) {
        this.player2Wins = player2Wins;
    }

    public Integer getPlayer3Wins() {
        return player3Wins;
    }

    public void setPlayer3Wins(Integer player3Wins) {
        this.player3Wins = player3Wins;
    }

    public Integer getPlayer4Wins() {
        return player4Wins;
    }

    public void setPlayer4Wins(Integer player4Wins) {
        this.player4Wins = player4Wins;
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
        this.gamesInProgress = gamesInProgress;
    }

    public Integer getPlayerRank(PlayerType player) {
        switch (player) {
            case PLAYERONE:
                return player1Rank;
            case PLAYERTWO:
                return player2Rank;
            case PLAYERTHREE:
                return player3Rank;
            case PLAYERFOUR:
                return player4Rank;
            default:
                return 0;
        }
    }

    private void setPlayerRank(PlayerType player, Integer rank) {
        switch (player) {
            case PLAYERONE:
                player1Rank = rank;
                break;
            case PLAYERTWO:
                player2Rank = rank;
                break;
            case PLAYERTHREE:
                player3Rank = rank;
                break;
            case PLAYERFOUR:
                player4Rank = rank;
                break;
            case NOPLAYER:
                break;
        }
    }

    public Integer gettotalScore(){
        return totalScore;
    }

    public void settotalScore(Integer totalScore){
        this.totalScore = totalScore;
    }

 //** new methods **   
    public void updateTotalScore(int points) {
        totalScore += points;
        calculatePlayerRanks();
    }

    public Integer checkAndAwardPoints(String[][] shownGrid, int[][] coordinatesList) {
        return checkAndAwardPoint;
    }

    private void calculatePlayerRanks() {
        Map<PlayerType, Integer> playerRanks = new HashMap<>();
        playerRanks.put(PlayerType.PLAYERONE, getPlayer1Wins());
        playerRanks.put(PlayerType.PLAYERTWO, getPlayer2Wins());
        playerRanks.put(PlayerType.PLAYERTHREE, getPlayer3Wins());
        playerRanks.put(PlayerType.PLAYERFOUR, getPlayer4Wins());

        // Sort by values(total points)
        Map<PlayerType, Integer> sortedPlayerRanks = playerRanks.entrySet().stream()
                .sorted(Map.Entry.<PlayerType, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, HashMap::new));

        // Update player ranks
        int rank = 1;
        for (PlayerType player : sortedPlayerRanks.keySet()) {
            setPlayerRank(player, rank);
            rank++;
        }
    }
    public String leaderBoard() {
        StringBuilder leaderboard = new StringBuilder();
        leaderboard.append("Leaderboard:\n");
        leaderboard.append("Player\tWins\tRank\n");
        leaderboard.append("Player 1\t").append(player1Wins).append("\t").append(player1Rank).append("\n");
        leaderboard.append("Player 2\t").append(player2Wins).append("\t").append(player2Rank).append("\n");
        leaderboard.append("Player 3\t").append(player3Wins).append("\t").append(player3Rank).append("\n");
        leaderboard.append("Player 4\t").append(player4Wins).append("\t").append(player4Rank).append("\n");
        return leaderboard.toString();
    }
}
 