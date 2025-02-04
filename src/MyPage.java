import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class MyPage extends JPanel {
    private JTextField passwordField, emailField, nicknameField, idField;
    private JTextField storeNameField, sellerNoField, industryField,
            storePhoneField, locationField, openhoursField;
    private JCheckBox isSellerCheckBox;
    private JPanel sellerPanel;
    private JScrollPane scrollPane;
    private MemberDAO memberDAO;
    private SellerDAO sellerDAO;
    private StoreDAO StoreDAO;
    private ImageSaver imgs = new ImageSaver();
    private JLabel currentNickname;
    private SellerService sellerService;
    private JLabel memberType;
    private RButton storeManageButton;
    private MainFrame mainFrame;

    public MyPage(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        memberDAO = new MemberDAO();
        sellerDAO = new SellerDAO();
        StoreDAO = new StoreDAO();

        initializeComponents();
        sellerService = new SellerService();
        loadMemberData();
    }

    private void initializeComponents() {
        setLayout(new BorderLayout());
        //setPreferredSize(new Dimension(360, 640));

        // Main content panel that will be scrollable
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(null);
        contentPanel.setPreferredSize(new Dimension(360, 800)); // Adjust height as needed

        // 마이페이지 타이틀
        JPanel titlePanel = new JPanel();
        titlePanel.setBounds(12, 32, 360, 35);
        titlePanel.setLayout(null);

        JLabel Title = new JLabel("마이페이지");
        Title.setBounds(0, 0, 100, 35);
        Title.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        Title.setHorizontalAlignment(SwingConstants.CENTER);
        titlePanel.add(Title);
        contentPanel.add(titlePanel);

        // 프로필 패널
        JPanel profilePanel = new JPanel();
        profilePanel.setLayout(null);
        profilePanel.setBounds(12, 67, 360, 80);
        contentPanel.add(profilePanel);

        // 프로필 사진
        JLabel profileImage = new JLabel();
        profileImage.setBounds(10, 10, 60, 60);
        profileImage.setBackground(Color.LIGHT_GRAY);
        profileImage.setOpaque(true);
        profilePanel.add(profileImage);

        // 현재 로그인한 멤버 ID
        String memberId = LoggedInUser.getMemberId();

        // 이미지 로드 시도
        String basePath = "./image/" + memberId;
        String imagePath = imgs.findImageWithExtension(basePath);
        File imageFile = new File(imagePath);
        if (imageFile.exists()) {
            ImageIcon profileIcon = imgs.loadImage(imagePath);
            profileImage.setIcon(profileIcon);
        } else {
            System.out.println("이미지 파일을 찾을 수 없습니다: " + imagePath);
        }

        profileImage.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                boolean saved = imgs.saveUnplus(memberId, "Member");
                if (saved) {
                    // 이미지 저장 후 갱신
                    String newImagePath = imgs.findImageWithExtension("./image/" + memberId);
                    ImageIcon newProfileIcon = imgs.loadImage(newImagePath);
                    profileImage.setIcon(newProfileIcon);
                } else {
                    System.out.println("이미지를 저장하는 데 실패했습니다.");
                }
            }
        });

        // 현재 닉네임
        currentNickname = new JLabel("현재 닉네임");
        currentNickname.setBounds(80, 15, 150, 25);
        currentNickname.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        profilePanel.add(currentNickname);

        // 회원유형
        memberType = new JLabel("소비자");
        memberType.setBounds(80, 40, 150, 20);
        memberType.setForeground(Color.GRAY);
        profilePanel.add(memberType);

        //가게관리 버튼
        storeManageButton = new RButton("가게관리");
        storeManageButton.setVisible(false);
        storeManageButton.setBounds(230, 40, 70,30);
        profilePanel.add(storeManageButton);

        storeManageButton.addActionListener( e -> {
            Seller seller = sellerDAO.getSellerByMemberId(memberId);
            System.out.println("Seller: " + seller);

            if (seller != null) {
                int storeNo = seller.getSellerNo();
                System.out.println("SellerNo: " + storeNo);
                Store store = StoreDAO.getStoreById(storeNo);
                System.out.println("Store: " + store);

                if (store != null) {
                    mainFrame.navigateToPage(new StoreManagement(store)); // StoreManagement로 이동
                } else {
                    JOptionPane.showMessageDialog(this, "해당 가게 정보를 찾을 수 없습니다.");
                }
            }
        });

        // 구분선
        JSeparator line = new JSeparator();
        line.setBounds(12, 150, 320, 1);
        contentPanel.add(line);

        // 회원정보 패널
        JPanel MemberinfoPanel = new JPanel();
        MemberinfoPanel.setLayout(null);
        MemberinfoPanel.setBounds(12, 160, 360, 500);

        // Panel 1 (기본 회원정보)
        JPanel panel_1 = new JPanel();
        panel_1.setLayout(null);
        panel_1.setBounds(0, 0, 368, 500);
        MemberinfoPanel.add(panel_1);

        // 닉네임 필드
        JLabel lbnickName = new JLabel("닉네임");
        lbnickName.setBounds(12, 43, 57, 15);
        panel_1.add(lbnickName);

        nicknameField = new JTextField("닉네임");
        nicknameField.setBounds(100, 43, 130, 23);
        panel_1.add(nicknameField);

        RButton nickModification = new RButton("수정");
        nickModification.setBackground(new Color(247, 211, 38));
        nickModification.setBounds(240, 43, 63, 23);
        panel_1.add(nickModification);

        nickModification.addActionListener(e -> {
            String newNickname = nicknameField.getText();
            updateNickname();
        });

        // ID 필드
        JLabel lbID = new JLabel("아이디");
        lbID.setBounds(12, 68, 57, 15);
        panel_1.add(lbID);

        idField = new JTextField("기존아이디");
        idField.setBounds(100, 68, 130, 23);
        idField.setEditable(false);
        panel_1.add(idField);

        // 비밀번호 필드
        JLabel lbPWD = new JLabel("비밀번호");
        lbPWD.setBounds(12, 93, 57, 15);
        panel_1.add(lbPWD);

        passwordField = new JPasswordField("13579");
        passwordField.setBounds(100, 93, 130, 23);
        panel_1.add(passwordField);

        RButton pwdModification = new RButton("수정");
        pwdModification.setBackground(new Color(247, 211, 38));
        pwdModification.setBounds(240, 93, 63, 23);
        panel_1.add(pwdModification);

        pwdModification.addActionListener(e -> {
            String newPassword = passwordField.getText();
            updatePassword();
        });

        // 이메일 필드
        JLabel lbEmail = new JLabel("이메일");
        lbEmail.setBounds(12, 120, 63, 23);
        panel_1.add(lbEmail);

        emailField = new JTextField("abc123@gmail.com");
        emailField.setBounds(100, 120, 130, 23);
        panel_1.add(emailField);

        RButton emailModification = new RButton("수정");
        emailModification.setBackground(new Color(247, 211, 38));
        emailModification.setBounds(240, 120, 63, 23);
        panel_1.add(emailModification);

        emailModification.addActionListener(e -> {
            String newEmail = emailField.getText();
            updateEmail();
        });

        // Panel 2 (판매자 정보)
        JPanel panel_2 = new JPanel();
        panel_2.setLayout(null);
        panel_2.setBounds(0, 150, 360, 400);
        MemberinfoPanel.add(panel_2);
/*
        // 소상공인 체크박스
        isSellerCheckBox = new JCheckBox("소상공인입니까?");
        isSellerCheckBox.setBounds(10, 150, 150, 30);
        panel_1.add(isSellerCheckBox);*/

        //가게등록하기 Checkbox -> Label
        JLabel isSellerCheckBox = new JLabel("<html><span style='font-size:14pt; color:2a4cb3;'>▶ 가게등록하기</span></html>");
        isSellerCheckBox.setBounds(10,150,150,30);
        isSellerCheckBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panel_1.add(isSellerCheckBox);

        isSellerCheckBox.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                sellerPanel.setVisible(!sellerPanel.isVisible());
            }
        });

        // 판매자 정보 패널
        sellerPanel = new JPanel();
        sellerPanel.setLayout(null);
        sellerPanel.setBounds(5, 35 , 300, 290);
        sellerPanel.setVisible(false);
        sellerPanel.setBorder(BorderFactory.createLineBorder(new Color(0x2349BA)));
        panel_2.add(sellerPanel);

        // 가게 정보 필드들

        addLableField(sellerPanel, "가게이름 :", storeNameField = new JTextField(), 0);
        addLableField(sellerPanel, "사업자등록번호 :", sellerNoField = new JTextField(), 1);
        addLableField(sellerPanel, "업종 :", industryField = new JTextField(), 2);
        addLableField(sellerPanel, "연락처 :", storePhoneField = new JTextField(), 3);
        addLableField(sellerPanel, "위치 :", locationField = new JTextField(), 4);
        addLableField(sellerPanel, "운영시간 :", openhoursField = new JTextField(), 5);

        // 등록 버튼
        RButton registerStore = new RButton("등록");
        registerStore.setBackground(new Color(247, 211, 38));
        registerStore.setBounds(200, 235, 80, 28);
        sellerPanel.add(registerStore);

        registerStore.addActionListener(e -> {
            registerStore();
        });

        contentPanel.add(MemberinfoPanel);

        add(contentPanel, BorderLayout.CENTER);
    }

    private void addLableField(JPanel panel, String labelText, JTextField field, int row) {
        JLabel label = new JLabel(labelText);
        label.setBounds(12, 20 + (row * 30), 100, 25);
        field.setBounds(120, 20 + (row * 30), 160, 25);
        panel.add(label);
        panel.add(field);
    }

    private void loadMemberData() {
        String memberId = LoggedInUser.getMemberId();
        User user = memberDAO.getUserById(memberId);
        if (user != null) {
            nicknameField.setText(user.getNick());
            idField.setText(user.getMemberId());
            passwordField.setText(user.getPwd());
            emailField.setText(user.getEmail());
            currentNickname.setText(user.getNick());
        }

        if (sellerDAO.isSeller(memberId)) {
            memberType.setText("판매자");
            storeManageButton.setVisible(true);
            Seller seller = sellerDAO.getSellerByMemberId(memberId);
            if (seller != null) {
                int storeNo = seller.getSellerNo();
                Store store = StoreDAO.getStoreById(storeNo);
                if (store != null) {
                    /*sellerPanel.setVisible(true);
                    storeNameField.setText(store.getStoreName());
                    sellerNoField.setText(String.valueOf(seller.getSellerNo()));
                    industryField.setText(String.valueOf(store.getIndustry()));
                    locationField.setText(store.getLocation());
                    openhoursField.setText(store.getOpenHours());*/
                }
            } else {
                JOptionPane.showMessageDialog(this, "해당 회원은 판매자가 아닙니다.");
            }
        } else {
            memberType.setText("소비자");
        }

    }

    private void updateNickname() {
        String newNickname = nicknameField.getText();
        boolean success =memberDAO.updateNick(LoggedInUser.getMemberId(),newNickname);
        if(success){
            LoggedInUser.setNick(newNickname);
            //JOptionPane.showMessageDialog(this, "닉네임이 수정되었습니다.");
            COptionPane.showCustomDialog(this, "닉네임이 수정되었습니다.", "알림");
            currentNickname.setText(newNickname);
        }else {
            COptionPane.showCustomDialog(this, "닉네임 수정에 실패했습니다.", "에러");
        }
    }

    private void updatePassword() {
        String newPassword = new String(((JPasswordField) passwordField).getPassword());
        boolean sucess = memberDAO.updatePWD(LoggedInUser.getMemberId(), newPassword);

        if(sucess) {
            COptionPane.showCustomDialog(this, "비밀번호가 수정되었습니다.", "알림");
        }else {
            COptionPane.showCustomDialog(this, "비밀번호 수정에 실패했습니다.", "에러");
        }
    }

    private void updateEmail() {
        String newEmail = emailField.getText();
        String memberId = LoggedInUser.getMemberId();
        String memberNick = LoggedInUser.getNick();

        boolean sucess = EmailValidator.isValidEmail(newEmail) && memberDAO.updateEmail(memberId,newEmail);

        if(sucess) {
            COptionPane.showCustomDialog(this, "<html><div style='text-align:center;'>이메일이 수정되었습니다.<br>" + newEmail + "</div></html>", "알림");
        }else {
            COptionPane.showCustomDialog(this, "이메일 수정에 실패했습니다.", "에러");
        }
    }

    private void toggleSellerPanel() {
        sellerPanel.setVisible(isSellerCheckBox.isSelected());
    }

    private void registerStore() {
        String memberId = LoggedInUser.getMemberId();
        String storeName = storeNameField.getText();
        int sellerNo = Integer.parseInt(sellerNoField.getText());
        int industry = Integer.parseInt(industryField.getText());
        String storePhone = storePhoneField.getText();
        String location = locationField.getText();
        String openHours = openhoursField.getText();

        if (storeName.isEmpty() || storePhone.isEmpty() || location.isEmpty() || openHours.isEmpty() || sellerNo <= 0) {
            COptionPane.showCustomDialog(this, "입력하지 않은 정보가 있습니다.", "에러");
            return;
        }

        boolean success = sellerService.registerSellerAndStore(sellerNo, memberId, storeName, storePhone, location, industry, openHours);
        if (success) {
            memberType.setText("판매자");
            storeManageButton.setVisible(true);
            COptionPane.showCustomDialog(this, "가게 정보가 등록되었습니다.", "알림");
        } else {
            COptionPane.showCustomDialog(this, "가게 정보 등록에 실패했습니다.", "에러");
        }
    }

    //0202실패한 가게관리 버튼 동작 메서드
    private void openStoreManagement(){
        String memberId = LoggedInUser.getMemberId();
        Seller seller = sellerDAO.getSellerByMemberId(memberId);

        if (seller != null) {
            int storeNo = seller.getSellerNo();
            Store store = StoreDAO.getStoreById(storeNo);

            if (store != null) {
                SwingUtilities.invokeLater(() -> {
                    StoreManagement storeManagementFrame = new StoreManagement(store);
                    storeManagementFrame.setVisible(true);
                });
            } else {
                JOptionPane.showMessageDialog(this, "해당 가게 정보를 찾을 수 없습니다.");
            }
        }
    }


    private void addLabelField(JPanel panel, String labelText, JTextField field, int row) {
        JLabel label = new JLabel(labelText);
        label.setBounds(12, 20 + (row * 30), 100, 25);
        field.setBounds(120, 20 + (row * 30), 140, 25);
        panel.add(label);
        panel.add(field);
    }
}