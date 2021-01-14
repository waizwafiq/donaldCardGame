package donaldgame_v4;

import java.io.*;
import java.util.Scanner;
import java.util.Random;

public class Card {
    private static final String path =
                    "carddeck.txt"; //insert your path here!!

    private static final int numberOfCardsInDeck = 52;
    private static String[] cards = new String[numberOfCardsInDeck];

    public static void fetchCard(){
        /*Fetches card deck from path (carddeck.txt)
        * and constructs them into cards[]*/
        int i = 0;
        try{
            Scanner inputText = new Scanner(new FileInputStream(path));
            System.out.println("Constructing the cards in the deck...");
            while(inputText.hasNextLine())
                cards[i++] = inputText.nextLine();
            System.out.println("52 cards has been constructed");
            inputText.close();
        }catch(FileNotFoundException e){
            System.out.println("File not found!");
        }
    }

    public static void shuffleCards(){
        /*Shuffles the cards[],
        * Random() and bubble sort algorithm are used to scramble the cards*/
        Random rand = new Random();
        System.out.println("Shuffling the card deck...");
        for(int i=0; i<numberOfCardsInDeck; i++){
            int randomPosition = rand.nextInt(numberOfCardsInDeck);
            String temp = cards[i];
            cards[i] = cards[randomPosition];
            cards[randomPosition] = temp;
        }
        System.out.println("Completed!");
    }

    public static void assignCards(Player player, int startAtCard){
        /*Assigns 13 shuffled cards[] into object player.
        *
        * params:
        * >> player: Object Player (can be computer)
        * >> startAtCard : the starting element of shuffled card deck to assign*/

        int numCardsToAssign = 13;
        String[] assignedCards = new String[numCardsToAssign];
        for(int i=0, j=startAtCard; i<numCardsToAssign; i++)
            assignedCards[i] = cards[j++];

        player.setAssignedCards(assignedCards);
    }

    public static void checkCards(Player player){
        /*This method prompts player if they want to check
        * their assigned card deck (hand cards).
        * */
        Scanner input = new Scanner(System.in);

        boolean repeat = false;
        do {
            System.out.println();
            System.out.print(player.getName() + " want to check your card?[Y/N] ");
            String ans = input.nextLine();
            if (ans.equalsIgnoreCase("y")) {
                player.checkCardOnPlayer();
                repeat = false;
            }else if (ans.equalsIgnoreCase("n")){
                repeat = false;
            }else{
                System.out.println("Invalid command!");
                repeat = true;
            }
        }while(repeat);
    }

    public static int findByCards(Player[] players, String cardName){
        /*For every player, check every card:
         * if card matches cardName,
         *   return index i (that indicates player[i])
         * else return -1 (not found)*/

        for (int i = 0; i < players.length; i++) {
            for (int j = 0; j < players[i].assignedCards.length; j++) {
                String cardOnPlayer = players[i].assignedCards[j];
                if (cardOnPlayer.equalsIgnoreCase(cardName)) return i;
            }
        }
        return -1;
    }

    public static void arrangeCards(Player player){
        /*Arrange player's hand cards in ascending order*/
        System.out.println("Sorting cards...");
        String temp;
        for(int i=0; i<player.assignedCards.length-1; i++){
            for(int j=i+1; j<player.assignedCards.length; j++){
                if(player.assignedCards[i].compareTo(player.assignedCards[j])>0){
                    temp = player.assignedCards[i];
                    player.assignedCards[i] = player.assignedCards[j];
                    player.assignedCards[j] = temp;
                }
            }
        }
        System.out.println("Sorted!");
    }

/*APPENDING A CARD INTO CARDS ARRAY*/
    public static String[] appendCard(String[] cards, String chosenCard){
        int len = cards.length;
        String[] outputCards = new String[len+1];

        //COPY MOST RECENT CARD DECK INTO OUTPUT
        for(int i=0; i<len; i++)
            outputCards[i] = cards[i];

        //AT THE LAST ELEMENT, APPEND ADDED CARD
        outputCards[len] = chosenCard;
        return outputCards;
    }

    public static String[] removeACard(String[] cards, String chosenCard){
        int len = cards.length;
        String[] outputCards = new String[len-1];

        for(int i=0,k=0; i<len; i++){
            if(!cards[i].equals(chosenCard))
                outputCards[k++] = cards[i];

        }
        return outputCards;
    }
}
