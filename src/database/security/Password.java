package database.security;

import java.util.HashMap;

public class Password {

    private static final HashMap<String, String> hashMap = new HashMap<>();
    private static final int length = 30;

    public static HashMap<String, String> hash(String password) {
        String saltValue = PassBasedEnc.getSaltvalue(length);
        String encrypted_password = PassBasedEnc.generateSecurePassword(password, saltValue);
        hashMap.put("salt", saltValue);
        hashMap.put("password", encrypted_password);
        return hashMap;
    }

    public static boolean verify(String password, String encryptedPassword, String saltValue) {
        return PassBasedEnc.verifyUserPassword(password, encryptedPassword, saltValue);
    }



}
