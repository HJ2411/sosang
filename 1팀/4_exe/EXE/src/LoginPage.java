import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LoginPage {
    private JFrame frame;
    private JTextField idField;
    private JPasswordField pwdField;

    public LoginPage() {
        frame = new JFrame("�α��� ������");
        frame.setSize(360, 640);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // �г� �߾� ��ġ��
        centerPanel.add(Box.createVerticalGlue()); // ��� ����

        centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 80, 200, 80)); // ��� 0, ���� 20, �ϴ� 0, ���� 20

        JLabel welcomeLabel = new JLabel("�ȳ��ϼ���.");
        welcomeLabel.setFont(new Font("���� ���", Font.BOLD, 20));
        welcomeLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("�һ��Դϴ�.");
        subtitleLabel.setFont(new Font("���� ���", Font.PLAIN, 20));
        subtitleLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);

        JLabel loginLabel = new JLabel("�α���");
        loginLabel.setFont(new Font("���� ���", Font.BOLD, 18));
        loginLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);

        // ID �Է� �ʵ�
        idField = createInputField("  ���̵� �Է��ϼ���");

        // ��й�ȣ �Է� �ʵ�
        pwdField = createPasswordField("  ��й�ȣ�� �Է��ϼ���");

        // �ʵ�� �� ���� ��ġ�� ���߱� ���� JLabel ũ�� ����
        welcomeLabel.setMaximumSize(idField.getMaximumSize());
        subtitleLabel.setMaximumSize(idField.getMaximumSize());
        loginLabel.setMaximumSize(idField.getMaximumSize());


        JLabel signUpLabel = new JLabel("ȸ������");
        signUpLabel.setForeground(new Color(0x2349BA));
        //signUpLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        signUpLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        signUpLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0)); // ���, ����, �ϴ�, ���� ���� ����

        signUpLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                frame.dispose(); // ���� �α��� â �ݱ�
                new SignUpPage(); // ȸ������ ������ ����
            }
        });

        RButton loginButton = new RButton("�α���");
        loginButton.setPreferredSize(new Dimension(100,30));
        loginButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
        loginButton.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 30)); // ��� 0, ���� 20, �ϴ� 0, ���� 20

// �α��� ��ư ��ġ�� ����
        Box buttonBox = Box.createHorizontalBox();
        buttonBox.add(Box.createHorizontalStrut(50)); // ���� ����
        buttonBox.add(loginButton);
        buttonBox.add(Box.createHorizontalStrut(50)); // ���� ����

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

        centerPanel.add(Box.createVerticalGlue()); // �ϴ� ����

        frame.add(centerPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void login(){
        String id = idField.getText();
        String password = new String(pwdField.getPassword());

        if (id.isEmpty() || id.equals("���̵� �Է��ϼ���")) {
            showError("���̵� �Է����ּ���.");
            return;
        }
        if (password.isEmpty() || password.equals("��й�ȣ�� �Է��ϼ���")) {
            showError("��й�ȣ�� �Է����ּ���.");
            return;
        }

        if (validateLogin(id, password)) {
            JOptionPane.showMessageDialog(frame, "�α��� ����!");
            frame.dispose();
            SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
        } else {
            showError("���̵� �Ǵ� ��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
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
                    //�α��� �����ϸ� LoggedInUser�� ���� ����
                    LoggedInUser.setMemberId(rs.getString("MemberID"));
                    LoggedInUser.setNick(rs.getString("Nick"));
                    LoggedInUser.setEmail(rs.getString("Email"));
                    return true;
                }else{
                    return false;
                }
            }
        } catch (SQLException e) {
            showError("�����ͺ��̽� ����: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private JTextField createInputField(String placeholder) {
        JTextField field = new JTextField(25);
        field.setPreferredSize(new Dimension(300, 25)); // ����: 300, ����: 25
        field.setMaximumSize(new Dimension(300, 25));  // ����: 300, ����: 25
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
        field.setPreferredSize(new Dimension(300, 25)); // ����: 300, ����: 40
        field.setMaximumSize(new Dimension(300, 25));  // ����: 300, ����: 40
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
        JOptionPane.showMessageDialog(frame, message, "����", JOptionPane.ERROR_MESSAGE);
    }

    public JFrame getFrame() {
        return frame;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginPage());
    }
}