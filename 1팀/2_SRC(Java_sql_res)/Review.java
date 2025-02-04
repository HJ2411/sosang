public class Review {
    private int reviewNo; // Primary key, auto-increment
    private java.util.Date createDate;
    private String memberID;
    private int storeNo;
    private String contents;
    private int rating; // 1~5 정수값
    private String reviewImage;

    // 생성자
    public Review(int reviewNo, String memberID, int storeNo, java.util.Date createDate, String contents, int rating, String reviewImage) {
        this.reviewNo = reviewNo;
        this.memberID = memberID;
        this.storeNo = storeNo;
        this.createDate = createDate;
        this.contents = contents;
        this.rating = rating;
        this.reviewImage = reviewImage;
    }

    // Getter와 Setter
    public int getReviewNo() {
        return reviewNo;
    }

    public void setReviewNo(int reviewNo) {
        this.reviewNo = reviewNo;
    }

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

    public java.util.Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(java.util.Date createDate) {
        this.createDate = createDate;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReviewImage() {
        return reviewImage;
    }

    public void setReviewImage(String reviewImage) {
        this.reviewImage = reviewImage;
    }
}
