package com.sunseen.spcontrolsystem.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sunseen.spcontrolsystem.R;
import com.sunseen.spcontrolsystem.activity.MainActivity;
import com.sunseen.spcontrolsystem.db.DatabaseManager;
import com.sunseen.spcontrolsystem.model.SystemConfig;
import com.sunseen.spcontrolsystem.utils.CommonUtils;
import com.sunseen.spcontrolsystem.utils.LogMsg;

import org.feezu.liuli.timeselector.TimeSelector;

public class PaySettingFragment extends Fragment {
    private ImageView iv_pay_setting_back;

    private View rootView;
    private EditText first_phase_time,
            second_phase_time,
            third_phase_time,
            fourth_phase_time,
            fifth_phase_time,
            sixth_phase_time,
            first_phase_pwd,
            second_phase_pwd,
            third_phase_pwd,
            fourth_phase_pwd,
            fifth_phase_pwd,
            sixth_phase_pwd;

    private TextView tv_pay_setting_save;
    private TimeSelector timeSelector;

    private DatabaseManager dbManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        initData();
        //TODO
        if (null != rootView) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (null != parent) {
                parent.removeView(rootView);
            }
        } else {
            rootView = inflater.inflate(R.layout.pay_setting_fragment, container, false);
            LogMsg.printDebugMsg("TimeSettingFragment onCreateView");
            initView(rootView);// 控件初始化
        }
        return rootView;
    }

    private void initData() {
        dbManager = ((MainActivity) getActivity()).dbManager;
        timeSelector = new TimeSelector(getActivity(), new TimeSelector.ResultHandler() {
            @Override
            public void handle(String time) {
                ((EditText) rootView.findFocus()).setText(time);
            }
        }, "2018-01-01 00:00", "2099-12-31 23:59:59");
        timeSelector.setIsLoop(false);//设置不循环,true循环
        timeSelector.setMode(TimeSelector.MODE.YMDHM);//显示 年月日时分（默认）
    }

    private void initView(View v) {
        iv_pay_setting_back = v.findViewById(R.id.iv_pay_setting_back);
        iv_pay_setting_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        tv_pay_setting_save = v.findViewById(R.id.tv_pay_setting_save);
        tv_pay_setting_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paySettingSave();
            }
        });
        first_phase_time = v.findViewById(R.id.first_phase_time);
        second_phase_time = v.findViewById(R.id.second_phase_time);
        third_phase_time = v.findViewById(R.id.third_phase_time);
        fourth_phase_time = v.findViewById(R.id.fourth_phase_time);
        fifth_phase_time = v.findViewById(R.id.fifth_phase_time);
        sixth_phase_time = v.findViewById(R.id.sixth_phase_time);

        first_phase_pwd = v.findViewById(R.id.first_phase_pwd);
        second_phase_pwd = v.findViewById(R.id.second_phase_pwd);
        third_phase_pwd = v.findViewById(R.id.third_phase_pwd);
        fourth_phase_pwd = v.findViewById(R.id.fourth_phase_pwd);
        fifth_phase_pwd = v.findViewById(R.id.fifth_phase_pwd);
        sixth_phase_pwd = v.findViewById(R.id.sixth_phase_pwd);

        first_phase_time.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                onEditFocusChange(v, hasFocus);
            }
        });

        first_phase_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEditFocusChange(v, true);
            }
        });

        second_phase_time.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                onEditFocusChange(v, hasFocus);
            }
        });
        second_phase_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEditFocusChange(v, true);
            }
        });
        third_phase_time.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                onEditFocusChange(v, hasFocus);
            }
        });
        third_phase_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEditFocusChange(v, true);
            }
        });
        fourth_phase_time.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                onEditFocusChange(v, hasFocus);
            }
        });
        fourth_phase_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEditFocusChange(v, true);
            }
        });

        fifth_phase_time.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                onEditFocusChange(v, hasFocus);
            }
        });
        fifth_phase_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEditFocusChange(v, true);
            }
        });
        sixth_phase_time.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                onEditFocusChange(v, hasFocus);
            }
        });
        sixth_phase_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEditFocusChange(v, true);
            }
        });
        initViewData();
    }

    /**
     * @param v
     * @param hasFocus editview focuschange listener
     */
    private void onEditFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            if (timeSelector != null) {
                timeSelector.show();
            }
        }
    }

    /**
     * 显示上次设置的数据
     */
    private void initViewData() {
        String first = dbManager.queryConfigValue(SystemConfig.FIRST_PHASE_TIME, "");
        String second = dbManager.queryConfigValue(SystemConfig.SECOND_PHASE_TIME, "");
        String third = dbManager.queryConfigValue(SystemConfig.THIRD_PHASE_TIME, "");
        String fourth = dbManager.queryConfigValue(SystemConfig.FOURTH_PHASE_TIME, "");
        String fifth = dbManager.queryConfigValue(SystemConfig.FIFTH_PHASE_TIME, "");
        String sixth = dbManager.queryConfigValue(SystemConfig.SIXTH_PHASE_TIME, "");
        String first_pwd = dbManager.queryConfigValue(SystemConfig.FIRST_PHASE_PWD, "");
        String second_pwd = dbManager.queryConfigValue(SystemConfig.SECOND_PHASE_PWD, "");
        String third_pwd = dbManager.queryConfigValue(SystemConfig.THIRD_PHASE_PWD, "");
        String fourth_pwd = dbManager.queryConfigValue(SystemConfig.FOURTH_PHASE_PWD, "");
        String fifth_pwd = dbManager.queryConfigValue(SystemConfig.FIFTH_PHASE_PWD, "");
        String sixth_pwd = dbManager.queryConfigValue(SystemConfig.SIXTH_PHASE_PWD, "");
        //时间
        first_phase_time.setText(first);
        second_phase_time.setText(second);
        third_phase_time.setText(third);
        fourth_phase_time.setText(fourth);
        fifth_phase_time.setText(fifth);
        sixth_phase_time.setText(sixth);
        //密码
        first_phase_pwd.setText(first_pwd);
        second_phase_pwd.setText(second_pwd);
        third_phase_pwd.setText(third_pwd);
        fourth_phase_pwd.setText(fourth_pwd);
        fifth_phase_pwd.setText(fifth_pwd);
        sixth_phase_pwd.setText(sixth_pwd);
    }

    /**
     * 保存设置的付费到期时间
     */
    private void paySettingSave() {
        if (dbManager != null) {
            String first = first_phase_time.getText().toString();
            String second = second_phase_time.getText().toString();
            String third = third_phase_time.getText().toString();
            String fourth = fourth_phase_time.getText().toString();
            String fifth = fifth_phase_time.getText().toString();
            String sixth = sixth_phase_time.getText().toString();

            String first_pwd = first_phase_pwd.getText().toString();
            String second_pwd = second_phase_pwd.getText().toString();
            String third_pwd = third_phase_pwd.getText().toString();
            String fourth_pwd = fourth_phase_pwd.getText().toString();
            String fifth_pwd = fifth_phase_pwd.getText().toString();
            String sixth_pwd = sixth_phase_pwd.getText().toString();

            if (TextUtils.isEmpty(first) || TextUtils.isEmpty(second) || TextUtils.isEmpty(third)
                    || TextUtils.isEmpty(fourth) || TextUtils.isEmpty(fifth) || TextUtils.isEmpty(sixth)) {
                showToast("到期时间未设置完成，请设置");
                return;
            }
            if (TextUtils.isEmpty(first_pwd) || TextUtils.isEmpty(second_pwd) || TextUtils.isEmpty(third_pwd)
                    || TextUtils.isEmpty(fourth_pwd) || TextUtils.isEmpty(fifth_pwd) || TextUtils.isEmpty(sixth_pwd)) {
                showToast("付费密码未设置完成，请设置");
                return;
            }

            if (first_pwd.equalsIgnoreCase(second) || first_pwd.equalsIgnoreCase(third_pwd) || first_pwd.equalsIgnoreCase(fourth_pwd)
                    || first_pwd.equalsIgnoreCase(fifth_pwd) || first_pwd.equalsIgnoreCase(sixth_pwd) || second_pwd.equalsIgnoreCase(third_pwd)
                    || second_pwd.equalsIgnoreCase(fifth_pwd) || second_pwd.equalsIgnoreCase(sixth_pwd) || second_pwd.equalsIgnoreCase(fourth_pwd)
                    || fourth_pwd.equalsIgnoreCase(fifth_pwd) || fourth_pwd.equalsIgnoreCase(sixth_pwd) || fifth_pwd.equalsIgnoreCase(sixth_pwd)){
                showToast("付费密码不能出现重复，请设置");
                return;
            }

            if (!CommonUtils.compareTime(first, second)) {
                showToast("时间有误，第二期时间要大于第一期时间");
                return;
            } else if (!CommonUtils.compareTime(second, third)) {
                showToast("时间有误，第三期时间要大于第二期时间");
                return;
            } else if (!CommonUtils.compareTime(third, fourth)) {
                showToast("时间有误，第四期时间要大于第三期时间");
                return;
            } else if (!CommonUtils.compareTime(fourth, fifth)) {
                showToast("时间有误，第五期时间要大于第四期时间");
                return;
            } else if (!CommonUtils.compareTime(fifth, sixth)) {
                showToast("时间有误，第六期时间要大于第五期时间");
                return;
            }
            dbManager.insertSystemConfig(new SystemConfig(SystemConfig.FIRST_PHASE_TIME, first));
            dbManager.insertSystemConfig(new SystemConfig(SystemConfig.SECOND_PHASE_TIME, second));
            dbManager.insertSystemConfig(new SystemConfig(SystemConfig.THIRD_PHASE_TIME, third));
            dbManager.insertSystemConfig(new SystemConfig(SystemConfig.FOURTH_PHASE_TIME, fourth));
            dbManager.insertSystemConfig(new SystemConfig(SystemConfig.FIFTH_PHASE_TIME, fifth));
            dbManager.insertSystemConfig(new SystemConfig(SystemConfig.SIXTH_PHASE_TIME, sixth));
            dbManager.insertSystemConfig(new SystemConfig(SystemConfig.FIRST_PHASE_PWD, first_pwd));
            dbManager.insertSystemConfig(new SystemConfig(SystemConfig.SECOND_PHASE_PWD, second_pwd));
            dbManager.insertSystemConfig(new SystemConfig(SystemConfig.THIRD_PHASE_PWD, third_pwd));
            dbManager.insertSystemConfig(new SystemConfig(SystemConfig.FOURTH_PHASE_PWD, fourth_pwd));
            dbManager.insertSystemConfig(new SystemConfig(SystemConfig.FIFTH_PHASE_PWD, fifth_pwd));
            dbManager.insertSystemConfig(new SystemConfig(SystemConfig.SIXTH_PHASE_PWD, sixth_pwd));
            showToast("设置完成！");
            ((MainActivity) getActivity()).initSystem();
        }
    }

    private void showToast(String str) {
        Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
    }
}
