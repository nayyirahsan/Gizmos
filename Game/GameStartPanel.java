//HR
package Game;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.image.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.*; 
public class GameStartPanel extends JPanel implements MouseListener
{
    private BufferedImage startBG;
    static boolean startGame;
    public GameStartPanel()
    {
        startGame = false;
        try
        {
            startBG = ImageIO.read(GameStartPanel.class.getResource("/GizmosBackgrounds/StartScreen.png"));
        } 
        catch (Exception e) 
        {
            System.out.println("Exception"); 
            return;
        }
        addMouseListener(this); 
    }
    public void paint(Graphics g)
    {
        super.paint(g);
        drawStartingScreen(g);
    }
    public void drawStartingScreen(Graphics g)
    {
        g.drawImage(startBG, 0, 0, getWidth(), getHeight(), null); 
    }
    public void mousePressed(MouseEvent e){}
    public void mouseReleased(MouseEvent e){}
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mouseClicked(MouseEvent e)
    {
        int x = e.getX();
        int y = e.getY(); 
        System.out.println("loc is ("+x+", "+y+")");
        if (getWidth()*755/1920 <= x  && x <= getWidth()*1160/1920 && getHeight()*710/1080 <= y && y <= getHeight()*805/1080)
        {
            setStartGame(true);
            System.out.println("Start Game");
        }
        if (getWidth()*885/1920 <= x && x <=getWidth()*1030/1920 && getHeight()*880/1080 <= y && y <= getHeight()*1015/1080)
        {
            System.out.println("Open Instructions");
            if (Desktop.isDesktopSupported())
            {
                try
                {   
                    File instructionsPDF = new File("Gizmos Rules.pdf");
                    Desktop.getDesktop().open(instructionsPDF); 
                }
                catch (Exception f)
                {
                    System.out.println("Could not display instructions");
                    return; 
                }
            }
            
        }
    }
    public void setStartGame(boolean bool)
    {
        startGame = bool;
    }

    public boolean getStartGame()
    {
        return startGame;
    }
}
//HR