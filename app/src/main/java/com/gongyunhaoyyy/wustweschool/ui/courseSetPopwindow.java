package com.gongyunhaoyyy.wustweschool.ui;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;


/**
 * Created by acer on 2017/10/30.
 */

public class courseSetPopwindow implements PopupWindow.OnDismissListener{
    private PopupWindowListener mPopupWindowListener;
    private PopupWindow mPopupWindow;
    private Activity mActivity;
    private View mParentView;
    private int mWidth;
    private int mHeight;
    private View mPopupWindowView;
    private boolean focusable;

    public courseSetPopwindow(View parentView, Activity activity, View contentView, int width, int
            height, boolean focusable) {
        this.mActivity = activity;
        this.mParentView = parentView;
        this.mWidth = width;
        this.mHeight = height;
        this.focusable = focusable;
        this.mPopupWindowView = contentView;
    }

    /**
     * 显示PopupWindow
     */
    public void showView() {

        mPopupWindow = new PopupWindow(mPopupWindowView, mWidth, mHeight, focusable);
        if (mPopupWindowListener != null) {
            mPopupWindowListener.initView();
        }
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(0xFFFFFF));
        //        mPopupWindow.setAnimationStyle( R.style.myCourseAnim );
        mPopupWindow.showAtLocation(mParentView, Gravity.TOP, 0, dp2px( 75 ));
        mPopupWindow.update();
        mPopupWindow.setOnDismissListener(this);
    }

    /** dp转px **/
    private int dp2px(float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, mActivity.getResources().getDisplayMetrics());
    }

    /**
     * 点击悬浮窗外面时的操作
     */
    @Override
    public void onDismiss() {
        setBackgroundAlpha(1f);
    }

    public interface PopupWindowListener {
        // 初始化PopupWindow的控件
        void initView();
    }

    public void setOnPopupWindowListener(PopupWindowListener listener) {
        this.mPopupWindowListener = listener;
    }

    /**
     * 隐藏PopupWindow
     */
    public void dismiss() {
        if (mPopupWindow != null) {
            mPopupWindow.dismiss();
            mPopupWindow = null;
        }
    }

    //设置屏幕背景透明效果
    public void setBackgroundAlpha(float alpha) {
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        lp.alpha = alpha;
        mActivity.getWindow().setAttributes(lp);
    }
}
