package components.buttons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PrimaryButton extends JButton {

    private Color hoverBackgroundColor;
    private Color pressedBackgroundColor;


    public PrimaryButton(String text) {
        super(text);
        super.setContentAreaFilled(false);

        setForeground(Color.white.brighter());
        setHorizontalTextPosition(SwingConstants.CENTER);
        setBorder(null);
        setBackground(new Color(0, 84, 129));
        setHoverBackgroundColor(new Color(0, 72, 107));
        setPressedBackgroundColor(new Color(0, 84, 129).darker());
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



    public void setAsDefaultButtonTo(Component frame) {
            JRootPane rootPane = SwingUtilities.getRootPane(frame);
            rootPane.setDefaultButton(this);
    }


}
