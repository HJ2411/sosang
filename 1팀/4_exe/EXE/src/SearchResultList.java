import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SearchResultList extends JPanel {
    private JPanel contentPanel;

    public SearchResultList() {
        setLayout(new BorderLayout());
        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        add(new JScrollPane(contentPanel), BorderLayout.CENTER);
    }

    public void updateResults(String query, boolean isStore) {
        contentPanel.removeAll(); // 기존 결과 제거

        if (isStore) {
            List<Store> stores = fetchStoresFromDB(query);
            for (Store store : stores) {
                System.out.println("Adding StoreInfoPanel for: " + store.getStoreName());
                StoreInfoPanel storeInfoPanel = new StoreInfoPanel(store); // StoreInfoPanel 생성
                contentPanel.add(storeInfoPanel);
                contentPanel.add(Box.createVerticalStrut(10));
            }
        } else {
            List<Product> products = fetchProductsFromDB(query);
            for (Product product : products) {
                contentPanel.add(createProductPanel(product));
                contentPanel.add(Box.createVerticalStrut(10));
            }
        }

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public class DebugLogger {
        public static void log(String message) {
            try (FileWriter writer = new FileWriter("C:/logs/log.txt", true)) { // 원하는 경로 지정
                writer.write(message + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private List<Store> fetchStoresFromDB(String query) {
        List<Store> stores = new ArrayList<>();
        String sql = "SELECT * FROM Store WHERE StoreName LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + query + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Store store = new Store(
                            rs.getInt("StoreNo"),        // storeNo
                            rs.getString("StoreName"),   // storeName
                            rs.getString("OpenHours"),   // openHours
                            rs.getString("LOC"),         // location
                            rs.getInt("IndustryNo"),     // industry
                            rs.getString("StoreImage"),  // ImagePath
                            rs.getInt("SellerNo")        // sellerNo
                    );
                    stores.add(store);
                    DebugLogger.log("Loaded store:" + store.getStoreName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        DebugLogger.log("Total stores loaded: " + stores.size());
        return stores;
    }

    private List<Product> fetchProductsFromDB(String query) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM Item WHERE ItemName LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + query + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    products.add(new Product(
                            rs.getInt("ItemNo"),
                            rs.getString("ItemName"),
                            rs.getString("Amount"),
                            rs.getString("StoreNo"),
                            rs.getString("ItemImage")
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    private JPanel createProductPanel(Product product) {
        JPanel productPanel = new JPanel(new BorderLayout());
        productPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        productPanel.setBackground(Color.WHITE);

        JLabel nameLabel = new JLabel(product.getProductName());
        JLabel amountLabel = new JLabel("가격: " + product.getAmount());

        productPanel.add(nameLabel, BorderLayout.NORTH);
        productPanel.add(amountLabel, BorderLayout.CENTER);

        return productPanel;
    }
}