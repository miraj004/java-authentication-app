package components.dialogs.password;

import components.Toolbar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangePasswordDialog extends JFrame implements ActionListener {

    public static int CURRENT = 0;
    public static int NEW = 1;
    private static final CurrentPasswordPanel currentPasswordPanel = new CurrentPasswordPanel();
    private static final ChangePasswordPanel changePasswordPanel = new ChangePasswordPanel();

    public ChangePasswordDialog(int type) {

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setUndecorated(true);

        setSize(400, type == CURRENT ? 350 : 450);
        getContentPane().setBackground(Color.white);

        Toolbar toolbar = new Toolbar(type == CURRENT ? "Current Password" : "Change Password");
        toolbar.getTitleLabel().setFont(new Font("Open Sans", Font.BOLD, 20));

        JPanel bottomPanel = new JPanel(new BorderLayout());
        JPanel linePanel = new JPanel();
        linePanel.setBackground(new Color(0, 135, 187));
        bottomPanel.setBackground(new Color(31, 54, 77));
        bottomPanel.setPreferredSize(new Dimension(0, 50));
        bottomPanel.add(linePanel, BorderLayout.NORTH);

        if (type == CURRENT)
            currentPasswordPanel.setEnterAsDefaultButton(this);
        else
            changePasswordPanel.setEnterAsDefaultButton(this);

        add(toolbar, BorderLayout.NORTH);
        add(type == CURRENT ? currentPasswordPanel : changePasswordPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.dispose();
    }
}
