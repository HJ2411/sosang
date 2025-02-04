import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StoreDAO {

    // 상점 정보를 데이터베이스에 삽입하는 메서드
    public boolean insertStore(Store store) {
        String query = "INSERT INTO Store (storeNo, storeName, openHours, LOC, StoreImage,sellerNo,industryNo) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, store.getStoreNo());
            pstmt.setString(2, store.getStoreName());
            pstmt.setString(3, store.getOpenHours());
            pstmt.setString(4, store.getLocation());
            pstmt.setString(5, store.getImage().toString());
            pstmt.setInt(6, store.getSellerNo());
            pstmt.setInt(7, store.getIndustry());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 상점 ID를 기준으로 특정 상점 정보를 가져오는 메서드
    public Store getStoreById(int storeNo) {
        String query = "SELECT * FROM Store WHERE storeNo = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, storeNo);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Store(
                        rs.getInt("storeNo"),
                        rs.getString("storeName"),
                        rs.getString("openHours"),
                        rs.getString("loc"),
                        rs.getInt("industryNo"),
                        null, // 이미지 필드는 처리 제외
                        rs.getInt("sellerNo")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 모든 상점 정보를 가져오는 메서드
    public List<Store> getAllStores() {
        List<Store> stores = new ArrayList<>();
        String query = "SELECT * FROM Store";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                stores.add(new Store(
                        rs.getInt("storeNo"),
                        rs.getString("storeName"),
                        rs.getString("openHours"),
                        rs.getString("location"),
                        rs.getInt("industryNo"),
                        null, // 이미지 필드는 처리 제외
                        rs.getInt("sellerNo")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stores;
    }

    // 상점 정보를 업데이트하는 메서드
    public boolean updateStore(Store store) {
        String query = "UPDATE Store SET storeName = ?, openHours = ?, location = ?, industry = ?, intro = ?, sellerNo = ?, address = ?, phone = ? " +
                "WHERE storeNo = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, store.getStoreName());
            pstmt.setString(2, store.getOpenHours());
            pstmt.setString(3, store.getLocation());
            pstmt.setInt(4, store.getIndustry());
            pstmt.setInt(6, store.getSellerNo());
            pstmt.setInt(9, store.getStoreNo());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 상점 정보를 삭제하는 메서드
    public boolean deleteStore(int storeNo) {
        String query = "DELETE FROM Store WHERE storeNo = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, storeNo);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
