package com.sunseen.spcontrolsystem.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.sunseen.spcontrolsystem.db.DatabaseManager;
import com.sunseen.spcontrolsystem.model.HandlerMessage;
import com.sunseen.spcontrolsystem.utils.LogMsg;

import java.lang.ref.WeakReference;

public class BaseActivity extends Activity {

    private UnMemLeakHandler mHandler =
            new UnMemLeakHandler(BaseActivity.this);

    public DatabaseManager dbManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbManager = new DatabaseManager(getApplicationContext());
    }

    private static class UnMemLeakHandler extends Handler
    {
        private WeakReference<BaseActivity> mActivityRef;

        /**
         * 构造函数
         * @param activity 当前Activity实例
         */
        public UnMemLeakHandler(BaseActivity activity)
        {
            mActivityRef = new WeakReference<BaseActivity>(activity);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void handleMessage(Message msg)
        {
            BaseActivity act = mActivityRef.get();
            if (act != null)
            {
                act.handleStateMessage(msg);
            }
        }
    }

    protected final Handler getHandler()
    {
        return mHandler;
    }

    /**
     * @param msg
     * 子类继承
     */
    protected void handleStateMessage(Message msg)
    {
    }
}
