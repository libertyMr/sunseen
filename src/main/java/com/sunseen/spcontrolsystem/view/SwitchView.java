package com.sunseen.spcontrolsystem.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.sunseen.spcontrolsystem.R;
import com.sunseen.spcontrolsystem.model.SystemRunModel;
import com.sunseen.spcontrolsystem.utils.LogMsg;

public class SwitchView extends View {
    private static final String TAG = "SwitchView";

    private SwitchViewOnClickListener listener;

    private boolean isON = false; //是否打开

    private Paint mPaint = new Paint();

    private RectF controlView = null;
    private RectF OnView = null;
    private RectF OffView = null;

    private int WIDTH = 0;
    private int HEIGHT = 0;

    private int mColorOnView = Color.BLUE;
    private int mColorOffView = Color.GRAY;
    private int mColorTextView = Color.WHITE;
    private int mColorText = Color.RED;
    private int mTextSize = 20;

    private int mSpacing = 10;
    private int mRadian = 8;

    private final String textOn = "ON";
    private final String textOff = "OFF";


    public SwitchView(Context context) {
        super(context);
    }

    public SwitchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.album, 0, 0);
        try {
            WIDTH = a.getLayoutDimension(R.styleable.album_android_layout_width, -1);
            HEIGHT = a.getLayoutDimension(R.styleable.album_android_layout_height, -2);
        } finally {
            a.recycle();
        }
    }

    public SwitchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SwitchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        if (canvas != null)
        {
            if (controlView == null)
            {
                controlView = new RectF(0,mSpacing, WIDTH, HEIGHT - mSpacing);
            }
            if (OnView == null)
            {
                OnView = new RectF(WIDTH - WIDTH/3,5,WIDTH,HEIGHT-5);
            }
            if (OffView == null)
            {
                OffView = new RectF(0,5, WIDTH/3, HEIGHT-5);
            }
            if (isON)
            {
                mPaint.setColor(getmColorOnView());
                canvas.drawRoundRect(controlView, mRadian,mRadian, mPaint);
                mPaint.setColor(getmColorTextView());
                canvas.drawRoundRect(OnView,mRadian,mRadian,mPaint);
                mPaint.setColor(getmColorText());
                mPaint.setTextSize(mTextSize);
                canvas.drawText(textOn, OnView.left + (OnView.right - OnView.left)/2 - textWidth(mPaint,textOn)/2,
                        HEIGHT/2 + textHeigth(mPaint,textOn)/3, mPaint);
            }
            else
            {
                mPaint.setColor(getmColorOffView());
                canvas.drawRoundRect(controlView, mRadian,mRadian, mPaint);
                mPaint.setColor(getmColorTextView());
                canvas.drawRoundRect(OffView,mRadian,mRadian,mPaint);
                mPaint.setColor(getmColorText());
                mPaint.setTextSize(mTextSize);
                canvas.drawText(textOff, OffView.left + (OffView.right - OffView.left)/2 - textWidth(mPaint,textOff)/2,
                        HEIGHT/2 + textHeigth(mPaint,textOff)/3, mPaint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (!isEnabled()) {
            return false;
        }
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN: {
                if (SystemRunModel.VIP)
                {
                    isON = !isON;
                    if (listener != null)
                    {
                        listener.isClicked(this,isON);
                    }
                    else
                    {
                        LogMsg.printErrorMsg("error, switch view listener is null!!!");
                    }
                    invalidate();
                }
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                break;
            }

            case MotionEvent.ACTION_UP: {
                break;
            }
            default:
                break;
        }
        return true;
    }

    /**
     * @param paint
     * @param str
     * @return
     */
    private float textWidth(Paint paint,String str)
    {
        float textWidth = paint.measureText(str);
        return textWidth;
    }

    /**
     * @param paint
     * @param str
     * @return
     */
    private float textHeigth(Paint paint,String str)
    {
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float textHeight = Math.abs((fontMetrics.bottom - fontMetrics.top));
        return textHeight;
    }

    public void setListener(SwitchViewOnClickListener listener)
    {
        this.listener = listener;
    }

    public int getmColorOnView() {
        return mColorOnView;
    }

    public void setmColorOnView(int mColorOnView) {
        this.mColorOnView = mColorOnView;
    }

    public int getmColorOffView() {
        return mColorOffView;
    }

    public void setmColorOffView(int mColorOffView) {
        this.mColorOffView = mColorOffView;
    }

    public int getmColorTextView() {
        return mColorTextView;
    }

    public void setmColorTextView(int mColorTextView) {
        this.mColorTextView = mColorTextView;
    }

    public int getmColorText() {
        return mColorText;
    }

    public void setmColorText(int mColorText) {
        this.mColorText = mColorText;
    }

    public int getmTextSize() {
        return mTextSize;
    }

    public void setmTextSize(int mTextSize) {
        this.mTextSize = mTextSize;
    }

    public int getmSpacing() {
        return mSpacing;
    }

    public void setmSpacing(int mSpacing) {
        this.mSpacing = mSpacing;
    }
    public  void setmRadian(int mRadian)
    {
        this.mRadian = mRadian;
    }

    public interface SwitchViewOnClickListener{
        void isClicked(View view, boolean isClicked);
    }
}
