package com.example.proyecto2023s1g2.domain;
import org.mindrot.jbcrypt.BCrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class PasswordHasher {
    public String hashPassword(String password) throws NoSuchAlgorithmException {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));
        return hashedPassword;

    }


    public boolean verifyPassword(String password, String hashedPassword) throws NoSuchAlgorithmException {
        return BCrypt.checkpw(password, hashedPassword);
    }

}


