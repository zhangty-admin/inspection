package net.whir.hos.inspection.commons.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;

/**
 * @Author: zty
 * @Date: 2020/5/6 7:05 下午
 */
public class WXToken {

    /*@Value("${WX.corpid}")*/
    public static String corpId = "ww7d1b766a3b9fcfb7";

    /*@Value("${WX.corpsecret}")*/
    public static String corpsecret = "dXkvUsnh94K2UEaZhKB77XBNhgxDfXSj9J-HY-Ye6f8";

    public static String url = "https://qyapi.weixin.qq.com/cgi-bin/gettoken";

    /**
     * 获取企业微信token
     *
     * @param corpId
     * @param corpsecret
     * @param url
     * @return
     */
    public static JSONObject getToken(String corpId, String corpsecret, String url) {
        HashMap<String, String> map = new HashMap<>();
        map.put("corpid", corpId);
        map.put("corpsecret", corpsecret);
        String httpData = MultiRequest.get(map, url);
        JSONObject jsonObject = JSON.parseObject(httpData);
        return jsonObject;
    }
}
