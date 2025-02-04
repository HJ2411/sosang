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

        // ���� ��� �ʱ�ȭ
        initializeComponents();
    }

    private void initializeComponents() {
        createContentPanel();  // ������ �г� ����
        add(contentPanel, BorderLayout.CENTER);
    }

    private void createContentPanel() {
        // ������ �г� ����
        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);

        // ���� ����Ʈ �г� ����
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(Color.WHITE);

        // ���� ������ ���� (DB ���� �� ����)
        List<Event> events = fetchEventsFromDB();

        // �̺�Ʈ �г� ���� �� �߰�
        for (Event event : events) {
            listPanel.add(createEventPanel(event));
            listPanel.add(Box.createVerticalStrut(10));  // �г� ���� ����
        }

        // contentPanel : scrollPane ���� & �߰�
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
        // �̺�Ʈ �г� ���� (��ü �г�)
        JPanel eventPanel = new JPanel(new BorderLayout());
        eventPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        eventPanel.setBackground(Color.WHITE);

        // ���� �г� (�̹���)
        ImageIcon originalIcon = new ImageIcon(event.getImage());
        // �̹��� ũ�� ���� (�ʺ�: 60px, ����: 60px)
        Image scaledImage = originalIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(scaledImage);

        JLabel imageLabel = new JLabel(resizedIcon);
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // ���� ����
        eventPanel.add(imageLabel, BorderLayout.WEST);

        // ������ �г� (�ؽ�Ʈ ����)
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(Color.WHITE);

        // ��� (����� �ð�)
        JPanel topPanel = new JPanel(new GridLayout(1, 2)); // ����, �ð� ������ ��ġ
        topPanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel(event.getStoreName());
        titleLabel.setFont(new Font("���� ���", Font.BOLD, 16));
        titleLabel.setHorizontalAlignment(SwingConstants.LEFT);

        JLabel hoursLabel = new JLabel(event.getHours());
        hoursLabel.setFont(new Font("���� ���", Font.PLAIN, 12));
        hoursLabel.setForeground(Color.GRAY);
        hoursLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        topPanel.add(titleLabel);
        topPanel.add(hoursLabel);

        // �߾� (����)
        JLabel contentLabel = new JLabel("<html>" + event.getContent() + "</html>");
        contentLabel.setFont(new Font("���� ���", Font.PLAIN, 12));
        contentLabel.setForeground(Color.DARK_GRAY);
        contentLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 10)); // �ؽ�Ʈ ����

        // ������ �гο� ���(����, �ð�)�� �߾�(����) �߰�
        rightPanel.add(topPanel, BorderLayout.NORTH); // ���� �� �ð�
        rightPanel.add(contentLabel, BorderLayout.CENTER); // ����

        // ���������� eventPanel�� ����(�̹���)�� ������(�ؽ�Ʈ �г�) �߰�
        eventPanel.add(imageLabel, BorderLayout.WEST); // ���� �̹���
        eventPanel.add(rightPanel, BorderLayout.CENTER); // ������ �ؽ�Ʈ ����

        return eventPanel;
    }
    /*
    public static void main(String[] args) {
        // Look and Feel ����
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // ������ ����
        SwingUtilities.invokeLater(() -> {
            EventList frame = new EventList();
            frame.setVisible(true);
        });
    }*/
}

