package Game;
import java.awt.image.BufferedImage;
//Jessica
public class AwardCard extends UpgradeCard{
    public int choice;
    public AwardCard(int value, Energy energyType, int energyCost, int tier, int choice, BufferedImage img){
        super(value,energyType,energyCost,tier,img);
        this.choice = choice;
    }
    public Player.CardType getType(){ return Player.CardType.UPGRADE;}

}
