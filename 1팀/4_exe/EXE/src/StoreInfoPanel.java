import javax.swing.*;
import java.awt.*;

public class StoreInfoPanel extends JPanel {
    private Store store;
    private JPanel mainPanel;
    private JPanel contentPanel;
    private JLabel phoneLabel;

    public StoreInfoPanel(Store store) {
        if (store == null) {
            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(
                    this,
                    "해당 Store 정보를 찾을 수 없습니다.",
                    "오류",
                    JOptionPane.ERROR_MESSAGE
            ));
            return;
        }
        this.store = store;

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        setLayout(new BorderLayout());
        initializeComponents();
        updateInfoUI();

        add(mainPanel, BorderLayout.CENTER);
    }

    private void initializeComponents() {
        contentPanel = new JPanel(null); // 절대 위치 사용
        contentPanel.setBorder(BorderFactory.createEmptyBorder(15, 5, 5, 5));
        contentPanel.setPreferredSize(new Dimension(350, 800)); // 스크롤 패널 내의 크기 설정

        JScrollPane scrollPane = new JScrollPane(contentPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
    }

    public void updateInfoUI() {
        contentPanel.removeAll();

        // 가게 이름 라벨
        JLabel nameLabel = new JLabel(store.getStoreName());
        nameLabel.setFont(new Font("맑은 고딕", Font.BOLD, 25));
        nameLabel.setBounds(20, 10, 250, 30); // 위치 및 크기 설정
        contentPanel.add(nameLabel);

        // 좋아요 버튼
        JButton likeButton = new JButton("♥");
        setLikeButtonProperties(likeButton, true);
        likeButton.setBounds(280, 10, 50, 30);

        //로그인한 유저 목록에 있나
        InterestListManager interestListManager = new InterestListManager();
        boolean isLiked = interestListManager.isStoreLiked(LoggedInUser.getMemberId(), store.getStoreNo());
        setLikeButtonProperties(likeButton, isLiked);

        likeButton.addActionListener(e -> {
            boolean currentLikedStatus = likeButton.getText().equals("♥");
            boolean newLikedStatus = !currentLikedStatus;
            setLikeButtonProperties(likeButton, newLikedStatus);

            if (newLikedStatus) {
                if (interestListManager.addInterestStore(LoggedInUser.getMemberId(), store.getStoreNo())) {
                    System.out.println(store.getStoreName() + " 관심 목록에 추가됨");
                } else {
                    System.out.println(store.getStoreName() + " 관심 목록에 추가 실패");
                }
            } else {
                if (interestListManager.removeInterestStore(LoggedInUser.getMemberId(), store.getStoreNo())) {
                    System.out.println(store.getStoreName() + " 관심 목록에서 제거됨");
                } else {
                    System.out.println(store.getStoreName() + " 관심 목록에서 제거 실패");
                }
            }
        });
        contentPanel.add(likeButton);

        JPanel addressPanel = new JPanel();
        addressPanel.setLayout(new BoxLayout(addressPanel, BoxLayout.X_AXIS));
        addressPanel.setBorder(BorderFactory.createEmptyBorder(0,10,0,0));
        addressPanel.setBounds(10, 50, 360, 30); // 위치 및 크기 설정
        JLabel addressLabel = new JLabel(store.getLocation());
        JLabel gpsIcon = new JLabel(new ImageIcon("./image/GPS.png"));
        addressPanel.add(gpsIcon);
        addressPanel.add(Box.createHorizontalStrut(5)); // 간격 조절
        addressPanel.add(addressLabel);
        contentPanel.add(addressPanel);

        JPanel hoursPanel = new JPanel();
        hoursPanel.setLayout(new BoxLayout(hoursPanel, BoxLayout.X_AXIS));
        hoursPanel.setBorder(BorderFactory.createEmptyBorder(0,10,0,0));
        hoursPanel.setBounds(10, 90, 360, 30); // 위치 및 크기 설정
        JLabel hoursLabel = new JLabel(store.getOpenHours());
        JLabel clockIcon = new JLabel(new ImageIcon("./image/clock.png"));
        hoursPanel.add(clockIcon);
        hoursPanel.add(Box.createHorizontalStrut(5)); // 간격 조절
        hoursPanel.add(hoursLabel);
        contentPanel.add(hoursPanel);

        JPanel phonePanel = new JPanel();
        phonePanel.setLayout(new BoxLayout(phonePanel, BoxLayout.X_AXIS));
        phonePanel.setBorder(BorderFactory.createEmptyBorder(0,10,0,0));
        phonePanel.setBounds(10, 130, 360, 30); // 위치 및 크기 설정
        JLabel phoneIcon = new JLabel(new ImageIcon("./image/phone.png"));
        phonePanel.add(phoneIcon);
        phonePanel.add(Box.createHorizontalStrut(5)); // 간격 조절
        phoneLabel = new JLabel();
        phonePanel.add(phoneLabel);
        contentPanel.add(phonePanel);

        SellerDAO sellerDAO = new SellerDAO();
        String storePhone = sellerDAO.getStorePhoneBySellerNo(store.getSellerNo());
        phoneLabel.setText(storePhone != null ? storePhone : "전화번호 미등록");

        JPanel imagePanel = new JPanel(null);
        imagePanel.setBounds(10, 170, 340, 200);

        try {
            ImageIcon icon = store.getImage();
            JLabel imageLabel = new JLabel(icon);
            imageLabel.setBounds(0,0,325, icon.getIconHeight());
            imagePanel.add(imageLabel);
        }catch(Exception e){
        }
        /*
        System.out.println("Image Path: " + store.getImagePath());
        JLabel imageLabel = new JLabel(store.getImage());
        imagePanel.add(imageLabel);*/
        contentPanel.add(imagePanel);

        // 탭 패널
        JPanel tabPanel = new JPanel();
        tabPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        tabPanel.setBounds(0, 380, 345, 40); // 위치 및 크기 설정
        contentPanel.add(tabPanel);

        RButton infoTab = new RButton("상품정보");
        RButton newsTab = new RButton("가게소식");
        RButton reviewTab = new RButton("후기");

        infoTab.setBackgroundColor(new Color(210,210,210));
        infoTab.setPreferredSize(new Dimension(90,40));
        tabPanel.add(infoTab);

        newsTab.setBackgroundColor(new Color(210,210,210));
        newsTab.setPreferredSize(new Dimension(90,40));
        tabPanel.add(newsTab);

        reviewTab.setBackgroundColor(new Color(210,210,210));
        reviewTab.setPreferredSize(new Dimension(90,40));
        tabPanel.add(reviewTab);

        // 액션 버튼
        JPanel actionPanel = new JPanel(null);
        actionPanel.setBounds(0, 430, 360, 60); // 위치 및 크기 설정
        contentPanel.add(actionPanel);

        RButton reviewButton = new RButton("후기 작성");
        reviewButton.setBounds(25, 5, 230, 50);
        reviewButton.setBackgroundColor(new Color(0x2349BA));
        reviewButton.setTextColor(new Color(240,240,240));
        reviewButton.setFontSize(20);

        ImageIcon gpsImg = new ImageIcon("./image/GPSw.png");
        RButton mapButton = new RButton(gpsImg);
        mapButton.setBounds(265, 5, 50, 50);
        mapButton.setBackgroundColor(new Color(0x2349BA));

        actionPanel.add(reviewButton);
        actionPanel.add(mapButton);

        mapButton.addActionListener(e -> new Map(store.getStoreNo()).setVisible(true));
        reviewButton.addActionListener(e -> openReviewPage());

        contentPanel.revalidate();
        contentPanel.repaint();
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

    private void openReviewPage() {
        SwingUtilities.invokeLater(() -> {
            ReviewPage reviewPage = new ReviewPage();
            reviewPage.setLocationRelativeTo(this); //정중앙 배열
            reviewPage.setVisible(true);
        });
    }
    public static void main(String[] args) {
        // 예시 Store 객체 생성
        Store exampleStore = new Store(2, "Example Store", "09:00-18:00", "Example Location", 1, "./image/store2.jpg", 1);

        JFrame frame = new JFrame("StoreInfoPanel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 600);
        frame.add(new StoreInfoPanel(exampleStore));
        frame.setVisible(true);
    }
}
