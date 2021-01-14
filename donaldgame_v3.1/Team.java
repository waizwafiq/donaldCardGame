package donaldgame_v4;

public class Team extends DonaldGame{
    private int teamScore;
    private Player p1,p2;

    public Team(Player p1, Player p2) {
        teamScore = 0;

        this.p1 = p1;
        this.p2 = p2;
    }

    public void showWinCriteria(int teamNumber){
        if(isDonaldTeam())
            System.out.println("Team "+teamNumber+" winning criteria: 6 + "+donaldNumber+" = "+(6+donaldNumber));
        else
            System.out.println("Team "+teamNumber+" winning criteria: 8 - "+donaldNumber+" = "+(8-donaldNumber));
    }

/*Set & Display  Team Score = player1's score + player2's score*/
    public void setTeamScore(){
        teamScore = p1.getScore() + p2.getScore();
    }

    public void showTeamScore(){
        System.out.println(
                p1.getScore()+" + "+
                p2.getScore()+" = "+
                teamScore);
    }

/*Determine whether the team is the donald team*/
    public boolean isDonaldTeam(){
        return p1.isPlayerDonald || p2.isPlayerDonald;
    }

/*Determines whether the team has won the game*/
    public boolean isWinGame(){
        if(isDonaldTeam())
            return (teamScore == 6+donaldNumber);
        else
            return (teamScore == 8-donaldNumber);
    }


    public boolean hasMember(Player player){
        return p1 == player || p2 == player;
    }
}
