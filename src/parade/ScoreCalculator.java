package parade;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.Hashtable;

import parade.enums.Colour;

public class ScoreCalculator {

    private ArrayList<Player> playerList;

    // private Hashtable<Player, Integer> playerScores = new Hashtable<Player, Integer>();
    
    public ScoreCalculator(PlayerList pl){
        this.playerList = pl.getPlayerList();
        calculateScores();
    }


     // in player class,
    // collected cards per player rep by EnumMap<Colour, ArrayList<Card>> collectedCards
    // use getCollectedCards
    // to remove cards from collectedCards
    private void calculateScores(){
        resetScores(); 
        EnumMap<Colour, ArrayList<Player>> AllColourMaxPlayers = new EnumMap<>(Colour.class);

        // add scores when player has max number of that colour
        for (Colour colour : Colour.values()){ 
            ArrayList<Player> maxPlayers = findMaxPlayers(colour); // get list of players with the maximum number of that card
            for (Player p : maxPlayers){
                p.addScore(p.getCollectedCards().get(colour).size());  // add the number of cards to the score
            }
            AllColourMaxPlayers.put(colour, maxPlayers); // record it
        }

        // add all other scores (skip if have max colour)
        for (Player p : playerList){
            EnumMap<Colour, ArrayList<Card>> collectedCards = p.getCollectedCards();
            collectedCards.forEach((colour, cardList) ->  {
                if (!AllColourMaxPlayers.get(colour).contains(p)) {
                    for (Card c: cardList) {
                        p.addScore(c.getCardNum());
                    }
                }
            });
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

    private void resetScores(){
        for (Player p : playerList) {
            int score = p.getScore();
            p.addScore(-score); // set score to 0
        }
    }
    
    public ArrayList<Player> findWinners(){
        calculateScores(); 

        ArrayList<Player> winners = new ArrayList<>();

        // get one winner (could have more)
        Player winner = Collections.min(playerList, Comparator.comparingInt(Player::getScore));

        for (Player p : playerList) {
            if (p.getScore() == winner.getScore()) winners.add(p);
        }
        return winners;
        
    }

    public void printLosers(){
        for (Player p : playerList){
            System.out.println(playerList.indexOf(p) + 1 + ": " + p.getScore());
        }
    }

}


