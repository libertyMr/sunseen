package com.sunseen.spcontrolsystem.model;

/**
 * 系统运行中的参数
 */
public class SystemRunModel {

    public static int SYSTEM_RUNNING_MODLE = -1;            //当前系统运行模式，根据RunModle.java中的数据进行设置

    public static boolean VIP = false;

    public static UserInfoModel LOGIN_USER_INFO = null;
//
//    public static boolean WATER_CYCLE_RUNNING = false;      //水循环系统是否运行标志位
//    public static boolean FILTER_RUNNING = false;           //过滤系统是否运行标志位
//    public static boolean WATER_MONITORING_RUNNING = false; //水质检测系统是否运行标志位
//    public static boolean DISINFECTION_RUNNING = false;     //消毒系统是否运行标志位
//    public static boolean WATER_HEATING_RUNNING = false;    //水加热系统是否运行标志位
//    public static boolean DEHUMIDIFIER_RUNNING = false;     //除湿系统是否运行标志位
//    public static boolean LIGHTING_RUNNING = false;         //灯光系统是否运行标志位
//    public static boolean COMPUTER_ROOM_RUNNING = false;    //机房控制系统是否运行标志位
//    public static boolean SPA_RUNNING = false;              //SPA系统是否运行标志位
//    public static boolean WATER_SUPPLY_RUNNING = false;     //排给水系统是否运行标志位
//    public static boolean SAFETY_RUNNING = false;           //安全保温系统是否运行标志位
//
//    /**
//     * 在apk被拉起时，在application中调用此方法，用与初始化状态位
//     * 如果是系统异常挂死，需要读取保存数据进行设置
//     */
    public static void SystemConfigInit()
    {
        SYSTEM_RUNNING_MODLE = -1;        //当前系统运行模式，根据RunModle.java中的数据进行设置
        VIP = false;
        LOGIN_USER_INFO = null;
//        WATER_CYCLE_RUNNING = false;      //水循环系统是否运行标志位
//        FILTER_RUNNING = false;           //过滤系统是否运行标志位
//        WATER_MONITORING_RUNNING = false; //水质检测系统是否运行标志位
//        DISINFECTION_RUNNING = false;     //消毒系统是否运行标志位
//        WATER_HEATING_RUNNING = false;    //水加热系统是否运行标志位
//        DEHUMIDIFIER_RUNNING = false;     //除湿系统是否运行标志位
//        LIGHTING_RUNNING = false;         //灯光系统是否运行标志位
//        COMPUTER_ROOM_RUNNING = false;    //机房控制系统是否运行标志位
//        SPA_RUNNING = false;              //SPA系统是否运行标志位
//        WATER_SUPPLY_RUNNING = false;     //排给水系统是否运行标志位
//        SAFETY_RUNNING = false;           //安全保温系统是否运行标志位
    }
}
