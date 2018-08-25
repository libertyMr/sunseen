package com.sunseen.spcontrolsystem.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.sunseen.spcontrolsystem.R;
import com.sunseen.spcontrolsystem.activity.SelfDialogActivity;
import com.sunseen.spcontrolsystem.model.DialogModel;
import com.sunseen.spcontrolsystem.model.FailureWarningModel;
import com.sunseen.spcontrolsystem.utils.LogMsg;

import java.util.ArrayList;

public class FailureWarningFragment extends Fragment {

    private ArrayList<FailureWarningModel> list;
    private ListView listView;
    private MyAdapter myAdapter;

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        initData();
        if (null != rootView) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (null != parent) {
                parent.removeView(rootView);
            }
        } else {
            rootView = inflater.inflate(R.layout.failure_warning_fragment, container, false);
            LogMsg.printDebugMsg("FailureWarningFragment onCreateView");
            initView(rootView);// 控件初始化
        }
        return rootView;
    }

    private void initView(View v) {
        listView = v.findViewById(R.id.lv_failure_warning_list);
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO
            }
        });
    }

    private void initData() {
        list = new ArrayList<>();
        //test数据
//        String failure_name,String failure_id,String failure_describe,String failure_time
        list.add(new FailureWarningModel("空气源热泵", "BY0215212145455221",
                "热泵温度超过60度", "2018/7/13/15:51:12", false, false));
        list.add(new FailureWarningModel("空气源热泵", "BY0215212145455221",
                "热泵温度超过60度", "2018/7/13/15:51:12", false, false));
        list.add(new FailureWarningModel("空气源热泵", "BY0215212145455221",
                "热泵温度超过60度", "2018/7/13/15:51:12", false, false));
        list.add(new FailureWarningModel("空气源热泵", "BY0215212145455221",
                "热泵温度超过60度", "2018/7/13/15:51:12", false, false));
        list.add(new FailureWarningModel("空气源热泵", "BY0215212145455221",
                "热泵温度超过60度", "2018/7/13/15:51:12", false, false));
        list.add(new FailureWarningModel("空气源热泵", "BY0215212145455221",
                "热泵温度超过60度", "2018/7/13/15:51:12", false, false));
        myAdapter = new MyAdapter(getActivity(), list);
    }

    class MyAdapter extends BaseAdapter {

        private LayoutInflater mInflater;
        private ArrayList<FailureWarningModel> list;

        private int index = 0;

        public MyAdapter(Context context, ArrayList<FailureWarningModel> list) {
            this.mInflater = LayoutInflater.from(context);
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (dialogActivity == null) {
                dialogActivity = new SelfDialogActivity();
            }
            if (holder == null) {
                convertView = mInflater.inflate(R.layout.failure_warning_item, null);
                holder = new ViewHolder();
                holder.ll_failure_handled = convertView.findViewById(R.id.ll_failure_handled);//未处理
                holder.ll_failure_result = convertView.findViewById(R.id.ll_failure_result);//已处理

                holder.tv_failure_delete = convertView.findViewById(R.id.tv_failure_delete);//还未点击处理的删除图标
                holder.tv_failure_describe = convertView.findViewById(R.id.tv_failure_describe);//警报描述
                holder.tv_failure_device_id = convertView.findViewById(R.id.tv_failure_device_id);//报警设备id
                holder.tv_failure_device_name = convertView.findViewById(R.id.tv_failure_device_name);//报警设备名称
                holder.tv_failure_handled = convertView.findViewById(R.id.tv_failure_handled);//已处理
                holder.tv_failure_untreated = convertView.findViewById(R.id.tv_failure_untreated);//未处理
                holder.tv_failure_no = convertView.findViewById(R.id.tv_failure_no);//序号
                holder.tv_failure_result_delete = convertView.findViewById(R.id.iv_failure_result_delete);//点击处理后的删除图标
                holder.tv_failure_result_time = convertView.findViewById(R.id.tv_failure_result_time);//点击处理后的时间
                holder.tv_failure_time = convertView.findViewById(R.id.tv_failure_time);//还未点击处理的时间
                holder.tv_failure_result = convertView.findViewById(R.id.tv_failure_result);//处理结果-未处理-已处理
                convertView.setTag(convertView);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.tv_failure_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialog(position);

                }
            });//还未点击处理的删除图标

            holder.tv_failure_result_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialog(position);
                }
            });//点击处理后的删除图标

            holder.tv_failure_handled.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO
                    ListNotifyDataSetChanged(position, true);
                }
            });//已处理按钮

            holder.tv_failure_untreated.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO
                    ListNotifyDataSetChanged(position, false);
                }
            });//未处理按钮

            holder.tv_failure_describe.setText(list.get(position).getFailure_describe());//警报描述
            holder.tv_failure_device_id.setText(list.get(position).getFailure_id());//报警设备id
            holder.tv_failure_device_name.setText(list.get(position).getFailure_name());//报警设备名称
            holder.tv_failure_no.setText(String.valueOf(position));//序号
            holder.tv_failure_result_time.setText(list.get(position).getFailure_time());//点击处理后的时间
            holder.tv_failure_time.setText(list.get(position).getFailure_time());//还未点击处理的时间

            if (list.get(position).isIs_result()) {
                holder.ll_failure_result.setVisibility(View.VISIBLE);//已处理
                holder.ll_failure_handled.setVisibility(View.GONE);//未处理
            } else {
                holder.ll_failure_handled.setVisibility(View.VISIBLE);//未处理
                holder.ll_failure_result.setVisibility(View.GONE);//未处理
            }
            if (list.get(position).isResult()) {
                holder.tv_failure_result.setText("已处理");
                holder.tv_failure_result.setTextColor(getResources().getColor(R.color.yellow_self));
            } else {
                holder.tv_failure_result.setText("未处理");
                holder.tv_failure_result.setTextColor(getResources().getColor(R.color.text_white));
            }
            return convertView;
        }

        class ViewHolder {
            ImageView tv_failure_delete;
            ImageView tv_failure_result_delete;
            TextView tv_failure_result_time;
            TextView tv_failure_result;
            TextView tv_failure_time;
            TextView tv_failure_handled;
            TextView tv_failure_describe;
            TextView tv_failure_device_name;
            TextView tv_failure_device_id;
            TextView tv_failure_no;
            TextView tv_failure_untreated;

            LinearLayout ll_failure_handled;
            LinearLayout ll_failure_result;
        }
    }

    public void ListNotifyDataSetChanged(int position, boolean result) {
        list.get(position).setResult(result);
        list.get(position).setIs_result(true);
        myAdapter.notifyDataSetChanged();
    }

    private int no = -1;
    private SelfDialogActivity dialogActivity;

    public void showDialog(int position) {
        no = position;
        Intent intent = new Intent();
        intent.putExtra("DIALOG_MODE", DialogModel.FAILURE_WARNING_DIALOG);
        intent.putExtra("position", position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("FailureWarningModel", list.get(position));
        intent.putExtras(bundle);
        intent.setClass(getActivity(), SelfDialogActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogMsg.printDebugMsg("FailureWarningFragment onActivityResult-->" + requestCode);

        switch (resultCode) {
            case DialogModel.RESULT_YES:
                list.remove(no);
                //TODO
                myAdapter.notifyDataSetChanged();
                break;
            case DialogModel.RESULT_NO:
                LogMsg.printInfoMsg("FailureWarningFragment onActivityResult RESULT_NO");
                break;
        }
    }
}
