package com.sunseen.spcontrolsystem.activity;

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
import com.sunseen.spcontrolsystem.model.UserInfoModel;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddUserActivity extends BaseActivity implements View.OnClickListener{

    private TextView add_user_jurisdiction_0,
            add_user_jurisdiction_1,
            add_user_jurisdiction_2,
            add_user_jurisdiction_3,
            add_user_yes,
            add_user_no;

    private EditText et_user_id,
            et_user_name,
            et_user_pwd_1,
            et_user_pwd_2;

    private UserInfoModel userInfoModel = null;

    private int power = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_user_activity);
        Display display = getWindowManager().getDefaultDisplay();
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.width = (int) (display.getWidth() * 0.8);
        layoutParams.height = (int) (display.getHeight() * 0.8);
        layoutParams.gravity = Gravity.RIGHT | Gravity.BOTTOM;
        getWindow().setAttributes(layoutParams);
        initView();
    }

    private void initView()
    {
        add_user_jurisdiction_0 = findViewById(R.id.add_user_jurisdiction_0);
        add_user_jurisdiction_1 = findViewById(R.id.add_user_jurisdiction_1);
        add_user_jurisdiction_2 = findViewById(R.id.add_user_jurisdiction_2);
        add_user_jurisdiction_3 = findViewById(R.id.add_user_jurisdiction_3);
        add_user_yes = findViewById(R.id.add_user_yes);
        add_user_no = findViewById(R.id.add_user_no);

        et_user_id = findViewById(R.id.et_user_id);
        et_user_name = findViewById(R.id.et_user_name);
        et_user_pwd_1 = findViewById(R.id.et_user_pwd_1);
        et_user_pwd_2 = findViewById(R.id.et_user_pwd_2);

        add_user_jurisdiction_0.setOnClickListener(this);
        add_user_jurisdiction_1.setOnClickListener(this);
        add_user_jurisdiction_2.setOnClickListener(this);
        add_user_jurisdiction_3.setOnClickListener(this);
        add_user_yes.setOnClickListener(this);
        add_user_no.setOnClickListener(this);
        setPower();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.add_user_jurisdiction_0:
                power = 0;
                setFocusForJurisdiction(add_user_jurisdiction_0);
                break;
            case R.id.add_user_jurisdiction_1:
                power = 1;
                setFocusForJurisdiction(add_user_jurisdiction_1);
                break;
            case R.id.add_user_jurisdiction_2:
                power = 2;
                setFocusForJurisdiction(add_user_jurisdiction_2);
                break;
            case R.id.add_user_jurisdiction_3:
                power = 3;
                setFocusForJurisdiction(add_user_jurisdiction_3);
                break;
            case R.id.add_user_yes:
                String user_id = et_user_id.getText().toString();
                String user_name = et_user_name.getText().toString();
                String pwd1 = et_user_pwd_1.getText().toString();
                String pwd2 = et_user_pwd_2.getText().toString();
                if (!TextUtils.isEmpty(user_id))
                {
                    if (!TextUtils.isEmpty(user_name))
                    {
                        if (!TextUtils.isEmpty(pwd1) && !TextUtils.isEmpty(pwd2))
                        {
                            if (pwd1.length() >= 6)
                            {
                                if (pwd1.equalsIgnoreCase(pwd2))
                                {
                                    if (power > -1)
                                    {
                                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");// HH:mm:ss
                                        Date date = new Date(System.currentTimeMillis());
                                        userInfoModel = new UserInfoModel(user_name,pwd1,power,simpleDateFormat.format(date),user_id);
                                        dbManager.insertUserInfo(userInfoModel);
//                                        Toast.makeText(AddUserActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                                        setFocusForJurisdiction(null);
                                        et_user_id.setText("");
                                        et_user_name.setText("");
                                        et_user_pwd_1.setText("");
                                        et_user_pwd_2.setText("");
                                        finish();
                                    }
                                    else
                                    {
                                        Toast.makeText(AddUserActivity.this, "选择权限", Toast.LENGTH_SHORT).show();
                                    }
                                }else{
                                    Toast.makeText(AddUserActivity.this, "两次密码输入不统一", Toast.LENGTH_SHORT).show();
                                    et_user_pwd_1.setText("");
                                    et_user_pwd_2.setText("");
                                }
                            }else
                            {
                                Toast.makeText(AddUserActivity.this, "密码应大于6位", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(AddUserActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(AddUserActivity.this, "用户名", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AddUserActivity.this, "工号不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.add_user_no:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userInfoModel = null;
    }

    private void setFocusForJurisdiction(View view)
    {
        add_user_jurisdiction_0.setBackground(getResources().getDrawable(R.drawable.tv_sp_running_bg_no_focus));
        add_user_jurisdiction_1.setBackground(getResources().getDrawable(R.drawable.tv_sp_running_bg_no_focus));
        add_user_jurisdiction_2.setBackground(getResources().getDrawable(R.drawable.tv_sp_running_bg_no_focus));
        add_user_jurisdiction_3.setBackground(getResources().getDrawable(R.drawable.tv_sp_running_bg_no_focus));
        if(view != null)
        {
            view.setBackground(getResources().getDrawable(R.drawable.tv_sp_running_bg_focus));
        }
    }

    private void setPower()
    {
        if (SystemRunModel.LOGIN_USER_INFO != null)
        {
            int power = SystemRunModel.LOGIN_USER_INFO.getUser_power();
            switch (power)
            {
                case 0:
                    add_user_jurisdiction_0.setVisibility(View.GONE);
                    add_user_jurisdiction_1.setVisibility(View.GONE);
                    add_user_jurisdiction_2.setVisibility(View.GONE);
                    add_user_jurisdiction_3.setVisibility(View.GONE);
                    break;
                case 1:
                    add_user_jurisdiction_0.setVisibility(View.VISIBLE);
                    add_user_jurisdiction_1.setVisibility(View.GONE);
                    add_user_jurisdiction_2.setVisibility(View.GONE);
                    add_user_jurisdiction_3.setVisibility(View.GONE);
                    break;
                case 2:
                    add_user_jurisdiction_0.setVisibility(View.VISIBLE);
                    add_user_jurisdiction_1.setVisibility(View.VISIBLE);
                    add_user_jurisdiction_2.setVisibility(View.GONE);
                    add_user_jurisdiction_3.setVisibility(View.GONE);
                    break;
                case 3:
                    add_user_jurisdiction_0.setVisibility(View.VISIBLE);
                    add_user_jurisdiction_1.setVisibility(View.VISIBLE);
                    add_user_jurisdiction_2.setVisibility(View.VISIBLE);
                    add_user_jurisdiction_3.setVisibility(View.GONE);
                    break;
                case 4:
                    add_user_jurisdiction_0.setVisibility(View.VISIBLE);
                    add_user_jurisdiction_1.setVisibility(View.VISIBLE);
                    add_user_jurisdiction_2.setVisibility(View.VISIBLE);
                    add_user_jurisdiction_3.setVisibility(View.VISIBLE);
                    break;
            }
        }
    }
}
