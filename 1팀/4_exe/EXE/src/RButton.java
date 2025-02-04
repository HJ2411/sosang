import javax.swing.*;
import java.awt.*;

//버튼 디자인
public class RButton extends JButton {
    public Color backgroundColor = new Color(247, 211, 38);
    public Color textColor = new Color(30, 30, 30);
    public Color borderColor = null;
    public int borderThickness = 1;

    public RButton() {
        super();
        decorate();
    }
    public RButton(String text) {
        super(text);
        decorate();
    }
    public RButton(Action action) {
        super(action);
        decorate();
    }
    public RButton(Icon icon) {
        super(icon);
        decorate();
    }

    public RButton(ImageIcon icon) {
        super(icon);
        decorate();
    }
    public RButton(String text, Icon icon) {
        super(text, icon);
        decorate();
    }
    public void decorate() {
        setBorderPainted(false);
        setOpaque(false);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = getWidth();
        int height = getHeight();
        Graphics2D graphics = (Graphics2D) g;

        //안티 앨리어싱
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (getModel().isArmed()) {
            graphics.setColor(backgroundColor.darker());
        }else if (getModel().isRollover()) {
            graphics.setColor(backgroundColor.darker());
        }else {
            graphics.setColor(backgroundColor);
        }
        graphics.fillRoundRect(0, 0, width, height, 12, 12); //radius

        if(borderColor != null){
            graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
            graphics.setColor(borderColor);
            graphics.setStroke(new BasicStroke(borderThickness));
            graphics.drawRoundRect(0,0, width-1, height-1, 12, 12);
            graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        }

        Icon icon = getIcon();
        if (icon != null) {
            int iconX = (width - icon.getIconWidth()) / 2;
            int iconY = (height - icon.getIconHeight()) / 2;
            icon.paintIcon(this, graphics, iconX, iconY);
        }

        FontMetrics fontMetrics = graphics.getFontMetrics();
        Rectangle stringBounds = fontMetrics.getStringBounds(this.getText(), graphics).getBounds();
        int textX = (width - stringBounds.width) / 2;
        int textY = (height - stringBounds.height) / 2 + fontMetrics.getAscent();
        graphics.setColor(textColor);
        graphics.setFont(getFont());

        graphics.drawString(getText(), textX, textY);
        graphics.dispose();
    }

    public void setFontSize(int size){
        setFont(new Font(getFont().getName(), getFont().getStyle(), size));
    }

    public void setBackgroundColor(Color color){
        this.backgroundColor = color;
        repaint();
    }

    public void setTextColor(Color color){
        this.textColor = color;
        repaint();
    }

    public void setBorderColor(Color color, int thickness) {
        this.borderColor = color;
        this.borderThickness = thickness;
        repaint();
    }
}
