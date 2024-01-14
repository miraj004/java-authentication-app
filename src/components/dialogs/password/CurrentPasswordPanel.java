package components.dialogs.password;

import authentication.user.UserFrame;
import components.buttons.PrimaryButton;
import components.buttons.ShowButton;
import database.DBUtils;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;

public class CurrentPasswordPanel extends
        JPanel implements ActionListener {
    private final PrimaryButton submitBtn;
    private final PrimaryButton cancelBtn;
    private final JPasswordField passwordField;
    private final ShowButton showButton;


    public CurrentPasswordPanel() {


        submitBtn = new PrimaryButton("NEXT");
        cancelBtn = new PrimaryButton("Cancel");

        submitBtn.setFont(new Font("Open Sans", Font.BOLD, 14));
        cancelBtn.setFont(new Font("Open Sans", Font.BOLD, 14));

        setLayout(new GridLayout(4, 1, 15, 2));
        setBorder(BorderFactory.createEmptyBorder(15, 30, 20, 30));
        setPreferredSize(new Dimension(300, 200));
        setBackground(new Color(245, 245, 245));

        Font textsFont = new Font("Open Sans", Font.BOLD, 13);


        Border topMarginForLabel = BorderFactory.createEmptyBorder(15, 0, 0, 0);
        Font labelsFont = new Font("Open Sans", Font.BOLD, 11);

        showButton = new ShowButton("Show");
        showButton.setBorder(BorderFactory.createEmptyBorder(0, 6, 0, 6));

        JLabel passwordLabel = new JLabel("CURRENT PASSWORD");
        passwordLabel.setFont(labelsFont);
        passwordLabel.setBorder(topMarginForLabel);
        passwordLabel.setForeground(new Color(168, 168, 168));

        passwordField = new JPasswordField();
        passwordField.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 2));
        passwordField.setForeground(new Color(124, 124, 124));
        passwordField.setSelectedTextColor(Color.white);
        passwordField.setSelectionColor(new Color(49, 116, 194));
        passwordField.setFont(textsFont);

        JPanel passwordPanel = new JPanel();
        passwordPanel.setBorder(BorderFactory.createLineBorder(new Color(212, 219, 227), 1));
        passwordPanel.setLayout(new BorderLayout());
        passwordPanel.add(passwordField, BorderLayout.CENTER);
        passwordPanel.add(showButton, BorderLayout.EAST);

        add(passwordLabel);
        add(passwordPanel);

        passwordField.setEchoChar('•');
        showButton.setVisible(false);

        JPanel whiteSpace = new JPanel();
        whiteSpace.setBackground(new Color(245, 245, 245));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2, 5, 0));
        buttonPanel.add(submitBtn);
        buttonPanel.add(cancelBtn);

        add(whiteSpace);
        add(buttonPanel);

        passwordField.addKeyListener(new PasswordKeyListener());
        showButton.addMouseListener(new ShowListener());
        submitBtn.addActionListener(this);
        cancelBtn.addActionListener(this);
    }


    private class PasswordKeyListener extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            super.keyReleased(e);
            int length = passwordField.getPassword().length;
            showButton.setVisible(length != 0);
        }
    }

    private class ShowListener extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e);
            passwordField.setEchoChar((char) 0);
            showButton.setText("Hide");
            showButton.setForeground(Color.white);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            super.mouseReleased(e);
            passwordField.setEchoChar('•');
            showButton.setText("Show");
            showButton.setForeground(Color.black);
            showButton.setBackground(Color.white);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitBtn) {
            if (DBUtils.verifyCurrentPassword(
                    String.valueOf(passwordField.getPassword()))) {
                UserFrame.getChangePasswordDialog().dispose();
                UserFrame.setChangePasswordDialog(new ChangePasswordDialog(ChangePasswordDialog.NEW));
            } else {
                JOptionPane.showMessageDialog(this, "Incorrect password! please try again", "Error", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        } else {
            UserFrame.getChangePasswordDialog().dispose();
        }

        passwordField.setText("");
        showButton.setVisible(false);
    }


    public void setEnterAsDefaultButton(JFrame frame) {
        submitBtn.setAsDefaultButtonTo(frame);
    }

}
