package com.experiment.npe.data.source;

/**
 * Created by lbavsc on 20-9-14
 */
public interface LocalDataSource {
    /**
     * 保存用户ID
     *
     * @param userId
     */
    void saveUserId(String userId);

    /**
     * 保存用户头像
     */
    void saveUserIcon(String userIcon);

    /**
     * 保存用户类型
     */
    void saveUserType(boolean userType);

    /**
     * 保存用户手机号
     */
    void savaUserPhone(String userPhone);

    /**
     * 保存用户名
     */
    void saveUserName(String userName);

    /**
     * 保存用户密码
     */
    void savePassword(String password);

    /**
     * 用户登录状态
     */
    void saveUserStatus(boolean userStatus);

    /**
     * 获取用户手机号
     */
    String getUserPhone();

    /**
     * 获取用户名
     */
    String getUserName();

    /**
     * 获取用户密码
     */
    String getPassword();

    /**
     * 获取用户ID
     */
    String getUserId();

    /**
     * 获取用户头像
     */
    String getUserIcon();

    /**
     * 获取用户性质
     */
    boolean getUserType();

    /**
     * 获取用户登录状态
     */
    boolean getUserStatus();

}
