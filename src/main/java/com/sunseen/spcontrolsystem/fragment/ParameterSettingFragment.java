package com.sunseen.spcontrolsystem.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sunseen.spcontrolsystem.R;
import com.sunseen.spcontrolsystem.activity.MainActivity;
import com.sunseen.spcontrolsystem.model.SystemConfig;
import com.sunseen.spcontrolsystem.model.SystemRunModel;
import com.sunseen.spcontrolsystem.utils.LogMsg;

public class ParameterSettingFragment extends Fragment implements View.OnClickListener {

    private View rootView;
    private ImageView iv_parameter_setting_back,
            iv_parameter_setting_left,
            iv_parameter_setting_right;
    private TextView tv_parameter_setting_model;

    private TextView parameter_setting_save;

    private static final String DOWNSTREAM = "顺流式", BACKFLOW = "逆流式";

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
            rootView = inflater.inflate(R.layout.parameter_setting_fragment, container, false);
            LogMsg.printDebugMsg("ParameterSettingFragment onCreateView");
            initView(rootView);// 控件初始化
        }
        return rootView;
    }

    private void initView(View v) {
        iv_parameter_setting_back = v.findViewById(R.id.iv_parameter_setting_back);
        iv_parameter_setting_left = v.findViewById(R.id.iv_parameter_setting_left);
        iv_parameter_setting_right = v.findViewById(R.id.iv_parameter_setting_right);
        tv_parameter_setting_model = v.findViewById(R.id.tv_parameter_setting_model);
        parameter_setting_save = v.findViewById(R.id.parameter_setting_save);

        iv_parameter_setting_back.setOnClickListener(this);
        iv_parameter_setting_left.setOnClickListener(this);
        iv_parameter_setting_right.setOnClickListener(this);
        setFlowModel();
    }


    /**
     * 用于外部调用设置
     */
    public void setFlowModel() {
        if (SystemConfig.FlowModel == 1) {
            tv_parameter_setting_model.setText(BACKFLOW);
        } else {
            tv_parameter_setting_model.setText(DOWNSTREAM);
        }

        if (SystemRunModel.LOGIN_USER_INFO != null) {
            if (SystemRunModel.LOGIN_USER_INFO.getUser_power() >= 2) {
                iv_parameter_setting_right.setClickable(true);
                iv_parameter_setting_right.setFocusable(true);
                iv_parameter_setting_left.setClickable(true);
                iv_parameter_setting_left.setFocusable(true);
            } else {
                iv_parameter_setting_right.setClickable(false);
                iv_parameter_setting_right.setFocusable(false);
                iv_parameter_setting_left.setClickable(false);
                iv_parameter_setting_left.setFocusable(false);
                Toast.makeText(getActivity(),"权限不足，请登录",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_parameter_setting_back:
                getFragmentManager().popBackStack();
                break;
            case R.id.iv_parameter_setting_right:
                if (DOWNSTREAM.equals(tv_parameter_setting_model.getText().toString())) {
                    tv_parameter_setting_model.setText(BACKFLOW);
                } else {
                    tv_parameter_setting_model.setText(DOWNSTREAM);
                }
                break;
            case R.id.iv_parameter_setting_left:
                if (DOWNSTREAM.equals(tv_parameter_setting_model.getText().toString())) {
                    tv_parameter_setting_model.setText(BACKFLOW);
                } else {
                    tv_parameter_setting_model.setText(DOWNSTREAM);
                }
                break;
            case R.id.parameter_setting_save:
                if (DOWNSTREAM.equals(tv_parameter_setting_model.getText().toString())) {
                    SystemConfig.FlowModel = 1;
                } else {
                    SystemConfig.FlowModel = 2;
                }
                //TODO
                ((MainActivity) getActivity()).dbManager.insertSystemConfig(new SystemConfig("flow_model", String.valueOf(SystemConfig.FlowModel)));
                break;
        }
    }
}
