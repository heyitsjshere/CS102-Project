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

        int numHumanPlayers = input.getUserInt("Enter number of Human Players (%d - %d): ", 1, MAX_PLAYER_NUM);
        int minNumBots = 0; 
        if (numHumanPlayers == 1) { minNumBots = 1; } 
        int numBotPlayers = input.getUserInt("Enter number of Bot Players (%d - %d): ", minNumBots, MAX_PLAYER_NUM - numHumanPlayers);
        
        ArrayList<Player> players = new ArrayList<Player>();

        for (int i = 0 ; i < numHumanPlayers; i++) { 
            players.add(new HumanPlayer());
        }

        for (int i = 0 ; i < numBotPlayers ; i++) {
            players.add(new BotPlayer());
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

    // in player class,
    // collected cards per player rep by EnumMap<Colour, ArrayList<Card>> collectedCards
    // use getCollectedCards
    // to remove cards from collectedCards

    public void printWinner(){
        EnumMap<Colour, ArrayList<Player>> AllColourMaxPlayers = new EnumMap<>(Colour.class);

        // iter through colours
        for (Colour colour : Colour.values()){
            ArrayList<Player> MaxPlayers = new ArrayList<>();
            int maxCount = 0;
            for (Player p : playerList){
                // if player doesn't have that colour card, continue to next player
                if (!p.getCollectedCards().containsKey(colour)){
                    continue;
                }

                int count = p.getCollectedCards().get(colour).size();
                // if >2 players, get player(s) with largest size
                if (playerList.size() > 2){
                    if (count > maxCount){
                        maxCount = count;
                        MaxPlayers.clear(); // remove all players with fewer cards from list
                        MaxPlayers.add(p);
                    } else if (count == maxCount){
                        MaxPlayers.add(p);
                    }
                }
                // if 2 players, get player with majority of >= 2
                else if (playerList.size() == 2){
                    if (count > maxCount + 1){
                        maxCount = count;
                        MaxPlayers.clear();
                        MaxPlayers.add(p);
                    }
                }

            }
            AllColourMaxPlayers.put(colour, MaxPlayers);
            for (Player p : AllColourMaxPlayers.get(colour)){
                p.score += p.getCollectedCards().get(colour).size();
                p.placeColourFaceDown(colour);
            }
        }

        updateIndivScores();
        // ArrayList<Integer> playerScores = updateIndivScores();
        Player winner = Collections.max(playerList, Comparator.comparingInt(Player::getScore));
        System.out.println(winner + " wins with a score of " + winner.score + "!"); // can use winner.getName() instead if getname implemented
    }

    // add value of remaining face up cards AFTER removing majority cards
    public void updateIndivScores(){
        // ArrayList<Integer> playerScores = new ArrayList<>();
        for (Player p : playerList){
            for (ArrayList<Card> cardList : p.getCollectedCards().values()){
                for (Card c : cardList){
                    p.score += c.getCardNum();
                }
            }
            // playerScores.add(p.getScore());
        }
        // return playerScores;
    }
}
