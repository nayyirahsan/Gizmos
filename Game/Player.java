package Game;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.*;

import Game.Card.Energy;
import Game.Player.CardType;

import static Game.Game.currentPlayer;
import static Game.Game.decks;

public class Player {

    public enum CardType {
        UPGRADE,
        CONVERTER,
        FILE,
        PICK,
        BUILD,
        ARCHIVE
    }

    static Card chosenBuild; //used to store player's build selection
    static Card chosen;
    private boolean hasConverted;
    private Card researchCard;
    private Card convertCard;
    private HashMap<CardType, Deck> Inventory;
    private ArrayList<String> Energy, Chainreactions;
    private int fileLimit, storageLimit, researchAmmount, VictoryPoints;
    //Jessica-for playerui convenience
    private boolean convertingStatus;
    private String name;
    static boolean researching;
    private ArrayList<Card> res;

    //Jessica
    public Player(String n) {
        researchCard = null;
        res = new ArrayList<>();
        Chainreactions = new ArrayList<String>();
        name = n;
        Inventory = new HashMap<CardType, Deck>();
        Inventory.put(CardType.UPGRADE, new Deck());
        Inventory.put(CardType.CONVERTER, new Deck());
        Inventory.put(CardType.FILE, new Deck());
        Inventory.put(CardType.PICK, new Deck());
        Inventory.put(CardType.BUILD, new Deck());
        Inventory.put(CardType.ARCHIVE, new Deck());
        VictoryPoints = 0;
        Energy = new ArrayList<String>();
        fileLimit = 1;
        storageLimit = 5;
        researchAmmount = 3;
        convertingStatus = false;
        researching = false;
        Inventory.get(CardType.FILE).getConditionList().add(new EffectCard(0, null, 0, 1, EffectCard.Triggers.FILE, EffectCard.Effects.RANDOMDRAW, null));
    }

    public void DrawRandomEnergy() //HR
    {
        //HR
        if (Energy.size() < storageLimit) {
            //Random random = new Random(GizmosRunner.game.energyDispenser.size());
            //String randEnergy = GizmosRunner.game.energyDispenser.get(random.nextInt());
            //Energy.add(randEnergy);
            //GizmosRunner.game.energyDispenser.remove(randEnergy);
            int rand = (int) (Math.random() * 4);
            if (rand == 0) {
                Energy.add("Blue");
            } else if (rand == 1) {
                Energy.add("Black");
            } else if (rand == 2) {
                Energy.add("Red");
            } else {
                Energy.add("Yellow");
            }
        }
    }

    public void Pick(String energy) //HR
    {
        if (Energy.size() < storageLimit) {
            Energy.add(energy);
            GizmosRunner.game.energyRow.remove(energy);
            GizmosRunner.game.rowFiller(GizmosRunner.game.energyDispenser, GizmosRunner.game.energyRow);
            ChainReactions("PICK " + energy);
        }
    }

    public void BuildT1(Card card) {
        Inventory.get(CardType.BUILD).add(card);
        if (card.getType().name().equals("UPGRADE")) {
            Upgrade((IncrementCard) card);
        }
        card.use();
        String cr = "BUILD " + card.getEnergyType().name();
        if (Inventory.get(CardType.FILE).getConditionList().contains(card)) {
            cr = "ARCHIVED" + cr;
        }
        ChainReactions(cr);
        if (Inventory.get(Player.CardType.ARCHIVE).getConditionList().contains(card))
            Inventory.remove(card.getType(), card);
        else if (card.getTier() == 1)
            decks.get(1).remove(card);
    }

    public void Negate(NegateCard card) {
        if (card.getChoice() == 0) {
            researchAmmount = 0;
        } else if (card.getChoice() == 1) {
            fileLimit = 0;
        }
    }

    public void Build(Card card) //malfunctioning
    {

        chosenBuild = card;
        System.out.println(card + "chosenBuild");
        //code goes here.
        if (isBuildable(card)) {
            Inventory.get(card.getType()).add(card);
            if (card.getType().name().equals("UPGRADE")) {
                try {
                    Upgrade((IncrementCard) card);
                } catch (Exception e) {
                    Negate((NegateCard) card);
                }

            }
            card.use();
            String cr = "BUILD " + card.getEnergyType().name();
            if (Inventory.get(CardType.FILE).getConditionList().contains(card)) {
                cr = "ARCHIVED" + cr;
            }
            if (card.getTier() == 2) {
                cr = "TIERTWO_" + cr;//TIERTWO_BUILD BLACK ARCHIVED
            }
            ChainReactions(cr);

            if (Inventory.get(CardType.ARCHIVE).getConditionList().contains(card))
                Inventory.get(CardType.ARCHIVE).remove(card);
            else if (card.getTier() == 1)
                decks.get(1).remove(card);
            else if (card.getTier() == 2)
                decks.get(2).remove(card);
            else {
                decks.get(3).remove(card);
            }

            if (card.getEnergyType().name().equals("ALL")) {
                for (int i = 0; i < card.getEnergyCost(); i++) {
                    Energy.remove(i);
                }
            }
            for (int i = 0; i < card.getEnergyCost(); i++) {
                String e = (card.getEnergyType().name().substring(0, 1).toUpperCase()) + card.getEnergyType().name().substring(1).toLowerCase();
                Energy.remove(e);
            }
        }else{
            setConvertingStatus(true);
        }

    }
    public String convertEnergyName(String energy){
        if(energy.equals("BLUE")){
            energy = "Blue";
        }else if(energy.equals("RED")){
            energy = "Red";
        }else if(energy.equals("YELLOW")){
            energy = "Yellow";
        }else if(energy.equals("BLACK")){
            energy = "Black";
        }
        return energy;
    }

    public int countEnergyInstances(String energy) {
        energy = convertEnergyName(energy);
        int cnt = 0;
        for (int i = 0; i < currentPlayer.getEnergy().size(); i++) {
            if (currentPlayer.getEnergy().get(i).equals(energy)) {
                cnt++;
            }
        }
        return cnt;
    }

    public void Convert(ConverterCard card) {
        if (isConverting()) {
            System.out.println(chosenBuild);
            System.out.println(isConverting() + "convert");
            if (!card.notUsed()) {
                System.out.println(card.notUsed() + "used");
                if (card.isDouble()) { //if the card is a one to two conversion (ex. 1 black -> 2 black)
                    System.out.println("isDouble");
                    if (card.isAdvanced()) { //if the card is an advanced card
                        if (chosenBuild.getEnergyType().equals(card.getInputEnergy1()) || chosenBuild.getEnergyType().equals(card.getInputEnergy2())) {
                            if (currentPlayer.countEnergyInstances(card.getInputEnergy1().name()) > 0) {
                                System.out.println("added advanced double");
                                currentPlayer.getEnergy().add(convertEnergyName(card.getInputEnergy1().name()));
                                currentPlayer.Build(chosenBuild);
                                currentPlayer.stopConverting();
                                return;
                            }
                        }
                        //currentPlayer.getEnergy().add(card.getInputEnergy1())
                    } else {
                        System.out.println("Trigger double not advanced");
                        System.out.println(card.getInputEnergy1());
                        System.out.println(currentPlayer.countEnergyInstances(card.getInputEnergy1().name()) + "energy Instances");
                        System.out.println(currentPlayer.getEnergy());
                        if (currentPlayer.countEnergyInstances(card.getInputEnergy1().name()) > 0) {
                            System.out.println("added double");
                            currentPlayer.getEnergy().add(convertEnergyName(card.getInputEnergy1().name())); //adds another energy of the same type to player's energy list (these would be deleted either way when building so it shouldn't cause bugs);
                            currentPlayer.Build(chosenBuild);
                            currentPlayer.stopConverting();
                            return;
                        }
                    }
                }else{
                    System.out.println("trigger not double");

                    if(card.isAdvanced()){
                        System.out.println("Convert advanced card");
                        currentPlayer.removeEnergy(card.getInputEnergy1().name());
                        currentPlayer.getEnergy().add(convertEnergyName(chosenBuild.getEnergyType().name()));
                        System.out.println(currentPlayer.getEnergy());
                        currentPlayer.Build(chosenBuild);
                        currentPlayer.stopConverting();
                        return;
                    }else{
                        System.out.println("Trigger normal convert");
                        currentPlayer.removeEnergy(card.getInputEnergy1().name());
                        currentPlayer.getEnergy().add(convertEnergyName(chosenBuild.getEnergyType().name()));
                        System.out.println(currentPlayer.getEnergy());
                        currentPlayer.Build(chosenBuild);
                        currentPlayer.stopConverting();
                    }
                }
            }else{
                System.out.println("Used");
            }
        }



    }

    public void File(Card card) {
        Inventory.get(CardType.ARCHIVE).add(card);
        Game.decks.get(card.getTier()).getConditionList().remove(card);
        ChainReactions("FILE");
    }

    public void Research(int level) {
        res.clear();
        if (level == 1) {
            for (int i = 5; i < Game.decks.get(1).getConditionList().size() && res.size() < researchAmmount; i++) {
                res.add(Game.decks.get(1).getConditionList().get(i));
            }
            for (int i = 0; i < res.size(); i++) {
                Game.decks.get(1).getConditionList().remove(res.get(i));
            }
        } else if (level == 2) {
            for (int i = 4; i < Game.decks.get(2).getConditionList().size() && res.size() < researchAmmount; i++) {
                res.add(Game.decks.get(2).getConditionList().get(i));
            }
            for (int i = 0; i < res.size(); i++) {
                Game.decks.get(2).getConditionList().remove(res.get(i));
            }
        } else {
            for (int i = 3; i < Game.decks.get(3).getConditionList().size() && res.size() < researchAmmount; i++) {
                res.add(Game.decks.get(3).getConditionList().get(i));
            }
            for (int i = 0; i < res.size(); i++) {
                Game.decks.get(3).getConditionList().remove(res.get(i));
            }
        }
        researching = true;
    }

    public ArrayList<Card> getResearch() {
        return res;
    }

    public void setResearchCard(Card card) {
        researchCard = card;
    }

    public Card getResearchCard() {
        return researchCard;
    }
    //jessica

    public void Upgrade(IncrementCard card) {
        fileLimit = fileLimit + card.getFileIncrement();
        researchAmmount = researchAmmount + card.getResearchIncrement();
        storageLimit = storageLimit + card.getEnergyIncrement();
    }

    public void resetChain() {
        Chainreactions.clear();
        for (int i = 0; i < Inventory.get(CardType.BUILD).getConditionList().size(); i++) {
            Inventory.get(CardType.BUILD).getConditionList().get(i).reset();
        }
        for (int i = 0; i < Inventory.get(CardType.PICK).getConditionList().size(); i++) {
            Inventory.get(CardType.PICK).getConditionList().get(i).reset();
        }
        for (int i = 0; i < Inventory.get(CardType.FILE).getConditionList().size(); i++) {
            Inventory.get(CardType.FILE).getConditionList().get(i).reset();
        }
    }

    public void ChainReactions(String action) {
        if (action.contains("BUILD")) {
            for (int i = 0; i < Inventory.get(CardType.BUILD).getConditionList().size(); i++) {
                EffectCard temp = (EffectCard) Inventory.get(CardType.BUILD).getConditionList().get(i);
                if (temp.notUsed() && temp.getTrigger().name().contains(action.substring(action.indexOf(" ") + 1).toUpperCase())) {
                    Chainreactions.add(temp.getEffects().name());
                    temp.use();
                } else if (temp.notUsed() && temp.getTrigger().name().contains("ARCHIVED") && action.contains("ARCHIVED")) {//archived builds
                    Chainreactions.add(temp.getEffects().name());
                    temp.use();
                } else if (temp.notUsed() && action.indexOf("_") > -1 && temp.getTrigger().name().contains("TIERTWO")) {//tier two
                    Chainreactions.add(temp.getEffects().name());
                    temp.use();
                } else if (temp.notUsed() && action.substring(action.indexOf(" ") + 1).equals("ALL")) {//wild cards
                    Chainreactions.add(temp.getEffects().name());
                    temp.use();
                }
            }
        } else if (action.contains("PICK")) {
            for (int i = 0; i < Inventory.get(CardType.PICK).getConditionList().size(); i++) {
                EffectCard temp = (EffectCard) Inventory.get(CardType.PICK).getConditionList().get(i);
                if (temp.notUsed() && temp.getTrigger().name().contains(action.substring(action.indexOf(" ") + 1).toUpperCase())) {
                    Chainreactions.add(temp.getEffects().name());
                    temp.use();
                }
            }
        } else if (action.contains("FILE")) {
            for (int i = 0; i < Inventory.get(CardType.FILE).getConditionList().size(); i++) {
                EffectCard temp = (EffectCard) Inventory.get(CardType.FILE).getConditionList().get(i);
                if (temp.notUsed()) {
                    Chainreactions.add(temp.getEffects().name());
                    temp.use();
                }

            }
        }
    }

    public void setConvertingStatus(boolean bool) {
        convertingStatus = bool;
    }

    public void setHasConverted(boolean bool) {
        hasConverted = bool;
    }

    public CardType[] getCardType() {
        return CardType.values();
    } //added method [not in UML]   {DW}

    public CardType getCardTypeInd(int i) {
        return CardType.values()[i];
    } //added method [not in UML] {DW}

    public HashMap<CardType, Deck> getMap() {
        return Inventory;
    }

    public int getVictoryPoints() {
        return VictoryPoints;
    }

    public ArrayList<String> getEnergy() {
        return Energy;
    }

    public int getFileLimit() {
        return fileLimit;
    }

    public int getResearchLimit() {
        return researchAmmount;
    }

    public int getStorageLimit() {
        return storageLimit;
    }

    public void addVictoryPoints(int points) {
        VictoryPoints += points;
    }

    public HashMap<CardType, Deck> getInventory() {
        return Inventory;
    }

    //convinience methods
    public boolean isConverting() {
        return convertingStatus;
    }

    public boolean isResearching() {
        return researching;
    }

    public void setResearching(boolean a) {
        researching = a;
    }

    public void changeConverting(boolean a) {
        convertingStatus = a;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getReactionList() {
        return Chainreactions;
    }

    public Deck getAvailableConverterCards() {
        Deck ret = new Deck();
        Deck list = getInventory().get(CardType.CONVERTER);
        for (int i = 0; i < list.getConditionList().size(); i++) {
            if ((getEnergy().contains(((ConverterCard) list.getConditionList().get(i)).getInputEnergy1()) || getEnergy().contains(((ConverterCard) list.getConditionList().get(i)).getInputEnergy2())) && list.getConditionListBool().get(i) == true) {
                ret.add(list.getConditionList().get(i));
            }
        }
        return ret;
    }

    public boolean isBuildable(Card x) {
        String cardColor = "";
        cardColor = x.getEnergyType().name();
        if (cardColor.equals("ALL")) {
            return Energy.size() >= x.getEnergyCost();
        } else {
            int num = x.getEnergyCost();
            if (cardColor.equals("RED") && getRedEnergyCount() >= num) {
                return true;
            } else if (cardColor.equals("YELLOW") && getYellowEnergyCount() >= num) {
                return true;
            } else if (cardColor.equals("BLUE") && getBlueEnergyCount() >= num) {
                return true;
            } else if (cardColor.equals("BLACK") && getBlackEnergyCount() >= num) {
                return true;
            }
        }
        return false;
    }

    public int getRedEnergyCount() {
        if (Energy.contains("Red")) {
            int cnt = 0;
            for (int i = 0; i < Energy.size(); i++) {
                if (Energy.get(i).equals("Red")) {
                    cnt++;
                }
            }
            return cnt;
        }
        return 0;
    }

    public int getYellowEnergyCount() {
        if (Energy.contains("Yellow")) {
            int cnt = 0;
            for (int i = 0; i < Energy.size(); i++) {
                if (Energy.get(i).equals("Yellow")) {
                    cnt++;
                }
            }
            return cnt;
        }
        return 0;
    }

    public int getBlueEnergyCount() {
        if (Energy.contains("Blue")) {
            int cnt = 0;
            for (int i = 0; i < Energy.size(); i++) {
                if (Energy.get(i).equals("Blue")) {
                    cnt++;
                }
            }
            return cnt;
        }
        return 0;
    }

    public int getBlackEnergyCount() {
        if (Energy.contains("Black")) {
            int cnt = 0;
            for (int i = 0; i < Energy.size(); i++) {
                if (Energy.get(i).equals("Black")) {
                    cnt++;
                }
            }
            return cnt;
        }
        return 0;
    }

    public int getEnergyCount() {
        return Energy.size();
    }

    public int getGizmosCount() {
        return Inventory.size();
    }

    //Jessica v
    public int totalCards() {
        int count = 0;
        for (int i = 0; i < Inventory.get(CardType.UPGRADE).getConditionList().size(); i++) {
            count++;
        }
        for (int i = 0; i < Inventory.get(CardType.CONVERTER).getConditionList().size(); i++) {
            count++;
        }
        for (int i = 0; i < Inventory.get(CardType.FILE).getConditionList().size(); i++) {
            count++;
        }
        for (int i = 0; i < Inventory.get(CardType.PICK).getConditionList().size(); i++) {
            count++;
        }
        for (int i = 0; i < Inventory.get(CardType.BUILD).getConditionList().size(); i++) {
            count++;
        }
        for (int i = 0; i < Inventory.get(CardType.ARCHIVE).getConditionList().size(); i++) {
            count++;
        }
        return count;
    }

    public int getTotalScore() {
        int score = VictoryPoints;
        for (int i = 0; i < Inventory.get(CardType.UPGRADE).getConditionList().size(); i++) {
            score = score + Inventory.get(CardType.UPGRADE).getConditionList().get(i).getValue();
            if (Inventory.get(CardType.UPGRADE).getConditionList().get(i).getEnergyType().equals(Card.Energy.ALL)) {
                Card temp = Inventory.get(CardType.UPGRADE).getConditionList().get(i);
                if (temp.getEnergyType().equals(Card.Energy.ALL)) {
                    score = score + Energy.size();
                } else {
                    score = score + VictoryPoints / 3;
                }
            }
        }
        for (int i = 0; i < Inventory.get(CardType.CONVERTER).getConditionList().size(); i++) {
            score = score + Inventory.get(CardType.CONVERTER).getConditionList().get(i).getValue();
        }
        for (int i = 0; i < Inventory.get(CardType.FILE).getConditionList().size(); i++) {
            score = score + Inventory.get(CardType.FILE).getConditionList().get(i).getValue();
        }
        for (int i = 0; i < Inventory.get(CardType.PICK).getConditionList().size(); i++) {
            score = score + Inventory.get(CardType.PICK).getConditionList().get(i).getValue();
        }
        for (int i = 0; i < Inventory.get(CardType.BUILD).getConditionList().size(); i++) {
            score = score + Inventory.get(CardType.BUILD).getConditionList().get(i).getValue();
        }
        return score;
    }

    public void removeEnergy(String energy){
        String energy2 = convertEnergyName(energy);
        System.out.println(energy2);
        for(int i = 0; i < currentPlayer.getEnergy().size(); i++){
            if(currentPlayer.getEnergy().get(i).equals(energy2)) {
                currentPlayer.getEnergy().remove(i);
                return;
            }
        }
        System.out.println("Energy Not FOund");
    }

    public void stopConverting() {
        convertingStatus = false;
    }
}
