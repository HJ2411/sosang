import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.sql.*;
import java.time.LocalDate;

public class EventWrite extends JPanel {
    private static final Color NAVIGATION_COLOR = new Color(247, 211, 38);
    private JTextField titleField;
    private JTextArea contentArea;
    private String attachedFilePath = null; // 첨부파일 경로
    private int storeNo = 1; // 테스트용으로 StoreNo 기본값 설정

    private String extend;
    ImageSaver imgs = new ImageSaver();

    public EventWrite() {
        setLayout(new BorderLayout(10, 10));
        add(createEventWritePanel(), BorderLayout.CENTER);
    }
    public EventWrite(int storeNo){
        this.storeNo=storeNo;
        setLayout(new BorderLayout(10, 10));
        add(createEventWritePanel(storeNo), BorderLayout.CENTER);
    }
    public EventWrite(int noticeNo,int storeNo){
        this.storeNo = storeNo;
        setLayout(new BorderLayout(10, 10));
        add(correctionEventWritePanel(loadEvent(noticeNo)), BorderLayout.CENTER);
    }
    private JPanel createEventWritePanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 제목 라벨, 입력 필드, 첨부파일 버튼
        JPanel panel1 = new JPanel(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));

        JLabel textTitleLabel = new JLabel("제목: ");
        titleField = new JTextField(20);
        RButton fileButton = new RButton("첨부파일");

        topPanel.add(textTitleLabel);
        topPanel.add(titleField);
        topPanel.add(Box.createHorizontalStrut(10));
        topPanel.add(fileButton);

        JLabel fileLabel = new JLabel("No file selected");
        fileLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        //첨부파일 버튼 이벤트
        fileButton.addActionListener(e -> openFileChooser(fileLabel));
        fileButton.setBackground(NAVIGATION_COLOR);

        panel1.add(topPanel, BorderLayout.CENTER);
        panel1.add(fileLabel, BorderLayout.SOUTH);

        // 내용 작성
        contentArea = new JTextArea("내용을 입력해주세요.");
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(contentArea);

        // 버튼 (취소, 등록)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        RButton registerButton = new RButton("수정");
        RButton cancelButton = new RButton("취소");
        cancelButton.setBackground(NAVIGATION_COLOR);
        registerButton.setBackground(NAVIGATION_COLOR);

        buttonPanel.add(registerButton);
        buttonPanel.add(cancelButton);

        // 취소 버튼 이벤트
        cancelButton.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(this,
                    "정말로 취소하시겠습니까?", "취소",
                    JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                Window window = SwingUtilities.getWindowAncestor(this);
                if (window != null) {
                    window.dispose();
                }
            }
        });

        // 등록 버튼 이벤트
        registerButton.addActionListener(e -> registerEvent());

        panel.add(panel1, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createEventWritePanel(int storeNo) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 제목 라벨, 입력 필드, 첨부파일 버튼
        JPanel panel1 = new JPanel(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));

        JLabel textTitleLabel = new JLabel("제목: ");
        titleField = new JTextField(20);
        RButton fileButton = new RButton("첨부파일");

        topPanel.add(textTitleLabel);
        topPanel.add(titleField);
        topPanel.add(Box.createHorizontalStrut(10));
        topPanel.add(fileButton);

        JLabel fileLabel = new JLabel("No file selected");
        fileLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        //첨부파일 버튼 이벤트
        fileButton.addActionListener(e -> openFileChooser(fileLabel));
        fileButton.setBackground(NAVIGATION_COLOR);

        panel1.add(topPanel, BorderLayout.CENTER);
        panel1.add(fileLabel, BorderLayout.SOUTH);

        // 내용 작성
        contentArea = new JTextArea("내용을 입력해주세요.");
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(contentArea);

        // 버튼 (취소, 등록)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        RButton registerButton = new RButton("수정");
        RButton cancelButton = new RButton("취소");
        cancelButton.setBackground(NAVIGATION_COLOR);
        registerButton.setBackground(NAVIGATION_COLOR);

        buttonPanel.add(registerButton);
        buttonPanel.add(cancelButton);

        // 취소 버튼 이벤트
        cancelButton.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(this,
                    "정말로 취소하시겠습니까?", "취소",
                    JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                Window window = SwingUtilities.getWindowAncestor(this);
                if (window != null) {
                    window.dispose();
                }
            }
        });

        // 등록 버튼 이벤트
        registerButton.addActionListener(e -> registerEvent(storeNo));

        panel.add(panel1, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel correctionEventWritePanel(Notice notice) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 제목 라벨, 입력 필드, 첨부파일 버튼
        JPanel panel1 = new JPanel(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));

        JLabel textTitleLabel = new JLabel("제목: ");
        titleField = new JTextField(20);
        titleField.setText(notice.getTitle());
        RButton fileButton = new RButton("첨부파일");

        topPanel.add(textTitleLabel);
        topPanel.add(titleField);
        topPanel.add(Box.createHorizontalStrut(10));
        topPanel.add(fileButton);

        JLabel fileLabel = new JLabel(notice.getNImage());
        fileLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        //첨부파일 버튼 이벤트
        fileButton.addActionListener(e -> openFileChooser(fileLabel));
        fileButton.setBackground(NAVIGATION_COLOR);

        panel1.add(topPanel, BorderLayout.CENTER);
        panel1.add(fileLabel, BorderLayout.SOUTH);

        // 내용 작성
        contentArea = new JTextArea(notice.getContents());
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(contentArea);

        // 버튼 (취소, 등록)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        RButton registerButton = new RButton("수정");
        RButton cancelButton = new RButton("취소");
        cancelButton.setBackground(NAVIGATION_COLOR);
        registerButton.setBackground(NAVIGATION_COLOR);

        buttonPanel.add(registerButton);
        buttonPanel.add(cancelButton);

        // 취소 버튼 이벤트
        cancelButton.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(this,
                    "정말로 취소하시겠습니까?", "취소",
                    JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                Window window = SwingUtilities.getWindowAncestor(this);
                if (window != null) {
                    window.dispose();
                }
            }
        });

        // 수정 버튼 이벤트
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

        // 유효성 검사
        if (title.isEmpty()) {
            JOptionPane.showMessageDialog(this, "제목을 입력해주세요!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (content.isEmpty() || content.equals("내용을 입력해주세요.")) {
            JOptionPane.showMessageDialog(this, "내용을 입력해주세요!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }


        // 데이터베이스 삽입
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO Notice (Title, Contents, NImage, CreateDate, StoreNo) VALUES (?, ?, ?, ?, ?)";


            try (PreparedStatement statement = connection.prepareStatement(sql)) {

                statement.setString(1, title);
                statement.setString(2, content);
                if(!(extend==null))
                    statement.setString(3, "./image/NImage" + imgs.LastPlus("notice") + extend);
                else
                    statement.setString(3, "./image/NImage/NoImg.jpg");
                statement.setDate(4, java.sql.Date.valueOf(LocalDate.now())); // 오늘 날짜
                statement.setInt(5, storeNo); // StoreNo는 임시값으로 설정

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(this, "이벤트가 성공적으로 등록되었습니다!", "알림", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "등록에 실패했습니다. 다시 시도해주세요.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "데이터베이스 오류가 발생했습니다.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void registerEvent(int noticeNo) {
        String title = titleField.getText().trim();
        String content = contentArea.getText().trim();

        // 유효성 검사
        if (title.isEmpty()) {
            JOptionPane.showMessageDialog(this, "제목을 입력해주세요!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (content.isEmpty() || content.equals("내용을 입력해주세요.")) {
            JOptionPane.showMessageDialog(this, "내용을 입력해주세요!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }


        // 데이터베이스 삽입
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO Notice (Title, Contents, NImage, CreateDate, StoreNo) VALUES (?, ?, ?, ?, ?)";


            try (PreparedStatement statement = connection.prepareStatement(sql)) {

                statement.setString(1, title);
                statement.setString(2, content);
                if(!(extend==null))
                    statement.setString(3, "./image/NImage" + imgs.LastPlus("notice") + extend);
                else
                    statement.setString(3, "./image/NImage/NoImg.jpg");
                statement.setDate(4, java.sql.Date.valueOf(LocalDate.now())); // 오늘 날짜
                statement.setInt(5, storeNo); // StoreNo는 임시값으로 설정

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(this, "이벤트가 성공적으로 등록되었습니다!", "알림", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "등록에 실패했습니다. 다시 시도해주세요.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "데이터베이스 오류가 발생했습니다.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void correctionEvent(int noticeNo) {
        String title = titleField.getText().trim();
        String content = contentArea.getText().trim();

        // 유효성 검사
        if (title.isEmpty()) {
            JOptionPane.showMessageDialog(this, "제목을 입력해주세요!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (content.isEmpty() || content.equals("내용을 입력해주세요.")) {
            JOptionPane.showMessageDialog(this, "내용을 입력해주세요!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }


        // 데이터베이스 수정
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
                    JOptionPane.showMessageDialog(this, "이벤트가 성공적으로 등록되었습니다!", "알림", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "등록에 실패했습니다. 다시 시도해주세요.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "데이터베이스 오류가 발생했습니다.", "Error", JOptionPane.ERROR_MESSAGE);
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

    // 테스트용 메인 메서드
    public static void main(String[] args) {
        JFrame frame = new JFrame("EventWriteTest");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(360, 640);
        frame.add(new EventWrite());
        frame.setVisible(true);
    }

}

