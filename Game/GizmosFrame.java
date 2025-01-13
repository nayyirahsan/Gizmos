package Game;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
public class GizmosFrame extends JFrame
{
    //HR
    private static final int WIDTH = 1080;
    private static final int HEIGHT = 1920; 
    CardLayout cardLayout; 
    Container contentPane;
    GameStartPanel gameStartPanel;
    BoardUI boardUI;
    JPanel cards;
    public GizmosFrame(String title)
    {
        super(title); 
        cards = new JPanel();
        cards.setLayout(cardLayout = new CardLayout());
        gameStartPanel = new GameStartPanel();
        boardUI = new BoardUI(0);
        cards.add(gameStartPanel, "GameStartPanel");
        cards.add(boardUI, "BoardUI");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add a mouse listener to gameStartPanel
        gameStartPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // When the mouse is clicked, check if startGame is true
                if (gameStartPanel.startGame) {
                    // If startGame is true, switch to boardUI
                    cardLayout.show(cards, "BoardUI");
                }
            }
        });

        add(cards);
        setVisible(true); 
    }
}
