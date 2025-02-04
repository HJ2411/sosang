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
    private String itemStr[] = {"�̸�", "����"};
    private ImageSaver imgsaver = new ImageSaver();

    private ImageIcon icon;
    // ��ȭ���� �ʱ�ȭ
    ItemWrite(Store store) {
        this.store = store;

        setTitle("������ ���� �Է�");
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // �Է� �ʵ�� �� ����
        for (int i = 0; i < inputText.length; i++) {
            JPanel jp = new JPanel();

            inputText[i] = new JTextField(20);
            iLabel[i] = new JLabel(itemStr[i]);
            jp.add(iLabel[i]);
            jp.add(inputText[i]);

            add(jp);
        }
        JButton yes = new JButton("Ȯ��");
        yes.addActionListener(e->{
            saveItem();
        });
        // ��ư �г� �߰�
        JPanel buttonPanel = new JPanel();
        yes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // "Ȯ��" ��ư Ŭ�� �� ������ ���� ����
                String name = inputText[0].getText();
                String price = inputText[1].getText();
                // ����� ������ ������ ó���ϴ� ������ ���⿡ �߰��� �� �ֽ��ϴ�.
                JOptionPane.showMessageDialog(ItemWrite.this, "�������� ����Ǿ����ϴ�!");
                dispose();  // ��ȭ���� �ݱ�
            }
        });
        JPanel jp = new JPanel();
        img = new JLabel();
        img.setIcon(imgsaver.loadImage("./image/NoImg.jpg"));
        jp.add(img);
        RButton imgch = new RButton("����");
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

        // ��ȭ���� ũ��� ��ġ ����
        setSize(300, 200);
        setLocationRelativeTo(null);  // ȭ�� �߾ӿ� ��ȭ���� ǥ��
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setModal(true);  // ��޷� �����Ͽ� �ٸ� â�� ��ȣ�ۿ� �Ұ�
    }
    ItemWrite(Store store,int itemNo) {
        this.store = store;
        this.itemNo = itemNo;
        setTitle("������ ���� �Է�");
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // �Է� �ʵ�� �� ����
        for (int i = 0; i < inputText.length; i++) {
            JPanel jp = new JPanel();

            inputText[i] = new JTextField(20);
            iLabel[i] = new JLabel(itemStr[i]);
            jp.add(iLabel[i]);
            jp.add(inputText[i]);

            add(jp);
        }
        JButton yes = new JButton("����");
        yes.addActionListener(e->{
            updateItem();
        });
        // ��ư �г� �߰�
        JPanel buttonPanel = new JPanel();
        yes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // "Ȯ��" ��ư Ŭ�� �� ������ ���� ����
                String name = inputText[0].getText();
                String price = inputText[1].getText();
                // ����� ������ ������ ó���ϴ� ������ ���⿡ �߰��� �� �ֽ��ϴ�.
                JOptionPane.showMessageDialog(ItemWrite.this, "�������� ����Ǿ����ϴ�!");
                dispose();  // ��ȭ���� �ݱ�
            }
        });
        JPanel jp = new JPanel();
        img = new JLabel();
        img.setIcon(imgsaver.loadImage("./image/NoImg.jpg"));
        jp.add(img);
        RButton imgch = new RButton("����");
        imgch.addActionListener(e -> {
            imgsaver.saveUnplus(imgRoot,"item");
            img.setIcon(imgsaver.loadImage("./image/" + imgRoot));
        });
        jp.add(imgch);
        add(jp);
        buttonPanel.add(yes);

        add(buttonPanel);
        loadItem(itemNo);
        // ��ȭ���� ũ��� ��ġ ����
        setSize(300, 200);
        setLocationRelativeTo(null);  // ȭ�� �߾ӿ� ��ȭ���� ǥ��
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setModal(true);  // ��޷� �����Ͽ� �ٸ� â�� ��ȣ�ۿ� �Ұ�
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
        // ���� ���� ��ü ����
        Store store = new Store(
                1,
                "���� �̸�",
                "10:00-22:00",
                "����Ư���� ������ ������� 123�� 45",
                1,
                "store1.png",
                10
        );
        // ��ȭ���� ���� �� ǥ��
        ItemWrite iw = new ItemWrite(store,1);
        iw.setVisible(true);
    }
}