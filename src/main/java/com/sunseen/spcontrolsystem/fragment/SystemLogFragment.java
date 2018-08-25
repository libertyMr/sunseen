package com.sunseen.spcontrolsystem.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sunseen.spcontrolsystem.R;
import com.sunseen.spcontrolsystem.activity.SystemLogDialogActivity;
import com.sunseen.spcontrolsystem.utils.LogMsg;

/**
 * SystemLogFragment
 */
public class SystemLogFragment extends Fragment implements View.OnClickListener{
    private View rootView;

    private ImageView system_log_operate_delete,
            system_log_failure_delete,
            system_log_work_delete,
            iv_system_log_fragment_back;

    private TextView system_log_operate_output,
            system_log_operate_see,
            system_log_failure_output,
            system_log_failure_see,
            system_log_work_output,
            system_log_work_see;

    private LinearLayout system_log_operate_log,
            system_log_failure_log,
            system_log_work_log;

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
            rootView = inflater.inflate(R.layout.system_log_fragment, container, false);
            LogMsg.printDebugMsg("SystemLogFragment onCreateView");
            initView(rootView);// 控件初始化
        }
        return rootView;
    }

    private void initView(View v)
    {
        system_log_operate_delete = v.findViewById(R.id.system_log_operate_delete);
        system_log_failure_delete = v.findViewById(R.id.system_log_failure_delete);
        system_log_work_delete = v.findViewById(R.id.system_log_work_delete);

        iv_system_log_fragment_back = v.findViewById(R.id.iv_system_log_fragment_back);

        system_log_operate_output = v.findViewById(R.id.system_log_operate_output);
        system_log_operate_see = v.findViewById(R.id.system_log_operate_see);
        system_log_failure_output = v.findViewById(R.id.system_log_failure_output);
        system_log_failure_see = v.findViewById(R.id.system_log_failure_see);
        system_log_work_output = v.findViewById(R.id.system_log_work_output);
        system_log_work_see = v.findViewById(R.id.system_log_work_see);

        system_log_operate_log = v.findViewById(R.id.system_log_operate_log);
        system_log_failure_log = v.findViewById(R.id.system_log_failure_log);
        system_log_work_log = v.findViewById(R.id.system_log_work_log);

        system_log_operate_delete.setOnClickListener(this);
        system_log_failure_delete.setOnClickListener(this);
        system_log_work_delete.setOnClickListener(this);
        system_log_operate_output.setOnClickListener(this);
        system_log_operate_see.setOnClickListener(this);
        system_log_failure_output.setOnClickListener(this);
        system_log_failure_see.setOnClickListener(this);
        system_log_work_output.setOnClickListener(this);
        system_log_work_see.setOnClickListener(this);
        system_log_operate_log.setOnClickListener(this);
        system_log_failure_log.setOnClickListener(this);
        system_log_work_log.setOnClickListener(this);

        iv_system_log_fragment_back.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        LogMsg.printDebugMsg("SystemLogFragment onClick");
        Intent intent = new Intent();
        switch (v.getId())
        {
            case R.id.system_log_operate_delete://删除
                break;
            case R.id.system_log_failure_delete:
                break;
            case R.id.system_log_work_delete:
                break;

            case R.id.system_log_operate_output://导出
                break;
            case R.id.system_log_failure_output:
                break;
            case R.id.system_log_work_output:
                break;

            case R.id.system_log_operate_see://查看
            case R.id.system_log_operate_log://item
                intent.setClass(getActivity(), SystemLogDialogActivity.class);
                intent.putExtra("title","系统操作日志");
                startActivity(intent);
                break;

            case R.id.system_log_failure_see:
            case R.id.system_log_failure_log:
                intent.setClass(getActivity(), SystemLogDialogActivity.class);
                intent.putExtra("title","系统报警日志");
                startActivity(intent);
                break;

            case R.id.system_log_work_see:
            case R.id.system_log_work_log:
                intent.setClass(getActivity(), SystemLogDialogActivity.class);
                intent.putExtra("title","系统工作日志");
                startActivity(intent);
                break;

            case R.id.iv_system_log_fragment_back:
                getFragmentManager().popBackStack();
                break;
        }
    }
}
