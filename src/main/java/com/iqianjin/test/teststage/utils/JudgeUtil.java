package com.iqianjin.test.teststage.utils;

//一些判断类的方法
public class JudgeUtil {
    /**
     * <p>验证src是否为null或空字符串</p>
     *
     * <pre>
     * isBlank(null)       = true
     * isBlank("")         = true
     * isBlank(" ")        = true
     * isBlank("moon")     = false
     * isBlank("  moon  ") = false
     * </pre>
     *
     * @param src
     * @return 如果为null或空字符串返回true;否则返回false
     */
    public static boolean isBlank(String src) {
        return src == null || "".equals(src.trim());
    }
}
