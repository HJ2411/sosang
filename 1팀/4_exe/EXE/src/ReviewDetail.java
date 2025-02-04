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
        // ���� �� ������ ��������
        Review review = fetchReviewDetailsFromDB(reviewNo);
        if (review == null) {
            JOptionPane.showMessageDialog(null, "���並 ã�� �� �����ϴ�!", "����", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // ��� ��������
        comments = fetchCommentsFromDB(reviewNo);

        // �г� �ʱ�ȭ
        setLayout(new BorderLayout());
        initializeComponents(review, reviewNo);
        layoutComponents();
    }

    private void initializeComponents(Review review, int reviewNo) {
        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ���� ����
        contentPanel.add(createInfoPanel(review.getCreateDate(), review.getMemberID()));
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(createContentArea("����: " + review.getContents()));
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(createContentArea("����: �� " + review.getRating()));
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(createContentArea("�̹���: " + review.getReviewImage()));
        contentPanel.add(Box.createVerticalStrut(10));

        // ��� ����
        contentPanel.add(createCommentSection(reviewNo));
    }

    private JPanel createInfoPanel(String createDate, String memberID) {
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

        JLabel dateLabel = new JLabel("�ۼ���: " + createDate);
        JLabel memberLabel = new JLabel("�ۼ���: " + memberID);

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
        commentPanel.setBorder(BorderFactory.createTitledBorder("���"));

        // ���� ��� �г�
        existingCommentsPanel = new JPanel();
        existingCommentsPanel.setLayout(new BoxLayout(existingCommentsPanel, BoxLayout.Y_AXIS));
        updateExistingComments(); // ��� ǥ��

        // ��� �Է� �г�
        commentInputPanel = new JPanel(new BorderLayout());
        newCommentArea = new JTextArea(3, 20);
        newCommentArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        newCommentArea.setLineWrap(true);
        newCommentArea.setWrapStyleWord(true);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        RButton cancelButton = new RButton("���");
        RButton submitButton = new RButton("���");

        buttonPanel.add(submitButton);
        buttonPanel.add(cancelButton);
        commentInputPanel.add(newCommentArea, BorderLayout.CENTER);
        commentInputPanel.add(buttonPanel, BorderLayout.SOUTH);

        RButton writeCommentButton = new RButton("��� �ۼ�");
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
                comments.add(newComment); // �� ��� �߰�
                newCommentArea.setText("");
                updateExistingComments(); // UI ����
                commentInputPanel.setVisible(false);
                writeCommentButton.setVisible(true);
            }
        });

        // ��� ���� ����
        commentPanel.add(existingCommentsPanel);
        commentPanel.add(Box.createVerticalStrut(10));
        commentPanel.add(writeCommentButton);
        commentPanel.add(commentInputPanel);

        commentInputPanel.setVisible(false); // �⺻ ����

        return commentPanel;
    }

    private void saveCommentToDB(String newComment, int reviewNo) {
        String query = "INSERT INTO ReviewComments (NContents, ReviewNo, MemberID) VALUES (?, ?, ?)";
        String memberID = "M001"; // �׽�Ʈ�� MemberID

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, newComment);
            stmt.setInt(2, reviewNo);
            stmt.setString(3, memberID);
            char[] Rating = new char[0];
            stmt.setString(4, String.valueOf(Rating));
            stmt.executeUpdate();

            System.out.println("����� ���������� ����Ǿ����ϴ�!");
        } catch (SQLIntegrityConstraintViolationException e) {
            JOptionPane.showMessageDialog(this, "��� ���� �� ���Ἲ ������ �߻��߽��ϴ�!", "����", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "��� ���� �� ������ �߻��߽��ϴ�!", "����", JOptionPane.ERROR_MESSAGE);
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
        existingCommentsPanel.removeAll(); // ���� ��� ����
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
