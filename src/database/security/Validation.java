package database.security;

import components.dialogs.MessageDialog;

import javax.swing.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {

    private static final String EMAIL_REGEXP = "^[A-Za-z0-9+_.-]+@(.+)$";
    private static final String USERNAME_REGEXP = "^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){5,20}[a-zA-Z0-9]$";
    private static final String PASSWORD_REGEXP = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
    private static final String PHONE_REGEXP = "^\\d{10}$";
    public static final int PHONE = 0;
    public static final int EMAIL = 1;
    public static final int USERNAME = 2;
    public static final int PASSWORD = 3;
    public static final int CONFIRM = 4;

    private static Matcher matcher;

//    At least 8 chars
//    Contains at least one digit
//    Contains at least one lower alpha char and one upper alpha char
//    Contains at least one char within a set of special chars (@#%$^ etc.)
//    Does not contain space, tab, etc.


    public static boolean validate(int type, String string) {

        boolean isValid = checkRegExp(type, string);

        if (!isValid)
            showValidateMessageDialog(type);

        return isValid;
    }

    public static boolean checkConfirmPassword(String password, String confirm) {

        boolean isValid;

        if (!(isValid = password.equals(confirm)))
            showValidateMessageDialog(CONFIRM);

        return isValid;
    }



    private static boolean checkRegExp(int type, String string) {

        Pattern pattern;

        switch (type) {
            case PHONE -> {
                pattern = Pattern.compile(PHONE_REGEXP);
                matcher = pattern.matcher(string);
            }
            case EMAIL -> {
                pattern = Pattern.compile(EMAIL_REGEXP);
                matcher = pattern.matcher(string);
            }
            case USERNAME -> {
                pattern = Pattern.compile(USERNAME_REGEXP);
                matcher = pattern.matcher(string);
            }
            case PASSWORD -> {
                pattern = Pattern.compile(PASSWORD_REGEXP);
                matcher = pattern.matcher(string);
            }
        }
        return matcher.find();
    }


    private static void showValidateMessageDialog(int type) {

        String message;
        String title;
        switch (type) {
            case PHONE -> {
                title = "Phone";
                message = """
                        Only Ten-Digit Number
                        """;
            }

            case EMAIL -> {
                title ="Email";
                message = """
                        1. A-Z characters allowed
                        2. a-z characters allowed
                        3. 0-9 numbers allowed
                        4. Additionally email may contain only dot(.), dash(-) and underscore(_)
                        5. Rest all characters are not allowed
                        """;
            }

            case USERNAME -> {
                title ="Username";
                message = """
                        1. Username consists of alphanumeric characters (a-zA-Z0-9), lowercase, or uppercase.
                        2. Username allowed of the dot (.), underscore (_), and hyphen (-).
                        3. The dot (.), underscore (_), or hyphen (-) must not be the first or last character.
                        4. The dot (.), underscore (_), or hyphen (-) does not appear consecutively, e.g., java..regex
                        5. The number of characters must be between 5 to 20.
                        """;
            }

            case PASSWORD -> {
                title = "Password";
                message = """
                        1. Password must contain at least one digit [0-9].
                        2. Password must contain at least one lowercase Latin character [a-z].
                        3. Password must contain at least one uppercase Latin character [A-Z].
                        4. Password must contain at least one special character like ! @ # & ( ).
                        5. Password must contain a length of at least 8 characters and a maximum of 20 characters.
                        """;
            }
            default -> {
                title = "Confirm";
                message = """
                        Passwords not matched!
                        """;
            }
        }
        JOptionPane.showMessageDialog( null, message, title, JOptionPane.WARNING_MESSAGE);
    }

}
