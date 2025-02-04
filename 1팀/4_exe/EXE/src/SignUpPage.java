import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class SignUpPage {
    private JFrame frame;
    private JTextField nameField, nickField, idField, birthField, contactField, businessRegField, emailField;
    private JPasswordField pwdField, confirmPwdField;
    private JCheckBox sellerCheckBox;

    public SignUpPage() {
        frame = new JFrame("회원가입 페이지");
        frame.setSize(360, 640);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout()); // 중앙 배치를 위한 GridBagLayout 사용
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 환영 메시지
        JLabel welcomeLabel = new JLabel("소상링에 오신것을");
        welcomeLabel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        welcomeLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("환영합니다.");
        subtitleLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
        subtitleLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        subtitleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 52)); // '환영합니다' 글자위치 수정
        nameField = createInputField("  이름");
        nickField = createInputField("  닉네임");
        idField = createInputField("  아이디");
        birthField = createInputField("  생년월일 (yyyyMMdd)");
        emailField = createInputField("  이메일");
        pwdField = createPasswordField("  비밀번호");
        confirmPwdField = createPasswordField("  비밀번호 확인");

        sellerCheckBox = new JCheckBox("판매자이신가요?");
        sellerCheckBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        sellerCheckBox.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 70));
        contactField = createInputField("  연락처 (숫자만 입력)");
        businessRegField = createInputField("  사업자 등록번호");
        contactField.setVisible(false);
        businessRegField.setVisible(false);

        // 체크박스 이벤트 처리
        sellerCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                boolean isSeller = e.getStateChange() == ItemEvent.SELECTED;
                contactField.setVisible(isSeller);
                businessRegField.setVisible(isSeller);
                frame.revalidate();
                frame.repaint();
            }
        });

        // 회원가입 버튼
        JButton signUpButton = new JButton("회원가입");
        signUpButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        /* 버튼 배경색 보이는 로직. 단 아래 버튼 위치 설정 레이아웃 설정이랑 겹쳐서 그런지 버튼 크기가 비정상적으로 출력돼 비활성화함.
        signUpButton.setBackground(Color.WHITE); // 배경색 흰색
        signUpButton.setOpaque(true); // 투명하지 않도록 설정
        signUpButton.setBorderPainted(false); // 버튼의 기본 외곽선 제거
         */
        signUpButton.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 85)); // 상단 0, 좌측 20, 하단 0, 우측 20
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateInputs() && insertMemberData()) {
                    JOptionPane.showMessageDialog(frame, "회원가입이 완료되었습니다.");
                    clearFields();

                    frame.dispose();
                    SwingUtilities.invokeLater(() -> new LoginPage().getFrame().setVisible(true));
                }
            }
        });

        // 로그인 링크
        JLabel loginLabel = new JLabel("로그인");
        loginLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 85)); // 상단 0, 좌측 20, 하단 0, 우측 20
        loginLabel.setForeground(new Color(0x2349BA));
        loginLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JOptionPane.showMessageDialog(frame, "로그인 페이지로 이동");

                frame.dispose();
                SwingUtilities.invokeLater(() -> new LoginPage().getFrame().setVisible(true));
            }
        });

        // 컴포넌트 배치
        centerPanel.add(welcomeLabel);
        centerPanel.add(subtitleLabel);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(nameField);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(nickField);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(idField);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(birthField);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(emailField);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(pwdField);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(confirmPwdField);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(sellerCheckBox);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(contactField);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(businessRegField);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(signUpButton);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(loginLabel);

        frame.add(centerPanel, gbc);
        frame.setVisible(true);
    }

    private boolean validateInputs() {
        String name = nameField.getText();
        String nickname = nickField.getText();
        String id = idField.getText();
        String birth = birthField.getText();
        String email = emailField.getText();
        String password = new String(pwdField.getPassword());
        String confirmPassword = new String(confirmPwdField.getPassword());

        if (name.isEmpty() || name.equals("이름")) {
            showError("이름을 입력해주세요.");
            return false;
        }
        if (nickname.isEmpty() || nickname.equals("닉네임")) {
            showError("닉네임을 입력해주세요.");
            return false;
        }
        if (id.isEmpty() || id.equals("아이디")) {
            showError("아이디를 입력해주세요.");
            return false;
        }
        if (!birth.matches("\\d{8}")) {
            showError("생년월일은 yyyyMMdd 형식으로 입력해주세요.");
            return false;
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            showError("유효한 이메일 주소를 입력해주세요.");
            return false;
        }
        if (!password.equals(confirmPassword)) {
            showError("비밀번호가 일치하지 않습니다.");
            return false;
        }
        if (sellerCheckBox.isSelected()) {
            if (contactField.getText().isEmpty() || contactField.getText().equals("연락처 (숫자만 입력)")) {
                showError("연락처를 입력해주세요.");
                return false;
            }
            if (!contactField.getText().matches("\\d+")) {
                showError("연락처는 숫자만 입력해주세요.");
                return false;
            }
            if (businessRegField.getText().isEmpty() || businessRegField.getText().equals("사업자 등록번호")) {
                showError("사업자 등록번호를 입력해주세요.");
                return false;
            }
        }

        return true;
    }

    private boolean insertMemberData() {
        String sql = "INSERT INTO Member (MemberID, Pwd, Name, Nick, Birth, Email, MemberImage) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            String birthDate = birthField.getText();
            String formattedBirth = birthDate.substring(0, 4) + "-" +
                    birthDate.substring(4, 6) + "-" +
                    birthDate.substring(6, 8);

            pstmt.setString(1, idField.getText());
            pstmt.setString(2, new String(pwdField.getPassword()));
            pstmt.setString(3, nameField.getText());
            pstmt.setString(4, nickField.getText());
            pstmt.setString(5, formattedBirth);
            pstmt.setString(6, emailField.getText());
            pstmt.setString(7, "default.jpg");

            int result = pstmt.executeUpdate();
            return result > 0;

        } catch (SQLIntegrityConstraintViolationException e) {
            showError("이미 존재하는 아이디입니다.");
            return false;
        } catch (SQLException e) {
            showError("데이터베이스 오류: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private JTextField createInputField(String placeholder) {
        JTextField field = new JTextField(20);
        field.setMaximumSize(new Dimension(300, 25)); // 가로: 300, 세로: 25
        field.setPreferredSize(new Dimension(300, 25)); // 가로: 300, 세로: 25
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
        JPasswordField field = new JPasswordField(20);
        field.setMaximumSize(field.getPreferredSize());
        field.setMaximumSize(new Dimension(300, 25)); // 가로: 300, 세로: 40
        field.setPreferredSize(new Dimension(300, 25)); // 가로: 300, 세로: 40
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

    private void clearFields() {
        nameField.setText(" 이름");
        nickField.setText(" 닉네임");
        idField.setText(" 아이디");
        birthField.setText(" 생년월일 (yyyyMMdd)");
        emailField.setText(" 이메일");
        pwdField.setText(" 비밀번호");
        confirmPwdField.setText(" 비밀번호 확인");
        contactField.setText(" 연락처 (숫자만 입력)");
        businessRegField.setText(" 사업자 등록번호");

        // 모든 필드 색상 초기화
        Color placeholderColor = new Color(0xCAC4D0);
        nameField.setForeground(placeholderColor);
        nickField.setForeground(placeholderColor);
        idField.setForeground(placeholderColor);
        birthField.setForeground(placeholderColor);
        emailField.setForeground(placeholderColor);
        pwdField.setForeground(placeholderColor);
        confirmPwdField.setForeground(placeholderColor);
        contactField.setForeground(placeholderColor);
        businessRegField.setForeground(placeholderColor);

        // 체크박스 초기화
        sellerCheckBox.setSelected(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SignUpPage());
    }
}