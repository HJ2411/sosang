public class User {
    private String memberId;
    private String pwd;
    private String name;
    private String nick;
    private java.util.Date birth;
    private String email;


    public User(String memberId, String pwd, String name, String nick, java.util.Date birth, String email) {
        this.memberId = memberId;
        this.pwd = pwd;
        this.name = name;
        this.nick = nick;
        this.birth = birth;
        this.email = email;
    }


    public String getMemberId() { return memberId; }
    public void setMemberId(String memberId) { this.memberId = memberId; }
    public String getPwd() { return pwd; }
    public void setPwd(String pwd) { this.pwd = pwd; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getNick() { return nick; }
    public void setNick(String nick) { this.nick = nick; }
    public java.util.Date getBirth() { return birth; }
    public void setBirth(java.util.Date birth) { this.birth = birth; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
