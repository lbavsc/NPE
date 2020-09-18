package com.experiment.npe.data.source.local;

import com.experiment.npe.data.source.LocalDataSource;

import me.goldze.mvvmhabit.utils.SPUtils;

/**
 * 本地数据源
 * Created by lbavsc on 20-9-14
 */
public class LocalDataSourceImpl implements LocalDataSource {
    private volatile static LocalDataSourceImpl INSTANCE = null;

    public static LocalDataSourceImpl getInstance() {
        if (INSTANCE == null) {
            synchronized (LocalDataSourceImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new LocalDataSourceImpl();
                }
            }
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    private LocalDataSourceImpl() {
        //数据库Helper构建
    }

    @Override
    public void saveUserId(String userId) {
        SPUtils.getInstance().put("UserId", userId);
    }

    @Override
    public void saveUserIcon(String userIcon) {
        SPUtils.getInstance().put("UserIcon", userIcon);
    }

    @Override
    public void saveUserType(boolean userType) {
        SPUtils.getInstance().put("UserType", userType);
    }

    @Override
    public void savaUserPhone(String userPhone) {
        SPUtils.getInstance().put("UserPhone", userPhone);
    }

    @Override
    public void saveUserName(String userName) {
        SPUtils.getInstance().put("UserName", userName);
    }

    @Override
    public void savePassword(String password) {
        SPUtils.getInstance().put("Password", password);
    }

    @Override
    public void saveUserStatus(boolean userStatus) {
        SPUtils.getInstance().put("UserStatus", userStatus);
    }


    @Override
    public String getUserPhone() {
        return SPUtils.getInstance().getString("UserPhone");
    }

    @Override
    public String getUserName() {
        return SPUtils.getInstance().getString("UserName");
    }

    @Override
    public String getPassword() {
        return SPUtils.getInstance().getString("Password");
    }

    @Override
    public String getUserId() {
        return SPUtils.getInstance().getString("UserId");
    }

    @Override
    public String getUserIcon() {
        return SPUtils.getInstance().getString("UserIcon");
    }

    @Override
    public boolean getUserType() {
        return SPUtils.getInstance().getBoolean("UserType");
    }

    @Override
    public boolean getUserStatus() {
        return SPUtils.getInstance().getBoolean("UserStatus");
    }

}
