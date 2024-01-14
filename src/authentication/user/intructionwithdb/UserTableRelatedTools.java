package authentication.user.intructionwithdb;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class UserTableRelatedTools extends JPanel implements ActionListener {

    static String[] items = {"All", "15", "25", "50"};
    static String[] sort_item = {
            "Name (ASC)", "Name (DESC)",
            "Username (ASC)", "Username (DESC)",
            "Phone (ASC)", "Phone (DESC)",
            "Email (ASC)", "Email (DESC)"
    };

    public static Integer rowNum = Integer.valueOf(items[1]);
    public static boolean isShowAll = false;
    public static JTable maxIdInTable = new JTable();
    public static JTable totalRecordInTable = new JTable();
    public static String selectSortItem = "id";
    static JTextField textFieldSearch = new JTextField();
    JComboBox<String> cbxAmountOfRows = new JComboBox<>(items);
    JComboBox<String> sorted_by_cbx = new JComboBox<>(sort_item);
    JLabel lbNumRows = new JLabel("Number of rows:");
    JLabel lbTotalRows = new JLabel("Total:");
    JLabel lbSortItem = new JLabel("Sort key by:");
    JLabel[] separator = new JLabel[5];


    public UserTableRelatedTools() {

        setBackground(new Color(245, 245, 245));
        setLayout(new FlowLayout(FlowLayout.LEADING));

        totalRecordInTable.setRowHeight(20);
        totalRecordInTable.setBackground(new Color(245, 245, 245));
        totalRecordInTable.setFocusable(false);
        totalRecordInTable.setPreferredSize(new Dimension(23, 20));
        totalRecordInTable.setSelectionBackground(new Color(245, 245, 245));
        totalRecordInTable.setSelectionForeground(Color.black);
        totalRecordInTable.setBorder(null);
        textFieldSearch.setPreferredSize(new Dimension(140, 30));
        textFieldSearch.setToolTipText("Search in this table");

        for(int i=0; i<separator.length; i++) {
            separator[i] = new JLabel("|");
            separator[i].setFont(new Font("Bradley Hand ITC", Font.PLAIN, 30));
            separator[i].setForeground(new Color(200, 200, 200));
        }


        add(UOperationButtons.add_btn);
        add(UOperationButtons.delete_btn);
        add(separator[0]);
        add(lbNumRows);
        add(cbxAmountOfRows);
        add(separator[1]);
        add(UOperationButtons.pre_view_btn);
        add(UOperationButtons.next_btn);
        add(separator[2]);
        add(new JLabel("Filter rows: "));
        add(textFieldSearch);
        add(separator[3]);
        add(lbSortItem);
        add(sorted_by_cbx);
        add(separator[4]);
        add(lbTotalRows);
        add(totalRecordInTable);



        cbxAmountOfRows.setSelectedIndex(1);
        cbxAmountOfRows.addActionListener(this);

        sorted_by_cbx.setSelectedIndex(0);
        sorted_by_cbx.addActionListener(this);


        UBasicOperation.findMaxRow();
        UBasicOperation.findTotalRow();

        textFieldSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                UBasicOperation.search(textFieldSearch.getText());
            }
        });

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cbxAmountOfRows) {

            String select_item = String.valueOf(cbxAmountOfRows.getSelectedItem());

            if (select_item.equals("All")) {
                isShowAll = true;
            } else {
                rowNum = Integer.valueOf(select_item);
                isShowAll = false;
            }

            UBasicOperation.showRows = rowNum;
            UBasicOperation.startFrom = 0;
            System.out.print(totalRecordInTable.getFont().getFontName());

        } else {

            selectSortItem = String.valueOf(sorted_by_cbx.getSelectedItem()).replace("(", "").replace(")", "");
            System.out.println(selectSortItem);
        }

        UBasicOperation.refreshTable(UBasicOperation.table);
        UserTablePanel.model = (DefaultTableModel) UBasicOperation.table.getModel();
    }
}
