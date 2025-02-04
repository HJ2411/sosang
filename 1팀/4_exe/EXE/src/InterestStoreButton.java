import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InterestStoreButton extends JButton {

    private String memberId;
    private int storeNo;
    private InterestListManager interestListManager;

    public InterestStoreButton(String memberId, int storeNo, InterestListManager interestListManager) {
        super("관심 가게 등록");
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
        if (getText().equals("관심 가게 등록")) {
            if (interestListManager.addInterestStore(memberId, storeNo)) {
                setText("관심 가게 취소");
                JOptionPane.showMessageDialog(this, "가게가 관심 목록에 추가되었습니다.");
            } else {
                JOptionPane.showMessageDialog(this, "가게 등록에 실패했습니다.");
            }
        } else {
            if (interestListManager.removeInterestStore(memberId, storeNo)) {
                setText("관심 가게 등록");
                JOptionPane.showMessageDialog(this, "가게가 관심 목록에서 삭제되었습니다.");
            } else {
                JOptionPane.showMessageDialog(this, "가게 삭제에 실패했습니다.");
            }
        }
    }
}
