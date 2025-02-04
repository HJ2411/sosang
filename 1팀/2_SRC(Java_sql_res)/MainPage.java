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

        // 배너 패널 추가
        JPanel bannerPanel = createBannerPanel();
        contentPanel.add(bannerPanel, BorderLayout.NORTH);

        // 공지와 추천 게시판 추가
        JPanel boardsPanel = new JPanel(new GridLayout(2, 1, 10, 8));
        noticesPanel = createBoardPanel("공지사항 〉", "공지작성", getNoticesFromDB());
        reviewsPanel = createBoardPanel("추천 〉", "후기작성", getReviewsFromDB());

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

                // 리뷰 데이터를 배열로 저장
                reviews.add(new String[]{contents, createDate, memberId, reviewNo});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }



    private JPanel createBoardPanel(String title, String buttonText, List<?> items) {
        JPanel panel = new JPanel(new BorderLayout());

        // 제목과 버튼
        JPanel titlePanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));

        // 제목 클릭 이벤트 추가
        titleLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (title.equals("공지사항 〉")) {
                    ((MainFrame) SwingUtilities.getWindowAncestor(MainPage.this)).navigateToPage(new EventList());
                }
                if (title.equals("추천 〉")) {
                    ((MainFrame) SwingUtilities.getWindowAncestor(MainPage.this)).navigateToPage(new ReviewScreen());
                }
            }
        });
        titleLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        titlePanel.add(titleLabel, BorderLayout.WEST);

        //JButton actionButton = new JButton(buttonText);
        RButton actionButton = new RButton(buttonText);
        actionButton.addActionListener(e -> {
            if (buttonText.equals("공지작성")) {
                openNoticeCreationPage();
            } else if (buttonText.equals("후기작성")) {
                openReviewCreationPage();
            }
        });
        titlePanel.add(actionButton, BorderLayout.EAST);
        //titlePanel 좌우 여백추가
        titlePanel.setBorder(BorderFactory.createEmptyBorder(0,10,0,10));

        // 게시글 목록
        JPanel listPanel = new JPanel(new GridLayout(5, 1));
        updateBoardList(listPanel, items);

        panel.add(titlePanel, BorderLayout.NORTH);
        panel.add(listPanel, BorderLayout.CENTER);

        return panel;
    }

    private void updateBoardList(JPanel listPanel, List<?> items) {
        listPanel.removeAll(); // 기존 목록 제거

        for (Object item : items) {
            String displayText = "";
            int id = -1; // 기본값을 -1로 설정

            if (item instanceof String[]) {
                String[] data = (String[]) item;

                try {
                    // 공지사항 데이터 처리
                    if (data.length == 3) { // 공지사항: [Title, CreateDate, NoticeNo]
                        id = Integer.parseInt(data[2]);
                        //displayText = String.format("공지: %s | 작성일: %s | ID: %d", data[0], data[1], id);
                        displayText = String.format("Store.%s | %s (작성일 %s)", id, data[0], data[1]);
                    }
                    // 리뷰 데이터 처리
                    else if (data.length == 4) { // 리뷰: [Contents, CreateDate, MemberID, ReviewNo]
                        id = Integer.parseInt(data[3]);
                        //displayText = String.format("리뷰: %s | 작성자: %s | 작성일: %s", data[0], data[2], data[1]);
                        displayText = String.format("%s번째 추천 | %s", data[3], data[0]);
                    }
                } catch (NumberFormatException e) {
                    System.err.println("잘못된 ID 형식: " + data[data.length - 1]);
                }

            } else {
                continue; // 다른 타입은 무시
            }

            JButton itemButton = new JButton(displayText);
            itemButton.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
            itemButton.setHorizontalAlignment(SwingConstants.LEFT);
            itemButton.setContentAreaFilled(false);
            itemButton.setBorderPainted(true);
            //itemButton.setOpaque(false);


            // 버튼 클릭 이벤트 추가
            int finalId = id; // lambda에서 참조를 위해 final 선언
            String finalDisplayText = displayText;
            itemButton.addActionListener(e -> {
                if (finalId != -1) {
                    // 공지사항과 리뷰에 따라 다른 동작 설정 + 0129 정규식패턴으로 수정
                    Pattern noticePattern = Pattern.compile("^Store\\..*");
                    Pattern reviewPattern = Pattern.compile("^\\d+번째 추천\\s\\|\\s.*");
                    Matcher noticeMatcher = noticePattern.matcher(finalDisplayText);
                    Matcher reviewMatcher = reviewPattern.matcher(finalDisplayText);

                    if(noticeMatcher.matches()){
                        showEventDetail(finalId);
                    }else if(reviewMatcher.matches()){
                        showReviewDetail(finalId);
                    }

                    /*if (finalDisplayText.startsWith("Store:")) {
                        showEventDetail(finalId);
                    } else if (finalDisplayText.startsWith("리뷰:")) {
                        showReviewDetail(finalId);
                    }*/
                } else {
                    System.err.println("ID가 유효하지 않습니다.");
                }
            });

            listPanel.add(itemButton);
        }

        listPanel.revalidate(); // 레이아웃 갱신
        listPanel.repaint(); // 화면 갱신
    }

    private void showEventDetail(int noticeNo) {
        ((MainFrame) SwingUtilities.getWindowAncestor(this)).navigateToPage(new EventDetail(noticeNo));
/*
        // 기존 패널 제거 후 새로운 EventDetail 패널 추가
        contentPanel.removeAll();
        contentPanel.add(new EventDetail(noticeNo), BorderLayout.CENTER);

        // 패널 갱신
        contentPanel.revalidate();
        contentPanel.repaint();*/
    }

    private void showReviewDetail(int reviewNo) {
        ((MainFrame) SwingUtilities.getWindowAncestor(this)).navigateToPage(new ReviewDetail(reviewNo));
/*
        // 기존 패널 제거 후 새로운 EventDetail 패널 추가
        contentPanel.removeAll();
        contentPanel.add(new ReviewDetail(reviewNo), BorderLayout.CENTER);

        // 패널 갱신
        contentPanel.revalidate();
        contentPanel.repaint(); */
    }

    private void openNoticeCreationPage() {
        //JOptionPane.showMessageDialog(this, "공지 작성 페이지로 이동합니다.");
        JFrame noticeFrame = new JFrame("공지 작성");
        noticeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        noticeFrame.setSize(340, 500);
        noticeFrame.add(new EventWrite());
        noticeFrame.setLocationRelativeTo(this);
        noticeFrame.setVisible(true);

        // 공지사항 목록 갱신
        updateBoardList((JPanel) noticesPanel.getComponent(1), getNoticesFromDB());
    }

    private void openReviewCreationPage() {
        ReviewPage reviewPage = new ReviewPage();
        Window parentWindow = SwingUtilities.getWindowAncestor(this);
        if (parentWindow != null) {
            reviewPage.setLocationRelativeTo(parentWindow);
        }
        reviewPage.setVisible(true);


        // 리뷰 목록 갱신
        updateBoardList((JPanel) reviewsPanel.getComponent(1), getReviewsFromDB());

    }

    private JPanel createBannerPanel() {
        JPanel bannerPanel = new JPanel(new BorderLayout());
        bannerPanel.setPreferredSize(new Dimension(800, 150));
        bannerPanel.setBackground(new Color(200, 200, 200));
        JLabel bannerLabel = new JLabel("배너 이미지");
        bannerLabel.setHorizontalAlignment(JLabel.CENTER);
        bannerPanel.add(bannerLabel, BorderLayout.CENTER);
        return bannerPanel;
    }
}
