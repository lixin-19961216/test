package com.iqianjin.test.teststage.utils;

import com.puhui.aes.AesEncryptionUtil;

import java.util.ArrayList;
import java.util.List;

public class TransformUtil {
    private static final String ENCRYPT_START_WORD = "xy";
    private static final String ENCRYPT_END_WORD = "20160926";

    public  static String listToString(List<Integer> integerList) {
        if (integerList == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        boolean flag = false;
        for (Integer temp : integerList) {
            if (flag) {
                result.append(",");
            } else {
                flag = true;
            }
            result.append(temp);
        }
        return result.toString();
    }
    /**
     *带有 ， 的字符串转换为list
     * @param str
     * @return
     */
    public static List<Integer> str2list(String str) {
        List<Integer> list = new ArrayList<>();
        if(str.contains(",")){
            String[] strings = str.split(",");
            for (int i = 0; i < strings.length; i++){
                list.add(Integer.parseInt(strings[i]));
            }
            return list;
        }
        list.add(Integer.parseInt(str));
        return list;
    }

    /**
     * 加密身份证
     *
     * @param content 原身份证号
     */
    public static String encryptIdNo(String content) {
        return encrypt(content);
    }

    /**
     * 解密身份证
     *
     * @param content 加密后的身份证信息
     */
    public static String decryptIdNo(String content) {
        return decrypt(content);
    }

    /**
     * 加密银行卡
     *
     * @param content   原银行卡号
     * @return
     */
    public static String encryptBankNo(String content) {
        return encrypt(content);
    }

    /**
     * 解密银行卡
     *
     * @param content   加密后的银行卡信息
     * @return
     */
    public static String decryptBankNo(String content) {
        return decrypt(content);
    }

    /**
     * 加密手机号
     *
     * @param mobile 原手机号
     */
    public static String encryptMobile(String mobile) {
        return encrypt(mobile);
    }

    /**
     * 解密手机号
     *
     * @param content 加密后的手机号
     */
    public static String decryptMobile(String content) {
        return decrypt(content);
    }

    /**
     * 解密方法
     *
     * @param content 解密得信息
     */
    private static String decrypt(String content) {
        if (JudgeUtil.isBlank(content)) {
            return content;
        }
        if (content.startsWith(ENCRYPT_START_WORD) && content.endsWith(ENCRYPT_END_WORD)) {
            return AesEncryptionUtil.decrypt(content);
        }
        return content;
    }

    /**
     * 加密信息
     *
     * @param content 加密前得信息
     */
    private static String encrypt(String content) {
        if (JudgeUtil.isBlank(content)) {
            return content;
        }
        if (content.startsWith(ENCRYPT_START_WORD) && content.endsWith(ENCRYPT_END_WORD)) {
            return content;
        }
        return AesEncryptionUtil.encrypt(content.trim());
    }
}
