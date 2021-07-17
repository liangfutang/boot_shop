package com.zjut.shop.service;

import com.zjut.shop.query.ApplicationBizParam;
import com.zjut.shop.vo.ApplicationBizVO;
import com.zjut.shop.vo.ApplicationVO;
import com.zjut.shop.vo.FromURLVO;
import com.zjut.shop.vo.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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


    /**
     * 模糊匹配
     * @param applicationBiz
     * @param applicationBizParam
     * @return true: ,fasle:
     */
    private boolean filterApplication(ApplicationBizVO applicationBiz, ApplicationBizParam applicationBizParam) {
        if (applicationBiz == null) return true;

        List<Boolean> notEmptyResult = new ArrayList<>();

        String bizName = applicationBiz.getBizName();
        String bizNameParam = applicationBizParam.getBizName();
        if (StringUtils.isBlank(bizName) && StringUtils.isBlank(bizNameParam)) {
            notEmptyResult.add(true);
        } else {
            notEmptyResult.add(StringUtils.isNotBlank(bizName) && bizName.contains(bizNameParam));
        }

        Integer fromUrl = applicationBiz.getFromUrl();
        Integer fromUrlParam = applicationBizParam.getFromUrl();
        if (fromUrl==null && fromUrlParam==null) {
            notEmptyResult.add(true);
        } else {
            notEmptyResult.add(fromUrl!=null && fromUrl.equals(fromUrlParam));
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


