package com.justao.weathernotice.ultil;

import java.util.ArrayList;
import java.util.List;

import com.justao.weathernotice.data.WeatherPic;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;

import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

public class TrendView extends View {
    private List<Integer> nums;
    private List<Bitmap> topBitmaps;
    private List<Bitmap> lowBitmaps;

    private float pointRadius = 15;
    
    private Paint mTextPaint;
    private Paint mPointPaint;
    private Paint mLinePaint;
	int focusIndex = 1;//默认为第二个点

    private Context c;

    public TrendView(Context context){
        super(context);
        this.c = context;
        init();
    }

    public TrendView(Context context, AttributeSet attrs){
        super(context,attrs);
        this.c = context;
        init();
    }
    
    public void setNumsAndDraw(List<Integer> nums) {
    	this.nums = nums;
    	postInvalidate();
    }
    
    public void setBitmap(List<Integer> topList,List<Integer> lowList){
    	int topIndex,lowIndex;
    	topBitmaps.clear();
    	lowBitmaps.clear();
    	
    	for (int i = 0; i < topList.size(); i++) 
    	{
    		topIndex = topList.get(i);
    		lowIndex = lowList.get(i);
			topBitmaps.add(WeatherPic.getSmallPic(c, topIndex, 0));
			lowBitmaps.add(WeatherPic.getSmallPic(c, lowIndex, 1));
		}
    }
    
   public void setFocusIndexAndRedraw(int focusIndex){
    	this.focusIndex = focusIndex;
    	postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        float fontHeight = fontMetrics.bottom - fontMetrics.top;
        int n = nums.size();
        int temSpace = 8;
        int posX,posY,lastposX=0,lastposY=0;
        String[] dayStr = new String []{"今天","明天","后天","大后天"};
		
        for (int i = 0; i < n; ++ i)
        {
			posX = this.getWidth()/n/2* (2*i+1);
			posY = this.getHeight()*2/3 - nums.get(i)* temSpace;
			Bitmap lowBmp = lowBitmaps.get(i);
			Bitmap topBmp = topBitmaps.get(i);
			
			if (i != 0) 
			{
				canvas.drawLine(lastposX, lastposY, posX, posY, mLinePaint);
			}
			if(i == focusIndex)
			{
				mPointPaint.setColor(Color.GREEN);
				canvas.drawCircle(posX, posY, pointRadius, mPointPaint);
				mPointPaint.setColor(Color.WHITE);
			}
			else{
				canvas.drawCircle(posX, posY, pointRadius, mPointPaint);
			}
			canvas.drawText(nums.get(i)+"℃", posX,posY- fontHeight/2, mTextPaint);
			canvas.drawText(dayStr[i], posX,posY+ fontHeight, mTextPaint);
			canvas.drawBitmap(lowBmp, posX-lowBmp.getWidth()/2, posY+fontHeight, null);
			canvas.drawBitmap(topBmp, posX-topBmp.getWidth()/2, posY-topBmp.getHeight()-fontHeight, null);
			lastposX = posX;
			lastposY = posY;
		}
    }

    private void init(){
        nums = new ArrayList<Integer>();
        topBitmaps = new ArrayList<Bitmap>();
        lowBitmaps = new ArrayList<Bitmap>();
        
        mPointPaint = new Paint();
        mPointPaint.setAntiAlias(true);
        mPointPaint.setColor(Color.WHITE);
        
        mLinePaint = new Paint();
        mLinePaint.setAntiAlias(true);
        mLinePaint.setColor(Color.WHITE);
        mLinePaint.setStrokeWidth(8);
        mLinePaint.setStyle(Style.FILL);
        
        mTextPaint = new Paint();
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextSize(40F);
        mTextPaint.setTextAlign(Align.CENTER);
    }

}
