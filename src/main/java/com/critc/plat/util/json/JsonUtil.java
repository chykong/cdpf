package com.critc.plat.util.json;

import com.critc.plat.util.log.LogUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * JSON工具类，进行对象转string和string转对象
 *
 * @author 孔垂云
 * @date 2017-05-23
 */
public class JsonUtil {
    private static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 把对象转成json串
     *
     * @param obj 对象，可以是VO、List、HashMap等等
     * @return 返回生成的json值
     */
    public static String toStr(Object obj) {
        String json_str = "";
        try {
            json_str = objectMapper.writer().writeValueAsString(obj);
        } catch (Exception e) {
            LogUtil.error(e);
            LogUtil.error("转换错误：" + obj.toString());
        }
        return json_str;
    }

    /**
     * json转对象
     *
     * @param jsonStr   json字符串
     * @param valueType 要转成的对象类型，采用泛型的方式
     * @return
     */
    public static <T> T toObject(String jsonStr, Class<T> valueType) {
        try {
            return objectMapper.readValue(jsonStr, valueType);
        } catch (Exception e) {
            LogUtil.error(e);
            LogUtil.error("转换错误：" + jsonStr);
        }
        return null;
    }


    /**
     * 生成操作后的json串，{success:false,msgText:'删除失败'}
     *
     * @param b   是否
     * @param msg 提示消息
     * @return
     */
    public static String createOperaStr(boolean b, String msg) {
        return "{\"success\":" + b + ",\"msgText\":\"" + msg + "\"}";
    }

    /**
     * 创建Extjs分页json
     *
     * @param rows 行数
     * @param data 对象已转json
     * @return
     */
    public static String createExtjsPageJson(long rows, String data) {
        String json = "{\"total\":\"" + rows + "\",\"rows\":" + data + "}";
        return json;
    }

    /**
     * 创建Extjs分页json
     *
     * @param rows 行数
     * @param obj  对象
     * @return 生成的json串
     */
    public static String createExtjsPageJson(long rows, Object obj) {
        String json = "{\"total\":\"" + rows + "\",\"root\":" + toStr(obj) + "}";
        return json;
    }

    /**
     * 生成bootgrid的分页json
     *
     * @param pageIndex 当前页数
     * @param rows      行数
     * @param data      数据json串
     * @return
     */
    public static String createBootGridPageJson(int pageIndex, int rows, String data) {
        String json = "{\"current\":" + pageIndex + ",\"rowCount\":\"" + 10 + "\",\"rows\":" + data + ",\"total\":" + rows + "}";
        return json;
    }
}
