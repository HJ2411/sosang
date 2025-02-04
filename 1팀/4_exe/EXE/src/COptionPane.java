import javax.swing.*;
import java.awt.*;

public class COptionPane {
    public static void showCustomDialog(Component parent, String message, String title) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(parent), title, true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(300, 120);
        dialog.setLocationRelativeTo(parent);

        JLabel messageLabel = new JLabel(message, SwingConstants.CENTER);

        // ���� �޽����� ��� �ؽ�Ʈ ���� ����
        if (title.equalsIgnoreCase("����")) {
            messageLabel.setForeground(Color.RED);
        }

        dialog.add(messageLabel, BorderLayout.CENTER);

        RButton okButton = new RButton("Ȯ��");
        okButton.addActionListener(e -> dialog.dispose());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);

        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }
}