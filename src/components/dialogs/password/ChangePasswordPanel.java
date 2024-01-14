package components.dialogs.password;

import authentication.user.UserFrame;
import components.buttons.PrimaryButton;
import components.buttons.ShowButton;
import database.DBUtils;
import database.security.Validation;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;

public class ChangePasswordPanel extends
        JPanel implements ActionListener {


    private final PrimaryButton submitBtn;
    private final JPasswordField passwordField;
    private final JPasswordField confirmPasswordField;
    private final ShowButton showPasswordBtn = new ShowButton("Show");
    private final ShowButton showConfirmBtn = new ShowButton("Show");


    public ChangePasswordPanel() {

        submitBtn = new PrimaryButton("CHANGE");
        PrimaryButton cancelBtn = new PrimaryButton("CANCEL");
        submitBtn.setFont(new Font("Open Sans", Font.BOLD, 14));
        cancelBtn.setFont(new Font("Open Sans", Font.BOLD, 14));

        setLayout(new GridLayout(6, 1, 15, 2));
        setBorder(BorderFactory.createEmptyBorder(20, 50, 50, 50));
        setBackground(new Color(245, 245, 245));

        Font textsFont = new Font("Open Sans", Font.BOLD, 13);

        String[] labelsTexts = new String[]{"PASSWORD", "CONFIRM"};

        Border topMarginForLabel = BorderFactory.createEmptyBorder(15, 0, 0, 0);
        Font labelsFont = new Font("Open Sans", Font.BOLD, 11);
        JPasswordField[] passwordFields = new JPasswordField[2];
        JLabel[] labels = new JLabel[passwordFields.length];
        JPanel[] panels = new JPanel[2];

        for (int i = 0; i < passwordFields.length; i++) {
            passwordFields[i] = new JPasswordField();
            labels[i] = new JLabel(labelsTexts[i]);
            passwordFields[i].setForeground(new Color(124, 124, 124));
            passwordFields[i].setSelectedTextColor(Color.white);
            passwordFields[i].setSelectionColor(new Color(49, 116, 194));
            passwordFields[i].setFont(textsFont);
            passwordFields[i].setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 2));
            passwordFields[i].setEchoChar('•');
            passwordFields[i].addKeyListener(new PasswordKeyListener());

            labels[i].setFont(labelsFont);
            labels[i].setBorder(topMarginForLabel);
            labels[i].setForeground(new Color(168, 168, 168));
            ShowButton[] showButtons = {showPasswordBtn, showConfirmBtn};
            showButtons[i].setBorder(BorderFactory.createEmptyBorder(0, 6, 0, 6));
            showButtons[i].setVisible(false);
            showButtons[i].addMouseListener(new ShowListener());

            panels[i] = new JPanel();
            panels[i].setBorder(BorderFactory.createLineBorder(new Color(212, 219, 227), 1));
            panels[i].setLayout(new BorderLayout());
            panels[i].add(passwordFields[i], BorderLayout.CENTER);
            panels[i].add(showButtons[i], BorderLayout.EAST);
            add(labels[i]);
            add(panels[i]);
        }
        passwordField = passwordFields[0];
        confirmPasswordField = passwordFields[1];

        JPanel whiteSpace = new JPanel();
        whiteSpace.setBackground(new Color(245, 245, 245));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2, 5, 0));
        buttonPanel.add(submitBtn);
        buttonPanel.add(cancelBtn);

        add(whiteSpace);
        add(buttonPanel);

        submitBtn.addActionListener(this);
        cancelBtn.addActionListener(this);

    }


    private class PasswordKeyListener extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            super.keyReleased(e);

            if (e.getSource() == confirmPasswordField) {
                int length = confirmPasswordField.getPassword().length;
                showConfirmBtn.setVisible(length != 0);
            } else {
                int length = passwordField.getPassword().length;
                showPasswordBtn.setVisible(length != 0);
            }

        }
    }

    private class ShowListener extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e);

            if (e.getSource() == showPasswordBtn) {
                passwordField.setEchoChar((char) 0);
                showPasswordBtn.setText("Hide");
                showPasswordBtn.setForeground(Color.white);
            } else {
                confirmPasswordField.setEchoChar((char) 0);
                showConfirmBtn.setText("Hide");
                showConfirmBtn.setForeground(Color.white);
            }

        }

        @Override
        public void mouseReleased(MouseEvent e) {
            super.mouseReleased(e);
            if (e.getSource() == showPasswordBtn) {
                passwordField.setEchoChar('•');
                showPasswordBtn.setText("Show");
                showPasswordBtn.setForeground(Color.black);
                showPasswordBtn.setBackground(Color.white);
            } else {
                confirmPasswordField.setEchoChar('•');
                showConfirmBtn.setText("Show");
                showConfirmBtn.setForeground(Color.black);
                showConfirmBtn.setBackground(Color.white);
            }

        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == submitBtn) {
            String password = String.valueOf(passwordField.getPassword());
            String confirm = String.valueOf(confirmPasswordField.getPassword());

            if (password.length() == 0) {
                JOptionPane.showMessageDialog(this, "Please enter your password");
                return;
            }

            if (!Validation.validate(Validation.PASSWORD, password))
                return;

            if (!password.equals(confirm)) {
                JOptionPane.showMessageDialog(this, "Passwords not matched!", "Change Password", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (DBUtils.changePassword(
                    String.valueOf(passwordField.getPassword()))) {
                JOptionPane.showMessageDialog(this, "Password Successfully Changed!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        UserFrame.getChangePasswordDialog().dispose();
        showPasswordBtn.setVisible(false);
        showConfirmBtn.setVisible(false);
        passwordField.setText("");
        confirmPasswordField.setText("");
    }


    public void setEnterAsDefaultButton(JFrame frame) {
        submitBtn.setAsDefaultButtonTo(frame);
    }

}
