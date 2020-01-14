package com.jeeplus.modules.esign.util;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Random;

/**
 * 随机生成工具类
 */
public class RandomUtils {
    public static final String preApplyId = "VSS";

    private static final NumberFormat nf = NumberFormat.getNumberInstance();

    public static Integer generateRandomNum(Integer min, Integer max) {
        Random rand = new Random();
        int diff = max - min + 1;
        return rand.nextInt(diff) + min;
    }

    public static String generateSmsCode() {
        String codeNum = "";
        int[] numbers = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            int next = random.nextInt(10000);
            //目的是产生足够随机的数，避免产生的数字重复率高的问题
            codeNum += numbers[next % 10];
        }
        return codeNum;
    }

    public static String randomAOrBGroup() {
        String[] strArr = {"A", "B"};
        Random random = new Random();
        int sub = random.nextInt(strArr.length);
        return strArr[sub];
    }

    /**
     * 翼支付生成applyId的随机数
     * 17位 后四位是生成的随机数
     */
    public static String generateYeeApplyId() {
        String ymd = DateUtils.getYeeApplyIdSuf();
        String applyId = preApplyId + "" + ymd + "" + (int) (Math.random() * (999 - 100 + 1) + 100);
        return applyId;
    }

    public static Long generateYeeNonce() {
        return (int) (Math.random() * (9999999 - 100000 + 1) + 100000) * 2L;
    }

    public static String generateYeeSourceSystem() {
        String sourceSystem = preApplyId + DateUtils.getSourceSystem(new Date());
        return sourceSystem;
    }

    public static Integer generateSuccessPersonNum(int max,int min){
        Random random = new Random();
        int num = random.nextInt(max)%(max-min+1)+min;
        return num;
    }

    /**
     * 产生一个15-20元的金额
     * @return
     */
    public static Double generateLuckAmt(int max,int min){
        Random random = new Random();
        Double num = min+random.nextDouble() +generateRandomNum(0,max-min-1);
        //保留两位小数
        nf.setMaximumFractionDigits(2);
        //不需要四舍五入
        nf.setRoundingMode(RoundingMode.DOWN);
        return Double.valueOf(nf.format(num));
    }

 /*   public static String genNonceStr() {
        Random random = new Random();
        return MD5Utils.MD5Encode(String.valueOf(random.nextInt(10000)), "");
    }*/

    public static Long vbsSign(){
        return System.currentTimeMillis()/1000;
    }

    public static void main(String[] args) {
       for(int i = 0 ;i<100;i++){
           System.out.println("随机数-->" + generateLuckAmt(30,15));
       }
    }

    public static Integer getMinute() {
        return ((int) ((Math.random() * (50 - 1 + 1)) + 1));
    }
}
