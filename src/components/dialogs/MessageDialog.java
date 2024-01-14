package components.dialogs;

import components.buttons.PrimaryButton;

import javax.swing.*;
import java.awt.*;

public class MessageDialog {

    private static JDialog dialog;
    private static final PrimaryButton button = new PrimaryButton("OK");

    public static final int ERROR_MESSAGE = 0;
    public static final int WARNING_MESSAGE = 1;
    public static final int INFO_MESSAGE = 2;
    public static final int PLAIN_MESSAGE = 3;
    private static ImageIcon icon;


    public static void showMessageDialog(String title, String message, int type) {
        JOptionPane optionPane = new JOptionPane(getShowMessagePanel(message),
                JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION, null,
                new Object[]{}, null);
        dialog = optionPane.createDialog(title);
        dialog.setIconImage(getIcon(type).getImage());
        button.setAsDefaultButtonTo(dialog);
        dialog.setVisible(true);
    }


    private static JPanel getShowMessagePanel(String message) {

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(0, 0));
        panel.setBackground(Color.white);

        JPanel panelButton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelButton.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        panelButton.setBackground(new Color(241, 241, 241));

        // Message
        JTextArea messagePane = new JTextArea();
        messagePane.setText(message);

        messagePane.setBorder(BorderFactory.createEmptyBorder(15, 10, 0, 10));
        messagePane.setFont(new Font("Noto Sans", Font.PLAIN, 12));
        messagePane.setForeground(new Color(77, 77, 77));
        messagePane.setBackground(new Color(241, 241, 241));
        messagePane.setEditable(false);

        // Button
        button.setPreferredSize(new Dimension(120, 32));
        button.addActionListener(e -> dialog.dispose());

        panelButton.add(button);

        panel.add(messagePane, BorderLayout.CENTER);
        panel.add(panelButton, BorderLayout.SOUTH);
        return panel;
    }

    private static ImageIcon getIcon(int type) {


        switch (type) {
            case ERROR_MESSAGE ->
                    icon = new ImageIcon("C:\\Users\\MotiullahMiraj\\Downloads\\icons8-error-message-53.png");
            case WARNING_MESSAGE -> icon = new ImageIcon("C:\\Users\\MotiullahMiraj\\Downloads\\icons8-warning-48.png");
            case INFO_MESSAGE -> icon = new ImageIcon("C:\\Users\\MotiullahMiraj\\Downloads\\icons8-info-48.png");
            case PLAIN_MESSAGE -> icon = null;
        }

        return icon;
    }

}
