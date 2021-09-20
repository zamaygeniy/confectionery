package by.training.сonfectionery.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordEncoder {

    private PasswordEncoder() {
    }

    public static String encodePassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean checkPassword(String pass, String hash) {
        return BCrypt.checkpw(pass, hash);
    }
}

