package Game;

import java.awt.image.BufferedImage;

//Jessica
public class Card {
    public enum Energy{
        BLUE,
        RED,
        BLACK,
        YELLOW,
        ALL
    }
    //public int value,tier,energyCost;
    //public Energy energyType;
    //public BufferedImage image;
    private int value,tier,energyCost;
    private Energy energyType;
    private BufferedImage image;
    private Boolean isUsed;
    public Card(int value, Energy en, int energyCost, int tier, BufferedImage img) {
        this.value = value;
        this.tier = tier;
        this.energyType = en;
        this.energyCost = energyCost;
        image = img;
        isUsed = false;
    }
    public BufferedImage getImage(){
        return image;
    }
    public Energy getEnergyType(){
        return energyType;
    }
    public int getValue(){
        return value;
    }
    public int getTier(){
        return tier;
    }
    public int getEnergyCost(){
        return energyCost;
    }
    public Player.CardType getType(){ return Player.CardType.UPGRADE;}

    public boolean notUsed(){
        return !isUsed;
    }
    public void setUsed(boolean b){
        isUsed = b;
    }
    public void reset(){
    isUsed= false;
    }
    public void use(){
    isUsed = true;
    }
}
