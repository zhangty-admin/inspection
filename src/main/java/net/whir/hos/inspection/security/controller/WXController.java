package net.whir.hos.inspection.security.controller;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.whir.hos.inspection.commons.utils.MultiRequest;
import net.whir.hos.inspection.commons.utils.WXToken;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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
    private String getIdentity(String code) {
        JSONObject token = WXToken.getToken(WXToken.corpId, WXToken.corpsecret, WXToken.url);
        String access_token = (String) token.get("access_token");
        Map<String, String> map = new HashMap<>();
        map.put("access_token", access_token);
        map.put("code", code);
        //"https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token=ACCESS_TOKEN&code=CODE";
        String s = MultiRequest.get(map, "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo");
        System.out.println(s);
        return /*MultiRequest.get(map, "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo")*/s;
    }

}
