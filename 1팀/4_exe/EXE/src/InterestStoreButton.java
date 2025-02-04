import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InterestStoreButton extends JButton {

    private String memberId;
    private int storeNo;
    private InterestListManager interestListManager;

    public InterestStoreButton(String memberId, int storeNo, InterestListManager interestListManager) {
        super("���� ���� ���");
        this.memberId = memberId;
        this.storeNo = storeNo;
        this.interestListManager = interestListManager;

        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleInterest();
            }
        });
    }

    private void toggleInterest() {
        if (getText().equals("���� ���� ���")) {
            if (interestListManager.addInterestStore(memberId, storeNo)) {
                setText("���� ���� ���");
                JOptionPane.showMessageDialog(this, "���԰� ���� ��Ͽ� �߰��Ǿ����ϴ�.");
            } else {
                JOptionPane.showMessageDialog(this, "���� ��Ͽ� �����߽��ϴ�.");
            }
        } else {
            if (interestListManager.removeInterestStore(memberId, storeNo)) {
                setText("���� ���� ���");
                JOptionPane.showMessageDialog(this, "���԰� ���� ��Ͽ��� �����Ǿ����ϴ�.");
            } else {
                JOptionPane.showMessageDialog(this, "���� ������ �����߽��ϴ�.");
            }
        }
    }
}
