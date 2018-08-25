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
import com.sunseen.spcontrolsystem.model.DeviceAdministrationModel;
import com.sunseen.spcontrolsystem.utils.LogMsg;

import java.util.ArrayList;

/**
 * 设备管理
 */
public class DeviceAdministrationFragment extends Fragment {

    private ArrayList<DeviceAdministrationModel> list;
    private ListView listView;
    private int[] color;
    private View rootView;
    private MyAdapter myAdapter;

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
            rootView = inflater.inflate(R.layout.device_administration_fragment, container, false);
            LogMsg.printDebugMsg("DeviceAdministrationFragment onCreateView");
            initView(rootView);// 控件初始化
        }
        return rootView;
    }

    /**
     *
     */
    private void initData() {
        list = new ArrayList<>();
//        int logo, String title, int onLine, int outLine, int right
        list.add(new DeviceAdministrationModel(R.mipmap.water_cycle_system,
                getResources().getString(R.string.water_cycle_system), 0, 0, R.mipmap.right));
        list.add(new DeviceAdministrationModel(R.mipmap.filter_system, getResources()
                .getString(R.string.filter_system), 0, 0, R.mipmap.right));
        list.add(new DeviceAdministrationModel(R.mipmap.water_quality_monitoring_system, getResources()
                .getString(R.string.water_quality_monitoring_system), 0, 0, R.mipmap.right));
        list.add(new DeviceAdministrationModel(R.mipmap.disinfection_system, getResources()
                .getString(R.string.disinfection_system), 0, 0, R.mipmap.right));
        list.add(new DeviceAdministrationModel(R.mipmap.water_heating_system, getResources()
                .getString(R.string.water_heating_system), 0, 0, R.mipmap.right));
        list.add(new DeviceAdministrationModel(R.mipmap.dehumidify_system, getResources()
                .getString(R.string.dehumidify_system), 0, 0, R.mipmap.right));
        list.add(new DeviceAdministrationModel(R.mipmap.lamp_system, getResources()
                .getString(R.string.lamp_system), 0, 0, R.mipmap.right));
        list.add(new DeviceAdministrationModel(R.mipmap.computer_room_running, getResources()
                .getString(R.string.computer_room_control_system), 0, 0, R.mipmap.right));
        list.add(new DeviceAdministrationModel(R.mipmap.water_supply_system, getResources()
                .getString(R.string.water_supply_system), 0, 0, R.mipmap.right));
        list.add(new DeviceAdministrationModel(R.mipmap.safety_system, getResources()
                .getString(R.string.safety_system), 0, 0, R.mipmap.right));
        list.add(new DeviceAdministrationModel(R.mipmap.spa, getResources()
                .getString(R.string.spa_system), 0, 0, R.mipmap.right));

        color = new int[]{getResources().getColor(R.color.blue_homepage_item),
                getResources().getColor(R.color.sky_blue_homepage_item),
                getResources().getColor(R.color.green_homepage_item),
                getResources().getColor(R.color.pink_homepage_item)};
    }

    /**
     * @param view
     */
    private void initView(View view) {
        listView = view.findViewById(R.id.lv_device_administration_list);
        listView.setVerticalScrollBarEnabled(false);
        listView.setFastScrollEnabled(false);
        if (myAdapter == null) {
            myAdapter = new MyAdapter(getActivity(), list);
        }
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO
            }
        });
    }

    class MyAdapter extends BaseAdapter {

        private LayoutInflater mInflater;
        private ArrayList<DeviceAdministrationModel> list;

        private int index = 0;

        public MyAdapter(Context context, ArrayList<DeviceAdministrationModel> list) {
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
                convertView = mInflater.inflate(R.layout.device_administration_item, null);
                holder = new ViewHolder();
                holder.device_logo = convertView.findViewById(R.id.device_administration_logo);
                holder.device_online = convertView.findViewById(R.id.tv_device_administration_online);
                holder.device_outline = convertView.findViewById(R.id.tv_device_administration_outline);
                holder.device_right = convertView.findViewById(R.id.device_administration_right);
                holder.device_text = convertView.findViewById(R.id.device_administration_text);
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
            holder.device_logo.setImageBitmap(BitmapFactory.decodeResource(getResources(), list.get(position).getLogo()));
            holder.device_text.setText(list.get(position).getTitle());
            holder.device_right.setImageBitmap(BitmapFactory.decodeResource(getResources(), list.get(position).getRight()));
            holder.device_outline.setText(String.valueOf(list.get(position).getOutLine()));
            holder.device_online.setText(String.valueOf(list.get(position).getOnLine()));
            return convertView;
        }

        class ViewHolder {
            ImageView device_logo;
            TextView device_text;
            ImageView device_right;
            TextView device_online;
            TextView device_outline;
        }
    }
}
