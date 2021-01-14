package donaldgame_v4;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;
public class DonaldGame extends Card {

    protected static int donaldNumber, difficultyLevel;
    protected String donaldColor = "";
    protected boolean isDonaldActivated;
    protected boolean isDonaldPlayed = false;
    private int donaldIdx;
    protected String donaldPlayer;

    private static Scanner input = new Scanner(System.in);
    private int colorPriorityValue=0, numberPriorityValue=0;
    private String[] ifAllNoFlags = new String[4];

    public void callDonald(Player[] players, int i){

        boolean repeat;
        do{
            /*Do you want to call for donald?*/
            input.nextLine();
            System.out.print(players[i].getName() + " call for donald[Y/N] ");
            String opt = input.nextLine();
            ifAllNoFlags[i] = opt; //if all players say no when calling for donald

            switch(opt){
                case "Y","y" -> { setDonaldNumber(players, i); repeat = false;}
                case "N","n" -> { repeat = false;}
                default -> {
                    System.out.println("Invalid Command!");
                    repeat = true;
                }
            }

        }while(repeat);
    }

/*TO SET DONALD CARD:
* ~~~~~~~~~~~setDonaldNumber()~~~~~~~~~~~
* 1. get donaldNumber from player
* 2. if donaldNumber ranks higher than or equal to the last one:
* 3.     assign the new donaldNumber
* ----------setDonaldColor()----------
* 4.     get donaldColor from player
* 5.     if donaldColor ranks higher than the last one:
* 6.        assign the new donaldColor
* 7.        find donaldPlayer (search card)
* 8.     else
* 9.        skip (dont alter donaldNumber value)
* -------------------------------------
*10. else
*11.     skip (dont alter both donaldColor and donaldNumber value)
* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/


    public void setDonaldNumber(Player[] player, int i) {
        /*Set Donald Card, this method contains setDonaldColor() method, so:
         * this method is to determine the donald color and number.*/
        boolean repeat = true;
        do {
            System.out.println("Enter the number of donald:[1-7] ");
            int enteredDonaldNumber = 0;

            try {
                enteredDonaldNumber = input.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Please enter a number between 1 to 7!");
                input.next();
            }

            if (enteredDonaldNumber >= 1 && enteredDonaldNumber <= 7) {
                //IF NO INPUT ERRORS

                if(enteredDonaldNumber > donaldNumber){ //if entered number > last num, RESET COLOR
                    donaldNumber = enteredDonaldNumber; //update the donaldNumber
                    colorPriorityValue=0; //RESET COLOR
                    input.nextLine();
                    setDonaldColor(player, i);
                }else if(enteredDonaldNumber == donaldNumber){
                    input.nextLine();
                    setDonaldColor(player,i);
                }
                repeat = false;
            } else {
                System.out.println("Number of donald is between 1 to 7.");
                repeat = true;
            }
        } while (repeat);
    }


    public void setDonaldColor(Player[] players, int i){
        /*Set Donald Color*/
        boolean repeat;
        int tempPriorityValue = 0;
    /* tempPriorityValue holds value 1 to 5:
     * if the entered color is a higher-ranked color,
     * donaldColor = [tempPriorityValue-1];
    */
        String[] colors = {"GREEN","BLUE","YELLOW","RED","NO DONALD"};
        do {
            //GET DONALD COLOR FROM PLAYER

            System.out.println("Enter the donald color:[RED/YELLOW/BLUE/GREEN/NO] ");
            String ans = input.nextLine();
            switch(ans){
                case "RED","red" -> {       repeat = false; tempPriorityValue=4;}
                case "YELLOW","yellow" -> { repeat = false; tempPriorityValue=3;}
                case "BLUE","blue" -> {     repeat = false; tempPriorityValue=2;}
                case "GREEN","green" -> {   repeat = false; tempPriorityValue=1;}
                case "NO","no" -> {         repeat = false; tempPriorityValue=5;}
                default -> {
                    System.out.println("Invalid command!");
                    repeat = true;
                    continue;
                }
            }

            if(tempPriorityValue>colorPriorityValue){
                //if donaldColor ranks higher than the last one
                colorPriorityValue = tempPriorityValue;
                donaldColor = colors[colorPriorityValue-1]; //assign the new higher-ranked color
                //setDonaldNumber(players, i);
                donaldIdx = i;
                repeat = false;
            }else if(tempPriorityValue==colorPriorityValue ){
                System.out.println("The card is the same! Choose another card");
                repeat = true;
                continue;
            }
        }while(repeat);

    }

/*Displays:
* >> Donald is played: true/false
* >> Donald number is: Donald number
* >> Donald color is : Donald color
* >> Donald player is: Donald player*/
    public void displayCardStats(){

        if (isDonaldPlayed) {
            System.out.println();
            System.out.println("Donald is played?: " + isDonaldActivated);
            System.out.println("Donald card is   : " + donaldColor + " " + donaldNumber);
            System.out.println("Donald player is : " + donaldPlayer);
        }else
            System.out.println("\nNo Donald is played!!");

    }

    private Player firstPlayer;

    public void setFirstPlayer(Player firstPlayer){
        this.firstPlayer = firstPlayer;
    }
    public void chooseDifficulty(){
        boolean repeat;
        System.out.println("###############################");
        System.out.println("Pick a difficulty:");
        System.out.println("(1) Baby Mode");
        System.out.println("(2) Hardcore");
        do {
            try {
                difficultyLevel = input.nextInt();
                repeat = false;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input!");
                input.next();
                repeat = true;
            }
        }while(repeat);
    }

    public void compTurn(Player player, Team[] teams){
        displayCardStats();
        checkCardsComp(player);

        compPlayCard(player);


        if(teams[0].hasMember(player))
            teams[0].showTeamScore();
        else
            teams[1].showTeamScore();
    }

    public void compPlayCard(Player player){
        String chosenCard;
        int chosenIndex=0;
        System.out.println("Which card do you want to play?");

        if(difficultyLevel == 1) {
            //baby mode  : randomly picks a card (dumb AI)
            chosenIndex = rand.nextInt(player.getAssignedCards().length);
        }else if(difficultyLevel==2){
            //hardcore mode: computer follow the rules completely (smart AI)
            int lastPlayedIdx = firstPlayer.playedCards.length -1;
            for(int i=0; i<player.getAssignedCards().length; i++){
                String[] split = player.getAssignedCards()[i].split(" ");
                String[] cmpSplit = firstPlayer.playedCards[lastPlayedIdx].split(" ");
                if(split[0].equals(cmpSplit[0]))//compare colors
                    chosenIndex = i; //select the card that is equal to the first player's color
                else if(split[0].equals(donaldColor) && isDonaldActivated==true)
                    chosenIndex = i; //select the card that is equal to the donaldColor (donald active)
                else if(split[0].equals(donaldColor) && isDonaldActivated==false && player.isPlayerDonald)
                    chosenIndex = i;
                else
                    chosenIndex = rand.nextInt(player.getAssignedCards().length); //random
            }
        }
        chosenCard = player.getAssignedCards()[chosenIndex];

        for (String eachCard : player.playedCards) { //for eachCard in playedCards[]

            //append chosenCard into player.playedCard[]
            //playedCards[] = playedCards[] + chosenCard;
            /*player.setPlayedCards(Card.removeACard(player.playedCards, ""));*/
            player.setPlayedCards(Card.appendCard(player.playedCards, chosenCard));

            //remove chosenCard from player.assignedCards[]
            //assignedCards[] = assignedCards[] - chosenCard;
            player.setAssignedCards(Card.removeACard(player.assignedCards, chosenCard));
            //print played card
            System.out.println(player.getName()+" has played "+chosenCard);
            boolean flag1 = player.isPlayerDonald;
            boolean flag2 = chosenCard.equals(donaldColor+" "+donaldNumber);

            if(flag1 && flag2) isDonaldActivated = true;
            break;
        }
    }
/*playerTurn():
* Displays:
*   (1) Arrange cards (sort in ascending order)
*       Card.arrangeCards();
*   (2) Play a card
*       playCard();
*   (3) Show score
*   (4) Show hand cards
* */
    public void playerTurn(Player player, Team[] teams){
        /*Player i's turn to play (can be used for computers)*/

        displayCardStats();

        boolean repeat;
        System.out.println();
        System.out.println(player.getName()+"'s turn:");
        System.out.println("Please enter your commands:\n"+
                "(1) Arrange cards\n"+
                "(2) Play a card\n"+
                "(3) Show score\n"+
                "(4) Show hand cards");

        do{

            int opt = 0;
            try{
                opt = input.nextInt();
            }catch(InputMismatchException e){
                System.out.println("Please enter a number between 1 to 4!");
                input.next();
            }

            switch(opt){
                case 1 -> {
                    //arrange card method here
                    Card.arrangeCards(player);
                    repeat = true;
                }
                case 2 -> {
                    //play card here
                    playCard(player);
                    repeat = false;
                }
                case 3 -> {
                    //show team score here
                    if(teams[0].hasMember(player))
                        teams[0].showTeamScore();
                    else
                        teams[1].showTeamScore();
                    repeat = true;
                }
                case 4 -> {
                    //check card in hand
                    player.checkCardOnPlayer();
                    repeat = true;
                }
                default ->{
                    System.out.println("Wrong command!\n");
                    repeat = true;
                }
            }
        }while(repeat);
    }

/*PLAY A CARD
(player: each/one player, not all players)
* 1) Prompt player to pick a card (the card must not be played before)
* 2) String chosenCard = player.getAssignedCards()[chosenIndex]
* 3) if(chosenCard has already been played):
*       //chosenCard is in player.playedCard[]
*       cannot play
* 4) else
*       append chosenCard into player.playedCard[]
*       compare Ranking
*       findWinner();*/
    public void playCard(Player player){
        boolean repeat = true;
        String chosenCard;
        do {
            System.out.println("Which card do you want to play? (Starting from 0)");


            int chosenIndex = -1;
            try {
                chosenIndex = input.nextInt(); //player choose a card by inputting card's index
            }catch (InputMismatchException e){ //catch error when player inputs other than integer
                System.out.println("Invalid command!");
                input.next();
            }

            if(chosenIndex<0 || chosenIndex>player.getAssignedCards().length){
                System.out.println("Please enter an integer between 0 to "+player.getAssignedCards().length);
                continue; //if conditions aren't fulfilled, keep asking player to pick a card
            } else
                chosenCard = player.getAssignedCards()[chosenIndex];


            for (String eachCard : player.playedCards) { //for eachCard in playedCards[]
                repeat = false;

                //append chosenCard into player.playedCard[]
                //playedCards[] = playedCards[] + chosenCard;
                /*player.setPlayedCards(Card.removeACard(player.playedCards, ""));*/
                player.setPlayedCards(Card.appendCard(player.playedCards, chosenCard));

                //remove chosenCard from player.assignedCards[]
                //assignedCards[] = assignedCards[] - chosenCard;
                player.setAssignedCards(Card.removeACard(player.assignedCards, chosenCard));
                //print played card
                System.out.println(player.getName()+" has played "+chosenCard);
                boolean flag1 = player.isPlayerDonald;
                //compare the color instead of the color and number
                String[] tempSplit = chosenCard.split(" ");
                boolean flag2 = tempSplit[0].equals(donaldColor);

                if(flag1 && flag2) isDonaldActivated = true;
                break;
            }

        }while(repeat);

    }

/*FIND ROUND WINNER*/
    private int winnerPlayerIdx=0;
    public void findRoundWinner(Player[] player) {
        int round = player[0].getPlayedCards().length; //get number of current round

        String cardColors[] = new String[4];
        String cardNumbers[] = new String[4];

        //SPLIT INTO COLORS AND NUMBERS ARRAY
        for (int i = 0; i < player.length; i++) {
            cardColors[i] = player[i].getPlayedCards()[round - 1].split(" ")[0];
            cardNumbers[i] = player[i].getPlayedCards()[round - 1].split(" ")[1];
        }

        compareColor(player, cardColors, cardNumbers);

        System.out.println(player[winnerPlayerIdx].getName()+" wins the round!");
        player[winnerPlayerIdx].addScore();
        System.out.println("-------------------------------------------------------------------------------");
    }

    public void compareColor(Player[] player, String[] cardColors, String[] cardNumbers){
        int tempColorPriorityValue=0;
        colorPriorityValue = 0;
        for(int i=0; i<player.length; i++){
            if (isDonaldActivated || (player[i].isPlayerDonald && cardColors[i].equals(donaldColor))) {
                if(cardColors[i].equals(donaldColor))           tempColorPriorityValue = 3;
                else if(cardColors[i].equals(cardColors[winnerPlayerIdx]))    tempColorPriorityValue = 2;
                else                                            tempColorPriorityValue = 1;

            } else {
                if(cardColors[i].equals(cardColors[winnerPlayerIdx])) tempColorPriorityValue = 2;
                else                                    tempColorPriorityValue = 1;
            }

            if(tempColorPriorityValue > colorPriorityValue){
                colorPriorityValue = tempColorPriorityValue;
                compareNumber(player, cardNumbers, i);
            }else if(tempColorPriorityValue == colorPriorityValue){
                compareNumber(player, cardNumbers, i);
            }
        }
    }

    public void compareNumber(Player[] player, String[] cardNumbers, int idx){
        int tempNumberPriorityValue=0;
        switch(cardNumbers[idx]){
            case "1" -> {tempNumberPriorityValue=1;  }
            case "2" -> {tempNumberPriorityValue=2;  }
            case "3" -> {tempNumberPriorityValue=3;  }
            case "4" -> {tempNumberPriorityValue=4;  }
            case "5" -> {tempNumberPriorityValue=5;  }
            case "6" -> {tempNumberPriorityValue=6;  }
            case "7" -> {tempNumberPriorityValue=7;  }
            case "8" -> {tempNumberPriorityValue=8;  }
            case "9" -> {tempNumberPriorityValue=9;  }
            case "10" ->{tempNumberPriorityValue=10; }
            case "A" -> {tempNumberPriorityValue=11; }
            case "B" -> {tempNumberPriorityValue=12; }
            case "C" -> {tempNumberPriorityValue=13; }
        }

        if(tempNumberPriorityValue > numberPriorityValue){
            numberPriorityValue = tempNumberPriorityValue;
            winnerPlayerIdx = idx;
        }
    }

    public void checkCardsComp(Player player){
        player.checkCardOnPlayer();
    }

    private static Random rand = new Random();
    public void callDonaldComp(Player[] player, int i){
        /*Set Donald Card, this method contains setDonaldColor() method, so:
         * this method is to determine the donald color and number.*/
        int enteredDonaldNumber = rand.nextInt(5)+1; //1-5
        System.out.println("\nDonald number called by "+player[i].getName()+":"+enteredDonaldNumber);
        if(enteredDonaldNumber >= donaldNumber){ //if entered number > last num, RESET COLOR
            donaldNumber = enteredDonaldNumber; //update the donaldNumber
            colorPriorityValue=0; //RESET COLOR

            compCallDonaldColor(player, i);
        }else if(enteredDonaldNumber == donaldNumber){
            compCallDonaldColor(player,i);
        }
    }

    public void compCallDonaldColor(Player[] player, int i){
        /*Set Donald Color*/
        /* tempPriorityValue holds value 1 to 5:
         * if the entered color is a higher-ranked color,
         * donaldColor = [tempPriorityValue-1];
         */
        boolean repeat=true;
        String[] colors = {"GREEN","BLUE","YELLOW","RED","NO DONALD"};
        do{
            int tempPriorityValue = rand.nextInt(5)+1;
            System.out.println("Donald number called by "+player[i].getName()+":"+colors[tempPriorityValue-1]);
            if(tempPriorityValue>colorPriorityValue){
                //if donaldColor ranks higher than the last one
                colorPriorityValue = tempPriorityValue;
                donaldColor = colors[colorPriorityValue-1]; //assign the new higher-ranked color
                //setDonaldNumber(players, i);
                donaldIdx = i;
                break;
            }else if(tempPriorityValue==colorPriorityValue ){
                System.out.println("The card is the same! Choose another card, computer!");
                repeat = true;
            }
        }while(repeat);


    }






/*ACCESSOR METHODS*/

    public int getDonaldIdx() {
        return donaldIdx;
    }

    public String[] getIfAllNoFlags() {
        return ifAllNoFlags;
    }

    public int getWinnerPlayerIdx() {
        return winnerPlayerIdx;
    }
}
