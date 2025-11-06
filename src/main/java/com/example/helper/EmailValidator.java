package com.example.helper;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.util.regex.Pattern;

public class EmailValidator {
    private static final Logger logger = LoggerFactory.getLogger(EmailValidator.class);

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );

    public boolean isValid(String email) {

        if (email == null || email.trim().isEmpty()) {
            logger.warn("Ogiltig email: tom sträng");
            return false;
        }


        boolean matches = EMAIL_PATTERN.matcher(email.trim()).matches();

        if (matches) {
            logger.info("Email godkänd: {}", email);
        } else {
            logger.warn("Ogiltig email-format: {}", email);
        }

        return matches;
    }
}
