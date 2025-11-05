package com.example.helper;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.util.regex.Pattern;

public class EmailValidator {
    private static final Logger logger = LoggerFactory.getLogger(EmailValidator.class);

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$"
    );

    public boolean isValid(String email)
    {
        if (email== null|| email.trim().isEmpty()){
            logger.warn("Ogiltig email: {}", email);
            return false;
        }

        logger.info("Email godkänd: {}", email);
        return true;
    }
}
