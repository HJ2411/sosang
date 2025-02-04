import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ItemWrite extends JDialog {
    private int itemNo = 0;
    private String imgRoot = "NoImg.jpg";
    private Store store;
    private JTextField inputText[] = new JTextField[2];
    private JLabel iLabel[] = new JLabel[2];
    private JLabel img;
    private String itemStr[] = {"이름", "가격"};
    private ImageSaver imgsaver = new ImageSaver();

    private ImageIcon icon;
    // 대화상자 초기화
    ItemWrite(Store store) {
        this.store = store;

        setTitle("아이템 정보 입력");
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // 입력 필드와 라벨 생성
        for (int i = 0; i < inputText.length; i++) {
            JPanel jp = new JPanel();

            inputText[i] = new JTextField(20);
            iLabel[i] = new JLabel(itemStr[i]);
            jp.add(iLabel[i]);
            jp.add(inputText[i]);

            add(jp);
        }
        JButton yes = new JButton("확인");
        yes.addActionListener(e->{
            saveItem();
        });
        // 버튼 패널 추가
        JPanel buttonPanel = new JPanel();
        yes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // "확인" 버튼 클릭 시 아이템 정보 저장
                String name = inputText[0].getText();
                String price = inputText[1].getText();
                // 저장된 아이템 정보를 처리하는 로직을 여기에 추가할 수 있습니다.
                JOptionPane.showMessageDialog(ItemWrite.this, "아이템이 저장되었습니다!");
                dispose();  // 대화상자 닫기
            }
        });
        JPanel jp = new JPanel();
        img = new JLabel();
        img.setIcon(imgsaver.loadImage("./image/NoImg.jpg"));
        jp.add(img);
        RButton imgch = new RButton("변경");
        imgch.addActionListener(e -> {
            if(itemNo ==0) {
                String imgName = imgsaver.save("item", "item");
                Pattern pattern = Pattern.compile("[a-zA-Z](\\d+)[.][a-zA-Z]");
                Matcher matcher = pattern.matcher("item" + imgName);
                if (matcher.find()) {
                    itemNo = Integer.parseInt(matcher.group(1));
                    img.setIcon(imgsaver.loadImage("./image/item" + imgName));
                }
                imgRoot = "item" + imgName;
            }else{
                imgsaver.saveUnplus(imgRoot,"item");
                img.setIcon(imgsaver.loadImage("./image/" + imgRoot));
            }
        });
        jp.add(imgch);
        add(jp);
        buttonPanel.add(yes);

        add(buttonPanel);

        // 대화상자 크기와 위치 설정
        setSize(300, 200);
        setLocationRelativeTo(null);  // 화면 중앙에 대화상자 표시
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setModal(true);  // 모달로 설정하여 다른 창과 상호작용 불가
    }
    ItemWrite(Store store,int itemNo) {
        this.store = store;
        this.itemNo = itemNo;
        setTitle("아이템 정보 입력");
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // 입력 필드와 라벨 생성
        for (int i = 0; i < inputText.length; i++) {
            JPanel jp = new JPanel();

            inputText[i] = new JTextField(20);
            iLabel[i] = new JLabel(itemStr[i]);
            jp.add(iLabel[i]);
            jp.add(inputText[i]);

            add(jp);
        }
        JButton yes = new JButton("수정");
        yes.addActionListener(e->{
            updateItem();
        });
        // 버튼 패널 추가
        JPanel buttonPanel = new JPanel();
        yes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // "확인" 버튼 클릭 시 아이템 정보 저장
                String name = inputText[0].getText();
                String price = inputText[1].getText();
                // 저장된 아이템 정보를 처리하는 로직을 여기에 추가할 수 있습니다.
                JOptionPane.showMessageDialog(ItemWrite.this, "아이템이 저장되었습니다!");
                dispose();  // 대화상자 닫기
            }
        });
        JPanel jp = new JPanel();
        img = new JLabel();
        img.setIcon(imgsaver.loadImage("./image/NoImg.jpg"));
        jp.add(img);
        RButton imgch = new RButton("변경");
        imgch.addActionListener(e -> {
            imgsaver.saveUnplus(imgRoot,"item");
            img.setIcon(imgsaver.loadImage("./image/" + imgRoot));
        });
        jp.add(imgch);
        add(jp);
        buttonPanel.add(yes);

        add(buttonPanel);
        loadItem(itemNo);
        // 대화상자 크기와 위치 설정
        setSize(300, 200);
        setLocationRelativeTo(null);  // 화면 중앙에 대화상자 표시
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setModal(true);  // 모달로 설정하여 다른 창과 상호작용 불가
    }
    void loadItem(int itemNo){
        String sql = "select itemName,Amount,ItemImage from item where itemNo = "+itemNo;
        Connection con;
        Statement stmt;
        ResultSet rs;
        try{
            con = DatabaseConnection.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()){
                inputText[0].setText(rs.getString(1));
                inputText[1].setText(String.valueOf(rs.getInt(2)));
                imgRoot = rs.getString(3);
                img = new JLabel(imgsaver.loadImage("./image/"+imgRoot));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }try{
            rs.close();
            stmt.close();
            con.close();
        }catch (SQLException se){}
    }

    private void saveItem(){
        String sql = "INSERT INTO Item (ItemName, Amount, StoreNo, ItemImage) VALUES ('"+inputText[0].getText()+"',"+inputText[1].getText()+","+store.getStoreNo()+",'"+imgRoot+"')";

        Connection con = null;
        Statement stmt = null;
        try{
            con = DatabaseConnection.getConnection();
            stmt = con.createStatement();
            int rowsAffected = stmt.executeUpdate(sql);


        } catch (SQLException e) {

        }finally {
            try {
                stmt.close();
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void updateItem(){
        String sql = "Update Item Set ItemName ='"+inputText[0].getText()+"', Amount="+inputText[1].getText()
                +", StoreNo="+store.getStoreNo()+", ItemImage='"+imgRoot+"' where ItemNo="+itemNo;

        Connection con = null;
        Statement stmt = null;
        try{
            con = DatabaseConnection.getConnection();
            stmt = con.createStatement();
            int rowsAffected = stmt.executeUpdate(sql);
        } catch (SQLException e) {

        }finally {
            try {
                stmt.close();
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static void main(String[] args) {
        // 샘플 가게 객체 생성
        Store store = new Store(
                1,
                "가게 이름",
                "10:00-22:00",
                "서울특별시 강남구 테헤란로 123길 45",
                1,
                "store1.png",
                10
        );
        // 대화상자 생성 후 표시
        ItemWrite iw = new ItemWrite(store,1);
        iw.setVisible(true);
    }
}