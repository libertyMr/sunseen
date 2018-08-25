package com.sunseen.spcontrolsystem.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sunseen.spcontrolsystem.R;
import com.sunseen.spcontrolsystem.fragment.DeviceAdministrationFragment;
import com.sunseen.spcontrolsystem.fragment.FailureWarningFragment;
import com.sunseen.spcontrolsystem.fragment.HomePageFragment;
import com.sunseen.spcontrolsystem.fragment.ParameterSettingFragment;
import com.sunseen.spcontrolsystem.fragment.PaySettingFragment;
import com.sunseen.spcontrolsystem.fragment.SystemLogFragment;
import com.sunseen.spcontrolsystem.fragment.SystemSettingsFragment;
import com.sunseen.spcontrolsystem.fragment.UserAdministrationFragment;
import com.sunseen.spcontrolsystem.fragment.UserLoginFragment;
import com.sunseen.spcontrolsystem.model.DialogModel;
import com.sunseen.spcontrolsystem.model.HandlerMessage;
import com.sunseen.spcontrolsystem.model.RunModel;
import com.sunseen.spcontrolsystem.model.SystemConfig;
import com.sunseen.spcontrolsystem.model.SystemRunModel;
import com.sunseen.spcontrolsystem.model.UserInfoModel;
import com.sunseen.spcontrolsystem.utils.CommonUtils;
import com.sunseen.spcontrolsystem.utils.LogMsg;
import com.sunseen.spcontrolsystem.view.SwitchView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends BaseActivity implements SwitchView.SwitchViewOnClickListener, View.OnClickListener {

    private View home_page,             //主页
            user_administration,        //用户管理
            device_administration,      //设备管理
            system_settings,            //系统设置
            failure_warning;            //故障报警

    private SwitchView system_total_switch;
    private ImageView iv_add_user;

    private FrameLayout fl_layout;

    private ImageView home_page_iv_navigation,
            user_administration_iv_navigation,
            device_administration_iv_navigation,
            system_settings_iv_navigation,
            failure_warning_iv_navigation;

    private TextView home_page_tv_navigation,
            user_administration_tv_navigation,
            device_administration_tv_navigation,
            system_settings_tv_navigation,
            failure_warning_tv_navigation;

    private LinearLayout user_admin_title, home_title;

    private Handler mHandler = getHandler();

    private LinearLayout layout;//根布局

    SystemSettingsFragment SystemFragment;
    PaySettingFragment paySettingFragment;
    SystemLogFragment systemLogFragment;

    HomePageFragment HomeFragment;
    DeviceAdministrationFragment deviceFragment;
    FailureWarningFragment failureFragment;

    UserLoginFragment userLoginFragment;
    UserAdministrationFragment userFragment;

    ParameterSettingFragment parameterSettingFragment;


    private ExecutorService executorService;

    private int time = 600;

    private boolean activity_opause = false;

    @Override
    protected void onResume() {
        super.onResume();
        layout.setSystemUiVisibility(View.INVISIBLE);
        activity_opause = false;
    }

    @Override
    protected void
    onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        executorService = Executors.newCachedThreadPool();
        initSystem();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdown();
        SystemRunModel.VIP = false;
        dbManager.closeDB();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        time = 600;
        LogMsg.printDebugMsg("dispatchTouchEvent --> time=" + time);
        return super.dispatchTouchEvent(ev);
    }

    /**
     * @param msg apk中的handler消息都经此处理
     */
    @Override
    protected void handleStateMessage(Message msg) {
        LogMsg.printDebugMsg("handleStateMessage msg = " + msg.what);
        switch (msg.what) {
            case HandlerMessage.USER_CHECKOUT_RESULT: {
                UserCheckoutResult(msg);
                break;
            }
            case HandlerMessage.LOGIN_TIME_OVER: {
                LoginTimeOver(msg);
                break;
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogMsg.printDebugMsg("onstop");
    }

    @Override
    protected void onPause() {
        super.onPause();
        activity_opause = true;
        LogMsg.printDebugMsg("onPause");
    }


    /**
     * @param isClicked 对自定义switch按钮触摸事件的回调，
     *                  是处于on状态还是off状态
     */
    @Override
    public void isClicked(View view, boolean isClicked) {
        switch (view.getId()) {
            case R.id.system_total_switch://系统总开关
            {
                LogMsg.printDebugMsg("system_total_switch, isClicked == " + isClicked);
                break;
            }
        }
    }

    /**
     * @param v 其他view的点击事件处理
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_page://主页
            {
                setFocus(1);
                home_title.setVisibility(View.VISIBLE);
                user_admin_title.setVisibility(View.GONE);
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                if (HomeFragment == null) {
                    HomeFragment = new HomePageFragment();
                }
                transaction.replace(R.id.fl_layout, HomeFragment);
                transaction.commit();
                break;
            }
            case R.id.user_administration://用户管理
            {
                setFocus(2);
                home_title.setVisibility(View.GONE);
                user_admin_title.setVisibility(View.VISIBLE);
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                if (!RunModel.isLogin) {
                    if (userLoginFragment == null) {
                        userLoginFragment = new UserLoginFragment();
                    }
                    transaction.replace(R.id.fl_layout, userLoginFragment);
                } else {
                    if (userFragment == null) {
                        userFragment = new UserAdministrationFragment();
                    }
                    transaction.replace(R.id.fl_layout, userFragment);
                }
                transaction.commit();
                break;
            }
            case R.id.device_administration://设备管理
            {
                setFocus(3);
                home_title.setVisibility(View.INVISIBLE);
                user_admin_title.setVisibility(View.GONE);
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                if (deviceFragment == null) {
                    deviceFragment = new DeviceAdministrationFragment();
                }
                transaction.replace(R.id.fl_layout, deviceFragment);
                transaction.commit();
                break;
            }
            case R.id.system_settings://系统设置
            {
                setFocus(4);
                home_title.setVisibility(View.INVISIBLE);
                user_admin_title.setVisibility(View.GONE);
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                if (SystemFragment == null) {
                    SystemFragment = new SystemSettingsFragment();
                }
                transaction.replace(R.id.fl_layout, SystemFragment);
                transaction.commit();
                break;
            }
            case R.id.failure_warning://报警
            {
                setFocus(5);
                home_title.setVisibility(View.INVISIBLE);
                user_admin_title.setVisibility(View.GONE);

                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                if (failureFragment == null) {
                    failureFragment = new FailureWarningFragment();
                }
                transaction.replace(R.id.fl_layout, failureFragment);
                transaction.commit();
                break;
            }
            case R.id.iv_add_user: {
                showDialog();
                break;
            }
        }
    }

    /**
     * @param id 设置首页点击效果
     */
    private void setFocus(int id) {
        home_page_iv_navigation.setImageDrawable(getResources().getDrawable(R.drawable.home_page));
        home_page_tv_navigation.setTextColor(Color.GRAY);

        user_administration_iv_navigation.setImageDrawable(getResources().getDrawable(R.drawable.user_administration));
        user_administration_tv_navigation.setTextColor(Color.GRAY);

        device_administration_iv_navigation.setImageDrawable(getResources().getDrawable(R.drawable.device_administration));
        device_administration_tv_navigation.setTextColor(Color.GRAY);

        system_settings_iv_navigation.setImageDrawable(getResources().getDrawable(R.drawable.system_settings_no_focus));
        system_settings_tv_navigation.setTextColor(Color.GRAY);

        failure_warning_iv_navigation.setImageDrawable(getResources().getDrawable(R.drawable.failure_warning_no_warning));
        failure_warning_tv_navigation.setTextColor(Color.GRAY);

        switch (id) {
            case 1:
                home_page_iv_navigation.setImageDrawable(getResources().getDrawable(R.drawable.home_page_focus));
                home_page_tv_navigation.setTextColor(getResources().getColor(R.color.sky_blue_homepage_item));
                break;
            case 2:
                user_administration_iv_navigation.setImageDrawable(getResources().getDrawable(R.drawable.user_administration_focus));
                user_administration_tv_navigation.setTextColor(getResources().getColor(R.color.sky_blue_homepage_item));
                break;
            case 3:
                device_administration_iv_navigation.setImageDrawable(getResources().getDrawable(R.drawable.device_administration_focus));
                device_administration_tv_navigation.setTextColor(getResources().getColor(R.color.sky_blue_homepage_item));
                break;
            case 4:
                system_settings_iv_navigation.setImageDrawable(getResources().getDrawable(R.drawable.system_settings_focus));
                system_settings_tv_navigation.setTextColor(getResources().getColor(R.color.sky_blue_homepage_item));
                break;
            case 5:
                failure_warning_iv_navigation.setImageDrawable(getResources().getDrawable(R.drawable.failure_warning_no_warning_focus));
                failure_warning_tv_navigation.setTextColor(getResources().getColor(R.color.sky_blue_homepage_item));
                break;
        }
    }

    public Handler getmHandler() {
        return super.getHandler();
    }

    public ExecutorService getExecutorService() {
        return this.executorService;
    }

    /**
     * 点击系统设置中的item后的操作
     */
    public void SystemSettingListItemClick() {
        switch (RunModel.setting_type) {
            case RunModel.PAY_SETTING://付费设置
            {
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                if (paySettingFragment == null) {
                    paySettingFragment = new PaySettingFragment();
                }
                transaction.replace(R.id.fl_layout, paySettingFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            }
            case RunModel.PARAMETER_SETTING://参数设置
            {
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                if (parameterSettingFragment == null) {
                    parameterSettingFragment = new ParameterSettingFragment();
                }
                transaction.replace(R.id.fl_layout, parameterSettingFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            }
            case RunModel.SYSTEM_LOG://系统日志
            {
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                if (systemLogFragment == null) {
                    systemLogFragment = new SystemLogFragment();
                }
                transaction.replace(R.id.fl_layout, systemLogFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            }
            case RunModel.UPGRAD_SETTING: //升级
            {
                break;
            }
        }
    }

    public void showDialog() {
//                no = position;
        Intent intent = new Intent();
        intent.putExtra("DIALOG_MODE", DialogModel.USER_ADMIN_ADD_DIALOG);
//        intent.putExtra("position", position);
        Bundle bundle = new Bundle();
//                bundle.putSerializable("FailureWarningModel", list.get(position));
        intent.putExtras(bundle);
        intent.setClass(MainActivity.this, AddUserActivity.class);
        startActivityForResult(intent, 1);
    }

    public void showSelfDialog(int mode, Object obj) {
        Intent intent = new Intent();
        intent.putExtra("DIALOG_MODE", mode);
//        intent.putExtra("position", position);
        Bundle bundle = new Bundle();
        if (mode == DialogModel.USER_ADMIN_DELETE_DIALOG) {
            bundle.putSerializable("Data", (UserInfoModel) obj);
        }
        intent.putExtras(bundle);
        intent.setClass(MainActivity.this, SelfDialogActivity.class);
        startActivityForResult(intent, 1);
    }

    /**
     * @param msg
     * UserCheckoutResult
     */
    private void UserCheckoutResult(Message msg)
    {
        boolean isChecked = (boolean) msg.obj;
        if (isChecked) {
//                    SystemRunModel.VIP = true;
            RunModel.isLogin = true;//登录成功
            home_title.setVisibility(View.GONE);
            user_admin_title.setVisibility(View.VISIBLE);
            setViewClickable(true);
        } else {
            RunModel.isLogin = false;//登录失败
            Toast.makeText(MainActivity.this, "账号或密码错误", Toast.LENGTH_SHORT).show();
        }
        if (RunModel.isLogin) {
            setFocus(2);
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            if (userFragment == null) {
                userFragment = new UserAdministrationFragment();
            }
            transaction.replace(R.id.fl_layout, userFragment);
            transaction.commit();
        }
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                while (time >= 0) {
                    try {
                        Thread.sleep(1000);
                        time--;
//                                LogMsg.printDebugMsg("Runnable time-->" + time);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (RunModel.isLogin)
                {
//                    RunModel.isLogin = false;
                    time = 600;
                    Message msg = mHandler.obtainMessage();
                    msg.what = HandlerMessage.LOGIN_TIME_OVER;
                    mHandler.sendMessage(msg);
                }
            }
        });
    }

    /**
     * @param msg
     * LoginTimeOver
     */
    private void LoginTimeOver(Message msg)
    {
        if (!activity_opause) {
            if (SystemRunModel.LOGIN_USER_INFO != null) {
                SystemRunModel.LOGIN_USER_INFO = null;
            }
            userFragment = null;
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            if (userLoginFragment != null) {
                userLoginFragment = null;
            }
            home_title.setVisibility(View.GONE);
            user_admin_title.setVisibility(View.GONE);
            setFocus(2);
            userLoginFragment = new UserLoginFragment();
            transaction.replace(R.id.fl_layout, userLoginFragment);
            transaction.commit();
            RunModel.isLogin = false;
        } else {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    //TODO 修改post_time = 3
                    //pause后一直发，等待activity到前台，时间为5s
                    int post_time = 3;
                    while (post_time >= 0) {
                        try {
                            Thread.sleep(1000);
                            post_time--;
//                                    LogMsg.printDebugMsg("Runnable time-->" + post_time);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
//                    RunModel.isLogin = false;
                    post_time = 3;
                    Message msg = mHandler.obtainMessage();
                    msg.what = HandlerMessage.LOGIN_TIME_OVER;
                    mHandler.removeMessages(HandlerMessage.LOGIN_TIME_OVER);
                    mHandler.sendMessage(msg);
                }
            });
        }
    }

    /**
     * view初始化
     */
    private void initView() {
        layout = findViewById(R.id.layout);
        home_page = findViewById(R.id.home_page);
        user_administration = findViewById(R.id.user_administration);
        device_administration = findViewById(R.id.device_administration);
        system_settings = findViewById(R.id.system_settings);
        failure_warning = findViewById(R.id.failure_warning);
        system_total_switch = findViewById(R.id.system_total_switch);
        fl_layout = findViewById(R.id.fl_layout);

        home_page_iv_navigation = home_page.findViewById(R.id.iv_navigation);
        user_administration_iv_navigation = user_administration.findViewById(R.id.iv_navigation);
        device_administration_iv_navigation = device_administration.findViewById(R.id.iv_navigation);
        system_settings_iv_navigation = system_settings.findViewById(R.id.iv_navigation);
        failure_warning_iv_navigation = failure_warning.findViewById(R.id.iv_navigation);

        home_page_tv_navigation = home_page.findViewById(R.id.tv_navigation);
        user_administration_tv_navigation = user_administration.findViewById(R.id.tv_navigation);
        device_administration_tv_navigation = device_administration.findViewById(R.id.tv_navigation);
        system_settings_tv_navigation = system_settings.findViewById(R.id.tv_navigation);
        failure_warning_tv_navigation = failure_warning.findViewById(R.id.tv_navigation);

        user_admin_title = findViewById(R.id.user_admin_title);
        home_title = findViewById(R.id.home_title);

        iv_add_user = findViewById(R.id.iv_add_user);

        home_page_iv_navigation.setImageDrawable(getResources().getDrawable(R.drawable.home_page));
        user_administration_iv_navigation.setImageDrawable(getResources().getDrawable(R.drawable.user_administration));
        device_administration_iv_navigation.setImageDrawable(getResources().getDrawable(R.drawable.device_administration));
        system_settings_iv_navigation.setImageDrawable(getResources().getDrawable(R.drawable.system_settings_no_focus));
        failure_warning_iv_navigation.setImageDrawable(getResources().getDrawable(R.drawable.failure_warning_no_warning));

        home_page_tv_navigation.setText(R.string.home_page);
        user_administration_tv_navigation.setText(R.string.user_administration);
        device_administration_tv_navigation.setText(R.string.device_administration);
        system_settings_tv_navigation.setText(R.string.system_settings);
        failure_warning_tv_navigation.setText(R.string.failure_warning);

        system_total_switch.setListener(this);
        system_total_switch.setmColorOnView(getResources().getColor(R.color.yellow_self));
        system_total_switch.setmColorOffView(Color.GRAY);
        system_total_switch.setmTextSize(23);
        system_total_switch.setmColorText(getResources().getColor(R.color.yellow_self));

        home_page.setOnClickListener(this);
        user_administration.setOnClickListener(this);
        device_administration.setOnClickListener(this);
        system_settings.setOnClickListener(this);
        failure_warning.setOnClickListener(this);

        iv_add_user.setOnClickListener(this);

        home_title.setVisibility(View.VISIBLE);
        setFocus(0);
    }

    private void setViewClickable(boolean flag) {
        home_page.setClickable(flag);
        user_administration.setClickable(flag);
        device_administration.setClickable(flag);
        system_settings.setClickable(flag);
        failure_warning.setClickable(flag);
        iv_add_user.setClickable(flag);
        system_total_switch.setClickable(flag);
    }

    /**
     * 系统初始化
     * 检测用户是否到期
     */
    public void initSystem() {
//        String run_time = dbManager.queryConfigValue(SystemConfig.RUN_TIME, "0");//运行时常
//        String can_run_time = dbManager.queryConfigValue(SystemConfig.CAN_RUN_TIME, "0");//可运行时间，用于计费
//        String isPay = dbManager.queryConfigValue(SystemConfig.IS_PAY, "false");//标记是否付费
        int flow_model = Integer.valueOf(dbManager.queryConfigValue(SystemConfig.FLOW_MODEL, "-1"));
        SystemConfig.FlowModel = flow_model;

//        LogMsg.printDebugMsg("run_time-->" + run_time + ", can_run_time-->" + can_run_time + ", is_pay-->" + isPay);
//        if (!Boolean.valueOf(isPay)) {
//            dbManager.insertSystemConfig(new SystemConfig(SystemConfig.IS_PAY, "false"));
//        }
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        if (CommonUtils.checkOverdue(MainActivity.this)) {//使用时间未超时
            SystemRunModel.VIP = true;
            setFocus(1);
            home_title.setVisibility(View.VISIBLE);
            user_admin_title.setVisibility(View.GONE);
            if (HomeFragment == null) {
                HomeFragment = new HomePageFragment();
            }
            transaction.replace(R.id.fl_layout, HomeFragment);
            transaction.commit();
        } else {//使用超时，只显示用户登入页面，其它不可点击
            SystemRunModel.VIP = false;
            setFocus(2);
            setViewClickable(false);
            home_title.setVisibility(View.GONE);
            user_admin_title.setVisibility(View.GONE);
            if (userLoginFragment == null) {
                userLoginFragment = new UserLoginFragment();
            }
            transaction.replace(R.id.fl_layout, userLoginFragment);
            transaction.commit();
            //TODO 弹框输入密码
            Intent intent = new Intent();
            intent.setClass(MainActivity.this,CheckActivity.class);
            startActivity(intent);
        }
    }
}
