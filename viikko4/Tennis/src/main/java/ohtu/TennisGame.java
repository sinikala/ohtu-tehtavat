package ohtu;

public class TennisGame {

    private int scorePlayer1 = 0;
    private int scorePlayer2 = 0;
    private String player1;
    private String player2;
    String[] printouts;

    public TennisGame(String player1Name, String player2Name) {
        this.player1 = player1Name;
        this.player2 = player2Name;
        this.printouts = new String[]{"Love", "Fifteen", "Thirty", "Forty"};
    }

    public void wonPoint(String playerName) {
        if (playerName == "player1")
            scorePlayer1++;
        else
            scorePlayer2++;
    }

    public String getScore() {
        if (scorePlayer1 == scorePlayer2) {
            return returnEvenScore();
        } else if (scorePlayer1 >= 4 || scorePlayer2 >= 4) {
            return returnBreakPoint();
        }
        return returnUnEvenScore();

    }

    public String returnBreakPoint() {
        if (scorePlayer1 > scorePlayer2) {
            if (scorePlayer1 - scorePlayer2 > 1) {
                return "Win for player1";
            }
            return "Advantage player1";
        }
        if (scorePlayer2 - scorePlayer1 > 1) {
            return "Win for player2";
        }
        return "Advantage player2";

    }

    public String returnEvenScore() {
        if (scorePlayer2 < 4) {
            return printouts[scorePlayer2] + "-All";
        }
        return "Deuce";
    }

    public String returnUnEvenScore() {
        return printouts[scorePlayer1] + "-" + printouts[scorePlayer2];
    }
}