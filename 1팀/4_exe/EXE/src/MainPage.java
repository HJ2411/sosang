import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainPage extends JPanel {
    private JPanel contentPanel;
    private JPanel noticesPanel;
    private JPanel reviewsPanel;

    public MainPage() {
        setLayout(new BorderLayout());
        initializeComponents();
    }

    private void initializeComponents() {
        contentPanel = new JPanel(new BorderLayout(0, 20));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        // ��� �г� �߰�
        JPanel bannerPanel = createBannerPanel();
        contentPanel.add(bannerPanel, BorderLayout.NORTH);

        // ������ ��õ �Խ��� �߰�
        JPanel boardsPanel = new JPanel(new GridLayout(2, 1, 10, 8));
        noticesPanel = createBoardPanel("�������� ��", "�����ۼ�", getNoticesFromDB());
        reviewsPanel = createBoardPanel("��õ ��", "�ı��ۼ�", getReviewsFromDB());

        boardsPanel.add(noticesPanel);
        boardsPanel.add(reviewsPanel);

        contentPanel.add(boardsPanel, BorderLayout.CENTER);

        add(contentPanel, BorderLayout.CENTER);
    }

    private List<String[]> getNoticesFromDB() {
        List<String[]> notices = new ArrayList<>();
        String query = "SELECT NoticeNo, Title, CreateDate FROM Notice ORDER BY CreateDate DESC LIMIT 5";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String noticeNo = String.valueOf(rs.getInt("NoticeNo"));
                String title = rs.getString("Title");
                String createDate = rs.getDate("CreateDate").toString();

                notices.add(new String[]{title, createDate, noticeNo});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notices;
    }


    private List<String[]> getReviewsFromDB() {
        List<String[]> reviews = new ArrayList<>();
        String query = "SELECT ReviewNo, Contents, CreateDate, MemberID FROM Review ORDER BY CreateDate DESC LIMIT 5";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String reviewNo = String.valueOf(rs.getInt("ReviewNo"));
                String contents = rs.getString("Contents");
                String createDate = rs.getDate("CreateDate").toString();
                String memberId = rs.getString("MemberID");

                // ���� �����͸� �迭�� ����
                reviews.add(new String[]{contents, createDate, memberId, reviewNo});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }



    private JPanel createBoardPanel(String title, String buttonText, List<?> items) {
        JPanel panel = new JPanel(new BorderLayout());

        // ����� ��ư
        JPanel titlePanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("���� ���", Font.BOLD, 14));

        // ���� Ŭ�� �̺�Ʈ �߰�
        titleLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (title.equals("�������� ��")) {
                    ((MainFrame) SwingUtilities.getWindowAncestor(MainPage.this)).navigateToPage(new EventList());
                }
                if (title.equals("��õ ��")) {
                    ((MainFrame) SwingUtilities.getWindowAncestor(MainPage.this)).navigateToPage(new ReviewScreen());
                }
            }
        });
        titleLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        titlePanel.add(titleLabel, BorderLayout.WEST);

        //JButton actionButton = new JButton(buttonText);
        RButton actionButton = new RButton(buttonText);
        actionButton.addActionListener(e -> {
            if (buttonText.equals("�����ۼ�")) {
                openNoticeCreationPage();
            } else if (buttonText.equals("�ı��ۼ�")) {
                openReviewCreationPage();
            }
        });
        titlePanel.add(actionButton, BorderLayout.EAST);
        //titlePanel �¿� �����߰�
        titlePanel.setBorder(BorderFactory.createEmptyBorder(0,10,0,10));

        // �Խñ� ���
        JPanel listPanel = new JPanel(new GridLayout(5, 1));
        updateBoardList(listPanel, items);

        panel.add(titlePanel, BorderLayout.NORTH);
        panel.add(listPanel, BorderLayout.CENTER);

        return panel;
    }

    private void updateBoardList(JPanel listPanel, List<?> items) {
        listPanel.removeAll(); // ���� ��� ����

        for (Object item : items) {
            String displayText = "";
            int id = -1; // �⺻���� -1�� ����

            if (item instanceof String[]) {
                String[] data = (String[]) item;

                try {
                    // �������� ������ ó��
                    if (data.length == 3) { // ��������: [Title, CreateDate, NoticeNo]
                        id = Integer.parseInt(data[2]);
                        //displayText = String.format("����: %s | �ۼ���: %s | ID: %d", data[0], data[1], id);
                        displayText = String.format("Store.%s | %s (�ۼ��� %s)", id, data[0], data[1]);
                    }
                    // ���� ������ ó��
                    else if (data.length == 4) { // ����: [Contents, CreateDate, MemberID, ReviewNo]
                        id = Integer.parseInt(data[3]);
                        //displayText = String.format("����: %s | �ۼ���: %s | �ۼ���: %s", data[0], data[2], data[1]);
                        displayText = String.format("%s��° ��õ | %s", data[3], data[0]);
                    }
                } catch (NumberFormatException e) {
                    System.err.println("�߸��� ID ����: " + data[data.length - 1]);
                }

            } else {
                continue; // �ٸ� Ÿ���� ����
            }

            JButton itemButton = new JButton(displayText);
            itemButton.setFont(new Font("���� ���", Font.PLAIN, 12));
            itemButton.setHorizontalAlignment(SwingConstants.LEFT);
            itemButton.setContentAreaFilled(false);
            itemButton.setBorderPainted(true);
            //itemButton.setOpaque(false);


            // ��ư Ŭ�� �̺�Ʈ �߰�
            int finalId = id; // lambda���� ������ ���� final ����
            String finalDisplayText = displayText;
            itemButton.addActionListener(e -> {
                if (finalId != -1) {
                    // �������װ� ���信 ���� �ٸ� ���� ���� + 0129 ���Խ��������� ����
                    Pattern noticePattern = Pattern.compile("^Store\\..*");
                    Pattern reviewPattern = Pattern.compile("^\\d+��° ��õ\\s\\|\\s.*");
                    Matcher noticeMatcher = noticePattern.matcher(finalDisplayText);
                    Matcher reviewMatcher = reviewPattern.matcher(finalDisplayText);

                    if(noticeMatcher.matches()){
                        showEventDetail(finalId);
                    }else if(reviewMatcher.matches()){
                        showReviewDetail(finalId);
                    }

                    /*if (finalDisplayText.startsWith("Store:")) {
                        showEventDetail(finalId);
                    } else if (finalDisplayText.startsWith("����:")) {
                        showReviewDetail(finalId);
                    }*/
                } else {
                    System.err.println("ID�� ��ȿ���� �ʽ��ϴ�.");
                }
            });

            listPanel.add(itemButton);
        }

        listPanel.revalidate(); // ���̾ƿ� ����
        listPanel.repaint(); // ȭ�� ����
    }

    private void showEventDetail(int noticeNo) {
        ((MainFrame) SwingUtilities.getWindowAncestor(this)).navigateToPage(new EventDetail(noticeNo));
/*
        // ���� �г� ���� �� ���ο� EventDetail �г� �߰�
        contentPanel.removeAll();
        contentPanel.add(new EventDetail(noticeNo), BorderLayout.CENTER);

        // �г� ����
        contentPanel.revalidate();
        contentPanel.repaint();*/
    }

    private void showReviewDetail(int reviewNo) {
        ((MainFrame) SwingUtilities.getWindowAncestor(this)).navigateToPage(new ReviewDetail(reviewNo));
/*
        // ���� �г� ���� �� ���ο� EventDetail �г� �߰�
        contentPanel.removeAll();
        contentPanel.add(new ReviewDetail(reviewNo), BorderLayout.CENTER);

        // �г� ����
        contentPanel.revalidate();
        contentPanel.repaint(); */
    }

    private void openNoticeCreationPage() {
        //JOptionPane.showMessageDialog(this, "���� �ۼ� �������� �̵��մϴ�.");
        JFrame noticeFrame = new JFrame("���� �ۼ�");
        noticeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        noticeFrame.setSize(340, 500);
        noticeFrame.add(new EventWrite());
        noticeFrame.setLocationRelativeTo(this);
        noticeFrame.setVisible(true);

        // �������� ��� ����
        updateBoardList((JPanel) noticesPanel.getComponent(1), getNoticesFromDB());
    }

    private void openReviewCreationPage() {
        ReviewPage reviewPage = new ReviewPage();
        Window parentWindow = SwingUtilities.getWindowAncestor(this);
        if (parentWindow != null) {
            reviewPage.setLocationRelativeTo(parentWindow);
        }
        reviewPage.setVisible(true);


        // ���� ��� ����
        updateBoardList((JPanel) reviewsPanel.getComponent(1), getReviewsFromDB());

    }

    private JPanel createBannerPanel() {
        JPanel bannerPanel = new JPanel(new BorderLayout());
        bannerPanel.setPreferredSize(new Dimension(800, 150));
        bannerPanel.setBackground(new Color(200, 200, 200));
        JLabel bannerLabel = new JLabel("��� �̹���");
        bannerLabel.setHorizontalAlignment(JLabel.CENTER);
        bannerPanel.add(bannerLabel, BorderLayout.CENTER);
        return bannerPanel;
    }
}
