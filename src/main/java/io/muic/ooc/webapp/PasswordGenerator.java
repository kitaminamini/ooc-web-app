package io.muic.ooc.webapp;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordGenerator {

    public static String hash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(10));
    }

    public static boolean verifyHash(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }
}
