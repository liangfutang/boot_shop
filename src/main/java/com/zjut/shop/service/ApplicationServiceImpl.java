package com.zjut.shop.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.zjut.shop.enums.ResultStatus;
import com.zjut.shop.execption.ShopRuntimeException;
import com.zjut.shop.query.ApplicationParam;
import com.zjut.shop.vo.ApplicationVO;
import com.zjut.shop.vo.PageResult;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ApplicationServiceImpl implements ApplicationService{
    private static ThreadLocal<SimpleDateFormat> dataFormat = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

    @Getter
    private static final List<ApplicationVO> applicationList = new CopyOnWriteArrayList<>();
    static {
        String[] applicationName = {"产品部", "研发部", "品控部", "总裁办", "后勤保障", "厂房", "设备管控部"};
        String[] principalName = {"张三", "李四", "王五", "李六", "张无忌", "张三丰", "郭靖"};
        Random random = new Random();
        for (int i=0; i<29; i++) {
            ApplicationVO applicationVO = new ApplicationVO();
            applicationVO.setId(UUID.randomUUID().toString().replaceAll("-", ""));
            applicationVO.setApplicationName(applicationName[random.nextInt(applicationName.length)]);
            applicationVO.setPrincipal(principalName[random.nextInt(principalName.length)]);
            applicationVO.setStatus(random.nextInt(2) == 0 ? "Y" : "N");
            String createTime = dataFormat.get().format(new Date(System.currentTimeMillis() - random.nextInt(900000000)));
            applicationVO.setGmtCreateStr(createTime);
            applicationList.add(applicationVO);
        }
    }

    @Override
    public PageResult<List<ApplicationVO>> selectApplicationList(ApplicationParam applicationParam) {
        // 模糊查询
        List<ApplicationVO> afterFilterApp = applicationList.stream().filter(one -> this.filterApplication(one, applicationParam)).collect(Collectors.toList());

        // 当前用户数
        int currentUserTotal = afterFilterApp.size();
        // 查询的起点
        int start = applicationParam.getPageNum() * applicationParam.getPageSize();
        int end = start + applicationParam.getPageSize();
        // 对起点和终点润色
        // 1. 如果起点超出范围则返回为空
        if (start > currentUserTotal-1) {
            log.info("当前查询页超标啦");
            return new PageResult<>(currentUserTotal, new ArrayList<>());
        }
        // 2. 如果终点超出了范围，则按最后一个算
        if (end > currentUserTotal-1) {
            log.info("终点超出了");
            end = currentUserTotal;
        }

        return new PageResult<>(currentUserTotal, afterFilterApp.subList(start, end));
    }

    @Override
    public ApplicationVO selectAppById(String id) {
        List<ApplicationVO> collect = applicationList.stream().filter(one -> id.equals(one.getId())).collect(Collectors.toList());
        log.info("根据id查出对应的应用结果:" + collect);
        if (CollectionUtil.isEmpty(collect)) {
            log.error("根据id没找到对应的应用信息");
            throw new ShopRuntimeException(ResultStatus.NO_APP_EXEC);
        }
        if (collect.size() > 1) {
            log.error("根据id找到多个应用信息,数据异常");
            throw new ShopRuntimeException(ResultStatus.MORE_APP_EXEC);
        }
        return collect.get(0);
    }

    @Override
    public ApplicationVO changeStatusById(String id, String status){
        ApplicationVO applicationVO = this.selectAppById(id);
        applicationVO.setStatus(status);
        return applicationVO;
    }

    @Override
    public boolean deleteAppById(String id) {
        ApplicationVO applicationVO = this.selectAppById(id);
        return applicationList.remove(applicationVO);
    }

    @Override
    public ApplicationVO updateApp(ApplicationParam applicationParam) {
        ApplicationVO applicationVO = this.selectAppById(applicationParam.getId());
        String applicationName = applicationParam.getApplicationName();
        if (StringUtils.isNotBlank(applicationName)) {
            applicationVO.setApplicationName(applicationName);
        }
        String principal = applicationParam.getPrincipal();
        if (StringUtils.isNotBlank(principal)) {
            applicationVO.setPrincipal(principal);
        }
        return applicationVO;
    }

    @Override
    public ApplicationVO insert(ApplicationParam applicationParam) {
        ApplicationVO applicationVO = new ApplicationVO();
        BeanUtil.copyProperties(applicationParam, applicationVO);

        applicationVO.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        applicationVO.setStatus("Y");
        applicationVO.setGmtCreate(new Date());
        applicationList.add(applicationVO);
        return applicationVO;
    }

    /**
     * 模糊匹配
     * @param applicationVO
     * @param applicationParam
     * @return true: ,fasle:
     */
    private boolean filterApplication(ApplicationVO applicationVO, ApplicationParam applicationParam) {
        if (applicationVO == null) return true;

        List<Boolean> notEmptyResult = new ArrayList<>();

        String id = applicationVO.getId();
        String idParam = applicationParam.getId();
        if (StringUtils.isBlank(id) && StringUtils.isBlank(idParam)) {
            notEmptyResult.add(true);
        } else {
            notEmptyResult.add(StringUtils.isNotBlank(id) && id.contains(idParam));
        }

        String applicationName = applicationVO.getApplicationName();
        String applicationNameParam = applicationParam.getApplicationName();
        if (StringUtils.isBlank(applicationName) && StringUtils.isBlank(applicationNameParam)) {
            notEmptyResult.add(true);
        } else {
            notEmptyResult.add(StringUtils.isNotBlank(applicationName) && applicationName.contains(applicationNameParam));
        }

        String status = applicationVO.getStatus();
        String statusParam = applicationParam.getStatus();
        if (StringUtils.isBlank(status) && StringUtils.isBlank(statusParam)) {
            notEmptyResult.add(true);
        } else {
            notEmptyResult.add(StringUtils.isNotBlank(status) && status.contains(statusParam));
        }

        String principal = applicationVO.getPrincipal();
        String principalParam = applicationParam.getPrincipal();
        if (StringUtils.isBlank(principal) && StringUtils.isBlank(principalParam)) {
            notEmptyResult.add(true);
        } else {
            notEmptyResult.add(StringUtils.isNotBlank(principal) && principal.contains(principalParam));
        }

        // 只有所有参数都是true才行
        List<Boolean> containFalse = notEmptyResult.stream().filter(one -> !one).collect(Collectors.toList());

        return !(containFalse.size() > 0);
    }
}
