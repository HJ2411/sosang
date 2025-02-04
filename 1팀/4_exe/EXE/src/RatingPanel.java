import javax.swing.*;
import java.awt.*;

public class RatingPanel extends JPanel {
    private final JButton[] stars; // �� ��ư �迭
    private int selectedRating = 0; // ���õ� ���� (0~5)

    public RatingPanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0)); // FlowLayout���� ����
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // �г� ���� �߰�

        JLabel ratingLabel = new JLabel("���ϱ�");
        ratingLabel.setFont(new Font("���� ���", Font.PLAIN, 14)); // ��Ʈ ����
        ratingLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 10)); // ����
        add(ratingLabel);

        stars = new JButton[5];
        for (int i = 0; i < 5; i++) {
            stars[i] = new JButton("��");
            setStarButtonProperties(stars[i]);
            add(stars[i]);

            int starIndex = i + 1; // ������ 1���� ����
            stars[i].addActionListener(e -> setStarRating(starIndex));
        }
    }

    // ���� ���� (Ŭ���� ���� ä���� ���·� ǥ��)
    public void setStarRating(int rating) {
        selectedRating = rating;
        updateStarColors();
    }

    // ���� ���õ� ���� ��ȯ
    public int getSelectedRating() {
        return selectedRating;
    }

    // �� ��ư ��Ÿ�� ����
    private void setStarButtonProperties(JButton starButton) {
        starButton.setBackground(null);  // ��� ����
        starButton.setBorder(null);  // �׵θ� ����
        starButton.setFocusPainted(false);
        starButton.setFont(new Font("���� ���", Font.PLAIN, 20));
        starButton.setContentAreaFilled(false);  // ���� ���� ä���� ����
    }

    // �� ���� ������Ʈ (Ŭ���� ���� ���������� ä���)
    private void updateStarColors() {
        for (int i = 0; i < 5; i++) {
            if (i < selectedRating) {
                stars[i].setForeground(Color.BLACK); // ���õ� ��
            } else {
                stars[i].setForeground(Color.LIGHT_GRAY); // ���õ��� ���� ��
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("���� ��");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(300, 100);
            frame.add(new RatingPanel());
            frame.setVisible(true);
        });
    }
}