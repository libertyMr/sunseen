package com.sunseen.spcontrolsystem.activity;

import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sunseen.spcontrolsystem.R;
import com.sunseen.spcontrolsystem.model.SystemRunModel;
import com.sunseen.spcontrolsystem.utils.CommonUtils;

public class CheckActivity extends BaseActivity {
    private TextView tv_check_activity_title, tv_check_ok;
    private EditText et_check_pwd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_activity);
        Display display = getWindowManager().getDefaultDisplay();
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        Point point = new Point();
        display.getSize(point);
        layoutParams.width = (int) (point.x * 0.8);
        layoutParams.height = (int) (point.y * 0.8);
//        layoutParams.gravity = Gravity.RIGHT | Gravity.BOTTOM;
        getWindow().setAttributes(layoutParams);
        initView();
    }

    /**
     * init view
     */
    private void initView() {
        tv_check_activity_title = findViewById(R.id.tv_check_activity_title);
        tv_check_ok = findViewById(R.id.tv_check_ok);
        et_check_pwd = findViewById(R.id.et_check_pwd);
        tv_check_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(et_check_pwd.getText().toString())){
                    boolean checked = CommonUtils.checkPWD(CheckActivity.this, et_check_pwd.getText().toString());
                    if (checked) {
                        SystemRunModel.VIP = true;
                        finish();
                    } else {
                        Toast.makeText(CheckActivity.this,"密码错误",Toast.LENGTH_SHORT).show();
                        SystemRunModel.VIP = false;
                    }
                } else {
                    Toast.makeText(CheckActivity.this,"请输入密码",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
