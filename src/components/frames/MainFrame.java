package components.frames;

import components.Toolbar;
import components.lookandfeel.NimLookAndFeel;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainFrame extends JFrame {

    int posX=0,posY=0;

    JPanel mainPanel;

    public static JButton homeBtn;

    public MainFrame() {
        NimLookAndFeel.setNimLookAndFeel(this);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
//        setSize(1280, 680);
        getContentPane().setBackground(Color.white);

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setSize(800, 700);

        Toolbar toolbar = new Toolbar("");

        JPanel bottomPanel = new JPanel(new BorderLayout());
        JPanel linePanel = new JPanel();
        linePanel.setBackground(new Color(43, 196, 138));
        bottomPanel.setBackground(new Color(11+20, 34+20, 57+20));
        bottomPanel.setPreferredSize(new Dimension(0, 50));
        bottomPanel.add(linePanel, BorderLayout.NORTH);

        mainPanel = new JPanel();
        mainPanel.setBackground(Color.white);
        mainPanel.setLayout(new BorderLayout());

        homeBtn = new JButton(new ImageIcon("icons\\home.png"));
        homeBtn.setBackground(new Color(11, 34, 57));

        JPanel homeBtnPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        homeBtnPanel.setBackground(new Color(11+20, 34+20, 57+20));
        homeBtn.setBorder(null);
        homeBtnPanel.add(homeBtn);
        homeBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        homeBtn.setToolTipText("Go To Home");

        mainPanel.add(homeBtnPanel, BorderLayout.NORTH);

        add(toolbar, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        setLocationRelativeTo(null);
        setVisible(true);

    }



    public void addPanels (JPanel fieldsPanel, JPanel tablePanel) {
        mainPanel.add(fieldsPanel, BorderLayout.WEST);
        mainPanel.add(tablePanel, BorderLayout.CENTER);
    }


}
