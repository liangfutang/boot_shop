package com.zjut.shop.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.zjut.shop.enums.ResultStatus;
import com.zjut.shop.execption.ShopRuntimeException;
import com.zjut.shop.query.UserAddParam;
import com.zjut.shop.query.UserParam;
import com.zjut.shop.query.UserRoleParam;
import com.zjut.shop.vo.AuthVO;
import com.zjut.shop.vo.PageResult;
import com.zjut.shop.vo.RoleVO;
import com.zjut.shop.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private static ThreadLocal<SimpleDateFormat> dataFormat = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

    private static final List<UserVO> userVOList = new CopyOnWriteArrayList<>();
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
            String createTime = dataFormat.get().format(new Date(System.currentTimeMillis() - random.nextInt(900000000)));
            user.setCreateTime(createTime);
            if (i==0) {
                user.setRoleName("超级管理员");
                userVOList.add(user);
                continue;
            }
            user.setRoleName(roleName[i % roleName.length]);
            userVOList.add(user);
        }
    }

    /**
     * 为用户分配角色
     */
    @PostConstruct
    public void init() {
        List<RoleVO> roleList = RoleServiceImpl.getRoleList();
        if (CollectionUtil.isEmpty(roleList)) return;

        for (UserVO one : userVOList) {
            // 超级管理员
            if (one.getId() == 0) {
                one.setRoleList(roleList);
                continue;
            }
            Random random = new Random();
            Set<RoleVO> roles = new HashSet<>();
            for (int i=0 ;i < 3; i++) {
                roles.add(roleList.get(random.nextInt(roleList.size())));
            }
            one.setRoleList(new ArrayList<>(roles));
        }
    }

    @Override
    public PageResult<List<UserVO>> selectList(UserParam userParam) {

        List<UserVO> selectUserList = null;
        // 1. 模糊条件查询
        if (StringUtils.isNotBlank(userParam.getQuery())) {
            selectUserList = userVOList.stream().filter(one -> this.filterUser(one, userParam.getQuery())).collect(Collectors.toList());
        } else {
            selectUserList = userVOList;
        }


        // 当前用户数
        int currentUserTotal = selectUserList.size();
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

        return new PageResult<>(currentUserTotal, selectUserList.subList(start, end));
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

    @Override
    public UserVO addUser(UserAddParam userAddParam) {
        int id = 0;
        if (userVOList.size() > 0) {
            // 查找当前所有id中最大的
            UserVO targetUser = userVOList.stream().max(Comparator.comparing(UserVO::getId)).get();
            log.info("新增用户的时候查到当前列表id最大用户:{}", targetUser);
            id = targetUser.getId() + 1;
        }

        UserVO userVO = new UserVO();
        userVO.setId(id);
        userVO.setMgState(true);
        userVO.setCreateTime(dataFormat.get().format(new Date()));
        BeanUtil.copyProperties(userAddParam, userVO);

        userVOList.add(userVO);
        return userVO;
    }

    @Override
    public UserVO deleteUserById(Integer id) {

        UserVO deleteOne = null;
        for (UserVO one : userVOList) {
            if (id.equals(one.getId())) {
                deleteOne = one;
                break;
            }
        }
        if (deleteOne == null) {
            log.error("没找到对应的用户");
            throw new ShopRuntimeException(ResultStatus.NO_USER_EXEC);
        }
        log.info("找到要删除的用户:{}", deleteOne);
        userVOList.remove(deleteOne);
        return deleteOne;
    }

    @Override
    public UserVO selectUserById(Integer id) {
        List<UserVO> users = userVOList.stream().filter(one -> id.equals(one.getId())).collect(Collectors.toList());
        log.info("查到用户列表:{}", users);
        if (users.size() == 0) {
            log.error("没查到对应用户");
            throw new ShopRuntimeException(ResultStatus.NO_USER_EXEC);
        }
        if (users.size() > 1) {
            log.error("查到多个用户，数据异常");
            throw new ShopRuntimeException(ResultStatus.MORE_USER_FAILURE);
        }
        return users.get(0);
    }

    @Override
    public UserVO editUserInfoById(UserVO user) {
        UserVO userVO = this.selectUserById(user.getId());
        String userName = user.getUserName();
        if (StringUtils.isNotBlank(userName)) {
            userVO.setUserName(userName);
        }
        String mobile = user.getMobile();
        if (StringUtils.isNotBlank(mobile)) {
            userVO.setMobile(mobile);
        }
        String email = user.getEmail();
        if (StringUtils.isNotBlank(email)) {
            userVO.setEmail(email);
        }
        return userVO;
    }

    @Override
    public UserVO addUserRoles(Integer id, UserRoleParam userRoleParam) {
        UserVO userVO = this.selectUserById(id);
        List<Integer> rid = userRoleParam.getRid();
        // 过滤掉存在的角色
        List<RoleVO> roleList = userVO.getRoleList();
        if (CollectionUtil.isNotEmpty(roleList)) roleList.forEach(one -> rid.remove(one.getId()));
        // 查出所有的角色
        List<RoleVO> collect = RoleServiceImpl.getRoleList().stream().filter(one -> rid.contains(one.getId())).collect(Collectors.toList());
        // 放进用户中
        if (CollectionUtil.isEmpty(roleList)) {
            userVO.setRoleList(collect);
            return userVO;
        }
        roleList.addAll(collect);
        return userVO;
    }


    /**
     * 校验当前的用户数据是否包含当前查询的字符串
     * @param user
     * @param query
     * @return
     */
    private boolean filterUser(UserVO user, String query) {
        if (user==null || StringUtils.isBlank(query)) {
            return false;
        }

        String userName = user.getUserName();
        if (StringUtils.isNotBlank(userName) && userName.contains(query)) return true;

        String email = user.getEmail();
        if (StringUtils.isNotBlank(email) && email.contains(query)) return true;

        String mobile = user.getMobile();
        if (StringUtils.isNotBlank(mobile) && mobile.contains(query)) return true;

        String roleName = user.getRoleName();
        if (StringUtils.isNotBlank(roleName) && roleName.contains(query)) return true;

        return false;
    }

    public static void main(String[] args) {
        System.out.println("删除前:" + userVOList);
        userVOList.removeIf(one -> new Integer(5).equals(one.getId()));
        System.out.println("删除后:" + userVOList);
    }

}
