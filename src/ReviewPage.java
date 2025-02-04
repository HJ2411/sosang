import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class ReviewPage extends JFrame {
    public ReviewPage() {
        setTitle("후기 작성");
        setSize(340, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // 메인 패널 (전체 페이지)
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // 가게 정보 패널 설정
        JPanel topPanel = createStoreInfoPanel();

        // 후기 작성 패널 설정
        JPanel reviewPanel = createReviewPanel();

        // 메인 패널에 가게 정보 패널과 후기 작성 패널 추가
        mainPanel.add(topPanel, BorderLayout.NORTH);    // 상단: 가게 정보
        mainPanel.add(reviewPanel, BorderLayout.CENTER); // 중앙: 후기 작성 영역

        add(mainPanel, BorderLayout.CENTER);
    }

    // 가게 정보 패널 생성 메서드
    private JPanel createStoreInfoPanel() {
        JLabel storeNameLabel = new JLabel(" 가나다가게");
        storeNameLabel.setFont(new Font("맑은 고딕", Font.BOLD, 16));

        JLabel storeRatingLabel = new JLabel(" ★ 4.22");
        storeRatingLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        storeRatingLabel.setForeground(Color.BLACK);

        JLabel storeAddressLabel = new JLabel(" 서울특별시 금천구 마리오아울렛");
        storeAddressLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 12));

        JPanel photoPanel = new JPanel();
        photoPanel.setBackground(Color.GRAY);
        photoPanel.setPreferredSize(new Dimension(80, 120));

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.add(storeNameLabel);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(storeRatingLabel);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(storeAddressLabel);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.setBackground(Color.WHITE);
        topPanel.add(photoPanel);
        topPanel.add(Box.createHorizontalStrut(10));
        topPanel.add(infoPanel);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return topPanel;
    }

    // 후기 작성 패널 생성 메서드
    private JPanel createReviewPanel() {
        JPanel reviewPanel = new JPanel();
        reviewPanel.setLayout(new BorderLayout());
        reviewPanel.setBackground(Color.WHITE);

        RatingPanel ratingPanel = new RatingPanel();
        reviewPanel.add(ratingPanel, BorderLayout.NORTH);

        JLabel reviewLabel = new JLabel("후기를 작성해주세요");
        reviewLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        reviewLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));

        JTextArea reviewTextArea = new JTextArea(8, 30);
        reviewTextArea.setLineWrap(true);
        reviewTextArea.setWrapStyleWord(true);
        reviewTextArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        RButton submitButton = new RButton("등록하기");
        submitButton.setPreferredSize(new Dimension(100, 30));
        submitButton.addActionListener(e -> {
            int selectedRating = ratingPanel.getSelectedRating();
            String reviewText = reviewTextArea.getText();
            // 임시로 memberID, storeNo 할당
            String memberID = "M001";
            int storeNo = 1;

            if (selectedRating == 0) {
                JOptionPane.showMessageDialog(
                        reviewPanel,
                        "별점을 선택해주세요.",
                        "경고",
                        JOptionPane.WARNING_MESSAGE
                );
            } else if (reviewText.isEmpty()) {
                JOptionPane.showMessageDialog(
                        reviewPanel,
                        "후기 내용을 작성해주세요.",
                        "경고",
                        JOptionPane.WARNING_MESSAGE
                );
            } else {
                try {
                    insertReviewData(selectedRating, reviewText, memberID, storeNo);
                    JOptionPane.showMessageDialog(
                            reviewPanel,
                            "별점: " + selectedRating + "\n후기: " + reviewText + "\n후기가 등록되었습니다.",
                            "등록 완료",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(
                            reviewPanel,
                            "후기 저장 중 오류가 발생했습니다: " + ex.getMessage(),
                            "오류",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });

        RButton cancelButton = new RButton("취소");
        cancelButton.setPreferredSize(new Dimension(100, 30));
        cancelButton.addActionListener( e -> {
            int result = JOptionPane.showConfirmDialog(this,
                    "작성을 취소하시겠습니까?\n작성중인 내용은 저장되지 않습니다.",
                    "취소", 0);
            if(result == 0){
                this.dispose();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(submitButton);
        buttonPanel.add(cancelButton);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(reviewLabel, BorderLayout.NORTH);
        centerPanel.add(new JScrollPane(reviewTextArea), BorderLayout.CENTER);

        reviewPanel.add(centerPanel, BorderLayout.CENTER);
        reviewPanel.add(buttonPanel, BorderLayout.SOUTH);

        return reviewPanel;
    }

    private boolean insertReviewData(int rating, String contents, String memberID, int storeNo) {
        String sql = "insert into REVIEW (CreateDate, Rating, Contents, MemberID, StoreNo) values (now(), ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, rating);
            pstmt.setString(2, contents);
            pstmt.setString(3, memberID);
            pstmt.setInt(4, storeNo);
            pstmt.executeUpdate();

            System.out.println("Review saved 성공!");

        } catch (SQLException se) {
            se.printStackTrace();
        }

        return false;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ReviewPage reviewPage = new ReviewPage();
            reviewPage.setVisible(true);
        });
    }
}
