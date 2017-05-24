package com.critc.plat.util.number;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 数字格式化工具类
 *
 * @author 孔垂云
 * @date 2017-05-23
 */
public class NumberUtil {
    /**
     * 格式化小数，返回值不带,
     *
     * @param val   字符串数字
     * @param point 小数位
     * @return
     * @author 孔垂云
     * @date 2017-05-23
     */
    public static String formatDouble(String val, int point) {
        String str = "";
        DecimalFormat nf = new DecimalFormat();
        nf.setMaximumFractionDigits(point);
        str = nf.format(Double.parseDouble(val));
        return str.replace(",", "");
    }

    /**
     * 格式化小数,传入的是数字，返回值不带,
     *
     * @param val   数字
     * @param point 小数位
     * @return
     * @author 孔垂云
     * @date 2017-05-23
     */
    public static double formatDouble(double val, int point) {
        String str = "";
        DecimalFormat nf = new DecimalFormat();
        nf.setMaximumFractionDigits(point);
        str = nf.format(val);
        return Double.parseDouble(str.replace(",", ""));
    }


    /**
     * 十六进制字符串转数字
     *
     * @param str
     * @return
     * @author 孔垂云
     * @date 2017-05-23
     */
    public static int hexTrans(String str) {
        return Integer.parseInt(str.replaceAll("^0[x|X]", ""), 16);
    }

    /**
     * 判断是否是整数
     *
     * @param integer
     * @return
     * @author 孔垂云
     * @date 2017-05-23
     */
    public static boolean isInteger(String integer) {
        Pattern p = Pattern.compile("\\d*");
        Matcher m = p.matcher(integer);
        boolean b = m.matches();
        return b;
    }

    /**
     * 判断是否是正整数
     *
     * @param integer
     * @return
     * @author 孔垂云
     * @date 2017-05-23
     */
    public static boolean isInteger2(String integer) {
        Pattern p = Pattern.compile("^[0-9]*[1-9][0-9]*$");
        Matcher m = p.matcher(integer);
        boolean b = m.matches();
        return b;
    }

    /**
     * 判断是否是小数
     *
     * @param decimal
     * @return
     * @author 孔垂云
     * @date 2017-05-23
     */
    public static boolean isDecimal(String decimal) {
        Pattern p = Pattern.compile("^([+-]?)\\\\d*\\\\.\\\\d+$");
        Matcher m = p.matcher(decimal);
        boolean b = m.matches();
        return b;
    }
    /**
     * 判断是否是正小数
     *
     * @param decimal
     * @return
     * @author 孔垂云
     * @date 2017-05-23
     */
    public static boolean isDecimal2(String decimal) {
        Pattern p = Pattern.compile("^-([1-9]\\\\d*.\\\\d*|0.\\\\d*[1-9]\\\\d*)$");
        Matcher m = p.matcher(decimal);
        boolean b = m.matches();
        return b;
    }
}
