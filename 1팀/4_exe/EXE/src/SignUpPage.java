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
        frame = new JFrame("ȸ������ ������");
        frame.setSize(360, 640);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout()); // �߾� ��ġ�� ���� GridBagLayout ���
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ȯ�� �޽���
        JLabel welcomeLabel = new JLabel("�һ󸵿� ���Ű���");
        welcomeLabel.setFont(new Font("���� ���", Font.BOLD, 20));
        welcomeLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("ȯ���մϴ�.");
        subtitleLabel.setFont(new Font("���� ���", Font.PLAIN, 20));
        subtitleLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        subtitleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 52)); // 'ȯ���մϴ�' ������ġ ����
        nameField = createInputField("  �̸�");
        nickField = createInputField("  �г���");
        idField = createInputField("  ���̵�");
        birthField = createInputField("  ������� (yyyyMMdd)");
        emailField = createInputField("  �̸���");
        pwdField = createPasswordField("  ��й�ȣ");
        confirmPwdField = createPasswordField("  ��й�ȣ Ȯ��");

        sellerCheckBox = new JCheckBox("�Ǹ����̽Ű���?");
        sellerCheckBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        sellerCheckBox.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 70));
        contactField = createInputField("  ����ó (���ڸ� �Է�)");
        businessRegField = createInputField("  ����� ��Ϲ�ȣ");
        contactField.setVisible(false);
        businessRegField.setVisible(false);

        // üũ�ڽ� �̺�Ʈ ó��
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

        // ȸ������ ��ư
        JButton signUpButton = new JButton("ȸ������");
        signUpButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        /* ��ư ���� ���̴� ����. �� �Ʒ� ��ư ��ġ ���� ���̾ƿ� �����̶� ���ļ� �׷��� ��ư ũ�Ⱑ ������������ ��µ� ��Ȱ��ȭ��.
        signUpButton.setBackground(Color.WHITE); // ���� ���
        signUpButton.setOpaque(true); // �������� �ʵ��� ����
        signUpButton.setBorderPainted(false); // ��ư�� �⺻ �ܰ��� ����
         */
        signUpButton.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 85)); // ��� 0, ���� 20, �ϴ� 0, ���� 20
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateInputs() && insertMemberData()) {
                    JOptionPane.showMessageDialog(frame, "ȸ�������� �Ϸ�Ǿ����ϴ�.");
                    clearFields();

                    frame.dispose();
                    SwingUtilities.invokeLater(() -> new LoginPage().getFrame().setVisible(true));
                }
            }
        });

        // �α��� ��ũ
        JLabel loginLabel = new JLabel("�α���");
        loginLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 85)); // ��� 0, ���� 20, �ϴ� 0, ���� 20
        loginLabel.setForeground(new Color(0x2349BA));
        loginLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JOptionPane.showMessageDialog(frame, "�α��� �������� �̵�");

                frame.dispose();
                SwingUtilities.invokeLater(() -> new LoginPage().getFrame().setVisible(true));
            }
        });

        // ������Ʈ ��ġ
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

        if (name.isEmpty() || name.equals("�̸�")) {
            showError("�̸��� �Է����ּ���.");
            return false;
        }
        if (nickname.isEmpty() || nickname.equals("�г���")) {
            showError("�г����� �Է����ּ���.");
            return false;
        }
        if (id.isEmpty() || id.equals("���̵�")) {
            showError("���̵� �Է����ּ���.");
            return false;
        }
        if (!birth.matches("\\d{8}")) {
            showError("��������� yyyyMMdd �������� �Է����ּ���.");
            return false;
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            showError("��ȿ�� �̸��� �ּҸ� �Է����ּ���.");
            return false;
        }
        if (!password.equals(confirmPassword)) {
            showError("��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
            return false;
        }
        if (sellerCheckBox.isSelected()) {
            if (contactField.getText().isEmpty() || contactField.getText().equals("����ó (���ڸ� �Է�)")) {
                showError("����ó�� �Է����ּ���.");
                return false;
            }
            if (!contactField.getText().matches("\\d+")) {
                showError("����ó�� ���ڸ� �Է����ּ���.");
                return false;
            }
            if (businessRegField.getText().isEmpty() || businessRegField.getText().equals("����� ��Ϲ�ȣ")) {
                showError("����� ��Ϲ�ȣ�� �Է����ּ���.");
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
            showError("�̹� �����ϴ� ���̵��Դϴ�.");
            return false;
        } catch (SQLException e) {
            showError("�����ͺ��̽� ����: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private JTextField createInputField(String placeholder) {
        JTextField field = new JTextField(20);
        field.setMaximumSize(new Dimension(300, 25)); // ����: 300, ����: 25
        field.setPreferredSize(new Dimension(300, 25)); // ����: 300, ����: 25
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
        field.setMaximumSize(new Dimension(300, 25)); // ����: 300, ����: 40
        field.setPreferredSize(new Dimension(300, 25)); // ����: 300, ����: 40
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

    private void clearFields() {
        nameField.setText(" �̸�");
        nickField.setText(" �г���");
        idField.setText(" ���̵�");
        birthField.setText(" ������� (yyyyMMdd)");
        emailField.setText(" �̸���");
        pwdField.setText(" ��й�ȣ");
        confirmPwdField.setText(" ��й�ȣ Ȯ��");
        contactField.setText(" ����ó (���ڸ� �Է�)");
        businessRegField.setText(" ����� ��Ϲ�ȣ");

        // ��� �ʵ� ���� �ʱ�ȭ
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

        // üũ�ڽ� �ʱ�ȭ
        sellerCheckBox.setSelected(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SignUpPage());
    }
}