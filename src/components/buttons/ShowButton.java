package components.buttons;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import java.awt.*;

public class ShowButton extends JButton {

    private Color hoverBackgroundColor;
    private Color pressedBackgroundColor;


    public ShowButton(String text) {
        super(text);
        super.setContentAreaFilled(false);
        setText(text);
        setFont(new Font("Open Sans", Font.BOLD, 9));
        setBackground(Color.white);
        setPressedBackgroundColor(new Color(3, 59, 90));
        setHoverBackgroundColor(Color.white);
        setBorder(new CompoundBorder(
                BorderFactory.createLineBorder(new Color(212, 219, 227)),
                BorderFactory.createEmptyBorder(2, 6 , 2, 6)));
        setFocusable(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isPressed()) {
            g.setColor(pressedBackgroundColor);
        } else if (getModel().isRollover()) {
            g.setColor(hoverBackgroundColor);
        } else {
            g.setColor(getBackground());
        }
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }

    @Override
    public void setContentAreaFilled(boolean b) {
    }

    public void setHoverBackgroundColor(Color hoverBackgroundColor) {
        this.hoverBackgroundColor = hoverBackgroundColor;
    }


    public void setPressedBackgroundColor(Color pressedBackgroundColor) {
        this.pressedBackgroundColor = pressedBackgroundColor;
    }
}
