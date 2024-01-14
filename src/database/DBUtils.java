package database;

import authentication.user.intructionwithdb.UserTablePanel;
import database.security.Password;
import database.security.Validation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.HashMap;
import java.util.Locale;
import java.util.Vector;

public class DBUtils {

    public static boolean login(String username, String providedPassword) {

        Connection connection;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        String saltValue = "";
        String encrypted_password = "";
        String query = "SELECT `salt`, `password` FROM accounts where accounts.username = '" + username + "';";

        try {
            connection = Connector.getConnection();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                saltValue = resultSet.getString("salt");
                encrypted_password = resultSet.getString("password");
            } else
                return false;

            connection.close();
            preparedStatement.close();

        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage());
        }

        return Password.verify(providedPassword, encrypted_password, saltValue);
    }


    public static boolean verifyCurrentPassword(String currentPassword) {

        Connection connection;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        String saltValue = "";
        String encrypted_password = "";
        String query = "SELECT `salt`, `password` FROM accounts where accounts.id = '" + UserTablePanel.getUserId() + "';";

        try {
            connection = Connector.getConnection();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                saltValue = resultSet.getString("salt");
                encrypted_password = resultSet.getString("password");
            } else
                return false;

            connection.close();
            preparedStatement.close();

        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage());
        }

        return Password.verify(currentPassword, encrypted_password, saltValue);
    }

    public static boolean changePassword(String password) {

        HashMap<String, String> hashedPassword = Password.hash(password);

        String updateQuery = "UPDATE accounts SET salt = ?, password = ? WHERE id = " + UserTablePanel.getUserId();
        Connection connection;
        PreparedStatement preparedStatement;
        try {
            connection = Connector.getConnection();
            preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setString(1, hashedPassword.get("salt"));
            preparedStatement.setString(2, hashedPassword.get("password"));
            preparedStatement.executeUpdate();
            connection.close();
            preparedStatement.close();
            return true;
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage());
            return false;
        }
    }

    public static TableModel resultSetToTableModel(ResultSet rs) {
        try {

            ResultSetMetaData metaData = rs.getMetaData();

            int numberOfColumns = metaData.getColumnCount();
            Vector<String> columnNames = new Vector<String>();

            // Get the column names
            for (int column = 0; column < numberOfColumns; column++) {
                columnNames.addElement(metaData.getColumnLabel(column + 1));
            }

            // Get all rows.
            Vector<Vector<Object>> rows = new Vector<Vector<Object>>();

            while (rs.next()) {
                Vector<Object> newRow = new Vector<Object>();

                for (int i = 1; i <= numberOfColumns; i++) {
                    newRow.addElement(rs.getObject(i));
                }

                rows.addElement(newRow);
            }

            return new DefaultTableModel(rows, columnNames) {
                @Override
                public boolean isCellEditable(int row, int column) {
//                    return column != 0;
                    return true;
                }
            };

        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

}
