package com.example.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Pattern;

public class RegistrationValidator {
    private static final Logger logger = LoggerFactory.getLogger(RegistrationValidator.class);
    private static final Pattern REG_PATTERN = Pattern.compile("^[A-Z]{3}\\d{2}[0-9A-Z]$");

    public boolean isValid(String regNumber)
    {
        if (regNumber == null || !REG_PATTERN.matcher(regNumber.trim().toUpperCase()).matches())
        {
            logger.warn("Felaktig regskylt. AAA-99(A/9) förväntas.");
            return false;
        }
        return true;
    }
}
