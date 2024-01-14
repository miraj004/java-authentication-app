package authentication.user.intructionwithdb;

import components.ErrorPanel;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UserTablePanel extends
        JPanel implements CellEditorListener {

    public static DefaultTableModel model;
    private static int row, column;
    private static final JTable table = new JTable();
    JScrollPane scrollPane;
    String old_value;
    JPanel tableErrorPanel;
    JPanel btn_opr_panel;
    private static String userId;

    private static final int columnIdIndex = 0;
    private static int columnNameIndex = 1, columnUsernameIndex = 2, columnPhoneIndex = 3, columnEmailIndex = 4;
    private final static JLabel selectedRowLabel = new JLabel("Not Selected");
    private final static JPanel mainPanel = new JPanel(new BorderLayout());
    static ErrorPanel errorPanel = new ErrorPanel();

    public UserTablePanel() {
        setBorder(BorderFactory.createLineBorder(Color.lightGray));
        tableErrorPanel = new JPanel();
        btn_opr_panel = new JPanel();

        setMinimumSize(new Dimension(400, 200));
        setLayout(new BorderLayout(0, 0));

        UBasicOperation.refreshTable(table);
        model = (DefaultTableModel) table.getModel();

        table.setSelectionBackground(new Color(255, 238, 211));
        table.setSelectionForeground(Color.black);

        table.getTableHeader().setFont(new Font("Open Sans", Font.BOLD, 14));
        table.getTableHeader().setForeground(new Color(15, 47, 70));

        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);

        table.setRowHeight(30);
        table.getTableHeader().setCursor(new Cursor(Cursor.MOVE_CURSOR));

        scrollPane = new JScrollPane(table);
        table.setFocusable(false);
        table.setIntercellSpacing(new Dimension(2, 2));

        selectedRowLabel.setBackground(new Color(255, 248, 209));

        selectedRowLabel.setBorder(new CompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(4, 10, 4, 2)));
        selectedRowLabel.setOpaque(true);
        selectedRowLabel.setToolTipText("Click To Dismiss");
        selectedRowLabel.setVisible(false);

        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.add(errorPanel.getErrorArea(), BorderLayout.CENTER);
        wrapperPanel.add(selectedRowLabel, BorderLayout.SOUTH);

        UserTableRelatedTools tools = new UserTableRelatedTools();

        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(wrapperPanel, BorderLayout.SOUTH);
        add(mainPanel, BorderLayout.CENTER);
        add(tools, BorderLayout.SOUTH);

        table.getCellEditor(row, column).addCellEditorListener(this);
        table.addMouseListener(new TableClickListener());
        table.getColumnModel().addColumnModelListener(new TableColumnListener());
        selectedRowLabel.addMouseListener(new LabelVisibleListener());
    }

    private static class TableColumnListener implements TableColumnModelListener {
        @Override
        public void columnAdded(TableColumnModelEvent e) {

        }
        @Override
        public void columnRemoved(TableColumnModelEvent e) {

        }
        @Override
        public void columnMoved(TableColumnModelEvent e) {
            String columnName = table.getColumnName(e.getFromIndex());
            switch (columnName) {
                case "name" -> columnNameIndex = e.getFromIndex();
                case "username" -> columnUsernameIndex = e.getFromIndex();
                case "phone" -> columnPhoneIndex = e.getFromIndex();
                case "email" -> columnEmailIndex = e.getFromIndex();
            }
        }
        @Override
        public void columnMarginChanged(ChangeEvent e) {

        }
        @Override
        public void columnSelectionChanged(ListSelectionEvent e) {

        }
    }

    private static class LabelVisibleListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            selectedRowLabel.setVisible(false);
        }
    }

    private class TableClickListener extends MouseAdapter {
        public void mouseClicked(MouseEvent e) {
            row = table.getSelectedRow();
            column = table.getSelectedColumn();
            old_value = table.getValueAt(row, column).toString();
            userId = table.getValueAt(row, columnIdIndex).toString();
            String name = table.getValueAt(row, columnNameIndex).toString();
            String username = table.getValueAt(row, columnUsernameIndex).toString();
            String phone = table.getValueAt(row, columnPhoneIndex).toString();
            String email = table.getValueAt(row, columnEmailIndex).toString();
            selectedRowLabel.setText("Name: " + name + " | Username: "
                    + username + " | Phone: " + phone + " | Email: " + email);
            selectedRowLabel.setVisible(true);
        }
    }


    @Override
    public void editingStopped(ChangeEvent e) {
       if (!UBasicOperation.editCell(table, old_value, row, column))
           table.setValueAt(old_value, row, column);
    }

    @Override
    public void editingCanceled(ChangeEvent e) {}

    public static int getColumnNameIndex() {
        return columnNameIndex;
    }

    public static int getColumnUsernameIndex() {
        return columnUsernameIndex;
    }

    public static int getColumnPhoneIndex() {
        return columnPhoneIndex;
    }

    public static int getColumnEmailIndex() {
        return columnEmailIndex;
    }

    public static int getColumnIdIndex() {
        return columnIdIndex;
    }

    public static ErrorPanel getErrorPanel() {
        return errorPanel;
    }

    public static JLabel getSelectedRowLabel() {
        return selectedRowLabel;
    }

    public static JTable getTable() {
        return table;
    }
    public static String getUserId() {
        return userId;
    }
}
