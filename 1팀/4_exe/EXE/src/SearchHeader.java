import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SearchHeader extends JPanel {
    private JTextField searchField;
    public JPanel suggestionPanel;
    private MainFrame mainFrame;
    private JButton backButton;
    private JToggleButton itemButton;
    private JButton searchButton;

    public SearchHeader(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());

        // 상단 검색 영역
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchField = new JTextField();

        searchPanel.add(searchField, BorderLayout.CENTER);

        // 돋보기 버튼 추가
        searchButton = new JButton("\uD83D\uDD0E");
        searchButton.addActionListener(e -> {
            String query = searchField.getText();
            if (!query.isEmpty()) {
                mainFrame.updateSearchResults(query, false);
            }
        });
        searchPanel.add(searchButton, BorderLayout.EAST);

        //0124 Enter event
        searchField.addActionListener( e -> {
            String query = searchField.getText();
            if(!query.isEmpty()){
                mainFrame.updateSearchResults(query, false);
            }
        });

        //0124 뒤로가기 버튼
        backButton = new JButton("\u2190");
        backButton.addActionListener( e-> mainFrame.goBack() );

        searchPanel.add(backButton, BorderLayout.WEST);

        // 하단 연관 검색어 영역
        suggestionPanel = new JPanel();
        suggestionPanel.setLayout(new BoxLayout(suggestionPanel, BoxLayout.Y_AXIS));
        suggestionPanel.setBackground(Color.WHITE);

        // 전체 패널에 추가
        add(searchPanel, BorderLayout.NORTH);
        add(suggestionPanel, BorderLayout.CENTER);

        // 실시간 입력 감지
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateSuggestions(searchField.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateSuggestions(searchField.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateSuggestions(searchField.getText());
            }
        });
    }

    private void updateSuggestions(String query) {
        suggestionPanel.removeAll();

        if (query.isEmpty()) {
            suggestionPanel.revalidate();
            suggestionPanel.repaint();
            return;
        }

        List<String> suggestions = getSuggestionsFromDB(query);

        for (String suggestion : suggestions) {
            JButton suggestionButton = new JButton(suggestion);
            suggestionButton.setAlignmentX(Component.LEFT_ALIGNMENT);
            suggestionButton.setFocusPainted(false);
            suggestionButton.setContentAreaFilled(false);
            suggestionButton.setBorderPainted(false);

            suggestionButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    searchField.setText(suggestion);

                    //0124 추가 -> 선택결과 숨기기
                    suggestionPanel.removeAll();
                    suggestionPanel.revalidate();
                    suggestionPanel.repaint();

                    if (isStore(suggestion)) {
                        Store store = fetchStoreDetails(suggestion);
                        if (store != null) {
                            openStoreInfo(store);
                        }
                    } else {
                        mainFrame.updateSearchResults(suggestion, false);
                    }
                }
            });

            suggestionPanel.add(suggestionButton);
        }

        suggestionPanel.revalidate();
        suggestionPanel.repaint();
    }

    private boolean isStore(String query) {
        String sql = "SELECT COUNT(*) FROM Store WHERE StoreName = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, query);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private Store fetchStoreDetails(String storeName) {
        String sql = "SELECT * FROM Store WHERE StoreName = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, storeName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Store(
                            rs.getInt("StoreNo"),
                            rs.getString("StoreName"),
                            rs.getString("OpenHours"),
                            rs.getString("LOC"),
                            rs.getInt("IndustryNo"),
                            rs.getString("StoreImage"),
                            rs.getInt("SellerNo")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<String> getSuggestionsFromDB(String query) {
        List<String> suggestions = new ArrayList<>();
        String sql = "SELECT StoreName FROM Store WHERE StoreName LIKE ? " +
                "UNION " +
                "SELECT ItemName FROM Item WHERE ItemName LIKE ? " +
                "LIMIT 10";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + query + "%");
            stmt.setString(2, "%" + query + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    suggestions.add(rs.getString(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return suggestions;
    }

    private void openStoreInfo(Store store) {
        //0131 호출방식 변경(JPanel)
        mainFrame.navigateToPage(new StoreInfoPanel(store));

        /*SwingUtilities.invokeLater(() -> {
            StoreInfoPanel storeInfoFrame = new StoreInfoPanel(store);
            storeInfoFrame.setVisible(true);
        });*/
    }
}