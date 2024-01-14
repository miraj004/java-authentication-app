package components.buttons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class XButton extends JButton {

    Font font = new Font("Open Sans", Font.BOLD, 13);

    public XButton(String label) {
        super(label);

        setBackground(new Color(11, 34, 57));
        setForeground(new Color(227, 212, 212));
        setFont(font);
        setFocusable(false);
        setBorder(null);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setForeground(new Color(241, 77, 77));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setForeground(new Color(227, 212, 212));
            }
        });
    }


}
