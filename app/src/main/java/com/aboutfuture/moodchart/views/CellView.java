package com.aboutfuture.moodchart.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.aboutfuture.moodchart.R;

public class CellView extends android.support.v7.widget.AppCompatTextView {
    private Point mPointA;
    private Point mPointB;
    private Point mPointC;
    private Path mPath;
    private Paint mTrianglePaint;
    private int mTriangleColor;

    public CellView(Context context) {
        super(context);
        init(null);
    }

    public CellView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CellView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(@Nullable AttributeSet set) {
        // Triangle
        mPointA = new Point(0, 50);
        mPointB = new Point(50, 0);
        mPointC = new Point(50, 50);
        mPath = new Path();
        mPath.setFillType(Path.FillType.EVEN_ODD);

        mTrianglePaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        TypedArray typedArray = getContext().obtainStyledAttributes(set, R.styleable.CellView);
        mTriangleColor = typedArray.getColor(R.styleable.CellView_second_color, Color.TRANSPARENT);
        mTrianglePaint.setColor(mTriangleColor);
        typedArray.recycle();
    }

    public void setSecondColor(int color) {
        mTriangleColor = color;
        //mTrianglePaint.setColor(color);
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // Draw triangle
        mPointA.y = getHeight();
        mPointB.x = getWidth();
        mPointC.x = getWidth();
        mPointC.y = getHeight();

        mPath.lineTo(mPointA.x, mPointA.y);
        mPath.lineTo(mPointB.x, mPointB.y);
        mPath.lineTo(mPointC.x, mPointC.y);
        mPath.lineTo(mPointA.x, mPointA.y);
        mPath.close();

        canvas.drawPath(mPath, mTrianglePaint);
        //canvas.save();
        super.onDraw(canvas);
        //canvas.restore();
    }
}
