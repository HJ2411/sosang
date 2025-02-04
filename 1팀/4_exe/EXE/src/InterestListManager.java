import java.sql.*;

public class InterestListManager {
    private Connection connection;

    // 관심 가게 추가
    public boolean addInterestStore(String memberId, int storeNo) {
        String query = "INSERT INTO InterestList (memberId, storeNo) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            conn.setAutoCommit(false);
            stmt.setString(1, memberId);
            stmt.setInt(2, storeNo);
            stmt.executeUpdate();
            conn.commit();
            System.out.println("가게 " + storeNo + "가 관심 목록에 추가되었습니다.");
            return true; //0131 반환값 추가
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 관심 가게 제거
    public boolean removeInterestStore(String memberId, int storeNo) {
        String query = "DELETE FROM InterestList WHERE memberId = ? AND storeNo = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            conn.setAutoCommit(false);
            stmt.setString(1, memberId);
            stmt.setInt(2, storeNo);
            stmt.executeUpdate();
            conn.commit();
            System.out.println("가게 " + storeNo + "가 관심 목록에서 제거되었습니다.");
            return true; //0131 반환값 추가
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isStoreLiked(String memberId, int storeNo){
        String sql = "select count(*) from InterestList where memberId = ? and storeNo =?";
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, memberId);
            stmt.setInt(2, storeNo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }catch(SQLException se){
            System.out.println(se);
        }
        return  false;
    }
}
