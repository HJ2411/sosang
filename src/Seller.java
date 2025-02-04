public class Seller {
    private int sellerNo;
    private String storePhone;
    private String memberId;

    public Seller(int sellerNo, String storePhone, String memberId) {
        this.sellerNo = sellerNo;
        this.storePhone = storePhone;
        this.memberId = memberId;
    }

    public int getSellerNo() { return sellerNo; }
    public String getStorePhone() { return storePhone; }
    public String getMemberId() { return memberId; }
}
