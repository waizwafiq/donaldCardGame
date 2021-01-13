package donaldgame_v3;

public class Player extends Card{
    protected String name;
    protected int score;
    protected boolean isPlayerDonald;
    protected String[] assignedCards;
    protected String[] playedCards = {""};

    public Player(String name) {
        this.name = name;
        score = 0;
        isPlayerDonald = false;
    }

    public void checkCardOnPlayer(){
        /*Checks player's hand cards*/
        for(int i=0; i<assignedCards.length; i++)
            System.out.print(assignedCards[i]+" | ");
        System.out.println();
    }

    public void addScore(){
        /*Adds a score to the player*/
        score++;
    }

/*ACCESSOR METHODS*/
    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public String[] getAssignedCards() {
        return assignedCards;
    }

    public String[] getPlayedCards() { return playedCards; }


/*MUTATOR METHODS*/
    public void setAssignedCards(String[] assignedCards) {
        /*To be called in assignCards() method in Card.java*/
        /*Can be used to delete playedCards*/
        this.assignedCards = assignedCards;
    }

    public void setPlayedCards(String[] playedCards) {
        this.playedCards = playedCards;
    }
}
