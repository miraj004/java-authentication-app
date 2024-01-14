package authentication.user.intructionwithdb;


import authentication.user.UserFields;
import database.Connector;
import database.DBUtils;
import database.security.Password;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UBasicOperation {
    private static final String DIGIT_REGEXP = "^\\d+$";
    private static final String NAME_REGEXP = "[a-zA-Z]{4,}(?: [a-zA-Z]+){0,2}$";
    private static final String ALPHA_NUMERIC_REGEXP = "^[a-zA-Z0-9._ \\-]*$";
    private static final String EMAIL_REGEXP = "^[A-Za-z0-9+_.-]+@(.+)$";
    private static final String USERNAME_REGEXP = "^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){5,20}[a-zA-Z0-9]$";
    private static final String PASSWORD_REGEXP = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
    private static final String PHONE_REGEXP = "^\\d{10}$";
    static Connection connection = Connector.getConnection();
    static PreparedStatement preStatement;
    static ResultSet resultSet;
    static String save_que, limitQue, update_que, getQue, getRowsCountQue, delQue;
    static boolean isValidName, isValidEmail, isValidUsername, isValidPhone, isValidPassword, isConfirmPassword;

    public static JTable table;
    public static int startFrom = 0;
    public static int showRows = UserTableRelatedTools.rowNum;
    static boolean isInserted = false;
    static Pattern ptr;
    static Matcher match;
    static int count_row;
    static long new_rowId;
    static Integer max_id = 0;
    static long totalRecord;

    static Color error_background = new Color(255, 192, 192);
    static Color error_foreground = new Color(199, 0, 0);
    static boolean isEmptyFields = false;

    static private DefaultComboBoxModel<String> model;

    public static void saveUserInfo(String name, String email, String phone, String username, String password, String confirm) {

        if (!isValidAll(name, email, phone, username, password, confirm)) {
            if (!isEmptyFields) {
                UserTablePanel.getErrorPanel().getErrorArea().setVisible(true);
            }
            return;
        }




        findMaxRow();

        new_rowId = max_id + 1;
        count_row = table.getRowCount();
        save_que = "INSERT INTO accounts(name, email, phone, username, salt, password) VALUES (?,?,?,?,?,?)";

        HashMap<String, String> hashedPassword = Password.hash(password);

        try {
            preStatement = connection.prepareStatement(save_que);
            preStatement.setString(1, name);
            preStatement.setString(2, email);
            preStatement.setString(3, phone);
            preStatement.setString(4, username);
            preStatement.setString(5, hashedPassword.get("salt"));
            preStatement.setString(6, hashedPassword.get("password"));
            preStatement.execute();
            isInserted = true;
            dismiss();
            resetTextFields();
        } catch (Exception e) {
            UserTablePanel.getErrorPanel().getErrorArea().setVisible(true);
            UserTablePanel.getErrorPanel().getErrorArea().setText(e.getMessage());
            isInserted = false;
        }
        if (isInserted) {
            if (UserTableRelatedTools.isShowAll || (count_row < showRows))
                UserTablePanel.model.insertRow(count_row, new Object[]{new_rowId, name, username, phone, email});

            max_id += 1;
            UserTableRelatedTools.maxIdInTable.setValueAt(max_id, 0, 0);
            totalRecord += 1;
            UserTableRelatedTools.totalRecordInTable.setValueAt(totalRecord, 0, 0);

            JOptionPane.showMessageDialog(null, "Record successfully added!");

        }

    }

    private static void resetTextFields() {
        for (int i = 0; i < UserFields.getTextField().length; i++) {
            UserFields.getTextField()[i].setText("");
        }
    }

    public static boolean check(String string, String pattern) {
        ptr = Pattern.compile(pattern);
        match = ptr.matcher(string);
        return match.find();
    }


    public static boolean isValidAll(String name, String email, String phone, String username, String password, String confirm) {

        for (int i = 0; i < UserFields.getTextField().length; i++) {
            if (UserFields.getTextField()[i].getText().trim().equals("")) {
                JOptionPane.showMessageDialog(null, "Filling of each field is necessary");
                isEmptyFields = true;
                return false;
            }
        }

        UserTablePanel.getErrorPanel().getErrorArea().setText("\n");

        isValidName = check(name, NAME_REGEXP);
        isValidEmail = check(email, EMAIL_REGEXP);
        isValidPhone = check(phone, PHONE_REGEXP);
        isValidUsername = check(username, USERNAME_REGEXP);
        isValidPassword = check(password, PASSWORD_REGEXP);
        isConfirmPassword = password.equals(confirm);

        if (!isValidName) {
            UserFields.getTextName().setBackground(error_background);
            UserFields.getTextName().setForeground(error_foreground);
            UserTablePanel.getErrorPanel().getErrorArea().append("""
                    Name:
                      A name only can contain letters, allowed of the dot (.) and must be at least for characters.
                    """);
        }

        boolean isDuplicate = isDuplicate("email", email);
        if (isDuplicate) {
            UserFields.getTextEmail().setBackground(error_background);
            UserFields.getTextEmail().setForeground(error_foreground);
            UserTablePanel.getErrorPanel().getErrorArea().append("""
                    Email:
                       The email is already exist.
                    """);

        }

        isDuplicate = isDuplicate("username", username);
        if (isDuplicate) {
            UserFields.getTextUsername().setBackground(error_background);
            UserFields.getTextUsername().setForeground(error_foreground);
            UserTablePanel.getErrorPanel().getErrorArea().append("""
                    Username:
                       The given username is duplicate.
                    """);
        }


        if (!isValidEmail) {
            UserFields.getTextEmail().setBackground(error_background);
            UserFields.getTextEmail().setForeground(error_foreground);
            UserTablePanel.getErrorPanel().getErrorArea().append("""
                    Email:
                      A-Z characters allowed
                      a-z characters allowed
                      0-9 numbers allowed
                      Additionally email may contain only dot(.), dash(-) and underscore(_)
                      Rest all characters are not allowed
                    """);
        }

        if (!isValidPhone) {
            UserFields.getTextPhone().setBackground(error_background);
            UserFields.getTextPhone().setForeground(error_foreground);
            UserTablePanel.getErrorPanel().getErrorArea().append("""
                    Phone:
                       Only then-digit is allowed
                    """);
        }

        if (!isValidUsername) {
            UserFields.getTextUsername().setBackground(error_background);
            UserFields.getTextUsername().setForeground(error_foreground);
            UserTablePanel.getErrorPanel().getErrorArea().append("""
                    Username:
                      Username consists of alphanumeric characters (a-zA-Z0-9), lowercase, or uppercase.
                      Username allowed of the dot (.), underscore (_), and hyphen (-).
                      The dot (.), underscore (_), or hyphen (-) must not be the first or last character.
                      The dot (.), underscore (_), or hyphen (-) does not appear consecutively, e.g., java..regex
                      The number of characters must be between 5 to 20.
                    """);
        }

        if (!isValidPassword) {
            UserFields.getTextPassword().setBackground(error_background);
            UserFields.getTextPassword().setForeground(error_foreground);
            UserTablePanel.getErrorPanel().getErrorArea().append("""
                    Password:
                      Must contain at least one digit [0-9].
                      Must contain at least one lowercase Latin character [a-z].
                      Must contain at least one uppercase Latin character [A-Z].
                      Must contain at least one special character like ! @ # & ( ).
                      Must contain a length of at least 8 characters and a maximum of 20 characters.
                    """);
        }

        if (!isConfirmPassword) {
            UserFields.getTextConfirmPassword().setBackground(error_background);
            UserFields.getTextConfirmPassword().setForeground(error_foreground);
            UserTablePanel.getErrorPanel().getErrorArea().append("""
                    Confirm password:
                      Password not matched!           
                    """);
        }


        isEmptyFields = false;

        return isValidName && isValidEmail && isValidUsername && isValidPhone && isValidPassword && isConfirmPassword && (!isDuplicate);
    }


    public static void findMaxRow() {

        getRowsCountQue = "SELECT MAX(id) AS max_id FROM accounts";
        try {
            preStatement = connection.prepareStatement(getRowsCountQue);
            resultSet = preStatement.executeQuery();
            UserTableRelatedTools.maxIdInTable.setModel(Objects.requireNonNull(DBUtils.resultSetToTableModel(resultSet)));
            max_id = (Integer) UserTableRelatedTools.maxIdInTable.getValueAt(0, 0);
            if (max_id == null) {
                max_id = 0;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    private static boolean isDuplicate(String column, String value) {

        String findDuplicate = "SELECT " + column + " FROM accounts WHERE " + column + " = ?";

        try {
            preStatement = connection.prepareStatement(findDuplicate);
            preStatement.setString(1, value);
            resultSet = preStatement.executeQuery();
            return resultSet.next();
        } catch (Exception e) {
            return false;
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preStatement != null) {
                    preStatement.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }


    public static void refreshTable(JTable table) {
        dismiss();
        UBasicOperation.table = table;

        getQue = "SELECT id, name, username, phone, email FROM accounts ORDER BY " + UserTableRelatedTools.selectSortItem;
        limitQue = "SELECT id, name, username, phone, email FROM accounts ORDER BY " + UserTableRelatedTools.selectSortItem + " LIMIT " + startFrom + ", " + showRows;

        try {
            if (UserTableRelatedTools.isShowAll)
                preStatement = connection.prepareStatement(getQue);
            else
                preStatement = connection.prepareStatement(limitQue);
            resultSet = preStatement.executeQuery();
            table.setModel(Objects.requireNonNull(DBUtils.resultSetToTableModel(resultSet)));
            table.getColumnModel().getColumn(0).setMinWidth(0);
            table.getColumnModel().getColumn(0).setMaxWidth(0);

        } catch (Exception exp) {
            System.out.println("out-of-bound-index error");
        }
    }


    public static void next() {
        if (!UserTableRelatedTools.isShowAll) {

            startFrom += showRows;
            refreshTable(table);

            if (table.getRowCount() == 0) {
                startFrom -= showRows;
                refreshTable(table);
            }

            UserTablePanel.model = (DefaultTableModel) table.getModel();

        }
    }

    public static void preview() {
        if (startFrom > 0 && !UserTableRelatedTools.isShowAll) {
            startFrom -= showRows;
            refreshTable(table);
            UserTablePanel.model = (DefaultTableModel) table.getModel();
        }
    }

    public static void delete() {
        if (table.getSelectedColumn() != -1) {
            String userId = UserTablePanel.getUserId();
            int response = JOptionPane.showConfirmDialog(null,
                    "Do You want to delete the record with (id=" + userId + ")", "Delete Record",
                    JOptionPane.YES_NO_CANCEL_OPTION, 2);
            if (response == JOptionPane.OK_OPTION) {

                try {
                    delQue = "DELETE FROM accounts WHERE id=" + userId;
                    preStatement = connection.prepareStatement(delQue);
                    preStatement.execute();
                    UserTablePanel.model.removeRow(table.getSelectedRow());
                    totalRecord -= 1;
                    UserTableRelatedTools.totalRecordInTable.setValueAt(totalRecord, 0, 0);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select record for delete!", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static boolean editCell(JTable table, String oldValue, int row, int column) {

        String columnName = table.getColumnName(column);
        System.out.println(columnName);
        String newValue;

        newValue = table.getValueAt(row, column).toString().trim();

        if (newValue.equals(oldValue)) {
            return false;
        }

        String regexp = "";

        String name = table.getValueAt(row, UserTablePanel.getColumnNameIndex()).toString();
        String username = table.getValueAt(row, UserTablePanel.getColumnUsernameIndex()).toString();
        String phone = table.getValueAt(row, UserTablePanel.getColumnPhoneIndex()).toString();
        String email = table.getValueAt(row, UserTablePanel.getColumnEmailIndex()).toString();

        switch (columnName) {
            case "name" -> {
                regexp = NAME_REGEXP;
                name = newValue;
            }
            case "username" -> {
                regexp = USERNAME_REGEXP;
                username = newValue;
                if (isDuplicate("username", username)) {
                    JOptionPane.showMessageDialog(null, "Duplicate entry for username");
                    return false;
                }
            }
            case "phone" -> {
                regexp = PHONE_REGEXP;
                phone = newValue;
            }
            case "email" -> {
                regexp = EMAIL_REGEXP;
                email = newValue;
                if (isDuplicate("email", email)) {
                    JOptionPane.showMessageDialog(null, "Email is already exits!");
                    return false;
                }
            }
        }


        if (!check(newValue, regexp)) {
            JOptionPane.showMessageDialog(null, "Invalid Input for " + columnName + " field", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        update_que = "UPDATE accounts SET " + columnName + "='" + newValue + "' WHERE id='" + UserTablePanel.getUserId() + "'";

        try {
            preStatement = connection.prepareStatement(update_que);
            preStatement.executeUpdate();
            table.setValueAt(newValue, row, column);
            UserTablePanel.getSelectedRowLabel().setText("Name: " + name + " | Username: " + username +
                    " | Phone: " + phone + " | Email: " + email);
            dismiss();
            JOptionPane.showMessageDialog(null, "The '" + oldValue + "' " + columnName + " Updated to '" + newValue + "'");
        } catch (Exception exp) {
            UserTablePanel.getErrorPanel().getErrorArea().setVisible(true);
            UserTablePanel.getErrorPanel().getErrorArea().setText(("\n" + exp.getMessage().replace("1", String.valueOf(row))).replace("key", "field") + "\n");
            return false;
        }

        return true;
    }

    public static void dismiss() {

        UserTablePanel.getErrorPanel().getErrorArea().setVisible(false);

    }

    public static void search(String text) {

        String search_que = "SELECT id, name, username, phone, email FROM accounts " +
                "WHERE name LIKE '%" + text + "%' " +
                "OR username LIKE '%" + text + "%' " +
                "OR email LIKE '%" + text + "%' " +
                "OR phone LIKE '%" + text + "%' ";

        try {
            preStatement = connection.prepareStatement(search_que);
            resultSet = preStatement.executeQuery();
            table.setModel(Objects.requireNonNull(DBUtils.resultSetToTableModel(resultSet)));
            table.getColumnModel().getColumn(0).setMinWidth(0);
            table.getColumnModel().getColumn(0).setMaxWidth(0);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public static void findTotalRow() {
        try {
            String count_que = "SELECT COUNT(id) FROM accounts";
            preStatement = connection.prepareStatement(count_que);
            resultSet = preStatement.executeQuery();
            UserTableRelatedTools.totalRecordInTable.setModel(Objects.requireNonNull(DBUtils.resultSetToTableModel(resultSet)));
            totalRecord = (long) UserTableRelatedTools.totalRecordInTable.getValueAt(0, 0);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}
