package com.example.like.mylibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by like on 2015/10/9.
 */
public class LineChartView extends View implements LineChartAdapter.DataChangeListener{

    private LineChartAdapter adapter;
    private Paint mPaint;
    private Path linePath;
    private Path areaPath;

    private int backgroudColor, divideLineColor,
            chartAreaColor, chartLineColor, textColor;
    private int textSize;

    private int viewWidth, viewHeight;
    private int leftValueWidth;
    private int bottomIndexHeight;
    private int innerPadding;       // 四侧留出边距

    private int maxValue;

    private List<PointSeed> seeds;

    public LineChartView(Context context) {
        this(context, null);
    }

    public LineChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs);
        mPaint = new Paint();
        mPaint.setTextSize(textSize);
        linePath = new Path();
        areaPath = new Path();
        seeds = new ArrayList<PointSeed>();
    }

    private void initAttr(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LineChartView);
        backgroudColor = a.getColor(R.styleable.LineChartView_backgroudColor, 0xFFF5F5F5);
        divideLineColor = a.getColor(R.styleable.LineChartView_divideLineColor,0xFFDADADA);
        chartAreaColor = a.getColor(R.styleable.LineChartView_chartAreaColor,0xFFBCE6E0);
        chartLineColor = a.getColor(R.styleable.LineChartView_chartLineColor,0xFF35BEA9);
        textColor = a.getColor(R.styleable.LineChartView_android_textColor, 0xFFB1B1B1);
        textSize = a.getDimensionPixelSize(R.styleable.LineChartView_android_textSize,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
        innerPadding = a.getDimensionPixelSize(R.styleable.LineChartView_innerPadding,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));
        a.recycle();
    }

    public void setAdapter(LineChartAdapter adapter) {
        this.adapter = adapter;
        this.adapter.setDataChangeListener(this);
        changeAdapter();
    }

    private void changeAdapter() {
        maxValue = adapter.getMaxVlaue();
        maxValue = ((maxValue /10)+1)*10;

        // 获取文字宽度
        Rect mBound = new Rect();
        String textStr = maxValue+"";
        mPaint.getTextBounds(textStr, 0, textStr.length(), mBound);
        leftValueWidth = mBound.width();
        bottomIndexHeight = mBound.height()+innerPadding;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewWidth = getMeasuredWidth() - innerPadding;
        viewHeight = getMeasuredHeight() - innerPadding;
        measureAdapterData();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawBackground(canvas);
        drawChatArea(canvas);
        drawChartLine(canvas);
    }

    private void drawChatArea(Canvas canvas) {
        areaPath.reset();
        areaPath.moveTo(innerPadding + leftValueWidth, innerPadding + viewHeight - bottomIndexHeight);
        for (PointSeed seed: seeds) {
            areaPath.lineTo(seed.getLocationX(),seed.getLocationY());
        }
        areaPath.lineTo(viewWidth, innerPadding + viewHeight - bottomIndexHeight);
        areaPath.close();
        mPaint.setColor(chartAreaColor);
        canvas.drawPath(areaPath, mPaint);
    }

    private void drawChartLine(Canvas canvas) {
        linePath.reset();
        for (PointSeed seed: seeds) {
            if (seeds.indexOf(seed) == 0) {
                linePath.moveTo(seed.getLocationX(),seed.getLocationY());
            } else {
                linePath.lineTo(seed.getLocationX(),seed.getLocationY());
            }
        }
        mPaint.setColor(chartLineColor);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(linePath, mPaint);

        mPaint.setStyle(Paint.Style.FILL);
        for (PointSeed seed: seeds) {
            canvas.drawCircle(seed.getLocationX(), seed.getLocationY(), 10, mPaint);
        }
    }

    private void drawBackground(Canvas canvas) {
        int chartDrawableHeight = viewHeight-bottomIndexHeight;

        // 文字 左侧
        mPaint.setColor(textColor);
        float textAscent = mPaint.getFontMetrics().ascent;
        canvas.drawText(Integer.toString(maxValue), 0, innerPadding - textAscent/2, mPaint);
        canvas.drawText(Integer.toString(maxValue/5*4), 0, innerPadding + chartDrawableHeight/5 - textAscent/2, mPaint);
        canvas.drawText(Integer.toString(maxValue/5*3), 0, innerPadding + chartDrawableHeight/5*2 - textAscent/2, mPaint);
        canvas.drawText(Integer.toString(maxValue/5*2), 0, innerPadding + chartDrawableHeight/5*3 - textAscent/2, mPaint);
        canvas.drawText(Integer.toString(maxValue/5*1), 0, innerPadding + chartDrawableHeight/5*4 - textAscent/2, mPaint);
        // 文字 底部
        for (PointSeed seed: seeds) {
            canvas.drawText(seed.getIndex(), seed.getLocationX()-(int) (mPaint.measureText(seed.getIndex())/2), viewHeight + innerPadding, mPaint);
        }

        // 背景布局
        mPaint.setColor(backgroudColor);
        canvas.drawRect(innerPadding + leftValueWidth, innerPadding, viewWidth, innerPadding + chartDrawableHeight, mPaint);
        mPaint.setColor(divideLineColor);
        canvas.drawLine(innerPadding + leftValueWidth,innerPadding ,viewWidth,innerPadding, mPaint);
        canvas.drawLine(innerPadding + leftValueWidth,innerPadding + chartDrawableHeight/5,viewWidth,innerPadding + chartDrawableHeight/5, mPaint);
        canvas.drawLine(innerPadding + leftValueWidth,innerPadding + chartDrawableHeight/5*2,viewWidth,innerPadding + chartDrawableHeight/5*2, mPaint);
        canvas.drawLine(innerPadding + leftValueWidth,innerPadding + chartDrawableHeight/5*3,viewWidth,innerPadding + chartDrawableHeight/5*3, mPaint);
        canvas.drawLine(innerPadding + leftValueWidth,innerPadding + chartDrawableHeight/5*4,viewWidth,innerPadding + chartDrawableHeight/5*4, mPaint);
        canvas.drawLine(innerPadding + leftValueWidth,innerPadding + chartDrawableHeight,viewWidth,innerPadding + chartDrawableHeight, mPaint);
    }

    @Override
    public void onChange() {
        changeAdapter();
        measureAdapterData();
        invalidate();
    }

    private void measureAdapterData() {
        if (adapter != null && adapter.getCount() >0) {
            seeds.clear();
            int count = adapter.getCount();

            for (int i=0; i<count; i++) {
                int value = adapter.getValueByPosition(i);
                PointSeed seed = new PointSeed();
                seed.setIndex(adapter.getIndexByPosition(i))
                        .setValue(value)
                        .setLocationX(innerPadding + leftValueWidth + (viewWidth - leftValueWidth - innerPadding) * i / (count - 1))
                        .setLocationY(viewHeight - bottomIndexHeight + innerPadding-((viewHeight-bottomIndexHeight)*value)/maxValue);

                seeds.add(seed);

            }
        }
    }

    static class PointSeed {
        private int value;
        private String index;
        private int locationX;
        private int locationY;

        public PointSeed setIndex(String index) {
            this.index = index;
            return this;
        }

        public PointSeed setLocationX(int locationX) {
            this.locationX = locationX;
            return this;
        }

        public PointSeed setLocationY(int locationY) {
            this.locationY = locationY;
            return this;
        }

        public PointSeed setValue(int value) {
            this.value = value;
            return this;
        }

        public String getIndex() {
            return index;
        }

        public int getLocationX() {
            return locationX;
        }

        public int getLocationY() {
            return locationY;
        }

        public int getValue() {
            return value;
        }
    }
}
