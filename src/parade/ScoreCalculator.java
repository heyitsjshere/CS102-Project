package parade;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import parade.enums.Colour;

public class ScoreCalculator {

    private ArrayList<Player> playerList;
    private HashMap<Player, Integer> scoreTracker;
    
    public ScoreCalculator(PlayerList pl){
        this.playerList = pl.getPlayerList();
        scoreTracker = new HashMap<>() {{ // initialise scoreTracker with all players' score = 0
            for (Player p : playerList) {
                put(p, 0);
            }
        }};

        calculateScores();
    }



    private void calculateScores(){
        // add scores when player has max number of that colour
        for (Colour colour : Colour.values()){ 
            ArrayList<Player> maxPlayers = findMaxPlayers(colour); // get list of players with the maximum number of that colour
            
            for (Player p : playerList) {
                // for each player
                int curScore = scoreTracker.get(p); // get current score (before adding)
                int toAdd = 0;

                if (maxPlayers.contains(p)) {
                    // if max player, only add number of cards, not value
                    toAdd = p.getCollectedCards().get(colour).size();
                } else if (p.getCollectedCardsWithColour(colour) != null ){ // check if player has any of that colour
                    // add value of cards
                    for (Card c : p.getCollectedCardsWithColour(colour)) {
                        toAdd += c.getCardNum();
                    }
                }

                scoreTracker.put(p, toAdd + curScore);
            }
        }
            
    }

    private ArrayList<Player> findMaxPlayers(Colour colour){
        ArrayList<Player> maxPlayers = new ArrayList<>();
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
                    maxPlayers.clear(); // remove all players with fewer cards from list
                    maxPlayers.add(p);
                } else if (count == maxCount){
                    maxPlayers.add(p);
                }
            }
            // if 2 players, get player with majority of >= 2
            else if (playerList.size() == 2){
                // P1 might have been wrongly added bc size was compared to initial maxCount of 0
                if (maxCount - count < 2){
                    maxPlayers.clear();
                }
                if (count > maxCount + 1){
                    maxCount = count;
                    maxPlayers.clear();
                    maxPlayers.add(p);
                }
            }
        }

        return maxPlayers;
    }

    
    public ArrayList<Player> findWinners(){
        calculateScores(); 

        ArrayList<Player> winners = new ArrayList<>();

        // get one winner (could have more)
        int minScore = Collections.min(scoreTracker.values());

        for (Player p : scoreTracker.keySet()) {
            if (scoreTracker.get(p) == minScore) {
                winners.add(p);
            }   
        }
        return winners;
        
    }

    public int getMinScore(){
       return Collections.min(scoreTracker.values());
    }

    // public HashMap<Player, Integer> getScoreTracker(){
    //     // for testing only
    //     return this.scoreTracker;
    // }

    public void printLosers(){

        for (Player p : scoreTracker.keySet()) {
            System.out.println("Player " + (playerList.indexOf(p) + 1) + " has a score of " + scoreTracker.get(p)) ;
        }
    }




}


