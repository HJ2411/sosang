import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReviewScreen extends JPanel {
    private static final int REVIEWS_PER_PAGE = 8; // �� �������� ǥ���� ���� ��
    private JPanel mainPanel;
    private JPanel reviewListPanel;
    private JPanel headerPanel;
    private JPanel paginationPanel;
    private List<Review> reviews; // ���� ������ ����Ʈ
    private int currentPage = 1;
    private JScrollPane scrollPane;

    public ReviewScreen() {
        setLayout(new BorderLayout());

        // ��� �г�
        headerPanel = createHeaderPanel(119, 4.29f); // �� ���� ���� ��� ���� ���� ������
        add(headerPanel, BorderLayout.NORTH);

        // ���� �г� (���� ����Ʈ)
        mainPanel = new JPanel(new BorderLayout());
        reviewListPanel = new JPanel();
        reviewListPanel.setLayout(new BoxLayout(reviewListPanel, BoxLayout.Y_AXIS));

        // �� scrollPane�� Ŭ���� �ʵ�� �ʱ�ȭ
        scrollPane = new JScrollPane(reviewListPanel);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER); // ���� ��ũ�� ��Ȱ��ȭ
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);


        // ����¡ �г�
        paginationPanel = new JPanel(new FlowLayout());
        add(paginationPanel, BorderLayout.SOUTH);

        // �׽�Ʈ�� ������ ����
        reviews = generateSampleReviews(119); // 119���� ���� ������ ����
        updateReviewList();
        setVisible(true);
    }

    private JPanel createHeaderPanel(int totalReviews, float avgRating) {
        JPanel header = new JPanel(new GridLayout(2, 1));
        header.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel totalReviewsLabel = new JLabel("��ü �ı��: " + totalReviews);
        totalReviewsLabel.setFont(new Font("�������, Arial", Font.BOLD, 14));

        DecimalFormat df = new DecimalFormat("#.##");
        JLabel avgRatingLabel = new JLabel("����� �� ���� �� " + df.format(avgRating) + " (��հ�)");
        avgRatingLabel.setFont(new Font("�������, Arial", Font.PLAIN, 14));

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

        // 1*5 �׸��� ���̾ƿ� �г� ����
        JPanel infoPanel = new JPanel(new GridLayout(1, 5, 0, 0)); // 1�� 4��, �� ���� 10px

        // 1�� �г�: ���� ������
        JPanel profilePanel = new JPanel();
        profilePanel.setBackground(Color.LIGHT_GRAY);
        profilePanel.setPreferredSize(new Dimension(40, 40)); // ũ�� ����
        JLabel profileLabel = new JLabel("??", SwingConstants.CENTER);
        profilePanel.add(profileLabel);

        // 2�� �г�: ������ �ۼ��� (���η� ��ġ)
        JPanel ratingAndDatePanel = new JPanel();
        ratingAndDatePanel.setLayout(new BoxLayout(ratingAndDatePanel, BoxLayout.Y_AXIS));
        ratingAndDatePanel.setAlignmentX(Component.LEFT_ALIGNMENT); // �г� ��ü�� ���� ����

        // ���� �߰�
        JPanel starPanel = createStarPanel(review.getRating());
        starPanel.setAlignmentX(Component.LEFT_ALIGNMENT); // ���� �гε� ���� ����
        ratingAndDatePanel.add(starPanel);

        // �ۼ��� �߰�
        JLabel dateLabel = new JLabel(new SimpleDateFormat("yyyy.MM.dd").format(review.getDate()));
        dateLabel.setAlignmentX(Component.LEFT_ALIGNMENT); // �ۼ��� ���� ����
        ratingAndDatePanel.add(Box.createVerticalStrut(5)); // ���� �߰�
        ratingAndDatePanel.add(dateLabel);


        // 3��, 4�� �г�: �� ����
        JPanel emptyPanel1 = new JPanel();
        JPanel emptyPanel2 = new JPanel();
        JPanel emptyPanel3 = new JPanel();

        // �г� �߰�
        infoPanel.add(profilePanel);       // 1�� �г�
        infoPanel.add(ratingAndDatePanel); // 2�� �г�
        infoPanel.add(emptyPanel1);        // 3�� �г�
        infoPanel.add(emptyPanel2);        // 4�� �г�
        infoPanel.add(emptyPanel3);        // 5�� �г�

        // �ı� ���� (4~5���� ���� Ȯ��)
        JTextArea contentArea = new JTextArea(review.getContent(), 4, 30); // 4�� 30���� ũ�� ����
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setEditable(false);

        reviewPanel.add(infoPanel, BorderLayout.NORTH); // ���ʿ� ������, ����, �ۼ���
        reviewPanel.add(contentArea, BorderLayout.CENTER); // �ı� ������ �߾ӿ� ��ġ

        return reviewPanel;
    }

    private JPanel createStarPanel(int rating) {
        JPanel starPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, -3)); // �� ������ 0���� ����
        for (int i = 0; i < 5; i++) {
            JLabel starLabel = new JLabel(i < rating ? "��" : "��");
            starLabel.setFont(new Font("���� ���, Arial", Font.PLAIN, 18)); // �� ũ�� ����
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
            sampleReviews.add(new Review("�ı� ���� ���� " + i, 3 + (i % 3), new Date()));
        }
        return sampleReviews;
    }

    public static void main(String[] args) {
        new ReviewScreen();
    }

    // ���� ������ Ŭ����
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



