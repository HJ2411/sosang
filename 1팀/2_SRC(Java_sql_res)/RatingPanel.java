import javax.swing.*;
import java.awt.*;

public class RatingPanel extends JPanel {
    private final JButton[] stars; // 별 버튼 배열
    private int selectedRating = 0; // 선택된 별점 (0~5)

    public RatingPanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0)); // FlowLayout으로 설정
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // 패널 여백 추가

        JLabel ratingLabel = new JLabel("평가하기");
        ratingLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 14)); // 폰트 설정
        ratingLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 10)); // 여백
        add(ratingLabel);

        stars = new JButton[5];
        for (int i = 0; i < 5; i++) {
            stars[i] = new JButton("★");
            setStarButtonProperties(stars[i]);
            add(stars[i]);

            int starIndex = i + 1; // 별점은 1부터 시작
            stars[i].addActionListener(e -> setStarRating(starIndex));
        }
    }

    // 별점 설정 (클릭된 별은 채워진 상태로 표시)
    public void setStarRating(int rating) {
        selectedRating = rating;
        updateStarColors();
    }

    // 현재 선택된 별점 반환
    public int getSelectedRating() {
        return selectedRating;
    }

    // 별 버튼 스타일 설정
    private void setStarButtonProperties(JButton starButton) {
        starButton.setBackground(null);  // 배경 제거
        starButton.setBorder(null);  // 테두리 제거
        starButton.setFocusPainted(false);
        starButton.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
        starButton.setContentAreaFilled(false);  // 내용 영역 채우지 않음
    }

    // 별 상태 업데이트 (클릭된 별은 검은색으로 채우기)
    private void updateStarColors() {
        for (int i = 0; i < 5; i++) {
            if (i < selectedRating) {
                stars[i].setForeground(Color.BLACK); // 선택된 별
            } else {
                stars[i].setForeground(Color.LIGHT_GRAY); // 선택되지 않은 별
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("별점 평가");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(300, 100);
            frame.add(new RatingPanel());
            frame.setVisible(true);
        });
    }
}