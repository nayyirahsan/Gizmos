package Game;
import java.awt.image.BufferedImage;
import java.util.*;

import Game.Card.Energy;
import Game.Player.CardType;

public class Game
{

    static ArrayList<Player> players;
    static TreeMap<Integer, Deck> decks;
    static Player currentPlayer, p1, p2, p3, p4;
    static ArrayList<String> energyDispenser;
    static ArrayList<String> energyRow;
    static boolean r,finishedGame,chosenAction,gameChoosingCard,gameChoosingBoardCard,gameChoosingDeck,gameChoosingEnergy,choose2,chooseT1,gameEnd;//start phase, build, file, research, pick
    static String theAction,gameEnergy;
    //static Card buildCard;
    //private Player p1;
    //private Player p2;
    //private Player p3;
    //private Player p4;
    public Game() //IDK if this is needed, so delete if you want to. Constructor is mainly for initializing and creating player list, unless its already done somewhere else
    { 
        gameEnd = false;
        choose2 =false;
        r=false;
        finishedGame = false;
        theAction = "";
        gameEnergy = "";
        //buildCard = null;
        chosenAction = false;
        gameChoosingCard = false;
        gameChoosingBoardCard = false;
        gameChoosingDeck = false;
        gameChoosingEnergy = false;
        p1 = new Player("Player 1");
        p2 = new Player("Player 2");
        p3 = new Player("Player 3");
        p4 = new Player("Player 4");
        energyDispenser = new ArrayList<String>();
        energyRow = new ArrayList<String>();
        enFiller(energyDispenser);
        rowFiller(energyDispenser, energyRow);
        players = new ArrayList<Player>();
        players.add(p1);
        players.add(p2);
        players.add(p3);
        players.add(p4);
        currentPlayer = p1; //for testing purposes
        decks = new TreeMap<Integer, Deck>();
        decks.put(1, new Deck());
        decks.put(2, new Deck());
        decks.put(3, new Deck());
        start();

        //testing
        //p1.setConvertingStatus(true);
        //nextTurn();
        //Tier 1 Cards (how fun)
    }
    // For filling the energy dispenser and row on the main screen, not energy type for cards
    public void enFiller (ArrayList<String> enHolder) //HR
    {
        String blueEnergy = "Blue";
        String redEnergy = "Red";
        String yellowEnergy = "Yellow";
        String blackEnergy = "Black";
        for (int i = 0; i < 13; i++)
        {
            enHolder.add(blueEnergy);
            enHolder.add(redEnergy);
            enHolder.add(yellowEnergy);
            enHolder.add(blackEnergy);
        }
        Collections.shuffle(enHolder);
    }
    public static void rowFiller (ArrayList<String> enHolder, ArrayList<String> enRow) //HR
    {
        int holdLength = enHolder.size() - 1;
        for (int i = holdLength; i > holdLength-6; i--)
        {
            String temp = enHolder.get(i);
            enRow.add(temp);
            enHolder.remove(i);
        }
        int rand = (int)(Math.random()*4);
        if(rand == 0){
            enHolder.add("Blue");
        }
        else if(rand == 1){
            enHolder.add("Black");
        }
        else if(rand == 2){
            enHolder.add("Red");
        }
        else{
            enHolder.add("Yellow");
        }
    }
    public void endGame() { //incomplete
        
        //In case of a tie, the tied player with the most Gizmos in their Active
        // Gizmo Area wins. If it’s still tied, the tied player with the most Energy
        // left in their Energy Storage Ring wins. If it’s still tied, the tied player
        // furthest from the First Player, going clockwise, wins.


        Map<String, Player> players = new HashMap<>();
        players.put("Player 1", p1);
        players.put("Player 2", p2);
        players.put("Player 3", p3);
        players.put("Player 4", p4);

         List<String> leaderboard = new ArrayList<>(players.keySet());
        leaderboard.sort((player1, player2) -> Integer.compare(players.get(player2).getTotalScore(), players.get(player1).getTotalScore()));

        // Determine the winner and handle ties
        String winner = leaderboard.get(0);
        if (leaderboard.size() > 1 && players.get(leaderboard.get(0)).getTotalScore() == players.get(leaderboard.get(1)).getTotalScore()) {
            // Check for Gizmos tiebreaker
            if (players.get(leaderboard.get(0)).getGizmosCount() > players.get(leaderboard.get(1)).getGizmosCount()) {
                winner = leaderboard.get(0);
            } else if (players.get(leaderboard.get(0)).getGizmosCount() < players.get(leaderboard.get(1)).getGizmosCount()) {
                winner = leaderboard.get(1);
            } else {
                // Check for Energy Storage Ring tiebreaker
                if (players.get(leaderboard.get(0)).getEnergyCount() > players.get(leaderboard.get(1)).getEnergyCount()) {
                    winner = leaderboard.get(0);
                } else if (players.get(leaderboard.get(0)).getEnergyCount() < players.get(leaderboard.get(1)).getEnergyCount()) {
                    winner = leaderboard.get(1);
                } else {
                  // Check for order variable
                  if (leaderboard.get(0).charAt(leaderboard.get(0).length() - 1) < leaderboard.get(1).charAt(leaderboard.get(1).length() - 1)) {
                    winner = leaderboard.get(0);
                  } else {
                    winner = leaderboard.get(1);
                  }
                }
            }
            leaderboard.remove(winner);
            leaderboard.add(0,winner);
        }

        System.out.println("Leaderboard:");
        for (String player : leaderboard) {
            System.out.println(player + ": " + players.get(player).getTotalScore());
        }

        System.out.println("Winner: " + winner);


    }
    public static ArrayList<Player> getWinner(){
		ArrayList<Player> list = new ArrayList<>();
		list.add(p1);
		ArrayList<Player> unchecked = new ArrayList<>();
		unchecked.add(p2);
		unchecked.add(p3);
		unchecked.add(p4);
		while(unchecked.size()>0) {
			int pos = 0;
			for(int i = 0; i < list.size();i++) {
				if(list.get(i).getTotalScore()<unchecked.get(0).getTotalScore()) {
					list.add(i,unchecked.get(0));
					break;
				}
				else if(list.get(i).getTotalScore()==unchecked.get(0).getTotalScore()) {
					if(list.get(i).getEnergyCount()<unchecked.get(0).getEnergyCount()) {
						list.add(i,unchecked.get(0));
						break;
					}
					else if(list.get(i).getEnergyCount()==unchecked.get(0).getEnergyCount()) {
						if(unchecked.get(0).getName().compareTo(list.get(i).getName())>0) {
							list.add(i,unchecked.get(0));
							break;
						}
					}
				}
			}
			if(!(list.contains(unchecked.get(0)))){
				list.add(unchecked.get(0));
			}
			unchecked.remove(0);
		}
        return list;
	}
    //make arraylists for players with same vicotry points for easier tiebreak
    //incomplete (needs tiebreakers, but nayyir is gonna work on that I think)
    //DW
    public ArrayList<String> getEnergyRow()
    {
        return energyRow;
    }
    public static void testAction(){
        chosenAction = true;
    }
    public static void testFile(){
        gameChoosingBoardCard = true;
    }
    public static void testPick(){
        gameChoosingEnergy = true;
    }
    public static void testPick2(){
        choose2 = true;
    }
    public static void testBuildT1(){
        chooseT1 = true;
    }
    public static void testBuild(){
        currentPlayer.isConverting();
        gameChoosingCard = true;
    }
    public static void testResearch(){
        gameChoosingDeck = true;
            r= true;
    }
    public void start(){
        testAction();
    }
    public static void nextTurn(){ //change so it rearranges the player arraylist accordingly
        
        players.remove(currentPlayer);
        players.add(currentPlayer);
        currentPlayer = players.get(0);
        if(finishedGame&&currentPlayer==p1){
            gameEnd = true;
        }
        else{
            currentPlayer.resetChain();
        testAction();
        Game.finish();
        }
    }
    // JH
    public static void finish(){
        for(int i = 0; i < players.size();i++){
            int tier3s = 0;
            int numCards = 0;
            for(int o = 0; o < players.get(i).getInventory().get(Player.CardType.UPGRADE).getConditionList().size();o++){
                if(players.get(i).getInventory().get(Player.CardType.UPGRADE).getConditionList().get(o).getTier()==3){
                    tier3s++;
                }
                numCards++;
            }
            for(int o = 0; o < players.get(i).getInventory().get(Player.CardType.CONVERTER).getConditionList().size();o++){
                if(players.get(i).getInventory().get(Player.CardType.CONVERTER).getConditionList().get(o).getTier()==3){
                    tier3s++;
                }
                numCards++;
            }
            for(int o = 0; o < players.get(i).getInventory().get(Player.CardType.FILE).getConditionList().size();o++){
                if(players.get(i).getInventory().get(Player.CardType.FILE).getConditionList().get(o).getTier()==3){
                    tier3s++;
                }
                numCards++;
            }
            for(int o = 0; o < players.get(i).getInventory().get(Player.CardType.PICK).getConditionList().size();o++){
                if(players.get(i).getInventory().get(Player.CardType.PICK).getConditionList().get(o).getTier()==3){
                    tier3s++;
                }
                numCards++;
            }
            for(int o = 0; o < players.get(i).getInventory().get(Player.CardType.BUILD).getConditionList().size();o++){
                if(players.get(i).getInventory().get(Player.CardType.BUILD).getConditionList().get(o).getTier()==3){
                    tier3s++;
                }
                numCards++;
            }
            if(tier3s>=4||numCards>=16){
                finishedGame = true;
            }
        }
    }
    public void isFinished() {

        int cnt = 0; //counter for the amount of tier 3s counted
        TreeSet<Player.CardType> temp = new TreeSet<>(currentPlayer.getMap().keySet());
        //Iterator iter = temp.iterator();
        for (int i = 0; i < Player.CardType.values().length; i++) { //loops through all card types
            Iterator<Player.CardType> iter = temp.iterator(); //re-initialize iterator
            int indexCnt = currentPlayer.getMap().get(iter.next()).getConditionList().size(); //counts index for conditionList
            while (iter.hasNext()){ //loops through set
                if(currentPlayer.getMap().get(iter.next()).getConditionList().get(indexCnt).getTier() == 3){ //if the card has a tier value of 3
                    cnt++;
                }
            }
            if(cnt < 4) { //if the requirement has not been met yet continue adding to counter
                cnt++;
            }else{ ///if the requirement is met, break out of loop
                break;
            }
            /* this code is less safe, so I am commenting it out for now
            for (int j = 0; i < currentPlayer.getMap().get(currentPlayer.getCardTypeInd(i)).conditionList.size(); i++) { //loops through all of player inv
                if(currentPlayer.getMap().get(currentPlayer.getCardTypeInd(i)).conditionList.get(j).tier == 3){ //if the card has a tier value of 3
                    if(cnt < 4) { //if the requirement has not been met yet continue adding to counter
                        cnt++;
                    }else{ ///if the requirement is met, break out of loop
                        break;
                    }
                }
            }
            */
        }

        if (currentPlayer.equals(players.get(3))) { //checks to see if it is the last player of the rotation
            if (currentPlayer.getMap().get("UPGRADE").getConditionList().size() == 16) { //checks first condition, if the player has built 16 gizmos
                endGame();
            }else if(cnt == 4){ //checks second condition, if player built 4 tier 3 cards
                endGame();
            }else{ //if none of these conditions are met, return a void value
                return;
            }
        }
        //Dennis Wei
    }
}
