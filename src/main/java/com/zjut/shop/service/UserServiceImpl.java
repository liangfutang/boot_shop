package com.zjut.shop.service;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.zjut.shop.enums.ResultStatus;
import com.zjut.shop.execption.ShopRuntimeException;
import com.zjut.shop.query.UserParam;
import com.zjut.shop.vo.PageResult;
import com.zjut.shop.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private static List<UserVO> userVOList = new CopyOnWriteArrayList<>();
    static {
        String[] userNames = {"安雅萍","白百合","白冰","陈钰琪","陈冲","陈红","陈妍希","陈意涵","陈乔恩","陈紫涵","楚月","程愫","蔡依林","陈数"
                ,"蔡少芬","陈美琪","陈晓旭","陈瑶","程瑷瑗"};
        String[] userNameCN = {"An Yaping", "White Lily", "Bai Bing", "Chen Yuqi", "Chen Chong", "Chen Hong", "Chen Yanxi", "Chen Yihan", "Chen Qiaoen", "Chen Zihan", "Chu Yue", "Chensu","Jolin Tsai","Chen Shu","Cai Shaofen","Chen Meiqi","Chen Xiaoxu","Chen Yao","Cheng Aiyuan"};
        String[] emailAft = {"@qq.com", "@sina.com", "@163.com", "@gmail.com"};
        String[] mobile = {"15068720000", "15068720001", "15068720002", "15068720003", "15068720004", "15068720005", "15068720006", "15068720007", "15068720008", "15068720009", "15068720010", "15068720011", "15068720012", "15068720013" };
        String[] roleName = {"管理员", "项目经理", "产品经理", "车间主任", "总裁秘书", "高级经理", "前端程序员", "后端程序员"};
        Random random = new Random();
        for (int i=0; i<12; i++) {
            UserVO user = new UserVO();
            user.setId(i);
            user.setUserName(userNames[i]);
            user.setEmail(userNameCN[i] + emailAft[i % emailAft.length]);
            user.setMobile(mobile[i]);
            user.setMgState(random.nextInt(2) != 0);
            if (i==0) {
                user.setRoleName("超级管理员");
                userVOList.add(user);
                continue;
            }
            user.setRoleName(roleName[i % roleName.length]);
            userVOList.add(user);
        }
    }

    @Override
    public PageResult<List<UserVO>> selectList(UserParam userParam) {
        // 当前用户数
        int currentUserTotal = userVOList.size();
        // 查询的起点
        int start = userParam.getPageNum() * userParam.getPageSize();
        int end = start + userParam.getPageSize();

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
        System.out.println(userVOList.get(11));

        return new PageResult<>(currentUserTotal, userVOList.subList(start, end));
    }

    @Override
    public UserVO updateStatus(Integer id, Boolean mgState) {
        List<UserVO> users = userVOList.stream().filter(one -> id.equals(one.getId())).collect(Collectors.toList());
        log.info("根据id查出的用户:{}", JSONObject.toJSONString(users));
        if (users.size() > 1) {
            log.error("存在多个用户");
            throw new ShopRuntimeException(ResultStatus.MORE_USER_FAILURE.getCode(), ResultStatus.MORE_USER_FAILURE.getMsg());
        }
        if (CollectionUtil.isEmpty(users)) {
            log.error("没查到相应的用户");
            throw new ShopRuntimeException(ResultStatus.NO_USER_FAILURE.getCode(), ResultStatus.NO_USER_FAILURE.getMsg());
        }

        // 修改查到的用户状态
        UserVO userVO = users.get(0);
        users.get(0).setMgState(mgState);
        return userVO;
    }

}
