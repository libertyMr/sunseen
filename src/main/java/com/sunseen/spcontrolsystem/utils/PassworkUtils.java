package com.sunseen.spcontrolsystem.utils;

import com.sunseen.spcontrolsystem.db.DatabaseManager;
import com.sunseen.spcontrolsystem.model.UserInfoModel;

public class PassworkUtils {
    public int getAccesslevel() {
        return 1;
    }

    /**
     * @param dbManager
     * @param user_name
     * @param pwd
     * @return
     * 对用户登录进行检测
     */
    public static UserInfoModel checkout(DatabaseManager dbManager, String user_name, String pwd) {
//        dbManager.insertUserInfo();
        UserInfoModel userInfoModel = dbManager.queryUserInfoForUserName(user_name);
        return userInfoModel;
    }

    //first_phase_time
    //first_phase_pwd

    /**
     * @return
     * 检测是否到期，true==到期，false==未到期
     */
    public static boolean checkExpire()
    {

        return false;
    }
}
