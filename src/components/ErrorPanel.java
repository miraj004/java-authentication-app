package components;


import authentication.user.intructionwithdb.UBasicOperation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ErrorPanel  extends JScrollPane  {

private final JTextArea errorArea = new JTextArea();

    public ErrorPanel(){

        errorArea.setBackground(new Color(255, 211, 217));
        errorArea.setForeground(new Color(197, 1, 1));
        errorArea.setWrapStyleWord(true);
        errorArea.setLineWrap(true);
        errorArea.setEditable(false);
        errorArea.setFocusable(false);
        errorArea.setToolTipText("Click to Dismiss");
        errorArea.getToolkit().setDynamicLayout(true);
        errorArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                errorArea.setVisible(false);
            }
        });

        errorArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                UBasicOperation.dismiss();
            }
        });

    }

    public JTextArea getErrorArea() {
        return errorArea;
    }

}
