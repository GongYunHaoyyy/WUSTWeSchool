package com.gongyunhaoyyy.wustweschool.ui;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by acer on 2017/10/12.
 */

public class NoScrollViewPager extends ViewPager {
    private boolean isScroll;
    public NoScrollViewPager(Context context,AttributeSet attrs){
        super(context, attrs);
    }

    public NoScrollViewPager(Context context) {
        super(context);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);   // return true;不行
    }
    //这个方法传入false，设置view之间取消跳转动画
    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem( item,false );
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // return false;//可行,不拦截事件,
        // return true;//不行,孩子无法处理事件
        //return super.onInterceptTouchEvent(ev);//不行,会有细微移动
        if (isScroll){
            return super.onInterceptTouchEvent(ev);
        }else{
            return false;
        }
    }
    /**
     * 是否消费事件
     * 消费:事件就结束
     * 不消费:往父控件传
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isScroll){
            return super.onTouchEvent(ev);
        }else {
            return true;// 可行,拦截事件
        }
    }
    public void setScroll(boolean scroll) {
        isScroll = scroll;
    }
}
