package authentication.user.intructionwithdb;


import authentication.user.UserFields;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UOperationButtons implements ActionListener {

    static JButton add_btn, pre_view_btn, next_btn, delete_btn;
    String[] btn_txt = {"Insert", "<<", ">>", "Delete"};
    String[] tooltip = {"Add", "Preview", "Next", "Delete"};
    JButton[] buttons = new JButton[btn_txt.length];

    public UOperationButtons() {
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton(btn_txt[i]);
            if (btn_txt[i].equals("Delete")) {
                buttons[i].setBackground(new Color(194, 71, 71));
                buttons[i].setForeground(Color.white);
            }
            buttons[i].setToolTipText(tooltip[i]);
            buttons[i].addActionListener(this);
        }
        add_btn = buttons[0];
        pre_view_btn = buttons[1];
        next_btn = buttons[2];
        delete_btn = buttons[3];
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == add_btn) {
            UBasicOperation.saveUserInfo(
                    UserFields.getTextName().getText().trim(),
                    UserFields.getTextEmail().getText().trim(),
                    UserFields.getTextPhone().getText().trim(),
                    UserFields.getTextUsername().getText().trim(),
                    UserFields.getTextPassword().getText().trim(),
                    UserFields.getTextConfirmPassword().getText()
            );
        }

        if (e.getSource() == pre_view_btn) {
            UBasicOperation.preview();
        }

        if (e.getSource() == next_btn) {
            UBasicOperation.next();
        }

        if (e.getSource() == delete_btn) {
            UBasicOperation.delete();
        }


    }
}
