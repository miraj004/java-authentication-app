package authentication.login;

import components.Toolbar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;


public class Login extends JFrame {

    public Login() {

        LoginContent loginContent = new LoginContent();
        loginContent.setEnterAsDefaultButton(this);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 500);
        setResizable(false);
        getContentPane().setBackground(Color.white);

        Toolbar toolbar = new Toolbar("Login");

        JPanel bottomPanel = new JPanel(new BorderLayout());
        JPanel linePanel = new JPanel();
        linePanel.setBackground(new Color(0, 135, 187));
        bottomPanel.setBackground(new Color(31, 54, 77));
        bottomPanel.setPreferredSize(new Dimension(0, 50));
        bottomPanel.add(linePanel, BorderLayout.NORTH);

        add(toolbar, BorderLayout.NORTH);
        add(loginContent, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);

    }


}
