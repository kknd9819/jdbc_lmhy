package com.zz.util.shengyuan;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CamelCaseUtils {
    private static final Logger logger = LoggerFactory.getLogger(CamelCaseUtils.class);

    private static final char SEPARATOR = '_';

    public static String toUnderlineName(String s) {
        if (StringUtil.isEmpty(s)) {
            logger.debug("toUnderlineNamec参数为空");
            return null;
        }

        StringBuilder sb = new StringBuilder();
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            boolean nextUpperCase = true;

            if (i < (s.length() - 1)) {
                nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
            }

            if ((i >= 0) && Character.isUpperCase(c)) {
                if (!upperCase || !nextUpperCase) {
                    if (i > 0)
                        sb.append(SEPARATOR);
                }
                upperCase = true;
            } else {
                upperCase = false;
            }

            sb.append(Character.toLowerCase(c));
        }

        return sb.toString();
    }

    public static String toCamelCase(String s) {
        if (StringUtil.isEmpty(s)) {
            logger.debug("toCamelCase参数为空");
            return null;
        }

        s = s.toLowerCase();

        StringBuilder sb = new StringBuilder(s.length());
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == SEPARATOR) {
                upperCase = true;
            } else if (upperCase) {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    public static String toCapitalizeCamelCase(String s) {
        if (StringUtil.isEmpty(s)) {
            logger.debug("toCapitalizeCamelCase参数为空");
            return null;
        }
        s = toCamelCase(s);
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
}
