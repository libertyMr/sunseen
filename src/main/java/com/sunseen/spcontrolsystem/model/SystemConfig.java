package com.sunseen.spcontrolsystem.model;

public class SystemConfig {
    /**
     * run_time 已运行时常
     * can_run_time 可运行时常 15811200--30年分钟数
     * is_pay 是否付费
     * flow_model 水流模式 1 顺流式, 2逆流式
     * first_phase_time ---第一期到期时间
     * first_phase_pwd ---第一期密码
     * second_phase_pwd
     * third_phase_pwd
     * fourth_phase_pwd
     * fifth_phase_pwd
     * sixth_phase_pwd
     */
    private String name;
    private String value;

    public SystemConfig(String name, String value)
    {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

//    dbManager.insertSystemConfig(new SystemConfig("first_phase_time", first));
//            dbManager.insertSystemConfig(new SystemConfig("second_phase_time", second));
//            dbManager.insertSystemConfig(new SystemConfig("third_phase_time", third));
//            dbManager.insertSystemConfig(new SystemConfig("fourth_phase_time", fourth));
//            dbManager.insertSystemConfig(new SystemConfig("fifth_phase_time", fifth));
//            dbManager.insertSystemConfig(new SystemConfig("sixth_phase_time", sixth));

    public static final String FIRST_PHASE_TIME = "first_phase_time";
    public static final String SECOND_PHASE_TIME = "second_phase_time";
    public static final String THIRD_PHASE_TIME = "third_phase_time";
    public static final String FOURTH_PHASE_TIME = "fourth_phase_time";
    public static final String FIFTH_PHASE_TIME = "fifth_phase_time";
    public static final String SIXTH_PHASE_TIME = "sixth_phase_time";

    public static final String FIRST_PHASE_PWD = "first_phase_pwd";
    public static final String SECOND_PHASE_PWD = "second_phase_pwd";
    public static final String THIRD_PHASE_PWD = "third_phase_pwd";
    public static final String FOURTH_PHASE_PWD = "fourth_phase_pwd";
    public static final String FIFTH_PHASE_PWD = "fifth_phase_pwd";
    public static final String SIXTH_PHASE_PWD = "sixth_phase_pwd";

    public static final String RUN_TIME = "run_time";
    public static final String CAN_RUN_TIME = "can_run_time";
    public static final String IS_PAY = "is_pay";
    public static final String FLOW_MODEL = "flow_model";


    public static String RunTime = "0";
    public static String CanRunTime = "0";
    public static boolean IsPay = false;
    public static int FlowModel = -1;//DOWNSTREAM = "顺流式" == 1, BACKFLOW = "逆流式" == 2;
}
