package Game;
import java.awt.image.BufferedImage;
//Jessica
public class NegateCard extends UpgradeCard{
    //public int choice;
    private int choice;
     public NegateCard(int value, Energy energyType, int energyCost, int tier, int choice, BufferedImage img){
        super(value,energyType,energyCost,tier,img);
        this.choice = choice;
    }
    public int getChoice(){
        return choice;
    }

    public Player.CardType getType(){ return Player.CardType.UPGRADE;}
}
// 0 = research, 1 = file