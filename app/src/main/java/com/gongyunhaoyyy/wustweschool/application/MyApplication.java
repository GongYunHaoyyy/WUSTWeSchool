package com.gongyunhaoyyy.wustweschool.application;

import android.app.Application;
import android.content.Context;

import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

import org.litepal.LitePal;

/**
 * @author: Gong Yunhao
 * @version: V1.0
 * @date: 2018/7/19
 * @github https://github.com/GongYunHaoyyy
 * @blog https://www.jianshu.com/u/52a8fa1f29fb
 */
public class MyApplication extends Application{

    private static Context mthis;

    @Override
    public void onCreate() {
        super.onCreate( );
        mthis = this;
        LitePal.initialize( this );
        LitePal.getDatabase();
    }

    public static Context getContext(){
        return mthis;
    }
}
