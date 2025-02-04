public class Query {
    private String keyword;
    private boolean isStore;

    public Query(String keyword, boolean isStore) {
        this.keyword = keyword;
        this.isStore = isStore;
    }

    public String getKeyword() {
        return keyword;
    }

    public boolean isStore() {
        return isStore;
    }
}
