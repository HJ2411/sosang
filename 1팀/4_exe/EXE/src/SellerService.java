public class SellerService {
    private SellerDAO sellerDAO;
    private StoreDAO storeDAO;

    public SellerService() {
        this.sellerDAO = new SellerDAO();
        this.storeDAO = new StoreDAO();
    }

    public boolean registerSellerAndStore(int sellerNo, String memberId, String storeName, String storePhone, String location, int industry, String openHours) {
        // �Ǹ��� ���� ����
        Seller seller = new Seller(sellerNo, storePhone, memberId);
        boolean sellerSaved = sellerDAO.insertSeller(seller);

        if (sellerSaved) {
            // ���� ���� ����
            Store store = new Store(0, storeName, openHours, location, industry, null, sellerNo);
            return storeDAO.insertStore(store);
        }
        return false;
    }
}