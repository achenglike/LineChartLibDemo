package com.example.like.linechart;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * �Զ���View
 * 
 * @author Aige
 * @since 2014/11/17
 */
public class CustomView extends View implements Runnable {
	private Paint mPaint;// ����
	private Context mContext;// �����Ļ�������

	private int radiu;// Բ���뾶

	public CustomView(Context context) {
		this(context, null);
	}

	public CustomView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;

		// ��ʼ������
		initPaint();
	}

	/**
	 * ��ʼ������
	 */
	private void initPaint() {
		// ʵ��ʲ��򿪿����
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

		/*
		 * ���û�����ʽΪ��ߣ�Բ�������Ȼ������䲻Ȼ��ô��˼��
		 * 
		 * ������ʽ�����֣�
		 * 1.Paint.Style.STROKE�����
		 * 2.Paint.Style.FILL_AND_STROKE����߲����
		 * 3.Paint.Style.FILL�����
		 */
		mPaint.setStyle(Paint.Style.STROKE);

		// ���û�����ɫΪǳ��ɫ
		mPaint.setColor(Color.LTGRAY);

		/*
		 * ������ߵĴ�ϸ����λ������px
		 * ע�⣺��setStrokeWidth(0)��ʱ����߿�Ȳ���Ϊ0����ֻռһ������
		 */
		mPaint.setStrokeWidth(10);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		// ����Բ��
		canvas.drawCircle(MeasureUtil.getScreenSize((Activity) mContext)[0] / 2, MeasureUtil.getScreenSize((Activity) mContext)[1] / 2, radiu, mPaint);
	}

	@Override
	public void run() {
		/*
		 * ȷ���̲߳���ִ�в���ˢ�½���
		 */
		while (true) {
			try {
				/*
				 * ���뾶С��200���Լӷ������200�����ð뾶ֵ��ʵ����
				 */
				if (radiu <= 200) {
					radiu += 10;

					// ˢ��View
					postInvalidate();
				} else {
					radiu = 0;
				}

				// ÿִ��һ����ͣ40����
				Thread.sleep(40);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
