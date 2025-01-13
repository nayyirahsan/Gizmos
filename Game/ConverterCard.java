package Game;
import java.awt.image.BufferedImage;
//Jessica
public class ConverterCard extends Card{
   // public boolean isDouble,isAdvanced;
    //public String inputEnergy1, inputEnergy2;
    private boolean isDouble,isAdvanced;
    private Energy inputEnergy1, inputEnergy2; 
    public ConverterCard(int value, Energy energyType, int energyCost, int tier, boolean isDouble, boolean isAdvanced, Energy inputOne, Energy inputTwo, BufferedImage img){
        super(value,energyType,energyCost,tier,img); //makes two isDouble //can convert twice isAdvanced
        this.isDouble = isDouble;
        this.isAdvanced = isAdvanced;
        inputEnergy1 = inputOne;
        inputEnergy2 = inputTwo;
    }
    public boolean isAdvanced(){
        return isAdvanced;
    }
    public boolean isDouble(){
        return isDouble;
    }
    public Energy getInputEnergy1(){
        return inputEnergy1;
    }
    public Energy getInputEnergy2(){
        if(inputEnergy2!=null)
        return inputEnergy1;
        return null;
    }
    public Player.CardType getType(){ return Player.CardType.CONVERTER;}

}
