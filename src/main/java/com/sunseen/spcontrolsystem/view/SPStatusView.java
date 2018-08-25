package com.sunseen.spcontrolsystem.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.sunseen.spcontrolsystem.R;

/**
 * by XCH 2018/7
 */
public class SPStatusView extends View {
    private int water = 2;
    private boolean isWaterChange = true;

    /**
     * 灯光
     */
    private boolean isLight = false;

    private Paint mPaint = new Paint();
    int WIDTH = 0;
    int HEIGHT = 0;
    private Rect dst = new Rect();
    private Rect src = new Rect();

    private Rect lightDst = new Rect();
    private Rect lightSrc = new Rect();
    private Bitmap bitmap;

    private Bitmap light_open_bitmap = null;
    private Bitmap light_close_bitmap = null;
    private Bitmap cacheBitmap;
    private Canvas cacheCanvas;

    public SPStatusView(Context context) {
        super(context);
    }

    public SPStatusView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.album, 0, 0);
        WIDTH = a.getLayoutDimension(R.styleable.album_android_layout_width, -1);
        HEIGHT = a.getLayoutDimension(R.styleable.album_android_layout_height, -2);
        cacheBitmap = Bitmap.createBitmap(1024, 600, Bitmap.Config.ARGB_8888);
        cacheCanvas = new Canvas(cacheBitmap);
    }


    public SPStatusView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SPStatusView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        if (canvas != null) {
            canvas.save();
            if (isWaterChange) {
//                isWaterChange = false;
                switch (water) {
                    case 0:
                        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.state_0);
                        break;
                    case 1:
                        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.state_1);
                        break;
                    case 2:
                        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.state_2);
                        break;
                    case 3:
                        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.state_3);
                        break;
                    case 4:
                        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.state_4);
                        break;
                    case 5:
                        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.state_5);
                        break;
                    case 6:
                        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.state_6);
                        break;
                    case 7:
                        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.state_7);
                        break;
                }
                dst.left = 0;
                dst.top = 0;
                dst.right = WIDTH;
                dst.bottom = HEIGHT;

                src.left = 0;
                src.top = 0;
                if (bitmap != null) {
                    src.right = bitmap.getWidth();
                    src.bottom = bitmap.getHeight();
                    canvas.drawBitmap(bitmap, src, dst, mPaint);
                } else {

                }
            } else {

            }

            if (isLight) {
                if (light_open_bitmap == null)
                {
                    light_open_bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.light_open);
                }
                drawLight(canvas, light_open_bitmap);
            } else {
                if (light_close_bitmap == null)
                {
                    light_close_bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.light_not_open);
                }
                drawLight(canvas, light_close_bitmap);
            }
            canvas.restore();
        } else {

        }
    }

    private void drawLight(Canvas canvas, Bitmap bitmap)
    {
        lightDst.bottom = HEIGHT - 108;
        if (isLight)
        {
            lightDst.top = lightDst.bottom - 12;
        }
        else
        {
            lightDst.top = lightDst.bottom - 8;
        }
        lightDst.right = WIDTH/3 + 12;
        lightDst.left = WIDTH/3 - 7;

        lightSrc.top = 0;
        lightSrc.bottom = bitmap.getHeight();
        lightSrc.right = bitmap.getWidth();
        lightSrc.left = 0;
        canvas.drawBitmap(bitmap,lightSrc,lightDst,mPaint);

        lightDst.right = WIDTH/2 + 8;
        lightDst.left = WIDTH/2 - 12;
        canvas.drawBitmap(bitmap,lightSrc,lightDst,mPaint);

        lightDst.right = WIDTH*2/3 + 8;
        lightDst.left = WIDTH*2/3 - 12;
        canvas.drawBitmap(bitmap,lightSrc,lightDst,mPaint);
    }

    public boolean isLight() {
        return isLight;
    }

    /**
     * @param light 设置灯光
     * @param num 0
     */
    public void setLight(boolean light, int num) {
        this.isWaterChange = true;
        isLight = light;
        invalidate();
    }

    public int getWater() {
        return water;
    }

    /**
     * @param water    当前水量等级
     * @param isChange 水量变化，传入true
     */
    public void setWater(int water, boolean isChange) {
        this.water = water;
        this.isWaterChange = isChange;
        invalidate();
    }
}
