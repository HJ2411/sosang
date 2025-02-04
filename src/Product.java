public class Product {
    private int storeNo;
    private String storeName;
    private String productName;
    private String amount;
    private String ItemImg;

    // 생성자
    public Product(int storeNo, String storeName, String productName, String amount, String ItemImg) {
        this.storeNo = storeNo;
        this.storeName = storeName;
        this.productName = productName;
        this.amount = amount;
        this.ItemImg = ItemImg;
    }

    // Getters and Setters
    public int getStoreNo() { return storeNo; }
    public void setStoreNo(int storeNo) { this.storeNo = storeNo; }
    public String getStoreName() { return storeName; }
    public void setStoreName(String storeName) { this.storeName = storeName; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public String getAmount() { return amount; }
    public void setAmount(String Amount) { this.amount = amount; }
    public String getItemImg() { return ItemImg; }
    public void setItemImg(String ItemImg) { this.ItemImg = ItemImg; }
}
