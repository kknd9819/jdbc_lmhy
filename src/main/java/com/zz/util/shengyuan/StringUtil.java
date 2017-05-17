package com.zz.util.shengyuan;

import org.apache.commons.lang.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class StringUtil {
    private static final String RMB = "￥";

    public StringUtil() {
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isBlank(String str) {
        int strLen;
        if(str != null && (strLen = str.length()) != 0) {
            for(int i = 0; i < strLen; ++i) {
                if(!Character.isWhitespace(str.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    public static String trim(String str) {
        return str == null?null:str.trim();
    }

    public static String trimToNull(String str) {
        String ts = trim(str);
        return isEmpty(ts)?null:ts;
    }

    public static String trimToEmpty(String str) {
        return str == null?"":str.trim();
    }

    public static boolean equals(String str1, String str2) {
        return str1 == null?str2 == null:str1.equals(str2);
    }

    public static boolean equalsIgnoreCase(String str1, String str2) {
        return str1 == null?str2 == null:str1.equalsIgnoreCase(str2);
    }

    public static String replace(String text, String searchString, String replacement) {
        return replace(text, searchString, replacement, -1);
    }

    public static String replace(String text, String searchString, String replacement, int max) {
        if(!isEmpty(text) && !isEmpty(searchString) && replacement != null && max != 0) {
            int start = 0;
            int end = text.indexOf(searchString, start);
            if(end == -1) {
                return text;
            } else {
                int replLength = searchString.length();
                int increase = replacement.length() - replLength;
                increase = increase < 0?0:increase;
                increase *= max < 0?16:(max > 64?64:max);

                StringBuffer buf;
                for(buf = new StringBuffer(text.length() + increase); end != -1; end = text.indexOf(searchString, start)) {
                    buf.append(text.substring(start, end)).append(replacement);
                    start = end + replLength;
                    --max;
                    if(max == 0) {
                        break;
                    }
                }

                buf.append(text.substring(start));
                return buf.toString();
            }
        } else {
            return text;
        }
    }

    public static String remove(String str, String remove) {
        return !isEmpty(str) && !isEmpty(remove)?replace(str, remove, "", -1):str;
    }

    public static String abbr(String str, int maxWidth) {
        return abbreviate(str, maxWidth);
    }

    public static String abbreviate(String str, int maxWidth) {
        return abbreviate(str, 0, maxWidth);
    }

    private static String abbreviate(String str, int offset, int maxWidth) {
        if(str == null) {
            return null;
        } else if(maxWidth < 4) {
            throw new IllegalArgumentException("maxWidth的值最小必须为4");
        } else if(str.length() <= maxWidth) {
            return str;
        } else {
            if(offset > str.length()) {
                offset = str.length();
            }

            if(str.length() - offset < maxWidth - 3) {
                offset = str.length() - (maxWidth - 3);
            }

            if(offset <= 4) {
                return str.substring(0, maxWidth - 3) + "...";
            } else if(maxWidth < 7) {
                throw new IllegalArgumentException("Minimum abbreviation width with offset is 7");
            } else {
                return offset + (maxWidth - 3) < str.length()?"..." + abbreviate(str.substring(offset), maxWidth - 3):"..." + str.substring(str.length() - (maxWidth - 3));
            }
        }
    }

    public static final byte[] compress(String str) {
        if(str == null) {
            return null;
        } else {
            ByteArrayOutputStream out = null;
            ZipOutputStream zout = null;

            byte[] compressed;
            try {
                out = new ByteArrayOutputStream();
                zout = new ZipOutputStream(out);
                zout.putNextEntry(new ZipEntry("0"));
                zout.write(str.getBytes());
                zout.closeEntry();
                compressed = out.toByteArray();
            } catch (IOException var17) {
                compressed = null;
            } finally {
                if(zout != null) {
                    try {
                        zout.close();
                    } catch (IOException var16) {
                        ;
                    }
                }

                if(out != null) {
                    try {
                        out.close();
                    } catch (IOException var15) {
                        ;
                    }
                }

            }

            return compressed;
        }
    }

    public static final String decompress(byte[] compressed) {
        if(compressed == null) {
            return null;
        } else {
            ByteArrayOutputStream out = null;
            ByteArrayInputStream in = null;
            ZipInputStream zin = null;

            String decompressed;
            try {
                out = new ByteArrayOutputStream();
                in = new ByteArrayInputStream(compressed);
                zin = new ZipInputStream(in);
                zin.getNextEntry();
                byte[] buffer = new byte[1024];
                boolean var6 = true;

                int offset;
                while((offset = zin.read(buffer)) != -1) {
                    out.write(buffer, 0, offset);
                }

                decompressed = out.toString();
            } catch (IOException var23) {
                decompressed = null;
            } finally {
                if(zin != null) {
                    try {
                        zin.close();
                    } catch (IOException var22) {
                        ;
                    }
                }

                if(in != null) {
                    try {
                        in.close();
                    } catch (IOException var21) {
                        ;
                    }
                }

                if(out != null) {
                    try {
                        out.close();
                    } catch (IOException var20) {
                        ;
                    }
                }

            }

            return decompressed;
        }
    }

    public static String replaceParam(String templateStr, Map<String, String> values) {
        List<String> paramList = new LinkedList();

        int pos2;
        for(int pos1 = templateStr.indexOf("${"); pos1 >= 0; pos1 = templateStr.indexOf("${", pos2)) {
            pos2 = templateStr.indexOf("}", pos1 + 2);
            paramList.add(templateStr.substring(pos1 + 2, pos2));
        }

        String result = templateStr;
        Iterator i$ = paramList.iterator();

        while(i$.hasNext()) {
            String param = (String)i$.next();
            String value = (String)values.get(param.trim());
            if(value != null) {
                result = StringUtils.replace(result, "${" + param + "}", value);
            } else {
                result = StringUtils.replace(result, "${" + param + "}", "");
            }
        }

        return result;
    }

    public static String handleAmount(Integer amount) {
        StringBuffer amtstr = new StringBuffer(String.valueOf(amount));
        if(amtstr.length() == 1) {
            amtstr.insert(0, "0.0");
        } else if(amtstr.length() == 2) {
            amtstr.insert(0, "0.");
        } else {
            amtstr.insert(amtstr.length() - 2, ".");
        }

        return amtstr.toString();
    }

    public static String handleAmount2(String amountstr) {
        if(isBlank(amountstr) || !StringUtils.isNumeric(amountstr)) {
            amountstr = "0";
        }

        return handleAmount(Integer.valueOf(Integer.parseInt(amountstr)));
    }

    public static Integer handleAmount(String amount) {
        String[] amtstrs = amount.split("\\.");
        if(amtstrs.length == 1) {
            return Integer.valueOf(Integer.parseInt(amtstrs[0] + "00"));
        } else if(amtstrs.length == 2) {
            StringBuffer amtstr = new StringBuffer(StringUtils.isBlank(amtstrs[0])?"0":amtstrs[0]);
            if(StringUtils.isBlank(amtstrs[1])) {
                amtstr.append("00");
            } else if(amtstrs[1].length() == 1) {
                amtstr.append(amtstrs[1]).append("0");
            } else if(amtstrs[1].length() == 2) {
                amtstr.append(amtstrs[1]);
            } else {
                amtstr.append(amtstrs[1].substring(0, 2));
            }

            return Integer.valueOf(Integer.parseInt(amtstr.toString()));
        } else {
            return Integer.valueOf(0);
        }
    }

    public static final String generateCode(String functionName) {
        StringBuilder generateCode = new StringBuilder(20);
        if(isBlank(functionName)) {
            return "";
        } else {
            functionName = functionName.toUpperCase();
            if(functionName.length() != 2) {
                functionName = functionName.substring(0, 2);
            }

            generateCode.append(functionName);
            generateCode.append(DateUtil.format(new Date(), "yyyyMMddHHmmss"));
            generateCode.append(RandomUtil.randomNum(4));
            return generateCode.toString();
        }
    }

    public static final String formatRMB(String value) {
        StringBuilder builder = new StringBuilder(20);
        builder.append("￥");
        builder.append(value);
        return builder.toString();
    }

    public static String decodeUnicode(String theString) {
        int len = theString.length();
        StringBuilder outBuilder = new StringBuilder(len);
        int x = 0;

        while(true) {
            while(true) {
                while(x < len) {
                    char aChar = theString.charAt(x++);
                    if(aChar == 92) {
                        aChar = theString.charAt(x++);
                        if(aChar == 117) {
                            int value = 0;

                            for(int i = 0; i < 4; ++i) {
                                aChar = theString.charAt(x++);
                                switch(aChar) {
                                    case '0':
                                    case '1':
                                    case '2':
                                    case '3':
                                    case '4':
                                    case '5':
                                    case '6':
                                    case '7':
                                    case '8':
                                    case '9':
                                        value = (value << 4) + aChar - 48;
                                        break;
                                    case ':':
                                    case ';':
                                    case '<':
                                    case '=':
                                    case '>':
                                    case '?':
                                    case '@':
                                    case 'G':
                                    case 'H':
                                    case 'I':
                                    case 'J':
                                    case 'K':
                                    case 'L':
                                    case 'M':
                                    case 'N':
                                    case 'O':
                                    case 'P':
                                    case 'Q':
                                    case 'R':
                                    case 'S':
                                    case 'T':
                                    case 'U':
                                    case 'V':
                                    case 'W':
                                    case 'X':
                                    case 'Y':
                                    case 'Z':
                                    case '[':
                                    case '\\':
                                    case ']':
                                    case '^':
                                    case '_':
                                    case '`':
                                    default:
                                        throw new IllegalArgumentException("Malformed   \\uxxxx   encoding.");
                                    case 'A':
                                    case 'B':
                                    case 'C':
                                    case 'D':
                                    case 'E':
                                    case 'F':
                                        value = (value << 4) + 10 + aChar - 65;
                                        break;
                                    case 'a':
                                    case 'b':
                                    case 'c':
                                    case 'd':
                                    case 'e':
                                    case 'f':
                                        value = (value << 4) + 10 + aChar - 97;
                                }
                            }

                            outBuilder.append((char)value);
                        } else {
                            if(aChar == 116) {
                                aChar = 9;
                            } else if(aChar == 114) {
                                aChar = 13;
                            } else if(aChar == 110) {
                                aChar = 10;
                            } else if(aChar == 102) {
                                aChar = 12;
                            }

                            outBuilder.append(aChar);
                        }
                    } else {
                        outBuilder.append(aChar);
                    }
                }

                return outBuilder.toString();
            }
        }
    }
}

