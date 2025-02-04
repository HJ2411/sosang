import java.awt.*;
import java.sql.*;

import javax.swing.*;

//import API.GoogleAPI;

public class Map extends JDialog {
    private JTextField textField = new JTextField(20);//����ڿ��� ���� �Է¹��� �� �ְ�
    private JPanel panel = new JPanel();//�ϳ��� �г��� ������ش�

    private GoogleAPI googleAPI = new GoogleAPI();
    private JLabel googleMap= new JLabel();//ó�� �ѹ��� �ʱ�ȭ
    private JPanel master = new JPanel();
    //���� �̹��� �������°�
    public void setMap(String location) {//�Էµ� ���뿡 ���� ���� �ٲ��ִ� �Լ�

        googleAPI.downloadMap(location);//�ش� �ּҸ� ������ �˻� �� �̹����� �ٿ�
        googleMap.setIcon(googleAPI.getMap(location));//JLabel�� �ʱ�ȭ���� �ʰ� �׸��� �ٲ��
        googleAPI.fileDelete(location);//�ٿ�ε� ���� �̹��� ������ �츮 ������Ʈ ������ ����
        master.add(BorderLayout.SOUTH, googleMap);


        pack();

    }
    void SetUI(){
        setSize(360,640);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//â�� ������ ���α׷��� ���������� ������ ����� �� �ֵ���
        setResizable(false);//â�� ũ�⸦ �ٲ� �� ������
        setVisible(true);
    }
    //���� ���� �Է�
    public Map(int storeNo) {

        //SetUI();
        textField.setEditable(false);
        textField.setHorizontalAlignment(JLabel.CENTER);
        panel.add(textField);//��ο��� �ؽ�Ʈ �ʵ� �߰�
        master.setLayout(new BorderLayout());

        master.add(BorderLayout.NORTH, panel);//BorderLayout:Ư���� ��ġ�� �������� ������ ��ҵ��� �迭
        master.add(BorderLayout.SOUTH,googleMap);//���� ���� ������ �ȿ� �߰�

        add(master);
        pack();
        String location = search(storeNo);
        location = location.replace(" ","");
        setMap(location);
    }
    private String search(int storeNo){
        String query = "SELECT LOC,StoreName FROM Store WHERE StoreNo = ?";
        String result = null;
        PreparedStatement rstmt = null;
        ResultSet rs = null;
        Connection conn = null;
        String StoreName = null;
        try {
            conn = DatabaseConnection.getConnection();
            rstmt = conn.prepareStatement(query);

            rstmt.setInt(1,storeNo);
            rs = rstmt.executeQuery();

            while(rs.next()){
                result = rs.getString("LOC");
                StoreName = rs.getString("StoreName");
            }
            textField.setText(StoreName);
        }catch(SQLException se){
        }finally {
            try{
                rs.close();
                rstmt.close();
                conn.close();
            }catch (SQLException se){}
        }
        return result;
    }
    /* �ܵ� �׽�Ʈ
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Map(1)); // StoreNo 1��
    }
    */

}
