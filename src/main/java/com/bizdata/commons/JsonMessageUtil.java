package com.bizdata.commons;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

/**
 * @author 顾剑峰<br/>
 *         创建时间：2015年7月30日 下午1:49:30<br/>
 *         描述：json消息返回工具类
 */
public class JsonMessageUtil {

    /**
     * @Fields gson : 设置gson
     */
    private static final Gson gson = new Gson();

    /**
     * @return
     * @author 顾剑峰<br/>
     * 创建时间：2015年7月30日 下午1:49:45<br/>
     * 描述：操作成功消息json提示
     */
    public static String setSuccessJsonString() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", true);
        map.put("message", "执行成功");
        String json = gson.toJson(map);
        return json;
    }

    /**
     * @param errorString
     * @return
     * @author 顾剑峰<br/>
     * 创建时间：2015年7月30日 下午1:50:06<br/>
     * 描述：操作错误消息json提示
     */
    public static String setErrorJsonString(String errorString) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", false);
        map.put("message", errorString);
        String json = gson.toJson(map);
        return json;
    }

}
