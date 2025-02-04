import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewDetail extends JPanel {
    private JTextArea newCommentArea;
    private JPanel commentInputPanel;
    private JPanel existingCommentsPanel, contentPanel;
    private List<String> comments;

    public ReviewDetail(int reviewNo) {
        // 리뷰 상세 데이터 가져오기
        Review review = fetchReviewDetailsFromDB(reviewNo);
        if (review == null) {
            JOptionPane.showMessageDialog(null, "리뷰를 찾을 수 없습니다!", "오류", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 댓글 가져오기
        comments = fetchCommentsFromDB(reviewNo);

        // 패널 초기화
        setLayout(new BorderLayout());
        initializeComponents(review, reviewNo);
        layoutComponents();
    }

    private void initializeComponents(Review review, int reviewNo) {
        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 리뷰 정보
        contentPanel.add(createInfoPanel(review.getCreateDate(), review.getMemberID()));
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(createContentArea("내용: " + review.getContents()));
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(createContentArea("평점: ★ " + review.getRating()));
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(createContentArea("이미지: " + review.getReviewImage()));
        contentPanel.add(Box.createVerticalStrut(10));

        // 댓글 섹션
        contentPanel.add(createCommentSection(reviewNo));
    }

    private JPanel createInfoPanel(String createDate, String memberID) {
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

        JLabel dateLabel = new JLabel("작성일: " + createDate);
        JLabel memberLabel = new JLabel("작성자: " + memberID);

        infoPanel.add(dateLabel);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(memberLabel);

        return infoPanel;
    }

    private JTextArea createContentArea(String contents) {
        JTextArea contentArea = new JTextArea(contents);
        contentArea.setEditable(false);
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        return contentArea;
    }

    private JPanel createCommentSection(int reviewNo) {
        JPanel commentPanel = new JPanel();
        commentPanel.setLayout(new BoxLayout(commentPanel, BoxLayout.Y_AXIS));
        commentPanel.setBorder(BorderFactory.createTitledBorder("댓글"));

        // 기존 댓글 패널
        existingCommentsPanel = new JPanel();
        existingCommentsPanel.setLayout(new BoxLayout(existingCommentsPanel, BoxLayout.Y_AXIS));
        updateExistingComments(); // 댓글 표시

        // 댓글 입력 패널
        commentInputPanel = new JPanel(new BorderLayout());
        newCommentArea = new JTextArea(3, 20);
        newCommentArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        newCommentArea.setLineWrap(true);
        newCommentArea.setWrapStyleWord(true);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        RButton cancelButton = new RButton("취소");
        RButton submitButton = new RButton("등록");

        buttonPanel.add(submitButton);
        buttonPanel.add(cancelButton);
        commentInputPanel.add(newCommentArea, BorderLayout.CENTER);
        commentInputPanel.add(buttonPanel, BorderLayout.SOUTH);

        RButton writeCommentButton = new RButton("댓글 작성");
        writeCommentButton.addActionListener(e -> {
            commentInputPanel.setVisible(true);
            writeCommentButton.setVisible(false);
        });

        cancelButton.addActionListener(e -> {
            //newCommentArea.setText("");
            commentInputPanel.setVisible(false);
            writeCommentButton.setVisible(true);
        });

        submitButton.addActionListener(e -> {
            String newComment = newCommentArea.getText().trim();
            if (!newComment.isEmpty()) {
                saveCommentToDB(newComment, reviewNo);
                comments.add(newComment); // 새 댓글 추가
                newCommentArea.setText("");
                updateExistingComments(); // UI 갱신
                commentInputPanel.setVisible(false);
                writeCommentButton.setVisible(true);
            }
        });

        // 댓글 섹션 구성
        commentPanel.add(existingCommentsPanel);
        commentPanel.add(Box.createVerticalStrut(10));
        commentPanel.add(writeCommentButton);
        commentPanel.add(commentInputPanel);

        commentInputPanel.setVisible(false); // 기본 숨김

        return commentPanel;
    }

    private void saveCommentToDB(String newComment, int reviewNo) {
        String query = "INSERT INTO ReviewComments (NContents, ReviewNo, MemberID) VALUES (?, ?, ?)";
        String memberID = "M001"; // 테스트용 MemberID

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, newComment);
            stmt.setInt(2, reviewNo);
            stmt.setString(3, memberID);
            char[] Rating = new char[0];
            stmt.setString(4, String.valueOf(Rating));
            stmt.executeUpdate();

            System.out.println("댓글이 성공적으로 저장되었습니다!");
        } catch (SQLIntegrityConstraintViolationException e) {
            JOptionPane.showMessageDialog(this, "댓글 저장 중 무결성 오류가 발생했습니다!", "오류", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "댓글 저장 중 오류가 발생했습니다!", "오류", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private Review fetchReviewDetailsFromDB(int reviewNo) {
        String query = "SELECT * FROM Review WHERE ReviewNo = ?";
        Review review = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, reviewNo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    review = new Review(
                            rs.getInt("ReviewNo"),
                            rs.getString("CreateDate"),
                            rs.getString("ReviewImage"),
                            rs.getString("Contents"),
                            rs.getInt("Rating"),
                            rs.getString("MemberID")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return review;
    }

    private void updateExistingComments() {
        existingCommentsPanel.removeAll(); // 기존 댓글 제거
        for (String comment : comments) {
            JTextArea commentArea = new JTextArea(comment);
            commentArea.setEditable(false);
            commentArea.setLineWrap(true);
            commentArea.setWrapStyleWord(true);
            commentArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            existingCommentsPanel.add(commentArea);
        }
        existingCommentsPanel.  revalidate();
        existingCommentsPanel.repaint();
    }

    private List<String> fetchCommentsFromDB(int reviewNo) {
        List<String> fetchedComments = new ArrayList<>();
        String query = "SELECT NContents FROM ReviewComments WHERE ReviewNo = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, reviewNo);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    fetchedComments.add(rs.getString("NContents"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fetchedComments;
    }

    private void layoutComponents() {
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane, BorderLayout.CENTER);
    }

    private static class Review {
        private final int reviewNo;
        private final String createDate;
        private final String reviewImage;
        private final String contents;
        private final int rating;
        private final String memberID;

        public Review(int reviewNo, String createDate, String reviewImage, String contents, int rating, String memberID) {
            this.reviewNo = reviewNo;
            this.createDate = createDate;
            this.reviewImage = reviewImage;
            this.contents = contents;
            this.rating = rating;
            this.memberID = memberID;
        }

        public int getReviewNo() {
            return reviewNo;
        }

        public String getCreateDate() {
            return createDate;
        }

        public String getReviewImage() {
            return reviewImage;
        }

        public String getContents() {
            return contents;
        }

        public int getRating() {
            return rating;
        }

        public String getMemberID() {
            return memberID;
        }
    }
}
