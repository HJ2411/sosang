import javax.swing.*;
import java.awt.*;

//Navigator.java
public class Navigator extends JPanel {
    private static final Color NAVIGATION_COLOR = new Color(247, 211, 38);
    private static final int NAV_HEIGHT = 50;
    private MainFrame mainFrame;

    public Navigator(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new GridLayout(1, 3));

        ImageIcon homeIcon = loadImageIcon("./image/Home.png", 30, 30);
        ImageIcon heartIcon = loadImageIcon("./image/Heart.png", 30, 30);
        ImageIcon humanIcon = loadImageIcon("./image/Human.png", 30, 30);

        JButton homeButton = new JButton(homeIcon);
        JButton heartButton = new JButton(heartIcon);
        JButton humanButton = new JButton(humanIcon);

        homeButton.setBackground(NAVIGATION_COLOR);
        heartButton.setBackground(NAVIGATION_COLOR);
        humanButton.setBackground(NAVIGATION_COLOR);

        homeButton.addActionListener(e -> this.mainFrame.navigateToPage(new MainPage()));
        heartButton.addActionListener(e -> this.mainFrame.navigateToPage(new LikeStoreList(mainFrame)));
        humanButton.addActionListener(e -> this.mainFrame.navigateToPage(new MyPage(mainFrame)));

        add(homeButton);
        add(heartButton);
        add(humanButton);
    }

    private ImageIcon loadImageIcon(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(path);
        Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }
}