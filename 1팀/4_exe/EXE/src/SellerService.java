public class SellerService {
    private SellerDAO sellerDAO;
    private StoreDAO storeDAO;

    public SellerService() {
        this.sellerDAO = new SellerDAO();
        this.storeDAO = new StoreDAO();
    }

    public boolean registerSellerAndStore(int sellerNo, String memberId, String storeName, String storePhone, String location, int industry, String openHours) {
        // 판매자 정보 생성
        Seller seller = new Seller(sellerNo, storePhone, memberId);
        boolean sellerSaved = sellerDAO.insertSeller(seller);

        if (sellerSaved) {
            // 가게 정보 생성
            Store store = new Store(0, storeName, openHours, location, industry, null, sellerNo);
            return storeDAO.insertStore(store);
        }
        return false;
    }
}