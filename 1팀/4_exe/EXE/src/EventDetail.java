import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class EventDetail extends JPanel {
    private JTextArea newCommentArea;
    private JPanel commentInputPanel;
    private JPanel existingCommentsPanel, contentPanel;
    private List<String> comments;

    public EventDetail(int noticeNo) {
        Notice notice = fetchNoticeDetailsFromDB(noticeNo);
        if (notice == null) {
            JOptionPane.showMessageDialog(null, "Notice�� ã�� �� �����ϴ�!", "����", JOptionPane.ERROR_MESSAGE);
            return;
        }

        comments = new ArrayList<>();
        comments = fetchCommentsFromDB(noticeNo);

        setLayout(new BorderLayout());
        initializeComponents(notice, noticeNo);
        layoutComponents();
    }

    private void initializeComponents(Notice notice, int noticeNo) {
        contentPanel = new JPanel();
        createContentPanel(notice, noticeNo);
    }

    private void createContentPanel(Notice notice, int noticeNo) {
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        //JScrollPane scrollPane = new JScrollPane(contentPanel);
        //scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(contentPanel, BorderLayout.CENTER);

        contentPanel.add(createTitlePanel(notice.getTitle()));
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(createInfoPanel(notice.getCreateDate(), notice.getSellerNo()));
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(createContentArea(notice.getContents()));
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(createCommentSection(noticeNo));
    }

    private JPanel createInfoPanel(Date createDate, int sellerNo) {
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

        JLabel dateLabel = new JLabel("�ۼ���: " + createDate);
        JLabel sellerLabel = new JLabel("�Ǹ��� ��ȣ: " + sellerNo);

        infoPanel.add(dateLabel);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(sellerLabel);

        return infoPanel;
    }

    private JPanel createTitlePanel(String title) {
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(new Color(51, 102, 204));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        titlePanel.setPreferredSize(new Dimension(getWidth(),50)); //Ÿ��Ʋ�г� ũ�����
        titlePanel.add(titleLabel, BorderLayout.CENTER);

        return titlePanel;
    }

    private JTextArea createContentArea(String contents) {
        JTextArea contentArea = new JTextArea(contents);
        contentArea.setEditable(false);
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        contentArea.setPreferredSize(new Dimension(getWidth(), 200)); //�������� ũ�����

        return contentArea;
    }

    private JPanel createCommentSection(int noticeNo) {
        JPanel commentPanel = new JPanel();
        commentPanel.setLayout(new BoxLayout(commentPanel, BoxLayout.Y_AXIS));
        commentPanel.setBorder(BorderFactory.createTitledBorder("���"));
        AbstractBorder border = new LineBorder(Color.BLACK, 1);


        existingCommentsPanel = new JPanel();
        existingCommentsPanel.setLayout(new BoxLayout(existingCommentsPanel, BoxLayout.Y_AXIS));

        //��ũ�� �߰�
        JScrollPane commentScrollPane = new JScrollPane(existingCommentsPanel);
        commentScrollPane.setPreferredSize(new Dimension(380, 200)); //ũ�� ����
        //commentScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        updateExistingComments();

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

        RButton writeCommentButton = new RButton("����ۼ�");
        writeCommentButton.addActionListener(e -> {
            commentInputPanel.setVisible(true);
            writeCommentButton.setVisible(false);
        });

        //��ҹ�ư Ŭ����
        cancelButton.addActionListener(e -> {
            //newCommentArea.setText(""); //��ҹ�ư�� ������ ���� ���� �����
            commentInputPanel.setVisible(false);
            writeCommentButton.setVisible(true);
        });

        submitButton.addActionListener(e -> {
            String newComment = newCommentArea.getText().trim();
            if (!newComment.isEmpty()) {
                saveCommentToDB(newComment, noticeNo);
                comments.add(newComment);
                newCommentArea.setText("");
                updateExistingComments();
                commentInputPanel.setVisible(false);
                writeCommentButton.setVisible(true);
            }

        });

        commentPanel.add(existingCommentsPanel);
        commentPanel.add(Box.createVerticalStrut(10));
        commentPanel.add(writeCommentButton);
        commentPanel.add(commentInputPanel);

        commentInputPanel.setVisible(false);

        //0124 �߰� frame ����
        SwingUtilities.invokeLater(() -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(commentPanel);
            if (frame != null){
                int width = frame.getWidth() - 20;
                commentScrollPane.setPreferredSize(new Dimension(width, 200));
                commentPanel.revalidate();
                commentPanel.repaint();
            }
        });

        return commentPanel;
    }

    private void saveCommentToDB(String newComment, int noticeNo) {
        //int testMemberId = 1; // ������ MemberID �� ����

        String query = "INSERT INTO Comments (NContents, NoticeNo, MemberID) VALUES (?, ?, ?)";
        String memberID = "M001";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, newComment);
            stmt.setInt(2, noticeNo);
            stmt.setString(3, memberID); // �׽�Ʈ�� MemberID ����
            stmt.executeUpdate();

            System.out.println("����� ���������� ����Ǿ����ϴ�!");
        } catch (SQLIntegrityConstraintViolationException e) {
            JOptionPane.showMessageDialog(this, "��� ���� �� ���Ἲ ������ �߻��߽��ϴ�!", "����", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "��� ���� �� ������ �߻��߽��ϴ�!", "����", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private Notice fetchNoticeDetailsFromDB(int noticeNo) {
        String query = "SELECT * FROM Notice WHERE NoticeNo = ?";
        Notice notice = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, noticeNo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    notice = new Notice(
                            rs.getInt("NoticeNo"),
                            rs.getString("Title"),
                            rs.getString("Contents"),
                            rs.getString("NImage"),
                            rs.getDate("CreateDate"),
                            rs.getInt("StoreNo")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return notice;
    }

    private void updateExistingComments() {
        existingCommentsPanel.removeAll();
        for (String comment : comments) {
            JTextArea commentArea = new JTextArea(comment);
            commentArea.setEditable(false);
            commentArea.setLineWrap(true);
            commentArea.setWrapStyleWord(true);
            commentArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            existingCommentsPanel.add(commentArea);
        }
        existingCommentsPanel.revalidate();
        existingCommentsPanel.repaint();
    }

    private List<String> fetchCommentsFromDB(int noticeNo) {
        List<String> fetchedComments = new ArrayList<>();
        String query = "SELECT NContents FROM Comments WHERE NoticeNo = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, noticeNo);
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("�̺�Ʈ ��");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 700);
            frame.add(new EventDetail(1));
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
