package com.example.dsa2_project.Database;

public class InputValidator {

    public static boolean isSafe(String input) {
        if (input == null || input.isEmpty()) return false;

        // check for special characters
        String[] dangerousChars = {"'", "\"", ";", "--", "#", "/*", "*/", "="};
        for (String c : dangerousChars) {
            if (input.contains(c)) return false;
        }

        // check for SQL keywords
        String[] sqlKeywords = {"SELECT", "INSERT", "DELETE", "DROP",
                "UPDATE", "UNION", "ALTER", "EXEC",
                "OR", "AND", "WHERE", "FROM"};
        String upperInput = input.toUpperCase();
        for (String keyword : sqlKeywords) {
            if (upperInput.contains(keyword)) return false;
        }

        // check for known injection patterns
        String[] patterns = {"OR 1=1", "OR '1'='1", "UNION SELECT",
                "DROP TABLE", "1=1", "1 = 1"};
        for (String pattern : patterns) {
            if (upperInput.contains(pattern.toUpperCase())) return false;
        }

        return true;
    }

    // restricts input length
    public static boolean isValidLength(String input, int maxLength) {
        return input != null && input.length() <= maxLength;
    }


    // password must be at least 3 characters
    public static boolean isValidPassword(String input) {
        return input != null && input.length() >= 3;
    }
}
