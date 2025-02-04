import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LoginPage {
    private JFrame frame;
    private JTextField idField;
    private JPasswordField pwdField;

    public LoginPage() {
        frame = new JFrame("로그인 페이지");
        frame.setSize(360, 640);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 패널 중앙 배치용
        centerPanel.add(Box.createVerticalGlue()); // 상단 여백

        centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 80, 200, 80)); // 상단 0, 좌측 20, 하단 0, 우측 20

        JLabel welcomeLabel = new JLabel("안녕하세요.");
        welcomeLabel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        welcomeLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("소상링입니다.");
        subtitleLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
        subtitleLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);

        JLabel loginLabel = new JLabel("로그인");
        loginLabel.setFont(new Font("맑은 고딕", Font.BOLD, 18));
        loginLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);

        // ID 입력 필드
        idField = createInputField("  아이디를 입력하세요");

        // 비밀번호 입력 필드
        pwdField = createPasswordField("  비밀번호를 입력하세요");

        // 필드와 라벨 간의 위치를 맞추기 위해 JLabel 크기 설정
        welcomeLabel.setMaximumSize(idField.getMaximumSize());
        subtitleLabel.setMaximumSize(idField.getMaximumSize());
        loginLabel.setMaximumSize(idField.getMaximumSize());


        JLabel signUpLabel = new JLabel("회원가입");
        signUpLabel.setForeground(new Color(0x2349BA));
        signUpLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        signUpLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        signUpLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 10)); // 상단, 좌측, 하단, 우측 여백 설정

        signUpLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                frame.dispose(); // 현재 로그인 창 닫기
                new SignUpPage(); // 회원가입 페이지 열기
            }
        });

        RButton loginButton = new RButton("로그인");
        loginButton.setPreferredSize(new Dimension(100,30));
        loginButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
        loginButton.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 30)); // 상단 0, 좌측 20, 하단 0, 우측 20

// 로그인 버튼 위치를 조정
        Box buttonBox = Box.createHorizontalBox();
        buttonBox.add(Box.createHorizontalStrut(50)); // 좌측 여백
        buttonBox.add(loginButton);
        buttonBox.add(Box.createHorizontalStrut(50)); // 우측 여백

        centerPanel.add(buttonBox);

        loginButton.addActionListener(e -> login());
        KeyAdapter enter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    login();
                }
            }
        };
        idField.addKeyListener(enter);
        pwdField.addKeyListener(enter);

        centerPanel.add(welcomeLabel);
        centerPanel.add(subtitleLabel);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(loginLabel);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(idField);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(pwdField);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(signUpLabel);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(loginButton);

        centerPanel.add(Box.createVerticalGlue()); // 하단 여백

        frame.add(centerPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void login(){
        String id = idField.getText();
        String password = new String(pwdField.getPassword());

        if (id.isEmpty() || id.equals("아이디를 입력하세요")) {
            showError("아이디를 입력해주세요.");
            return;
        }
        if (password.isEmpty() || password.equals("비밀번호를 입력하세요")) {
            showError("비밀번호를 입력해주세요.");
            return;
        }

        if (validateLogin(id, password)) {
            JOptionPane.showMessageDialog(frame, "로그인 성공!");
            frame.dispose();
            SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
        } else {
            showError("아이디 또는 비밀번호가 일치하지 않습니다.");
        }
    }


    private boolean validateLogin(String id, String password) {
        String sql = "SELECT * FROM Member WHERE MemberID = ? AND Pwd = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                if(rs.next()){
                    //로그인 성공하면 LoggedInUser에 정보 저장
                    LoggedInUser.setMemberId(rs.getString("MemberID"));
                    LoggedInUser.setNick(rs.getString("Nick"));
                    LoggedInUser.setEmail(rs.getString("Email"));
                    return true;
                }else{
                    return false;
                }
            }
        } catch (SQLException e) {
            showError("데이터베이스 오류: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private JTextField createInputField(String placeholder) {
        JTextField field = new JTextField(25);
        field.setPreferredSize(new Dimension(300, 25)); // 가로: 300, 세로: 25
        field.setMaximumSize(new Dimension(300, 25));  // 가로: 300, 세로: 25
        field.setMaximumSize(field.getPreferredSize());
        field.setBorder(BorderFactory.createLineBorder(new Color(0xCAC4D0)));
        field.setText(placeholder);
        field.setForeground(new Color(0xCAC4D0));

        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setText(placeholder);
                    field.setForeground(new Color(0xCAC4D0));
                }
            }
        });
        return field;
    }

    private JPasswordField createPasswordField(String placeholder) {
        JPasswordField field = new JPasswordField(25);
        field.setMaximumSize(field.getPreferredSize());
        field.setPreferredSize(new Dimension(300, 25)); // 가로: 300, 세로: 40
        field.setMaximumSize(new Dimension(300, 25));  // 가로: 300, 세로: 40
        field.setBorder(BorderFactory.createLineBorder(new Color(0xCAC4D0)));
        field.setText(placeholder);
        field.setForeground(new Color(0xCAC4D0));

        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (new String(field.getPassword()).equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (field.getPassword().length == 0) {
                    field.setText(placeholder);
                    field.setForeground(new Color(0xCAC4D0));
                }
            }
        });
        return field;
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(frame, message, "오류", JOptionPane.ERROR_MESSAGE);
    }

    public JFrame getFrame() {
        return frame;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginPage());
    }
}