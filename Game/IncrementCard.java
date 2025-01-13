package Game;
import java.awt.image.BufferedImage;
//Jessica
public class IncrementCard extends UpgradeCard{
    //public int researchInc, fileInc, energyInc;
    private  int researchInc, fileInc, energyInc;
    public IncrementCard(int value, Energy energyType, int energyCost, int tier, int researchIncrement, int fileIncrement, int energyIncrement, BufferedImage img){
        super(value,energyType,energyCost,tier,img);
        researchInc = researchIncrement;
        fileInc = fileIncrement;
        energyInc = energyIncrement;
    }
    public int getResearchIncrement(){
        return researchInc;
    }
    public int getFileIncrement(){
        return fileInc;
    }
    public int getEnergyIncrement(){
        return energyInc;
    }

    public Player.CardType getType(){ return Player.CardType.UPGRADE;}

}
