package com.bionic.services;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.security.SecureRandom;
import java.util.Random;

/**
 * Generates random OTP tokens using the letters A-Z and digits 0-9.
 */
@Service
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class OtpGenerator {
    private static final char[] CHARS = {'A', 'a', 'B', 'b', 'C', 'c', 'D', 'd', 'E', 'e', 'F',
            'f', 'G', 'g', 'H', 'h', 'I', 'i', 'J', 'j', 'K', 'k', 'L', 'l',
            'M', 'm', 'N', 'n', 'O', 'o', 'P', 'p', 'Q', 'q', 'R', 'r', 'S', 's',
            'T', 't', 'U', 'u', 'V', 'v', 'W', 'w', 'X', 'x', 'Y', 'y', 'Z', 'z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    private Random rand;
    private int length;

    public OtpGenerator() {
        this.length = 12;
        this.rand = new SecureRandom();
    }

    public String generateToken() {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CHARS[rand.nextInt(CHARS.length)]);
        }
        return sb.toString();
    }
}
