package com.chunlei.sapp.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

public class RadarView extends View{

	
	private float mPx;
	private float mPy;
	private float mScanLineAngle = 0.0f;
	private Paint mPaint;
	
	public RadarView(Context context) {
		this(context, null);
	}

	public RadarView(Context context, AttributeSet attrs) {
		this(context, null, 0);
	}
	
	public RadarView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		mPx = getWidth() / 2;
		mPy = getHeight() / 2;
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				invalidate();
			}
		};
		
		new Thread() {
			@Override
			public void run() {
				while(true) {
				    mScanLineAngle -= 0.06;
				    handler.sendEmptyMessage(0);
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}.start();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		mPaint.setColor(Color.WHITE);
		canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
		drawCircles(canvas);
		drawScanLine(canvas);
		super.onDraw(canvas);
	}
	
	private void drawCircles(Canvas canvas) {
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(Color.BLACK);
		paint.setStrokeWidth(2);
		
		float radius = Math.max(getWidth(), getHeight()) / 2;
		float step = radius / 4;

		
		
		float px = getWidth() / 2;
		float py = getHeight() / 2;
		
		for (int i = 0;i < 4; i ++) {
	        paint.setColor(Color.WHITE);
	        paint.setStrokeWidth(1);
	        canvas.drawCircle(px, py, radius, paint);
	        
	        paint.setColor(Color.GRAY);
	        paint.setStrokeWidth(1);
	        canvas.drawCircle(px, py, radius - 1, paint);  
	        
	    	paint.setColor(Color.WHITE);
	        paint.setStrokeWidth(2);
	        canvas.drawCircle(px, py, radius - 2, paint);  

	        
	        radius -= step;
		}
		
	}
	
	private void drawScanLine(Canvas canvas) {
		float radius = Math.max(getWidth(), getHeight()) / 2;
		float width = (float) (radius * Math.sin(mScanLineAngle));
		float height = (float) (radius * Math.cos(mScanLineAngle));
		
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(Color.GRAY);
		paint.setStrokeWidth(1);
		canvas.drawLine(getWidth() / 2, getHeight() / 2, getWidth() / 2 + width, getHeight() / 2 + height, paint);	
	}

	
}
