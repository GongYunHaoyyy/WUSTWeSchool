package com.gongyunhaoyyy.wustweschool.util;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gongyunhaoyyy.wustweschool.R;
import com.gongyunhaoyyy.wustweschool.application.MyApplication;
import com.gongyunhaoyyy.wustweschool.base.BaseActivity;
import com.wang.avi.AVLoadingIndicatorView;

import static com.gongyunhaoyyy.wustweschool.util.ScreenUtil.dp2px;

/**
 * @author: Gong Yunhao
 * @version: V1.0
 * @date: 2018/8/27
 * @github https://github.com/Roman-Gong
 * @blog https://www.jianshu.com/u/52a8fa1f29fb
 */
public class ToastUtil {
    private static Toast toast;
    private static AlertDialog dialog;

    public static void showToast(int textId) {
        showToast(MyApplication.getContext().getString(textId));
    }

    public static void showToast(String text) {
        if (toast == null) {
            toast = Toast.makeText(MyApplication.getContext(), text, Toast.LENGTH_SHORT);
        } else {
            toast.setText(text);
        }
        toast.show();
    }

    public static void showToast(BaseActivity activity, String text) {
        if (toast == null) {
            toast = Toast.makeText(activity, text, Toast.LENGTH_SHORT);
        } else {
            toast.setText(text);
        }
        toast.show();
    }

    /**
     * 显示带图片的Toast
     * @param text  需要显示的文字
     * @param imgId 需要显示的图片
     */
    public static void showImageToast(String text, int imgId) {
        Toast toast = Toast.makeText(MyApplication.getContext(), text, Toast.LENGTH_SHORT);
        toast.setText(text);
        LinearLayout toastView = (LinearLayout) toast.getView();
        toastView.setGravity( Gravity.CENTER);
        ImageView toastImg = new ImageView(MyApplication.getContext());
        toastImg.setLayoutParams(new LinearLayout.LayoutParams(dp2px(25), dp2px(25)));
        toastImg.setScaleType( ImageView.ScaleType.FIT_XY);
        toastImg.setImageResource(imgId);
        toastView.addView(toastImg, 0);
        toast.show();
    }

    public static void cancel(){
        if (toast != null) {
            toast.cancel();
        }
        if (dialog != null) {
            dialog.cancel();
        }
    }

    public static void cancelToast() {
        if (toast != null) {
            toast.cancel();
        }
    }

    public static void cancelDialog() {
        if (dialog != null) {
            dialog.cancel();
        }
    }

    public static void loadingDialog(String text,boolean cancelable){
        View view= LayoutInflater.from(MyApplication.getContext()).inflate
                (R.layout.toast_loading,null);
        AVLoadingIndicatorView avl = view.findViewById(R.id.avl);
        avl.show();
        TextView tv=view.findViewById(R.id.tv);
        tv.setText(text);
        dialog=new AlertDialog.Builder(MyApplication.getContext(),R.style.CustomDialog)
                .setView(view)
                .setCancelable(cancelable)
                .create();
        dialog.show();
    }
}
