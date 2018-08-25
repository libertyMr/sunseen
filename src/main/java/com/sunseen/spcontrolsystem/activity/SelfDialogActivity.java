package com.sunseen.spcontrolsystem.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sunseen.spcontrolsystem.R;
import com.sunseen.spcontrolsystem.model.DialogModel;
import com.sunseen.spcontrolsystem.model.FailureWarningModel;
import com.sunseen.spcontrolsystem.model.UserInfoModel;
import com.sunseen.spcontrolsystem.utils.LogMsg;

public class SelfDialogActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_self_dialog_title;
    private Button bt_yes, bt_no;

    private LinearLayout ll_user_administration;
    private TextView tv_self_dialog_user_id,
            tv_self_dialog_user_name,
            tv_self_dialog_user_spinner_power,
            tv_self_dialog_user_time;

    private LinearLayout ll_failure_warning;
    private TextView tv_self_dialog_failure_no,
            tv_self_dialog_failure_name,
            tv_self_dialog_failure_id,
            tv_self_dialog_failure_describe,
            tv_self_dialog_failure_result,
            tv_self_dialog_failure_time;

    private int mode = -1;

    private LinearLayout layout;//根布局

    private FailureWarningModel failureWarningModel = null;
    private UserInfoModel userInfoModel = null;
    private int position = -1;

    @Override
    protected void onResume() {
        super.onResume();
        layout.setSystemUiVisibility(View.INVISIBLE);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.self_dialog_activity);
        Display display = getWindowManager().getDefaultDisplay();
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.width = (int) (display.getWidth() * 0.6);
        layoutParams.height = (int) (display.getHeight() * 0.5);
        getWindow().setAttributes(layoutParams);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initView() {

        layout = findViewById(R.id.dialog_layout);
        tv_self_dialog_title = findViewById(R.id.tv_self_dialog_title);
        bt_yes = findViewById(R.id.bt_yes);
        bt_no = findViewById(R.id.bt_no);

        ll_user_administration = findViewById(R.id.ll_self_dialog_user_administration);
        tv_self_dialog_user_id = findViewById(R.id.tv_self_dialog_user_id);
        tv_self_dialog_user_name = findViewById(R.id.tv_self_dialog_user_name);
        tv_self_dialog_user_spinner_power = findViewById(R.id.tv_self_dialog_user_spinner_power);
        tv_self_dialog_user_time = findViewById(R.id.tv_self_dialog_user_time);

        ll_failure_warning = findViewById(R.id.ll_self_dialog_failure_warning);
        tv_self_dialog_failure_no = findViewById(R.id.tv_self_dialog_failure_no);
        tv_self_dialog_failure_name = findViewById(R.id.tv_self_dialog_failure_name);
        tv_self_dialog_failure_id = findViewById(R.id.tv_self_dialog_failure_id);
        tv_self_dialog_failure_describe = findViewById(R.id.tv_self_dialog_failure_describe);
        tv_self_dialog_failure_time = findViewById(R.id.tv_self_dialog_failure_time);
        tv_self_dialog_failure_result = findViewById(R.id.tv_self_dialog_failure_result);

        bt_yes.setOnClickListener(this);
        bt_no.setOnClickListener(this);
        Intent intent = getIntent();
        if (intent == null) {
            mode = -1;
        } else {
            mode = intent.getIntExtra("DIALOG_MODE", -1);
        }
        LogMsg.printDebugMsg("mode-->" + mode);
        if (mode == -1) {
            ll_user_administration.setVisibility(View.GONE);
            ll_failure_warning.setVisibility(View.GONE);
            tv_self_dialog_title.setText("error!!!");
        } else if (mode == DialogModel.FAILURE_WARNING_DIALOG) {
            failureWarningModel = (FailureWarningModel) intent.getSerializableExtra("FailureWarningModel");
            position = intent.getIntExtra("position", -1);
            ll_failure_warning.setVisibility(View.VISIBLE);
            ll_user_administration.setVisibility(View.GONE);
            tv_self_dialog_title.setText("是否删除当前报警信息？");
            changeView();
        } else if (mode == DialogModel.USER_ADMIN_ADD_DIALOG) {
            //废弃
//            ll_user_administration.setVisibility(View.VISIBLE);
//            ll_failure_warning.setVisibility(View.GONE);
//            tv_self_dialog_user_spinner_power.setVisibility(View.VISIBLE);
//            tv_self_dialog_title.setText("是否添加新用户？");
        } else if (mode == DialogModel.USER_ADMIN_DELETE_DIALOG) {
            userInfoModel = (UserInfoModel) intent.getExtras().get("Data");
            ll_user_administration.setVisibility(View.VISIBLE);
            ll_failure_warning.setVisibility(View.GONE);
            tv_self_dialog_title.setText("是否删除当前用户？");
            changeView();
        }
    }

    private void changeView() {
        if (mode == -1) {
        } else if (mode == DialogModel.FAILURE_WARNING_DIALOG) {
            LogMsg.printDebugMsg("changeView 1");
            if (failureWarningModel != null) {
                LogMsg.printDebugMsg("changeView 2");
                tv_self_dialog_failure_no.setText(position + "");
                tv_self_dialog_failure_name.setText(failureWarningModel.getFailure_name());
                tv_self_dialog_failure_id.setText(failureWarningModel.getFailure_id());
                tv_self_dialog_failure_describe.setText(failureWarningModel.getFailure_describe());
                tv_self_dialog_failure_time.setText(failureWarningModel.getFailure_time());
                LogMsg.printDebugMsg("failureWarningModel.isResult()-->" + failureWarningModel.isResult());
                if (failureWarningModel.isResult()) {
                    tv_self_dialog_failure_result.setText("已处理");
                } else {
                    tv_self_dialog_failure_result.setText("未处理");
                }
            }
        } else if (mode == DialogModel.USER_ADMIN_ADD_DIALOG) {
            //TODO
        } else if (mode == DialogModel.USER_ADMIN_DELETE_DIALOG) {
            //TODO
            if (userInfoModel != null) {
                tv_self_dialog_user_id.setText(userInfoModel.getUser_id());
                tv_self_dialog_user_name.setText(userInfoModel.getUser_name());
                tv_self_dialog_user_spinner_power.setText("权限" + userInfoModel.getUser_power());
                tv_self_dialog_user_time.setText(userInfoModel.getLast_login());
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_yes:
                SelfDialogActivity.this.setResult(DialogModel.RESULT_NO, null);
                //关闭Activity
                if (mode == DialogModel.USER_ADMIN_DELETE_DIALOG) {
                    if (userInfoModel != null) {
                        dbManager.deleteUserInfo(userInfoModel.getUser_id());
                    }
                }
                mode = -1;
                SelfDialogActivity.this.finish();
                break;
            case R.id.bt_no:
                //Test
                SelfDialogActivity.this.setResult(DialogModel.RESULT_YES, null);
                //关闭Activity
                mode = -1;
                SelfDialogActivity.this.finish();
                break;
        }
    }
}
