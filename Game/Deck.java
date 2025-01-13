package Game;
import java.util.*;
import java.util.ArrayList;

public class Deck 
{
    private ArrayList<Card> conditionList;
    private ArrayList<Boolean> conditionListBool;
    public Deck()
    {
        conditionList = new ArrayList<Card>();
        conditionListBool = new ArrayList<Boolean>();
    }
    public ArrayList<Card> getConditionList(){
        return conditionList;
    }

    public Card getRandomCard(){
        int len = conditionList.size();
        return conditionList.get((int)(Math.random()*len));
    }
    public void shuffle(){
        for(int i = 0; i < conditionList.size();i++){
            int rand = (int)(Math.random()*conditionList.size());
            conditionList.add(conditionList.remove(rand));
        }
    }
    //public static void Shuffle(ArrayList<Card> deck1, ArrayList<Boolean> deck2) {
    //    int length = deck1.size();
    //    ArrayList<Boolean> temp2 = new ArrayList<>();
    //    ArrayList<Card> temp = new ArrayList<>();
    //    while (length > 0) {
    //        int x = (int) (Math.random() * deck1.size());
    //        temp.add(deck1.remove(x));
    //        temp2.add(deck2.remove(x));
    //        length --;
    //    }
    //    deck1 = temp;
    //    deck2 = temp2;
    //}
    //jessica-for temporary convenience, change if needed for final product
    public void used(Card x){
        conditionListBool.set(conditionList.indexOf(x),false);
    }
    public void add(Card x){
        conditionList.add(x);
        conditionListBool.add(true);
    }
    public void remove(Card x){
        int i = conditionList.indexOf(x);
        conditionList.remove(x);
        conditionListBool.remove(i);
    }
    public ArrayList<Boolean> getConditionListBool(){
        return conditionListBool;
    }

}
