package com.sunseen.spcontrolsystem.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Point;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.sunseen.spcontrolsystem.R;
import com.sunseen.spcontrolsystem.utils.LogMsg;
import com.sunseen.spcontrolsystem.view.SwanTextView;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.CharBuffer;

public class BigTxtReaderActivity extends Activity implements
        SwanTextView.OnPreDrawListener {

    private static final String LOG_TAG = "BigTxtReaderActivity";
    private static final int BUF_SIZE = 1024 * 50;
    private static final int BUF_SHOW = 3;

    private static final int ARROW_UP = 1;
    private static final int ARROW_DOWN = 2;

    private static String ENCODING = "GB2312";

    private InputStreamReader mIsReader = null;
    private Uri mUri = null;
    private SwanTextView mTextShow;
    private ScrollView mScrollView;
    private String mStringShow = null;
    private StringBuilder mStringBuilder = null;

    private boolean mReadNext = true;
    private boolean mReadBack = false;
    private boolean mStopThread = false;

    private int mPreBottom = -1;
    private int mCurBottom = -1;
    private int mReadBufNum = 0;
    private int mBuffHeight = -1;
    private int mPreScrollY = -1;

    private TextView tv_big_text_title;
    private ImageView tv_big_text_back;

    private String title;

    @SuppressLint("HandlerLeak")
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ARROW_DOWN:
                    mTextShow.setText((CharBuffer) msg.obj);
                    break;
                case ARROW_UP:
                    mTextShow.setText((CharBuffer) msg.obj);
                    mScrollView.scrollTo(0, mBuffHeight);
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.big_txt_reader_activity);
        initData();
        initView();
    }

    /**
     * init data
     */
    private void initData() {
        mUri = getIntent().getData();
        title = mUri.toString().split("/")[mUri.toString().split("/").length - 1];
        new TextShowTask().execute(mUri);
    }

    /**
     * init view
     */
    private void initView() {
        Display display = getWindowManager().getDefaultDisplay();
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        Point point = new Point();
        display.getSize(point);
        layoutParams.width = (int) (point.x * 0.861);
        layoutParams.height = (int) (point.y * 0.848);
        layoutParams.gravity = Gravity.RIGHT | Gravity.BOTTOM;
        getWindow().setAttributes(layoutParams);

        mScrollView = findViewById(R.id.text_show_scroll);
        mTextShow = findViewById(R.id.text_show);
        mTextShow.setOnPreDrawListener(this);

        tv_big_text_title = findViewById(R.id.tv_big_text_title);
        tv_big_text_title.setText(title);
        tv_big_text_back = findViewById(R.id.tv_big_text_back);
        tv_big_text_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        new TextShowTask().execute(mUri);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        LogMsg.printDebugMsg(LOG_TAG + event.getAction() + "");
        if (MotionEvent.ACTION_OUTSIDE == event.getAction()) {
            finish();
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    private void showText(Uri uri) throws IOException {

        LogMsg.printDebugMsg(LOG_TAG + uri.toString());
        mIsReader = new InputStreamReader(new FileInputStream(
                uri.getPath()), ENCODING);

        mStringBuilder = new StringBuilder();
        int initBufSize = BUF_SIZE * (BUF_SHOW - 1);
        char[] buf = new char[BUF_SIZE];

        while (!mStopThread) {
            int scrollY = mScrollView.getScrollY();
            if (mCurBottom == scrollY && mPreScrollY < scrollY) {
                mReadNext = true;
                mReadBack = false;
            } else if (mReadBufNum > BUF_SHOW && 0 == scrollY && mPreScrollY != scrollY) {
                mReadNext = false;
                mReadBack = true;
            }

            mPreScrollY = scrollY;

            int len = -1;
            if (mReadNext && (len = mIsReader.read(buf)) > 0) {
                mReadNext = false;
                mReadBufNum++;

                if (mStringBuilder.length() > initBufSize) {
                    mStringBuilder.delete(0, BUF_SIZE);
                    mPreBottom = mCurBottom;

                    Message msg = mHandler.obtainMessage(ARROW_DOWN);
                    msg.obj = CharBuffer.wrap(mStringBuilder.toString());
                    mHandler.sendMessage(msg);

                    mStringShow = mStringBuilder.append(buf, 0, len).toString();
                } else {
                    while (mStringBuilder.length() < initBufSize) {
                        mStringBuilder.append(buf);
                        mIsReader.read(buf);
                        mReadBufNum++;
                    }

                    mStringBuilder.append(buf);
                    Message msg = mHandler.obtainMessage(ARROW_DOWN);
                    msg.obj = CharBuffer.wrap(mStringBuilder.toString());
                    mHandler.sendMessage(msg);
                }
            } else if (mReadBack && mReadBufNum > BUF_SHOW) {
                LogMsg.printDebugMsg(LOG_TAG + "Prepare to read back");
                mReadBack = false;
                mIsReader.close();
                new BackBufReadThread(mStringBuilder).start();
            }
        }
    }

    private class TextShowTask extends AsyncTask<Object, Object, Object> {
        @Override
        protected void onPostExecute(Object param) {
//            Log.d(LOG_TAG, "Send broadcast");
        }

        @Override
        protected Object doInBackground(Object... params) {
//            Uri uri = (Uri) params[0];
//            uri = Uri.parse("/sdcard/books/aaa.txt");
            try {
                if (mUri == null) {
                    LogMsg.printErrorMsg("BigTxtReaderActivity showText mUri is null");
                } else {
                    showText(mUri);
                }
            } catch (Exception e) {
                LogMsg.printDebugMsg("Exception e = " + e);
            }
            return null;
        }
    }

    private class BackBufReadThread extends Thread {
        StringBuilder mSbPre = null;

        public BackBufReadThread(StringBuilder sb) {
            mSbPre = sb.delete(0, sb.length());
        }

        @Override
        public void run() {
            try {
                mIsReader = new InputStreamReader(new FileInputStream(
                        mUri.getPath()), ENCODING);

                char[] buf = new char[BUF_SIZE];
                int i = 0;
                while ((mReadBufNum - BUF_SHOW) > ++i && mIsReader.read(buf) > 0) {
                    // Just to skip the inputstream. Any better methods?
                }
                mReadBufNum--;

                for (i = 0; i < BUF_SHOW; i++) {
                    mIsReader.read(buf);
                    mSbPre.append(buf);
                }


//                mSbPre.delete(mSbPre.length() - BUF_SIZE, mSbPre.length()).insert(0, buf);
                Message msg = mHandler.obtainMessage(ARROW_UP);
                msg.obj = CharBuffer.wrap(mSbPre.toString());
                mHandler.sendMessage(msg);
            } catch (Exception e) {
                LogMsg.printDebugMsg(LOG_TAG + " Exception " + e);
            }
        }
    }

    public void onPreDraw(int bottom) {
        mCurBottom = bottom - mScrollView.getHeight();

        if (!TextUtils.isEmpty(mStringShow)) {
            // Use the last deleted buff to evaluate the height
            mBuffHeight = mPreBottom - mScrollView.getScrollY();

            // Set the text to add new content without flash the view
            Message msg = mHandler.obtainMessage(ARROW_DOWN);
            msg.obj = CharBuffer.wrap(mStringShow);
            mHandler.sendMessage(msg);

            mStringShow = null;
        }
    }

    @Override
    public void finish() {
        mStopThread = true;
        super.finish();
    }
}
