package com.zjut.shop.controller;

import com.zjut.shop.query.CateParam;
import com.zjut.shop.response.BaseResult;
import com.zjut.shop.service.CateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class CateController {

    @Autowired
    private CateService cateService;

    @GetMapping("/categories")
    public ResponseEntity<?> selectList(CateParam cateParam) {
        return new ResponseEntity<>(BaseResult.handlerSuccess("查询分类列表成功", cateService.selectList(cateParam)), HttpStatus.OK);
    }

}
