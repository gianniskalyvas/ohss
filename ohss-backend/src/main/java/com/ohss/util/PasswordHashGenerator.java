package com.ohss.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Utility to generate BCrypt password hashes for database insertion
 * Run this class to generate hashed passwords
 */
public class PasswordHashGenerator {
    
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        
        // Generate hash for "giannis"
        String password = "giannis";
        String hash = encoder.encode(password);
        
        System.out.println("=== PASSWORD HASH GENERATOR ===");
        System.out.println("Plain password: " + password);
        System.out.println("Hashed password: " + hash);
    }
}
