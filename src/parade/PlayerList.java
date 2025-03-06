package parade;

import java.util.ArrayList;

import parade.exceptions.EndGameException;

public class PlayerList {

    private ArrayList<Player> playerList;
    Deck deck;
    private Parade parade;

    public PlayerList (ArrayList<Player> pl, Parade p, Deck d) {
       this.playerList = pl;
       this.deck = d;
    //    parade = new Parade(deck);
       this.parade = p;
       dealInitialCards();
    }

    private void dealInitialCards() {
        for (int i = 0; i < 5; i++) {
            for (Player p : playerList) {
                try {
                    p.addCard(deck.drawCard());
                } catch (EndGameException e) {
                    System.out.println("There are not enough cards to start the game.");
                    System.exit(-1);
                }
            }
        }
    }

    public ArrayList<Player> getPlayerList(){
        return this.playerList;
    }

    public Player getPlayer(int i){
        int size = playerList.size(); // it will never go out
        return playerList.get(i % size);
    }
    
}
