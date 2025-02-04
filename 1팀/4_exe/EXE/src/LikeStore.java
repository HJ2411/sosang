import javax.swing.*;

public class LikeStore {
    // 좋아요 상태를 나타내는 필드
    private String memberID;
    private int storeNo;

    // 생성자
    public LikeStore(String memberID, int storeNo) {
        this.memberID = memberID;
        this.storeNo = storeNo;
    }

    // Getter와 Setter
    public String getMemberID() {
        return memberID;
    }

    public void setMemberID(String memberID) {
        this.memberID = memberID;
    }

    public int getStoreNo() {
        return storeNo;
    }

    public void setStoreNo(int storeNo) {
        this.storeNo = storeNo;
    }
}
