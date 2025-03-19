package parade;

import java.util.*;

import parade.enums.Colour;
import parade.exceptions.EndGameException;

public class PlayerList {

    private ArrayList<Player> playerList;
    Deck deck;
    private static final int MAX_PLAYER_NUM = 6;
    private static final int HAND_SIZE = 5;

    public PlayerList(Deck d){
        UserInput input = new UserInput();
        ArrayList<Player> players = new ArrayList<Player>();

        Scanner sc = new Scanner(System.in);
        // get human players
        int numHumanPlayers = input.getUserInt("Enter number of Human Players (%d - %d): ", 1, MAX_PLAYER_NUM);
        for (int i = 0 ; i < numHumanPlayers; i++) { 
            // get their names
            // System.out.print("Enter name: ");
            // String name = sc.nextLine();
            String name = input.getString("Enter name: ");
            players.add(new HumanPlayer(name));
        }


        int minNumBots = 0; 
        if (numHumanPlayers == 1) { minNumBots = 1; } 
        int numBotPlayers = input.getUserInt("Enter number of Bot Players (%d - %d): ", minNumBots, MAX_PLAYER_NUM - numHumanPlayers);

        for (int i = 1 ; i < numBotPlayers+1 ; i++) {
            players.add(new BotPlayer("Bot " + i));
        }

        this.playerList = players;
        this.deck = d;
        dealInitialCards();
    }

    // public PlayerList (ArrayList<Player> pl, Deck d) {
    //    this.playerList = pl;
    //    this.deck = d;
    //    dealInitialCards();
    // }

    private void dealInitialCards() {
        for (int i = 0; i < HAND_SIZE; i++) {
            for (Player p : playerList) {
                try {
                    p.addCard(deck.drawCard());
                } catch (EndGameException e) {
                    System.out.println("There are not enough cards to start the game.");
                    // if printWinner is going to be called when catching endgame exception shouldn't this be a different exception
                    // or just catch the moment a list with >n players is passed in?
                    System.exit(-1);
                }
            }
        }
    }

    public ArrayList<Player> getPlayerList(){
        return this.playerList;
    }

    public int getNumberOfPlayers(){
        return playerList.size();
    }

    public Player getPlayer(int i){
        int size = playerList.size(); // it will never go out
        return playerList.get(i % size);
    }

}
