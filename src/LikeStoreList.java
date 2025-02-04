import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LikeStoreList extends JPanel {
    private JPanel contentPanel;
    private MainFrame mainFrame;

    public LikeStoreList(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());
        initializeComponents();
        layoutComponents();
    }

    private void initializeComponents() {
        contentPanel = new JPanel(new FlowLayout());
        createContentPanel();
    }

    private void layoutComponents() {
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void createContentPanel() {
        contentPanel.setBackground(Color.WHITE);

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(Color.WHITE);

        String memberId = LoggedInUser.getMemberId();
        List<FavoriteStore> likedStores = fetchLikedStores(memberId);
        for (FavoriteStore favoriteStore : likedStores) {
            listPanel.add(createStorePanel(favoriteStore));
            listPanel.add(Box.createVerticalStrut(10));
        }

        contentPanel.add(listPanel);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private List<FavoriteStore> fetchLikedStores(String memberId) {
        List<FavoriteStore> likedStores = new ArrayList<>();

        String query = "SELECT s.StoreNo, s.StoreName, s.LOC, s.OpenHours, " +
                "COALESCE(AVG(r.Rating), 0) AS avgRating, COUNT(r.Rating) AS reviewCount, s.SellerNo " +
                "FROM InterestList i " +
                "JOIN Store s ON i.StoreNo = s.StoreNo " +
                "LEFT JOIN Review r ON s.StoreNo = r.StoreNo " +
                "WHERE i.MemberID = ? " +
                "GROUP BY s.StoreNo, s.StoreName, s.LOC, s.OpenHours, s.SellerNo";

        try (Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/App_schema", "scott", "tiger");
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, memberId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int storeNo = rs.getInt("StoreNo");
                String storeName = rs.getString("StoreName");
                String location = rs.getString("LOC");
                String openHours = rs.getString("OpenHours");
                double avgRating = rs.getDouble("avgRating");
                int reviewCount = rs.getInt("reviewCount");
                int sellerNo = rs.getInt("SellerNo");

                likedStores.add(new FavoriteStore(storeNo, storeName, location, openHours, avgRating, reviewCount, sellerNo));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return likedStores;
    }

    private JPanel createStorePanel(FavoriteStore favoriteStore) {
        JPanel storePanel = new JPanel(new BorderLayout());
        storePanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        storePanel.setBackground(Color.WHITE);
        storePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showStoreDetails(favoriteStore);
            }
        });


        JLabel nameLabel = new JLabel(favoriteStore.getStoreName());
        nameLabel.setFont(new Font("맑은 고딕", Font.BOLD, 16));

        JLabel ratingLabel = new JLabel(String.format("★ %.1f (리뷰 %d)", favoriteStore.getAvgRating(), favoriteStore.getReviewCount()));
        ratingLabel.setForeground(Color.GRAY);

        JButton likeButton = new JButton("♥");
        setLikeButtonProperties(likeButton, true);
        likeButton.addActionListener(e -> {
            boolean isLiked = !likeButton.getText().equals("♥");
            setLikeButtonProperties(likeButton, isLiked);

            InterestListManager interestListManager = new InterestListManager();
            if (isLiked) {
                if (interestListManager.addInterestStore(LoggedInUser.getMemberId(), favoriteStore.getStoreNo())) {
                    System.out.println(favoriteStore.getStoreName() + " 관심 목록에 추가됨");
                } else {
                    System.out.println(favoriteStore.getStoreName() + " 관심 목록에 추가 실패");
                }
            } else {
                if (interestListManager.removeInterestStore(LoggedInUser.getMemberId(), favoriteStore.getStoreNo())) {
                    System.out.println(favoriteStore.getStoreName() + " 관심 목록에서 제거됨");
                } else {
                    System.out.println(favoriteStore.getStoreName() + " 관심 목록에서 제거 실패");
                }
            }
            //System.out.println(favoriteStore.getStoreName() + " 좋아요 상태: " + isLiked);
        });

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);

        JLabel locationLabel = new JLabel(favoriteStore.getLocation());
        JLabel hoursLabel = new JLabel(favoriteStore.getOpenHours());

        infoPanel.add(locationLabel);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(hoursLabel);

        storePanel.add(nameLabel, BorderLayout.NORTH);
        storePanel.add(ratingLabel, BorderLayout.SOUTH);
        storePanel.add(infoPanel, BorderLayout.WEST);
        storePanel.add(likeButton, BorderLayout.EAST);

        return storePanel;
    }

    private void setLikeButtonProperties(JButton likeButton, boolean isLiked) {
        likeButton.setText(isLiked ? "♥" : "♡");
        likeButton.setForeground(isLiked ? Color.RED : Color.GRAY);
        likeButton.setBackground(null);
        likeButton.setBorder(null);
        likeButton.setFocusPainted(false);
        likeButton.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        likeButton.setContentAreaFilled(false);
    }

    private void showStoreDetails(FavoriteStore favoriteStore) {
        SellerDAO sellerDAO = new SellerDAO();
        String storePhone = sellerDAO.getStorePhoneBySellerNo(favoriteStore.getStoreNo());

        Store store = new Store(
                favoriteStore.getStoreNo(),
                favoriteStore.getStoreName(),
                favoriteStore.getOpenHours(),
                favoriteStore.getLocation(),
                0, //수정필요
                ("./image/market.png"), //수정필요
                //favoriteStore.getReviewCount(),
                favoriteStore.getSellerNo()
        );

        /*dialog.add(new StoreInfoPanel(store));
        dialog.setVisible(true);*/
        mainFrame.navigateToPage(new StoreInfoPanel(store));
    }
}

class FavoriteStore {
    private int storeNo;
    private String storeName;
    private String location;
    private String openHours;
    private double avgRating;
    private int reviewCount;
    private int sellerNo;

    public FavoriteStore(int storeNo, String storeName, String location, String openHours, double avgRating, int reviewCount, int sellerNo) {
        this.storeNo = storeNo;
        this.storeName = storeName;
        this.location = location;
        this.openHours = openHours;
        this.avgRating = avgRating;
        this.reviewCount = reviewCount;
        this.sellerNo = sellerNo;
    }

    public int getStoreNo() {
        return storeNo;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getLocation() {
        return location;
    }

    public String getOpenHours() {
        return openHours;
    }

    public double getAvgRating() {
        return avgRating;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public int getSellerNo(){
        return sellerNo;
    }
}