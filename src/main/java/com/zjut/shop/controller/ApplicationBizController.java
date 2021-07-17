package com.zjut.shop.controller;

import com.zjut.shop.query.ApplicationBizParam;
import com.zjut.shop.response.BaseResult;
import com.zjut.shop.service.ApplicationBizService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class ApplicationBizController {

    @Autowired
    private ApplicationBizService applicationBizService;

    /**
     * 按需查询应用下面的业务列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/api/private/v1/application/biz")
    public ResponseEntity<?> selectList(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "5") Integer pageSize
            , @RequestParam(defaultValue = "") String applicationId, @RequestParam(defaultValue = "") String bizCode, @RequestParam(defaultValue = "") String bizName
            , @RequestParam(defaultValue = "") String status, @RequestParam(required = false) Integer fromUrl) {
        ApplicationBizParam applicationBizParam = new ApplicationBizParam();
        applicationBizParam.setPageNum(pageNum - 1);
        applicationBizParam.setPageSize(pageSize);
        applicationBizParam.setApplicationId(applicationId);
        applicationBizParam.setBizCode(bizCode);
        applicationBizParam.setBizName(bizName);
        applicationBizParam.setStatus(status);
        applicationBizParam.setFromUrl(fromUrl);

        log.info("查询应用下面业务的参数:{}", applicationBizParam);
        return new ResponseEntity<>(BaseResult.handlerSuccess("查询应用业务列表成功", applicationBizService.selectApplicationBizList(applicationBizParam)), HttpStatus.OK);
    }

    /**
     * 根据业务id查应用下业务
     * @param id
     * @return
     */
    @GetMapping("/api/private/v1/application/biz/{id}")
    public ResponseEntity<?> selectByBizCode(@PathVariable Integer id) {
        log.info("根据id查询应用下面业务的参数:{}", id);
        return new ResponseEntity<>(BaseResult.handlerSuccess("查询应用业务成功", applicationBizService.selectByBizCode(id)), HttpStatus.OK);
    }

    /**
     * 根据业务code修改业务状态
     * @param id
     * @return
     */
    @PutMapping("/api/private/v1/application/biz/{id}/{status}")
    public ResponseEntity<?> upodateByBizCode(@PathVariable Integer id, @PathVariable String status) {
        log.info("根据业务code修改应用下面业务的参数:{}", id);
        return new ResponseEntity<>(BaseResult.handlerSuccess("修改应用业务成功", applicationBizService.changeStatusByBizCode(id, status)), HttpStatus.OK);
    }

    /**
     * 根据业务code修改业务
     * @param applicationBizParam
     * @return
     */
    @PutMapping("/api/private/v1/application/biz")
    public ResponseEntity<?> updateById(@RequestBody ApplicationBizParam applicationBizParam) {
        log.info("根据id修改应用下面业务:{}", applicationBizParam);
        return new ResponseEntity<>(BaseResult.handlerSuccess("修改应用业务成功", applicationBizService.updateByBizCode(applicationBizParam)), HttpStatus.OK);
    }

    /**
     * 根据业务id删除业务
     * @param id
     * @return
     */
    @DeleteMapping("/api/private/v1/application/biz/{id}")
    public ResponseEntity<?> deleteByBizCode(@PathVariable Integer id) {
        log.info("根据id删除应用下面业务的参数:{}", id);
        return new ResponseEntity<>(BaseResult.handlerSuccess("删除应用业务成功", applicationBizService.deleteByBizCode(id)), HttpStatus.OK);
    }

    /**
     * 新增业务
     * @param applicationBizParam
     * @return
     */
    @PostMapping("/api/private/v1/application/biz")
    public ResponseEntity<?> insert(@RequestBody ApplicationBizParam applicationBizParam) {
        log.info("新增应用下面业务:{}", applicationBizParam);
        return new ResponseEntity<>(BaseResult.handlerSuccess("新增应用业务成功", applicationBizService.addApplicationBiz(applicationBizParam)), HttpStatus.OK);
    }

}
