package com.me.budgetbackend.utils;

import java.nio.charset.StandardCharsets;

public class URLDecoderUtils {
    public static String decode(String str) {
        return java.net.URLDecoder.decode(str, StandardCharsets.UTF_8);
    }
}
