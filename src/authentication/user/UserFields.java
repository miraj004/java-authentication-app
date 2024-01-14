package authentication.user;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class UserFields extends JPanel implements FocusListener {

    private static final JTextField[] textFields = new JTextField[6];
    private static JTextField textName, textEmail, textUsername, textPassword, textConfirmPassword, textPhone;

    public static JTextField[] getTextField() {
        return textFields;
    }

    public UserFields() {


        setLayout(new GridLayout(12, 1, 15, 2));
        setBorder(BorderFactory.createEmptyBorder(20, 50, 50, 50));
        setPreferredSize(new Dimension(300, 0));
        setBackground(new Color(245, 245, 245));

        Font textsFont = new Font("Open Sans", Font.BOLD, 13);
        String[] labelsTexts = new String[]{"Name", "EMAIL", "PHONE", "USERNAME", "PASSWORD", "CONFIRM"};
        Border topMarginForLabel = BorderFactory.createEmptyBorder(15, 0, 0, 0);
        Font labelsFont = new Font("Open Sans", Font.BOLD, 12);
        JLabel[] labels = new JLabel[textFields.length];

        for (int i = 0; i < textFields.length; i++) {
            textFields[i] = i >= textFields.length - 2 ? new JPasswordField() : new JTextField();
            labels[i] = new JLabel(labelsTexts[i]);
            CompoundBorder customBorder = new CompoundBorder(
                    BorderFactory.createLineBorder(new Color(212, 219, 227), 1),
                    BorderFactory.createEmptyBorder(4, 4, 4, 2));
            textFields[i].setBorder(customBorder);
            textFields[i].setForeground(new Color(101, 101, 101));
            textFields[i].setSelectedTextColor(Color.white);
            textFields[i].setSelectionColor(new Color(49, 116, 194));
            textFields[i].setFont(textsFont);
            labels[i].setFont(textsFont);
            labels[i].setFont(labelsFont);
            labels[i].setBorder(topMarginForLabel);
            labels[i].setForeground(new Color(111, 111, 111));
            textFields[i].addFocusListener(this);
            add(labels[i]);
            add(textFields[i]);
        }

        textName = textFields[0];
        textEmail = textFields[1];
        textPhone = textFields[2];
        textUsername = textFields[3];
        textPassword = textFields[4];
        textConfirmPassword = textFields[5];

    }

    public static JTextField getTextName() {
        return textName;
    }


    public static JTextField getTextEmail() {
        return textEmail;
    }


    public static JTextField getTextUsername() {
        return textUsername;
    }


    public static JTextField getTextPassword() {
        return textPassword;
    }


    public static JTextField getTextConfirmPassword() {
        return textConfirmPassword;
    }


    public static JTextField getTextPhone() {
        return textPhone;
    }


    @Override
    public void focusGained(FocusEvent e) {
        for (JTextField textField :
                textFields) {
            if (textField == e.getSource()) {
                textField.setForeground(new Color(101, 101, 101));
                textField.setBackground(Color.white);
            }
        }
    }

    @Override
    public void focusLost(FocusEvent e) {

    }
}