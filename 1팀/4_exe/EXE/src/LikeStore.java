import javax.swing.*;

public class LikeStore {
    // ���ƿ� ���¸� ��Ÿ���� �ʵ�
    private String memberID;
    private int storeNo;

    // ������
    public LikeStore(String memberID, int storeNo) {
        this.memberID = memberID;
        this.storeNo = storeNo;
    }

    // Getter�� Setter
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
