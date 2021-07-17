package com.zjut.shop.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.zjut.shop.enums.FromUrlParam;
import com.zjut.shop.enums.ResultStatus;
import com.zjut.shop.execption.ShopRuntimeException;
import com.zjut.shop.vo.FromURLVO;
import com.zjut.shop.vo.PageResult;
import lombok.Getter;
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
public class FromURLServiceImpl implements FromURLService {
    private static ThreadLocal<SimpleDateFormat> dataFormat = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

    @Getter
    private static final List<FromURLVO> fromURLList = new CopyOnWriteArrayList<>();
    static {
        String[] descs = {"奇瑞", "比亚迪", "大众", "华为", "中兴", "移动", "电信", "联通"};
        Random random = new Random();
        for (int i=0; i< descs.length; i++) {
            FromURLVO fromURLVO = new FromURLVO();
            fromURLVO.setValue(i);
            String name = descs[random.nextInt(descs.length)];
            fromURLVO.setName("【" + name + "】");
            fromURLVO.setDesc(name + "的短信前缀内容");
            fromURLVO.setStatus(random.nextInt(2) == 0 ? "Y" : "N");
            String createTime = dataFormat.get().format(new Date(System.currentTimeMillis() - random.nextInt(900000000)));
            fromURLVO.setGmtCreateStr(createTime);
            fromURLList.add(fromURLVO);
        }
    }

    @Override
    public PageResult<List<FromURLVO>> selectList(FromUrlParam fromUrlParam) {
        // 当前前缀总数
        int currentFromUrlTotal = fromURLList.size();
        // 查询的起点
        int start = fromUrlParam.getPageNum() * fromUrlParam.getPageSize();
        int end = start + fromUrlParam.getPageSize();

        // 1. 如果起点超出范围则返回为空
        if (start > currentFromUrlTotal-1) {
            log.info("当前查询页超标啦");
            return new PageResult<>(currentFromUrlTotal, new ArrayList<>());
        }

        // 2. 如果终点超出了范围，则按最后一个算
        if (end > currentFromUrlTotal-1) {
            log.info("终点超出了");
            end = currentFromUrlTotal;
        }
        return new PageResult<>(currentFromUrlTotal, fromURLList.subList(start, end));
    }

    @Override
    public FromURLVO selectListByValue(Integer value) {
        List<FromURLVO> collect = fromURLList.stream().filter(one -> value.equals(one.getValue())).collect(Collectors.toList());
        log.info("根据id查到的前缀列表:{}", collect);
        if (CollectionUtil.isEmpty(collect)) {
            log.error("对应的前缀不存在");
            throw new ShopRuntimeException(ResultStatus.NO_FROMURL_EXEC);
        }

        if (collect.size() > 1) {
            log.error("根据id查到多条数据，数据异常");
            throw new ShopRuntimeException(ResultStatus.MORE_FROMURL_EXEC);
        }
        return collect.get(0);
    }

    @Override
    public FromURLVO changeStatusById(Integer value, String status) {
        FromURLVO fromURLVO = this.selectListByValue(value);
        fromURLVO.setStatus(status);
        return fromURLVO;
    }

    @Override
    public boolean deleteFromUrlByValue(Integer value) {
        FromURLVO fromURLVO = this.selectListByValue(value);
        return fromURLList.remove(fromURLVO);
    }

    @Override
    public FromURLVO updateFromUrl(FromUrlParam fromUrlParam) {
        FromURLVO fromURLVO = this.selectListByValue(fromUrlParam.getValue());

        String name = fromUrlParam.getName();
        if (StringUtils.isNotBlank(name)) {
            log.info("修改前缀显示内容");
            fromURLVO.setName(name);
        }

        String desc = fromUrlParam.getDesc();
        if (StringUtils.isNotBlank(desc)) {
            log.info("修改描述");
            fromURLVO.setDesc(desc);
        }
        return fromURLVO;
    }

    @Override
    public FromURLVO addFromUrl(FromUrlParam fromUrlParam) {
        List<FromURLVO> collect = fromURLList.stream().filter(one -> fromUrlParam.getValue().equals(one.getValue())).collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(collect)) {
            log.error("当前前缀值已经存在了");
            throw new ShopRuntimeException(ResultStatus.EXITS_FROMURL_EXEC);
        }
        FromURLVO fromURLVO = new FromURLVO();
        BeanUtil.copyProperties(fromUrlParam, fromURLVO);
        fromURLList.add(fromURLVO);
        return fromURLVO;
    }

}
