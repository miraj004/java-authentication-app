package authentication.user;

import authentication.user.intructionwithdb.UOperationButtons;
import authentication.user.intructionwithdb.UserTablePanel;
import components.Toolbar;
import components.dialogs.password.ChangePasswordDialog;
import components.lookandfeel.NimLookAndFeel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UserFrame extends
        JFrame implements ActionListener {

    int posX = 0, posY = 0;

    private static final JButton changePasswordBtn = new JButton("CHANGE PASSWORD");

    private static ChangePasswordDialog changePasswordDialog;

    public UserFrame() {

        NimLookAndFeel.setNimLookAndFeel(this);

        new UOperationButtons();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 400);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        getContentPane().setBackground(Color.white);

        Toolbar toolbar = new Toolbar("Account Management");

        JPanel bottomPanel = new JPanel(new BorderLayout());
        JPanel linePanel = new JPanel();
        linePanel.setBackground(new Color(213, 192, 127));
        bottomPanel.setBackground(new Color(11 + 20, 34 + 20, 57 + 20));
        bottomPanel.setPreferredSize(new Dimension(0, 50));
        bottomPanel.add(linePanel, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.white);
        mainPanel.setLayout(new BorderLayout());


        changePasswordBtn.setBackground(new Color(31, 54, 77));
        changePasswordBtn.setBorderPainted(false);
        changePasswordBtn.setForeground(Color.white);
        changePasswordBtn.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        changePasswordBtn.setFont(new Font("Open Sans", Font.BOLD, 12));
        changePasswordBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        changePasswordBtn.setFocusable(false);
        changePasswordBtn.addActionListener(this);
        changePasswordBtn.addMouseListener(new ButtonMouseOverListener());


        JPanel homeBtnPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        homeBtnPanel.setBackground(new Color(31, 54, 77));

        changePasswordBtn.setToolTipText("Change password");


        homeBtnPanel.add(changePasswordBtn);

        mainPanel.add(homeBtnPanel, BorderLayout.NORTH);
        UserFields userFields = new UserFields();
        mainPanel.add(userFields, BorderLayout.WEST);
        UserTablePanel userTablePanel = new UserTablePanel();
        mainPanel.add(userTablePanel, BorderLayout.CENTER);

        add(toolbar, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        setLocationRelativeTo(null);

        this.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                posX = e.getX();
                posY = e.getY();
            }
        });
        this.addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent evt) {
                setLocation(evt.getXOnScreen() - posX, evt.getYOnScreen() - posY);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {


        if (e.getSource() == changePasswordBtn) {

            int row = UserTablePanel.getTable().getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Please Select the user to change password", "Info", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            changePasswordDialog = new ChangePasswordDialog(ChangePasswordDialog.CURRENT);
        }
    }

    public static ChangePasswordDialog getChangePasswordDialog() {
        return changePasswordDialog;
    }

    public static void setChangePasswordDialog(ChangePasswordDialog changeDialog) {
        changePasswordDialog = changeDialog;
    }

    private static class ButtonMouseOverListener extends MouseAdapter {


        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {
            if (e.getSource() == UserFrame.changePasswordBtn) {
                UserFrame.changePasswordBtn.setForeground(new Color(136, 251, 255));
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if (e.getSource() == UserFrame.changePasswordBtn) {
                UserFrame.changePasswordBtn.setForeground(Color.WHITE);
            }
        }
    }


}
