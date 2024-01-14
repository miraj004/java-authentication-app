package authentication.login;

import authentication.user.UserFrame;
import components.buttons.PrimaryButton;
import components.buttons.ShowButton;
import components.dialogs.MessageDialog;
import database.DBUtils;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.event.*;

public class LoginContent extends
        JPanel implements ActionListener {

    private final PrimaryButton submitBtn;
    private final JPasswordField passwordField;
    private final JTextField usernameField;
    private final ShowButton showButton;

    public LoginContent() {


        submitBtn = new PrimaryButton("LOGIN");

        setLayout(new GridLayout(6, 1, 15, 2));
        setBorder(BorderFactory.createEmptyBorder(20, 50, 50, 50));
        setBackground(Color.white);

        Font textsFont = new Font("Open Sans", Font.BOLD, 13);

        String[] labelsTexts = new String[]{"USERNAME", "PASSWORD"};

        Border topMarginForLabel = BorderFactory.createEmptyBorder(15, 0, 0, 0);
        Font labelsFont = new Font("Open Sans", Font.BOLD, 11);
        JTextField[] textFields = new JTextField[2];
        JLabel[] labels = new JLabel[textFields.length];
        showButton = new ShowButton("Show");

        for (int i = 0; i < textFields.length; i++) {
            textFields[i] = i == 0 ? new JTextField() : new JPasswordField();
            labels[i] = new JLabel(labelsTexts[i]);
            CompoundBorder customBorder = new CompoundBorder(
                    BorderFactory.createLineBorder(new Color(212, 219, 227), 1),
                    BorderFactory.createEmptyBorder(0, 4, 0, 2));
            textFields[i].setBorder(customBorder);
            textFields[i].setForeground(new Color(124, 124, 124));
            textFields[i].setSelectedTextColor(Color.white);
            textFields[i].setSelectionColor(new Color(49, 116, 194));
            textFields[i].setFont(textsFont);
            labels[i].setFont(labelsFont);
            labels[i].setBorder(topMarginForLabel);
            labels[i].setForeground(new Color(168, 168, 168));
            add(labels[i]);
            if (i == textFields.length - 1) {
                textFields[i].setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 2));
                showButton.setBorder(BorderFactory.createEmptyBorder(0, 6, 0, 6));
                JPanel panel = new JPanel();
                panel.setBorder(BorderFactory.createLineBorder(new Color(212, 219, 227), 1));
                panel.setLayout(new BorderLayout());
                panel.add(textFields[i], BorderLayout.CENTER);
                panel.add(showButton, BorderLayout.EAST);
                add(panel);
            } else {
                add(textFields[i]);
            }
        }
        usernameField = textFields[0];
        passwordField = (JPasswordField) textFields[1];
        passwordField.setEchoChar('•');
        showButton.setVisible(false);

        JPanel whiteSpace = new JPanel();
        whiteSpace.setBackground(Color.white);

        add(whiteSpace);
        add(submitBtn);

        passwordField.addKeyListener(new PasswordKeyListener());
        showButton.addMouseListener(new ShowListener());
        submitBtn.addActionListener(this);

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
            showButton.setBackground(Color.white);
            showButton.setForeground(Color.black);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {


        int passLength = usernameField.getText().length();
        int usernameLength = passwordField.getPassword().length;

        if (passLength == 0 || usernameLength == 0) {
            MessageDialog.showMessageDialog("Info", "Please enter both username and password!", MessageDialog.INFO_MESSAGE);
            return;
        }


        if (DBUtils.login(
                usernameField.getText(),
                String.valueOf(passwordField.getPassword()))) {

            // Find the top-level parent (usually the JFrame)
            Container container = this;
            while (!(container instanceof JFrame) && container != null) {
                container = container.getParent();
            }

            // Close the current frame
            if (container != null) {
                ((JFrame) container).dispose();
            }

            new UserFrame().setVisible(true);
        } else
            MessageDialog.showMessageDialog("Error", "wrong username or/and password!", MessageDialog.INFO_MESSAGE);

    }


    public void setEnterAsDefaultButton(JFrame frame) {
        submitBtn.setAsDefaultButtonTo(frame);
    }

}
