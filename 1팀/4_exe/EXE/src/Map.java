import java.awt.*;
import java.sql.*;

import javax.swing.*;

//import API.GoogleAPI;

public class Map extends JDialog {
    private JTextField textField = new JTextField(20);//사용자에세 직접 입력받을 수 있게
    private JPanel panel = new JPanel();//하나의 패널을 만들어준다

    private GoogleAPI googleAPI = new GoogleAPI();
    private JLabel googleMap= new JLabel();//처음 한번만 초기화
    private JPanel master = new JPanel();
    //지도 이미지 가져오는것
    public void setMap(String location) {//입력된 내용에 따라 맵을 바꿔주는 함수

        googleAPI.downloadMap(location);//해당 주소를 실제로 검색 후 이미지로 다운
        googleMap.setIcon(googleAPI.getMap(location));//JLabel을 초기화하지 않고 그림만 바뀌도록
        googleAPI.fileDelete(location);//다운로드 받은 이미지 파일은 우리 프로젝트 내에서 삭제
        master.add(BorderLayout.SOUTH, googleMap);


        pack();

    }
    void SetUI(){
        setSize(360,640);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//창을 껐을떄 프로그램이 성공적으로 완전히 종료될 수 있도록
        setResizable(false);//창의 크기를 바꿀 수 없도록
        setVisible(true);
    }
    //지도 정보 입력
    public Map(int storeNo) {

        //SetUI();
        textField.setEditable(false);
        textField.setHorizontalAlignment(JLabel.CENTER);
        panel.add(textField);//페널에는 텍스트 필드 추가
        master.setLayout(new BorderLayout());

        master.add(BorderLayout.NORTH, panel);//BorderLayout:특정한 위치를 기준으로 각각의 요소들을 배열
        master.add(BorderLayout.SOUTH,googleMap);//구글 맵을 프레임 안에 추가

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
    /* 단독 테스트
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Map(1)); // StoreNo 1번
    }
    */

}
