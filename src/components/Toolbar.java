package components;

import javax.swing.*;
import java.awt.*;

public class Toolbar extends JPanel {

    private final JLabel titleLabel = new JLabel();

    public Toolbar(String label) {

        setPreferredSize(new Dimension(0, 100));
        setBackground(new Color(11, 34, 57));
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));

        titleLabel.setText(label);
        titleLabel.setFont(new Font("Open Sans", Font.BOLD, 30));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setVerticalAlignment(JLabel.CENTER);
        titleLabel.setForeground(Color.white);

        add(titleLabel);
    }

    public JLabel getTitleLabel() {
        return titleLabel;
    }

}
