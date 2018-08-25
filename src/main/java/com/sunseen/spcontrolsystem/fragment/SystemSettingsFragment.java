package com.sunseen.spcontrolsystem.fragment;

import android.app.Fragment;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.sunseen.spcontrolsystem.R;
import com.sunseen.spcontrolsystem.activity.MainActivity;
import com.sunseen.spcontrolsystem.model.RunModel;
import com.sunseen.spcontrolsystem.model.SystemRunModel;
import com.sunseen.spcontrolsystem.model.SystemSettingsItemModel;
import com.sunseen.spcontrolsystem.utils.LogMsg;

import java.util.ArrayList;

/**
 * 系统设置
 */
public class SystemSettingsFragment extends Fragment {

    private ListView listView;
    private MyAdapter myAdapter;
    public ArrayList<SystemSettingsItemModel> list;
    private int[] color;
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
            rootView = inflater.inflate(R.layout.system_settings_fragment, container, false);
            LogMsg.printDebugMsg("SystemSettingsFragment onCreateView");
            initView(rootView);// 控件初始化
        }
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        if (list == null) {
            list = new ArrayList<>();
        } else {
            list.clear();
        }
        list.add(new SystemSettingsItemModel(getResources().getString(R.string.parameter_settings),
                R.mipmap.parameter_settings, R.mipmap.system_settings_logo1));
        list.add(new SystemSettingsItemModel(getResources().getString(R.string.system_log),
                R.mipmap.system_log, R.mipmap.system_settings_logo1));
        list.add(new SystemSettingsItemModel(getResources().getString(R.string.system_upgrad),
                R.mipmap.upgrad_settings, R.mipmap.system_settings_logo1));
        if (SystemRunModel.LOGIN_USER_INFO != null && SystemRunModel.LOGIN_USER_INFO.getUser_power() >= 2) {
            list.add(new SystemSettingsItemModel(getResources().getString(R.string.pay_settings),
                    R.mipmap.pay_settings, R.mipmap.system_settings_logo1));
        }
        if (color == null) {
            color = new int[]{getResources().getColor(R.color.blue_homepage_item),
                    getResources().getColor(R.color.sky_blue_homepage_item),
                    getResources().getColor(R.color.green_homepage_item),
                    getResources().getColor(R.color.pink_homepage_item)};
        }
        if (myAdapter == null) {
            myAdapter = new MyAdapter(getActivity(), list);
        }
        myAdapter.notifyDataSetChanged();
    }

    /**
     * @param view
     */
    private void initView(View view) {
        listView = view.findViewById(R.id.lv_system_settings_list);
        listView.setAdapter(myAdapter);
        listView.setVerticalScrollBarEnabled(false);
        listView.setFastScrollEnabled(false);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        RunModel.setting_type = RunModel.PARAMETER_SETTING;
                        break;
                    case 1:
                        RunModel.setting_type = RunModel.SYSTEM_LOG;
                        break;
                    case 2:
                        RunModel.setting_type = RunModel.UPGRAD_SETTING;
                        break;
                    case 3:
                        RunModel.setting_type = RunModel.PAY_SETTING;
                        break;
                }
                ((MainActivity) getActivity()).SystemSettingListItemClick();
            }
        });
    }

    /**
     *
     */
    class MyAdapter extends BaseAdapter {

        private LayoutInflater mInflater;
        private ArrayList<SystemSettingsItemModel> list;

        private int index = 0;

        public MyAdapter(Context context, ArrayList<SystemSettingsItemModel> list) {
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
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (holder == null) {
                convertView = mInflater.inflate(R.layout.system_setting_list_item, null);
                holder = new ViewHolder();
                holder.system_settings_logo = convertView.findViewById(R.id.system_settings_logo);
                holder.system_settings_right = convertView.findViewById(R.id.system_settings_right);
                holder.system_settings_text = convertView.findViewById(R.id.system_settings_text);
                convertView.setTag(convertView);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            if (index < color.length) {
                convertView.setBackgroundColor(color[index]);
                index++;
            } else {
                index = 0;
            }

            holder.system_settings_logo.setImageBitmap(
                    BitmapFactory.decodeResource(getResources(), list.get(position).getLogo()));
            holder.system_settings_right.setImageBitmap(
                    BitmapFactory.decodeResource(getResources(), list.get(position).getRight()));
            holder.system_settings_text.setText(list.get(position).getTitle());

            return convertView;
        }

        class ViewHolder {
            ImageView system_settings_logo;
            TextView system_settings_text;
            ImageView system_settings_right;
        }
    }
}
