import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class ReviewPage extends JFrame {
    public ReviewPage() {
        setTitle("�ı� �ۼ�");
        setSize(340, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // ���� �г� (��ü ������)
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // ���� ���� �г� ����
        JPanel topPanel = createStoreInfoPanel();

        // �ı� �ۼ� �г� ����
        JPanel reviewPanel = createReviewPanel();

        // ���� �гο� ���� ���� �гΰ� �ı� �ۼ� �г� �߰�
        mainPanel.add(topPanel, BorderLayout.NORTH);    // ���: ���� ����
        mainPanel.add(reviewPanel, BorderLayout.CENTER); // �߾�: �ı� �ۼ� ����

        add(mainPanel, BorderLayout.CENTER);
    }

    // ���� ���� �г� ���� �޼���
    private JPanel createStoreInfoPanel() {
        JLabel storeNameLabel = new JLabel(" �����ٰ���");
        storeNameLabel.setFont(new Font("���� ���", Font.BOLD, 16));

        JLabel storeRatingLabel = new JLabel(" �� 4.22");
        storeRatingLabel.setFont(new Font("���� ���", Font.PLAIN, 14));
        storeRatingLabel.setForeground(Color.BLACK);

        JLabel storeAddressLabel = new JLabel(" ����Ư���� ��õ�� �������ƿ﷿");
        storeAddressLabel.setFont(new Font("���� ���", Font.PLAIN, 12));

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

    // �ı� �ۼ� �г� ���� �޼���
    private JPanel createReviewPanel() {
        JPanel reviewPanel = new JPanel();
        reviewPanel.setLayout(new BorderLayout());
        reviewPanel.setBackground(Color.WHITE);

        RatingPanel ratingPanel = new RatingPanel();
        reviewPanel.add(ratingPanel, BorderLayout.NORTH);

        JLabel reviewLabel = new JLabel("�ı⸦ �ۼ����ּ���");
        reviewLabel.setFont(new Font("���� ���", Font.PLAIN, 14));
        reviewLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));

        JTextArea reviewTextArea = new JTextArea(8, 30);
        reviewTextArea.setLineWrap(true);
        reviewTextArea.setWrapStyleWord(true);
        reviewTextArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        RButton submitButton = new RButton("����ϱ�");
        submitButton.setPreferredSize(new Dimension(100, 30));
        submitButton.addActionListener(e -> {
            int selectedRating = ratingPanel.getSelectedRating();
            String reviewText = reviewTextArea.getText();
            // �ӽ÷� memberID, storeNo �Ҵ�
            String memberID = "M001";
            int storeNo = 1;

            if (selectedRating == 0) {
                JOptionPane.showMessageDialog(
                        reviewPanel,
                        "������ �������ּ���.",
                        "���",
                        JOptionPane.WARNING_MESSAGE
                );
            } else if (reviewText.isEmpty()) {
                JOptionPane.showMessageDialog(
                        reviewPanel,
                        "�ı� ������ �ۼ����ּ���.",
                        "���",
                        JOptionPane.WARNING_MESSAGE
                );
            } else {
                try {
                    insertReviewData(selectedRating, reviewText, memberID, storeNo);
                    JOptionPane.showMessageDialog(
                            reviewPanel,
                            "����: " + selectedRating + "\n�ı�: " + reviewText + "\n�ıⰡ ��ϵǾ����ϴ�.",
                            "��� �Ϸ�",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(
                            reviewPanel,
                            "�ı� ���� �� ������ �߻��߽��ϴ�: " + ex.getMessage(),
                            "����",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });

        RButton cancelButton = new RButton("���");
        cancelButton.setPreferredSize(new Dimension(100, 30));
        cancelButton.addActionListener( e -> {
            int result = JOptionPane.showConfirmDialog(this,
                    "�ۼ��� ����Ͻðڽ��ϱ�?\n�ۼ����� ������ ������� �ʽ��ϴ�.",
                    "���", 0);
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

            System.out.println("Review saved ����!");

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
