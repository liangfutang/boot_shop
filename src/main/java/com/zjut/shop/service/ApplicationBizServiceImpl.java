package com.zjut.shop.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.zjut.shop.enums.ResultStatus;
import com.zjut.shop.execption.ShopRuntimeException;
import com.zjut.shop.query.ApplicationBizParam;
import com.zjut.shop.vo.ApplicationBizVO;
import com.zjut.shop.vo.ApplicationVO;
import com.zjut.shop.vo.FromURLVO;
import com.zjut.shop.vo.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ApplicationBizServiceImpl implements ApplicationBizService{
    private static final ThreadLocal<SimpleDateFormat> dataFormat = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));


    private static final List<ApplicationBizVO> applicationBizList = new CopyOnWriteArrayList<>();
    static {
        List<ApplicationVO> applicationList = ApplicationServiceImpl.getApplicationList();
        List<FromURLVO> fromURLList = FromURLServiceImpl.getFromURLList();
        String[] bizName = {"北京", "上海", "江苏", "安徽", "广东", "浙江", "江西", "西藏", "新疆", "山西", "广西", "天津", "辽宁", "内蒙古", "重庆", "甘肃", "宁夏"};
        String[] bizCode = {"beijing", "shanghai", "jiangsu", "an hui", "guang dong", "zhe jiang", "jiang xi", "xi zang", "xin jiang", "shan xi", "guang xi", "tian jin", "liao ning", "nei meng gu", "chong qing", "gan su", "ning xia"};

        Random random = new Random();
        for (int i=0; i<40; i++) {
            ApplicationBizVO applicationBiz = new ApplicationBizVO();
            applicationBiz.setId(i);
            applicationBiz.setApplicationId(applicationList.get(random.nextInt(applicationList.size())).getId());
            int i1 = random.nextInt(bizName.length);
            applicationBiz.setBizCode(bizCode[i1]);
            applicationBiz.setBizName(bizName[i1]);
            applicationBiz.setStatus(random.nextInt(2) == 0 ? "Y" : "N");
            String createTime = dataFormat.get().format(new Date(System.currentTimeMillis() - random.nextInt(900000000)));
            applicationBiz.setGmtCreateStr(createTime);
            applicationBiz.setFromUrl(fromURLList.get(random.nextInt(fromURLList.size())).getValue());
            applicationBizList.add(applicationBiz);
        }
    }

    @Autowired
    private ApplicationService applicationService;

    @Override
    public PageResult<List<ApplicationBizVO>> selectApplicationBizList(ApplicationBizParam applicationBizParam) {
// 模糊查询
        List<ApplicationBizVO> afterFilterApp = applicationBizList.stream().filter(one -> this.filterApplication(one, applicationBizParam)).collect(Collectors.toList());

        // 当前用户数
        int currentUserTotal = afterFilterApp.size();
        // 查询的起点
        int start = applicationBizParam.getPageNum() * applicationBizParam.getPageSize();
        int end = start + applicationBizParam.getPageSize();
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
    public ApplicationBizVO selectByBizCode(Integer id){
        List<ApplicationBizVO> collect = applicationBizList.stream().filter(one -> id.equals(one.getId())).collect(Collectors.toList());
        if (CollectionUtil.isEmpty(collect)) {
            log.error("没找到对应的业务");
            throw new ShopRuntimeException(ResultStatus.NO_APP_BIZ_EXEC);
        }
        if (collect.size() > 1) {
            log.error("没找到对应的业务");
            throw new ShopRuntimeException(ResultStatus.MORE_APP_BIZ_EXEC);
        }
        return collect.get(0);
    }

    @Override
    public ApplicationBizVO addApplicationBiz(ApplicationBizParam applicationBizParam) {
        ApplicationVO applicationVO = applicationService.selectAppById(applicationBizParam.getApplicationId());
        if (applicationVO == null) {
            throw new ShopRuntimeException(ResultStatus.NO_APP_EXEC);
        }

        List<ApplicationBizVO> collect = applicationBizList.stream()
                .filter(one -> applicationBizParam.getBizCode().equals(one.getBizCode()) && applicationBizParam.getApplicationId().equals(one.getApplicationId())).collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(collect)) {
            throw new ShopRuntimeException(ResultStatus.EXIST_APP_BIZ_EXEC);
        }

        ApplicationBizVO applicationBizVO = new ApplicationBizVO();
        BeanUtil.copyProperties(applicationBizParam, applicationBizVO);
        applicationBizList.add(applicationBizVO);
        return applicationBizVO;
    }


    @Override
    public ApplicationBizVO updateByBizCode(ApplicationBizParam applicationBizParam) {
        ApplicationBizVO applicationBizVO = this.selectByBizCode(applicationBizParam.getId());
        String bizName = applicationBizParam.getBizName();
        if (StringUtils.isNotBlank(bizName))  applicationBizVO.setBizName(bizName);

        Integer fromUrl = applicationBizParam.getFromUrl();
        if (fromUrl != null) applicationBizVO.setFromUrl(fromUrl);

        return applicationBizVO;
    }

    @Override
    public ApplicationBizVO changeStatusByBizCode(Integer id, String status) {
        ApplicationBizVO applicationBizVO = this.selectByBizCode(id);
        applicationBizVO.setStatus(status);
        return applicationBizVO;
    }

    @Override
    public boolean deleteByBizCode(Integer id) {
        List<ApplicationBizVO> collect = applicationBizList.stream().filter(one -> id.equals(one.getId())).collect(Collectors.toList());
        if (collect.size() > 1) throw new ShopRuntimeException(ResultStatus.MORE_APP_BIZ_EXEC);
        if (collect.size() == 0) return true;
        return applicationBizList.remove(collect.get(0));
    }


    /**
     * 模糊匹配
     * @param applicationBiz
     * @param applicationBizParam
     * @return true: ,fasle:
     */
    private boolean filterApplication(ApplicationBizVO applicationBiz, ApplicationBizParam applicationBizParam) {
        if (applicationBiz == null) return true;

        List<Boolean> notEmptyResult = new ArrayList<>();

        String applicationId = applicationBiz.getApplicationId();
        String applicationIdParam = applicationBizParam.getApplicationId();
        if (StringUtils.isBlank(applicationId) && StringUtils.isBlank(applicationIdParam)) {
            notEmptyResult.add(true);
        } else {
            notEmptyResult.add(StringUtils.isNotBlank(applicationId) && applicationId.contains(applicationIdParam));
        }

        String bizCode = applicationBiz.getBizCode();
        String bizCodeParam = applicationBizParam.getBizCode();
        if (StringUtils.isBlank(bizCode) && StringUtils.isBlank(bizCodeParam)) {
            notEmptyResult.add(true);
        } else {
            notEmptyResult.add(StringUtils.isNotBlank(bizCode) && bizCode.contains(bizCodeParam));
        }

        String bizName = applicationBiz.getBizName();
        String bizNameParam = applicationBizParam.getBizName();
        if (StringUtils.isBlank(bizName) && StringUtils.isBlank(bizNameParam)) {
            notEmptyResult.add(true);
        } else {
            notEmptyResult.add(StringUtils.isNotBlank(bizName) && bizName.contains(bizNameParam));
        }

        Integer fromUrl = applicationBiz.getFromUrl();
        Integer fromUrlParam = applicationBizParam.getFromUrl();
        if (fromUrlParam != null) {
            if (fromUrl == null) {
                notEmptyResult.add(false);
            } else {
                notEmptyResult.add(fromUrl.equals(fromUrlParam));
            }
        }

        String status = applicationBiz.getStatus();
        String statusParam = applicationBizParam.getStatus();
        if (StringUtils.isBlank(status) && StringUtils.isBlank(statusParam)) {
            notEmptyResult.add(true);
        } else {
            notEmptyResult.add(StringUtils.isNotBlank(status) && status.contains(statusParam));
        }

        // 只有所有参数都是true才行
        List<Boolean> containFalse = notEmptyResult.stream().filter(one -> !one).collect(Collectors.toList());

        return !(containFalse.size() > 0);
    }

}


