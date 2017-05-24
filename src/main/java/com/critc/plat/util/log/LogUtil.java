package com.critc.plat.util.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 说明：日志操作类，在系统中如果需要输入日志，调用该类的info方法，而不是自己建立一个logger
 *
 * @author 孔垂云
 * @date 2017-05-23
 */
public class LogUtil {
    private static Logger sysLog = LoggerFactory.getLogger("sysLog");

    /**
     * 功能描述:记录系统日志
     *
     * @param str 要输出的字符串
     * @author 孔垂云
     * @date 2017-05-23
     */
    public static void inf(String str) {
        sysLog.info(str);
    }

    /**
     * 功能描述:记录系统异常
     *
     * @param str 要输出的字符串
     * @author 孔垂云
     * @date 2017-05-23
     */
    public static void error(String str) {
        sysLog.error(str);
    }

    /**
     * 功能描述:记录系统异常
     *
     * @param e 系统异常
     * @author 孔垂云
     * @date 2017-05-23
     */
    public static void error(Exception e) {
        sysLog.error(e.getStackTrace().toString());
    }

}