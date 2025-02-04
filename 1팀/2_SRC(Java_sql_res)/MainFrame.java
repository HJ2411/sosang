import javax.swing.*;
import java.awt.*;
import java.util.Stack;

public class MainFrame extends JFrame {
    private JPanel contentPanel;
    private SearchHeader searchHeader;
    private Navigator navigator;
    private SearchResultList searchResultList;
    private Stack<JPanel> pageStack;
    private CardLayout cardLayout;

    public MainFrame() {
        setTitle("소상링");
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(360, 800);
        setLayout(new BorderLayout());

        pageStack = new Stack<>();
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        // 검색 결과 화면 초기화 (스크롤 추가)
        searchResultList = new SearchResultList();
        JScrollPane scrollPane = new JScrollPane(searchResultList);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        contentPanel.add(scrollPane, "SearchResult");

        // MainFrame 객체를 전달하여 SearchHeader 생성
        searchHeader = new SearchHeader(this);
        navigator = new Navigator(this);

        LikeStoreList likeStoreList = new LikeStoreList(this);
        contentPanel.add(likeStoreList, "LikeStoreList");

        // Add components
        add(searchHeader, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
        //add(contentPanel, SearchResultList.CENTER); //불러오고 싶음
        add(navigator, BorderLayout.SOUTH);

        // Initialize first page
        navigateToPage(new MainPage());
    }

    public void navigateToPage(JPanel page) {
        String pageName = page.getClass().getName();

        /*
        // 모든 페이지를 JScrollPane으로 감싸 크기 제한
        JScrollPane scrollPane = new JScrollPane(page);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); // 세로 스크롤 활성화
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // 가로 스크롤 비활성화
        scrollPane.setPreferredSize(new Dimension(360, 640)); // 전체 화면 크기 고정
        */

        contentPanel.add(page, pageName);
        cardLayout.show(contentPanel, pageName);
        pageStack.push(page);
    }


    public void goBack() {
        if (pageStack.size() > 1) {
            pageStack.pop();  // 현재 페이지 제거
            JPanel previousPage = pageStack.peek();  // 이전 페이지 가져오기
            cardLayout.show(contentPanel, previousPage.getClass().getName());

            //0124 검색결과가 있다면
            searchResultList.setVisible(false);
            searchHeader.suggestionPanel.setVisible(true);
        }
    }

    public void updateSearchResults(String query, boolean isStore) {
        // 검색 결과 업데이트
        searchResultList.updateResults(query, isStore);
        /*0124 주석처리
        cardLayout.show(contentPanel, "SearchResult");

        pageStack.clear();
        */
        searchResultList.setVisible(true);
        searchHeader.suggestionPanel.setVisible(false);
        pageStack.push(new JPanel());
        cardLayout.show(contentPanel, "SearchResult");

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}