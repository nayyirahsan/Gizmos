package Game;
import java.awt.image.BufferedImage;
//Jessica
public class DiscountCard extends UpgradeCard{
    //public DiscountType type;
    public enum DiscountType
    {
        TIER2,
        RESEARCH,
        FILE
    }
    private DiscountType type;
    public DiscountCard(int value, Energy energyType, int energyCost, int tier, DiscountType type, BufferedImage img){
        super(value,energyType,energyCost,tier,img);
        this.type = type;
    }
    public DiscountType getDiscountType(){
        return type;
    }

    public Player.CardType getType(){ return Player.CardType.UPGRADE;}

}
