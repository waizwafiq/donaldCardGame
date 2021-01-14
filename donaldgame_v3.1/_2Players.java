package donaldgame_v4;

import java.util.Scanner;
import java.util.Random;
public class _2Players extends DonaldGame {
    private static int playerMax = 4;
    private static Player player[] = new Player[playerMax];
    private static Scanner input = new Scanner(System.in);
    private static Random rand = new Random();
    private Team[] team = new Team[2];

    public void play() {
        /*PLAY DONALD CARD GAME WITH 4 PLAYERS GAME MODE*/
        chooseDifficulty();
        getNames(); //prompt the users to get the names
        Card.fetchCard(); //fetch card deck from carddeck.txt
        Card.shuffleCards(); //shuffle the card deck

        /*Assign cards to each player*/
        System.out.println("\n\nAssigning cards to the player...");
        for (int i = 0; i < playerMax; i++)
            Card.assignCards(player[i], i * 13);
        System.out.println("Assigned!");

        for(int i=0; i<2; i++) {
            checkCards(player[i]);
            callDonald(player, i);
        }

        for (int i = 2; i < playerMax; i++) {
            /*Meaning:
             *For each player i,*/
            callDonaldComp(player, i);
        }

        player[getDonaldIdx()].isPlayerDonald = true;
        isDonaldPlayed = true;
        donaldPlayer = player[getDonaldIdx()].getName();

        if(getDonaldIdx()==0 || getDonaldIdx()==1)
            formTeam(team);
        else
            compFormTeam(team);
        //IN A ROUND:
        do {
            //LOOP FOR ALL PLAYERS TO PLAY
            setFirstPlayer(player[getWinnerPlayerIdx()]);
            boolean flag1 = player[getWinnerPlayerIdx()].getName().equalsIgnoreCase(player[1].getName());
            boolean flag2 = player[getWinnerPlayerIdx()].getName().equalsIgnoreCase("Computer 2");
            boolean flag3 = player[getWinnerPlayerIdx()].getName().equalsIgnoreCase("Computer 3");

            if(flag1){
                playerTurn(player[1], team);
                playerTurn(player[0], team);
                compTurn(player[2], team);
                compTurn(player[3], team);
            }else if(flag2){
                compTurn(player[2], team);
                playerTurn(player[0], team);
                playerTurn(player[1], team);
                compTurn(player[3], team);
            }else if(flag3){
                compTurn(player[3], team);
                playerTurn(player[0], team);
                playerTurn(player[1], team);
                compTurn(player[2], team);
            }else{
                playerTurn(player[0], team);
                playerTurn(player[1], team);
                compTurn(player[2], team);
                compTurn(player[3], team);
            }

            //FIND THE WINNER OF THE ROUND
            findRoundWinner(player);
            if(team[0].hasMember(player[getWinnerPlayerIdx()])){
                team[0].setTeamScore();
            }else {
                team[1].setTeamScore();
            }

        }while(!team[0].isWinGame() && !team[1].isWinGame());

        if(team[0].isWinGame()) System.out.println("Team 1 wins!");
        else System.out.println("Team 2 wins!");
    }


    /*------------------------------------------------------------------------------------------------------------------------*/


    /*GET NAMES*/
    public static void getNames(){
        /*Prompt the players to input their names.
         * */

        for(int i=0; i<2;i++) {
            System.out.printf("Player %d, please enter your name: ", (i + 1));
            player[i] = new Player(input.nextLine());
        }
        player[2]= new Player("Computer 2");
        player[3]= new Player("Computer 3");

    }

    /*FORM TEAMS
     * The donald player will choose a teammate by
     * entering the "Color Rank" of someone's card*/
    public void formTeam(Team[] team){
        for(int donald=0; donald<playerMax; donald++){
            if(player[donald].isPlayerDonald){
                System.out.println();
                System.out.println(player[donald].getName()+" would be the donald");
                System.out.println(player[donald].getName()+", choose your teammate");
                System.out.println("By entering \"Color Rank\" of someone's card");
                boolean repeat = true;
                do {
                    int teammateIndex = findByCards(player, input.nextLine());
                    if(teammateIndex<0){
                        System.out.println("Please enter the color rank correctly![e.g: RED 4]");
                        repeat = true;
                    }else if(teammateIndex==donald){
                        System.out.println("You cannot choose yourself as your teammate!");
                        repeat = true;
                    }else{
                        /*IF NO ERRORS OCCUR:
                         * put the members into a team class*/
                        System.out.println();
                        team[0] = new Team(player[donald], player[teammateIndex]);
                        System.out.println("Team 1: "+player[donald].getName()+" and "+player[teammateIndex].getName());


                        Player nextTeamPlayers[] = new Player[2];
                        for(int i=0,j=0; i<playerMax; i++) {
                            if(i!=donald && i!=teammateIndex){
                                nextTeamPlayers[j++] = player[i];
                            }
                        }
                        team[1] = new Team(nextTeamPlayers[0], nextTeamPlayers[1]);
                        System.out.println("Team 2: "+nextTeamPlayers[0].getName()+" and "+nextTeamPlayers[1].getName());
                        team[0].showWinCriteria(1);
                        team[1].showWinCriteria(2);
                        System.out.println("--------------------------------------------------------------------------");
                        repeat = false;
                    }
                }while(repeat);
            }
        }
    }


    public void compFormTeam(Team[] team){
        for(int donald=1; donald<playerMax; donald++){
            if(player[donald].isPlayerDonald){
                System.out.println();
                System.out.println(player[donald].getName()+" would be the donald");
                boolean repeat = true;
                do {
                    int teammateIndex = rand.nextInt(3)+1; //1 to 3
                    if(teammateIndex==donald){
                        repeat = true;
                    }else{
                        /*IF NO ERRORS OCCUR:
                         * put the members into a team class*/
                        System.out.println();
                        team[0] = new Team(player[donald], player[teammateIndex]);
                        System.out.println("Team 1: "+player[donald].getName()+" and "+player[teammateIndex].getName());


                        Player nextTeamPlayers[] = new Player[2];
                        for(int i=0,j=0; i<playerMax; i++) {
                            if(i!=donald && i!=teammateIndex){
                                nextTeamPlayers[j++] = player[i];
                            }
                        }
                        team[1] = new Team(nextTeamPlayers[0], nextTeamPlayers[1]);
                        System.out.println("Team 2: "+nextTeamPlayers[0].getName()+" and "+nextTeamPlayers[1].getName());
                        team[0].showWinCriteria(1);
                        team[1].showWinCriteria(2);
                        System.out.println("--------------------------------------------------------------------------");
                        repeat = false;
                    }
                }while(repeat);
            }
        }
    }
}
