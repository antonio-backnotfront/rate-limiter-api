package io.github.antonio.backnotfront.ratelimiter.utility;

import java.security.SecureRandom;

public class Base62Generator {
    public static final String BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final SecureRandom random = new SecureRandom();

    public static String generate(int length){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++){
            int index = random.nextInt(BASE62.length());
            sb.append(BASE62.charAt(index));
        }
        return sb.toString();
    }
}
