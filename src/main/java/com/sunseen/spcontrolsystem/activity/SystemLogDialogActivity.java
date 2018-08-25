package com.sunseen.spcontrolsystem.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.sunseen.spcontrolsystem.R;
import com.sunseen.spcontrolsystem.model.HandlerMessage;
import com.sunseen.spcontrolsystem.utils.FileUtils;
import com.sunseen.spcontrolsystem.utils.LogMsg;

import java.util.ArrayList;

/**
 * 系统日志页面
 */
public class SystemLogDialogActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_system_log_activity_back,
            iv_system_log_activity_delete;
    private TextView tv_system_log_activity_title;
    private ListView lv_system_log_activity_list;

    private ArrayList<String> list;

    private MyAdapter myAdapter;

    private String title = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.system_log_dialog_activity);
        Display display = getWindowManager().getDefaultDisplay();
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        Point point = new Point();
        display.getSize(point);
        layoutParams.width = (int) (point.x * 0.8);
        layoutParams.height = (int) (point.y * 0.8);
        layoutParams.gravity = Gravity.RIGHT | Gravity.BOTTOM;
        getWindow().setAttributes(layoutParams);
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        initData();
    }

    private void initData() {
        getPermission();
        list = FileUtils.getFileName(Environment.getExternalStorageDirectory().getPath() + "/sunseen/operate_log");
        if (list != null)
        {
            initView();
        }
    }

    void getPermission() {
        int permissionCheck1 = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
//        int permissionCheck2 = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck1 != PackageManager.PERMISSION_GRANTED/* || permissionCheck2 != PackageManager.PERMISSION_GRANTED*/) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                    124);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        LogMsg.printDebugMsg("requestCode == " + requestCode);
        if (requestCode == 124) {
            if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                initData();
                initView();
            }
        }
    }

    private void initView() {
        iv_system_log_activity_back = findViewById(R.id.iv_system_log_activity_back);
        iv_system_log_activity_delete = findViewById(R.id.iv_system_log_activity_delete);
        tv_system_log_activity_title = findViewById(R.id.tv_system_log_activity_title);
        tv_system_log_activity_title.setText(title);
        lv_system_log_activity_list = findViewById(R.id.lv_system_log_activity_list);
        iv_system_log_activity_back.setOnClickListener(this);
        iv_system_log_activity_delete.setOnClickListener(this);
        myAdapter = new MyAdapter(list, this);

        lv_system_log_activity_list.setAdapter(myAdapter);
        lv_system_log_activity_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LogMsg.printDebugMsg("SystemLogDialogActivity OnItemClick");
                Message msg = getHandler().obtainMessage();
                msg.what = HandlerMessage.OPEN_LOG;
                msg.obj = "/sdcard/sunseen/operate_log/" + list.get(position);
                getHandler().removeMessages(msg.what);
                getHandler().sendMessage(msg);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_system_log_activity_back:
                finish();
                break;
            case R.id.iv_system_log_activity_delete:
                //TODO
                break;
        }
    }

    class MyAdapter extends BaseAdapter {

        private ArrayList<String> list;
        private Context mContext;
        private LayoutInflater mInflater;

        public MyAdapter(ArrayList<String> list, Context context) {
            this.mInflater = LayoutInflater.from(context);
            this.list = list;
            this.mContext = context;
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
                convertView = mInflater.inflate(R.layout.system_log_dialog_activity_list_item, null);
                holder = new ViewHolder();
                holder.file_name = convertView.findViewById(R.id.system_log_dialog_file_name);
                convertView.setTag(convertView);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.file_name.setText(list.get(position));
            return convertView;
        }

        class ViewHolder {
            TextView file_name;
        }
    }

    @Override
    protected void handleStateMessage(Message msg) {
        switch (msg.what)
        {
            case HandlerMessage.OPEN_LOG: {
                String uriString = (String) msg.obj;
                if (!TextUtils.isEmpty(uriString)) {
                    LogMsg.printDebugMsg("OPEN_LOG uriString == " + uriString);
                    Uri uri = Uri.parse(uriString);
                    Intent intent = new Intent();
                    intent.setClass(SystemLogDialogActivity.this, BigTxtReaderActivity.class);
                    intent.setData(uri);
                    startActivity(intent);
                }
                break;
            }
        }
    }
}
