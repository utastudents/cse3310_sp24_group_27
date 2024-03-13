package uta.cse3310;

public class Statistics {
    // this class stores global statistics of the
    // program
    private Long RunningTime;
    private Integer XWins;
    private Integer OWins;
    private Integer Draws;
    private Integer TotalGames;
    private Integer GamesInProgress;

    public Statistics() {
        RunningTime = 0L;
        XWins = 0;
        OWins = 0;
        Draws = 0;
        TotalGames = 0;
        GamesInProgress = 0;
    }

    public Long getRunningTime() {
        return RunningTime;
    }

    public void setRunningTime(Long runningTime) {
        RunningTime = runningTime;
    }

    public Integer getXWins() {
        return XWins;
    }

    public void setXWins(Integer xWins) {
        XWins = xWins;
    }

    public Integer getOWins() {
        return OWins;
    }

    public void setOWins(Integer oWins) {
        OWins = oWins;
    }

    public Integer getDraws() {
        return Draws;
    }

    public void setDraws(Integer draws) {
        Draws = draws;
    }

    public Integer getTotalGames() {
        return TotalGames;
    }

    public void setTotalGames(Integer totalGames) {
        TotalGames = totalGames;
    }

    public Integer getGamesInProgress() {
        return GamesInProgress;
    }

    public void setGamesInProgress(Integer gamesInProgress) {
        GamesInProgress = gamesInProgress;
    }

}
