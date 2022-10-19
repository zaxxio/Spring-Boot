package com.avaand.app.utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility {

    public static boolean matchPattern(String v, Pattern p) {
        Matcher m = p.matcher(v);
        try {
            if (!m.matches()) {
                return false;
            } else {
                for (int i = 1; i <= 4; i++) {
                    int octet = Integer.parseInt(m.group(i));
                    if (octet > 255) {
                        return false;
                    }
                }
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }

}
