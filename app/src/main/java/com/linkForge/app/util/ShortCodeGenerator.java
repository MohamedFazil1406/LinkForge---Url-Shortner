package com.linkForge.app.util;


import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class ShortCodeGenerator {

    private static final String CHARS =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public String generateCode() {

        Random random = new Random();

        StringBuilder sb = new StringBuilder();

        for(int i=0;i<6;i++){
            sb.append(
                    CHARS.charAt(
                            random.nextInt(CHARS.length())
                    )
            );
        }

        return sb.toString();
    }
}
