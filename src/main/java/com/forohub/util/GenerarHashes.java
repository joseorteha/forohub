package com.forohub.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GenerarHashes {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        
        System.out.println("Hash para 'admin123': " + encoder.encode("admin123"));
        System.out.println("Hash para 'password123': " + encoder.encode("password123"));
        System.out.println("Hash para 'password456': " + encoder.encode("password456"));
    }
}
