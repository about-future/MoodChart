package com.aboutfuture.moodchart.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.aboutfuture.moodchart.R;

public class HexaView extends AppCompatTextView {

    private Path mHexaPath;
    private Paint mHexaPaint;
    private int mHexaColor;
    private int mHexaSize;

    public HexaView(Context context) {
        super(context);
        init(null);
    }

    public HexaView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public HexaView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(@Nullable AttributeSet set) {
        if (set == null) return;

        // Hexa paint
        mHexaPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mHexaPaint.setStyle(Paint.Style.FILL);

        // Hexa path
        mHexaPath = new Path();

        TypedArray typedArray = getContext().obtainStyledAttributes(set, R.styleable.HexaView);
        mHexaColor = typedArray.getColor(R.styleable.HexaView_hexa_color, Color.BLACK);
        //mHexaSize = typedArray.getDimensionPixelSize(R.styleable.HexaView_hexa_height, 86);
        mHexaPaint.setColor(mHexaColor);
        typedArray.recycle();
    }

    public void setHexaColor(int color) {
        mHexaColor = color;
        mHexaPaint.setColor(mHexaColor);
        invalidate();
        requestLayout();
    }

//    public void setHexaSize(int height) {
//        mHexaSize = height;
//        invalidate();
//        requestLayout();
//    }

    public int getHexaColor() { return mHexaColor; }

    @Override
    protected void onDraw(Canvas canvas) {
        // Hexagon path
        mHexaPath.moveTo(0, getHeight() / 2);

        mHexaPath.lineTo(getWidth() / 4, 0);
        mHexaPath.lineTo(3 * getWidth() / 4, 0);
        mHexaPath.lineTo(getWidth(), getHeight() / 2);
        mHexaPath.lineTo(3 * getWidth() / 4, getHeight());
        mHexaPath.lineTo(getWidth() / 4, getHeight());
        mHexaPath.lineTo(0, getHeight() / 2);
        mHexaPath.close();

        canvas.clipPath(mHexaPath);
        // Draw a hexagon with the above path and defined paint
        canvas.drawPath(mHexaPath, mHexaPaint);

        super.onDraw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = (int) (Math.sqrt(3) * width / 2);

        setMeasuredDimension(width, height);
    }
}
