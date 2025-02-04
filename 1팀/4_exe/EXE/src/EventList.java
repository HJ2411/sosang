import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class EventList extends JPanel {
    private JPanel contentPanel;

    public EventList() {
       setLayout(new BorderLayout());
       //setBackground(Color.WHITE);

        // 구성 요소 초기화
        initializeComponents();
    }

    private void initializeComponents() {
        createContentPanel();  // 컨텐츠 패널 생성
        add(contentPanel, BorderLayout.CENTER);
    }

    private void createContentPanel() {
        // 컨텐츠 패널 설정
        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);

        // 가게 리스트 패널 생성
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(Color.WHITE);

        // 더미 데이터 생성 (DB 연동 시 삭제)
        List<Event> events = fetchEventsFromDB();

        // 이벤트 패널 생성 및 추가
        for (Event event : events) {
            listPanel.add(createEventPanel(event));
            listPanel.add(Box.createVerticalStrut(10));  // 패널 사이 간격
        }

        // contentPanel : scrollPane 설정 & 추가
        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBorder(null);

        contentPanel.add(scrollPane, BorderLayout.CENTER);
    }

    private List<Event> fetchEventsFromDB() {
        List<Event> events = new ArrayList<>();
        String query = "SELECT NoticeNo, Title, Contents, CreateDate, StoreNo FROM Notice";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("NoticeNo");
                String title = rs.getString("Title");
                String contents = rs.getString("Contents");
                String createDate = rs.getString("CreateDate");
                String StoreNo = rs.getString("StoreNo");

                events.add(new Event(id, title, contents, createDate, StoreNo));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return events;
    }

    private JPanel createEventPanel(Event event) {
        // 이벤트 패널 생성 (전체 패널)
        JPanel eventPanel = new JPanel(new BorderLayout());
        eventPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        eventPanel.setBackground(Color.WHITE);

        // 왼쪽 패널 (이미지)
        ImageIcon originalIcon = new ImageIcon(event.getImage());
        // 이미지 크기 조정 (너비: 60px, 높이: 60px)
        Image scaledImage = originalIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(scaledImage);

        JLabel imageLabel = new JLabel(resizedIcon);
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // 여백 설정
        eventPanel.add(imageLabel, BorderLayout.WEST);

        // 오른쪽 패널 (텍스트 정보)
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(Color.WHITE);

        // 상단 (제목과 시간)
        JPanel topPanel = new JPanel(new GridLayout(1, 2)); // 제목, 시간 나란히 배치
        topPanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel(event.getStoreName());
        titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        titleLabel.setHorizontalAlignment(SwingConstants.LEFT);

        JLabel hoursLabel = new JLabel(event.getHours());
        hoursLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
        hoursLabel.setForeground(Color.GRAY);
        hoursLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        topPanel.add(titleLabel);
        topPanel.add(hoursLabel);

        // 중앙 (내용)
        JLabel contentLabel = new JLabel("<html>" + event.getContent() + "</html>");
        contentLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
        contentLabel.setForeground(Color.DARK_GRAY);
        contentLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 10)); // 텍스트 여백

        // 오른쪽 패널에 상단(제목, 시간)과 중앙(내용) 추가
        rightPanel.add(topPanel, BorderLayout.NORTH); // 제목 및 시간
        rightPanel.add(contentLabel, BorderLayout.CENTER); // 내용

        // 최종적으로 eventPanel에 왼쪽(이미지)와 오른쪽(텍스트 패널) 추가
        eventPanel.add(imageLabel, BorderLayout.WEST); // 왼쪽 이미지
        eventPanel.add(rightPanel, BorderLayout.CENTER); // 오른쪽 텍스트 정보

        return eventPanel;
    }
    /*
    public static void main(String[] args) {
        // Look and Feel 설정
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 프레임 실행
        SwingUtilities.invokeLater(() -> {
            EventList frame = new EventList();
            frame.setVisible(true);
        });
    }*/
}

