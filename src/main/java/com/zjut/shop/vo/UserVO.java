package com.zjut.shop.vo;

import lombok.Data;

import java.util.List;

@Data
public class UserVO {

     private Integer id;
     private String userName;
     private String mobile;
     private String type;
     private String email;
     private String createTime;
     /**
      * 用户状态
      */
     private Boolean mgState;
     private String roleName;


     private List<RoleVO> roleList;

}
