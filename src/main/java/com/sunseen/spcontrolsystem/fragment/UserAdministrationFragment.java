package com.sunseen.spcontrolsystem.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Message;
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
import com.sunseen.spcontrolsystem.db.DatabaseManager;
import com.sunseen.spcontrolsystem.model.DialogModel;
import com.sunseen.spcontrolsystem.model.HandlerMessage;
import com.sunseen.spcontrolsystem.model.RunModel;
import com.sunseen.spcontrolsystem.model.UserInfoModel;
import com.sunseen.spcontrolsystem.utils.LogMsg;

import java.util.ArrayList;

/**
 * 用户管理
 */
public class UserAdministrationFragment extends Fragment {

    private ListView listView;
    private ArrayList<UserInfoModel> list;
    private DatabaseManager dbManager = null;
    private MyAdapter myAdapter;

    private TextView logout;

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
            rootView = inflater.inflate(R.layout.user_administration_fragment, container, false);
            LogMsg.printDebugMsg("UserAdministrationFragment onCreateView");
            initView(rootView);// 控件初始化
        }
        return rootView;
    }

    private void initData()
    {
        dbManager = new DatabaseManager(getActivity());
        list = dbManager.queryUserInfo();
        myAdapter = new MyAdapter(getActivity(),list);
    }

    @Override
    public void onResume() {
        super.onResume();
        list.clear();
        list = dbManager.queryUserInfo();
        myAdapter.notifyDataSetChanged();
    }

    private void initView(View v)
    {
        listView = v.findViewById(R.id.user_administration_list);
        listView.setAdapter(myAdapter);
        listView.setVerticalScrollBarEnabled(false);
        listView.setFastScrollEnabled(false);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LogMsg.printDebugMsg("User listView ItemClick");
            }
        });
        logout = v.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
//                RunModel.isLogin = false;
                Message msg = ((MainActivity)getActivity()).getmHandler().obtainMessage();
                msg.what = HandlerMessage.LOGIN_TIME_OVER;
                ((MainActivity)getActivity()).getmHandler().sendMessage(msg);
            }
        });
    }


    class MyAdapter extends BaseAdapter {

        private LayoutInflater mInflater;
        private ArrayList<UserInfoModel> list;

        public MyAdapter(Context context, ArrayList<UserInfoModel> list) {
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
            if (holder == null)
            {
                convertView = mInflater.inflate(R.layout.user_administration_item,null);
                holder = new ViewHolder();
                holder.user_delete = convertView.findViewById(R.id.iv_user_administration_delete);
                holder.user_last_login = convertView.findViewById(R.id.user_administration_last_login);
                holder.user_id = convertView.findViewById(R.id.user_administration_id);
                holder.user_administration_power = convertView.findViewById(R.id.user_administration_power);
                holder.user_name = convertView.findViewById(R.id.user_administration_name);
                convertView.setTag(convertView);
            } else {
                holder = (ViewHolder)convertView.getTag();
            }
            convertView.setBackgroundColor(getResources().getColor(R.color.blue_homepage_item));
            holder.user_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity)getActivity()).showSelfDialog(DialogModel.USER_ADMIN_DELETE_DIALOG,list.get(position));
                    LogMsg.printDebugMsg("user_delete onclick");
                }
            });
            holder.user_last_login.setText(list.get(position).getLast_login());
            holder.user_name.setText(list.get(position).getUser_name());
            holder.user_id.setText(list.get(position).getUser_id());
            holder.user_administration_power.setText("权限"+list.get(position).getUser_power());

            return convertView;
        }

        class ViewHolder{
            ImageView user_delete;
            TextView user_last_login;
            TextView user_name;
            TextView user_id;
            TextView user_administration_power;
        }
    }

//    public void UpdataDeviceinfo() {
//        //数据库操作
//        if (myAdapter != null) {
//            myAdapter.notifyDataSetChanged();
//        }
//    }
}
