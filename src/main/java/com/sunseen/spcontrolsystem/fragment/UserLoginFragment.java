package com.sunseen.spcontrolsystem.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sunseen.spcontrolsystem.R;
import com.sunseen.spcontrolsystem.activity.MainActivity;
import com.sunseen.spcontrolsystem.db.DatabaseManager;
import com.sunseen.spcontrolsystem.model.HandlerMessage;
import com.sunseen.spcontrolsystem.model.SystemRunModel;
import com.sunseen.spcontrolsystem.model.UserInfoModel;
import com.sunseen.spcontrolsystem.utils.LogMsg;
import com.sunseen.spcontrolsystem.utils.PassworkUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UserLoginFragment extends Fragment implements View.OnClickListener {
    private View rootView;
    private EditText tv_user_login_name, tv_user_login_pwd;
    private Button bt_user_login_ok;
    private String user_name;
    private String user_pwd;
    private boolean result = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
//        initData();
        if (null != rootView) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (null != parent) {
                parent.removeView(rootView);
            }
        } else {
            rootView = inflater.inflate(R.layout.user_login_fragment, container, false);
            LogMsg.printDebugMsg("UserLoginFragment onCreateView");
            initView(rootView);// 控件初始化
        }
        return rootView;
    }

    private void initView(View v) {
        tv_user_login_name = v.findViewById(R.id.tv_user_login_name);
        tv_user_login_pwd = v.findViewById(R.id.tv_user_login_pwd);
        bt_user_login_ok = v.findViewById(R.id.bt_user_login_ok);
        bt_user_login_ok.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_user_login_ok) {
            LogMsg.printDebugMsg("bt_user_login_ok onclick");
            user_name = tv_user_login_name.getText().toString();
            user_pwd = tv_user_login_pwd.getText().toString();
            if (!TextUtils.isEmpty(user_name)) {
                if (!TextUtils.isEmpty(user_pwd)) {
                    //sunseen 111111代码开发权限
                    //by by111产品权限
                    if ("sunseen".equalsIgnoreCase(user_name) && "111111".equalsIgnoreCase(user_pwd)) {
                        Handler handler = ((MainActivity) getActivity()).getmHandler();
                        Message msg = handler.obtainMessage();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");// HH:mm:ss
                        Date date = new Date(System.currentTimeMillis());
                        SystemRunModel.LOGIN_USER_INFO = new UserInfoModel("sunseen", "111111",
                                4, simpleDateFormat.format(date), "sunseen");
                        msg.obj = result = true;
                        msg.what = HandlerMessage.USER_CHECKOUT_RESULT;
                        handler.sendMessage(msg);
                        return;
                    } else if ("by".equalsIgnoreCase(user_name) && "by111".equalsIgnoreCase(user_pwd)) {
                        Handler handler = ((MainActivity) getActivity()).getmHandler();
                        Message msg = handler.obtainMessage();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");// HH:mm:ss
                        Date date = new Date(System.currentTimeMillis());
                        SystemRunModel.LOGIN_USER_INFO = new UserInfoModel("by", "by111",
                                3, simpleDateFormat.format(date), "by");
                        msg.obj = result = true;
                        msg.what = HandlerMessage.USER_CHECKOUT_RESULT;
                        handler.sendMessage(msg);
                        return;
                    } else {
                        //TODO 需要在线程中校验，仅仅查数据库则无需，后需改正重写
                        ((MainActivity) getActivity()).getExecutorService().execute(new Runnable() {
                            @Override
                            public void run() {
                                result = checkUserName(((MainActivity) getActivity()).dbManager, user_name, user_pwd);
                                Handler handler = ((MainActivity) getActivity()).getmHandler();
                                Message msg = handler.obtainMessage();
                                msg.obj = result;
                                msg.what = HandlerMessage.USER_CHECKOUT_RESULT;
                                handler.sendMessage(msg);
                            }
                        });
                    }
                } else {
                    Toast.makeText(getActivity(), "请输入密码", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity(), "请输入用户名", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean checkUserName(DatabaseManager dbManager, String user_name, String user_pwd) {
        UserInfoModel userInfoModel = PassworkUtils.checkout(dbManager, user_name, user_pwd);
        if (userInfoModel != null) {
            if (user_pwd.equalsIgnoreCase(userInfoModel.getPwd())) {
                SystemRunModel.LOGIN_USER_INFO = userInfoModel;
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public void cleanUserPwd() {
        LogMsg.printDebugMsg("cleanUserPwd");
        tv_user_login_pwd.setText(null);
        tv_user_login_name.setText(null);
        user_pwd = "";
    }
}
