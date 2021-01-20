package com.zjut.shop.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
public class LoginController {

    @PostMapping("/api/private/v1/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> param) {
        log.info("收到:{}", param);
        String account = param.get("account");

        JSONObject result = new JSONObject();
        JSONObject data = new JSONObject();
        data.put("id", 500);
        data.put("rid", 0);
        data.put("username", "admin");
        data.put("mobile", "123");
        data.put("email", "123@qq.com");
        data.put("token", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1aWQiOjUwMCwicmlkIjowLCJpYXQiOjE1MTI1NDQyOTksImV4cCI6MTUxMjYzMDY5OX0.eGrsrvwHm-tPsO9r_pxHIQ5i5L1kX9RX444uwnRGaIM");
        if (StringUtils.isNotBlank(account) && "cuo".equals(account)) {
            result.put("data", data);
        }
        JSONObject meta = new JSONObject();
        meta.put("msg", "登录成功啦");
        meta.put("status", 200);
        if (StringUtils.isNotBlank(account) && "cuo".equals(account)) {
            meta.put("status", 400);
            meta.put("msg", "登录失败啦");
        }
        result.put("meta", meta);
        return new ResponseEntity(result, HttpStatus.OK);
    }
}
