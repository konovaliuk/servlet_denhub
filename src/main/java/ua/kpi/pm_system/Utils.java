package ua.kpi.pm_system;

import ua.kpi.pm_system.exceptions.CredentialsException;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

public class Utils {
    public static String hashPassword(String password) {
        String hash = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            StringBuilder hashedPassword = new StringBuilder(new BigInteger(1, md.digest(password.getBytes())).toString(16));
            while (hashedPassword.length() < 32) {
                hashedPassword.insert(0, "0");
            }
            hash = hashedPassword.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hash;
    }

    public static String validateUsername(String username) throws CredentialsException {
        if (username.length() < 3) {
            throw new CredentialsException("The username should contain at least 3 characters.");
        }
        return username;
    }

    public static String validateFirstName(String firstName) throws CredentialsException {
        if (firstName.length() < 1) {
            throw new CredentialsException("The first name should contain at least 1 characters.");
        }
        return firstName;
    }

    public static String validateEmail(String email) throws CredentialsException {
        Pattern pattern = Pattern.compile("\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\\b");
        if (!pattern.matcher(email).matches()) {
            throw new CredentialsException("Invalid email.");
        }
        return email;
    }

    public static String validatePassword(String password) throws CredentialsException {
        if (password.length() < 6) {
            throw new CredentialsException("The password should contain at least 6 characters.");
        }
        if (!Pattern.compile("[A-Z]").matcher(password).find()) {
            throw new CredentialsException("The password must include at least one uppercase letter.");
        }

        if (!Pattern.compile("[a-z]").matcher(password).find()) {
            throw new CredentialsException("The password must include at least one lowercase letter.");
        }

        if (!Pattern.compile("\\d").matcher(password).find()) {
            throw new CredentialsException("The password must include at least one number.");
        }

        if (!Pattern.compile("[!#$%&'()*+,-.]").matcher(password).find()) {
            throw new CredentialsException("The password must include at least one symbol.");
        }
        return password;
    }
}