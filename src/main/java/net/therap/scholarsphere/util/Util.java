package net.therap.scholarsphere.util;

import net.therap.scholarsphere.model.User;
import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 * @author mehzabinaothoi
 * @since 1/4/24
 */
public class Util {

    public static void hashPassword(User user) {
        String salt = BCrypt.gensalt();

        String hashedPassword = BCrypt.hashpw(user.getPassword(), salt);

        user.setPassword(hashedPassword);
    }

    public static boolean checkPassword(String plaintextPassword, String hashedPassword) {
        return BCrypt.checkpw(plaintextPassword, hashedPassword);
    }
}
