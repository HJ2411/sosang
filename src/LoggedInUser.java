public class LoggedInUser {
    private static String memberId;
    private static String nick;
    private static String email;

    public static String getMemberId() {
        return memberId;
    }

    public static void setMemberId(String memberId) {
        LoggedInUser.memberId = memberId;
    }

    public static String getNick() {
        return nick;
    }

    public static void setNick(String nick) {
        LoggedInUser.nick = nick;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        LoggedInUser.email = email;
    }

    public static void clear() {
        memberId = null;
        nick = null;
        email = null;
    }
}