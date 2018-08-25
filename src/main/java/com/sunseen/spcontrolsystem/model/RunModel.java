package com.sunseen.spcontrolsystem.model;

/**
 * 系统的运行模式
 */
public class RunModel {

    public static final int FILTER_MODLE = 1;           //  过滤模式
    public static final int NOT_FILTER_MODLE = 2;       //非过滤模式
    public static final int SELF_DRAINAGE_MODLE = 3;    //自排水模式
    public static final int FORCE_DRAINAGE_MODLE = 4;   //强排水模式
    public static final int BACKWASH = 5;               //反冲洗模式
    public static final int CLOSEMODE = 6;              //关闭模式

    //根据水温的运行模式
    public static final int HEAT_MODLE = 7;             //水加热模式
    public static final int NORMA_MODLE = 8;            //水常温模式

    //除湿要求
    public static final int DEHUMIDIFIER_MODLE = 9;      //除湿制热模式
    public static final int DEHUMIDIFIER_WATER_MODLE = 10;//除湿制热水模式
    public static final int DEHUMIDIFIER_COLD_MODLE = 11; //除湿制冷模式

    public static boolean isLogin = false;               //标记用户是否登陆

    //system setting model
    public static int setting_type = -1;
    public static final int PAY_SETTING = 12;           //付费设置
    public static final int PARAMETER_SETTING = 13;      //参数设置
    public static final int SYSTEM_LOG = 14;             //系统日志
    public static final int UPGRAD_SETTING = 15;          //升级设置
}
