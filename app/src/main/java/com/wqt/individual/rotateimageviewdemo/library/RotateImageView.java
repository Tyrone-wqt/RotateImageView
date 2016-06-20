package com.wqt.individual.rotateimageviewdemo.library;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Administrator on 2016/6/19.
 */
public class RotateImageView extends ImageView {

    Drawable mDrawbleSrc;
    Context context;
    Bitmap mBitmapOut;
    Bitmap output;
    int defaultWidth;
    int defaultHeight;
    int diameter;
    int radius;

    int currentDegree;
    int savedDegree;
    boolean isRotateEnable;
    boolean isRotating;


    public RotateImageView(Context context) {
        this(context, null);
    }

    public RotateImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RotateImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {
        mDrawbleSrc = getDrawable();

        isRotateEnable = false;
        isRotating = false;
    }



    public void startRotate() {
        if (isRotating == true) return;
        isRotateEnable = true;
        isRotating = true;
        currentDegree = savedDegree;
        invalidate();
    }

    public void stopRotate() {
        isRotating = false;
        isRotateEnable = false;
        savedDegree = currentDegree;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (mDrawbleSrc == null)
            return;

        if (getWidth() == 0 || getHeight() == 0)
            return;

        if (mDrawbleSrc.getClass() == NinePatchDrawable.class)
            return;

        if (output == null) {
            defaultHeight = getHeight();
            defaultWidth = getWidth();
            diameter = (defaultHeight > defaultWidth ? defaultWidth : defaultHeight);
            radius = diameter / 2;
            mBitmapOut = getCuttedPicture(mDrawbleSrc);

            Paint paint = new Paint();
            Rect rect = new Rect(0, 0, mBitmapOut.getWidth(),
                    mBitmapOut.getHeight());

            paint.setAntiAlias(true);
            paint.setFilterBitmap(true);
            paint.setDither(true);

            output = Bitmap.createBitmap(mBitmapOut.getWidth(),
                    mBitmapOut.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas mTempCanvas = new Canvas(output);
            mTempCanvas.drawARGB(0, 0, 0, 0);
            mTempCanvas.drawCircle(mBitmapOut.getWidth() / 2,
                    mBitmapOut.getHeight() / 2, mBitmapOut.getWidth() / 2,
                    paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            mTempCanvas.drawBitmap(mBitmapOut, rect, rect, paint);

        }

        if (isRotateEnable) {
            currentDegree = (currentDegree + 1) % 360;
            canvas.save();
            canvas.rotate(currentDegree, defaultWidth / 2, defaultHeight / 2);
            canvas.drawBitmap(output, defaultWidth / 2 - radius, defaultHeight / 2 - radius, null);
            canvas.restore();
            if (isRotateEnable) {
                postInvalidateDelayed(30);
            }
        } else {
            canvas.save();
            canvas.rotate(currentDegree, defaultWidth / 2, defaultHeight / 2);
            canvas.drawBitmap(output, defaultWidth / 2 - radius, defaultHeight / 2 - radius, null);
            canvas.restore();
        }

    }

    private Bitmap getCuttedPicture(Drawable DrawbleSrc) {
        Bitmap mBitmapOrigin = ((BitmapDrawable) DrawbleSrc).getBitmap();
        int mWidth = mBitmapOrigin.getWidth();
        int mHeight = mBitmapOrigin.getHeight();


        float scale = Math.min((float) mWidth / (float) defaultWidth, (float) mHeight / (float) defaultHeight);
        Bitmap mBitmapScaled = Bitmap.createScaledBitmap(mBitmapOrigin, (int) (mWidth / scale), (int) (mHeight / scale), false);
        int x;
        int y;

        x = mBitmapScaled.getWidth() / 2 - radius;
        y = mBitmapScaled.getHeight() / 2 - radius;

        if (x < 0)
            x = 0;
        if (y < 0)
            y = 0;
        Bitmap mBitmapCropped = Bitmap.createBitmap(mBitmapScaled, x, y, diameter, diameter);
        return mBitmapCropped;
    }
}
