import java.awt.*;
import java.awt.event.*;
import java.text.CompactNumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.EventListener;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.*;

import java.sql.*;

import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;


public class StoreManagement extends JPanel {

    String[] openingTimes = {
            "00:00", "00:30", "01:00", "01:30", "02:00", "02:30", "03:00", "03:30", "04:00", "04:30",
            "05:00", "05:30", "06:00", "06:30", "07:00", "07:30", "08:00", "08:30", "09:00", "09:30",
            "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30",
            "15:00", "15:30", "16:00", "16:30", "17:00", "17:30", "18:00", "18:30", "19:00", "19:30",
            "20:00", "20:30", "21:00", "21:30", "22:00", "22:30", "23:00", "23:30"
    };

    private JComboBox<String> comboBoxOpenTime = new JComboBox<>(openingTimes);
    private JComboBox<String> comboBoxCloseTime = new JComboBox<>();

    private Store store;
    private RButton[] StoreBtn = new RButton[3];
    private JLabel my;
    private String isql = "select ItemNo,ItemName,Amount from item where StoreNo=";
    private String nsql = "select NoticeNo,Title from notice where StoreNo=";
    private JTable nJT,iJT;
    private RButton[] storeBtn = new RButton[3];
    private JTextArea addrInput1 = new JTextArea(1,13);
    private JTextArea addrInput2 = new JTextArea(1,13);
    private JTextArea addrInput3 = new JTextArea(1,22);
    private JScrollPane sp,spItem;
    int row;
    StoreManagement(Store store) {
        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(1,3));
        ImageIcon icon = new ImageIcon("./image/"+store.getImage());
        Image img = icon.getImage().getScaledInstance(30, 30, DO_NOTHING_ON_CLOSE);
        ImageIcon user = new ImageIcon(img);
        TableModel notice;
        TableModel item;
        this.store = store;


        my = new JLabel("가게이름",user,SwingConstants.LEFT);
        my.setText(store.getStoreName());
        add(my,BorderLayout.NORTH);

        JPanel mp = new JPanel();
        mp.setPreferredSize(new Dimension(350,640));
        mp.setLayout(new GridLayout(3,1));

        JPanel p1 = new JPanel();
        p1.setLayout(new BorderLayout());
        storeBtn[0] = new RButton("가게정보 수정");
        JLabel addr1 = new JLabel("시");
        JLabel addr2 = new JLabel("구");
        JLabel addr3 = new JLabel("상세주소");
        storeBtn[0].addActionListener(e->{
            String loc = addrInput1.getText()+" "+addrInput2.getText()+" "+addrInput3.getText();
            String openHours = comboBoxOpenTime.getSelectedItem().toString()+"-"+comboBoxCloseTime.getSelectedItem().toString();
            String sql = "update Store set LOC='"+loc+"',OpenHours='"+openHours+"' where StoreNo="+store.getStoreNo();
            Connection con = null;
            Statement stmt = null;
            System.out.println(sql);
            try{
                con = DatabaseConnection.getConnection();
                stmt = con.createStatement();
                stmt.execute(sql);

                JOptionPane.showMessageDialog(this,"수정완료");
            }catch(SQLException se){

            } finally {
                try{
                    stmt.close();
                    con.close();
                }catch (SQLException se){}
            }
        });
        JLabel opentime = new JLabel("오픈시간");
        comboBoxOpenTime.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 선택된 오픈시간
                String selectedOpenTime = (String) comboBoxOpenTime.getSelectedItem();

                // selectedOpenTime이 null인 경우
                if (selectedOpenTime == null) {
                    return; // 아무것도 선택되지 않으면 리턴
                }

                // 가능한 클로즈시간 리스트
                ArrayList<String> availableCloseTimes = new ArrayList<>();

                // 오픈시간의 인덱스를 구함
                int openIndex = java.util.Arrays.asList(openingTimes).indexOf(selectedOpenTime);

                // 오픈시간보다 30분 뒤부터 시작
                for (int i = openIndex + 1; i < openingTimes.length; i++) {
                    availableCloseTimes.add(openingTimes[i]);
                }

                // 클로즈시간 ComboBox에 업데이트
                comboBoxCloseTime.removeAllItems();
                for (String closeTime : availableCloseTimes) {
                    comboBoxCloseTime.addItem(closeTime);
                }

                // 첫 번째 클로즈시간 선택
                if (comboBoxCloseTime.getItemCount() > 0) {
                    comboBoxCloseTime.setSelectedIndex(0);
                }
            }
        });


        JPanel p1_1 = new JPanel();
        JPanel p1_2 = new JPanel();
        p1_1.setLayout(new BorderLayout());
        p1_1.add(storeBtn[0],BorderLayout.WEST);
        p1.add(p1_1,BorderLayout.NORTH);
        p1_2.add(addr1);
        p1_2.add(addrInput1);
        p1_2.add(addr2);
        p1_2.add(addrInput2);
        p1_2.add(addr3);
        p1_2.add(addrInput3);
        p1_2.add(opentime);
        p1_2.add(comboBoxOpenTime);
        p1_2.add(new JLabel("~"));
        p1_2.add(comboBoxCloseTime);
        p1.add(p1_2,BorderLayout.CENTER);


        JPanel p2 = new JPanel();
        JPanel p2_1 = new JPanel();
        p2.setLayout(new BorderLayout());
        storeBtn[1] = new RButton("가게소식 등록");
        storeBtn[1].addActionListener(e->{
            JFrame noticeFrame = new JFrame("공지 작성");
            noticeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            noticeFrame.setSize(340, 500);
            noticeFrame.add(new EventWrite());
            noticeFrame.setLocationRelativeTo(this);

            noticeFrame.setVisible(true);

            noticeFrame.addWindowListener(new WindowAdapter(){
                @Override
                public void windowClosed(WindowEvent e) {
                    refreshNoticeTable();
                }
            });
        });

        p2_1.setLayout(new BorderLayout());
        p2_1.add(storeBtn[1],BorderLayout.WEST);
        nJT = new JTable(setJT(nsql+store.getStoreNo()));

        resizeColumnWidth(nJT);
        nJT.addMouseListener(new handler());
        nJT.setDefaultEditor(Object.class,null);
        sp = new JScrollPane(nJT);


        p2.add(p2_1,BorderLayout.NORTH);
        p2.add(sp,BorderLayout.CENTER);

        //

        iJT = new JTable(setJT(isql+store.getStoreNo()));
        resizeColumnWidth(iJT);
        spItem = new JScrollPane(iJT);

        iJT.setDefaultEditor(Object.class,null);
        iJT.addMouseListener(new handler());
        JPanel p3 = new JPanel();
        p3.setLayout(new BorderLayout());
        storeBtn[2] = new RButton("가게물품 등록");
        storeBtn[2].addActionListener(e->{
            JDialog itemJD = new JDialog(new ItemWrite(store, (Integer) iJT.getModel().getValueAt(row,0)));

            itemJD.addWindowListener(new WindowAdapter(){
                @Override
                public void windowClosing(WindowEvent e) {
                    refreshItemTable();
                }
            });
        });
        JPanel p3_1 = new JPanel();
        p3_1.setLayout(new BorderLayout());
        p3_1.add(storeBtn[2],BorderLayout.WEST);
        p3.add(p3_1,BorderLayout.NORTH);
        p3.add(spItem,BorderLayout.CENTER);
        storeBtn[2].addActionListener(e->{
            new ItemWrite(store).setVisible(true);
        });
        for(int i=0;i<3;i++)
            storeBtn[i].setBackground(Color.yellow);

        mp.add(p1);
        mp.add(p2);
        mp.add(p3);
        setInfo();
        parseAddress(store.getLocation());
        add(mp,BorderLayout.CENTER);

    }

    void SetUI() {

        setSize(360, 640);
        setVisible(true);
    }

    TableModel setJT(String sql) {

        ResultSet rs = null;
        Statement stmt = null;
        Connection con = null;
        Vector<String> columnNames = new Vector<>();
        Vector<Vector<Object>> rowData = new Vector<>();
        try {
            con = DatabaseConnection.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();

            int cc = rsmd.getColumnCount();
            for(int i=1;i<=cc;i++)
                columnNames.add(rsmd.getColumnName(i));

            while(rs.next()) {
                Vector<Object> vT = new Vector<>();
                for(int i=1;i<=cc;i++) {
                    vT.add(rs.getObject(i));
                }
                rowData.add(vT);
            }
            TableModel dm = new DefaultTableModel(rowData, columnNames);
            return dm;
        } catch (SQLException e) {

            e.printStackTrace();
        }finally {
            try {
                if (rs!=null) rs.close();
                stmt.close();
                con.close();
            } catch (SQLException e) {}
        }
        return null;
    }

    void setInfo(){
        String sql = "select LOC,OpenHours from Store where StoreNo= "+store.getStoreNo();
        ResultSet rs = null;
        Statement stmt = null;
        Connection con = null;
        String LOC = null;
        String openHours = null;
        try {
            con = DatabaseConnection.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()){
                LOC = rs.getString(1);
                openHours = rs.getString(2);
            }
        } catch (SQLException e) {}
        finally {
            try {
                if (rs!=null) rs.close();
                stmt.close();
                con.close();
            } catch (SQLException e) {}
        }
        String[] parts = openHours.split("-");

        comboBoxOpenTime.setSelectedItem(parts[0]);
        comboBoxCloseTime.setSelectedItem(parts[1]);
    }

    void parseAddress(String address) {
        // 공백 기준으로 주소를 분리
        String[] parts = address.split(" ",3);

        // 주소 배열의 길이에 맞춰 반환 (시/도, 구/군, 동, 번지 등)
        if (parts.length == 3) {
            addrInput1.append(parts[0]);
            addrInput2.append(parts[1]);
            addrInput3.append(parts[2]);
        }
    }

    void refreshNoticeTable() {
        DefaultTableModel model = (DefaultTableModel) nJT.getModel();
        model.setRowCount(0); // 기존 데이터 삭제

        ResultSet rs = null;
        Statement stmt = null;
        Connection con = null;

        try {
            con = DatabaseConnection.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(nsql + store.getStoreNo());

            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getInt("NoticeNo"));
                row.add(rs.getString("Title"));
                model.addRow(row);
            }

            model.fireTableDataChanged(); // 데이터 변경 알림
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        resizeColumnWidth(nJT);
    }

    void refreshItemTable() {
        DefaultTableModel model = (DefaultTableModel) iJT.getModel();
        model.setRowCount(0); // 기존 데이터 삭제

        ResultSet rs = null;
        Statement stmt = null;
        Connection con = null;

        try {
            con = DatabaseConnection.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(isql + store.getStoreNo());

            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getInt("ItemNo"));
                row.add(rs.getString("ItemName"));
                row.add(rs.getInt("Amount"));

                model.addRow(row);
            }

            model.fireTableDataChanged(); // 데이터 변경 알림
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        resizeColumnWidth(iJT);
    }

    void resizeColumnWidth(JTable table) {
        final TableColumnModel columnModel = table.getColumnModel();
        for (int column = 0; column < table.getColumnCount(); column++) {
            int width = 50; // Min width
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer renderer = table.getCellRenderer(row, column);
                Component comp = table.prepareRenderer(renderer, row, column);
                width = Math.max(comp.getPreferredSize().width +1 , width);
            }
            columnModel.getColumn(column).setPreferredWidth(width);
        }
    }

    class handler implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            Object obj = e.getSource();
            if(e.getClickCount()==2) {
                if (obj == nJT){
                    int row = nJT.getSelectedRow();
                    JFrame noticeFrame = new JFrame("공지 작성");
                    noticeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    noticeFrame.setSize(340, 500);
                    noticeFrame.add(new EventWrite((int) nJT.getModel().getValueAt(row,0),store.getStoreNo()));
                    noticeFrame.setVisible(true);

                    noticeFrame.addWindowListener(new WindowAdapter(){
                        @Override
                        public void windowClosed(WindowEvent e) {
                            refreshNoticeTable();
                        }
                    });
                }
                else if(obj == iJT){
                    row = iJT.getSelectedRow();
                    JDialog itemJD =  new ItemWrite(store, (Integer) iJT.getModel().getValueAt(row,0));
                    itemJD.setVisible(true);
                    itemJD.addWindowListener(new WindowAdapter(){
                        @Override
                        public void windowClosed(WindowEvent e) {
                            System.out.println("test");
                            refreshItemTable();
                        }
                    });
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
    public static void main(String[] args) {
        Store store = new Store(
            1,
            "가게 이름",
            "10:00-22:00",
            "서울특별시 강남구 테헤란로 123길 45",
            1,
            ("store1.png"),
            10
    );
        JFrame frame = new JFrame("StoreManagement");
        frame.setLayout(new GridLayout(1,3) );
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(360,640);
        frame.add(new StoreManagement(store));
        frame.setVisible(true);
    }
}

