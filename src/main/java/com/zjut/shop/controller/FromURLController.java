package com.zjut.shop.controller;

import com.zjut.shop.enums.FromUrlParam;
import com.zjut.shop.response.BaseResult;
import com.zjut.shop.service.FromURLService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class FromURLController {


    @Autowired
    private FromURLService fromURLService;

    /**
     * 查询前缀
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/api/private/v1/fromurl")
    public ResponseEntity<?> selectFromUrlList(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "5") Integer pageSize) {
        FromUrlParam fromUrlParam = new FromUrlParam();
        fromUrlParam.setPageNum(pageNum - 1);
        fromUrlParam.setPageSize(pageSize);
        log.info("查询前缀的参数:{}", fromUrlParam);
        return new ResponseEntity<>(BaseResult.handlerSuccess("查询前缀列表成功", fromURLService.selectList(fromUrlParam)), HttpStatus.OK);
    }

    /**
     * 根据id修改前缀的状态值
     * @param value
     * @param status
     * @return
     */
    @PutMapping("/api/private/v1/fromurl/{value}/{status}")
    public ResponseEntity<?> changeStatusByValue(@PathVariable Integer value, @PathVariable String status) {
        log.info("修改应用的id:{},status:{}", value, status);
        return new ResponseEntity<>(BaseResult.handlerSuccess("根据id修改应用状态成功", fromURLService.changeStatusById(value, status)), HttpStatus.OK);
    }

    /**
     * 根据id删除前缀
     * @param value
     * @return
     */
    @DeleteMapping("/api/private/v1/fromurl/{value}")
    public ResponseEntity<?> deleteFromUrlByValue(@PathVariable Integer value) {
        log.info("修改应用的id:{}", value);
        return new ResponseEntity<>(BaseResult.handlerSuccess("根据id修改应用状态成功", fromURLService.deleteFromUrlByValue(value)), HttpStatus.OK);
    }

    /**
     * 根据id查询前缀
     * @param value
     * @return
     */
    @GetMapping("/api/private/v1/fromurl/{value}")
    public ResponseEntity<?> selectFromUrlByValue(@PathVariable Integer value) {
        log.info("修改应用的id:{}", value);
        return new ResponseEntity<>(BaseResult.handlerSuccess("根据id查询应用状态成功", fromURLService.selectListByValue(value)), HttpStatus.OK);
    }

    /**
     * 根据id修改前缀
     * @param fromUrlParam
     * @return
     */
    @PutMapping("/api/private/v1/fromurl")
    public ResponseEntity<?> updateFromUrl(@RequestBody FromUrlParam fromUrlParam) {
        log.info("修改应用的id:{}", fromUrlParam);
        return new ResponseEntity<>(BaseResult.handlerSuccess("根据id查询应用状态成功", fromURLService.updateFromUrl(fromUrlParam)), HttpStatus.OK);
    }

    /**
     * 新增前缀
     * @param fromUrlParam
     * @return
     */
    @PostMapping("/api/private/v1/fromurl")
    public ResponseEntity<?> addFromUrl(@RequestBody FromUrlParam fromUrlParam) {
        log.info("新增前缀:{}", fromUrlParam);
        return new ResponseEntity<>(BaseResult.handlerSuccess("新增前缀成功", fromURLService.addFromUrl(fromUrlParam)), HttpStatus.OK);
    }

}
