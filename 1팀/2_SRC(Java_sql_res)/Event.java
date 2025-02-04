public class Event {
    private int eventNo;
    private String storeName;
    private String content;
    private String hours;
    private String ImagePath;

    // 수정된 생성자
    public Event(int eventNo, String storeName, String content, String hours, String image) {
        this.eventNo = eventNo;
        this.storeName = storeName;
        this.content = content;
        this.hours = hours;
        this.ImagePath = ImagePath;
    }

    // Getters and Setters
    public int getEventNo() { return eventNo; }
    public void setEventNo(int eventNo) { this.eventNo = eventNo; }
    public String getStoreName() { return storeName; }
    public void setStoreName(String storeName) { this.storeName = storeName; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getHours() { return hours; }
    public void setHours(String hours) { this.hours = hours; }
    public String getImage() { return ImagePath; }
    public void setImage(String imagePath) { this.ImagePath = ImagePath; }
}
