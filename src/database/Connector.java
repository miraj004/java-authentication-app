package database;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;

public class Connector {

    private final static String URL = "jdbc:mysql://localhost:3306/au_sys";
    private final static String USERNAME = "root";
    private final static String PASSWORD = "";


    public static Connection getConnection(){
        Connection con = null;
        try{
            con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return con;
    }


}
