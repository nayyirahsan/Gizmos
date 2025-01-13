package Game;

import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class BoardUI extends JPanel implements MouseListener {
    //Player vars
    private Player play;
    private int turn;
    private static int phaseTracker; //tracks researching process phase [DW]
    private HashMap<String, Card> cards;
    private ArrayList<Card> useableConverters;
    private BufferedImage point, pointfive, dashBoard1, dashBoard2, dashBoard3, dashBoard4, endButton;
    private boolean fileOrBuild;
    private String ret;

    // Board vars
    private BufferedImage LV1, LV2, LV3, energyDispenser, gameBoard, blueEnergyImage, redEnergyImage, yellowEnergyImage, blackEnergyImage;
    private Deck d1, d2, d3;
    private boolean ChoosingAny, ChoosingTierOne, choosingEnergy;
    private String choosenEnergy;
    //Tier 1 Cards - DW
    private BufferedImage BLD1BLK11BLUPICK, BLD1BLK11RED1VICTPT, CNVRT1BLK11FFYLWNULL, CNVRT1BLK11FFREDNULL, FILE1BLK11FILEPICK, BLD1BLU11BLDYLWPICK,
            BLD1BLK11BLDBLK1VICTPT, CNVRT1BLU11FFREDNULL, CNVRT1BLU11FFYLWNULL, FILE1BLU11FILEPICK, PICK1BLU11REDRAND, PICK1BLU11BLKRAND,
            INC1BLU11101, INC1BLU11011, PICK1BLK11PICKBLKRAND, PICK1BLK11PICKYLWRAND, INC1BLK11101, INC1BLK11011, BLD1RED11BLKPICK,
            BLD1RED11YLW1VICTPT, CNVRT1RED11FFBLUNULL, CNVRT1RED11FFBLKNULL, FILE1RED11FILEPICK, PICK1RED11BLURAND, PICK1RED11YLWRAND,
            INC1RED11011, INC1RED11101, BLD1YLW11BLDREDPICK, BLD1YLW11BLDBLU1VICTPT, CNVRT1YLW11FFBLKNULL, CNVRT1YLW11FFBLUNULL, FILE1YLW11FILEPICK,
            PICK1YLW11PICKBLKRAND, PICK1YLW11PICKREDRAND, INCYLW11101, INCYLW11011;

    //Level 3 Card Images - HR - name is the VP value, the color (B = Black, R = Red, Y = Yellow, Bl = Black), the number of energy needed to build it, and the tier (3) plus an underscore and a number if there are multiple cards with the same name
    private BufferedImage sixB63, eightB43, fourB43, sumA73, fiveBl53, sevenBl43, fourBl43, energyA73, sixY63, eightY43, fiveY53, sumA73_2, fiveR53, sevenR43, fiveBl53_2, energyA73_2,
            fiveB53, fourB43_2, fiveB53_2, fiveB53_3, sixB63_2, fiveBl53_3, fourBl43_2, sevenBl73, sixBl63, sixR63, fourR43, fourR43_2, fiveR53_2, fiveR53_3, sevenR73, fourY43, fourY43_2,
            fiveY53_2, sixY63_2, fiveY53_3;

    private BufferedImage t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22, t23, t24, t25, t26, t27, t28, t29, t30, t31, t32, t33, t34, t35, t36;
    private BufferedImage pick, file, research, build, end;

    public BoardUI(int p) {
        //Jessica - player stuff
        useableConverters = new ArrayList<>();
        fileOrBuild = false;

        // game stuff
        d1 = GizmosRunner.game.decks.get(1);
        d2 = GizmosRunner.game.decks.get(2);
        d3 = GizmosRunner.game.decks.get(3);
        ChoosingAny = false;
        ChoosingTierOne = false;
        choosingEnergy = false;
        choosenEnergy = null;
        try {
            //fxns buttons
            pick = ImageIO.read(BoardUI.class.getResource("/buttons/pickButton.png"));
            file = ImageIO.read(BoardUI.class.getResource("/buttons/fileButton.png"));
            build = ImageIO.read(BoardUI.class.getResource("/buttons/buildButton.png"));
            research = ImageIO.read(BoardUI.class.getResource("/buttons/researchButton.png"));
            endButton = ImageIO.read(BoardUI.class.getResource("/buttons/endTurn.png"));
            // player images
            point = ImageIO.read(BoardUI.class.getResource("/Victory Points/VICTORYPOINT.png"));
            pointfive = ImageIO.read(BoardUI.class.getResource("/Victory Points/VICTORYPOINT5.png"));
            dashBoard1 = ImageIO.read(BoardUI.class.getResource("/Player Dashboards - Gizmos/FPLAYER.png"));
            dashBoard2 = ImageIO.read(BoardUI.class.getResource("/Player Dashboards - Gizmos/REGPLAYER-Green.png"));
            dashBoard3 = ImageIO.read(BoardUI.class.getResource("/Player Dashboards - Gizmos/REGPLAYER-Grey.png"));
            dashBoard4 = ImageIO.read(BoardUI.class.getResource("/Player Dashboards - Gizmos/REGPLAYER-Purple.png"));
            //board images
            LV1 = ImageIO.read(BoardUI.class.getResource("/Level 1/Level 1 Card.png"));
            LV2 = ImageIO.read(BoardUI.class.getResource("/Level 2/Level 2 Card.png"));
            LV3 = ImageIO.read(BoardUI.class.getResource("/Level 3/Level 3 Card.png"));
            energyDispenser = ImageIO.read(BoardUI.class.getResource("/Energy Spheres/energyDispenser.png"));
            gameBoard = ImageIO.read(BoardUI.class.getResource("/GizmosBackgrounds/MainGameboard.png"));
            blueEnergyImage = ImageIO.read(BoardUI.class.getResource("/Energy Spheres/blueSphere.png"));
            redEnergyImage = ImageIO.read(BoardUI.class.getResource("/Energy Spheres/redSphere.png"));
            yellowEnergyImage = ImageIO.read(BoardUI.class.getResource("/Energy Spheres/yellowSphere.png"));
            blackEnergyImage = ImageIO.read(BoardUI.class.getResource("/Energy Spheres/blackSphere.png"));
            // tier 3 images
            sixB63 = ImageIO.read(BoardUI.class.getResource("/Level 3/76.png"));
            eightB43 = ImageIO.read(BoardUI.class.getResource("/Level 3/77.png"));
            fourB43 = ImageIO.read(BoardUI.class.getResource("/Level 3/78.png"));
            sumA73 = ImageIO.read(BoardUI.class.getResource("/Level 3/79.png"));
            fiveBl53 = ImageIO.read(BoardUI.class.getResource("/Level 3/80.png"));
            sevenBl43 = ImageIO.read(BoardUI.class.getResource("/Level 3/81.png"));
            fourBl43 = ImageIO.read(BoardUI.class.getResource("/Level 3/82.png"));
            energyA73 = ImageIO.read(BoardUI.class.getResource("/Level 3/83.png"));
            sixY63 = ImageIO.read(BoardUI.class.getResource("/Level 3/84.png"));
            eightY43 = ImageIO.read(BoardUI.class.getResource("/Level 3/85.png"));
            fiveY53 = ImageIO.read(BoardUI.class.getResource("/Level 3/86.png"));
            sumA73_2 = ImageIO.read(BoardUI.class.getResource("/Level 3/87.png"));
            fiveR53 = ImageIO.read(BoardUI.class.getResource("/Level 3/88.png"));
            sevenR43 = ImageIO.read(BoardUI.class.getResource("/Level 3/89.png"));
            fiveBl53_2 = ImageIO.read(BoardUI.class.getResource("/Level 3/90.png"));
            energyA73_2 = ImageIO.read(BoardUI.class.getResource("/Level 3/91.png"));
            fiveB53 = ImageIO.read(BoardUI.class.getResource("/Level 3/92.png"));
            fourB43_2 = ImageIO.read(BoardUI.class.getResource("/Level 3/93.png"));
            fiveB53_2 = ImageIO.read(BoardUI.class.getResource("/Level 3/94.png"));
            fiveB53_3 = ImageIO.read(BoardUI.class.getResource("/Level 3/95.png"));
            sixB63_2 = ImageIO.read(BoardUI.class.getResource("/Level 3/96.png"));
            fiveBl53_3 = ImageIO.read(BoardUI.class.getResource("/Level 3/97.png"));
            fourBl43_2 = ImageIO.read(BoardUI.class.getResource("/Level 3/98.png"));
            sevenBl73 = ImageIO.read(BoardUI.class.getResource("/Level 3/99.png"));
            sixBl63 = ImageIO.read(BoardUI.class.getResource("/Level 3/100.png"));
            sixR63 = ImageIO.read(BoardUI.class.getResource("/Level 3/101.png"));
            fourR43 = ImageIO.read(BoardUI.class.getResource("/Level 3/102.png"));
            fourR43_2 = ImageIO.read(BoardUI.class.getResource("/Level 3/103.png"));
            fiveR53_2 = ImageIO.read(BoardUI.class.getResource("/Level 3/104.png"));
            fiveR53_3 = ImageIO.read(BoardUI.class.getResource("/Level 3/105.png"));
            sevenR73 = ImageIO.read(BoardUI.class.getResource("/Level 3/106.png"));
            fourY43 = ImageIO.read(BoardUI.class.getResource("/Level 3/107.png"));
            fourY43_2 = ImageIO.read(BoardUI.class.getResource("/Level 3/108.png"));
            fiveY53_2 = ImageIO.read(BoardUI.class.getResource("/Level 3/109.png"));
            sixY63_2 = ImageIO.read(BoardUI.class.getResource("/Level 3/110.png"));
            fiveY53_3 = ImageIO.read(BoardUI.class.getResource("/Level 3/111.png"));
            //tier 1 buffered images
            BLD1BLK11BLUPICK = ImageIO.read(BoardUI.class.getResource("/Level 1/1BB.png"));
            BLD1BLK11RED1VICTPT = ImageIO.read(BoardUI.class.getResource("/Level 1/1BB_.png"));
            CNVRT1BLK11FFYLWNULL = ImageIO.read(BoardUI.class.getResource("/Level 1/1BC.png"));
            CNVRT1BLK11FFREDNULL = ImageIO.read(BoardUI.class.getResource("/Level 1/1BC_.png"));
            FILE1BLK11FILEPICK = ImageIO.read(BoardUI.class.getResource("/Level 1/1BF.png"));
            BLD1BLU11BLDYLWPICK = ImageIO.read(BoardUI.class.getResource("/Level 1/1BlB.png"));
            BLD1BLK11BLDBLK1VICTPT = ImageIO.read(BoardUI.class.getResource("/Level 1/1BlB_.png"));
            CNVRT1BLU11FFREDNULL = ImageIO.read(BoardUI.class.getResource("/Level 1/1BlC.png"));
            CNVRT1BLU11FFYLWNULL = ImageIO.read(BoardUI.class.getResource("/Level 1/1BlC_.png"));
            FILE1BLU11FILEPICK = ImageIO.read(BoardUI.class.getResource("/Level 1/1BlF.png"));
            PICK1BLU11REDRAND = ImageIO.read(BoardUI.class.getResource("/Level 1/1BlP.png"));
            PICK1BLU11BLKRAND = ImageIO.read(BoardUI.class.getResource("/Level 1/1BlP_.png"));
            INC1BLU11101 = ImageIO.read(BoardUI.class.getResource("/Level 1/1BlU.png"));
            INC1BLU11011 = ImageIO.read(BoardUI.class.getResource("/Level 1/1BlU_.png"));
            PICK1BLK11PICKBLKRAND = ImageIO.read(BoardUI.class.getResource("/Level 1/1BP.png"));
            PICK1BLK11PICKYLWRAND = ImageIO.read(BoardUI.class.getResource("/Level 1/1BP_.png"));
            INC1BLK11101 = ImageIO.read(BoardUI.class.getResource("/Level 1/1BU.png"));
            INC1BLK11011 = ImageIO.read(BoardUI.class.getResource("/Level 1/1BU_.png"));
            BLD1RED11BLKPICK = ImageIO.read(BoardUI.class.getResource("/Level 1/1RB.png"));
            BLD1RED11YLW1VICTPT = ImageIO.read(BoardUI.class.getResource("/Level 1/1RB_.png"));
            CNVRT1RED11FFBLUNULL = ImageIO.read(BoardUI.class.getResource("/Level 1/1RC.png"));
            CNVRT1RED11FFBLKNULL = ImageIO.read(BoardUI.class.getResource("/Level 1/1RC_.png"));
            FILE1RED11FILEPICK = ImageIO.read(BoardUI.class.getResource("/Level 1/1RF.png"));
            PICK1RED11BLURAND = ImageIO.read(BoardUI.class.getResource("/Level 1/1RP.png"));
            PICK1RED11YLWRAND = ImageIO.read(BoardUI.class.getResource("/Level 1/1RP_.png"));
            INC1RED11011 = ImageIO.read(BoardUI.class.getResource("/Level 1/1RU.png"));
            INC1RED11101 = ImageIO.read(BoardUI.class.getResource("/Level 1/1RU_.png"));
            BLD1YLW11BLDREDPICK = ImageIO.read(BoardUI.class.getResource("/Level 1/1YB.png"));
            BLD1YLW11BLDBLU1VICTPT = ImageIO.read(BoardUI.class.getResource("/Level 1/1YB_.png"));
            CNVRT1YLW11FFBLKNULL = ImageIO.read(BoardUI.class.getResource("/Level 1/1YC.png"));
            CNVRT1YLW11FFBLUNULL = ImageIO.read(BoardUI.class.getResource("/Level 1/1YC_.png"));
            FILE1YLW11FILEPICK = ImageIO.read(BoardUI.class.getResource("/Level 1/1YF.png"));
            PICK1YLW11PICKBLKRAND = ImageIO.read(BoardUI.class.getResource("/Level 1/1YP.png"));
            PICK1YLW11PICKREDRAND = ImageIO.read(BoardUI.class.getResource("/Level 1/1YP_.png"));
            INCYLW11101 = ImageIO.read(BoardUI.class.getResource("/Level 1/1YU.png"));
            INCYLW11011 = ImageIO.read(BoardUI.class.getResource("/Level 1/1YU_.png"));
            t1 = ImageIO.read(BoardUI.class.getResource("/Level 2/2BB.png"));
            t2 = ImageIO.read(BoardUI.class.getResource("/Level 2/2BB_++.png"));
            t3 = ImageIO.read(BoardUI.class.getResource("/Level 2/2BB_+.png"));
            t4 = ImageIO.read(BoardUI.class.getResource("/Level 2/2BB_.png"));
            t5 = ImageIO.read(BoardUI.class.getResource("/Level 2/2BC.png"));
            t6 = ImageIO.read(BoardUI.class.getResource("/Level 2/2BC_+.png"));
            t7 = ImageIO.read(BoardUI.class.getResource("/Level 2/2BC_.png"));
            t8 = ImageIO.read(BoardUI.class.getResource("/Level 2/2BlB.png"));
            t9 = ImageIO.read(BoardUI.class.getResource("/Level 2/2BlB_++.png"));
            t10 = ImageIO.read(BoardUI.class.getResource("/Level 2/2BlB_+.png"));
            t11 = ImageIO.read(BoardUI.class.getResource("/Level 2/2BlB_.png"));
            t12 = ImageIO.read(BoardUI.class.getResource("/Level 2/2BlC.png"));
            t13 = ImageIO.read(BoardUI.class.getResource("/Level 2/2BlC_+.png"));
            t14 = ImageIO.read(BoardUI.class.getResource("/Level 2/2BlC_.png"));
            t15 = ImageIO.read(BoardUI.class.getResource("/Level 2/2BlP.png"));
            t16 = ImageIO.read(BoardUI.class.getResource("/Level 2/2BlU.png"));
            t17 = ImageIO.read(BoardUI.class.getResource("/Level 2/2BP.png"));
            t18 = ImageIO.read(BoardUI.class.getResource("/Level 2/2BU.png"));
            t19 = ImageIO.read(BoardUI.class.getResource("/Level 2/2RB.png"));
            t20 = ImageIO.read(BoardUI.class.getResource("/Level 2/2RB_++.png"));
            t21 = ImageIO.read(BoardUI.class.getResource("/Level 2/2RB_+.png"));
            t22 = ImageIO.read(BoardUI.class.getResource("/Level 2/2RB_.png"));
            t23 = ImageIO.read(BoardUI.class.getResource("/Level 2/2RC.png"));
            t24 = ImageIO.read(BoardUI.class.getResource("/Level 2/2RC_+.png"));
            t25 = ImageIO.read(BoardUI.class.getResource("/Level 2/2RC_.png"));
            t26 = ImageIO.read(BoardUI.class.getResource("/Level 2/2RP.png"));
            t27 = ImageIO.read(BoardUI.class.getResource("/Level 2/2RU.png"));
            t28 = ImageIO.read(BoardUI.class.getResource("/Level 2/2YB.png"));
            t29 = ImageIO.read(BoardUI.class.getResource("/Level 2/2YB_++.png"));
            t30 = ImageIO.read(BoardUI.class.getResource("/Level 2/2YB_+.png"));
            t31 = ImageIO.read(BoardUI.class.getResource("/Level 2/2YB_.png"));
            t32 = ImageIO.read(BoardUI.class.getResource("/Level 2/2YC.png"));
            t33 = ImageIO.read(BoardUI.class.getResource("/Level 2/2YC_+.png"));
            t34 = ImageIO.read(BoardUI.class.getResource("/Level 2/2YC_.png"));
            t35 = ImageIO.read(BoardUI.class.getResource("/Level 2/2YP.png"));
            t36 = ImageIO.read(BoardUI.class.getResource("/Level 2/2YU.png"));
            addMouseListener(this);
        } catch (Exception e) {
            System.out.println("BoardUI error");
            e.printStackTrace();
        }
        //tier 1 cards instantiation + adding to deck1
        //tier 1 cards
        d1.add(new EffectCard(1, Card.Energy.BLACK, 1, 1, EffectCard.Triggers.BUILD_BLUE, EffectCard.Effects.PICK, BLD1BLK11BLUPICK));
        d1.add(new EffectCard(1, Card.Energy.BLACK, 1, 1, EffectCard.Triggers.BUILD_RED, EffectCard.Effects.ONEVICTORYPOINT, BLD1BLK11RED1VICTPT));
        d1.add(new ConverterCard(1, Card.Energy.BLACK, 1, 1, false, false, Card.Energy.YELLOW, null, CNVRT1BLK11FFYLWNULL));
        d1.add(new ConverterCard(1, Card.Energy.BLACK, 1, 1, false, false, Card.Energy.RED, null, CNVRT1BLK11FFREDNULL));
        d1.add(new EffectCard(1, Card.Energy.BLACK, 1, 1, EffectCard.Triggers.FILE, EffectCard.Effects.PICK, FILE1BLK11FILEPICK));
        d1.add(new EffectCard(1, Card.Energy.BLUE, 1, 1, EffectCard.Triggers.BUILD_YELLOW, EffectCard.Effects.PICK, BLD1BLU11BLDYLWPICK));
        d1.add(new EffectCard(1, Card.Energy.BLUE, 1, 1, EffectCard.Triggers.BUILD_BLACK, EffectCard.Effects.ONEVICTORYPOINT, BLD1BLK11BLDBLK1VICTPT));
        d1.add(new ConverterCard(1, Card.Energy.BLUE, 1, 1, false, false, Card.Energy.RED, null, CNVRT1BLU11FFREDNULL));
        d1.add(new ConverterCard(1, Card.Energy.BLUE, 1, 1, false, false, Card.Energy.YELLOW, null, CNVRT1BLU11FFYLWNULL));
        d1.add(new EffectCard(1, Card.Energy.BLUE, 1, 1, EffectCard.Triggers.FILE, EffectCard.Effects.PICK, FILE1BLU11FILEPICK));
        d1.add(new EffectCard(1, Card.Energy.BLUE, 1, 1, EffectCard.Triggers.PICK_RED, EffectCard.Effects.RANDOMDRAW, PICK1BLU11REDRAND));
        d1.add(new EffectCard(1, Card.Energy.BLUE, 1, 1, EffectCard.Triggers.PICK_BLACK, EffectCard.Effects.RANDOMDRAW, PICK1BLU11BLKRAND));
        d1.add(new IncrementCard(1, Card.Energy.BLUE, 1, 1, 1, 0, 1, INC1BLU11101));
        d1.add(new IncrementCard(1, Card.Energy.BLUE, 1, 1, 0, 1, 1, INC1BLU11011));
        d1.add(new EffectCard(1, Card.Energy.BLACK, 1, 1, EffectCard.Triggers.PICK_BLACK, EffectCard.Effects.RANDOMDRAW, PICK1BLK11PICKBLKRAND));
        d1.add(new EffectCard(1, Card.Energy.BLACK, 1, 1, EffectCard.Triggers.PICK_YELLOW, EffectCard.Effects.RANDOMDRAW, PICK1BLK11PICKYLWRAND));
        d1.add(new IncrementCard(1, Card.Energy.BLACK, 1, 1, 1, 0, 1, INC1BLK11101));
        d1.add(new IncrementCard(1, Card.Energy.BLACK, 1, 1, 0, 1, 1, INC1BLK11011));
        d1.add(new EffectCard(1, Card.Energy.RED, 1, 1, EffectCard.Triggers.BUILD_BLACK, EffectCard.Effects.PICK, BLD1RED11BLKPICK));
        d1.add(new EffectCard(1, Card.Energy.RED, 1, 1, EffectCard.Triggers.BUILD_YELLOW, EffectCard.Effects.ONEVICTORYPOINT, BLD1RED11YLW1VICTPT));
        d1.add(new ConverterCard(1, Card.Energy.RED, 1, 1, false, false, Card.Energy.BLUE, null, CNVRT1RED11FFBLUNULL));
        d1.add(new ConverterCard(1, Card.Energy.RED, 1, 1, false, false, Card.Energy.BLACK, null, CNVRT1RED11FFBLKNULL));
        d1.add(new EffectCard(1, Card.Energy.RED, 1, 1, EffectCard.Triggers.FILE, EffectCard.Effects.PICK, FILE1RED11FILEPICK));
        d1.add(new EffectCard(1, Card.Energy.RED, 1, 1, EffectCard.Triggers.PICK_BLUE, EffectCard.Effects.RANDOMDRAW, PICK1RED11BLURAND));
        d1.add(new EffectCard(1, Card.Energy.RED, 1, 1, EffectCard.Triggers.PICK_YELLOW, EffectCard.Effects.RANDOMDRAW, PICK1RED11YLWRAND));
        d1.add(new IncrementCard(1, Card.Energy.RED, 1, 1, 0, 1, 1, INC1RED11011));
        d1.add(new IncrementCard(1, Card.Energy.RED, 1, 1, 1, 0, 1, INC1RED11101));
        d1.add(new EffectCard(1, Card.Energy.YELLOW, 1, 1, EffectCard.Triggers.BUILD_RED, EffectCard.Effects.PICK, BLD1YLW11BLDREDPICK));
        d1.add(new EffectCard(1, Card.Energy.YELLOW, 1, 1, EffectCard.Triggers.BUILD_BLUE, EffectCard.Effects.ONEVICTORYPOINT, BLD1YLW11BLDBLU1VICTPT));
        d1.add(new ConverterCard(1, Card.Energy.YELLOW, 1, 1, false, false, Card.Energy.BLACK, null, CNVRT1YLW11FFBLKNULL));
        d1.add(new ConverterCard(1, Card.Energy.YELLOW, 1, 1, false, false, Card.Energy.BLUE, null, CNVRT1YLW11FFBLUNULL));
        d1.add(new EffectCard(1, Card.Energy.YELLOW, 1, 1, EffectCard.Triggers.FILE, EffectCard.Effects.PICK, FILE1YLW11FILEPICK));
        d1.add(new EffectCard(1, Card.Energy.YELLOW, 1, 1, EffectCard.Triggers.PICK_BLACK, EffectCard.Effects.RANDOMDRAW, PICK1YLW11PICKBLKRAND));
        d1.add(new EffectCard(1, Card.Energy.YELLOW, 1, 1, EffectCard.Triggers.PICK_RED, EffectCard.Effects.RANDOMDRAW, PICK1YLW11PICKREDRAND));
        d1.add(new IncrementCard(1, Card.Energy.YELLOW, 1, 1, 1, 0, 1, INCYLW11101));
        d1.add(new IncrementCard(1, Card.Energy.YELLOW, 1, 1, 0, 1, 1, INCYLW11011));
        // Level 3 Card instantiation - HR reminder for negate choices: 0 = research, 1 = file
        d3.add(new DiscountCard(6, Card.Energy.BLACK, 6, 3, DiscountCard.DiscountType.RESEARCH, sixB63)); //76.png
        d3.add(new NegateCard(8, Card.Energy.BLACK, 4, 3, 0, eightB43)); //77.png
        d3.add(new IncrementCard(4, Card.Energy.BLACK, 4, 3, 0, 0, 4, fourB43)); //78.png
        d3.add(new UpgradeCard(Game.currentPlayer.getVictoryPoints(), Card.Energy.ALL, 7, 3, sumA73)); //79.png
        d3.add(new DiscountCard(5, Card.Energy.BLUE, 5, 3, DiscountCard.DiscountType.FILE, fiveBl53)); //80.png
        d3.add(new NegateCard(7, Card.Energy.BLUE, 4, 3, 1, sevenBl43)); //81.png
        d3.add(new IncrementCard(4, Card.Energy.BLUE, 4, 3, 0, 0, 4, fourBl43)); //82.png
        d3.add(new UpgradeCard(Game.currentPlayer.getEnergy().size(), Card.Energy.ALL, 7, 3, energyA73)); //83.png
        d3.add(new DiscountCard(6, Card.Energy.YELLOW, 6, 3, DiscountCard.DiscountType.RESEARCH, sixY63)); //84.png
        d3.add(new NegateCard(8, Card.Energy.YELLOW, 4, 3, 0, eightY43)); //85.png
        d3.add(new DiscountCard(5, Card.Energy.YELLOW, 5, 3, DiscountCard.DiscountType.FILE, fiveY53)); //86.png
        d3.add(new UpgradeCard(Game.currentPlayer.getVictoryPoints(), Card.Energy.ALL, 7, 3, sumA73_2)); //87.png
        d3.add(new DiscountCard(5, Card.Energy.RED, 5, 3, DiscountCard.DiscountType.FILE, fiveR53)); //88.png
        d3.add(new NegateCard(7, Card.Energy.RED, 4, 3, 1, sevenR43)); //89.png
        d3.add(new DiscountCard(5, Card.Energy.BLUE, 5, 3, DiscountCard.DiscountType.TIER2, fiveBl53_2)); //90.png
        d3.add(new UpgradeCard(Game.currentPlayer.getEnergy().size(), Card.Energy.ALL, 7, 3, energyA73_2)); //91.png
        d3.add(new ConverterCard(5, Card.Energy.BLACK, 5, 3, true, true, Card.Energy.BLUE, Card.Energy.YELLOW, fiveB53)); //92.png
        d3.add(new EffectCard(4, Card.Energy.BLACK, 4, 3, EffectCard.Triggers.FILE, EffectCard.Effects.ONEVICTORYPOINT, fourB43_2)); //93.png
        d3.add(new EffectCard(5, Card.Energy.BLACK, 5, 3, EffectCard.Triggers.BUILD_REDBLUE, EffectCard.Effects.TWOVICTORYPOINT, fiveB53_2)); //94.png
        d3.add(new EffectCard(5, Card.Energy.BLACK, 5, 3, EffectCard.Triggers.BUILD_BLUEYELLOW, EffectCard.Effects.FILEACTION, fiveB53_3)); //95.png
        d3.add(new EffectCard(6, Card.Energy.BLACK, 6, 3, EffectCard.Triggers.BUILD_TIERTWO, EffectCard.Effects.PICK2, sixB63_2)); //96.png
        d3.add(new ConverterCard(5, Card.Energy.BLUE, 5, 3, true, true, Card.Energy.BLACK, Card.Energy.RED, fiveBl53_3)); //97.png
        d3.add(new EffectCard(4, Card.Energy.BLUE, 4, 3, EffectCard.Triggers.FILE, EffectCard.Effects.RANDOMDRAW3, fourBl43_2)); //98.png
        d3.add(new EffectCard(7, Card.Energy.BLUE, 7, 3, EffectCard.Triggers.BUILD_YELLOWRED, EffectCard.Effects.RESEARCH, sevenBl73)); //99.png
        d3.add(new EffectCard(6, Card.Energy.BLUE, 6, 3, EffectCard.Triggers.BUILD_YELLOWRED, EffectCard.Effects.BUILDTIERONEFREE, sixBl63)); //100.png
        d3.add(new EffectCard(6, Card.Energy.RED, 6, 3, EffectCard.Triggers.BUILD_TIERTWO, EffectCard.Effects.PICK2, sixR63)); //101.png
        d3.add(new ConverterCard(4, Card.Energy.RED, 4, 3, false, false, Card.Energy.ALL, null, fourR43)); //102.png
        d3.add(new EffectCard(4, Card.Energy.RED, 4, 3, EffectCard.Triggers.FILE, EffectCard.Effects.ONEVICTORYPOINT, fourR43_2)); //103.png
        d3.add(new EffectCard(5, Card.Energy.RED, 5, 3, EffectCard.Triggers.BUILD_ARCHIVED, EffectCard.Effects.TWOVICTORYPOINT, fiveR53_2)); //104.png
        d3.add(new EffectCard(5, Card.Energy.RED, 5, 3, EffectCard.Triggers.BUILD_YELLOWBLACK, EffectCard.Effects.TWOVICTORYPOINT, fiveR53_3)); //105.png
        d3.add(new EffectCard(7, Card.Energy.RED, 7, 3, EffectCard.Triggers.BUILD_BLUEBLACK, EffectCard.Effects.RESEARCH, sevenR73)); //106.png
        d3.add(new ConverterCard(4, Card.Energy.YELLOW, 4, 3, false, false, Card.Energy.ALL, null, fourY43)); //107.png this is not initialized right, i have questions
        d3.add(new EffectCard(4, Card.Energy.YELLOW, 4, 3, EffectCard.Triggers.FILE, EffectCard.Effects.RANDOMDRAW3, fourY43_2)); //108.png
        d3.add(new EffectCard(5, Card.Energy.YELLOW, 5, 3, EffectCard.Triggers.BUILD_ARCHIVED, EffectCard.Effects.TWOVICTORYPOINT, fiveY53_2)); //109.png
        d3.add(new EffectCard(6, Card.Energy.YELLOW, 6, 3, EffectCard.Triggers.BUILD_BLUEBLACK, EffectCard.Effects.BUILDTIERONEFREE, sixY63_2)); //110.png
        d3.add(new EffectCard(5, Card.Energy.YELLOW, 5, 3, EffectCard.Triggers.BUILD_BLACKRED, EffectCard.Effects.FILEACTION, fiveY53_3)); //111.png
        // Level 2 Cards - NA
        d2.add(new EffectCard(3, Card.Energy.BLACK, 3, 2, EffectCard.Triggers.BUILD_ARCHIVED, EffectCard.Effects.PICK2, t1));
        d2.add(new EffectCard(3, Card.Energy.BLACK, 3, 2, EffectCard.Triggers.BUILD_BLUEYELLOW, EffectCard.Effects.ONEVICTORYPOINT, t2));
        d2.add(new EffectCard(2, Card.Energy.BLACK, 2, 2, EffectCard.Triggers.BUILD_YELLOWRED, EffectCard.Effects.PICK, t3));
        d2.add(new EffectCard(2, Card.Energy.BLACK, 2, 2, EffectCard.Triggers.BUILD_REDBLUE, EffectCard.Effects.PICK, t4));
        d2.add(new ConverterCard(3, Card.Energy.BLACK, 3, 2, true, false, Card.Energy.RED, null, t5));
        d2.add(new ConverterCard(3, Card.Energy.BLACK, 3, 2, true, false, Card.Energy.YELLOW, null, t6));
        d2.add(new ConverterCard(2, Card.Energy.BLACK, 2, 2, false, true, Card.Energy.BLUE, Card.Energy.BLUE, t7));
        d2.add(new EffectCard(3, Card.Energy.BLUE, 3, 2, EffectCard.Triggers.BUILD_ARCHIVED, EffectCard.Effects.PICK2, t8));
        d2.add(new EffectCard(3, Card.Energy.BLUE, 3, 2, EffectCard.Triggers.BUILD_BLACKRED, EffectCard.Effects.ONEVICTORYPOINT, t9));
        d2.add(new EffectCard(2, Card.Energy.BLUE, 2, 2, EffectCard.Triggers.BUILD_YELLOWRED, EffectCard.Effects.PICK, t10));
        d2.add(new EffectCard(2, Card.Energy.BLUE, 2, 2, EffectCard.Triggers.BUILD_YELLOWBLACK, EffectCard.Effects.PICK, t11));
        d2.add(new ConverterCard(3, Card.Energy.BLUE, 3, 2, true, false, Card.Energy.RED, null, t12));
        d2.add(new ConverterCard(3, Card.Energy.BLUE, 3, 2, true, false, Card.Energy.YELLOW, null, t13));
        d2.add(new ConverterCard(2, Card.Energy.BLUE, 2, 2, false, true, Card.Energy.BLACK, Card.Energy.BLACK, t14));
        d2.add(new EffectCard(2, Card.Energy.BLUE, 2, 2, EffectCard.Triggers.PICK_YELLOWBLACK, EffectCard.Effects.RANDOMDRAW, t15));
        d2.add(new IncrementCard(3, Card.Energy.BLUE, 3, 2, 2, 1, 2, t16));
        d2.add(new EffectCard(2, Card.Energy.BLACK, 2, 2, EffectCard.Triggers.PICK_YELLOWRED, EffectCard.Effects.RANDOMDRAW, t17));
        d2.add(new IncrementCard(3, Card.Energy.BLACK, 3, 2, 2, 1, 2, t18));
        d2.add(new EffectCard(3, Card.Energy.RED, 3, 2, EffectCard.Triggers.BUILD_ARCHIVED, EffectCard.Effects.PICK2, t19));
        d2.add(new EffectCard(3, Card.Energy.RED, 3, 2, EffectCard.Triggers.BUILD_YELLOWBLACK, EffectCard.Effects.ONEVICTORYPOINT, t20));
        d2.add(new EffectCard(2, Card.Energy.RED, 2, 2, EffectCard.Triggers.BUILD_BLUEYELLOW, EffectCard.Effects.PICK, t21));
        d2.add(new EffectCard(2, Card.Energy.RED, 2, 2, EffectCard.Triggers.BUILD_BLUEBLACK, EffectCard.Effects.PICK, t22));
        d2.add(new ConverterCard(3, Card.Energy.RED, 3, 2, true, false, Card.Energy.BLACK, null, t23));
        d2.add(new ConverterCard(3, Card.Energy.RED, 3, 2, true, false, Card.Energy.BLUE, null, t24));
        d2.add(new ConverterCard(2, Card.Energy.RED, 2, 2, false, true, Card.Energy.YELLOW, Card.Energy.YELLOW, t25));
        d2.add(new EffectCard(2, Card.Energy.RED, 2, 2, EffectCard.Triggers.PICK_BLUEBLACK, EffectCard.Effects.RANDOMDRAW, t26));
        d2.add(new IncrementCard(3, Card.Energy.RED, 3, 2, 2, 1, 2, t27));
        d2.add(new EffectCard(2, Card.Energy.YELLOW, 2, 2, EffectCard.Triggers.BUILD_BLACKRED, EffectCard.Effects.PICK, t28));
        d2.add(new EffectCard(3, Card.Energy.YELLOW, 3, 2, EffectCard.Triggers.BUILD_REDBLUE, EffectCard.Effects.ONEVICTORYPOINT, t29));
        d2.add(new EffectCard(2, Card.Energy.YELLOW, 2, 2, EffectCard.Triggers.BUILD_BLUEBLACK, EffectCard.Effects.PICK, t30));
        d2.add(new EffectCard(3, Card.Energy.YELLOW, 3, 2, EffectCard.Triggers.BUILD_ARCHIVED, EffectCard.Effects.PICK2, t31));
        d2.add(new ConverterCard(3, Card.Energy.YELLOW, 3, 2, true, false, Card.Energy.BLUE, null, t32));
        d2.add(new ConverterCard(3, Card.Energy.YELLOW, 3, 2, true, false, Card.Energy.BLACK, null, t33));
        d2.add(new ConverterCard(2, Card.Energy.YELLOW, 2, 2, false, true, Card.Energy.RED, Card.Energy.RED, t34));
        d2.add(new EffectCard(2, Card.Energy.YELLOW, 2, 2, EffectCard.Triggers.BUILD_REDBLUE, EffectCard.Effects.RANDOMDRAW, t35));
        d2.add(new IncrementCard(3, Card.Energy.YELLOW, 3, 2, 2, 1, 2, t36));
        Collections.shuffle(Game.decks.get(1).getConditionList());
        Collections.shuffle(Game.decks.get(2).getConditionList());
        Collections.shuffle(Game.decks.get(3).getConditionList());
    }

    public void paint(Graphics g) {
        super.paint(g);
        drawGameBoard(g);
        drawDeckOne(g);
        drawDeckTwo(g);
        drawDeckThree(g);
        drawEnergyDispenser(g);
        testDrawDash(g);
        //drawEnergy(g, GizmosRunner.game.currentPlayer);
        drawRowOfEnergy(g, GizmosRunner.game.energyRow);
        drawAdditionalPoints(g);
        drawCards(g);
        if (Game.currentPlayer.getReactionList().size() > 0) {
            drawList(g);
        }
        if (Game.currentPlayer.isResearching()) {
            drawResearch(g);
        }
        if (Game.currentPlayer.isConverting()) {
            drawConversionChoices(g);
        }
        if (fileOrBuild) {
            g.drawImage(file, getWidth() * 96 / 1350, getHeight() * 552 / 729, getWidth() * 80 / 1350, getHeight() * 100 / 729, null);
            g.drawImage(build, getWidth() * 196 / 1350, getHeight() * 552 / 729, getWidth() * 80 / 1350, getHeight() * 100 / 729, null);
        }
        //if(buildChoice){
        //	drawBuildChoices(g);
        //}
        g.drawImage(endButton, getWidth() * 1187 / 1350, getHeight() * 69 / 729, getWidth() * 100 / 1350, getWidth() * 100 / 1350, null);
        if (Game.chosenAction) {
            drawChoices(g);
        }
        if (Game.gameEnd) {
            drawEnd(g);
        }
        //drawDashboard(g);
        repaint();
    }

    public void drawDeckOne(Graphics g) {
        if (d1.getConditionList().size() > 4) {
            g.drawImage(LV1, getWidth() * 558 / 1924, getHeight() * 617 / 1061, getWidth() * 95 / 1920, getHeight() * 9 / 100, null);
            g.drawImage(d1.getConditionList().get(3).getImage(), getWidth() * (675 + 120 * 3) / 1924, getHeight() * 617 / 1061, getWidth() * 95 / 1920, getHeight() * 9 / 100, null);
            g.drawImage(d1.getConditionList().get(2).getImage(), getWidth() * (675 + 120 * 2) / 1924, getHeight() * 617 / 1061, getWidth() * 95 / 1920, getHeight() * 9 / 100, null);
            g.drawImage(d1.getConditionList().get(1).getImage(), getWidth() * (675 + 120) / 1924, getHeight() * 617 / 1061, getWidth() * 95 / 1920, getHeight() * 9 / 100, null);
            g.drawImage(d1.getConditionList().get(0).getImage(), getWidth() * 675 / 1924, getHeight() * 617 / 1061, getWidth() * 95 / 1920, getHeight() * 9 / 100, null);
        } else {
            for (int i = 0; i < d1.getConditionList().size(); i++) {
                int x = getWidth() * (675 + 120 * i) / 1924;
                int y = getHeight() * 617 / 1061;
                g.drawImage(d1.getConditionList().get(i).getImage(), x, y, getWidth() * 95 / 1920, getHeight() * 9 / 100, null);
            }
        }
    }

    public void drawDeckTwo(Graphics g) {
        if (d2.getConditionList().size() > 3) {
            g.drawImage(LV2, getWidth() * 568 / 1924, getHeight() * 501 / 1061, getWidth() * 95 / 1920, getHeight() * 9 / 100, null);
            g.drawImage(d2.getConditionList().get(0).getImage(), getWidth() * 679 / 1924, getHeight() * 501 / 1061, getWidth() * 95 / 1920, getHeight() * 9 / 100, null);
            g.drawImage(d2.getConditionList().get(1).getImage(), getWidth() * 798 / 1924, getHeight() * 501 / 1061, getWidth() * 95 / 1920, getHeight() * 9 / 100, null);
            g.drawImage(d2.getConditionList().get(2).getImage(), getWidth() * 917 / 1924, getHeight() * 501 / 1061, getWidth() * 95 / 1920, getHeight() * 9 / 100, null);
        } else {
            for (int i = 0; i < d2.getConditionList().size(); i++) {
                int x = getWidth() * (501 + 120 * i) / 1924;
                int y = getHeight() * 501 / 1061;
                g.drawImage(d2.getConditionList().get(i).getImage(), x, y, getWidth() * 95 / 1920, getHeight() * 9 / 100, null);
            }
        }
    }

    public void drawDeckThree(Graphics g) {
        if (d3.getConditionList().size() > 2) {
            g.drawImage(LV3, getWidth() * 59 / 195, getHeight() * 73 / 203, getWidth() * 95 / 1920, getHeight() * 9 / 100, null);
            g.drawImage(d3.getConditionList().get(0).getImage(), getWidth() * (559 + 120) / 1924, getHeight() * 382 / 1061, getWidth() * 95 / 1920, getHeight() * 9 / 100, null);
            g.drawImage(d3.getConditionList().get(1).getImage(), getWidth() * (559 + 120 * 2) / 1924, getHeight() * 382 / 1061, getWidth() * 95 / 1920, getHeight() * 9 / 100, null);
        } else {
            for (int i = 0; i < d3.getConditionList().size(); i++) {
                int x = getWidth() * (559 + 120 * i) / 1924;
                int y = getHeight() * 382 / 1061;
                g.drawImage(d3.getConditionList().get(i).getImage(), x, y, getWidth() * 95 / 1920, getHeight() * 9 / 100, null);
            }
        }
    }

    public void drawGameBoard(Graphics g) //HR
    {
        g.drawImage(gameBoard, 0, 0, getWidth(), getHeight(), null);
    }

    public void drawList(Graphics g) {
        ArrayList<String> list = Game.currentPlayer.getReactionList();
        for (int i = 0; i < list.size(); i++) {
            g.setColor(Color.white);
            g.fillRect(getWidth() * 1245 / 1924, getHeight() * 475 / 1061 + (i * getHeight() * 50 / 1061), getWidth() * 200 / 1924, getHeight() * 50 / 1061);
            g.setColor(Color.black);
            g.drawRect(getWidth() * 1245 / 1924, getHeight() * 475 / 1061 + (i * getHeight() * 50 / 1061), getWidth() * 200 / 1924, getHeight() * 50 / 1061);
            g.setFont(new Font("SansSerif", Font.PLAIN, getWidth() * 20 / 1924));
            g.drawString(list.get(i), getWidth() * 1245 / 1924, getHeight() * 500 / 1061 + (i * getHeight() * 50 / 1061));
        }
    }

    public void drawEnd(Graphics g) {
        ArrayList<Player> list = Game.getWinner();
        g.setColor(new Color(145, 53, 49));
        g.fillRect(getWidth() * 133 / 1924, getHeight() * 319 / 1061, getWidth() * 800 / 1924, getHeight() * 630 / 1061);
        g.setColor(Color.black);
        g.setFont(new Font("Serif", Font.BOLD, getWidth() * 100 / 1924));
        g.drawString("GAME OVER", getWidth() * 226 / 1924, getHeight() * 444 / 1061);
        //g.setFont(new Font("Serif",Font.BOLD,getWidth()*50/1924));
        //g.drawString("PLAYER 1 WINS!", getWidth()*348/1924, getHeight()*516/1061);
        g.setFont(new Font("SansSerif", Font.PLAIN, getWidth() * 40 / 1924));
        for (int i = 0; i < 4; i++) {
            g.drawString(list.get(i).getName() + ": " + list.get(i).getTotalScore() + " Points", getWidth() * 295 / 1924, getHeight() * (603 + i * 60) / 1061);
        }
    }

    public void drawEnergyDispenser(Graphics g) //HR
    {
        g.drawImage(energyDispenser, (getWidth() * 125) / 1920, (getHeight() * 315) / 1080, ((getWidth() * 425) / 1920), ((getHeight() * 425) / 1080), null);
    }

    public void drawRowOfEnergy(Graphics g, ArrayList<String> row) //HR
    {
        int x = getWidth() * 355 / 1920;
        int y = getHeight() * 530 / 1080;
        int z = 0;
        for (int i = 0; i < 6; i++) {
            if (row.get(i).equals("Blue")) {
                g.drawImage(blueEnergyImage, x + z, y, ((getWidth() * 30) / 1920), ((getHeight() * 30) / 1080), null);
            } else if (row.get(i).equals("Red")) {
                g.drawImage(redEnergyImage, x + z, y, ((getWidth() * 30) / 1920), ((getHeight() * 30) / 1080), null);
            } else if (row.get(i).equals("Yellow")) {
                g.drawImage(yellowEnergyImage, x + z, y, ((getWidth() * 30) / 1920), ((getHeight() * 30) / 1080), null);
            } else if (row.get(i).equals("Black")) {
                g.drawImage(blackEnergyImage, x + z, y, ((getWidth() * 30) / 1920), ((getHeight() * 30) / 1080), null);
            }
            z += ((getWidth() * 30) / 1920);
        }
    }

    //Jessica v
    public String chooseEnergy() {
        choosingEnergy = true;
        while (choosingEnergy) {
            try {
                Thread.sleep(300);
            } catch (Exception e) {
                System.out.println("chooose energy wait faiiilled");
            }
        }
        return choosenEnergy;//fill in later.
    }

    private void chooseEn(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        for (int i = 0; i < 6; i++) {
            if (y >= getHeight() * 495 / 1080 && y <= getHeight() * 530 / 1080 && x >= getWidth() * 355 / 1920 && x <= getWidth() * 535 / 1920) {
                choosenEnergy = Game.energyRow.get(i);
                Game.energyRow.remove(i);
                GizmosRunner.game.rowFiller(Game.energyDispenser, Game.energyRow);
                choosingEnergy = false;
                System.out.println("ran");
            }
        }
    }

    public void chooseTierOneCard(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();
        if (mx >= getWidth() * (675 + 120 * 3) / 1924 && mx <= getWidth() * (675 + 120 * 3 + getWidth() * 95 / 1920) / 1924 &&
                my >= getHeight() * 617 / 1061 && my <= (getHeight() * 617 / 1061) + getHeight() * 9 / 100) {
            Player.chosen = d1.getConditionList().get(3);
        } else if (mx >= getWidth() * (675 + 120 * 2) / 1924 && mx <= getWidth() * (675 + 120 * 2 + getWidth() * 95 / 1920) / 1924 &&
                my >= getHeight() * 617 / 1061 && my <= (getHeight() * 617 / 1061) + getHeight() * 9 / 100) {
            Player.chosen = d1.getConditionList().get(2);
        } else if (mx >= getWidth() * (675 + 120) / 1924 && mx <= getWidth() * (675 + 120 + getWidth() * 95 / 1920) / 1924 &&
                my >= getHeight() * 617 / 1061 && my <= (getHeight() * 617 / 1061) + getHeight() * 9 / 100) {
            Player.chosen = d1.getConditionList().get(1);
        } else if (mx >= getWidth() * 674 / 1924 && mx <= (getWidth() * 674 + getWidth() * 95 / 1920) / 1924 &&
                my >= getHeight() * 617 / 1061 && my <= (getHeight() * 617 / 1061) + getHeight() * 9 / 100) {
            Player.chosen = d1.getConditionList().get(0);
        } else {
            System.out.println("Choose Tier one card positioning failed");

        }
    }

    public void chooseAnyCard(MouseEvent e) {

        int mx = e.getX();
        int my = e.getY();
        if (my >= getHeight() * 617 / 1061 && my <= (getHeight() * 617 / 1061) + getHeight() * 9 / 100) {
            for (int i = 0; i < 4; i++) {
                if (mx >= getWidth() * (675 + 120 * i) / 1924 && mx <= getWidth() * (675 + 120 * i + getWidth() * 95 / 1920) / 1924) {
                    Player.chosen = d1.getConditionList().get(i);
                }
            }
            System.out.println("ChoseAnyCard click failed");
        } else if (my >= getHeight() * 501 / 1061 && my <= getHeight() * 501 / 1061 + getHeight() * 9 / 100) {
            for (int i = 0; i < 3; i++) {
                if (mx >= getWidth() * (680 + 118 * i) / 1924 && mx <= getWidth() * (680 + 118 * i) / 1924 + getWidth() * 95 / 1920) {
                    Player.chosen = d2.getConditionList().get(i);
                }
            }
        } else if (my >= getHeight() * 382 / 1061 && my <= getHeight() * 382 / 1061 + getHeight() * 9 / 100) {
            for (int i = 0; i < 2; i++) {
                if (mx >= getWidth() * (697 + 120 * i) / 1924 && mx <= getWidth() * (697 + 120 * i) / 1924 + getWidth() * 95 / 1920) {
                    Player.chosen = d3.getConditionList().get(i);
                }
            }
        } else {
            System.out.println("chooseAnyCard method positioning error");
        }
    }

    public void testDrawDash(Graphics g) {
        int ix = getWidth() * 485 / 1350;
        int iy = getHeight() * 59 / 729;
        int w = getWidth() * 627 / 1350;
        int h = getHeight() * 211 / 1061;
        g.setFont(new Font("SansSerif", Font.PLAIN, getWidth() * 20 / 1924));
        if (Game.currentPlayer == Game.p1) {
            g.drawString("Score: " + Game.currentPlayer.getTotalScore(), getWidth() * 405 / 1350, getHeight() * 129 / 729);
            g.drawString("VP count: " + Game.currentPlayer.getVictoryPoints(), getWidth() * 395 / 1350, getHeight() * 159 / 729);
            g.drawImage(dashBoard1, ix, iy, w, h, null);
        } else if (Game.currentPlayer == Game.p2) {
            g.drawString("Score: " + Game.currentPlayer.getTotalScore(), getWidth() * 405 / 1350, getHeight() * 129 / 729);
            g.drawString("VP count: " + Game.currentPlayer.getVictoryPoints(), getWidth() * 395 / 1350, getHeight() * 159 / 729);
            g.drawImage(dashBoard2, ix, iy, w, h, null);
        } else if (Game.currentPlayer == Game.p3) {
            g.drawString("Score: " + Game.currentPlayer.getTotalScore(), getWidth() * 405 / 1350, getHeight() * 129 / 729);
            g.drawString("VP count: " + Game.currentPlayer.getVictoryPoints(), getWidth() * 395 / 1350, getHeight() * 159 / 729);
            g.drawImage(dashBoard3, ix, iy, w, h, null);
        } else {
            g.drawString("Score: " + Game.currentPlayer.getTotalScore(), getWidth() * 405 / 1350, getHeight() * 129 / 729);
            g.drawString("VP count: " + Game.currentPlayer.getVictoryPoints(), getWidth() * 395 / 1350, getHeight() * 159 / 729);
            g.drawImage(dashBoard4, ix, iy, w, h, null);
        }
        int count = 0;
        for (int i = 0; i < 4 && Game.currentPlayer.getEnergy().size() > count; i++) {
            for (int o = 0; o < 4 && Game.currentPlayer.getEnergy().size() > count; o++) {
                if (Game.currentPlayer.getEnergy().get(count).equals("Blue")) {
                    g.drawImage(blueEnergyImage, getWidth() * 511 / 1350 + o * getWidth() * 15 / 1350, getHeight() * 110 / 729 + i * getWidth() * 15 / 1350, getWidth() * 15 / 1350, getWidth() * 15 / 1350, null);
                } else if (Game.currentPlayer.getEnergy().get(count).equals("Black")) {
                    g.drawImage(blackEnergyImage, getWidth() * 511 / 1350 + o * getWidth() * 15 / 1350, getHeight() * 110 / 729 + i * getWidth() * 15 / 1350, getWidth() * 15 / 1350, getWidth() * 15 / 1350, null);
                } else if (Game.currentPlayer.getEnergy().get(count).equals("Red")) {
                    g.drawImage(redEnergyImage, getWidth() * 511 / 1350 + o * getWidth() * 15 / 1350, getHeight() * 110 / 729 + i * getWidth() * 15 / 1350, getWidth() * 15 / 1350, getWidth() * 15 / 1350, null);
                } else {
                    g.drawImage(yellowEnergyImage, getWidth() * 511 / 1350 + o * getWidth() * 15 / 1350, getHeight() * 110 / 729 + i * getWidth() * 15 / 1350, getWidth() * 15 / 1350, getWidth() * 15 / 1350, null);
                }
                count++;
            }
        }
        for (int q = 1; q < Game.players.size(); q++) {
            ix = getWidth() * 870 / 1350;
            iy = getHeight() * 310 / 729 + (q - 1) * getHeight() * 90 / 729;
            w = getWidth() * 266 / 1350;
            h = getHeight() * 68 / 729;
            Player p = Game.players.get(q);
            g.setFont(new Font("SansSerif", Font.PLAIN, getWidth() * 10 / 1924));
            if (p == Game.p1) {
                g.drawString("Score: " + Game.p1.getTotalScore(), ix - getWidth() * 22 / 1350, iy + getHeight() * 20 / 729);
                g.drawString("VP count: " + Game.p1.getVictoryPoints(), ix - getWidth() * 27 / 1350, iy + getHeight() * 25 / 729);
                g.drawImage(dashBoard1, ix, iy, w, h, null);
            } else if (p == Game.p2) {
                g.drawString("Score: " + Game.p2.getTotalScore(), ix - getWidth() * 22 / 1350, iy + getHeight() * 20 / 729);
                g.drawString("VP count: " + Game.p2.getVictoryPoints(), ix - getWidth() * 27 / 1350, iy + getHeight() * 25 / 729);
                g.drawImage(dashBoard2, ix, iy, w, h, null);
            } else if (p == Game.p3) {
                g.drawString("Score: " + Game.p3.getTotalScore(), ix - getWidth() * 22 / 1350, iy + getHeight() * 20 / 729);
                g.drawString("VP count: " + Game.p3.getVictoryPoints(), ix - getWidth() * 27 / 1350, iy + getHeight() * 25 / 729);
                g.drawImage(dashBoard3, ix, iy, w, h, null);
            } else if (p == Game.p4) {
                g.drawString("Score: " + Game.p4.getTotalScore(), ix - getWidth() * 22 / 1350, iy + getHeight() * 20 / 729);
                g.drawString("VP count: " + Game.p4.getVictoryPoints(), ix - getWidth() * 27 / 1350, iy + getHeight() * 25 / 729);
                g.drawImage(dashBoard4, ix, iy, w, h, null);
            }
            count = 0;
            for (int i = 0; i < 4 && p.getEnergy().size() > count; i++) {
                for (int o = 0; o < 4 && p.getEnergy().size() > count; o++) {
                    if (p.getEnergy().get(count).equals("Blue")) {
                        g.drawImage(blueEnergyImage, (ix + getWidth() * 10 / 1350) + o * getWidth() * 5 / 1350, (iy + getHeight() * 25 / 729) + i * getWidth() * 5 / 1350, getWidth() * 5 / 1350, getWidth() * 5 / 1350, null);
                    } else if (p.getEnergy().get(count).equals("Black")) {
                        g.drawImage(blackEnergyImage, (ix + getWidth() * 10 / 1350) + o * getWidth() * 5 / 1350, (iy + getHeight() * 25 / 729) + i * getWidth() * 5 / 1350, getWidth() * 5 / 1350, getWidth() * 5 / 1350, null);
                    } else if (p.getEnergy().get(count).equals("Red")) {
                        g.drawImage(redEnergyImage, (ix + getWidth() * 10 / 1350) + o * getWidth() * 5 / 1350, (iy + getHeight() * 25 / 729) + i * getWidth() * 5 / 1350, getWidth() * 5 / 1350, getWidth() * 5 / 1350, null);
                    } else {
                        g.drawImage(yellowEnergyImage, (ix + getWidth() * 10 / 1350) + o * getWidth() * 5 / 1350, (iy + getHeight() * 25 / 729) + i * getWidth() * 5 / 1350, getWidth() * 5 / 1350, getWidth() * 5 / 1350, null);
                    }
                    count++;
                }
            }
        }
    }

    //Player methods
    public void drawDashboard(Graphics g) {
        if (Game.currentPlayer == Game.players.get(0)) {
            g.drawImage(dashBoard1, getWidth() * 775 / 1924, getHeight() * 100 / 1080, getWidth() * 650 / 1924, getHeight() * 220 / 1061, null);
            g.drawImage(dashBoard2, getWidth() * 1250 / 1924, getHeight() * 320 / 1061, getWidth() * 450 / 1924, getHeight() * 150 / 1061, null);
            g.drawImage(dashBoard3, getWidth() * 1250 / 1924, getHeight() * 500 / 1061, getWidth() * 450 / 1924, getHeight() * 150 / 1061, null);
            g.drawImage(dashBoard4, getWidth() * 1250 / 1924, getHeight() * 680 / 1061, getWidth() * 450 / 1924, getHeight() * 150 / 1061, null);
        } else if (Game.currentPlayer == Game.players.get(1)) {
            g.drawImage(dashBoard2, getWidth() * 775 / 1924, getHeight() * 100 / 1061, getWidth() * 650 / 1924, getHeight() * 220 / 1061, null);
            g.drawImage(dashBoard3, getWidth() * 1250 / 1924, getHeight() * 320 / 1061, getWidth() * 450 / 1924, getHeight() * 150 / 1061, null);
            g.drawImage(dashBoard4, getWidth() * 1250 / 1924, getHeight() * 500 / 1061, getWidth() * 450 / 1924, getHeight() * 150 / 1061, null);
            g.drawImage(dashBoard1, getWidth() * 1250 / 1924, getHeight() * 680 / 1061, getWidth() * 450 / 1924, getHeight() * 150 / 1061, null);
        } else if (Game.currentPlayer == Game.players.get(2)) {
            g.drawImage(dashBoard3, getWidth() * 775 / 1924, getHeight() * 100 / 1061, getWidth() * 650 / 1924, getHeight() * 220 / 1061, null);
            g.drawImage(dashBoard4, getWidth() * 1250 / 1924, getHeight() * 320 / 1061, getWidth() * 450 / 1924, getHeight() * 150 / 1061, null);
            g.drawImage(dashBoard1, getWidth() * 1250 / 1924, getHeight() * 500 / 1061, getWidth() * 450 / 1924, getHeight() * 150 / 1061, null);
            g.drawImage(dashBoard2, getWidth() * 1250 / 1924, getHeight() * 680 / 1061, getWidth() * 450 / 1924, getHeight() * 150 / 1061, null);
        } else if (Game.currentPlayer == Game.players.get(3)) {
            g.drawImage(dashBoard4, getWidth() * 775 / 1924, getHeight() * 100 / 1061, getWidth() * 650 / 1924, getHeight() * 220 / 1061, null);
            g.drawImage(dashBoard1, getWidth() * 1250 / 1924, getHeight() * 320 / 1061, getWidth() * 450 / 1924, getHeight() * 150 / 1061, null);
            g.drawImage(dashBoard2, getWidth() * 1250 / 1924, getHeight() * 500 / 1061, getWidth() * 450 / 1924, getHeight() * 150 / 1061, null);
            g.drawImage(dashBoard3, getWidth() * 1250 / 1924, getHeight() * 680 / 1061, getWidth() * 450 / 1924, getHeight() * 150 / 1061, null);
        }
    }

    //public void drawEnergy(Graphics g, Player currentPlayer) // HR going to have to redo, mistake in dashboards
    //{
    // c denotes current player
    //   int cW = getWidth()*18/1920;
    //   int cH = getHeight()*18/1080;
    //   int cX = getWidth()*800/1920;
    //  int cY = getHeight()*110/1080;
    //   int cxInc = 0;
    //   double fourCt = 0;
    //   int tW = getWidth()*12/1920;
    //   int tH = getHeight()*12/1080;
    //   int tX = getWidth()*1265/1920;
    //   int t1Y = getHeight()*330/1080;
    //   int t2Y = getHeight()*510/1080;
    //   int t3Y = getHeight()*690/1080;
    //   for (int i = 0; i < currentPlayer.getEnergy().size(); i++)
    //   {
    //        if (fourCt == 1.0)
    //        {
    //            cxInc = 0;
    //            cY += getHeight()*18/1080;
    //            fourCt = 0;
    //       }
    //        if (currentPlayer.getEnergy().get(i).equals("Blue"))
    //        {
    //            g.drawImage(blueEnergyImage, cX + cxInc, cY, cW, cH, null);
    //        }
    //        else if (currentPlayer.getEnergy().get(i).equals("Red"))
    //        {
    //           g.drawImage(redEnergyImage, cX + cxInc, cY, cW, cH, null);
    //        }
    //        else if (currentPlayer.getEnergy().get(i).equals("Yellow"))
    //        {
    //            g.drawImage(yellowEnergyImage, cX + cxInc, cY, cW, cH, null);
    //        }
    //        else if (currentPlayer.getEnergy().get(i).equals("Black"))
    //        {
    //           g.drawImage(blackEnergyImage, cX + cxInc, cY, cW, cH, null);
    //       }
    //       cxInc += getWidth()*18/1920;
    //       fourCt += 0.25;
    //  }
    //  fourCt = 0;
    //   for (int i = 0; i < temp1.getEnergy().size();i++)
    //   {
//
    //   }
    //}
    public void drawCards(Graphics g) {
        for (int turn = 1; turn < 5; turn++) {
            play = Game.players.get(turn - 1);
            if (turn == 1) {
                ArrayList<Card> card;
                int ix, iy;
                int width = getWidth() * 100 / 1924;
                int height = getHeight() * 90 / 1061;
                int ct = 0;
                Player.CardType[] inv = play.getCardType();
                while (ct < 6) {
                    Player.CardType a = inv[ct];
                    ct++;
                    switch (a) {
                        case UPGRADE:
                            ix = getWidth() * 888 / 1920;
                            iy = getHeight() * 205 / 1017;
                            card = play.getInventory().get(a).getConditionList();
                            for (int i = 0; i < card.size(); i++) {
                                g.drawImage(card.get(i).getImage(), ix, iy + (getHeight() * 20 / 1017) * i, width, height, null);
                            }
                            break;
                        case CONVERTER:
                            ix = getWidth() * 1010 / 1920;
                            iy = getHeight() * 205 / 1017;
                            card = play.getInventory().get(a).getConditionList();
                            for (int i = 0; i < card.size(); i++) {
                                g.drawImage(card.get(i).getImage(), ix, iy + (getHeight() * 20 / 1017) * i, width, height, null);
                            }
                            break;
                        case FILE:
                            ix = getWidth() * 1150 / 1920;
                            iy = getHeight() * 229 / 1017;
                            card = play.getInventory().get(a).getConditionList();
                            for (int i = 0; i < card.size(); i++) {
                                if (card.get(i).getImage() != null) {
                                    g.drawImage(card.get(i).getImage(), ix, iy + (getHeight() * 20 / 1017) * (i - 1), width, height, null);
                                }
                            }
                            break;
                        case PICK:
                            ix = getWidth() * 1261 / 1920;
                            iy = getHeight() * 205 / 1017;
                            card = play.getInventory().get(a).getConditionList();
                            for (int i = 0; i < card.size(); i++) {
                                g.drawImage(card.get(i).getImage(), ix, iy + (getHeight() * 20 / 1017) * i, width, height, null);
                            }
                            break;
                        case BUILD:
                            ix = getWidth() * 1378 / 1920;
                            iy = getHeight() * 205 / 1017;
                            card = play.getInventory().get(a).getConditionList();
                            for (int i = 0; i < card.size(); i++) {
                                g.drawImage(card.get(i).getImage(), ix, iy + (getHeight() * 20 / 1017) * i, width, height, null);
                            }
                            break;
                        case ARCHIVE:
                            ix = getWidth() * 1601 / 1924;
                            iy = getHeight() * 173 / 1017;
                            card = play.getInventory().get(a).getConditionList();
                            for (int i = 0; i < card.size(); i++) {
                                g.drawImage(card.get(i).getImage(), ix + (getWidth() * 20 / 1920), iy + (getHeight() * 20 / 1017) * i, width, height, null);
                            }
                            break;
                    }
                }
            } else {
                ArrayList<Card> card;
                int ix, iy;
                int width = getWidth() * 40 / 1924;
                int height = getHeight() * 33 / 1061;
                int ct = 0;
                Player.CardType[] inv = play.getCardType();
                while (ct < 6) {
                    Player.CardType a = inv[ct];
                    ct++;
                    switch (a) {
                        case UPGRADE:
                            card = play.getInventory().get(a).getConditionList();
                            ix = getWidth() * 1321 / 1920;
                            iy = getHeight() * 486 / 1017 + ((turn - 2) * getHeight() * 125) / 1017;
                            for (int i = 0; i < card.size(); i++) {
                                g.drawImage(card.get(i).getImage(), ix, iy + (getHeight() * 10 / 1061) * i, width, height, null);
                            }
                            break;
                        case CONVERTER:
                            card = play.getInventory().get(a).getConditionList();
                            ix = getWidth() * 1374 / 1920;
                            iy = getHeight() * 486 / 1017 + ((turn - 2) * getHeight() * 125) / 1017;
                            for (int i = 0; i < card.size(); i++) {
                                g.drawImage(card.get(i).getImage(), ix, iy + (getHeight() * 10 / 1061) * i, width, height, null);
                            }
                            break;
                        case FILE:
                            card = play.getInventory().get(a).getConditionList();
                            ix = getWidth() * 1429 / 1920;
                            iy = (getHeight() * 486 / 1017 + getHeight() * 10 / 1061) + ((turn - 2) * getHeight() * 125) / 1017;
                            for (int i = 0; i < card.size(); i++) {
                                if (card.get(i).getImage() != null) {
                                    g.drawImage(card.get(i).getImage(), ix, iy + (getHeight() * 10 / 1061) * i, width, height, null);
                                }
                            }
                            break;
                        case PICK:
                            card = play.getInventory().get(a).getConditionList();
                            ix = getWidth() * 1481 / 1920;
                            iy = getHeight() * 486 / 1017 + ((turn - 2) * getHeight() * 125) / 1017;
                            for (int i = 0; i < card.size(); i++) {
                                g.drawImage(card.get(i).getImage(), ix, iy + (getHeight() * 10 / 1061) * i, width, height, null);
                            }
                            break;
                        case BUILD:
                            card = play.getInventory().get(a).getConditionList();
                            ix = getWidth() * 1530 / 1920;
                            iy = getHeight() * 486 / 1017 + ((turn - 2) * getHeight() * 125) / 1017;
                            for (int i = 0; i < card.size(); i++) {
                                g.drawImage(card.get(i).getImage(), ix, iy + (getHeight() * 10 / 1061) * i, width, height, null);
                            }
                            break;
                        case ARCHIVE:
                            card = play.getInventory().get(a).getConditionList();
                            ix = getWidth() * 1616 / 1920;
                            iy = getHeight() * 470 / 1017 + ((turn - 2) * getHeight() * 125 / 1017);
                            for (int i = 0; i < card.size(); i++) {
                                g.drawImage(card.get(i).getImage(), ix + (getHeight() * 10 / 1061) * i, iy + (getHeight() * 10 / 1061) * i, width, height, null);
                            }
                            break;
                    }
                }
            }
        }


    }

    public void drawAdditionalPoints(Graphics g) {
        //Jessica v
        int px, py;
        for (int i = 0; i < Game.players.size(); i++) {
            Player temp = Game.players.get(i);
            int fives = temp.getVictoryPoints() / 5;
            int ones = temp.getVictoryPoints() % 5;
            if (i == 0) {
                px = getWidth() * 888 / 1924;
                py = getHeight() * 133 / 1061;
                while (fives > 0) {
                    g.drawImage(pointfive, px, py, getWidth() * 48 / 1924, getHeight() * 50 / 1061, null);
                    px += getWidth() * 50 / 1924;
                    fives--;
                }
                while (ones > 0) {
                    g.drawImage(point, px, py, getWidth() * 48 / 1924, getHeight() * 50 / 1061, null);
                    px += getWidth() * 50 / 1924;
                    ones--;
                }
            } else {
                int wi = getWidth() * 15 / 1920;
                int he = getHeight() * 10 / 1017;
                px = getWidth() * 1318 / 1920;
                //py = getHeight()*(464+150*(i-1))/1061;
                py = getHeight() * (450 + (i - 1) * 127) / 1017;
                while (fives > 0) {
                    g.drawImage(pointfive, px, py, wi, he, null);
                    px += wi;
                    fives--;
                }
                while (ones > 0) {
                    g.drawImage(point, px, py, wi, he, null);
                    px += wi;
                    ones--;
                }
            }
        }
    }

    //public void drawBuildChoices(Graphics g){
    //	//different icons for board and archived cards
    //	g.drawImage(FileButton,getWidth()*96/1350,getHeight()*83/729,getWidth()*100/1350,getWidth()*100/1350,null);
    //	g.drawImage(BoardButton,getWidth()*196/1350,getHeight()*83/729,getWidth()*100/1350,getWidth()*100/1350,null);
    //}
    public void drawConversionChoices(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(getWidth() * 502 / 1350, getHeight() * 556 / 729, WIDTH, HEIGHT);
        ArrayList<Card> ACC = Game.currentPlayer.getInventory().get(Player.CardType.CONVERTER).getConditionList();
        for (int i = 0; i < ACC.size(); i++) {
            g.drawImage(ACC.get(i).getImage(), getWidth() * 502 / 1350 + (getWidth() * 100 / 1924) * i, getHeight() * 556 / 729, getWidth() * 100 / 1924, getHeight() * 90 / 1061, null);
        }
    }

    public void drawResearch(Graphics g) {
        ArrayList<Card> temp = Game.currentPlayer.getResearch();
        g.setColor(new Color(30, 72, 46));
        g.fillRect(getWidth() * 88 / 1350, getHeight() * 536 / 729, temp.size() * (getWidth() * 95 / 1920), getHeight() * 9 / 100);
        for (int i = 0; i < temp.size(); i++) {
            g.drawImage(temp.get(i).getImage(), getWidth() * 88 / 1350 + (i * (getWidth() * 95 / 1920)), getHeight() * 536 / 729, (getWidth() * 95 / 1920), getHeight() * 9 / 100, null);
        }
    }

    // Fxns panel
    public void drawChoices(Graphics g) {
        g.drawImage(file, getWidth() * 96 / 1350, getHeight() * 552 / 729, getWidth() * 80 / 1350, getHeight() * 100 / 729, null);
        g.drawImage(pick, getWidth() * 196 / 1350, getHeight() * 552 / 729, getWidth() * 80 / 1350, getHeight() * 100 / 729, null);
        g.drawImage(research, getWidth() * 296 / 1350, getHeight() * 552 / 729, getWidth() * 80 / 1350, getHeight() * 100 / 729, null);
        g.drawImage(build, getWidth() * 396 / 1350, getHeight() * 552 / 729, getWidth() * 80 / 1350, getHeight() * 100 / 729, null);
        g.drawImage(end, getWidth() * 1180 / 1350, getHeight() * 552 / 729, getWidth() * 80 / 1350, getHeight() * 100 / 729, null);
    }

    public void mouseClicked(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();
        if (Game.currentPlayer.isConverting()) {
            for (int i = 0; i < Game.currentPlayer.getInventory().get(Player.CardType.CONVERTER).getConditionList().size(); i++) { //loops through current player's converters
                int leftX = getWidth() * 502 / 1350 + (getWidth() * 100 / 1924) * i;
                int leftY = getHeight() * 556 / 729;
                int width = getWidth() * 100 / 1924;
                int height = getHeight() * 90 / 1061;
                if (mx > leftX && my > leftY && mx < leftX + width && my < leftY + height) {
                    System.out.println(i);
                    System.out.println(Game.currentPlayer.isConverting());
                    System.out.println(Game.currentPlayer.getInventory().get(Player.CardType.CONVERTER).getConditionList().get(i));
                    Game.currentPlayer.Convert((ConverterCard) Game.currentPlayer.getInventory().get(Player.CardType.CONVERTER).getConditionList().get(i));
                    return;
                }
            }
        }
        int x = e.getX();
        int y = e.getY();
        //if(Game.currentPlayer.isConverting()){
        //	for(int i = 0; i < Game.currentPlayer.getInventory().get(Player.CardType.CONVERTER).getConditionList().size();i++){
        //        if(x>=getWidth()*502/1350+(getWidth()*100/1924)*i&&x<=getWidth()*502/1350+(getWidth()*100/1924)*i+getWidth()*100/1924){
        //            String temp = Game.currentPlayer.Convert(Game.currentPlayer.getInventory().get(Player.CardType.CONVERTER).getConditionList().get(i));
        //            if(temp.contains("double")){
        //                temp = temp.toLowerCase();
        //                if (temp.contains("red")){
        //                    Game.currentPlayer.getEnergy().add("Red");
        //                }
        //                else if (temp.contains("yellow")){
        //                    Game.currentPlayer.getEnergy().add("Yellow");
        //                }
        //                else if (temp.contains("blue")){
        //                   Game.currentPlayer.getEnergy().add("Blue");
        // /               }
        //                else if (temp.contains("Black")){
        //                    Game.currentPlayer.getEnergy().add("Black");
        //                }
        //            }
        //        }
        //    }
        //}
        //if(Player.chooseConvertColor){
        //    if(x>=0&&x<=getWidth()*80/1350&&y>=0&&y<=getWidth()*80/1350){
//
        //    }
        //    else if(){

        //    }
        //    else if(){

        //    }
        //    else if(){

        //     }
        //     Player.chooseConvertColor = false;
        // }
        if (ChoosingAny) {
            chooseAnyCard(e);
            ChoosingAny = false;
        }
        if (ChoosingTierOne) {
            chooseAnyCard(e);
            ChoosingAny = false;
        }
        if (choosingEnergy) {
            chooseEn(e);
        }
        //System.out.println("loc is (" + mx + ", " + my + ")");

        if (x >= getWidth() * 1187 / 1350 && x <= getWidth() * 1187 / 1350 + getWidth() * 100 / 1350 && y >= getHeight() * 69 / 729 && y <= getHeight() * 69 / 729 + getWidth() * 100 / 1350) {
            Game.nextTurn();
            Game.currentPlayer.stopConverting();
        }
        if (Game.chosenAction) {
            if (x > getWidth() * 96 / 1350 && x < getWidth() * 176 / 1350 &&
                    y > getHeight() * 552 / 729 && y < getHeight() * 652 / 729) {
                Game.testFile();
                System.out.println("file");
                Game.chosenAction = false;
            }
            if (x > getWidth() * 196 / 1350 && x < getWidth() * 276 / 1350 &&
                    y > getHeight() * 552 / 729 && y < getHeight() * 652 / 729) {
                Game.testPick();
                System.out.println("pck");
                Game.chosenAction = false;
            }
            if (x > getWidth() * 296 / 1350 && x < getWidth() * 376 / 1350 &&
                    y > getHeight() * 552 / 729 && y < getHeight() * 652 / 729) {
                Game.testResearch();
                System.out.println("reserch");
                Game.chosenAction = false;
            }
            if (x > getWidth() * 396 / 1350 && x < getWidth() * 476 / 1350 &&
                    y > getHeight() * 552 / 729 && y < getHeight() * 652 / 729) {
                Game.testBuild();
                System.out.println("build");
                Game.chosenAction = false;
            }
        }
        if (Game.gameChoosingBoardCard) {//archive action
            if (y >= getHeight() * 617 / 1061 && y <= getHeight() * 617 / 1061 + getHeight() * 9 / 100) {
                for (int i = 0; i < 4 && i < Game.decks.get(1).getConditionList().size(); i++) {
                    if (x >= getWidth() * (675 + 120 * i) / 1924 && x <= getWidth() * (675 + 120 * i) / 1924 + getWidth() * 95 / 1920) {
                        if (Game.currentPlayer.getFileLimit() > Game.currentPlayer.getInventory().get(Player.CardType.ARCHIVE).getConditionList().size()) {
                            Game.currentPlayer.File(Game.decks.get(1).getConditionList().get(i));
                        }
                        Game.gameChoosingBoardCard = false;
                        break;
                    }
                }
            } else if (y >= getHeight() * 501 / 1061 && y <= getHeight() * 501 / 1061 + getHeight() * 9 / 100) {
                for (int i = 0; i < 3 && i < Game.decks.get(2).getConditionList().size(); i++) {
                    if (x >= getWidth() * (i * 119 + 679) / 1924 && x <= getWidth() * (i * 119 + 679) / 1924 + getWidth() * 95 / 1920) {
                        if (Game.currentPlayer.getFileLimit() > Game.currentPlayer.getInventory().get(Player.CardType.ARCHIVE).getConditionList().size()) {
                            Game.currentPlayer.File(Game.decks.get(2).getConditionList().get(i));
                        }
                        Game.gameChoosingBoardCard = false;
                        break;
                    }
                }
            } else if (y >= getHeight() * 73 / 203 && y <= getHeight() * 73 / 203 + getHeight() * 9 / 100) {
                for (int i = 0; i < 2 && i < Game.decks.get(3).getConditionList().size(); i++) {
                    if (x >= getWidth() * (559 + 120 * (i + 1)) / 1924 && x <= getWidth() * (559 + 120 * (i + 1)) / 1924 + getWidth() * 95 / 1920) {
                        if (Game.currentPlayer.getFileLimit() > Game.currentPlayer.getInventory().get(Player.CardType.ARCHIVE).getConditionList().size()) {
                            Game.currentPlayer.File(Game.decks.get(3).getConditionList().get(i));
                        }
                        Game.gameChoosingBoardCard = false;
                        break;
                    }
                }
            }
        }
        if (Game.chooseT1) {
            if (y >= getHeight() * 617 / 1061 && y <= getHeight() * 617 / 1061 + getHeight() * 9 / 100) {
                for (int i = 0; i < 4 && i < Game.decks.get(1).getConditionList().size(); i++) {
                    if (x >= getWidth() * (675 + 120 * i) / 1924 && x <= getWidth() * (675 + 120 * i) / 1924 + getWidth() * 95 / 1920) {
                        Game.currentPlayer.BuildT1(Game.decks.get(1).getConditionList().get(i));
                        Game.chooseT1 = false;
                        break;
                    }
                }
            }
            for (int i = Game.currentPlayer.getInventory().get(Player.CardType.FILE).getConditionList().size() - 1; i >= 0; i--) {
                if (y >= getHeight() * 240 / 1061 + getHeight() * 30 / 1061 * i && y <= (getHeight() * 240 / 1061 + getHeight() * 30 / 1061 * i) + getHeight() * 90 / 1061) {
                    //if(Game.currentPlayer.isBuildable(Game.currentPlayer.getInventory().get(Player.CardType.FILE).getConditionList().get(i))){
                    Game.currentPlayer.BuildT1(Game.currentPlayer.getInventory().get(Player.CardType.FILE).getConditionList().get(i));
                    Game.chooseT1 = false;
                    break;
                    //}

                }
            }
        }
        if (Game.gameChoosingCard) {//build action
            if (y >= getHeight() * 617 / 1061 && y <= getHeight() * 617 / 1061 + getHeight() * 9 / 100) {
                for (int i = 0; i < 4 && i < Game.decks.get(1).getConditionList().size(); i++) {
                    if (x >= getWidth() * (675 + 120 * i) / 1924 && x <= getWidth() * (675 + 120 * i) / 1924 + getWidth() * 95 / 1920) {
                            Game.currentPlayer.Build(Game.decks.get(1).getConditionList().get(i));
                            Game.gameChoosingCard = false;
                            break;
                    }
                }
            } else if (y >= getHeight() * 501 / 1061 && y <= getHeight() * 501 / 1061 + getHeight() * 9 / 100) {
                for (int i = 0; i < 3 && i < Game.decks.get(2).getConditionList().size(); i++) {
                    if (x >= getWidth() * (i * 119 + 679) / 1924 && x <= getWidth() * (i * 119 + 679) / 1924 + getWidth() * 95 / 1920) {
                            Game.currentPlayer.Build(Game.decks.get(2).getConditionList().get(i));
                            Game.gameChoosingCard = false;
                            break;
                    }
                }
            } else if (y >= getHeight() * 73 / 203 && y <= getHeight() * 73 / 203 + getHeight() * 9 / 100) {
                for (int i = 0; i < 2 && i < Game.decks.get(3).getConditionList().size(); i++) {
                    if (x >= getWidth() * (559 + 120 * (i + 1)) / 1924 && x <= getWidth() * (559 + 120 * (i + 1)) / 1924 + getWidth() * 95 / 1920) {
                            Game.currentPlayer.Build(Game.decks.get(3).getConditionList().get(i));
                            Game.gameChoosingCard = false;
                            break;
                    }
                }
            }
            //else if(x>=getWidth()*1601/1924&&x<=getWidth()*1601/1924+getWidth()*100/1924){
            //	for(int i = Game.currentPlayer.getInventory().get(Player.CardType.FILE).getConditionList().size()-1;i>=0;i--){
            //		if(y>=getHeight()*173/1017+getHeight()*30/1061*i&&y<=(getHeight()*173/1017+getHeight()*30/1061*i)+getHeight()*90/1061){
            //			if(Game.currentPlayer.isBuildable(Game.currentPlayer.getInventory().get(Player.CardType.FILE).getConditionList().get(i))){
            //                Game.currentPlayer.Build(Game.currentPlayer.getInventory().get(Player.CardType.FILE).getConditionList().get(i));
            //				Game.gameChoosingCard = false;
            //				break;
            //			}
            //
            //		}
            //	}
            //}
            else {
                Card temp = null;
                for (int i = 0; i < Game.currentPlayer.getInventory().get(Player.CardType.FILE).getConditionList().size(); i++) {
                    if (x >= getWidth() * 1601 / 1924 + (getWidth() * 20 / 1920) && x <= getWidth() * 1601 / 1924 + (getWidth() * 20 / 1920) + getWidth() * 100 / 1924 && y >= (getHeight() * 173 / 1017) + getHeight() * 20 / 1017 * i && y <= (getHeight() * 173 / 1017) + getHeight() * 20 / 1017 * i + (getHeight() * 90 / 1061)) {
                        temp = Game.currentPlayer.getInventory().get(Player.CardType.FILE).getConditionList().get(i);
                    }
                }
                if (temp != null) {
                    if (Game.currentPlayer.getInventory().get(Player.CardType.ARCHIVE).getConditionList().size() > 0)
                        Game.currentPlayer.Build(Game.currentPlayer.getInventory().get(Player.CardType.ARCHIVE).getConditionList().get(0));
                    Game.gameChoosingCard = false;
                    Game.currentPlayer.stopConverting();

                }

            }

        }
        if (Game.gameChoosingDeck) {
            if (x >= getWidth() * 558 / 1924 && x <= getWidth() * 558 / 1924 + getWidth() * 95 / 1920 &&
                    y >= getHeight() * 617 / 1061 && y <= getHeight() * 617 / 1061 + getHeight() * 9 / 100) {
                Game.currentPlayer.Research(1);
                Game.gameChoosingDeck = false;
            } else if (x >= getWidth() * 568 / 1924 && x <= getWidth() * 568 / 1924 + getWidth() * 95 / 1920 &&
                    y >= getHeight() * 501 / 1061 && y <= getHeight() * 501 / 1061 + getHeight() * 9 / 100) {
                Game.currentPlayer.Research(2);
                Game.gameChoosingDeck = false;
            } else if (x >= getWidth() * 59 / 195 && x <= getWidth() * 59 / 195 + getWidth() * 95 / 1920 &&
                    y >= getHeight() * 73 / 203 && y <= getHeight() * 73 / 203 + getHeight() * 9 / 100) {
                Game.currentPlayer.Research(3);
                Game.gameChoosingDeck = false;
            }
        }
        if (Game.currentPlayer.isResearching()) {
            ArrayList<Card> temp = Game.currentPlayer.getResearch();
            for (int i = 0; i < temp.size(); i++) {
                //g.drawImage(temp.get(i).getImage(),getWidth()*88/1350+(i*(getWidth()*40/1924)), getHeight()*536/729, (getWidth()*40/1924), getHeight()*113/729,null);
                if (y >= getHeight() * 536 / 729 && y <= getHeight() * 536 / 729 + getHeight() * 9 / 100 && x >= getWidth() * 88 / 1350 + (i * (getWidth() * 95 / 1920)) && x <= getWidth() * 88 / 1350 + (i * (getWidth() * 95 / 1920)) + getWidth() * 95 / 1920) {
                    Game.currentPlayer.setResearchCard(temp.get(i));
                    Game.currentPlayer.setResearching(false);
                    fileOrBuild = true;
                }
            }
        }
        if (fileOrBuild) {
            if (x >= getWidth() * 96 / 1350 && x <= getWidth() * 96 / 1350 + getWidth() * 80 / 1350 && y >= getHeight() * 552 / 729 && y <= getHeight() * 552 / 729 + getHeight() * 100 / 729) {
                Game.currentPlayer.File(Game.currentPlayer.getResearchCard());
                Game.currentPlayer.getResearch().remove(Game.currentPlayer.getResearchCard());
                for (int i = 0; i < Game.currentPlayer.getResearch().size(); i++) {//readd research cards
                    Game.decks.get(Game.currentPlayer.getResearchCard().getTier()).getConditionList().add(Game.currentPlayer.getResearch().get(i));
                }
                fileOrBuild = false;
                Game.r = false;
            }
            if (x >= getWidth() * 196 / 1350 && x <= getWidth() * 196 / 1350 + getWidth() * 80 / 1350 && y >= getHeight() * 552 / 729 && y <= getHeight() * 552 / 729 + getHeight() * 100 / 729) {
                    Game.currentPlayer.Build(Game.currentPlayer.getResearchCard());
                    for (int i = 0; i < Game.currentPlayer.getResearch().size(); i++) {
                        Game.decks.get(Game.currentPlayer.getResearchCard().getTier()).getConditionList().add(Game.currentPlayer.getResearch().get(i));
                    }
                    fileOrBuild = false;
                    Game.r = false;

            }
        }
        if (Game.choose2) {
            for (int i = 0; i < 6; i++) {
                if (y >= getHeight() * 530 / 1080 && y <= getHeight() * 530 / 1080 + (getHeight() * 30 / 1080) && x >= getWidth() * 355 / 1920 + (i * getWidth() * 30 / 1920) && x <= getWidth() * 355 / 1920 + ((i + 1) * getWidth() * 30 / 1920)) {
                    Game.currentPlayer.Pick(Game.energyRow.get(i));
                    System.out.println("selected energy");
                }
            }
            for (int i = 0; i < 6; i++) {
                if (y >= getHeight() * 530 / 1080 && y <= getHeight() * 530 / 1080 + (getHeight() * 30 / 1080) && x >= getWidth() * 355 / 1920 + (i * getWidth() * 30 / 1920) && x <= getWidth() * 355 / 1920 + ((i + 1) * getWidth() * 30 / 1920)) {
                    Game.currentPlayer.Pick(Game.energyRow.get(i));
                    System.out.println("selected energy");
                }
            }
            Game.choose2 = false;
        }
        if (Game.gameChoosingEnergy) {//lkj
            for (int i = 0; i < 6; i++) {
                if (y >= getHeight() * 530 / 1080 && y <= getHeight() * 530 / 1080 + (getHeight() * 30 / 1080) && x >= getWidth() * 355 / 1920 + (i * getWidth() * 30 / 1920) && x <= getWidth() * 355 / 1920 + ((i + 1) * getWidth() * 30 / 1920)) {
                    Game.currentPlayer.Pick(Game.energyRow.get(i));
                    Game.gameChoosingEnergy = false;
                    System.out.println("selected energy");
                }
            }
        }

        if (Game.currentPlayer.getReactionList().size() > 0) {
            for (int i = 0; i < Game.currentPlayer.getReactionList().size(); i++) {
                if (x >= getWidth() * 1245 / 1924 && x <= getWidth() * 1245 / 1924 + getWidth() * 200 / 1924 && y >= getHeight() * 475 / 1061 + (i * getHeight() * 50 / 1061) && y <= getHeight() * 475 / 1061 + (i * getHeight() * 50 / 1061) + getHeight() * 50 / 1061) {
                    String it = Game.currentPlayer.getReactionList().get(i);
                    if (it.equals("PICK")) {
                        Game.testPick();
                        Game.currentPlayer.getReactionList().remove(i);
                    } else if (it.equals("PICK2")) {
                        Game.testPick2();
                        Game.currentPlayer.getReactionList().remove(i);
                    } else if (it.equals("RANDOMDRAW")) {
                        Game.currentPlayer.DrawRandomEnergy();
                        Game.currentPlayer.getReactionList().remove(i);
                    } else if (it.equals("RANDOMDRAW3")) {
                        Game.currentPlayer.DrawRandomEnergy();
                        Game.currentPlayer.DrawRandomEnergy();
                        Game.currentPlayer.DrawRandomEnergy();
                        Game.currentPlayer.getReactionList().remove(i);
                    } else if (it.equals("BUILDTIERONEFREE")) {
                        Game.testBuildT1();
                        Game.currentPlayer.getReactionList().remove(i);
                    } else if (it.equals("FILEACTION")) {
                        Game.testFile();
                        Game.currentPlayer.getReactionList().remove(i);
                    } else if (it.equals("RESEARCH")) {
                        Game.testResearch();
                        Game.currentPlayer.getReactionList().remove(i);
                    } else if (it.equals("ONEVICTORYPOINT")) {
                        Game.currentPlayer.addVictoryPoints(1);
                        Game.currentPlayer.getReactionList().remove(i);
                    } else if (it.equals("TWOVICTORYPOINT")) {
                        Game.currentPlayer.addVictoryPoints(2);

                        Game.currentPlayer.getReactionList().remove(i);
                    }
                }
            }
        }
        System.out.println("getWidth()*" + x + "/" + getWidth() + " getHeight()*" + y + "/" + getHeight());
        //System.out.println("loc is (" + x + ", " + y + ")");
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void isChoosingAny() {
        ChoosingAny = true;
    }

    public void isChoosingTierOne() {
        ChoosingTierOne = true;
    }
}
