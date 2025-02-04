import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.sql.*;
import java.time.LocalDate;

public class EventWrite extends JPanel {
    private static final Color NAVIGATION_COLOR = new Color(247, 211, 38);
    private JTextField titleField;
    private JTextArea contentArea;
    private String attachedFilePath = null; // ÷������ ���
    private int storeNo = 1; // �׽�Ʈ������ StoreNo �⺻�� ����

    private String extend;
    ImageSaver imgs = new ImageSaver();

    public EventWrite() {
        setLayout(new BorderLayout(10, 10));
        add(createEventWritePanel(), BorderLayout.CENTER);
    }

    public EventWrite(int noticeNo,int storeNo){
        this.storeNo = storeNo;
        setLayout(new BorderLayout(10, 10));
        add(correctionEventWritePanel(loadEvent(noticeNo)), BorderLayout.CENTER);
    }
    private JPanel createEventWritePanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // ���� ��, �Է� �ʵ�, ÷������ ��ư
        JPanel panel1 = new JPanel(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));

        JLabel textTitleLabel = new JLabel("����: ");
        titleField = new JTextField(20);
        RButton fileButton = new RButton("÷������");

        topPanel.add(textTitleLabel);
        topPanel.add(titleField);
        topPanel.add(Box.createHorizontalStrut(10));
        topPanel.add(fileButton);

        JLabel fileLabel = new JLabel("No file selected");
        fileLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        //÷������ ��ư �̺�Ʈ
        fileButton.addActionListener(e -> openFileChooser(fileLabel));
        fileButton.setBackground(NAVIGATION_COLOR);

        panel1.add(topPanel, BorderLayout.CENTER);
        panel1.add(fileLabel, BorderLayout.SOUTH);

        // ���� �ۼ�
        contentArea = new JTextArea("������ �Է����ּ���.");
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(contentArea);

        // ��ư (���, ���)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        RButton registerButton = new RButton("����");
        RButton cancelButton = new RButton("���");
        cancelButton.setBackground(NAVIGATION_COLOR);
        registerButton.setBackground(NAVIGATION_COLOR);

        buttonPanel.add(registerButton);
        buttonPanel.add(cancelButton);

        // ��� ��ư �̺�Ʈ
        cancelButton.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(this,
                    "������ ����Ͻðڽ��ϱ�?", "���",
                    JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                Window window = SwingUtilities.getWindowAncestor(this);
                if (window != null) {
                    window.dispose();
                }
            }
        });

        // ��� ��ư �̺�Ʈ
        registerButton.addActionListener(e -> registerEvent());

        panel.add(panel1, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel correctionEventWritePanel(Notice notice) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // ���� ��, �Է� �ʵ�, ÷������ ��ư
        JPanel panel1 = new JPanel(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));

        JLabel textTitleLabel = new JLabel("����: ");
        titleField = new JTextField(20);
        titleField.setText(notice.getTitle());
        RButton fileButton = new RButton("÷������");

        topPanel.add(textTitleLabel);
        topPanel.add(titleField);
        topPanel.add(Box.createHorizontalStrut(10));
        topPanel.add(fileButton);

        JLabel fileLabel = new JLabel(notice.getNImage());
        fileLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        //÷������ ��ư �̺�Ʈ
        fileButton.addActionListener(e -> openFileChooser(fileLabel));
        fileButton.setBackground(NAVIGATION_COLOR);

        panel1.add(topPanel, BorderLayout.CENTER);
        panel1.add(fileLabel, BorderLayout.SOUTH);

        // ���� �ۼ�
        contentArea = new JTextArea(notice.getContents());
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(contentArea);

        // ��ư (���, ���)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        RButton registerButton = new RButton("����");
        RButton cancelButton = new RButton("���");
        cancelButton.setBackground(NAVIGATION_COLOR);
        registerButton.setBackground(NAVIGATION_COLOR);

        buttonPanel.add(registerButton);
        buttonPanel.add(cancelButton);

        // ��� ��ư �̺�Ʈ
        cancelButton.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(this,
                    "������ ����Ͻðڽ��ϱ�?", "���",
                    JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                Window window = SwingUtilities.getWindowAncestor(this);
                if (window != null) {
                    window.dispose();
                }
            }
        });

        // ���� ��ư �̺�Ʈ
        registerButton.addActionListener(e -> correctionEvent(notice.getNoticeNo()));

        panel.add(panel1, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void openFileChooser(JLabel fileLabel) {
        if(imgs.save("NImage",fileLabel,"notice"))
            extend = imgs.getExtension(fileLabel.getText());

    }

    private void registerEvent() {
        String title = titleField.getText().trim();
        String content = contentArea.getText().trim();

        // ��ȿ�� �˻�
        if (title.isEmpty()) {
            JOptionPane.showMessageDialog(this, "������ �Է����ּ���!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (content.isEmpty() || content.equals("������ �Է����ּ���.")) {
            JOptionPane.showMessageDialog(this, "������ �Է����ּ���!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }


        // �����ͺ��̽� ����
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO Notice (Title, Contents, NImage, CreateDate, StoreNo) VALUES (?, ?, ?, ?, ?)";


            try (PreparedStatement statement = connection.prepareStatement(sql)) {

                statement.setString(1, title);
                statement.setString(2, content);
                if(!(extend==null))
                    statement.setString(3, "./image/NImage" + imgs.LastPlus("notice") + extend);
                else
                    statement.setString(3, "./image/NImage/NoImg.jpg");
                statement.setDate(4, java.sql.Date.valueOf(LocalDate.now())); // ���� ��¥
                statement.setInt(5, storeNo); // StoreNo�� �ӽð����� ����

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(this, "�̺�Ʈ�� ���������� ��ϵǾ����ϴ�!", "�˸�", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "��Ͽ� �����߽��ϴ�. �ٽ� �õ����ּ���.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "�����ͺ��̽� ������ �߻��߽��ϴ�.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void registerEvent(Notice notice) {
        String title = titleField.getText().trim();
        String content = contentArea.getText().trim();

        // ��ȿ�� �˻�
        if (title.isEmpty()) {
            JOptionPane.showMessageDialog(this, "������ �Է����ּ���!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (content.isEmpty() || content.equals("������ �Է����ּ���.")) {
            JOptionPane.showMessageDialog(this, "������ �Է����ּ���!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }


        // �����ͺ��̽� ����
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO Notice (Title, Contents, NImage, CreateDate, StoreNo) VALUES (?, ?, ?, ?, ?)";


            try (PreparedStatement statement = connection.prepareStatement(sql)) {

                statement.setString(1, title);
                statement.setString(2, content);
                if(!(extend==null))
                    statement.setString(3, "./image/NImage" + imgs.LastPlus("notice") + extend);
                else
                    statement.setString(3, "./image/NImage/NoImg.jpg");
                statement.setDate(4, java.sql.Date.valueOf(LocalDate.now())); // ���� ��¥
                statement.setInt(5, storeNo); // StoreNo�� �ӽð����� ����

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(this, "�̺�Ʈ�� ���������� ��ϵǾ����ϴ�!", "�˸�", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "��Ͽ� �����߽��ϴ�. �ٽ� �õ����ּ���.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "�����ͺ��̽� ������ �߻��߽��ϴ�.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void correctionEvent(int noticeNo) {
        String title = titleField.getText().trim();
        String content = contentArea.getText().trim();

        // ��ȿ�� �˻�
        if (title.isEmpty()) {
            JOptionPane.showMessageDialog(this, "������ �Է����ּ���!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (content.isEmpty() || content.equals("������ �Է����ּ���.")) {
            JOptionPane.showMessageDialog(this, "������ �Է����ּ���!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }


        // �����ͺ��̽� ����
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "update Notice set Title=?,Contents=?,NImage=? where noticeNo = "+noticeNo;

            try (PreparedStatement statement = connection.prepareStatement(sql)) {

                statement.setString(1, title);
                statement.setString(2, content);
                if(!(extend==null))
                    statement.setString(3, "./image/NImage/" + "notice"+noticeNo + extend);
                else
                    statement.setString(3, "./image/NImage/NoImg.jpg");

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(this, "�̺�Ʈ�� ���������� ��ϵǾ����ϴ�!", "�˸�", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "��Ͽ� �����߽��ϴ�. �ٽ� �õ����ּ���.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "�����ͺ��̽� ������ �߻��߽��ϴ�.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Notice loadEvent(int noticeNo){
        String sql = "select * from notice where NoticeNo="+noticeNo;
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        Notice notice =null;
        try{
            con = DatabaseConnection.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            while(rs.next()){
                notice = new Notice(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getDate(5),
                        rs.getInt(6)
                        );
            }
        }catch (SQLException e) {

        }finally {
            try{
                rs.close();
                stmt.close();
                con.close();
            } catch (SQLException e) {
            }
        }

        return notice;
    }

    // �׽�Ʈ�� ���� �޼���
    public static void main(String[] args) {
        JFrame frame = new JFrame("EventWriteTest");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(360, 640);
        frame.add(new EventWrite());
        frame.setVisible(true);
    }

}

