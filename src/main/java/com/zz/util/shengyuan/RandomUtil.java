package com.zz.util.shengyuan;

import java.util.Random;
import java.util.UUID;

public class RandomUtil {
    private static char[] A_Z = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
            'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

    /**
     *
     * @param generateNumber
     *            生成位数
     * @return
     */
    public static String randomNum(int generateNumber) {
        StringBuilder num = new StringBuilder(generateNumber);

        num.append((int) (Math.random() * 9 + 1));
        for (int i = 1; i < generateNumber; i++) {
            num.append((int) (Math.random() * 10));
        }

        return num.toString();
    }
    public static String randomUUID(){
        String uuidStr =  UUID.randomUUID().toString();
        StringBuilder uuidBuilder = new StringBuilder(32);
        uuidBuilder.append(uuidStr.substring(0, 8));
        uuidBuilder.append(uuidStr.substring(9, 13));
        uuidBuilder.append(uuidStr.substring(14, 18));
        uuidBuilder.append(uuidStr.substring(19, 23));
        uuidBuilder.append(uuidStr.substring(24));
        return uuidBuilder.toString();
    }

    public static String randomCode(int minNumber, int maxNumber) {
        if (minNumber < 0 || maxNumber < minNumber) {
            minNumber = 4;
            maxNumber = 4;
        }
        int len = new Random().nextInt(maxNumber - minNumber + 1) + minNumber;

        StringBuffer code = new StringBuffer();
        for (int i = 0; i < len; i++) {
            code.append(A_Z[new Random().nextInt(A_Z.length)]);
        }
        return code.toString();
    }

    /**
     * 返回12位HashCode
     * @return
     */
    public static String randomUUIDHashCode(int bit){
        int uuidHashCode =  UUID.randomUUID().toString().hashCode();
        if(uuidHashCode < 0){
            uuidHashCode = -uuidHashCode;
        }
        return String.format("%0"+bit+"d", uuidHashCode);
    }

    //生成唯一编码：规则（32位）=时间(14位如：051212)+UUID(18位uuid hashCode)
    public static String randomCode(){
        StringBuilder code = new StringBuilder(32);
        code.append(DateUtil.formatCurrentDateTime());//14
        code.append(randomUUIDHashCode(18));//32位
        return code.toString();
    }

    public static void main(String[] args) {
        System.out.println(randomNum(1));
    }

    public static String r(){
        StringBuilder code = new StringBuilder(32);
        code.append(DateUtil.formatCurrentDateTime());//14
        code.append(randomUUIDHashCode(10));//32位

        String str = code.toString();

        StringBuilder randomStr = new StringBuilder(20);

        for (int i=0; i < str.length(); i=i+4) {
            String strtmp = Integer.toHexString(Integer.valueOf(str.substring(i, i + 4)));
            randomStr.append(strtmp);
        }
        return randomStr.toString();
    }
}
