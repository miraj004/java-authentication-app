package components.buttons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SuccessButton extends JButton {

    Font font = new Font("Open Sans", Font.BOLD, 13);

    public SuccessButton(String label) {

        super(label);

        setBackground(new Color(43, 196, 138));
        setForeground(Color.white);
        setFont(font);
        setFocusable(false);
        setBorder(null);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(new Color(0, 175, 128));
                setForeground(new Color(214, 221, 225));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(new Color(43, 196, 138));
                setForeground(Color.white);
            }
        });
    }

    public void setAsDefaultButtonTo(Component frame) {
            JRootPane rootPane = SwingUtilities.getRootPane(frame);
            rootPane.setDefaultButton(this);
    }


}
