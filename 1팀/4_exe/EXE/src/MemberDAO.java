import javax.swing.*;
import java.sql.*;

public class MemberDAO {
    public User getUserById(String memberId) {
        String query = "SELECT * FROM Member WHERE MemberID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, memberId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                User user = new User(
                        rs.getString("MemberID"),
                        rs.getString("Pwd"),
                        rs.getString("Name"),
                        rs.getString("Nick"),
                        rs.getDate("Birth"),
                        rs.getString("Email")
                );
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //��й�ȣ ����
    public boolean updatePWD(String memberId, String password) {
        // SQL ���� �ۼ��Ͽ� �����ͺ��̽� ������Ʈ
        String sql = "UPDATE Member SET Pwd=? WHERE MemberID=?";

        if(!(password.length()>0 && password.length()<14))
            return false;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, password); // password�� ��ȣȭ�Ͽ� �����ؾ� ��
            pstmt.setString(2, memberId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    //�г��� ����
    public boolean updateNick(String memberId, String nick) {
        // SQL ���� �ۼ��Ͽ� �����ͺ��̽� ������Ʈ
        String sql = "UPDATE Member SET Nick=? WHERE MemberID=?";
        if(!(nick.getBytes().length>0&&nick.getBytes().length<14))
            return false;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nick);
            pstmt.setString(2, memberId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    //�̸��� ����
    public boolean updateEmail(String memberId, String Email) {
        // SQL ���� �ۼ��Ͽ� �����ͺ��̽� ������Ʈ
        String sql = "UPDATE Member SET Email=? WHERE MemberID=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, Email);
            pstmt.setString(2, memberId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    //�ߺ�üũ
    boolean checkDuplicate(String target,String newValue){
        String sql = "select * from Member where "+target+"=" + newValue;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            conn = DatabaseConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            return rs.first();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (SQLException e) {}
        }
    }
}
