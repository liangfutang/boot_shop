package com.zjut.shop.service;

import com.zjut.shop.vo.UserVO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {

    private static List<UserVO> userVOList = Collections.synchronizedList(new ArrayList<>());
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
            user.setMgState(random.nextInt(1) != 0);
            if (i==0) {
                user.setRoleName("超级管理员");
                continue;
            }
            user.setRoleName(roleName[i % roleName.length]);
            userVOList.add(user);
        }
    }

    @Override
    public List<UserVO> selectList() {
        return userVOList;
    }

}
