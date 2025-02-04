import javax.swing.*;
import java.awt.*;

public class COptionPane {
    public static void showCustomDialog(Component parent, String message, String title) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(parent), title, true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(300, 120);
        dialog.setLocationRelativeTo(parent);

        JLabel messageLabel = new JLabel(message, SwingConstants.CENTER);

        // 오류 메시지일 경우 텍스트 색상 변경
        if (title.equalsIgnoreCase("에러")) {
            messageLabel.setForeground(Color.RED);
        }

        dialog.add(messageLabel, BorderLayout.CENTER);

        RButton okButton = new RButton("확인");
        okButton.addActionListener(e -> dialog.dispose());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);

        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }
}