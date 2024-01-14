package components.combobox;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import javax.swing.*;
import java.awt.*;

public class ComboRender extends JLabel implements ListCellRenderer {

    public ComboRender() {
        setOpaque(true);
        setFont(new Font("Open Snas", Font.PLAIN, 12));
        setBackground(new Color(255, 255, 255));
        setForeground(new Color(11, 34, 57));
    }

    @Override
    public Component getListCellRendererComponent(JList l, Object val,
                                                  int i, boolean isSelected, boolean cellHasFocus) {
        setText(val.toString());
        return this;
    }
}