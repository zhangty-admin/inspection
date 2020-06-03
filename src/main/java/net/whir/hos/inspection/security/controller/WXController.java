package net.whir.hos.inspection.security.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.whir.hos.inspection.commons.entity.Result;
import net.whir.hos.inspection.commons.entity.StatusCode;
import net.whir.hos.inspection.commons.utils.MultiRequest;
import net.whir.hos.inspection.commons.utils.WXToken;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Author: zty
 * @Date: 2020/5/8 11:10 上午
 */

@RestController
@CrossOrigin
@RequestMapping("/wx")
@Api(description = "微信操作")
public class WXController {

    @ApiOperation(value = "获取用户信息")
    @GetMapping
    private Result getIdentity(String code) {
        JSONObject token = WXToken.getToken(WXToken.corpId, WXToken.corpsecret, WXToken.url);
        String access_token = (String) token.get("access_token");
        Map<String, String> map = new HashMap<>();
        map.put("access_token", access_token);
        map.put("code", code);
        //"https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token=ACCESS_TOKEN&code=CODE";
        String json = MultiRequest.get(map, "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo");
        JSONObject jsonObject = JSON.parseObject(json);
        Set<Map.Entry<String, Object>> entries = jsonObject.getInnerMap().entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            if ("errcode".equals(entry.getKey()) && !"0".equals(entry.getValue().toString())) {
                return new Result(false, StatusCode.ERROR, "失败");
            }
        }
        return new Result(true, StatusCode.OK, "成功",jsonObject);
    }

}
