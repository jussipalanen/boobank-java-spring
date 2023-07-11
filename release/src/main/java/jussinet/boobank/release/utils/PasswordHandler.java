package jussinet.boobank.release.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordHandler {
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
