package com.experiment.npe.entity;

/**
 * Created by lbavsc on 20-9-11
 * 用户信息类
 */
public class UserEntity {
    private String userId;      //用户ID
    private String phone;       //用户手机号
    private String name;        //用户名
    private String password;    //用户密码
    private boolean type;       //用户帐号类型
    private String icon;        //用户头像
    private boolean status;     //用户登录状态

    public UserEntity() {
    }

    public UserEntity(String phone, String name, String password) {
        this.phone = phone;
        this.name = name;
        this.password = password;
    }

    public UserEntity(String userId, String phone, String name, String password, boolean type, String icon, boolean status) {
        this.userId = userId;
        this.phone = phone;
        this.name = name;
        this.password = password;
        this.type = type;
        this.icon = icon;
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
