package net.whir.hos.inspection.security.controller;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.whir.hos.inspection.commons.entity.MultiRequest;
import net.whir.hos.inspection.commons.entity.WXToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @PostMapping()
    private String getIdentity(String code) {
        JSONObject token = WXToken.getToken(WXToken.corpId, WXToken.corpsecret, WXToken.url);
        String access_token = (String) token.get("access_token");
        Map<String, String> map = new HashMap<>();
        map.put("access_token", access_token);
        map.put("code", code);
        //"https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token=ACCESS_TOKEN&code=CODE";
        return MultiRequest.get(map, "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo");
    }

}
