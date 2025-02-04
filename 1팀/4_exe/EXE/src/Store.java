import javax.swing.*;
import java.awt.*;

public class Store { // ���ƿ� ���¸� ��Ÿ���� �ʵ�
    private int storeNo;
    private String storeName;
    private String openHours;
    private String location;
    private int industry;
    private String ImagePath;
    private int sellerNo;

    // ��ϵ� �ı� ��
    private int totalReviews; // ��� ���� ��

    public Store(int storeNo, String storeName, String openHours, String location, int industry, String image, int sellerNo) {
        this.storeNo = storeNo;
        this.storeName = storeName;
        this.openHours = openHours;
        this.location = location;
        this.industry = industry;
        this.ImagePath = "./image/store"+ storeNo+ ".jpg";
        this.sellerNo = sellerNo;
    }

    // Getter�� Setter
    public int getStoreNo() { return storeNo; }
    public void setStoreNo(int storeNo) { this.storeNo = storeNo; }
    public String getStoreName() { return storeName; }
    public void setStoreName(String storeName) { this.storeName = storeName; }
    public String getOpenHours() { return openHours; }
    public void setOpenHours(String openHours) { this.openHours = openHours; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public int getIndustry() { return industry; }
    public void setIndustry(int industry) { this.industry = industry; }
    public String getImagePath() { return ImagePath; }
    public void setImagePath(String imagePath) { this.ImagePath = imagePath; }

    public ImageIcon getImage() {
        if (ImagePath == null || ImagePath.isEmpty()) {
            // �⺻ �̹��� ��ȯ
            return new ImageIcon("./image/default.png"); // �⺻ �̹��� ��� ����
        }
        try {/*
            return new ImageIcon(ImagePath);*/
            System.out.println("Loading image from: " + ImagePath);
            ImageIcon icon = new ImageIcon(ImagePath);
            Image img = icon.getImage().getScaledInstance(340, 200, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } catch (Exception e) {
            System.err.println("Failed to load image: " + e.getMessage());
            return new ImageIcon("./image/default.png"); // �̹��� �ε� ���� �� �⺻ �̹��� ��ȯ
        }
    }
    public void setImage(String image) { this.ImagePath = image; }
    public int getSellerNo() { return sellerNo; }
    public void setSellerNo(int sellerNo) { this.sellerNo = sellerNo; }

}
