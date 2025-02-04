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
                    "�ش� Store ������ ã�� �� �����ϴ�.",
                    "����",
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
        contentPanel = new JPanel(null); // ���� ��ġ ���
        contentPanel.setBorder(BorderFactory.createEmptyBorder(15, 5, 5, 5));
        contentPanel.setPreferredSize(new Dimension(350, 800)); // ��ũ�� �г� ���� ũ�� ����

        JScrollPane scrollPane = new JScrollPane(contentPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
    }

    public void updateInfoUI() {
        contentPanel.removeAll();

        // ���� �̸� ��
        JLabel nameLabel = new JLabel(store.getStoreName());
        nameLabel.setFont(new Font("���� ���", Font.BOLD, 25));
        nameLabel.setBounds(20, 10, 250, 30); // ��ġ �� ũ�� ����
        contentPanel.add(nameLabel);

        // ���ƿ� ��ư
        JButton likeButton = new JButton("��");
        setLikeButtonProperties(likeButton, true);
        likeButton.setBounds(280, 10, 50, 30);

        //�α����� ���� ��Ͽ� �ֳ�
        InterestListManager interestListManager = new InterestListManager();
        boolean isLiked = interestListManager.isStoreLiked(LoggedInUser.getMemberId(), store.getStoreNo());
        setLikeButtonProperties(likeButton, isLiked);

        likeButton.addActionListener(e -> {
            boolean currentLikedStatus = likeButton.getText().equals("��");
            boolean newLikedStatus = !currentLikedStatus;
            setLikeButtonProperties(likeButton, newLikedStatus);

            if (newLikedStatus) {
                if (interestListManager.addInterestStore(LoggedInUser.getMemberId(), store.getStoreNo())) {
                    System.out.println(store.getStoreName() + " ���� ��Ͽ� �߰���");
                } else {
                    System.out.println(store.getStoreName() + " ���� ��Ͽ� �߰� ����");
                }
            } else {
                if (interestListManager.removeInterestStore(LoggedInUser.getMemberId(), store.getStoreNo())) {
                    System.out.println(store.getStoreName() + " ���� ��Ͽ��� ���ŵ�");
                } else {
                    System.out.println(store.getStoreName() + " ���� ��Ͽ��� ���� ����");
                }
            }
        });
        contentPanel.add(likeButton);

        JPanel addressPanel = new JPanel();
        addressPanel.setLayout(new BoxLayout(addressPanel, BoxLayout.X_AXIS));
        addressPanel.setBorder(BorderFactory.createEmptyBorder(0,10,0,0));
        addressPanel.setBounds(10, 50, 360, 30); // ��ġ �� ũ�� ����
        JLabel addressLabel = new JLabel(store.getLocation());
        JLabel gpsIcon = new JLabel(new ImageIcon("./image/GPS.png"));
        addressPanel.add(gpsIcon);
        addressPanel.add(Box.createHorizontalStrut(5)); // ���� ����
        addressPanel.add(addressLabel);
        contentPanel.add(addressPanel);

        JPanel hoursPanel = new JPanel();
        hoursPanel.setLayout(new BoxLayout(hoursPanel, BoxLayout.X_AXIS));
        hoursPanel.setBorder(BorderFactory.createEmptyBorder(0,10,0,0));
        hoursPanel.setBounds(10, 90, 360, 30); // ��ġ �� ũ�� ����
        JLabel hoursLabel = new JLabel(store.getOpenHours());
        JLabel clockIcon = new JLabel(new ImageIcon("./image/clock.png"));
        hoursPanel.add(clockIcon);
        hoursPanel.add(Box.createHorizontalStrut(5)); // ���� ����
        hoursPanel.add(hoursLabel);
        contentPanel.add(hoursPanel);

        JPanel phonePanel = new JPanel();
        phonePanel.setLayout(new BoxLayout(phonePanel, BoxLayout.X_AXIS));
        phonePanel.setBorder(BorderFactory.createEmptyBorder(0,10,0,0));
        phonePanel.setBounds(10, 130, 360, 30); // ��ġ �� ũ�� ����
        JLabel phoneIcon = new JLabel(new ImageIcon("./image/phone.png"));
        phonePanel.add(phoneIcon);
        phonePanel.add(Box.createHorizontalStrut(5)); // ���� ����
        phoneLabel = new JLabel();
        phonePanel.add(phoneLabel);
        contentPanel.add(phonePanel);

        SellerDAO sellerDAO = new SellerDAO();
        String storePhone = sellerDAO.getStorePhoneBySellerNo(store.getSellerNo());
        phoneLabel.setText(storePhone != null ? storePhone : "��ȭ��ȣ �̵��");

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

        // �� �г�
        JPanel tabPanel = new JPanel();
        tabPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        tabPanel.setBounds(0, 380, 345, 40); // ��ġ �� ũ�� ����
        contentPanel.add(tabPanel);

        RButton infoTab = new RButton("��ǰ����");
        RButton newsTab = new RButton("���Լҽ�");
        RButton reviewTab = new RButton("�ı�");

        infoTab.setBackgroundColor(new Color(210,210,210));
        infoTab.setPreferredSize(new Dimension(90,40));
        tabPanel.add(infoTab);

        newsTab.setBackgroundColor(new Color(210,210,210));
        newsTab.setPreferredSize(new Dimension(90,40));
        tabPanel.add(newsTab);

        reviewTab.setBackgroundColor(new Color(210,210,210));
        reviewTab.setPreferredSize(new Dimension(90,40));
        tabPanel.add(reviewTab);

        // �׼� ��ư
        JPanel actionPanel = new JPanel(null);
        actionPanel.setBounds(0, 430, 360, 60); // ��ġ �� ũ�� ����
        contentPanel.add(actionPanel);

        RButton reviewButton = new RButton("�ı� �ۼ�");
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
        likeButton.setText(isLiked ? "��" : "��");
        likeButton.setForeground(isLiked ? Color.RED : Color.GRAY);
        likeButton.setBackground(null);
        likeButton.setBorder(null);
        likeButton.setFocusPainted(false);
        likeButton.setFont(new Font("���� ���", Font.BOLD, 20));
        likeButton.setContentAreaFilled(false);
    }

    private void openReviewPage() {
        SwingUtilities.invokeLater(() -> {
            ReviewPage reviewPage = new ReviewPage();
            reviewPage.setLocationRelativeTo(this); //���߾� �迭
            reviewPage.setVisible(true);
        });
    }
    public static void main(String[] args) {
        // ���� Store ��ü ����
        Store exampleStore = new Store(2, "Example Store", "09:00-18:00", "Example Location", 1, "./image/store2.jpg", 1);

        JFrame frame = new JFrame("StoreInfoPanel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 600);
        frame.add(new StoreInfoPanel(exampleStore));
        frame.setVisible(true);
    }
}
