import java.util.Date;

public class Notice {
    private int noticeNo;
    private String title;
    private String contents;
    private String nImage;
    private Date createDate;
    private int sellerNo;

    public Notice(int noticeNo, String title, String contents, String nImage, Date createDate, int sellerNo) {
        this.noticeNo = noticeNo;
        this.title = title;
        this.contents = contents;
        this.nImage = nImage;
        this.createDate = createDate;
        this.sellerNo = sellerNo;
    }

    public int getNoticeNo() {
        return noticeNo;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public String getNImage() {
        return nImage;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public int getSellerNo() {
        return sellerNo;
    }
}