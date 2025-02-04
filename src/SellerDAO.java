import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SellerDAO {
    public boolean insertSeller(Seller seller) {
        String sql = "insert into Seller (SellerNo, StorePhone, MemberID) VALUES (?, ?, ?)";
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, seller.getSellerNo());
            pstmt.setString(2, seller.getStorePhone());
            pstmt.setString(3, seller.getMemberId());
            return pstmt.executeUpdate() > 0;
        }catch(SQLException se){
            System.out.println(se);
        }
        return false;
    }

    // 특정 멤버 ID가 판매자인지 확인하는 메서드
    public boolean isSeller(String memberId) {
        String query = "SELECT COUNT(*) FROM store s JOIN seller se ON s.sellerno = se.sellerno WHERE se.memberid = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, memberId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Seller getSellerByMemberId(String memberId) {
        String query = "SELECT * FROM Seller WHERE MemberID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, memberId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Seller(
                        rs.getInt("SellerNo"),
                        rs.getString("StorePhone"),
                        rs.getString("MemberID")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

        public String getStorePhoneBySellerNo(int sellerNo) {
            String sql = "SELECT StorePhone FROM Seller WHERE SellerNo = ?";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, sellerNo);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    String phone = rs.getString("StorePhone");
                    //return rs.getString("StorePhone");
                    System.out.println("StorePhone: " + phone);
                    return phone;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
}
