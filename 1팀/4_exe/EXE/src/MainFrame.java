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
        setTitle("�һ�");
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(360, 800);
        setLayout(new BorderLayout());

        pageStack = new Stack<>();
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        // �˻� ��� ȭ�� �ʱ�ȭ (��ũ�� �߰�)
        searchResultList = new SearchResultList();
        JScrollPane scrollPane = new JScrollPane(searchResultList);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        contentPanel.add(scrollPane, "SearchResult");

        // MainFrame ��ü�� �����Ͽ� SearchHeader ����
        searchHeader = new SearchHeader(this);
        navigator = new Navigator(this);

        LikeStoreList likeStoreList = new LikeStoreList(this);
        contentPanel.add(likeStoreList, "LikeStoreList");

        // Add components
        add(searchHeader, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
        //add(contentPanel, SearchResultList.CENTER); //�ҷ����� ����
        add(navigator, BorderLayout.SOUTH);

        // Initialize first page
        navigateToPage(new MainPage());
    }

    public void navigateToPage(JPanel page) {
        String pageName = page.getClass().getName();

        /*
        // ��� �������� JScrollPane���� ���� ũ�� ����
        JScrollPane scrollPane = new JScrollPane(page);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); // ���� ��ũ�� Ȱ��ȭ
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // ���� ��ũ�� ��Ȱ��ȭ
        scrollPane.setPreferredSize(new Dimension(360, 640)); // ��ü ȭ�� ũ�� ����
        */

        contentPanel.add(page, pageName);
        cardLayout.show(contentPanel, pageName);
        pageStack.push(page);
    }


    public void goBack() {
        if (pageStack.size() > 1) {
            pageStack.pop();  // ���� ������ ����
            JPanel previousPage = pageStack.peek();  // ���� ������ ��������
            cardLayout.show(contentPanel, previousPage.getClass().getName());

            //0124 �˻������ �ִٸ�
            searchResultList.setVisible(false);
            searchHeader.suggestionPanel.setVisible(true);
        }
    }

    public void updateSearchResults(String query, boolean isStore) {
        // �˻� ��� ������Ʈ
        searchResultList.updateResults(query, isStore);
        /*0124 �ּ�ó��
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