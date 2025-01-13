package Game;
import java.awt.image.BufferedImage;
//Jessica
public class EffectCard extends Card{
    
   // public enum Effects{
   //     PICK,
   //     PICK2,
   //     GAIN,
   //     GAIN2,
   //     BUILD0,
   //     FILE,
   //     RESEARCH,
   //     DRAW,
   ///     DRAW3
   // }
   // public enum Triggers{
   //     PICK,
   //     PICK2,
    //    BUILD,
   //     BUILD2,
    //    ARCHIVEBUILD,
    //    TIERBUILD,
    //    FILEACTION
    //}
    public enum Triggers{
        FILE,
        BUILD_TIERTWO,
        BUILD_ARCHIVED,
        BUILD_BLUE,
        BUILD_RED,
        BUILD_BLACK,
        BUILD_YELLOW,
        BUILD_YELLOWRED,
        BUILD_REDBLUE,
        BUILD_BLUEYELLOW,
        BUILD_YELLOWBLACK,
        BUILD_BLACKRED,
        BUILD_BLUEBLACK,
        PICK_BLUE,
        PICK_RED,
        PICK_YELLOW,
        PICK_BLACK,
        PICK_YELLOWRED,
        PICK_YELLOWBLACK,
        PICK_REDBLUE,
        PICK_BLUEBLACK
    }
    public enum Effects{
        PICK,
        PICK2,
        RANDOMDRAW,
        RANDOMDRAW3,
        BUILDTIERONEFREE,
        FILEACTION,
        RESEARCH,
        ONEVICTORYPOINT,
        TWOVICTORYPOINT
    }
    //public Triggers trig;
    //public Effects effects;
    private Triggers trig;
    private Effects effects;
    public EffectCard(int value, Energy energyType, int energyCost, int tier, Triggers trigger, Effects effect, BufferedImage img){
        super(value,energyType,energyCost,tier,img);
        trig = trigger;
        effects = effect;
    }
    public Triggers getTrigger(){
        return trig;
    }
    public Effects getEffects(){
        return effects;
    }

    public Player.CardType getType() {
        if (getTrigger() == Triggers.FILE)
            return Player.CardType.FILE;
        else if (getTrigger() == Triggers.BUILD_TIERTWO ||
                getTrigger() == Triggers.BUILD_ARCHIVED ||
                getTrigger() == Triggers.BUILD_BLUE ||
                getTrigger() == Triggers.BUILD_RED ||
                getTrigger() == Triggers.BUILD_BLACK ||
                getTrigger() == Triggers.BUILD_YELLOW ||
                getTrigger() == Triggers.BUILD_YELLOWRED ||
                getTrigger() == Triggers.BUILD_REDBLUE ||
                getTrigger() == Triggers.BUILD_BLUEYELLOW ||
                getTrigger() == Triggers.BUILD_YELLOWBLACK ||
                getTrigger() == Triggers.BUILD_BLACKRED ||
                getTrigger() == Triggers.BUILD_BLUEBLACK)
            return Player.CardType.BUILD;
        else
            return Player.CardType.PICK;

    }
    //file, pick, build

}
// test commit and push
