package com.jeeplus.modules.esign.util;


import java.util.Random;

/**
 * ID工具类处理工具
 */
public class IdUtils {


    public static String generateCouponBatchNo() {
        return "NO." + getCurrentFileName();
    }

    public static String getCurrentFileName() {
        String timestamp = DateUtils.getYMD();
        int randomSuffix = RandomUtils.generateRandomNum(0, 9);
        return timestamp + randomSuffix;
    }






    public static String getRandomCharacterAndNumber(int length) {
        String val = "";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num"; // 输出字母还是数字

            if ("char".equalsIgnoreCase(charOrNum)) // 字符串
            {
                int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; // 取得大写字母还是小写字母
                val += (char) (choice + random.nextInt(26));
                // int choice = 97; // 指定字符串为小写字母
              //  val += (char) (choice + random.nextInt(26));
            } else if ("num".equalsIgnoreCase(charOrNum)) // 数字
            {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }

}
