import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReviewScreen extends JPanel {
    private static final int REVIEWS_PER_PAGE = 8; // 한 페이지에 표시할 리뷰 수
    private JPanel mainPanel;
    private JPanel reviewListPanel;
    private JPanel headerPanel;
    private JPanel paginationPanel;
    private List<Review> reviews; // 리뷰 데이터 리스트
    private int currentPage = 1;
    private JScrollPane scrollPane;

    public ReviewScreen() {
        setLayout(new BorderLayout());

        // 헤더 패널
        headerPanel = createHeaderPanel(119, 4.29f); // 총 리뷰 수와 평균 평점 샘플 데이터
        add(headerPanel, BorderLayout.NORTH);

        // 메인 패널 (리뷰 리스트)
        mainPanel = new JPanel(new BorderLayout());
        reviewListPanel = new JPanel();
        reviewListPanel.setLayout(new BoxLayout(reviewListPanel, BoxLayout.Y_AXIS));

        // ★ scrollPane을 클래스 필드로 초기화
        scrollPane = new JScrollPane(reviewListPanel);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER); // 가로 스크롤 비활성화
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);


        // 페이징 패널
        paginationPanel = new JPanel(new FlowLayout());
        add(paginationPanel, BorderLayout.SOUTH);

        // 테스트용 데이터 생성
        reviews = generateSampleReviews(119); // 119개의 샘플 데이터 생성
        updateReviewList();
        setVisible(true);
    }

    private JPanel createHeaderPanel(int totalReviews, float avgRating) {
        JPanel header = new JPanel(new GridLayout(2, 1));
        header.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel totalReviewsLabel = new JLabel("전체 후기수: " + totalReviews);
        totalReviewsLabel.setFont(new Font("맑은고딕, Arial", Font.BOLD, 14));

        DecimalFormat df = new DecimalFormat("#.##");
        JLabel avgRatingLabel = new JLabel("사용자 총 평점 ★ " + df.format(avgRating) + " (평균값)");
        avgRatingLabel.setFont(new Font("맑은고딕, Arial", Font.PLAIN, 14));

        header.add(totalReviewsLabel);
        header.add(avgRatingLabel);
        return header;
    }

    private void updateReviewList() {
        reviewListPanel.removeAll();
        paginationPanel.removeAll();

        int start = (currentPage - 1) * REVIEWS_PER_PAGE;
        int end = Math.min(start + REVIEWS_PER_PAGE, reviews.size());

        for (int i = start; i < end; i++) {
            reviewListPanel.add(createReviewPanel(reviews.get(i)));
            reviewListPanel.add(new JSeparator());
        }

        createPaginationButtons();
        reviewListPanel.revalidate();
        reviewListPanel.repaint();
    }

    private JPanel createReviewPanel(Review review) {
        JPanel reviewPanel = new JPanel(new BorderLayout());
        reviewPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 1*5 그리드 레이아웃 패널 생성
        JPanel infoPanel = new JPanel(new GridLayout(1, 5, 0, 0)); // 1행 4열, 열 간격 10px

        // 1번 패널: 유저 프로필
        JPanel profilePanel = new JPanel();
        profilePanel.setBackground(Color.LIGHT_GRAY);
        profilePanel.setPreferredSize(new Dimension(40, 40)); // 크기 조정
        JLabel profileLabel = new JLabel("??", SwingConstants.CENTER);
        profilePanel.add(profileLabel);

        // 2번 패널: 별점과 작성일 (세로로 배치)
        JPanel ratingAndDatePanel = new JPanel();
        ratingAndDatePanel.setLayout(new BoxLayout(ratingAndDatePanel, BoxLayout.Y_AXIS));
        ratingAndDatePanel.setAlignmentX(Component.LEFT_ALIGNMENT); // 패널 자체도 왼쪽 정렬

        // 별점 추가
        JPanel starPanel = createStarPanel(review.getRating());
        starPanel.setAlignmentX(Component.LEFT_ALIGNMENT); // 별점 패널도 왼쪽 정렬
        ratingAndDatePanel.add(starPanel);

        // 작성일 추가
        JLabel dateLabel = new JLabel(new SimpleDateFormat("yyyy.MM.dd").format(review.getDate()));
        dateLabel.setAlignmentX(Component.LEFT_ALIGNMENT); // 작성일 왼쪽 정렬
        ratingAndDatePanel.add(Box.createVerticalStrut(5)); // 간격 추가
        ratingAndDatePanel.add(dateLabel);


        // 3번, 4번 패널: 빈 공간
        JPanel emptyPanel1 = new JPanel();
        JPanel emptyPanel2 = new JPanel();
        JPanel emptyPanel3 = new JPanel();

        // 패널 추가
        infoPanel.add(profilePanel);       // 1번 패널
        infoPanel.add(ratingAndDatePanel); // 2번 패널
        infoPanel.add(emptyPanel1);        // 3번 패널
        infoPanel.add(emptyPanel2);        // 4번 패널
        infoPanel.add(emptyPanel3);        // 5번 패널

        // 후기 내용 (4~5줄의 공간 확보)
        JTextArea contentArea = new JTextArea(review.getContent(), 4, 30); // 4행 30열로 크기 지정
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setEditable(false);

        reviewPanel.add(infoPanel, BorderLayout.NORTH); // 위쪽에 프로필, 별점, 작성일
        reviewPanel.add(contentArea, BorderLayout.CENTER); // 후기 내용은 중앙에 배치

        return reviewPanel;
    }

    private JPanel createStarPanel(int rating) {
        JPanel starPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, -3)); // 별 간격을 0으로 설정
        for (int i = 0; i < 5; i++) {
            JLabel starLabel = new JLabel(i < rating ? "★" : "☆");
            starLabel.setFont(new Font("맑은 고딕, Arial", Font.PLAIN, 18)); // 별 크기 조정
            starPanel.add(starLabel);
        }
        return starPanel;
    }

    private void createPaginationButtons() {
        int totalPages = (int) Math.ceil((double) reviews.size() / REVIEWS_PER_PAGE);

        for (int i = 1; i <= totalPages; i++) {
            JButton pageButton = new JButton(String.valueOf(i));
            pageButton.addActionListener(e -> {
                currentPage = Integer.parseInt(e.getActionCommand());
                updateReviewList();
            });
            paginationPanel.add(pageButton);
        }

        paginationPanel.revalidate();
        paginationPanel.repaint();
    }

    private List<Review> generateSampleReviews(int count) {
        List<Review> sampleReviews = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            sampleReviews.add(new Review("후기 내용 샘플 " + i, 3 + (i % 3), new Date()));
        }
        return sampleReviews;
    }

    public static void main(String[] args) {
        new ReviewScreen();
    }

    // 리뷰 데이터 클래스
    static class Review {
        private String content;
        private int rating;
        private Date date;

        public Review(String content, int rating, Date date) {
            this.content = content;
            this.rating = rating;
            this.date = date;
        }

        public String getContent() {
            return content;
        }

        public int getRating() {
            return rating;
        }

        public Date getDate() {
            return date;
        }
    }
}



